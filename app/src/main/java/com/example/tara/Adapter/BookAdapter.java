package com.example.tara.Adapter;

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
import com.example.tara.Models.Booking;
import com.example.tara.R;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MyViewHolder>{
    private final RecyclerViewInterface recyclerViewInterface;
    private final int lastPosition = -1;
    Context context;

    ArrayList<Booking> list;

    public BookAdapter(Context context, ArrayList<Booking> list, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.list = list;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public BookAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.booked_item,parent,false);
        return new BookAdapter.MyViewHolder(v,recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.MyViewHolder holder, int position) {
        Booking booking = list.get(position);
        if(booking!=null){
            holder.bindBooking(booking);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{
        ImageView img;
        TextView bmy, location , price,host,bookedDate;

        void bindBooking(Booking booking) {
            bmy.setText(booking.getBmy());
            location.setText(booking.getLocation());
            price.setText(booking.getPriceRate());
            host.setText(booking.getName());
            bookedDate.setText(booking.getBookDate());
            Glide.with(img.getContext()).load(booking.getExterior1Url()).into(img);
        }

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            img = (ImageView)itemView.findViewById(R.id.ivbCarImage);
            bmy = (TextView)itemView.findViewById(R.id.tvbBmy);
            location = (TextView)itemView.findViewById(R.id.tvbLocation);
            price = (TextView)itemView.findViewById(R.id.tvbPrice);
            host = (TextView)itemView.findViewById(R.id.tvbHost);
            bookedDate = (TextView)itemView.findViewById(R.id.tvbookDate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface != null){
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

}
