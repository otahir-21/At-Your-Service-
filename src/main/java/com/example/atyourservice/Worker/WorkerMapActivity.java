package com.example.atyourservice.Worker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.atyourservice.Login.LoginActivity;
import com.example.atyourservice.Model.JobSearch;
import com.example.atyourservice.R;
import com.example.atyourservice.Worker.SelectWorker.Fragments.DataParser;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class WorkerMapActivity extends FragmentActivity implements OnMapReadyCallback,
        LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    final Handler handler = new Handler();
    Location mLastLocation;
    Marker mCurrLocationMarker;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference1;
    DatabaseReference databaseReference2;
    LatLng l1;
    String arr;
    double LatAfterSelectingJobWorkerCurrentLocation;
    double LngAfterSelectingJobWorkerCurrentLocation;
    TextView idworker;
    TextView logout;
    Double lat;
    Double lng;
    int w2 =0;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String str;
    Boolean isactive = true;
    private GoogleMap mMap;

    @Override
    protected void onStart() {
        super.onStart();
        try {
            Intent mIntent = getIntent();
            str = mIntent.getStringExtra("Type");
            System.out.println("workssssssssssser" + str);
            idworker.setText(str);
        }catch (NullPointerException e){
            System.out.println("nullobject");
        }

        assignworker();
    }

    private void assignworker() {
        if (str == null) {
            showAlertDialogWorkerSelect();
        } else {
            if (isactive) {
                refresh();
//                content();
            }
//            content();
        }
    }

    private void content() {
        String uid = user.getUid();
        if (Objects.equals(str, "Plumber")) {
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference("PlumberJob");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        JobSearch data = snapshot1.getValue(JobSearch.class);
                        assert data != null;
                        String uidCompare = data.getUid();
                        lat = data.getLatitude();
                        lng = data.getLongitude();
                        try {
                            LatLng latLng = new LatLng(lat, lng);
                            l1 = latLng;
                            System.out.println(latLng + "sadsadasdadwdwdwdw" + " dfsdf" + l1);
                        } catch (NullPointerException e) {
                            System.out.println(e + "nolatlng");
                        }
                        System.out.println("uidfromdb" + uidCompare);
                        if (Objects.equals(uidCompare, uid)) {
                            isactive=false;
                            System.out.println("compare" + uid);

                            showDialog(l1, str, uidCompare);
                        } else {
                            System.out.println("notasdadadFound" + uid);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    System.out.println("LatLng Not Found");
                }
            });
            Toast.makeText(this, "Plumber", Toast.LENGTH_SHORT).show();
        }
        else if (Objects.equals(str, "Electrician")) {
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference("ElectricianJob");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        JobSearch data = snapshot1.getValue(JobSearch.class);
                        assert data != null;
                        String uidCompare = data.getUid();
                        lat = data.getLatitude();
                        lng = data.getLongitude();
                        try {
                            LatLng latLng = new LatLng(lat, lng);
                            l1 = latLng;
                            System.out.println(latLng + "sadsadasdadwdwdwdw" + " dfsdf" + l1);
                        } catch (NullPointerException e) {
                            System.out.println(e + "nolatlng");
                        }
                        System.out.println("uidfromdb" + uidCompare);
                        if (Objects.equals(uidCompare, uid)) {
                            isactive=false;
                            System.out.println("compare" + uid);
                            showDialog(l1, str, uidCompare);
                        } else {
                            System.out.println("notasdadadFound" + uid);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    System.out.println("LatLng Not Found");
                }
            });
            Toast.makeText(this, "Electrician", Toast.LENGTH_SHORT).show();
        }

        else if (Objects.equals(str, "CarWash")) {
            Toast.makeText(this, "CarWash", Toast.LENGTH_SHORT).show();
        } else if (Objects.equals(str, "AcService")) {
            Toast.makeText(this, "AcService", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No Worker has been selected", Toast.LENGTH_SHORT).show();
        }

    }

    private void refresh() {
        final Runnable runnable = this::content;
        handler.postDelayed(runnable, 5000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_map);
        idworker = findViewById(R.id.workertypeid);
        logout = findViewById(R.id.logout);
//        Button profile = findViewById(R.id.profile);
//        profile.setOnClickListener(v -> {
//            BottomSheetDialog bottomSheetFragment = new BottomSheetDialog();
//            bottomSheetFragment.show(getSupportFragmentManager(),bottomSheetFragment.getTag());
//        });
        logout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(WorkerMapActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.WorkerMap);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    private void showAlertDialogWorkerSelect() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(WorkerMapActivity.this);
        alertDialog.setTitle("Please Select Worker Type");
        String[] items = {"Plumber", "Electrician", "Ac Service", "Car Wash","gutter"};
        int checkedItem = -1;
        alertDialog.setSingleChoiceItems(items, checkedItem, (dialog, which) -> {
            switch (which) {
                case 0:
                    HashMap<String, Object> result = new HashMap<>();
                    result.put("WorkerType", "Plumber");
                    FirebaseDatabase.getInstance().getReference().child("Worker").child(user.getUid()).updateChildren(result);
                    Toast.makeText(getApplicationContext(), "Plumber Is Selected", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                    break;
                case 1:
                    HashMap<String, Object> result2 = new HashMap<>();
                    result2.put("WorkerType", "Electrician");
                    FirebaseDatabase.getInstance().getReference().child("Worker").child(user.getUid()).updateChildren(result2);
                    Toast.makeText(getApplicationContext(), "Electrician Is Selected", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                    break;
                case 2:
                    HashMap<String, Object> result3 = new HashMap<>();
                    result3.put("WorkerType", "Ac Service");
                    FirebaseDatabase.getInstance().getReference().child("Worker").child(user.getUid()).updateChildren(result3);

                    Toast.makeText(getApplicationContext(), "Ac Service Is Selected", Toast.LENGTH_LONG).show();
                    dialog.dismiss();

                    break;
                case 3:
                    HashMap<String, Object> result4 = new HashMap<>();
                    result4.put("WorkerType", "Car wash");
                    FirebaseDatabase.getInstance().getReference().child("Worker").child(user.getUid()).updateChildren(result4);
                    Toast.makeText(getApplicationContext(), "Car Wash Is Selected", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                    break;
                case 4:
                    HashMap<String, Object> result5 = new HashMap<>();
                    result5.put("WorkerType", "Gutter");
                    FirebaseDatabase.getInstance().getReference().child("Worker").child(user.getUid()).updateChildren(result5);
                    Toast.makeText(getApplicationContext(), "Car Wash Is Selected", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                    break;
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        Toast.makeText(this, user.getUid(), Toast.LENGTH_SHORT).show();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Worker Location");
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    arr = Objects.requireNonNull(snapshot1.getValue()).toString();
                    System.out.println("LatLng" + arr);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("LatLng Not Found");
            }
        });
    }

    void UserLocation(LatLng l1) {
        LatLng latLng = new LatLng(l1.latitude, l1.longitude);
        // below line is use to add marker to each location of our array list.
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(str + "Customer Location")
        );

        // below lin is use to zoom our camera on map.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f));

        // below line is use to move our camera to the specific location.
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    void showDialog(LatLng l1, String str, String uidCompare) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                .setTitle(str).setMessage(
                        "Are you sure you want to do this job?");
        dialog.setPositiveButton("Confirm",
                (dialog1, whichButton) -> {
                    UserLocation(l1);
                    String url = getUrl(l1, new LatLng(LatAfterSelectingJobWorkerCurrentLocation, LngAfterSelectingJobWorkerCurrentLocation));
                    WorkerMapActivity.FetchUrl FetchUrl = new WorkerMapActivity.FetchUrl();
                    databaseReference2 = firebaseDatabase.getReference("AcceptedJob").child(uidCompare);
                    String uid = user.getUid();
                    w2 = 1;
                databaseReference2.child(uid);
                databaseReference2.child("WorkerType").setValue(str);
                    databaseReference2.child("WorkerWhoAcceptJobUid").setValue(uid);
                    databaseReference2.child("WorkerWhoAcceptJobLat").setValue(LatAfterSelectingJobWorkerCurrentLocation);
                    databaseReference2.child("WorkerWhoAcceptJobLng").setValue(LngAfterSelectingJobWorkerCurrentLocation);
                    // Start downloading json data from Google Directions API
                    FetchUrl.execute(url);
                });

        final AlertDialog alert = dialog.create();
        alert.show();

        new CountDownTimer(10000, 10000) {

            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onFinish() {
                if(w2==1){
                    System.out.println("reject request not send");
                }else {
                    databaseReference2 = firebaseDatabase.getReference("PlumberJob");
                    databaseReference2.child(uidCompare).removeValue().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            isactive = true;
//                            Toast.makeText(WorkerMapActivity.this, "Successfully Delete ", Toast.LENGTH_SHORT).show();
                            int a = 0;
                            databaseReference2 = firebaseDatabase.getReference("RejectedJob").child(user.getUid());
                            databaseReference2.child("RejectedRequest").setValue(a += 1);
                        }

                    });
                }

                alert.dismiss();
            }
        }.start();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();

        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        try {
            databaseReference1 = firebaseDatabase.getReference("Worker Location").child(str).child(user.getUid());
            databaseReference1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    databaseReference1.child("latitude").setValue(location.getLatitude());
                    databaseReference1.child("longitude").setValue(location.getLongitude());
                    // after adding this data we are showing toast message.
//                    Toast.makeText(WorkerMapActivity.this, user.getUid(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // if the data is not added or it is cancelled then
                    // we are displaying a failure toast message.
                    Toast.makeText(WorkerMapActivity.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (NullPointerException e){
            System.out.println("locationnull");
        }


        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        mCurrLocationMarker = mMap.addMarker(markerOptions);
        LatAfterSelectingJobWorkerCurrentLocation = location.getLatitude();
        LngAfterSelectingJobWorkerCurrentLocation = location.getLongitude();

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }


    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    String getUrl(LatLng pickUp, LatLng dest) {

        String origin = pickUp.latitude + "," + pickUp.longitude;
        String destination = dest.latitude + "," + dest.longitude;

        String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" + origin + "&destination=" + destination + "&mode=driving&key=AIzaSyBuR-xq-Vd3LuchLTqEHKlDG7RiGdhMr7w";

        return url;
    }

//    private double distance(double lat1, double lon1, double lat2, double lon2) {
//        double theta = lon1 - lon2;
//        double dist = Math.sin(deg2rad(lat1))
//                * Math.sin(deg2rad(lat2))
//                + Math.cos(deg2rad(lat1))
//                * Math.cos(deg2rad(lat2))
//                * Math.cos(deg2rad(theta));
//        dist = Math.acos(dist);
//        dist = rad2deg(dist);
//        dist = dist * 60 * 1.1515;
//        return (dist);
//    }
//
//    private double deg2rad(double deg) {
//        return (deg * Math.PI / 180.0);
//    }
//
//    private double rad2deg(double rad) {
//        return (rad * 180.0 / Math.PI);
//    }

    class FetchUrl extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

// For storing data from web service
            String data = "";
            try {
// Fetching the data from web service
                data = downloadUrl(url[0]);
//                Log.d("Background Task data", data.toString());
            } catch (Exception e) {
//                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            WorkerMapActivity.ParserTask parserTask = new WorkerMapActivity.ParserTask();

// Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }

        private String downloadUrl(String strUrl) throws IOException {
            String data = "";
            InputStream iStream = null;
            HttpURLConnection urlConnection = null;

            try {
                URL url = new URL(strUrl);
// Creating an http connection to communicate with url
                urlConnection = (HttpURLConnection) url.openConnection();
// Connecting to url
                urlConnection.connect();
// Reading data from url
                iStream = urlConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
                StringBuffer sb = new StringBuffer();
                String line = "";
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                data = sb.toString();


                try {
                    JSONObject obj = new JSONObject(data);
                    JSONArray test1 = obj.getJSONArray("routes");
                    JSONObject test2 = test1.getJSONObject(0);
                    JSONArray test3 = test2.getJSONArray("legs");
                    JSONObject test4 = test3.getJSONObject(0);
                    JSONArray test5 = test4.getJSONArray("steps");
                    for (int i = 0; i < test5.length(); i++) {
                        JSONObject test6 = test5.getJSONObject(i);
                        JSONObject test7 = test6.getJSONObject("distance");
                        System.out.println(test7.toString());
//                        Log.i("req data", test7.toString());
                    }

                    System.out.println("helo");

                } catch (Throwable t) {
//                    Log.e("My App", "Could not parse malformed JSON: \"" + data + "\"");
                }


//                Log.d("downloadUrl", data.toString());

                br.close();
            } catch (Exception e) {
//                Log.d("Exception", e.toString());
            } finally {
                iStream.close();
                urlConnection.disconnect();
            }
            return data;
        }
    }

    class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
        // Parsing the data in non-ui thread

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;
            try {
                jObject = new JSONObject(jsonData[0]);
//                Log.d("ParserTask", jsonData[0].toString());
                DataParser parser = new DataParser();
//                Log.d("ParserTask", parser.toString());

// Starts parsing data
                routes = parser.parse(jObject);
//                Log.d("ParserTask", "Executing routes");
//                Log.d("ParserTask", routes.toString());
            } catch (Exception e) {
//                Log.d("ParserTask", e.toString());
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;
// Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<>();
                lineOptions = new PolylineOptions();
// Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);
// Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);
                    double lat = Double.parseDouble(Objects.requireNonNull(point.get("lat")));
                    double lng = Double.parseDouble(Objects.requireNonNull(point.get("lng")));
                    LatLng position = new LatLng(lat, lng);
                    points.add(position);
                }
// Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(5);
                lineOptions.color(Color.BLUE);
//                Log.d("onPostExecute", "onPostExecute lineoptions decoded");
            }
// Drawing polyline in the Google Map for the i-th route
            if (lineOptions != null) {
                mMap.addPolyline(lineOptions);
            } else {
//                Log.d("onPostExecute", "without Polylines drawn");
            }
        }
    }

}