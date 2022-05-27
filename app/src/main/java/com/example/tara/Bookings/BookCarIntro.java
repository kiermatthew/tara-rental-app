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
import com.google.firebase.database.DatabaseReference;

public class BookCarIntro extends Fragment {
    Button  bookNow;
//nako naman

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_car, container, false);

        MessageDialog loadingDialog = new MessageDialog(getActivity());

        bookNow = view.findViewById(R.id.bookNowBtn);

        bookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingDialog.startLoadingDialog();
                //startActivity(new Intent(getContext(),Main.class));
            }
        });


        return view;
    }

}