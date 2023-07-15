package com.example.licentaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.licentaapp.Clase.Asociatie;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class DocUploadActivity extends AppCompatActivity {
    private static final int PICK_PDF_REQUEST = 1;
    Button uploadButton;
    Uri selectedPdfUri;
    DatabaseReference databaseRef;
    StorageReference storageRef;
    String titludoc;

    ListView listViewPdf;
    ArrayList<String> documentsList=new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_upload);
        setTitle("Încărcare documente");

        listViewPdf =findViewById(R.id.listViewPdfAdmin);
        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, documentsList);
        PDFAdminAdapter adapter = new PDFAdminAdapter(getApplicationContext(), R.layout.adapter_pdf_admin, documentsList);
        listViewPdf.setAdapter(adapter);

        Intent intent = getIntent();
        String titluDosar = intent.getStringExtra("dosarNume");

        // Obțineți o referință către baza de date Realtime Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseRef = database.getReference("Upload");

        // Obțineți o referință către Firebase Storage
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        uploadButton = findViewById(R.id.btnUploadDocumentG);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Deschideți selectorul de fișiere pentru a permite utilizatorului să selecteze un document PDF
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(intent, PICK_PDF_REQUEST);

                Timer timer = new Timer();

                // Programarea unei TimerTask pentru a afișa adaptorul personalizat în ListView după o întârziere de 3 secunde
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        // Afișează adaptorul personalizat în ListView
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listViewPdf.setAdapter(adapter);
                                documentsList.add(titludoc);
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                }, 3000);

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedPdfUri = data.getData();
            uploadFileToFirebase();
        }
    }


    private void uploadFileToFirebase() {
        if (selectedPdfUri != null) {
            StorageReference fileRef = storageRef.child("cale_document.pdf");
            UploadTask uploadTask = fileRef.putFile(selectedPdfUri);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri downloadUrl) {
                            String downloadUrlString = downloadUrl.toString();
                            // Salvati URL-ul de descărcare în baza de date Realtime Firebase

                            String urlId = databaseRef.push().getKey();
                            databaseRef.child("url").child(urlId).setValue(downloadUrlString);

                            Toast.makeText(getApplicationContext(), "Documentul PDF a fost încărcat cu succes.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Eroare la încărcarea documentului PDF."+ e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}