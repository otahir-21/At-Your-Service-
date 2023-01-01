package com.example.atyourservice.Splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.atyourservice.Login.LoginActivity;
import com.example.atyourservice.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final int SPLASH_SCREEN = 2000;

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(() -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            Toast.makeText(MainActivity.this, "Welcome To At Your Service!", Toast.LENGTH_SHORT).show();
            if (user != null) {
                String uid = user.getUid();
                Log.d("uid", uid);
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                // User is signed in
            } else {

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }, SPLASH_SCREEN);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}