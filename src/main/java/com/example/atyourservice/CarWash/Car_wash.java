package com.example.atyourservice.CarWash;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.atyourservice.Map.MapActivity;
import com.example.atyourservice.R;

import java.util.Objects;

public class Car_wash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_wash);
        Objects.requireNonNull(getSupportActionBar()).hide();

        Button next = findViewById(R.id.SearchCarwash);
        ImageButton moveBack = findViewById(R.id.back);

        next.setOnClickListener(n -> {
            Intent myIntent = new Intent(getApplicationContext(), MapActivity.class);
            myIntent.putExtra("intVariableName", 4);
            startActivity(myIntent);
        });

        moveBack.setOnClickListener(n -> super.onBackPressed());
    }
}