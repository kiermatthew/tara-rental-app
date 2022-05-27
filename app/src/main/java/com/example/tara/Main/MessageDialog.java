package com.example.tara.Main;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;

import com.example.tara.R;

public class MessageDialog {
    Activity activty;
    Dialog dialog;

    public MessageDialog(Activity activty){
        this.activty = activty;
    }

    public void startLoadingDialog(){
        dialog = new Dialog(activty);
        LayoutInflater inflater = activty.getLayoutInflater();

        dialog.setContentView(R.layout.message_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.show();
    }

    public void dismissDialog(){
        dialog.dismiss();
    }
}
