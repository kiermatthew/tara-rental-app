package com.example.tara.Host;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tara.Adapter.VehicleAdapter;
import com.example.tara.Main.RecyclerViewInterface;
import com.example.tara.Models.Car;
import com.example.tara.Models.Vehicle;
import com.example.tara.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

//displays all the users host cars
public class HostedCars extends Fragment implements RecyclerViewInterface {

    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    ArrayList<Vehicle> list;
    VehicleAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    String uId;
    DataSnapshot dataSnapshot;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hosted_cars, container, false);

        FloatingActionButton addButton = view.findViewById(R.id.addButton);
        String databaseLocation = getString(R.string.databasePath);

        recyclerView = view.findViewById(R.id.vehicleRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        list = new ArrayList<>();
        adapter = new VehicleAdapter(getContext(),list,this);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshVH);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), HostCar.class));
            }
        });

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        databaseReference = FirebaseDatabase.getInstance(databaseLocation).getReference("vehicle");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataSnapshot = snapshot;
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        String checkId = dataSnapshot1.getKey();
                        if(checkId.equals(userId)){
                            Vehicle vehicle = dataSnapshot1.getValue(Vehicle.class);
                            list.add(vehicle);
                        }
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.clear();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @SuppressLint("NotifyDataSetChanged")
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                                        String checkId = dataSnapshot1.getKey();
                                        if(checkId.equals(userId)){
                                            Vehicle vehicle = dataSnapshot1.getValue(Vehicle.class);
                                            list.add(vehicle);
                                        }
                                    }
                                }
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 500);
            }
        });

        return view;
    }


    @Override
    public void onItemClick(int position) {

    }
}
