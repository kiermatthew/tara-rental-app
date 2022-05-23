package com.example.tara.Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.example.tara.R;

public class Legal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legal);

        WebView webView=findViewById(R.id.webViewLegal);
        webView.loadUrl("file:///android_asset/privacyPolicy.html");

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