package com.example.atyourservice.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.atyourservice.Admin.Fragments.MyAdapter;
import com.example.atyourservice.Admin.Fragments.SenderModel;
import com.example.atyourservice.Login.LoginActivity;
import com.example.atyourservice.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Profile extends Fragment {
    View view;
    FusedLocationProviderClient fusedLocationProviderClient;
    ArrayList<SenderModel> list;
    MyAdapter adapter;
    private ProgressDialog mProgress;

    TextView addressUser, name, number;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public Profile() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        mProgress.show();
        LocationManager locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getLocation();
        } else {
            showAlertMessageLocationDisabled();
        }

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Customers");
        list = new ArrayList<>();
        adapter = new MyAdapter(getContext(), list);
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot db : snapshot.getChildren()) {
                    SenderModel senderModel = db.getValue(SenderModel.class);
                    assert senderModel != null;
                    name.setText(senderModel.getUsername());
                    number.setText(senderModel.getContactNo());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        mProgress.dismiss();
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener((Activity) requireContext(), location -> {
                if (location != null) {
                    Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                    try {
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        String address = addresses.get(0).getAddressLine(0);
                        addressUser.setText(address);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            requestPermission();
        }
    }


    private void showAlertMessageLocationDisabled() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setMessage("Device Location is turned off, Do You Want To Turn On Location?");
        builder.setCancelable(false);
        builder.setPositiveButton("yes", (dialog, which) -> startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)));
        builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(requireActivity(), new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION
                },
                10);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_2, container, false);

        TextView Sign_out = view.findViewById(R.id.signout);
        name = view.findViewById(R.id.nnameee);
        number = view.findViewById(R.id.no);
        LinearLayout help = view.findViewById(R.id.helprate);
        help.setOnClickListener(n -> Toast.makeText(getContext(), "At Your  Services, we provide you with a variety of home services that simplify your everyday living. All you have to do is, Place request, Sit back and relax. Doorstep services is a marketplace for home and professional services where you can find and get every type of service right at your doorstep on a single platform", Toast.LENGTH_SHORT).show());
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        addressUser = view.findViewById(R.id.addresUser);

        LinearLayout rating = view.findViewById(R.id.ratingapp);
        rating.setOnClickListener(n -> {
            final Dialog dialog = new Dialog(getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.bottomsheetlayout);
            dialog.show();
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setGravity(Gravity.BOTTOM);
        });
        Sign_out.setOnClickListener(n -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getContext(), LoginActivity.class);
            intent.putExtra("finish", true);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
            startActivity(intent);
        });

        name.setOnClickListener(v -> {
        });
        mProgress = new ProgressDialog(getContext());
        String titleId = "SignUp is in progress...";
        mProgress.setTitle(titleId);
        mProgress.setMessage("Please Wait...");
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                Toast.makeText(getContext(), "Location Is Required:Please Enable Location From Settings", Toast.LENGTH_SHORT).show();
            }
        }
    }
}