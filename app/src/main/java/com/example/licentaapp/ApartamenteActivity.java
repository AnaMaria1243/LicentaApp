package com.example.licentaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.licentaapp.Clase.Apartament;
import com.example.licentaapp.Clase.Asociatie;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ApartamenteActivity extends AppCompatActivity {
    DatabaseReference reference;
    FirebaseUser user;

    RecyclerView recyclerViewApartamente;
    ArrayList<Apartament> listaApartamente;
    RecycleViewApartamenteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartamente);
        setTitle("Apartamente");
        user = FirebaseAuth.getInstance().getCurrentUser();


        Intent intent = getIntent();
        String asociatieNume = intent.getStringExtra("asociatieNume");



        recyclerViewApartamente=findViewById(R.id.recycleViewApartamente);
        listaApartamente=new ArrayList<>();
        recyclerViewApartamente.setLayoutManager(new LinearLayoutManager(this));
        adapter=new RecycleViewApartamenteAdapter(this,listaApartamente);
        recyclerViewApartamente.setAdapter(adapter);

        reference= FirebaseDatabase.getInstance().getReference().child("Administratori").child(user.getUid()).child("Asociatii");
        Query query = reference.orderByChild("numeAsociatie").equalTo(asociatieNume);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot asociatieSnapshot : dataSnapshot.getChildren()) {
                    String asociatieId = asociatieSnapshot.getKey();
                    DatabaseReference apartamenteRef = reference.child(asociatieId).child("Apartamente");
                    apartamenteRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot apartamentSnapshot : dataSnapshot.getChildren()) {
                                Apartament apartament=apartamentSnapshot.getValue(Apartament.class);
                                if (!listaApartamente.contains(apartament)) {
                                    listaApartamente.add(apartament);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                            adapter.notifyDataSetChanged();
                            recyclerViewApartamente.scrollToPosition(listaApartamente.size()-1);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

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