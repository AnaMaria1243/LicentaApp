package com.example.licentaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

public class RegisterAdminActivity extends AppCompatActivity {

    TextInputLayout til_numeadmin,til_prenumeadmin,til_passwordRegisteradmin,til_repeatPasswordadmin,til_mailadmin;
    Button registerBtnadmin;
    private FirebaseAuth firebaseAuth;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_admin);

        til_numeadmin=findViewById(R.id.til_numeadmin);
        til_prenumeadmin=findViewById(R.id.til_prenumeadmin);
        til_mailadmin=findViewById(R.id.til_mailadmin);
        til_passwordRegisteradmin=findViewById(R.id.til_passwordregisteradmin);
        til_repeatPasswordadmin=findViewById(R.id.til_passwordregisterrepeatadmin);
        registerBtnadmin=findViewById(R.id.btn_registeradmin);
        firebaseAuth=FirebaseAuth.getInstance();

        registerBtnadmin.setOnClickListener(getRegisterEventAdmin());
    }

    private View.OnClickListener getRegisterEventAdmin() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=til_mailadmin.getEditText().getText().toString().trim();
                String passwordRegister=til_passwordRegisteradmin.getEditText().getText().toString().trim();
                String repeatPassword=til_repeatPasswordadmin.getEditText().getText().toString().trim();
                String nume=til_numeadmin.getEditText().getText().toString().trim();
                String prenume=til_prenumeadmin.getEditText().getText().toString().trim();

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    til_mailadmin.setError("E-mail invalid!");
                    til_mailadmin.setFocusable(true);
                }

                if(!passwordRegister.equals(repeatPassword)){
                    til_passwordRegisteradmin.setError("Parola nu este aceeași!");
                    til_repeatPasswordadmin.setError("Parola nu este aceeași!");
                    til_passwordRegisteradmin.setFocusable(true);
                    til_repeatPasswordadmin.setFocusable(true);
                }else if(passwordRegister.length()<5){
                    til_passwordRegisteradmin.setError("Parola trebuie să aibă minim 5 caractere!");
                    til_passwordRegisteradmin.setFocusable(true);
                    til_repeatPasswordadmin.setError("Parola trebuie să aibă minim 5 caractere!");
                    til_repeatPasswordadmin.setFocusable(true);
                }else if(TextUtils.isEmpty(nume)){
                    Toast.makeText(RegisterAdminActivity.this,"Introduceti numele!",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(email)){
                    Toast.makeText(RegisterAdminActivity.this,"Introduceti e-mailul!",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(passwordRegister)){
                    Toast.makeText(RegisterAdminActivity.this,"Introduceti parola!",Toast.LENGTH_SHORT).show();
                } else if(TextUtils.isEmpty(prenume)){
                    Toast.makeText(RegisterAdminActivity.this,"Introduceti adresa!",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(repeatPassword)){
                    Toast.makeText(RegisterAdminActivity.this,"Introduceti numărul apartamentului!",Toast.LENGTH_SHORT).show();
                } else{
                    registerAdmin(email,passwordRegister);}
            }
        };
    }

    private void registerAdmin(String email, String passwordRegister) {
        firebaseAuth.createUserWithEmailAndPassword(email,passwordRegister)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterAdminActivity.this, "Inregistrarea a fost realizata cu succes!", Toast.LENGTH_SHORT).show();
                            FirebaseUser user=firebaseAuth.getCurrentUser();


                            String nume=til_numeadmin.getEditText().getText().toString().trim();
                            String prenume=til_prenumeadmin.getEditText().getText().toString().trim();


                            String mail=user.getEmail();
                            String uid=user.getUid();
                            if (email.contains("@admin.com")) {
                                HashMap<Object,String> hashMap=new HashMap<>();
                                hashMap.put("nume",nume);
                                hashMap.put("prenume",prenume);
                                hashMap.put("email",mail);
                                hashMap.put("uid",uid);
                                //firebase instance
                                FirebaseDatabase database=FirebaseDatabase.getInstance();
                                //path to store data
                                DatabaseReference reference=database.getReference("Administratori");
                                //put data within hashmap in database
                                reference.child(uid).setValue(hashMap);
                            }else{
                                Toast.makeText(RegisterAdminActivity.this, "Email-ul trebuie sa fie de forma abc@admin.com", Toast.LENGTH_SHORT).show();
                            }

                            startActivity(new Intent(RegisterAdminActivity.this, LoginActivity.class));
                            finish();
                        }else{
                            Toast.makeText(RegisterAdminActivity.this, "Inregistrare esuata!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterAdminActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}