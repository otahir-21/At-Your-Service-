package com.example.atyourservice.Menu;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.atyourservice.Fragments.Home;
import com.example.atyourservice.Fragments.Profile;
import com.example.atyourservice.R;

import java.util.Objects;

public class MenuActivity extends AppCompatActivity {
ImageButton fragmentNo1,fragmentNo2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Objects.requireNonNull(getSupportActionBar()).hide();
        fragmentNo1=findViewById(R.id.fragment1);
        fragmentNo2=findViewById(R.id.fragment2);

        fragmentNo1.setOnClickListener(v -> replaceFragment(new Home()));
        fragmentNo2.setOnClickListener(v -> replaceFragment(new Profile()));
    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}