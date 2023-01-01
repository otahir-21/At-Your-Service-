package com.example.atyourservice.Worker;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.atyourservice.R;
import com.example.atyourservice.Worker.SelectWorker.Fragments.WorkerHome;
import com.example.atyourservice.Worker.SelectWorker.Fragments.WorkerProfile;

public class WorkerMenu extends AppCompatActivity {
    ImageButton fragmentNo1, fragmentNo2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_menu);
        fragmentNo1 = findViewById(R.id.WorkerFragment1Home);
        fragmentNo2 = findViewById(R.id.WorkerFragment2Profile);

        fragmentNo1.setOnClickListener(v -> replaceFragment(new WorkerHome()));
        fragmentNo2.setOnClickListener(v -> replaceFragment(new WorkerProfile()));
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.WorkerFragemntMenu, fragment);
        fragmentTransaction.commit();
    }
}