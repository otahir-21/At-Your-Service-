package com.example.atyourservice.Map;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.atyourservice.Model.acceptedjob;
import com.example.atyourservice.Model.rejectjob;
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
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
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


public class MapActivity extends FragmentActivity implements OnMapReadyCallback,
        LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    final Handler handler = new Handler();
    Location mLastLocation;
    Marker mCurrLocationMarker;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference3;
    DatabaseReference getDatabaseReference;
    Double latFromDataBse;
    Double lngFromDataBse;
    LatLng latLng1;
    LatLng latLng;
    Double CurrentUserLat;
    Double CurrentUserLng;
    String uid;
    int intValue;
    String type;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    int reject = 0;
    String uidworkeraccept;
    double latworkeraccept, lngworkeraccept;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Intent mIntent = getIntent();
        intValue = mIntent.getIntExtra("intVariableName", 0);
        System.out.println("workssssssssssser" + intValue);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        if (Objects.equals(intValue, 1)) {
            type = "Plumber";
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference("Worker Location").child("Plumber");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        Double latFromDataBse = snapshot1.child("latitude").getValue(Double.class);
                        Double lngFromDataBse = snapshot1.child("longitude").getValue(Double.class);
                        uid = snapshot1.getKey();
                        try {
                            System.out.println(latFromDataBse + " " + lngFromDataBse + "dsadsadasdasdasdasdasd   " + uid);
                            latLng1 = new LatLng(latFromDataBse, lngFromDataBse);
                        } catch (NullPointerException e) {
                            System.out.println(e + "Dsd");
                        }
                        for (int i = 0; i < Objects.requireNonNull(uid).length(); i++) {

                            // below line is use to add marker to each location of our array list.
                            mMap.addMarker(new MarkerOptions().position(new LatLng(latLng1.latitude, latLng1.longitude)).title("Plumber"));

                            // below lin is use to zoom our camera on map.
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(12));

                            // below line is use to move our camera to the specific location.
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng1));

                            System.out.println("Latssssssssssssssssssssssssssss" + latLng1);

                            calculationminimumdistance();
                            System.out.println("Minimum element in ArrayList = " + calculationminimumdistance() + "sadsad" + CurrentUserLat + uid);

                        }
                    }
                    System.out.println("Latssssssssssssssssssssssssssss" + latLng + "asdasdjabfkasbfkabask" + CurrentUserLat);
                    System.out.println("Minimum element in ArrayList = " + calculationminimumdistance() + "sadsad" + CurrentUserLat + uid);

                    content();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    System.out.println("LatLng Not Found");
                }
            });

        } else if (Objects.equals(intValue, 2)) {
            type = "Electrician";
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference("Worker Location").child("Electrician");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        Double latFromDataBse = snapshot1.child("latitude").getValue(Double.class);
                        Double lngFromDataBse = snapshot1.child("longitude").getValue(Double.class);
                        uid = snapshot1.getKey();
                        try {
                            System.out.println(latFromDataBse + " " + lngFromDataBse + "dsadsadasdasdasdasdasd   " + uid);
                            latLng1 = new LatLng(latFromDataBse, lngFromDataBse);
                        } catch (NullPointerException e) {
                            System.out.println(e + "Dsd");
                        }
                        for (int i = 0; i < Objects.requireNonNull(uid).length(); i++) {

                            // below line is use to add marker to each location of our array list.
                            mMap.addMarker(new MarkerOptions().position(new LatLng(latLng1.latitude, latLng1.longitude)).title("Plumber"));

                            // below lin is use to zoom our camera on map.
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(12));

                            // below line is use to move our camera to the specific location.
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng1));
//                            calculationminimumdistance();
                            System.out.println("fksnfkjsnf" + latLng1.latitude);
                            System.out.println(CurrentUserLng);
                        }
                    }
                    double s = 0.0;
                    try {
                        s = distance(CurrentUserLat, CurrentUserLng, latLng1.latitude, latLng1.longitude);

                    } catch (NullPointerException e) {
                        System.out.println("jfskjdbff" + e);
                    }
                    System.out.println(s + "fkjbfkjbfkjb");
