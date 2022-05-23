package com.example.tara.Explore;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.tara.Main.RecyclerViewInterface;
import com.example.tara.Models.Car;
import com.example.tara.Adapter.CarAdapter;
import com.example.tara.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import android.os.Handler;

public class ExploreFragmentMenu extends Fragment implements RecyclerViewInterface {

    RecyclerView recyclerView;
    DatabaseReference database;
    CarAdapter myAdapter;
    ArrayList<Car> list, filteredList;
    SwipeRefreshLayout swipeRefreshLayout;
    String carId, carHostId,search;
    DataSnapshot dataSnapshot;
    SearchView searchView;
    Boolean isFiltered;
    Query query;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);

        String databaseLocation = getString(R.string.databasePath);
        isFiltered = false;
        recyclerView = view.findViewById(R.id.carListRV);
        query = FirebaseDatabase.getInstance(databaseLocation).getReference("vehicle");
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        list = new ArrayList<>();
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        searchView = view.findViewById(R.id.searchView);
        searchView.setFocusable(false);
        myAdapter = new CarAdapter(getContext(),list,this);
        recyclerView.setAdapter(myAdapter);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                search = newText;
                filterList(search);
                isFiltered = true;
                return true;
            }
        });

        query.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataSnapshot = snapshot;
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        Car car = dataSnapshot1.getValue(Car.class);
                        list.add(car);
                    }
                }
                myAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.clear();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        query.addValueEventListener(new ValueEventListener() {
                            @SuppressLint("NotifyDataSetChanged")
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                                        Car car = dataSnapshot1.getValue(Car.class);
                                        list.add(car);
                                    }
                                }
                                myAdapter.notifyDataSetChanged();
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) { }
                        });
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 500);
            }
        });
        return view;
    }

    private void filterList(String newText) {
        filteredList = new ArrayList<>();

        query.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                filteredList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        String city = dataSnapshot1.child("city").getValue().toString();
                        String province = dataSnapshot1.child("province").getValue().toString();
                        String street = dataSnapshot1.child("address1").getValue().toString();
                        String barangay = dataSnapshot1.child("address2").getValue().toString();

                        if(city.toLowerCase().contains(newText.toLowerCase())){
                            Car car = dataSnapshot1.getValue(Car.class);
                            filteredList.add(car);
                        }
                        else if(province.toLowerCase().contains(newText.toLowerCase())){
                            Car car = dataSnapshot1.getValue(Car.class);
                            filteredList.add(car);
                        }
                        else if(street.toLowerCase().contains(newText.toLowerCase())){
                            Car car = dataSnapshot1.getValue(Car.class);
                            filteredList.add(car);
                        }
                        else if(barangay.toLowerCase().contains(newText.toLowerCase())){
                            Car car = dataSnapshot1.getValue(Car.class);
                            filteredList.add(car);
                        }
                    }
                    myAdapter.setFilteredList(filteredList);
                    myAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        int index  = 0;
        for(DataSnapshot childSnapshot : dataSnapshot.getChildren()){
            if(index == position){
                DatabaseReference currentReference = childSnapshot.getRef();
                carId = currentReference.getKey();
            }
            for(DataSnapshot childSnapshot2 : childSnapshot.getChildren()){
                if(index == position){
                    DatabaseReference ref2 = childSnapshot2.getRef();
                    carHostId = ref2.getKey();
                }
            }
            index++;
        }
        Intent intent = new Intent(getContext(), CarDetails.class);
        intent.putExtra("carId", carId);
        intent.putExtra("carHostId", carHostId);
        startActivity(intent);
    }
}
