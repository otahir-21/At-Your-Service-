package com.example.atyourservice.Electrician;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.atyourservice.Map.MapActivity;
import com.example.atyourservice.R;

import java.util.Objects;

public class Electrician extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electrician);
        Objects.requireNonNull(getSupportActionBar()).hide();
        Button next = findViewById(R.id.SearchElectrician);

        next.setOnClickListener(n -> {
            Intent myIntent = new Intent(getApplicationContext(), MapActivity.class);
            myIntent.putExtra("intVariableName", 2);
            startActivity(myIntent);
        });
        ImageButton moveBack = findViewById(R.id.back);
        moveBack.setOnClickListener(n -> super.onBackPressed());
    }
}