//                    System.out.println("Lat" + latFromDataBse + "Lng" + latFromDataBse + latLng1);
                    System.out.println("Latssssssssssssssssssssssssssss" + latLng1);
                    System.out.println("Minimum element in ArrayList = " + calculationminimumdistance() + " " + uid);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    System.out.println("LatLng Not Found");
                }
            });
        } else if (Objects.equals(intValue, 3)) {
            type = "AcService";
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference("Worker Location").child("Ac Service");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        Double latFromDataBse = snapshot1.child("latitude").getValue(Double.class);
                        Double lngFromDataBse = snapshot1.child("longitude").getValue(Double.class);
                        uid = snapshot1.getKey();
                        try {
                            System.out.println(latFromDataBse + " " + lngFromDataBse + "dsadsadasdasdasdasdasd   " + uid);
                            latLng1 = new LatLng(latFromDataBse, lngFromDataBse);
                        } catch (NullPointerException e) {
                            System.out.println(e + "Dsd");
                        }
                        for (int i = 0; i < Objects.requireNonNull(uid).length(); i++) {

                            // below line is use to add marker to each location of our array list.
                            mMap.addMarker(new MarkerOptions().position(new LatLng(latLng1.latitude, latLng1.longitude)).title("Plumber"));

                            // below lin is use to zoom our camera on map.
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(12));

                            // below line is use to move our camera to the specific location.
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng1));
//                            calculationminimumdistance();
                            System.out.println("fksnfkjsnf" + latLng1.latitude);
                            System.out.println(CurrentUserLng);
                        }
                    }
                    double s = 0.0;
                    try {
                        s = distance(CurrentUserLat, CurrentUserLng, latLng1.latitude, latLng1.longitude);

                    } catch (NullPointerException e) {
                        System.out.println("jfskjdbff" + e);
                    }
                    System.out.println(s + "fkjbfkjbfkjb");
//                    System.out.println("Lat" + latFromDataBse + "Lng" + latFromDataBse + latLng1);
                    System.out.println("Latssssssssssssssssssssssssssss" + latLng1);
                    System.out.println("Minimum element in ArrayList = " + calculationminimumdistance() + " " + uid);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    System.out.println("LatLng Not Found");
                }
            });
        } else if (Objects.equals(intValue, 4)) {
            type = "CarWash";
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference("Worker Location").child("CarWash");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        Double latFromDataBse = snapshot1.child("latitude").getValue(Double.class);
                        Double lngFromDataBse = snapshot1.child("longitude").getValue(Double.class);
                        uid = snapshot1.getKey();
                        try {
                            System.out.println(latFromDataBse + " " + lngFromDataBse + "dsadsadasdasdasdasdasd   " + uid);
                            latLng1 = new LatLng(latFromDataBse, lngFromDataBse);
                        } catch (NullPointerException e) {
                            System.out.println(e + "Dsd");
                        }
                        for (int i = 0; i < Objects.requireNonNull(uid).length(); i++) {

                            // below line is use to add marker to each location of our array list.
                            mMap.addMarker(new MarkerOptions().position(new LatLng(latLng1.latitude, latLng1.longitude)).title("Plumber"));

                            // below lin is use to zoom our camera on map.
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(12));

                            // below line is use to move our camera to the specific location.
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng1));
//                            calculationminimumdistance();
                            System.out.println("fksnfkjsnf" + latLng1.latitude);
                            System.out.println(CurrentUserLng);
                        }
                    }
                    double s = 0.0;
                    try {
                        s = distance(CurrentUserLat, CurrentUserLng, latLng1.latitude, latLng1.longitude);

                    } catch (NullPointerException e) {
                        System.out.println("jfskjdbff" + e);
                    }
                    System.out.println(s + "fkjbfkjbfkjb");
//                    System.out.println("Lat" + latFromDataBse + "Lng" + latFromDataBse + latLng1);
                    System.out.println("Latssssssssssssssssssssssssssss" + latLng1);
                    System.out.println("Minimum element in ArrayList = " + calculationminimumdistance() + " " + uid);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    System.out.println("LatLng Not Found");
                }
            });
        }

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
    }

    private void content() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("RejectedJob");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    rejectjob data = snapshot1.getValue(rejectjob.class);
                    reject = Objects.requireNonNull(data).getRejectedRequest();
                    System.out.println(reject + "kfjsbdfksbfksjbfkjs");
                    if (reject == 1) {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(MapActivity.this);
                        builder1.setMessage("No Worker Is Available Please Try Again Later....");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "ok",
                                (dialog, id) -> {
                                    databaseReference = firebaseDatabase.getReference("RejectedJob").child(uid);
                                    int a = 0;
                                    databaseReference.child("RejectedRequest").setValue(a = 0);
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();

//                        Toast.makeText(MapActivity.this, "reject", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("LatLng Not Found");
            }
        });
