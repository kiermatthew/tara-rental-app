package com.example.tara.Profile;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatButton;

import com.example.tara.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddContact {
    Activity activty;
    Dialog dialog;
    AppCompatButton okayBtn;
    EditText etContact;
    DatabaseReference userRef;
    String userId;

    public AddContact(Activity activty){
        this.activty = activty;
    }

    public void startLoadingDialog(){
        dialog = new Dialog(activty);
        LayoutInflater inflater = activty.getLayoutInflater();
        dialog.setContentView(R.layout.contact_dialog);

        String databaseLocation = "https://tara-351111-default-rtdb.firebaseio.com";
        okayBtn = dialog.findViewById(R.id.okayBtn);
        etContact = dialog.findViewById(R.id.etContact);
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userRef = FirebaseDatabase.getInstance(databaseLocation).getReference().child("users").child(userId);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();

        okayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contact = etContact.getText().toString();
                if(!contact.equals("")){
                    userRef.child("contactNum").setValue(contact);
                    dismissDialog();
                }

            }
        });
    }

    public void dismissDialog(){
        dialog.dismiss();
    }
}
