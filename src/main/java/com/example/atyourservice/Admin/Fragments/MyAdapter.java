package com.example.atyourservice.Admin.Fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atyourservice.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<SenderModel> list;

    public MyAdapter(Context context, ArrayList<SenderModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.main_items, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        SenderModel senderModel = list.get(position);

        holder.uname.setText(senderModel.getUsername());
        holder.email.setText(senderModel.getEmail());
        holder.cn.setText(senderModel.getContactNo());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView uname, email, cn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            uname = itemView.findViewById(R.id.NName);
            email = itemView.findViewById(R.id.emails);
            cn = itemView.findViewById(R.id.phoneno);

        }
    }
}
