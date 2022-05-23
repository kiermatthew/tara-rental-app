package com.example.tara.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;

import com.example.tara.R;

public class LoadingDialog {
    Activity activty;
    Dialog dialog;

    public LoadingDialog(Activity activty){
        this.activty = activty;
    }

    public void startLoadingDialog(){
        dialog = new Dialog(activty);
        LayoutInflater inflater = activty.getLayoutInflater();

        dialog.setContentView(R.layout.custom_loading);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false );
        dialog.show();
    }

    public void dismissDialog(){
        dialog.dismiss();
    }
}
