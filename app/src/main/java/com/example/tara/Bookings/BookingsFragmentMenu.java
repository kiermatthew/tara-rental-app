package com.example.tara.Bookings;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tara.Main.RecyclerViewInterface;
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
    DatabaseReference userRef,vehicleRef;
    ArrayList<BookedCars> list;
    ArrayList<String> carIdList;
    FirebaseAuth mAuth;
    BookAdapter adapter;
    String userId,carId, carHostId;
    DataSnapshot bookedCarRef, dataSnapshot;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__booked_cars, container, false);

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        recyclerview = view.findViewById(R.id.bookingsRV);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        list = new ArrayList<>();
        adapter = new BookAdapter(getContext(),list,this);
        recyclerview.setAdapter(adapter);
        String databaseLocation = getString(R.string.databasePath);

        userRef = FirebaseDatabase.getInstance(databaseLocation).getReference("users").child(userId);
        vehicleRef = FirebaseDatabase.getInstance(databaseLocation).getReference("vehicle");

        userRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookedCarRef = snapshot.child("bookedCars");
                list.clear();
                for(DataSnapshot snapshot1 : bookedCarRef.getChildren()){
                    for(DataSnapshot snapshot2 : snapshot1.getChildren()){
                        BookedCars bookedCars = snapshot2.getValue(BookedCars.class);
                        list.add(bookedCars);
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
        int index = 0;
        for(DataSnapshot snapshot1 : bookedCarRef.getChildren()){
            if(index == position){
                DatabaseReference currentReference = snapshot1.getRef();
                carId = currentReference.getKey();

            }
            for(DataSnapshot snap2 : snapshot1.getChildren()){
                if(index == position){
                    DatabaseReference currentRef = snap2.getRef();
                    carHostId = currentRef.getKey();
                }
            }
            index++;
        }


        Intent intent = new Intent(getContext(), BookDetails.class);
        intent.putExtra("carId", carId);
        intent.putExtra("carHostId", carHostId);
        startActivity(intent);
    }
}