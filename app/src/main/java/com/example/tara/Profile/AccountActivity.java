package com.example.tara.Profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tara.LoginSignup.User;
import com.example.tara.Main.LoadingDialog;
import com.example.tara.Main.ResetDialog;
import com.example.tara.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class  AccountActivity extends AppCompatActivity {
//    private LinearLayout llName, llEmail, llPassword;
    private TextView tvName, tvEmail,verifyAccountBtn, tvContact;
    private TextView tvEditName;
    private ImageView ivEditPhoto;
    GoogleSignInAccount signInAccount;
    DatabaseReference userRef;
    String databaseLocation;
    FirebaseUser currentUser;
    TextView resetPasswordAccount;
    FirebaseAuth auth;
    Boolean isVerified = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        getSupportActionBar().hide();
        //fetch user data

        databaseLocation = getString(R.string.databasePath);
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        Toolbar toolbar = findViewById(R.id.appBar);
        ivEditPhoto = findViewById(R.id.editPhoto);
        tvEditName = findViewById(R.id.editName);
        verifyAccountBtn = findViewById(R.id.verifyAccountBtn);
        resetPasswordAccount = findViewById(R.id.resetPasswordLocal);
        tvContact = findViewById(R.id.tvContact);
        signInAccount = GoogleSignIn.getLastSignedInAccount(this);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance(databaseLocation);
        userRef = database.getReference("users").child(currentUser.getUid());


        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if(signInAccount != null){
            Uri photoUrl = signInAccount.getPhotoUrl();
            tvEditName.setText(signInAccount.getDisplayName());
            tvName.setText(signInAccount.getDisplayName());
            tvEmail.setText(signInAccount.getEmail());
            Glide.with(this).load(photoUrl).into(ivEditPhoto);
        }
        else
            ivEditPhoto.setImageResource(R.drawable.ic_profile_image);

        tvContact.setText("Add contact");
        tvContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddContact addContact = new AddContact(AccountActivity.this);
                addContact.startLoadingDialog();
            }
        });

        userRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint({"ResourceAsColor", "SetTextI18n"})
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists() && snapshot.getChildrenCount() > 0){
                    User user =  snapshot.getValue(User.class);
                    assert user != null;
                    if(!user.imageUrl.isEmpty()){
                        String imageUrl = snapshot.child("imageUrl").getValue().toString();
                        Glide.with(AccountActivity.this).load(imageUrl).into(ivEditPhoto);
                    }
                    else
                        ivEditPhoto.setImageResource(R.drawable.ic_profile_image);

                    tvName.setText(snapshot.child("name").getValue().toString());
                    tvEmail.setText(snapshot.child("email").getValue().toString());
                    tvEditName.setText(snapshot.child("name").getValue().toString());
                    tvContact.setText(snapshot.child("contactNum").getValue().toString());
                }
                else {
                    Toast.makeText(AccountActivity.this,"Error retrieving info",Toast.LENGTH_LONG).show();
                }
                isVerified = (Boolean) snapshot.child("isVerified").getValue();
                if(isVerified){
                    verifyAccountBtn.setClickable(false);
                    verifyAccountBtn.setText("Verified");
                    verifyAccountBtn.setTextColor(Color.parseColor("#58b996"));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ivEditPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountActivity.this,EditProfilePicture.class));
            }
        });

        resetPasswordAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText resetMail = new EditText(view.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
                passwordResetDialog.setTitle("Reset Password?");
                passwordResetDialog.setMessage("Enter Your Email To Received Reset Link.");
                passwordResetDialog.setView(resetMail);
                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //extract the email link
                        String mail = resetMail.getText().toString();

                            auth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(AccountActivity.this, "Reset Link Sent to Your Email.", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AccountActivity.this, "This Account is not yet Registered", Toast.LENGTH_SHORT).show();
                                }
                            });
                    }
                });
                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                passwordResetDialog.create().show();
            }
        });

        verifyAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountActivity.this, AccountVerification.class));
            }
        });

    }

}






