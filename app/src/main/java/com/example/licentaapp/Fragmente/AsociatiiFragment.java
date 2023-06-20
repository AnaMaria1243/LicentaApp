package com.example.licentaapp.Fragmente;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.licentaapp.AddAsociatieActivity;
import com.example.licentaapp.Clase.Asociatie;
import com.example.licentaapp.R;
import com.example.licentaapp.RecycleViewAsociatiiAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class AsociatiiFragment extends Fragment {

    DatabaseReference reference;
    FirebaseUser user;

    FloatingActionButton fab;
    RecyclerView recyclerView;
    RecycleViewAsociatiiAdapter adapter;
    ArrayList<Asociatie> asociatiiList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_asociatii, container, false);
        fab=view.findViewById(R.id.floatingActionButton);
        recyclerView=view.findViewById(R.id.recycleViewAdmin);

        fab.setOnClickListener(openAddActivityEvent());
//        listView=view.findViewById(R.id.recycleViewAdmin);
//
//        adapter=new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,asociatiiList);
//        listView.setAdapter(adapter);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("Administratori").child(user.getUid());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        asociatiiList=new ArrayList<>();
        adapter=new RecycleViewAsociatiiAdapter(getContext(),asociatiiList);
        recyclerView.setAdapter(adapter);
        reference.child("Asociatii").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                asociatiiList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Asociatie asociatie=dataSnapshot.getValue(Asociatie.class);
                    if (!asociatiiList.contains(asociatie)) {
                        asociatiiList.add(asociatie);
                        adapter.notifyDataSetChanged();
                    }
                }
                adapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(asociatiiList.size()-1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }

    private View.OnClickListener openAddActivityEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddAsociatieActivity.class);
                startActivity(intent);
            }
        };
    }
}