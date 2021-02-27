package com.example.aqua;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MAP extends FragmentActivity implements OnMapReadyCallback {

    EditText etSource, etDestination;
    Button btnConfirm;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    String status = "false";
    String et = "";
    String destinationStatus = "false";
    String etSourceLat;
    String etSourceLong;
    String etDestinationLat;
    String etDestinationLong;

    private final static int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_a_p);
        Toast.makeText(this, "Moving to current location", Toast.LENGTH_SHORT).show();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();

        //Assign variables
        etSource = findViewById(R.id.et_source);
        etDestination = findViewById(R.id.et_destination);
        btnConfirm = findViewById(R.id.btn_confirm);

        //Initialize places
        Places.initialize(getApplicationContext(),"AIzaSyDq2JY20nHKZVof_c5MbZLdRK63L7stew0");


        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etSource.getText().toString().equals("") && (etDestination.getText().toString().equals(""))) {
                    //When both value blank
                    Toast.makeText(MAP.this, "Enter Both Location", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MAP.this, "Hello", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Set EditText on focusable for source
        etSource.setFocusable(false);
        etSource.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                    //Initialize place field list
                    List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS
                            ,Place.Field.LAT_LNG,Place.Field.NAME);
                    //Create intent
                    Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY
                            ,fieldList).build(MAP.this);

                    //Start activity result
                    startActivityForResult(intent,100);
                    et = "etSource";
            }
        });

        //Set EditText non focusable for Destination
        etDestination.setFocusable(false);
        etDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Initialize place field list
                List<Place.Field> fieldList2 = Arrays.asList(Place.Field.ADDRESS
                        ,Place.Field.LAT_LNG,Place.Field.NAME);

                //Create intent
                Intent intent2 = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY
                        ,fieldList2).build(MAP.this);

                //Start activity result
                startActivityForResult(intent2,200);
                Toast.makeText(MAP.this, "You have clicked on etDestination", Toast.LENGTH_SHORT).show();
                et = "etDestination";
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (et == "etSource" && resultCode == RESULT_OK){
            //When success
            //Initialize place
            Place place = Autocomplete.getPlaceFromIntent(data);
            //Set Address on EditText

            etSource.setText(place.getAddress());
            etSource.setText(String.format(place.getName()));
            //etSource.setText(String.valueOf(place.getLatLng()));

            //Toast.makeText(this, "Lat/Lng"+place.getLatLng(), Toast.LENGTH_SHORT).show();

            etSourceLat = String.valueOf(place.getLatLng().latitude);
            etSourceLong = String.valueOf(place.getLatLng().longitude);
//            String etSourceAddress = place.getAddress();
//
            Toast.makeText(this, ""+etSourceLat, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, ""+etSourceLong, Toast.LENGTH_SHORT).show();

            LatLng latLng = new LatLng(Double.parseDouble(etSourceLat), Double.parseDouble(etSourceLong));
            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Source Location");

            status = "true";
            if(status == "true")
            {
                fetchLastLocation();
                et = "";
            }


        }
        else if(et == "etDestination" && resultCode == RESULT_OK)
        {
            //When success
            //Initialize place
            Place place = Autocomplete.getPlaceFromIntent(data);
            etDestination.setText(place.getAddress());
            etDestination.setText(String.format(place.getName()));
//            etDestination.setText(String.valueOf(place.getLatLng()));
            etDestinationLat = String.valueOf(place.getLatLng().latitude);
            etDestinationLong = String.valueOf(place.getLatLng().longitude);

            LatLng latLng1 = new LatLng(Double.parseDouble(etDestinationLat), Double.parseDouble(etDestinationLong));
            MarkerOptions markerOptions1 = new MarkerOptions().position(latLng1).title("Destination Location");

            status = "destination";
            if(status == "destination")
            {
                fetchLastLocation();
                et = "";
            }
        }
        else if (resultCode == AutocompleteActivity.RESULT_ERROR){
            //When error
            //Initialize status
            Status status = Autocomplete .getStatusFromIntent(data);
            //Display Toast
            Toast.makeText(getApplicationContext(),status.getStatusMessage()
                    ,Toast.LENGTH_SHORT).show();
        }
    }


    private void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    Toast.makeText(getApplicationContext(), currentLocation.getLatitude()
                            + "" + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();

                    SupportMapFragment supportMapFragment = (SupportMapFragment)
                            getSupportFragmentManager().findFragmentById(R.id.google_map);
                    supportMapFragment.getMapAsync(MAP.this::onMapReady);
                }
            }
        });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
//FOR CURRENT LOCATION
            if(status == "false")
            {
                LatLng latlng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                MarkerOptions markerOptions = new MarkerOptions().position(latlng).title("I am here");
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latlng));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15));
                googleMap.addMarker(markerOptions);
            }
//FOR SOURCE
            else if(status == "true")
            {
                LatLng latlng = new LatLng(Double.parseDouble(etSourceLat), Double.parseDouble(etSourceLong));
                MarkerOptions markerOptions = new MarkerOptions().position(latlng).title("I am here");
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latlng));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15));
                googleMap.addMarker(markerOptions);
            }
//FOR DESTINATION
            else if(status == "destination")
            {
                LatLng latlng = new LatLng(Double.parseDouble(etDestinationLat), Double.parseDouble(etDestinationLong));
                MarkerOptions markerOptions = new MarkerOptions().position(latlng).title("I am here");
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latlng));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15));
                googleMap.addMarker(markerOptions);
            }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLastLocation();
                }
                break;
        }
    }
}