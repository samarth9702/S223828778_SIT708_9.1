package com.example.lostfound;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    Context context;
    ArrayList type, desc, location, date, phone, name;
    private AdapterView.OnItemClickListener listener;

    public MyAdapter(Context context, ArrayList type, ArrayList desc, ArrayList location, ArrayList date, ArrayList phone, ArrayList name, AdapterView.OnItemClickListener listener) {
        this.context = context;
        this.type = type;
        this.desc = desc;
        this.location = location;
        this.date = date;
        this.phone = phone;
        this.name = name;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.advert_layout,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.layout_type.setText(String.valueOf(type.get(position)));
        holder.layout_desc.setText(String.valueOf(desc.get(position)));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailedAdvert.class);
                intent.putExtra("Name", String.valueOf(name.get(position)));
                intent.putExtra("Date", String.valueOf(date.get(position)));
                intent.putExtra("Desc", String.valueOf(desc.get(position)));
                intent.putExtra("Phone", String.valueOf(phone.get(position)));
                intent.putExtra("Location", String.valueOf(location.get(position)));
                intent.putExtra("Type", String.valueOf(type.get(position)));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return type.size();
    }
}
