package com.example.locinder;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.example.locinder.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.android.PolyUtil;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.TravelMode;

import org.joda.time.DateTime;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private static final int overview = 0;

    private String Latitude, Longitude, Location, LocationAddress2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent= getIntent();
        Location= intent.getStringExtra("Location");

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DatabaseReference ref;
        ref=FirebaseDatabase.getInstance().getReference("Locinder");
        Query b= ref.orderByChild("LOCATIONS").equalTo(Location);
        b.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    Latitude= ds.child("COORDINATES").getValue().toString();
                    //Longitude=ds.child("Long").getValue().toString();

                  //  String LocationAddress= Latitude + "," + Longitude;
                   // LocationAddress2=LocationAddress.replace("\"", "");


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    public DirectionsResult getDirectionsDetails(String origin, String destination, TravelMode mode){



        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        DateTime now = new DateTime();
        try {
            return DirectionsApi.newRequest(getGeoContext())
                    .mode(mode)
                    .origin(origin)
                    .destination(destination)
                    .departureTime(now)
                    .await();
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {

        DatabaseReference reference;

        reference= FirebaseDatabase.getInstance().getReference("Locations");

        Query z= reference.orderByChild("id").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        z.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds: snapshot.getChildren()){

                    String Lat=ds.child("coordinates").getValue().toString();
                   // String Long=ds.child("Long").getValue().toString();

               //  String myLocation= Lat+ "," + Long;
                // String myLocation2=myLocation.replace("\"", "");

                    setupGoogleMapScreenSettings(googleMap);

                    DirectionsResult results= getDirectionsDetails(Lat,Latitude,TravelMode.DRIVING);
                    if(results!=null){

                        addPolyline(results, googleMap);
                        positionCamera(results.routes[overview], googleMap);
                        addMarkersToMap(results, googleMap);
                    }

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        



    }


    private String getEndLocationTitle(DirectionsResult results){
        return  "Time :"+ results.routes[overview].legs[overview].duration.humanReadable + " Distance :" + results.routes[overview].legs[overview].distance.humanReadable;
    }
    private void addPolyline(DirectionsResult results, GoogleMap mMap) {
        List<LatLng> decodedPath = PolyUtil.decode(results.routes[overview].overviewPolyline.getEncodedPath());
        mMap.addPolyline(new PolylineOptions().addAll(decodedPath));
    }

    private void positionCamera(DirectionsRoute route, GoogleMap googleMap) {


        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(route.legs[overview].startLocation.lat, route.legs[overview].startLocation.lng), 12));
    }

    private void setupGoogleMapScreenSettings(GoogleMap googleMap) {

        googleMap.setBuildingsEnabled(true);
        googleMap.setIndoorEnabled(true);
        googleMap.setTrafficEnabled(true);
        UiSettings mUiSettings = googleMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setCompassEnabled(true);
        mUiSettings.setMyLocationButtonEnabled(true);
        mUiSettings.setScrollGesturesEnabled(true);
        mUiSettings.setZoomGesturesEnabled(true);
        mUiSettings.setTiltGesturesEnabled(true);
        mUiSettings.setRotateGesturesEnabled(true);
    }

    private void addMarkersToMap(DirectionsResult results, GoogleMap mMap) {
        int overview = 0;
        mMap.addMarker(new MarkerOptions().position(new LatLng(results.routes[overview].legs[overview].startLocation.lat,results.routes[overview].legs[overview].startLocation.lng)).title(results.routes[overview].legs[overview].startAddress));
        mMap.addMarker(new MarkerOptions().position(new LatLng(results.routes[overview].legs[overview].endLocation.lat,results.routes[overview].legs[overview].endLocation.lng)).title(results.routes[overview].legs[overview].startAddress).snippet(getEndLocationTitle(results)));
    }
    private GeoApiContext getGeoContext() {
        GeoApiContext geoApiContext = new GeoApiContext();
        return geoApiContext
                .setQueryRateLimit(3)
                .setApiKey(getString(R.string.directionsApiKey))
                .setConnectTimeout(1, TimeUnit.SECONDS)
                .setReadTimeout(1, TimeUnit.SECONDS)
                .setWriteTimeout(1, TimeUnit.SECONDS);
    }




}