package com.example.tara.LoginRegistration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tara.R;

public class LoginSignupActivity extends AppCompatActivity {
    private Button loginBtn;
    private TextView dontHaveAcc;
    private TextView signUp;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);
        getSupportActionBar().hide();

        loginBtn = findViewById(R.id.loginBtn2);
        dontHaveAcc = findViewById((R.id.dontHaveAcc2));
        signUp = findViewById(R.id.signUp2);

        loginBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(LoginSignupActivity.this,LoginActivity.class));
            finish();
        }
    });

        dontHaveAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginSignupActivity.this,SignupActivity.class));
                finish();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginSignupActivity.this,SignupActivity.class));
                finish();
            }
        });

    }
}
