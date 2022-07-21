package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    Context context;
    Activity activity;
    List<Model> list;

    public Adapter(Context context, Activity activity, List<Model> list) {
        this.context = context;
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_layout,parent,false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull Adapter.MyViewHolder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.phone.setText(list.get(position).getPhone());
        holder.desc.setText(list.get(position).getDesc());
        holder.date.setText(list.get(position).getDate());
        holder.location.setText(list.get(position).getLocation());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Update.class);

                intent.putExtra("title", list.get(position).getTitle());
                intent.putExtra("phone", list.get(position).getPhone());
                intent.putExtra("desc", list.get(position).getDesc());
                intent.putExtra("date", list.get(position).getDate());
                intent.putExtra("location", list.get(position).getLocation());
                intent.putExtra("id", list.get(position).getId());
                activity.startActivityForResult(intent,1);
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, phone, desc, date, location;
        RelativeLayout layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title1);
            phone = itemView.findViewById(R.id.phone);
            desc = itemView.findViewById(R.id.desc);
            date = itemView.findViewById(R.id.date);
            location = itemView.findViewById(R.id.location);
            layout = itemView.findViewById(R.id.advert_layout);
        }
    }
}
