package com.caseyyoung.potholetracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import android.Manifest;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.vision.text.Text;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainActivity extends FragmentActivity implements View.OnClickListener, GoogleMap.OnMapClickListener, OnMapReadyCallback {

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference ref = db.getReference();
    private GoogleMap gMap;
    protected static final String TAG = "MainActivity";
    private Button track;
    private Button report;
    private Pothole currentHole;
    private LocationManager locationManager;
    private String address;
    private Location mCurrentLocation;
    private double lat;
    private double lng;
    private static User user;
    private ArrayList<Pothole> holes;
    private int potholeSeverity;
    private EditText severityText;
    private LatLng coords;
    private ArrayList<Pothole> pots;


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * @Description : RETURN BTN RESPONSE FUNCTION
     */
    public void onReturn(View view) {
        Log.d(TAG, "onReturn");
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        // When the compile and target version is higher than 22, please request the
        // following permissions at runtime to ensure the
        // SDK work well.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.VIBRATE,
                            Manifest.permission.INTERNET, Manifest.permission.ACCESS_WIFI_STATE,
                            Manifest.permission.WAKE_LOCK, Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SYSTEM_ALERT_WINDOW,
                            Manifest.permission.READ_PHONE_STATE,
                    }
                    , 1);
        }
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        initUser();
        severityText = (EditText)findViewById(R.id.severityText);
        initUI();

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!isLocationEnabled()) {
            showAlert();
        }
        getLocation();
    }


    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mCurrentLocation = location;
                System.out.println(mCurrentLocation.toString());
                address="";
                getAddress();
                currentHole = new Pothole(lat,lng, address, 5);
                coords = new LatLng(lat, lng);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
//        switch (v.getId()) {
//            case R.id.config:{
//                showSettingDialog();
//                break;
//            }
//            default:
//                break;
//        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        // TODO Auto-generated method stub
        // Initializing Amap object
        if (gMap == null) {
            gMap = googleMap;
            setUpMap();
            gMap.getUiSettings().setZoomGesturesEnabled(true);
        }
        LatLng ge = new LatLng(29.952000, -90.070151);
//        gMap.addMarker(new MarkerOptions().position(ge).title("Marker at GE"));
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ge, 14.0f));
        ref.child("users").child(user.getUsername()).child("holes").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d: dataSnapshot.getChildren()){
                    Pothole p = new Pothole();
                    p = d.getValue(Pothole.class);
                    pots.add(p);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void setUpMap() {
        gMap.setOnMapClickListener(this);// add the listener for click for amap object
        gMap.getUiSettings().setZoomControlsEnabled(true);
        gMap.setMapType(1);
    }
    @Override
    public void onMapClick(LatLng point) {
    }

    private void initUI() {
        track = (Button)findViewById(R.id.button2);
        pots = new ArrayList<>();

/*


move to new activity to reset display
 */
        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.child("users").child(user.getUsername()).child("holes").push().setValue(currentHole);
                System.out.println("NUMBER OF POTHOLES: " + pots.size());
                gMap.addMarker(new MarkerOptions().position(coords).title("Marker at click"));
                gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coords, 25.0f));
                Snackbar.make(findViewById(R.id.activity_main), "pothole added",5000).show();
                severityText.setVisibility(View.VISIBLE);
                severityText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                        //severity will be rounded to fit within range 1-5
                        if (!severityText.getText().toString().isEmpty()) {
                            currentHole.setSeverity(Integer.parseInt(severityText.getText().toString()));
                            System.out.println("SEVERITY " + currentHole.getSeverity());
                            gMap.addMarker(new MarkerOptions().position(coords).title("Marker at click"));
                            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coords, 25.0f));
                            Snackbar.make(findViewById(R.id.activity_main), "pothole added", 5000).show();
                        }
//                        severityText.setVisibility(View.INVISIBLE);
                    }
                });
            }
            });



        report =(Button)findViewById(R.id.reportButton);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ReportActivity.class);

                startActivity(intent);
                finish();
            }
        });
        }
    private boolean isLocationEnabled(){
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
    private void initUser(){
//        user = new User("ethan@crochet.getRekt", holes, 3, 5);
        ArrayList<Pothole> h = new ArrayList<>();
        h.add(new Pothole(-90.77, 30.231, "Address", 5));
        holes = h;
        user = new User( "ethan@yahoo.com", holes , 3 ,4 , "getRekt");
        ref.child("users").child(user.getUsername()).setValue(user);
    }
    private void showAlert(){
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location").setMessage("Your Locations Settings is set to 'Off'." +
                "\nPlease Enable Location to use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int paramint) {
                Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(myIntent);
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int paramint) {

            }
        });
        dialog.show();
    }
    public void getAddress(){
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        lat = mCurrentLocation.getLatitude();
        lng = mCurrentLocation.getLongitude();
        try {
            List<Address> addresses = geocoder.getFromLocation(lat,lng,1);
            Address addy = addresses.get(0);
            System.out.println(addy);
            for (int i =0; i<= addy.getMaxAddressLineIndex(); i++){
                if(i < addy.getMaxAddressLineIndex()){
                    address = address + addy.getAddressLine(i) + ", ";
                }
                else {
                    address = address + " " + addy.getAddressLine(i);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
