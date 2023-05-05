package com.example.licentaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.licentaapp.Clase.Client;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

     TextInputLayout til_clientCode,til_email,til_passwordRegister,til_repeatPassword,til_adresa,til_nrAp;
     Button registerBtn;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Creare cont");
        //back btn
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        til_clientCode=findViewById(R.id.til_codclient);
        til_email=findViewById(R.id.til_mail);
        til_passwordRegister=findViewById(R.id.til_passwordregister);
        til_repeatPassword=findViewById(R.id.til_passwordregisterrepeat);
        til_adresa=findViewById(R.id.til_adresa);
        til_nrAp=findViewById(R.id.til_nrAp);
        registerBtn=findViewById(R.id.btn_register);

        firebaseAuth=FirebaseAuth.getInstance();

        registerBtn.setOnClickListener(getRegisterEvent());
    }

    private View.OnClickListener getRegisterEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clientCode=til_clientCode.getEditText().getText().toString().trim();
                String email=til_email.getEditText().getText().toString().trim();
                String passwordRegister=til_passwordRegister.getEditText().getText().toString().trim();
                String repeatPassword=til_repeatPassword.getEditText().getText().toString().trim();
                String adresa=til_adresa.getEditText().getText().toString().trim();
                String nrAp=til_nrAp.getEditText().getText().toString().trim();

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    til_email.setError("E-mail invalid!");
                    til_email.setFocusable(true);
                }

                if(!passwordRegister.equals(repeatPassword)){
                    til_passwordRegister.setError("Parola nu este aceeași!");
                    til_repeatPassword.setError("Parola nu este aceeași!");
                    til_passwordRegister.setFocusable(true);
                    til_repeatPassword.setFocusable(true);
                }else if(passwordRegister.length()<5){
                    til_passwordRegister.setError("Parola trebuie să aibă minim 5 caractere!");
                    til_passwordRegister.setFocusable(true);
                    til_repeatPassword.setError("Parola trebuie să aibă minim 5 caractere!");
                    til_repeatPassword.setFocusable(true);
                }else if(TextUtils.isEmpty(clientCode)){
                    Toast.makeText(RegisterActivity.this,"Introduceti codul de client!",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(email)){
                    Toast.makeText(RegisterActivity.this,"Introduceti e-mailul!",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(passwordRegister)){
                    Toast.makeText(RegisterActivity.this,"Introduceti parola!",Toast.LENGTH_SHORT).show();
                } else if(TextUtils.isEmpty(adresa)){
                    Toast.makeText(RegisterActivity.this,"Introduceti adresa!",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(nrAp)){
                    Toast.makeText(RegisterActivity.this,"Introduceti numărul apartamentului!",Toast.LENGTH_SHORT).show();
                } else{
                registerUser(email,passwordRegister);}
                }
        };
    }

    private void registerUser(String email, String passwordRegister) {
        firebaseAuth.createUserWithEmailAndPassword(email,passwordRegister)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "Inregistrarea a fost realizata cu succes!", Toast.LENGTH_SHORT).show();
                            FirebaseUser user=firebaseAuth.getCurrentUser();
                            Client client=new Client();

                            String clientCode=til_clientCode.getEditText().getText().toString().trim();
                            String adresa=til_adresa.getEditText().getText().toString().trim();
                            String nrAp=til_nrAp.getEditText().getText().toString().trim();

                            String mail=user.getEmail();
                            String uid=user.getUid();
                            //cand utilizatorul s-a logat sa stocheze infromatiile si in realtie database
                            HashMap<Object,String> hashMap=new HashMap<>();
                            hashMap.put("email",mail);
                            hashMap.put("uid",uid);
                            hashMap.put("codClient",clientCode);
                            hashMap.put("adresa",adresa);
                            hashMap.put("apartament",nrAp);
                            hashMap.put("sumaDePlata","");
                            //firebase instance
                            FirebaseDatabase database=FirebaseDatabase.getInstance();
                            //path to store data
                            DatabaseReference reference=database.getReference("Users");
                            //put data within hashmap in database
                            reference.child(uid).setValue(hashMap);

                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            finish();
                        }else{
                            Toast.makeText(RegisterActivity.this, "Inregistrare esuata!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent login = new Intent( RegisterActivity.this,  LoginActivity.class);
        startActivity(login);
        finish();
        return super.onSupportNavigateUp();
    }

}