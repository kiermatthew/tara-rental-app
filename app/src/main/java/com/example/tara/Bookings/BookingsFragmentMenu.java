package com.example.tara.Bookings;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tara.Adapter.BookAdapter;
import com.example.tara.Adapter.VehicleAdapter;
import com.example.tara.Main.RecyclerViewInterface;
import com.example.tara.Models.Booking;
import com.example.tara.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class BookingsFragmentMenu extends Fragment implements RecyclerViewInterface {
    RecyclerView recyclerview;
    DatabaseReference userRef,vehicleRef,bookingRef;
    ArrayList<Booking> list;
    ArrayList<String> carIdList;
    FirebaseAuth mAuth;
    BookAdapter adapter;
    String userId,carId;
    DataSnapshot child;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookings, container, false);

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        recyclerview = view.findViewById(R.id.bookingsRV);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        list = new ArrayList<>();
        adapter = new BookAdapter(getContext(),list,this);
        recyclerview.setAdapter(adapter);
        String databaseLocation = getString(R.string.databasePath);

        bookingRef = FirebaseDatabase.getInstance(databaseLocation).getReference("users").child(userId);

        bookingRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("bookedCars").exists()){
                    child = snapshot.child("bookedCars");
                    for(DataSnapshot snapshot1 : child.getChildren()){
                        Booking booking = snapshot1.getValue(Booking.class);
                        list.add(booking);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }

    @Override
    public void onItemClick(int position) {

    }
}