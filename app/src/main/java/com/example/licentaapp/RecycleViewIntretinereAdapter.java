package com.example.licentaapp;

import android.app.Dialog;
import android.content.Context;
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
import com.example.licentaapp.Clase.SablonIntretinere;
import com.example.licentaapp.Clase.Singleton;
import com.example.licentaapp.Clase.SpatiuVerde;

import java.util.ArrayList;

public class RecycleViewIntretinereAdapter extends RecyclerView.Adapter<RecycleViewIntretinereAdapter.MyViewHolder> {
    Context context;
    ArrayList<SablonIntretinere> intretinereList;
    private String nrAp;

    public RecycleViewIntretinereAdapter(Context context, ArrayList<SablonIntretinere> intretinereList, String nrAp) {
        this.context = context;
        this.intretinereList = intretinereList;
        this.nrAp = nrAp;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.itemsablonintretinere,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewIntretinereAdapter.MyViewHolder holder, int position) {
        SablonIntretinere sablonIntretinere=intretinereList.get(position);
        holder.nrApTv.setText(nrAp);
        holder.titlu.setText(sablonIntretinere.getTitlu());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getBindingAdapterPosition();
                SablonIntretinere sablon =intretinereList.get(position);

                Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.builderaddintretinere);

                EditText titluDoc = dialog.findViewById(R.id.ed_titluDialog);
                EditText servAdmin = dialog.findViewById(R.id.ed_serviciuAdminDialog);
                EditText paza = dialog.findViewById(R.id.ed_pazaDialog);
                EditText curatenia = dialog.findViewById(R.id.ed_curateniaDialog);
                EditText fondReparatii = dialog.findViewById(R.id.ed_fondReparatiiDialog);
                EditText fondChNeprev = dialog.findViewById(R.id.ed_fondCheltuieliNeprevazuteDialog);
                EditText salubrizare = dialog.findViewById(R.id.ed_salubrizareDialog);
                EditText lumina = dialog.findViewById(R.id.ed_luminaComunaDialog);
                EditText apa = dialog.findViewById(R.id.ed_apaDialog);
                Button addBtn=dialog.findViewById(R.id.addIntretinereBtnDialog);
                Button cancelBtn=dialog.findViewById(R.id.cancelAddIntretinereBtnDialog);

                int clickedPosition = holder.getBindingAdapterPosition();
                SablonIntretinere clickedIntretinere = intretinereList.get(clickedPosition);

                titluDoc.setText(clickedIntretinere.getTitlu());
                servAdmin.setText(clickedIntretinere.getPlataServiciuAdministrator());
                paza.setText(clickedIntretinere.getPlataPaza());
                curatenia.setText(clickedIntretinere.getPlataCuratenie());
                fondReparatii.setText(clickedIntretinere.getFondReparatii());
                fondChNeprev.setText(clickedIntretinere.getFondCheltuieliNeprevazute());
                salubrizare.setText(clickedIntretinere.getPlataSlubrizare());
                lumina.setText(clickedIntretinere.getPlataLuminaComuna());
                apa.setText(clickedIntretinere.getPlataApaIndividual());
                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();






            }
        });


    }

    @Override
    public int getItemCount() {
        return intretinereList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nrApTv,titlu;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nrApTv=itemView.findViewById(R.id.tv_nrApartamentDocInput);
            titlu=itemView.findViewById(R.id.tv_titluDosar);
        }
    }
}
