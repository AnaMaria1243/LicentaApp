package com.example.licentaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.licentaapp.Clase.Apartament;
import com.example.licentaapp.Clase.Asociatie;
import com.example.licentaapp.Clase.SablonIntretinere;
import com.example.licentaapp.Clase.Singleton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

public class SituatieApartamenteActivity extends AppCompatActivity {

    RecyclerView recyclerViewIntretinereAp;
    ArrayList<SablonIntretinere> sablonList;
    RecycleViewIntretinereAdapter adapter;

    FloatingActionButton fabIntretinere;

    DatabaseReference reference;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_situatie_apartamente);

        setTitle("Situație întreținere");

        Intent intent = getIntent();
        String nrAp = intent.getStringExtra("nrAp");
        System.out.println("NrAp: " + nrAp);

        String asociatieNume = Singleton.getInstance().getAsociatieNume();
        System.out.println("Asociatie Nume: " + asociatieNume);

        fabIntretinere=findViewById(R.id.floatingActionButtonSituatieApartamente);
        recyclerViewIntretinereAp=findViewById(R.id.recycleViewSituatieApartamente);
        sablonList=new ArrayList<>();
        recyclerViewIntretinereAp.setLayoutManager(new LinearLayoutManager(this));
        adapter=new RecycleViewIntretinereAdapter(this,sablonList,nrAp);
        recyclerViewIntretinereAp.setAdapter(adapter);
        user= FirebaseAuth.getInstance().getCurrentUser();

        reference= FirebaseDatabase.getInstance().getReference().child("Administratori").child(user.getUid()).child("Asociatii");

        fabIntretinere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog=new Dialog(SituatieApartamenteActivity.this);
                dialog.setContentView(R.layout.builderaddintretinere);
                dialog.show();


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

                addBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String text1=titluDoc.getText().toString();
                        String text2=servAdmin.getText().toString();
                        String text3=paza.getText().toString();
                        String text4=curatenia.getText().toString();
                        String text5=fondReparatii.getText().toString();
                        String text6=fondChNeprev.getText().toString();
                        String text7=salubrizare.getText().toString();
                        String text8=lumina.getText().toString();
                        String text9=apa.getText().toString();



                        HashMap<Object,String> hashMap6=new HashMap<>();
                        hashMap6.put("titlu",text1);
                        hashMap6.put("plataServiciuAdministrator",text2);
                        hashMap6.put("plataPaza",text3);
                        hashMap6.put("plataCuratenie",text4);
                        hashMap6.put("fondReparatii",text5);
                        hashMap6.put("fondCheltuieliNeprevazute",text6);
                        hashMap6.put("plataSlubrizare",text7);
                        hashMap6.put("plataLuminaComuna",text8);
                        hashMap6.put("plataApaIndividual",text9);
                        Log.d("SituatieApartamente", "Text1: " + text1);
                        Log.d("SituatieApartamente", "Text2: " + text2);


                        Query query = reference.orderByChild("numeAsociatie").equalTo(asociatieNume);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot asociatieSnapshot : dataSnapshot.getChildren()) {
                                    String asociatieId = asociatieSnapshot.getKey();
                                    String intretinereId = reference.child(asociatieId).child("Apartamente").child(nrAp).child("Intretineri").push().getKey();
                                    reference.child(asociatieId).child("Apartamente").child(nrAp).child("Intretineri").child(intretinereId).setValue(hashMap6);


                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        dialog.dismiss();
                    }
                });

                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }

        });


        Query query2 = reference.orderByChild("numeAsociatie").equalTo(asociatieNume);
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot asociatieSnapshot : dataSnapshot.getChildren()) {
                    String asociatieId = asociatieSnapshot.getKey();
//                    String intretinereId = reference.child(asociatieId).child("Apartamente").child(nrAp).child("Intretineri").push().getKey();
                    DatabaseReference r=reference.child(asociatieId).child("Apartamente").child(nrAp).child("Intretineri");
                    r.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            sablonList.clear();
                            for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                                SablonIntretinere sablon=dataSnapshot.getValue(SablonIntretinere.class);
                                if (!sablonList.contains(sablon)) {
                                    sablonList.add(sablon);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                            adapter.notifyDataSetChanged();
                            recyclerViewIntretinereAp.scrollToPosition(sablonList.size()-1);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}