package com.example.licentaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;

import com.example.licentaapp.Clase.SpatiuVerde;
import com.example.licentaapp.Fragmente.AsociatiiFragment;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class AddAsociatieActivity extends AppCompatActivity {

    DatabaseReference reference;
    FirebaseUser user;
    ArrayList<HashMap> asociatiiList=new ArrayList<>();
    ArrayAdapter<HashMap> adapter;


    Button addBtn,cancelBtn;
    TextInputLayout numeAsociatie,adresaAsociatie,nrEtaje,totalAp,presedinte,suprafataComuna;
    RadioGroup rgSpatiuVerde;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_asociatie);
        setTitle("Creare asociație de proprietari");
        numeAsociatie=findViewById(R.id.til_numeAsociatie);
        adresaAsociatie=findViewById(R.id.til_adresaAsociatie);
        nrEtaje=findViewById(R.id.til_nrEtajeAsociatie);
        totalAp=findViewById(R.id.til_nrApAsociatie);
        presedinte=findViewById(R.id.til_presedinteAsociatie);
        suprafataComuna=findViewById(R.id.til_suprafataAsociatie);
        rgSpatiuVerde=findViewById(R.id.rgSpatiuVerde);

        addBtn=findViewById(R.id.addAsociatieBtn);
        addBtn.setOnClickListener(addEvent());

        cancelBtn=findViewById(R.id.cancelAsociatieBtn);
        cancelBtn.setOnClickListener(cancelEvent());

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("Administratori").child(user.getUid());
    }

    private View.OnClickListener addEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid=user.getUid();
                String text1=numeAsociatie.getEditText().getText().toString();
                String text2=adresaAsociatie.getEditText().getText().toString();
                String text3=nrEtaje.getEditText().getText().toString();
                String text4=totalAp.getEditText().getText().toString();
                String text5=presedinte.getEditText().getText().toString();
                String text6=suprafataComuna.getEditText().getText().toString();
                SpatiuVerde spatiuVerde = SpatiuVerde.DA;
                if(rgSpatiuVerde.getCheckedRadioButtonId() ==R.id.spatiuverdenu){
                    spatiuVerde = SpatiuVerde.NU;
                }

                HashMap<Object,String> hashMap2=new HashMap<>();
                hashMap2.put("numeAsociatie",text1);
                hashMap2.put("adresaAsociatie",text2);
                hashMap2.put("nrEtaje",text3);
                hashMap2.put("totalAp",text4);
                hashMap2.put("suprafataComuna",text6);
                hashMap2.put("presedintele",text5);
                hashMap2.put("spatiuVerde",spatiuVerde.toString());
                asociatiiList.add(hashMap2);

                String asociatieId = reference.push().getKey();
                reference.child("Asociatii").child(asociatieId).setValue(hashMap2);
                Intent intent = new Intent(getApplicationContext(), AdminMenuActivity.class);
                startActivity(intent);
            }
        };
    }

    private View.OnClickListener cancelEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminMenuActivity.class);
                startActivity(intent);
            }
        };
    }
}