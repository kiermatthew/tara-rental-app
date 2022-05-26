package com.example.tara.LoginRegistration;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tara.R;

public class GetStarted extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);
        getSupportActionBar().hide();

        Button loginBtn = findViewById(R.id.loginBtn2);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(GetStarted.this,LoginActivity.class));
              finish();
            }
        });

        SharedPreferences prefs = getSharedPreferences("PREFERENCE",MODE_PRIVATE);
        String FirstTime = prefs.getString("FirstTimeInstall","");

        if(FirstTime.equals("Yes")){
            startActivity(new Intent(GetStarted.this,LoginActivity.class));
        }else{
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("FirstTimeInstall","Yes");
            editor.apply();
        }

    }
}