//        Toast.makeText(this, "Plumber", Toast.LENGTH_SHORT).show();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference3 = firebaseDatabase.getReference("AcceptedJob");
        databaseReference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    acceptedjob data = snapshot1.getValue(acceptedjob.class);
                    try {
                        uidworkeraccept = data.getWorkerWhoAcceptJobUid();
                        latworkeraccept = data.getWorkerWhoAcceptJobLat();
                        lngworkeraccept = data.getWorkerWhoAcceptJobLng();
                    } catch (NullPointerException e) {
                        System.out.println("nullobject");
                    }
                    System.out.println("uidsakndkauidsakndka" + uidworkeraccept + "lat" + latworkeraccept + "lng" + lngworkeraccept);
                    LatLng latLng = new LatLng(CurrentUserLat, CurrentUserLng);
                    String url = getUrl(latLng, new LatLng(latworkeraccept, lngworkeraccept));

                    MapActivity.FetchUrl FetchUrl = new MapActivity.FetchUrl();

                    FetchUrl.execute(url);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        refresh(10000);
    }

    private void refresh(int i) {
        final Runnable runnable = this::content;
        handler.postDelayed(runnable, i);
    }

    private Double calculationminimumdistance() {
        ArrayList<Double> myList = new ArrayList<Double>();
        double dist = 0.0;
        try {
            dist = distance(CurrentUserLat, CurrentUserLng, latLng1.latitude, latLng1.longitude);
        } catch (NullPointerException e) {
            System.out.println("nullobjet" + e);
        }
        myList.add(dist);
        Double minimum = myList.get(0);     // minimum distance is in this variable
        for (int i = 0; i < myList.size(); i++) {
            if (minimum > myList.get(i))
                minimum = myList.get(i);
            System.out.println("minimumdistance" + minimum + "dsdsdsd" + dist);
        }
        return minimum;
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);

    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
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
    public void onLocationChanged(@NonNull Location location) {

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CurrentUserLat = location.getLatitude();
        CurrentUserLng = location.getLongitude();
        if (intValue == 1) {
            type = "Plumber";
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference("PlumberJob").child(uid);
            databaseReference.child("latitude").setValue(CurrentUserLat);
            databaseReference.child("longitude").setValue(CurrentUserLng);
            databaseReference.child("uid").setValue(uid);

        } else if (intValue == 2) {
            type = "Electrician";
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference("ElectricianJob").child(uid);
            databaseReference.child("latitude").setValue(CurrentUserLat);
            databaseReference.child("longitude").setValue(CurrentUserLng);
            databaseReference.child("uid").setValue(uid);

        } else if (intValue == 3) {
            type = "AcService";
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference("AcServiceJob").child(uid);
            databaseReference.child("latitude").setValue(CurrentUserLat);
            databaseReference.child("longitude").setValue(CurrentUserLng);
            databaseReference.child("uid").setValue(uid);

        } else if (intValue == 4) {
            type = "CarWash";
            firebaseDatabase = FirebaseDatabase.getInstance();
            try {
                databaseReference = firebaseDatabase.getReference("CarWashJob").child(uid);
            } catch (NullPointerException e) {
                System.out.println(e);
            }
            databaseReference.child("latitude").setValue(CurrentUserLat);
            databaseReference.child("longitude").setValue(CurrentUserLng);
            databaseReference.child("uid").setValue(uid);
        }
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        Circle circle = mMap.addCircle(new CircleOptions()
                .center(new LatLng(location.getLatitude(), location.getLongitude()))
                .radius(5000)
                .strokeColor(Color.RED)
                .fillColor(Color.TRANSPARENT));
        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
//        String url = getUrl(latLng, new LatLng(33.5969, 73.0528));
//        FetchUrl FetchUrl = new FetchUrl();
//// Start downloading json data from Google Directions API
//        FetchUrl.execute(url);
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


    //
    public class FetchUrl extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

// For storing data from web service
            String data = "";
            try {
// Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.d("Background Task data", data.toString());
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ParserTask parserTask = new ParserTask();
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
                        Log.i("req data", test7.toString());
                    }

                    System.out.println("helo");

                } catch (Throwable t) {
                    Log.e("My App", "Could not parse malformed JSON: \"" + data + "\"");
                }


//                Log.d("downloadUrl", data.toString());

                br.close();
            } catch (Exception e) {
                Log.d("Exception", e.toString());
            } finally {
                iStream.close();
                urlConnection.disconnect();
            }
            return data;
        }
    }

    public class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
        // Parsing the data in non-ui thread

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;
            try {
                jObject = new JSONObject(jsonData[0]);
                Log.d("ParserTask", jsonData[0].toString());
                DataParser parser = new DataParser();
                Log.d("ParserTask", parser.toString());

// Starts parsing data
                routes = parser.parse(jObject);
                Log.d("ParserTask", "Executing routes");
                Log.d("ParserTask", routes.toString());
            } catch (Exception e) {
                Log.d("ParserTask", e.toString());
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
                Log.d("onPostExecute", "onPostExecute lineoptions decoded");
            }
// Drawing polyline in the Google Map for the i-th route
            if (lineOptions != null) {
                mMap.addPolyline(lineOptions);
            } else {
                Log.d("onPostExecute", "without Polylines drawn");
            }
        }
    }

}