package com.example.tara.Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.example.tara.R;

public class AboutTara extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_tara);

        WebView webView=findViewById(R.id.webView);
        webView.loadUrl("file:///android_asset/aboutTara.html");

        getSupportActionBar().hide();
        Toolbar toolbar =  findViewById(R.id.appBar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}