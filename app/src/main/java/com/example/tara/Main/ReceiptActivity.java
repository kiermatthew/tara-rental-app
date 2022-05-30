package com.example.tara.Main;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tara.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ReceiptActivity extends AppCompatActivity {
    Button doneBtn;
    TextView amount,host,brandModelYear;
    String uid,price,carHostName,bmy;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    TextView transactionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        getSupportActionBar().hide();
        doneBtn = findViewById(R.id.doneBtn);
        transactionId = findViewById(R.id.transactionId);
        amount = findViewById(R.id.amount);
        TextView textView=findViewById(R.id.date);
        host = findViewById(R.id.hostReceipt);
        brandModelYear = findViewById(R.id.brandModelYear);
        carHostName = getIntent().getStringExtra("carHostName");
        bmy = getIntent().getStringExtra("bmy");
        price = getIntent().getStringExtra("price");

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        textView.setText(currentDateandTime);
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        RandomString randomString = new RandomString();
        String result = randomString.generateAlphanumeric(12);
        transactionId.setText(result);
        amount.setText("Php "+price);
        host.setText(capitalizeWord(carHostName));
        brandModelYear.setText(bmy);


        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReceiptActivity.this,Main.class));
            }
        });
    }

    public void Screenshot(View view) {
        View view1 = getWindow().getDecorView().getRootView();
        view1.setDrawingCacheEnabled(true);

        Bitmap bitmap = Bitmap.createBitmap(view1.getDrawingCache());
        view1.setDrawingCacheEnabled(false);

        String filePath = Environment.getExternalStorageDirectory()+"/Download/"+ Calendar.getInstance().getTime().toString()+".jpg";
        File fileScreenshot = new File(filePath);

       FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(fileScreenshot);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            Toast.makeText(ReceiptActivity.this, "Receipt saved to Downloads", Toast.LENGTH_LONG).show();


            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(fileScreenshot);
            intent.setDataAndType(uri,"image/jpeg");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();



        }

    }

    public String capitalizeWord(String str){
        String[] words =str.split("\\s");
        String capitalizeWord="";
        for(String w:words){
            String first=w.substring(0,1);
            String afterfirst=w.substring(1);
            capitalizeWord+=first.toUpperCase()+afterfirst+" ";
        }
        return capitalizeWord.trim();
    }

}