package com.example.licentaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.licentaapp.Fragmente.AvizierFragment;
import com.example.licentaapp.Fragmente.ForumFragment;
import com.example.licentaapp.Fragmente.HomeFragment;
import com.example.licentaapp.Fragmente.PayFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {


    FirebaseAuth firebaseAuth;
    ActionBar actionBar;
    HomeFragment homeFragment=new HomeFragment();
    PayFragment payFragment=new PayFragment();
    AvizierFragment avizierFragment=new AvizierFragment();
    ForumFragment forumFragment=new ForumFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        actionBar=getSupportActionBar();
//        //back btn
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle(" ");


        firebaseAuth=FirebaseAuth.getInstance();

        BottomNavigationView navigationView=findViewById(R.id.bottomnavigation);



        getSupportFragmentManager().beginTransaction().replace(R.id.contentFrame,homeFragment).commit();
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.contentFrame,homeFragment).commit();
                        return true;
                    case R.id.menu_pay:
                        getSupportFragmentManager().beginTransaction().replace(R.id.contentFrame,payFragment).commit();
                        return true;
                    case R.id.menu_avizier:
                        getSupportFragmentManager().beginTransaction().replace(R.id.contentFrame,avizierFragment).commit();
                        return true;
                    case R.id.menu_forum:
                        getSupportFragmentManager().beginTransaction().replace(R.id.contentFrame,forumFragment).commit();
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
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
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