package com.example.tara.Host;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tara.Main.RecyclerViewInterface;

import com.example.tara.R;

import java.util.ArrayList;

public class CarHostedAdapter extends RecyclerView.Adapter<CarHostedAdapter.MyViewHolder>{
    private final RecyclerViewInterface recyclerViewInterface;
    private final int lastPosition = -1;
    Context context;

    ArrayList<CarHosted> list;

    public CarHostedAdapter(Context context, ArrayList<CarHosted> list, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.list = list;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.hosted_item,parent,false);
        return new MyViewHolder(v,recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CarHosted carHosted = list.get(position);
        holder.bmy.setText(carHosted.getBmy());
        holder.plateNumber.setText(carHosted.getPlateNumber());
        Glide.with(holder.carImage.getContext()).load(carHosted.getCarUrl()).into(holder.carImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{
        ImageView carImage;
        TextView bmy, plateNumber;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

                carImage = itemView.findViewById(R.id.ivCarImage);
                bmy = itemView.findViewById(R.id.tvBrandModelYear);
                plateNumber = itemView.findViewById(R.id.tvPlateNumber);

        }
    }

}
