package com.example.atyourservice.plumber;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.atyourservice.Map.MapActivity;
import com.example.atyourservice.R;

import java.util.Objects;

public class Plumber extends AppCompatActivity {
    ImageButton moveBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plumber);
        Objects.requireNonNull(getSupportActionBar()).hide();


        moveBack=findViewById(R.id.back);
        Button next = findViewById(R.id.SearchPlumber);
        next.setOnClickListener(n->{
            Intent myIntent = new Intent(getApplicationContext(), MapActivity.class);
            myIntent.putExtra("intVariableName", 1);
            startActivity(myIntent);
        });

        Button clear = findViewById(R.id.GeyserInstallation);
        clear.setOnClickListener(n->{
            CheckBox mcheckbox =findViewById(R.id.leakReplacement);
            if (mcheckbox .isChecked()){
                mcheckbox.setChecked(false);
            }
            CheckBox acheckbox =findViewById(R.id.Drain);
            if (acheckbox .isChecked()){
                acheckbox.setChecked(false);
            }
            CheckBox scheckbox =findViewById(R.id.Water);
            if (scheckbox .isChecked()){
                scheckbox.setChecked(false);
            }
            CheckBox pcheckbox =findViewById(R.id.Geyser);
            if (pcheckbox .isChecked()){
                pcheckbox.setChecked(false);
            }
            CheckBox fcheckbox =findViewById(R.id.Pipe);
            if (fcheckbox .isChecked()){
                fcheckbox.setChecked(false);
            }
        });
        moveBack.setOnClickListener(n -> super.onBackPressed());

    }
}