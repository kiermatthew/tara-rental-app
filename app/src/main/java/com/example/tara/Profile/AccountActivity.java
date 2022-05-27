package com.example.tara.Profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.tara.LoginRegistration.GetStarted;
import com.example.tara.LoginRegistration.LoginActivity;
import com.example.tara.LoginRegistration.User;
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
    private LinearLayout llName, llEmail, llPassword;
    private TextView tvName, tvEmail, tvPassword, verifyAccountBtn;
    private TextView tvEditName;
    private ImageView ivEditPhoto;
    private Button saveChangesBtn;
    GoogleSignInAccount signInAccount;
    StorageReference storageReference;
    DatabaseReference Databasereference;
    String databaseLocation;
    Uri imageUri;
    FirebaseUser currentUser;
    TextView resetPasswordAccount;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        getSupportActionBar().hide();
        //fetch user data

        databaseLocation = getString(R.string.databasePath);
        llName = findViewById(R.id.accountUserName);
        llEmail = findViewById(R.id.accountEmail);
        llPassword = findViewById(R.id.accountPassword);
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        Toolbar toolbar = findViewById(R.id.appBar);
        ivEditPhoto = findViewById(R.id.editPhoto);
        tvEditName = findViewById(R.id.editName);
        saveChangesBtn = findViewById(R.id.saveChangesBtn);
        verifyAccountBtn = findViewById(R.id.verifyAccountBtn);
        resetPasswordAccount = findViewById(R.id.resetPasswordLocal);
        signInAccount = GoogleSignIn.getLastSignedInAccount(this);


        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance(databaseLocation);
        Databasereference = database.getReference("users").child(currentUser.getUid());


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
                                Toast.makeText(AccountActivity.this,  "This Account is not yet Registered", Toast.LENGTH_SHORT).show();
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

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ivEditPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (signInAccount != null) {
            tvName.setText(signInAccount.getDisplayName());
            tvEmail.setText(signInAccount.getEmail());
        }


        Databasereference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null) {
                    //display user info
                    Glide.with(AccountActivity.this).load(user.imageUrl).into(ivEditPhoto);
                    tvName.setText(user.name);
                    tvEmail.setText(user.email);
                } else {
                    Toast.makeText(AccountActivity.this, "Error retrieving info", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }/*End*/
    private void selectImage() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, 2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         //if image is selected, display the image
        if(requestCode==2 && resultCode== -1 && data != null){
            imageUri = data.getData();
            ivEditPhoto.setImageURI(imageUri);
            saveChangesBtn.setVisibility(View.VISIBLE);
            saveChangesBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    uploadImage();
                }
            });

            if(signInAccount != null){
                Uri photoUrl = signInAccount.getPhotoUrl();
                tvEditName.setText(signInAccount.getDisplayName());
                Glide.with(this).load(photoUrl).into(ivEditPhoto);
            }
            else
                ivEditPhoto.setImageResource(R.drawable.ic_profile_image);

                Databasereference.addValueEventListener(new ValueEventListener() {
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
                            tvEditName.setText(snapshot.child("name").getValue().toString());
                        }
                        else {
                            Toast.makeText(AccountActivity.this,"Error retrieving info",Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });



            }
        }

        // upload the image to the cloud storage
        private void uploadImage(){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
            Date now = new Date();
            String fileName = formatter.format(now);

            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Updating profile picture");
            progressDialog.setMessage("Please wait, we are setting your picture");
            progressDialog.show();

            storageReference = FirebaseStorage.getInstance("gs://tara-351111.appspot.com/").getReference("profile/"+fileName);
            storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        // gets the image url and store it in the database
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            String imageUrl = task.getResult().toString();
                            Databasereference.child("imageUrl").setValue(imageUrl);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AccountActivity.this, "Upload failed",Toast.LENGTH_LONG).show();
                }
            });
        }




    }






