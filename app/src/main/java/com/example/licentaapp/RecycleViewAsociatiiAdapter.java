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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.licentaapp.Clase.Asociatie;
import com.example.licentaapp.Clase.SpatiuVerde;
import com.example.licentaapp.Fragmente.AsociatiiFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
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

public class RecycleViewAsociatiiAdapter extends RecyclerView.Adapter<RecycleViewAsociatiiAdapter.MyViewHolder> {


    Context context;
    ArrayList<Asociatie> asociatiiList;
    DatabaseReference reference;
    FirebaseUser user;

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



        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.updateasociatie);

                EditText et_numeAsociatieUpdate=dialog.findViewById(R.id.editText_numeAsociatieUpdate);
                EditText et_adresaAsociatieUpdate=dialog.findViewById(R.id.editText_adresaAsociatieUpdate);
                EditText et_nrEtAsociatieUpdate=dialog.findViewById(R.id.editText_nrEtajeAsociatieUpdate);
                EditText et_nrApAsociatieUpdate=dialog.findViewById(R.id.editText_nrApAsociatieUpdate);
                EditText et_suprafataAsociatieUpdate=dialog.findViewById(R.id.editText_suprafataAsociatieUpdate);
                RadioGroup rgSpatiuVerdeUpdate=dialog.findViewById(R.id.rgSpatiuVerdeUpdate);
                EditText et_presedinteAsociatieUpdate=dialog.findViewById(R.id.editText_presedinteAsociatieUpdate);

                int clickedPosition = holder.getBindingAdapterPosition();
                Asociatie clickedAsociatie = asociatiiList.get(clickedPosition);

                et_numeAsociatieUpdate.setText(clickedAsociatie.getNumeAsociatie());
                et_adresaAsociatieUpdate.setText(clickedAsociatie.getAdresaAsociatie());
                et_nrEtAsociatieUpdate.setText(clickedAsociatie.getNrEtaje());
                et_nrApAsociatieUpdate.setText(clickedAsociatie.getTotalAp());
                et_suprafataAsociatieUpdate.setText(clickedAsociatie.getSuprafataComuna());
                SpatiuVerde spatiuVerde = clickedAsociatie.getSpatiuVerde();
                if (spatiuVerde == SpatiuVerde.NU) {
                    rgSpatiuVerdeUpdate.check(R.id.spatiuverdenuUpdate);
                } else {
                    rgSpatiuVerdeUpdate.check(R.id.spatiuverdedaUpdate);
                }

                et_presedinteAsociatieUpdate.setText(clickedAsociatie.getPresedintele());

                Button xBtn=dialog.findViewById(R.id.cancelAsociatieBtnUpdate);
                Button updateAsociatieBtn=dialog.findViewById(R.id.actualizeazaAsociatieBtn);

                xBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                updateAsociatieBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String text1=et_numeAsociatieUpdate.getText().toString();
                        String text2=et_adresaAsociatieUpdate.getText().toString();
                        String text3=et_nrEtAsociatieUpdate.getText().toString();
                        String text4=et_nrApAsociatieUpdate.getText().toString();
                        String text5=et_presedinteAsociatieUpdate.getText().toString();
                        String text6=et_suprafataAsociatieUpdate.getText().toString();
                        SpatiuVerde spatiuVerde = SpatiuVerde.DA;
                        if(rgSpatiuVerdeUpdate.getCheckedRadioButtonId() ==R.id.spatiuverdenu){
                            spatiuVerde = SpatiuVerde.NU;
                        }

                        Asociatie asociatieUpdate=new Asociatie(text1,text2,text3,text4,text6,spatiuVerde,text5);
                        asociatiiList.set(clickedPosition,asociatieUpdate);
                        notifyItemChanged(clickedPosition);

                        Map<String,Object> hashMap3=new HashMap<>();
                        hashMap3.put("numeAsociatie",text1);
                        hashMap3.put("adresaAsociatie",text2);
                        hashMap3.put("nrEtaje",text3);
                        hashMap3.put("totalAp",text4);
                        hashMap3.put("suprafataComuna",text6);
                        hashMap3.put("presedintele",text5);
                        hashMap3.put("spatiuVerde",spatiuVerde.toString());

                        dialog.dismiss();
                        user = FirebaseAuth.getInstance().getCurrentUser();
                       // reference = FirebaseDatabase.getInstance().getReference().child("Administratori").child(user.getUid()).child("Asociatii");
                       // reference.child("-NX7iMeV9-_uADEWk8ub").updateChildren(hashMap3);

                        reference = FirebaseDatabase.getInstance().getReference().child("Administratori").child(user.getUid()).child("Asociatii");

                        Query query = reference.orderByChild("adresaAsociatie").equalTo(text2); // Căutare după adresa

                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    String asociatieId = snapshot.getKey();
                                    reference.child(asociatieId).updateChildren(hashMap3);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });


                    }
                });

                dialog.show();

                




            }

           



        });

        holder.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setCancelable(false);
                builder.setMessage("Ești sigur ca vrei să ștergi?");
                builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = holder.getBindingAdapterPosition();
                        deleteItem(position);
                    }
                });
                builder.setNegativeButton("Nu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert=builder.create();
                alert.show();


            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getBindingAdapterPosition();
                Intent intent=new Intent(context,ApartamenteActivity.class);
                Asociatie asociatie = asociatiiList.get(position);
                intent.putExtra("asociatieNume", asociatie.getNumeAsociatie());
                context.startActivity(intent);

            }
        });


        
    }



    private void deleteItem(int position) {
        // Obțineți elementul din lista de date asociată cu RecyclerView la poziția dată
        Asociatie asociatie = asociatiiList.get(position);

        // Ștergeți elementul din lista de date asociată cu RecyclerView
        asociatiiList.remove(position);

        // Notificați adaptorul despre ștergerea elementului pentru a actualiza RecyclerView
        notifyItemRemoved(position);

        // Ștergeți elementul din baza de date Firebase utilizând criterii de identificare

        // Exemplu: Ștergeți asociatia pe baza numelui și adresei
        deleteAsociatieFromFirebase(asociatie.getNumeAsociatie(), asociatie.getAdresaAsociatie());
    }

    private void deleteAsociatieFromFirebase(String numeAsociatie, String adresaAsociatie) {
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("Administratori").child(user.getUid()).child("Asociatii");

        Query query = reference.orderByChild("numeAsociatie").equalTo(numeAsociatie).limitToFirst(1);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String asociatieId = snapshot.getKey();

                    // Verificați dacă adresa asociatiei corespunde și ștergeți-o din baza de date
                    if (snapshot.child("adresaAsociatie").getValue(String.class).equals(adresaAsociatie)) {
                        reference.child(asociatieId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // Ștergerea din baza de date Firebase s-a efectuat cu succes
                                } else {

                                }
                            }
                        });
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Tratați cazul în care interogarea a fost anulată sau a apărut o eroare de comunicare cu baza de date
            }
        });


    }

    @Override
    public int getItemCount() {
        return asociatiiList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView numeAsociatie,adresa,nrEtaje,nrAp,suprafata,presedinte,spatiuVerde;
        Button editBtn,removeBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            numeAsociatie=itemView.findViewById(R.id.tvNumeAsociatie);
            adresa=itemView.findViewById(R.id.tvAdresaAsociatie);
            nrEtaje=itemView.findViewById(R.id.tvNrEtaje);
            nrAp=itemView.findViewById(R.id.tvNrAp);
            suprafata=itemView.findViewById(R.id.tvTotalSuprafata);
            spatiuVerde=itemView.findViewById(R.id.tvSpatiuVerde);
            presedinte=itemView.findViewById(R.id.tvPresedinte);
            editBtn=itemView.findViewById(R.id.editBtnAsociatii);
            removeBtn=itemView.findViewById(R.id.removeBtnAsociatii);




        }
    }

}
