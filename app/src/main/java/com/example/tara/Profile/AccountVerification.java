package com.example.tara.Profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tara.Main.LoadingDialog;
import com.example.tara.Main.Main;
import com.example.tara.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AccountVerification extends AppCompatActivity {
    Button verifyBtn;
    DatabaseReference userRef;
    String userId;
    Uri dlUri1, dlUri2, selfieUri;
    StorageReference storageReference;
    ImageView ivDl1, ivDl2, ivSelfie;
    CardView cvDl2;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_verification);
        getSupportActionBar().hide();

        String databaseLocation = getString(R.string.databasePath);
        Toolbar toolbar = findViewById(R.id.appBar);
        verifyBtn = findViewById(R.id.verifyBtn);
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userRef = FirebaseDatabase.getInstance(databaseLocation).getReference().child("users").child(userId);
        ivDl1 = findViewById(R.id.driverLicense1);
        ivDl2 = findViewById(R.id.driverLicense2);
        ivSelfie = findViewById(R.id.selfie1);
        cvDl2 = findViewById(R.id.cvDL2);

        ivDl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage(1);
            }
        });

        ivDl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage(2);
            }
        });

        ivSelfie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage(3);
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        LoadingDialog loadingDialog = new LoadingDialog(AccountVerification.this);

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingDialog.startLoadingDialog("Uploading data");
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadingDialog.dismissDialog();
                        userRef.child("isVerified").setValue(true);
                        //uploadImage();
                        startActivity(new Intent(AccountVerification.this, AccountActivity.class));
                    }
                },3000);
            }
        });


    }
    private void selectImage(int requestCode){
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, requestCode);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //if image is selected, display the image
        if(requestCode==1 && resultCode== -1 && data != null){
            try {
                dlUri1 = data.getData();
                ivDl1.setImageURI(dlUri1);
                cvDl2.setVisibility(View.VISIBLE);
            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(AccountVerification.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }
        if(requestCode==2 && resultCode== -1 && data != null){
            try {
                dlUri2 = data.getData();
                ivDl2.setImageURI(dlUri2);
            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(AccountVerification.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }
        if(requestCode==3 && resultCode== -1 && data != null){
            try {
                selfieUri = data.getData();
                ivSelfie.setImageURI(selfieUri);
            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(AccountVerification.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void uploadImage(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
        Date now = new Date();
        String fileName = formatter.format(now);

        storageReference = FirebaseStorage.getInstance("gs://tara-351111.appspot.com/").getReference("ivDriversLicense1/"+fileName);

        storageReference.putFile(dlUri1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    // gets the image url and store it in the database
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
//                        String imageUrl = task.getResult().toString();
//                        userRef.child("imageUrl").setValue(imageUrl);
                    }
                });
                Toast.makeText(AccountVerification.this, "Upload successfully",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AccountVerification.this, "Upload failed",Toast.LENGTH_LONG).show();
            }
        });
    }

}