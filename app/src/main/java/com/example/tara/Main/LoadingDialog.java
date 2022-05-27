package com.example.tara.Main;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.example.tara.R;

public class LoadingDialog {
    Activity activty;
    Dialog dialog;
    TextView loadingMessage;

    public LoadingDialog(Activity activty){
        this.activty = activty;
    }

    public void startLoadingDialog(String loadingMsg){
        dialog = new Dialog(activty);
        LayoutInflater inflater = activty.getLayoutInflater();
        dialog.setContentView(R.layout.custom_loading);

        loadingMessage = dialog.findViewById(R.id.txtLoading);
        loadingMessage.setText(loadingMsg);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false );
        dialog.show();
    }

    public void dismissDialog(){
        dialog.dismiss();
    }
}
