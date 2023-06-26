package com.example.licentaapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.licentaapp.Clase.Apartament;
import com.example.licentaapp.Clase.Asociatie;

import java.util.ArrayList;

public class RecycleViewApartamenteAdapter extends RecyclerView.Adapter<RecycleViewApartamenteAdapter.MyViewHolder> {
    Context context;
    ArrayList<Apartament> listaApartamente;

    public RecycleViewApartamenteAdapter(Context context, ArrayList<Apartament> listaApartamente) {
        this.context = context;
        this.listaApartamente = listaApartamente;
    }

    @NonNull
    @Override
    public RecycleViewApartamenteAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.itemapartament,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewApartamenteAdapter.MyViewHolder holder, int position) {
        Apartament apartament=listaApartamente.get(position);
        //holder.imgUsaAp.set
        holder.nrApartament.setText(apartament.getNrApartament());
        holder.etajApartament.setText(apartament.getEtajApartament());
        holder.numeProprietarApartament.setText(apartament.getNumeProprietar());
        holder.suprafataApartament.setText(apartament.getSuprafataApartament());
        holder.contactApartament.setText(apartament.getContact());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getBindingAdapterPosition();
                Intent intent=new Intent(context,SituatieApartamenteActivity.class);
                Apartament ap=listaApartamente.get(position);
                intent.putExtra("nrAp", ap.getNrApartament());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return listaApartamente.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nrApartament,etajApartament,numeProprietarApartament,suprafataApartament,contactApartament;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nrApartament=itemView.findViewById(R.id.tv_nrApInput);
            etajApartament=itemView.findViewById(R.id.tv_nrEtajInputApartament);
            numeProprietarApartament=itemView.findViewById(R.id.tv_proprietarInputApartament);
            suprafataApartament= itemView.findViewById(R.id.tv_suprafataInputApartament);
            contactApartament=itemView.findViewById(R.id.contactApartamentInput);
        }
    }


}
