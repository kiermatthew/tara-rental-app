package com.example.tara.Profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.tara.LoginRegistration.LoginActivity;
import com.example.tara.Models.User;
import com.example.tara.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragmentMenu extends Fragment {

    private TextView tvAccount,logoutBtn,tvUserName, tvAboutTara, tvHowTaraWorks, tvContactSupport, tvLegal;
    private ImageView ivPhoto1;
    GoogleSignInAccount signInAccount;
    String databaseLocation;
    FirebaseAuth mAuth;
    GoogleSignInClient gSignIn;
    DatabaseReference databaseReference;
    LinearLayout profileBtn;

    //commentdsadsa

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        tvLegal = view.findViewById(R.id.legalBtn);
        tvContactSupport = view.findViewById(R.id.contactSupportBtn);
        tvAboutTara = view.findViewById(R.id.aboutTaraBtn);
        tvHowTaraWorks = view.findViewById(R.id.howItWorksBtn);

        tvAccount = view.findViewById(R.id.accountBtn);
        logoutBtn = view.findViewById(R.id.logoutBtn);
        tvUserName = view.findViewById(R.id.userName);
        ivPhoto1 = view.findViewById(R.id.userPhoto);
        profileBtn = view.findViewById(R.id.profileBtn);
        signInAccount = GoogleSignIn.getLastSignedInAccount(getContext());
        databaseLocation = getString(R.string.databasePath);
        mAuth = FirebaseAuth.getInstance();
        gSignIn = GoogleSignIn.getClient(getActivity(), new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build());


        //fetch data from database
        FirebaseUser currentUser = mAuth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance(databaseLocation);
        databaseReference = database.getReference("users").child(currentUser.getUid());

        tvAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(), AccountActivity.class));
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutUser();
            }
        });
        

        tvAboutTara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AboutTara.class));
            }
        });

        tvHowTaraWorks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), HowTaraWorks.class));
            }
        });

        tvContactSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ContactSupport.class));
            }
        });

        tvLegal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Legal.class));
            }
        });



        //if gmail account is signed in
        if(signInAccount != null){
            Uri photoUrl = signInAccount.getPhotoUrl();
            tvUserName.setText(signInAccount.getDisplayName());
            Glide.with(getContext()).load(photoUrl).into(ivPhoto1);
        }
        else
            ivPhoto1.setImageResource(R.drawable.ic_profile_image);

        //detect changes in database and updates data
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user =  snapshot.getValue(User.class);
                //display name nad profile picture
                if(user!=null){
                    if(!user.imageUrl.isEmpty()){
                        Uri imageUri = Uri.parse(user.imageUrl);
                        Glide.with(getContext()).load(imageUri).into(ivPhoto1);
                    }
                    else
                        ivPhoto1.setImageResource(R.drawable.ic_profile_image);
                    tvUserName.setText(user.name);
                }
                else {
                    Toast.makeText(getContext(),"Error retrieving info",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        return view;
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }

    private void logoutUser(){
        mAuth.getInstance().signOut();
        gSignIn.signOut();
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }

}