package com.example.licentaapp;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.licentaapp.Clase.Asociatie;
import com.example.licentaapp.Clase.Singleton;
import com.example.licentaapp.Clase.SpatiuVerde;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecycleViewGestionareAsociatiiAdapter extends RecyclerView.Adapter<RecycleViewGestionareAsociatiiAdapter.MyViewHolder> {


    Context context;
    ArrayList<Asociatie> asociatiiList;
    DatabaseReference reference;
    FirebaseUser user;


    public RecycleViewGestionareAsociatiiAdapter(Context context, ArrayList<Asociatie> asociatiiList) {
        this.context = context;
        this.asociatiiList = asociatiiList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.itemasociatiisimple,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Asociatie asociatie=asociatiiList.get(position);
        holder.numeAsociatie.setText(asociatie.getNumeAsociatie());
        holder.adresa.setText(asociatie.getAdresaAsociatie());
//        holder.nrEtaje.setText(asociatie.getNrEtaje());
//        holder.nrAp.setText(asociatie.getTotalAp());
//        holder.suprafata.setText(asociatie.getSuprafataComuna());
//        holder.spatiuVerde.setText(asociatie.getSpatiuVerde().toString());
//        holder.presedinte.setText(asociatie.getPresedintele());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getBindingAdapterPosition();
                Asociatie asociatie = asociatiiList.get(position);
                Intent intent=new Intent(context,DocumenteGestionareActivity.class);
                intent.putExtra("asociatieNumeGestionare", asociatie.getNumeAsociatie());
                context.startActivity(intent);

                Singleton.getInstance().setAsociatieNume(asociatie.getNumeAsociatie());


            }
        });



    }


    @Override
    public int getItemCount() {
        return asociatiiList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView numeAsociatie,adresa;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            numeAsociatie=itemView.findViewById(R.id.tvNumeAsociatieGestiune);
            adresa=itemView.findViewById(R.id.tvAdresaAsociatieGestiune);


        }
    }


}
