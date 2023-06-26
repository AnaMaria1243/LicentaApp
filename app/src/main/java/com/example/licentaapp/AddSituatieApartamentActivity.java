package com.example.licentaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class AddSituatieApartamentActivity extends AppCompatActivity {

    DatabaseReference reference;
    FirebaseUser user;
    ArrayList<HashMap> intretinereList=new ArrayList<>();
    ArrayAdapter<HashMap> adapter;

    Button btnAddIntretinere,cancelIntretinereBtn;
    EditText titluDoc,nrApIntretinere,servAdmin,paza,curatenia,fondReparatii,fondChNeprev,salubrizare,lumina,apa;

    Intent intent = getIntent();
    String nrAp = intent.getStringExtra("nrAp");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_situatie_apartament);

//        titluDoc=findViewById(R.id.ed_titluDialog);
//        nrApIntretinere=findViewById(R.id.ed_nrApIntretinereDialog);
//        servAdmin=findViewById(R.id.ed_serviciuAdminDialog);
//        paza=findViewById(R.id.ed_pazaDialog);
//        curatenia=findViewById(R.id.ed_curateniaDialog);
//        fondReparatii=findViewById(R.id.ed_fondReparatiiDialog);
//        fondChNeprev=findViewById(R.id.ed_fondCheltuieliNeprevazuteDialog);
//        salubrizare=findViewById(R.id.ed_salubrizareDialog);
//        lumina=findViewById(R.id.ed_luminaComunaDialog);
//        apa=findViewById(R.id.ed_apaDialog);
//
//
//
//        btnAddIntretinere=findViewById(R.id.addIntretinereBtnDialog);
//        btnAddIntretinere.setOnClickListener(getAddIntretinereEvent());
//
//        cancelIntretinereBtn=findViewById(R.id.cancelAddIntretinereBtnDialog);
//        cancelIntretinereBtn.setOnClickListener(getCancelIntretinereEvent());

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("Administratori").child(user.getUid()).child("Asociatii");
    }

    private View.OnClickListener getAddIntretinereEvent() {
        return new View.OnClickListener() {
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

                intretinereList.add(hashMap6);

                String intretinereId = reference.push().getKey();
                reference.child("Asociatii").child(intretinereId).setValue(hashMap6);
                Intent intent = new Intent(getApplicationContext(), ApartamenteActivity.class);
                startActivity(intent);
            }
        };
    }

    private View.OnClickListener getCancelIntretinereEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
    }


}