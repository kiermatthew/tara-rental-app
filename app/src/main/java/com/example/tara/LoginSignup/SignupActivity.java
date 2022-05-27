package com.example.tara.LoginSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tara.Main.Main;
import com.example.tara.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;

public class SignupActivity extends AppCompatActivity {
    private EditText etName, etEmail, etPassword, etConfirmPassword;
    private FirebaseAuth mAuth;
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();

        etName = findViewById(R.id.name);
        etEmail = findViewById(R.id.emailSignup);
        etPassword = findViewById(R.id.passwordSignup);
        etConfirmPassword = findViewById(R.id.confirmPassword);
        Button signupBtn = findViewById(R.id.signupBtn);
        CardView signupGoogle = findViewById(R.id.loginGoogle);
        TextView alreadyHaveAcc = findViewById(R.id.alreadyHaveAcc);
        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail().build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);


        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });

        signupGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInGoogle();
            }
        });

        alreadyHaveAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                finish();
            }
        });
    }

    public void signInGoogle(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            }catch (ApiException e){

            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                }
                else{
                    updateUI(null);
                }
            }
        });
    }
    //l

    private void updateUI(FirebaseUser user) {
        if(user != null) {
            GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(SignupActivity.this);
            String name = signInAccount.getDisplayName();
            String email = signInAccount.getEmail();
            String imageUrl = signInAccount.getPhotoUrl().toString();

            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            User userGmail = new User(name,email,userId, imageUrl,false,false,false);
            String databaseLocation = getString(R.string.databasePath);
            FirebaseDatabase.getInstance(databaseLocation).getReference().child("users").child(userId)
                    .setValue(userGmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Intent intent = new Intent(SignupActivity.this, Main.class);
                    startActivity(intent);
                    finish();
                }
            });

        }
        else
            Toast.makeText(SignupActivity.this,"Google sign in failed",Toast.LENGTH_LONG).show();

    }

    private void createAccount(){
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String confirmPW = etConfirmPassword.getText().toString();

        if(name.isEmpty()){
            etName.setError("Name cannot be empty");
            etName.requestFocus();
        }
        else if(email.isEmpty()){
            etEmail.setError("Email cannot be empty");
            etEmail.requestFocus();
        }
        else if(password.isEmpty()){
            etPassword.setError("Password cannot be empty");
            etPassword.requestFocus();
        }
        else if(confirmPW.isEmpty()){
            etConfirmPassword.setError("Please confirm password");
            etConfirmPassword.requestFocus();
        }
        else{
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                Toast.makeText(this,"Invalid email format!",Toast.LENGTH_LONG).show();
                return;
            }
            if(password.equals(confirmPW)){
                //authenticate user
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // store user info on database
                                    String imageUrl = "";
                                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                    User user = new User(name,email,userId,imageUrl,false,false,false);
                                    String databaseLocation = getString(R.string.databasePath);
                                    FirebaseDatabase.getInstance(databaseLocation).getReference().child("users").child(userId)
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Intent intent = new Intent(SignupActivity.this, Main.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });

                                } else {
                                    Toast.makeText(SignupActivity.this,"Error adding to database",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
            else{
                Toast.makeText(SignupActivity.this,"Password don't match!",Toast.LENGTH_LONG).show();
            }
        }

    }
}