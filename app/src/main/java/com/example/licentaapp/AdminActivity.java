package com.example.licentaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.licentaapp.Fragmente.AsociatiiFragment;
import com.example.licentaapp.Fragmente.ContactProprietariFragment;
import com.example.licentaapp.Fragmente.GestionareFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminActivity extends AppCompatActivity {
    BottomNavigationView navigationView;
    FirebaseAuth firebaseAuth;
    ActionBar actionBar;

    AsociatiiFragment asociatiiFragment=new AsociatiiFragment();
    ContactProprietariFragment contactProprietariFragment=new ContactProprietariFragment();
    GestionareFragment gestionareFragment=new GestionareFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        actionBar=getSupportActionBar();
        firebaseAuth=FirebaseAuth.getInstance();
        navigationView=findViewById(R.id.bottomnavigationAdmin);

        getSupportFragmentManager().beginTransaction().replace(R.id.contentFrameAdmin,asociatiiFragment).commit();
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.menu_asociatii:
                        getSupportFragmentManager().beginTransaction().replace(R.id.contentFrameAdmin,asociatiiFragment).commit();
                        return true;
                    case R.id.menu_contact:
                        getSupportFragmentManager().beginTransaction().replace(R.id.contentFrameAdmin,contactProprietariFragment).commit();
                        return true;
                    case R.id.menu_gestionare:
                        getSupportFragmentManager().beginTransaction().replace(R.id.contentFrameAdmin,gestionareFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }

    private void checkUserStatus(){
        FirebaseUser user=firebaseAuth.getCurrentUser();
        if (user!=null){
            user.reload();
        }else{
            startActivity(new Intent(AdminActivity.this, LoginActivity.class));
            finish();
        }
    }

    @Override
    protected void onStart() {
        checkUserStatus();
        super.onStart();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_3puncte,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.menu_3puncte){
            firebaseAuth.signOut();
            checkUserStatus();
        }

        return super.onOptionsItemSelected(item);
    }
}