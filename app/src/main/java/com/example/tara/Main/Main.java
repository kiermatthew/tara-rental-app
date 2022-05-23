package com.example.tara.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;


import com.example.tara.Host.HostedCars;
import com.example.tara.Models.User;
import com.example.tara.Profile.ProfileFragmentMenu;
import com.example.tara.Explore.ExploreFragmentMenu;
import com.example.tara.Host.HostFragmentMenu;
import com.example.tara.Bookings.BookingsFragmentMenu;
import com.example.tara.R;
import com.example.tara.databinding.MainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

public class Main extends AppCompatActivity {

    private long pressedTime;
    private BottomNavigationView navigation;
    private MainBinding binding;
    boolean isHost = false;
    private FirebaseAuth mAuth;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        getSupportActionBar().hide();

        binding = MainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new ExploreFragmentMenu());

        navigation = findViewById(R.id.bottomNavigationView);
        mAuth = FirebaseAuth.getInstance();


        String databaseLocation = getString(R.string.databasePath);
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference reference =  FirebaseDatabase.getInstance(databaseLocation).getReference().child("users").child(userId);


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                assert user != null;
                if(user.isHost){
                    isHost = true;
                }
                else{
                    isHost = false;
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //switch between bottom menu
        binding.bottomNavigationView.setOnItemSelectedListener(item->{
            switch ((item.getItemId())){
                case R.id.search:
                    replaceFragment(new ExploreFragmentMenu());
                    break;
                case R.id.bookings:
                    replaceFragment(new BookingsFragmentMenu());
                    break;
                case R.id.profile:
                    replaceFragment(new ProfileFragmentMenu());
                    break;
                case R.id.host:
                    if(isHost)
                        replaceFragment(new HostedCars());
                    else
                        replaceFragment(new HostFragmentMenu());
                    break;
            }

            return true;
        });

        //make bottom menu invisible when typing
        KeyboardVisibilityEvent.setEventListener(
                this,
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        if(isOpen){
                            navigation.setVisibility(View.INVISIBLE);
                        }else{
                            navigation.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }


    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }


    @Override
    public void onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }
}