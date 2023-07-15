package com.example.licentaapp.Fragmente;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.licentaapp.Clase.Apartament;
import com.example.licentaapp.PDFAdapter;
import com.example.licentaapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class PdfFragment extends Fragment {
    ListView listViewPdf;
    DatabaseReference reference;
    FirebaseUser user;
    ArrayList<String> documentsList=new ArrayList<>();
    ArrayAdapter<String> adapter;
    String message;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pdf, container, false);
        listViewPdf = view.findViewById(R.id.listViewPdf);
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, documentsList);
        PDFAdapter adapter = new PDFAdapter(getContext(), R.layout.adapter_pdf, documentsList);
        listViewPdf.setAdapter(adapter);

        Bundle bundle = getArguments();
        String dosarNume = null;
        if (bundle != null) {
            dosarNume = bundle.getString("dosarNume");
        }
        System.out.println("nume"+dosarNume);

        try{
            user = FirebaseAuth.getInstance().getCurrentUser();
            reference = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("documente");
            Query query = reference.orderByChild("titlu").equalTo(dosarNume);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dosarSnapshot : dataSnapshot.getChildren()) {
                    String dosarId = dosarSnapshot.getKey();
                    DatabaseReference apartamenteRef = reference.child(dosarId).child("fisiere");
                    apartamenteRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                message = snapshot.child("url").getValue(String.class);
                                String numePdf = snapshot.child("numePdf").getValue(String.class);
                                if(!documentsList.contains(numePdf)){
                                documentsList.add(numePdf);
                                adapter.notifyDataSetChanged();
                                    System.out.println(message);}
                            }
                            adapter.notifyDataSetChanged();;
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
        } catch (Exception e) {
            e.printStackTrace();
        }

//        listViewPdf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                try{
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(message));
//                startActivity(intent);
//                } catch (ActivityNotFoundException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//        });


        listViewPdf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (message != null && !message.isEmpty()) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(message));
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getContext(), "Invalid URL", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }
}