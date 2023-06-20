package com.example.licentaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.licentaapp.Fragmente.AsociatiiFragment;

public class UpdateAsociatieActivity extends AppCompatActivity {

    Button xAsociatieBtn,updateAsociatieBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_asociatie);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        xAsociatieBtn=findViewById(R.id.cancelAsociatieBtnUpdate);
        xAsociatieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), AdminMenuActivity.class);
                startActivity(intent);
            }
        });
    }
}