package com.example.licentaapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.licentaapp.Clase.Dosar;
import com.example.licentaapp.Clase.PDF;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class DocumenteGestionareActivity extends AppCompatActivity {

     private static final int PICK_PDF_REQUEST = 1;
     Uri selectedPdfUri;
     DatabaseReference databaseRef;
     StorageReference storageRef;
     FloatingActionButton fabGestiune;

    RecyclerView recyclerViewDosarG;
    ArrayList<Dosar> listaDosareG;
    RecycleViewDosarAdminAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documente_gestionare);
        setTitle("Dosare");
        Intent intent1 = getIntent();
        String asociatieNumeGestionare = intent1.getStringExtra("asociatieNumeGestionare");

        // Obțineți o referință către baza de date Realtime Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseRef = database.getReference("Upload");

        // Obțineți o referință către Firebase Storage
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        recyclerViewDosarG=findViewById(R.id.recycleViewGestionare);
        listaDosareG=new ArrayList<>();
        recyclerViewDosarG.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter=new RecycleViewDosarAdminAdapter(getApplicationContext(),listaDosareG);
        recyclerViewDosarG.setAdapter(adapter);
        //        storageReference= FirebaseStorage.getInstance().getReference();
//        databaseReference= FirebaseDatabase.getInstance().getReference("Upload");

        fabGestiune=findViewById(R.id.floatingActionButtonAddDosar);
        fabGestiune.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DocumenteGestionareActivity.this);
                View dialogView = getLayoutInflater().inflate(R.layout.dosarnougestiune, null);
                builder.setView(dialogView);

                EditText et_denumire = dialogView.findViewById(R.id.denumireDosar);

                builder.setPositiveButton("Adăugare", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String value = et_denumire.getText().toString();
                        String idDosar = databaseRef.push().getKey();
                        databaseRef.child("Dosare").child(idDosar).child("titlu").setValue(value);
                        databaseRef.child("Dosare").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                listaDosareG.clear();
                                for (DataSnapshot dosarSnapshot : snapshot.getChildren()) {
                                    Dosar dosar = dosarSnapshot.getValue(Dosar.class);
                                    if (!listaDosareG.contains(dosar)) {
                                        String titluDosar = dosarSnapshot.child("titlu").getValue(String.class);
                                        dosar.setTitluDosar(titluDosar);
                                        listaDosareG.add(dosar);
                                        adapter.notifyDataSetChanged();
                                    }
                                }
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        dialog.dismiss();

                    }
                });

                builder.setNegativeButton("Anulează", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        databaseRef.child("Dosare").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaDosareG.clear();
                for (DataSnapshot dosarSnapshot : snapshot.getChildren()) {
                    Dosar dosar = dosarSnapshot.getValue(Dosar.class);
                    if (!listaDosareG.contains(dosar)) {
                        String titluDosar = dosarSnapshot.child("titlu").getValue(String.class);
                        dosar.setTitluDosar(titluDosar);
                        listaDosareG.add(dosar);
                        adapter.notifyDataSetChanged();
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}


