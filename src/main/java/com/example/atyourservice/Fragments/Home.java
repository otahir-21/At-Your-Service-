package com.example.atyourservice.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import com.example.atyourservice.AcService.Ac_Service;
import com.example.atyourservice.Electrician.Electrician;
import com.example.atyourservice.R;
import com.example.atyourservice.plumber.Plumber;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.Objects;

public class Home extends Fragment {
    View view;
    FusedLocationProviderClient fusedLocationProviderClient;
    Double lat;
    Double lng;

    SliderView sliderView;
    int[] images = {R.drawable.plumberslider,
            R.drawable.electricianslider,
            R.drawable.acslider,
            R.drawable.carslider};

    @Override
    public void onStart() {
        super.onStart();
        LocationManager locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getLocation();

        } else {
            showAlertMessageLocationDisabled();
        }
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener((Activity) requireContext(), new OnSuccessListener<Location>() {

                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        updateUI(location);
                    }
                }

                private void updateUI(Location location) {
                    lat = location.getLongitude();
                    lng = location.getLatitude();
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
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    public Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_1, container, false);

        sliderView = view.findViewById(R.id.image_slider);

        ImageView imageView = view.findViewById(R.id.mnmn);
        imageView.setOnClickListener(n -> {
            Intent intent = new Intent(getContext(), Plumber.class);
            ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    (Activity) requireContext(),
                    imageView,
                    Objects.requireNonNull(ViewCompat.getTransitionName(imageView))
            );
            startActivity(intent, option.toBundle());
        });

        ImageView electrician = view.findViewById(R.id.electrician);
        electrician.setOnClickListener(n -> {
            Intent intent = new Intent(getContext(), Electrician.class);
            ActivityOptionsCompat option1 = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    (Activity) requireContext(),
                    electrician,
                    Objects.requireNonNull(ViewCompat.getTransitionName(electrician))
            );
            startActivity(intent, option1.toBundle());
        });

        ImageView Ac_Service = view.findViewById(R.id.AcService);
        Ac_Service.setOnClickListener(n -> {
            Intent intent = new Intent(getContext(), Ac_Service.class);
            ActivityOptionsCompat option2 = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    (Activity) requireContext(),
                    Ac_Service,
                    Objects.requireNonNull(ViewCompat.getTransitionName(Ac_Service))
            );
            startActivity(intent, option2.toBundle());
        });

//        ImageView Car_wash = view.findViewById(R.id.Carwash);
//        Car_wash.setOnClickListener(n -> {
//            Intent intent = new Intent(getContext(), Car_wash.class);
//            ActivityOptionsCompat option3 = ActivityOptionsCompat.makeSceneTransitionAnimation(
//                    (Activity) requireContext(),
//                    Car_wash,
//                    Objects.requireNonNull(ViewCompat.getTransitionName(Car_wash))
//            );
//            startActivity(intent, option3.toBundle());
//        });

        SliderAdapter sliderAdapter = new SliderAdapter(images);

        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());

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