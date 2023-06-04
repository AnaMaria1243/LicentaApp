package com.example.licentaapp;

import static androidx.constraintlayout.widget.Constraints.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.example.licentaapp.Fragmente.AsociatiiFragment;
import com.example.licentaapp.Fragmente.ContactProprietariFragment;
import com.example.licentaapp.Fragmente.GestionareFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.licentaapp.databinding.ActivityAdminMenuBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminMenuActivity extends AppCompatActivity {


    DatabaseReference reference;
    FirebaseUser user;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Fragment fragment;
    TextView numeAdmin,emailAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);
        configNavigation();

        navigationView=findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(selectedItem());

        fragment = new AsociatiiFragment();
        openFragment();

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("Administratori").child(user.getUid());
        reference.addListenerForSingleValueEvent(valueEventListener);


        View headerView = navigationView.getHeaderView(0);
        emailAdmin= headerView.findViewById(R.id.emailAdmin);
        numeAdmin=headerView.findViewById(R.id.DetaliiNumeAdmin);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            String txt_nume = dataSnapshot.child("nume").getValue(String.class);
            String txt_prenume = dataSnapshot.child("prenume").getValue(String.class);
            String emailAd=dataSnapshot.child("email").getValue(String.class);
            numeAdmin.setText(txt_nume+" "+txt_prenume);
            emailAdmin.setText(emailAd);;
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.d(TAG, databaseError.getMessage());
        }
    };







    private NavigationView.OnNavigationItemSelectedListener selectedItem(){
        return new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_calculate) {
                    fragment=new GestionareFragment();
                }
                if(item.getItemId() == R.id.nav_apartament) {
                    fragment=new AsociatiiFragment();
                }
                if(item.getItemId() == R.id.nav_contacte) {
                    fragment=new ContactProprietariFragment();
                }
                openFragment();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        };
    }

    private void openFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.containerPrincipal,fragment).addToBackStack(null).commit();

    }

    private void configNavigation() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
}