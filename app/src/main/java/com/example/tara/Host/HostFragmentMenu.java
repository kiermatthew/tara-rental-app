package com.example.tara.Host;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.tara.Explore.CarDetails;
import com.example.tara.Main.MessageDialog;
import com.example.tara.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class HostFragmentMenu extends Fragment {
    DatabaseReference userRef;
    String userId;
    Boolean isVerified = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_host, container, false);

        String databaseLocation = getString(R.string.databasePath);
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userRef = FirebaseDatabase.getInstance(databaseLocation).getReference().child("users").child(userId);
        CardView hostBtn = view.findViewById(R.id.hostBtn);


        hostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isVerified)
                    startActivity(new Intent(getContext(), HostCar.class));
                else{
                    MessageDialog loadingDialog = new MessageDialog(getActivity());
                    loadingDialog.startLoadingDialog();
                }
            }
        });

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                isVerified = (Boolean) snapshot.child("isVerified").getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        return view;
    }
}