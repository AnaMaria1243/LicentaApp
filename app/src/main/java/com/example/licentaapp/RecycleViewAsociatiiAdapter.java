package com.example.licentaapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.licentaapp.Clase.Asociatie;

import java.util.ArrayList;

public class RecycleViewAsociatiiAdapter extends RecyclerView.Adapter<RecycleViewAsociatiiAdapter.MyViewHolder> {


    Context context;
    ArrayList<Asociatie> asociatiiList;

    public RecycleViewAsociatiiAdapter(Context context, ArrayList<Asociatie> asociatiiList) {
        this.context = context;
        this.asociatiiList = asociatiiList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Asociatie asociatie=asociatiiList.get(position);
        holder.numeAsociatie.setText(asociatie.getNumeAsociatie());
        holder.adresa.setText(asociatie.getAdresaAsociatie());
        holder.nrEtaje.setText(asociatie.getNrEtaje());
        holder.nrAp.setText(asociatie.getTotalAp());
        holder.suprafata.setText(asociatie.getSuprafataComuna());
        holder.spatiuVerde.setText(asociatie.getSpatiuVerde().toString());
        holder.presedinte.setText(asociatie.getPresedintele());
    }

    @Override
    public int getItemCount() {
        return asociatiiList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView numeAsociatie,adresa,nrEtaje,nrAp,suprafata,presedinte,spatiuVerde;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            numeAsociatie=itemView.findViewById(R.id.tvNumeAsociatie);
            adresa=itemView.findViewById(R.id.tvAdresaAsociatie);
            nrEtaje=itemView.findViewById(R.id.tvNrEtaje);
            nrAp=itemView.findViewById(R.id.tvNrAp);
            suprafata=itemView.findViewById(R.id.tvTotalSuprafata);
            spatiuVerde=itemView.findViewById(R.id.tvSpatiuVerde);
            presedinte=itemView.findViewById(R.id.tvPresedinte);

        }
    }

}
