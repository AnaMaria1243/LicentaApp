package com.example.licentaapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.licentaapp.Clase.Asociatie;
import com.example.licentaapp.Clase.Dosar;
import com.example.licentaapp.Clase.Singleton;
import com.example.licentaapp.Fragmente.PdfFragment;

import java.util.ArrayList;

public class RecycleViewDosarAdminAdapter extends RecyclerView.Adapter<RecycleViewDosarAdminAdapter.MyViewHolder> {

    Context context;
    ArrayList<Dosar> dosarList;

    public RecycleViewDosarAdminAdapter(Context context, ArrayList<Dosar> dosarList) {
        this.context = context;
        this.dosarList = dosarList;
    }

    @NonNull
    @Override
    public RecycleViewDosarAdminAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.itemdosarpdf,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewDosarAdminAdapter.MyViewHolder holder, int position) {
        Dosar dosar=dosarList.get(position);
        holder.titlu.setText(dosar.getTitluDosar());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getBindingAdapterPosition();
                Dosar dosar1= dosarList.get(position);
                Intent intent=new Intent(context, DocUploadActivity.class);
                intent.putExtra("dosarNume", dosar1.getTitluDosar());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);





            }
        });
    }

    @Override
    public int getItemCount() {
        return dosarList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView titlu;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titlu=itemView.findViewById(R.id.tv_titluDosar);
        }

    }
}
