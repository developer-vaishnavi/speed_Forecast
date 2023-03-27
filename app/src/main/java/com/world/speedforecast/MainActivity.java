package com.world.speedforecast;
//
//import android.Manifest;
//import androidx.annotation.NonNull;
//import androidx.annotation.RequiresApi;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.loader.app.LoaderManager;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.content.pm.PackageManager;
//import android.graphics.Color;
//import android.location.Location;
//import android.location.LocationManager;
//import android.location.LocationRequest;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Looper;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationCallback;
//import com.google.android.gms.location.LocationResult;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//
//public class MainActivity extends AppCompatActivity {
//SpeedometerView Speed;
//TextView txtLat;
//FusedLocationProviderClient fusedLocationProviderClient;
//int PERMISSION_ID = 44;
//private static final long INTERVAL = 1000*2;
//private static final long FASTEST_INTERVAL = 1000*5;
//
//    @SuppressLint("MissingInflatedId")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        Speed = (SpeedometerView)findViewById(R.id.speedometer);
//        txtLat = (TextView) findViewById(R.id.TextV);
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//        getLastLocation();
//
//        Speed.setLabelConverter(new SpeedometerView.LabelConverter() {
//            @Override
//            public String getLabelFor(double progress, double maxProgress) {
//                return String.valueOf((int) Math.round(progress));
//            }
//        });
//
//// configure value range and ticks
//        Speed.setMaxSpeed(100);
//        Speed.setMajorTickStep(25);
//        Speed.setMinorTicks(2);
//
//// Configure value range colors
//        Speed.addColoredRange(0, 50, Color.GREEN);
//        Speed.addColoredRange(50, 75, Color.YELLOW);
//        Speed.addColoredRange(75, 100, Color.RED);
//        Speed.setSpeed(25, 2000, 500);
//
//    }
//
//    private void getLastLocation() {
//        if (checkPermission()) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//                if(isLocationEnabled()){
//                fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Location> task) {
//                        Location location = task.getResult();
//                        if (location == null) {
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//                                try {
//                                    requestNewLocation();
//                                }
//                                catch (Exception e) {
//                                    System.out.println(e);
//                                }
//                            }
//                        } else {
//                            double dSpeed = location.getSpeed();
//                            double a = 3.6 * (dSpeed);
//                            int kmhSpeed = (int) (Math.round(a));
//                            txtLat.setText("SPEED= " + kmhSpeed);
//                            Speed.setSpeed(kmhSpeed);
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//                                try {
//                                    requestNewLocation();
//                                }
//                                catch (Exception e) {
//                                    System.out.println(e);
//                                }
//                            }
//                        }
//                    }
//                });
//            } else {
//                Toast.makeText(this, "Please Turn On Your Location", Toast.LENGTH_SHORT).show();
//            }
//            }
//        }
//         else{
//            requestPermission();
//        }
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.P)
//    private boolean isLocationEnabled() {
//        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//
//        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)||locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//    }
////
////    @RequiresApi(api = Build.VERSION_CODES.S)
////    private void requestNewLocation() {
////
//////            LocationRequest locationRequest = new LocationRequest.Builder(FASTEST_INTERVAL).build();
//////
//////            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//////        fusedLocationProviderClient.requestLocationUpdates(locationRequest,
//////                locationCallback,
//////                Looper.getMainLooper());
////
////    }
//@SuppressLint("MissingPermission")
//private void requestNewLocationData() {
//
//    // Initializing LocationRequest
//    // object with appropriate methods
//    LocationRequest mLocationRequest = new LocationRequest();
//    mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//    mLocationRequest.setInterval(5);
//    mLocationRequest.setFastestInterval(0);
//    mLocationRequest.setNumUpdates(1);
//
//    // setting LocationRequest
//    // on FusedLocationClient
//    mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//    mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
//}
//
//    private LocationCallback locationCallback = new LocationCallback() {
//        @Override
//        public void onLocationResult(@NonNull LocationResult locationResult) {
//
//                Location lastLocation = locationResult.getLastLocation();
//                assert lastLocation != null;
//                double dSpeed = lastLocation.getSpeed();
//                double a = 3.6 * (dSpeed);
//                int kmhSpeed = (int) (Math.round(a));
//                txtLat.setText("SPEED= " + kmhSpeed);
//                Speed.setSpeed(kmhSpeed);
//                try {
//                    getLastLocation();
//                } catch (Exception e) {
//                    System.out.println(e);
//                }
//
//        }
//    };
//
//
//
//    private boolean checkPermission() {
//        return ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) ==
//                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED;
//    }
//
//    private void requestPermission(){
//        ActivityCompat.requestPermissions(this,new String[]{
//                Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_ID);
//        }
//        public void onRequestPermissionResult(int requestCode, @NonNull String[] permission, @NonNull int [] grantResult){
//        super.onRequestPermissionsResult(requestCode,permission,grantResult);
//        if(requestCode==PERMISSION_ID){
//            if(grantResult.length>0 && grantResult[0]==PackageManager.PERMISSION_GRANTED){
//                getLastLocation();
//            }
//        }
//        }
//        public void onResume(){
//            super.onResume();
//            if(checkPermission())
//                getLastLocation();
//        }
//
//    }
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    // initializing
    // FusedLocationProviderClient
    // object
    FusedLocationProviderClient mFusedLocationClient;
    SpeedometerView Speed;
    TextView txtLat;
    private static final long INTERVAL = 1000*2;
private static final long FASTEST_INTERVAL = 1000*5;

    // Initializing other items
    // from layout file
    TextView latitudeTextView, longitTextView;
    int PERMISSION_ID = 44;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        latitudeTextView = findViewById(R.id.latTextView);
        longitTextView = findViewById(R.id.lonTextView);
        Speed = (SpeedometerView)findViewById(R.id.speedometer);
        txtLat = (TextView) findViewById(R.id.TextV);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        Speed.setLabelConverter(new SpeedometerView.LabelConverter() {
            @Override
            public String getLabelFor(double progress, double maxProgress) {
                return String.valueOf((int) Math.round(progress));
            }
        });

// configure value range and ticks
        Speed.setMaxSpeed(100);
        Speed.setMajorTickStep(25);
        Speed.setMinorTicks(2);

// Configure value range colors
        Speed.addColoredRange(0, 50, Color.GREEN);
        Speed.addColoredRange(50, 75, Color.YELLOW);
        Speed.addColoredRange(75, 100, Color.RED);
        Speed.setSpeed(25, 2000, 500);

        // method to get the location
        getLastLocation();
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            latitudeTextView.setText(location.getLatitude() + "");
                            longitTextView.setText(location.getLongitude() + "");
                            double dSpeed = location.getSpeed();
                            double a = 3.6 * (dSpeed);
                            int kmhSpeed = (int) (Math.round(a));
                            txtLat.setText("SPEED= " + kmhSpeed);
                            Speed.setSpeed(kmhSpeed);
                            requestNewLocationData();

                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);


        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            latitudeTextView.setText("Latitude: " + mLastLocation.getLatitude() + "");
            longitTextView.setText("Longitude: " + mLastLocation.getLongitude() + "");
            Location lastLocation = locationResult.getLastLocation();
                assert lastLocation != null;
                double dSpeed = lastLocation.getSpeed();
                double a = 3.6 * (dSpeed);
                int kmhSpeed = (int) (Math.round(a));
                txtLat.setText("SPEED= " + kmhSpeed);
                Speed.setSpeed(kmhSpeed);
                try {
                    getLastLocation();
                } catch (Exception e) {
                    System.out.println(e);
                }
        }
    };

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }
}

