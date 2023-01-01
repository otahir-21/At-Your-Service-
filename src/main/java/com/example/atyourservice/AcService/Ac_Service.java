package com.example.atyourservice.AcService;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.atyourservice.Map.MapActivity;
import com.example.atyourservice.R;

import java.util.Objects;

public class Ac_Service extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac_service);
        Objects.requireNonNull(getSupportActionBar()).hide();

        ImageButton moveBack=findViewById(R.id.back);
        Button next = findViewById(R.id.SearchAcservice);

        next.setOnClickListener(n->{
            Intent myIntent = new Intent(getApplicationContext(), MapActivity.class);
            myIntent.putExtra("intVariableName", 3);
            startActivity(myIntent);
        });
        moveBack.setOnClickListener(n -> super.onBackPressed());
    }
}