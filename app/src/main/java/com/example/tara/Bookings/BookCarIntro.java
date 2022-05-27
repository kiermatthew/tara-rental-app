package com.example.tara.Bookings;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tara.Explore.ExploreFragmentMenu;
import com.example.tara.Main.LoadingDialog;
import com.example.tara.Main.Main;
import com.example.tara.Main.MessageDialog;
import com.example.tara.Main.PaymentActivity;
import com.example.tara.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BookCarIntro extends Fragment {
    Button  bookNow;
    DatabaseReference userRef;
    String userId;
    Boolean isVerified = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_car, container, false);


        String databaseLocation = getString(R.string.databasePath);
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userRef = FirebaseDatabase.getInstance(databaseLocation).getReference().child("users").child(userId);
        bookNow = view.findViewById(R.id.bookNowBtn);


        bookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isVerified)
                    startActivity(new Intent(getContext(),Main.class));
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