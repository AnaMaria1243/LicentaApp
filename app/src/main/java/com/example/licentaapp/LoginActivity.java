package com.example.licentaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout email;
    private TextInputLayout password;
    private Button btn_login;
    private TextView text_noaccount;
    private TextView text_forgotpassword;

    private FirebaseAuth firebaseAuth;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("");

        firebaseAuth=FirebaseAuth.getInstance();

        btn_login=findViewById(R.id.btn_login);
        text_noaccount=findViewById(R.id.text_noaccount);
        email=findViewById(R.id.til_username);
        password=findViewById(R.id.til_password);
        text_forgotpassword=findViewById(R.id.text_forgotpassword);

        text_noaccount.setOnClickListener(getRegisterEvent());
        btn_login.setOnClickListener(getLoginEvent());
        text_forgotpassword.setOnClickListener(getForgotPassworEvent());

        pd=new ProgressDialog(this);

    }

    private View.OnClickListener getForgotPassworEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRecoverPasswordDialog();
            }
        };
    }

    private void showRecoverPasswordDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Recuperare parola");
        LinearLayout linearLayout=new LinearLayout(this);
        EditText emailEt=new EditText(this);
        emailEt.setHint("Introduceți email-ul");
        emailEt.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        emailEt.setMinEms(16);

        linearLayout.addView(emailEt);
        linearLayout.setPadding(10,10,10,10);

        builder.setView(linearLayout);

        builder.setPositiveButton("Recuperare", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email=emailEt.getText().toString().trim();
                beginRecovery(email);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            }
        });
        
        builder.create().show();
    }

    private void beginRecovery(String email) {
        pd.setMessage("Trimitere mail...");
        pd.show();
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
               pd.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Mail trimis! Verificați mesajul trimis pe email!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginActivity.this, "Eroare!", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(LoginActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private View.OnClickListener getLoginEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=email.getEditText().getText().toString();
                String parola=password.getEditText().getText().toString();
                if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
                    email.setError("E-mail invalid!");
                    email.setFocusable(true);
                }else{
                    loginUser(mail,parola);
                }
            }
        };
    }

    private void loginUser(String mail, String parola) {
        pd.setMessage("Log in...");
        pd.show();
        firebaseAuth.signInWithEmailAndPassword(mail,parola)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            pd.dismiss();
                            FirebaseUser user= firebaseAuth.getCurrentUser();
                            String email = user.getEmail();
                            if (email != null) {
                                if (email.contains("@admin.com")) {
                                    startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                                } else {
                                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                }
                                finish();
//                            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
//                            finish();
                            }
                            }else{
                            pd.dismiss();
                            Toast.makeText(LoginActivity.this, "Autentificare esuata!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(LoginActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private View.OnClickListener getRegisterEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();

            }
        };
    }

}