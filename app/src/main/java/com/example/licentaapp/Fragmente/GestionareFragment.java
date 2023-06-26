package com.example.licentaapp.Fragmente;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.licentaapp.Clase.Asociatie;
import com.example.licentaapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;


public class GestionareFragment extends Fragment {

    DatabaseReference reference;
    FirebaseUser user;

    FloatingActionButton fab;
    RecyclerView recyclerView;
    //RecycleViewGestionareAdapter adapter;
    ArrayList<Asociatie> asociatiiList;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_gestionare, container, false);
        recyclerView=view.findViewById(R.id.recycleViewAdminGestionare);
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("Administratori").child(user.getUid());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        asociatiiList=new ArrayList<>();
       // adapter=new RecycleViewGestionareAdapter(getContext(),asociatiiList);
       // recyclerView.setAdapter(adapter);
        reference.child("Asociatii").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                asociatiiList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Asociatie asociatie=dataSnapshot.getValue(Asociatie.class);
                    if (!asociatiiList.contains(asociatie)) {
                        asociatiiList.add(asociatie);
                     //   adapter.notifyDataSetChanged();
                    }
                }
              //  adapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(asociatiiList.size()-1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }



    private void generatePdf() {
        // Creează un document nou
        Document document = new Document();

        try {
            // Specifică calea și numele fișierului PDF pe care dorești să-l generezi
            String filePath = Environment.getExternalStorageDirectory() + File.separator + "my_file.pdf";

            // Creați un obiect PdfWriter pentru a scrie în fișierul PDF
            PdfWriter.getInstance(document, new FileOutputStream(filePath));

            // Deschide documentul pentru a putea scrie în el
            document.open();

            // Obține datele din Firebase Realtime Database și adaugă-le la documentul PDF utilizând obiectul Paragraph
            FirebaseDatabase.getInstance().getReference().child("path_to_your_data_in_database")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    String data = dataSnapshot.getValue(String.class);
                                    try {
                                        document.add(new Paragraph(data));
                                    } catch (DocumentException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } else {
                                Toast.makeText(getActivity(), "Nu există date în baza de date.", Toast.LENGTH_SHORT).show();
                            }

                            // Închide documentul după ce ai terminat de adăugat conținutul
                            document.close();
                            Toast.makeText(getActivity(), "PDF generat cu succes", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getActivity(), "Eroare la citirea datelor din baza de date",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Eroare la generarea PDF-ului", Toast.LENGTH_SHORT).show();
        }
    }

}