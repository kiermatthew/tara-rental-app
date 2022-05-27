package com.example.tara.Main;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.example.tara.Profile.AccountActivity;
import com.example.tara.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ResetDialog {
    Activity activty;
    Dialog dialog;
    AppCompatButton okayBtn;
    FirebaseAuth mAuth;
    TextView tvEmail;
    String mail;

    public ResetDialog(Activity activty){
        this.activty = activty;
    }

    public void startLoadingDialog(){
        dialog = new Dialog(activty);
        dialog.setContentView(R.layout.reset_email_dialog);

        mAuth = FirebaseAuth.getInstance();
        tvEmail = dialog.findViewById(R.id.tvMailReset);
        okayBtn = dialog.findViewById(R.id.resetBtn);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();
        mail = tvEmail.getText().toString();
        okayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
                dismissDialog();
            }
        });
    }

    public void dismissDialog(){
        dialog.dismiss();
    }
}
