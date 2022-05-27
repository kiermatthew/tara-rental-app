package com.example.tara.Main;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;

import com.example.tara.R;

public class MessageDialog {
    Activity activty;
    Dialog dialog;
    AppCompatButton okayBtn;

    public MessageDialog(Activity activty){
        this.activty = activty;
    }

    public void startLoadingDialog(){
        dialog = new Dialog(activty);
        LayoutInflater inflater = activty.getLayoutInflater();
        dialog.setContentView(R.layout.message_dialog);

        okayBtn = dialog.findViewById(R.id.okayBtn);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();

        okayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissDialog();
            }
        });
    }

    public void dismissDialog(){
        dialog.dismiss();
    }
}
