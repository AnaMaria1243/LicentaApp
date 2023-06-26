package com.example.licentaapp.Fragmente;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.licentaapp.Clase.Apartament;
import com.example.licentaapp.Clase.Dosar;
import com.example.licentaapp.PDFAdapter;
import com.example.licentaapp.R;
import com.example.licentaapp.RecycleViewApartamenteAdapter;
import com.example.licentaapp.RecycleViewDosarPdfAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class AvizierFragment extends Fragment {

    DatabaseReference reference;
    FirebaseUser user;

    RecyclerView recyclerViewDosar;
    ArrayList<Dosar> listaDosare;
    RecycleViewDosarPdfAdapter adapter;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_avizier, container, false);
        recyclerViewDosar=view.findViewById(R.id.recycleViewDosar);
        listaDosare=new ArrayList<>();
        recyclerViewDosar.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter=new RecycleViewDosarPdfAdapter(getContext(),listaDosare);
        recyclerViewDosar.setAdapter(adapter);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
        reference.child("documente").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaDosare.clear();
                for (DataSnapshot dosarSnapshot : snapshot.getChildren()) {
                    Dosar dosar = dosarSnapshot.getValue(Dosar.class);
                    if (!listaDosare.contains(dosar)) {
                        String titluDosar = dosarSnapshot.child("titlu").getValue(String.class);
                        dosar.setTitluDosar(titluDosar);
                        listaDosare.add(dosar);
                        adapter.notifyDataSetChanged();
                    }
                }
                adapter.notifyDataSetChanged();
                recyclerViewDosar.scrollToPosition(listaDosare.size()-1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }
}