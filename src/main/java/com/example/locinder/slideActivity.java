package com.example.locinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.locinder.Model.CourseModel2;
import com.google.common.base.Stopwatch;



import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.daprlabs.cardstack.SwipeDeck;
import com.example.locinder.Adapter.DeckAdapter;
import com.example.locinder.Model.CourseModal;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.realm.Realm;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class slideActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private Toolbar toolBar;
    private SwipeDeck cardStack;
    private ArrayList<CourseModal> courseModalArrayList;
    private ArrayList<CourseModel2> courseModalArrayList2;
    private NavigationView nav_view;
    private Realm realm;
    private TextView location, description,latitude,longitude;
    private static int loop = 0;
    //Taking this to have loop

    private FirebaseAuth mAuth;
    private DatabaseReference userDatabaseRef;

    private DatabaseReference userRef;
    private DatabaseReference ref;


    private LocationRequest locationRequest;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);





        toolBar = (Toolbar) findViewById(R.id.toolBar2);
        setSupportActionBar(toolBar);
        getSupportActionBar().setTitle("LOCINDER");
        nav_view = findViewById(R.id.navigationBar1);
        drawerLayout = findViewById(R.id.drawerLayout1);
        location = findViewById(R.id.destination);

        description = findViewById(R.id.description);

        courseModalArrayList2=new ArrayList<>();
        courseModalArrayList = new ArrayList<>();
        cardStack =findViewById(R.id.swipe_deck);


        // on below line we are adding data to our array list.


        // on below line we are creating a variable for our adapter class and passing array list to it.
        final DeckAdapter adapter = new DeckAdapter(courseModalArrayList2, this);


        // on below line we are setting adapter to our card stack.
        cardStack.setAdapter(adapter);
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Distance");

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                        courseModalArrayList2.clear();

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        CourseModel2 user = dataSnapshot.getValue(CourseModel2.class);
                        courseModalArrayList2.add(user);
                    }



                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        nav_view.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(slideActivity.this, drawerLayout, toolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {
                Toast.makeText(slideActivity.this, "Card Swiped Left", Toast.LENGTH_SHORT).show();


            }


            @Override
            public void cardSwipedRight(int position) {

                LikePhotos(position);


            }

            private DatabaseReference ref;

            private void LikePhotos(int position) {


                Object selectItemObj = adapter.getItem(position);

                String Location, descriptionz, photoz, rating, review, coordinates,lat, Long;
                Double distance;

                rating = "";
                review = "";
                coordinates = "";
                lat= "";
                Long="";
                distance=0.000;



                String CurrentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
               Location = courseModalArrayList2.get(position).getlocations();
              descriptionz = courseModalArrayList2.get(position).getdescription();
               photoz = courseModalArrayList2.get(position).getphoto();
               DatabaseReference tish = FirebaseDatabase.getInstance().getReference("fav");


               CourseModel2 cm = new CourseModel2(coordinates,CurrentUserId,descriptionz,lat,Location,Long,photoz,rating,review,distance);
               tish.child(CurrentUserId).push().setValue(cm);





            }


            @Override
            public void cardsDepleted() {
                Toast.makeText(slideActivity.this, "No more cards", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void cardActionDown() {
                Log.i("TAG", "CARDS MOVED DOWN");

            }

            @Override
            public void cardActionUp() {
                Log.i("TAG", "CARDS MOVED UP");

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        startLocationUpdates();
        change();

        calculateTime();

    }

    private void calculateTime() {

        Stopwatch timer = Stopwatch.createStarted();
        change();
        Log.i("TAG","Method took: " + timer.stop());


    }

    private void change() {


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (isGPSEnabled()) {

                    startLocationUpdates();

                } else {

                    turnOnGPS();
                }
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {

                startLocationUpdates();
            }
        }
    }

    private void startLocationUpdates() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(slideActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                if (isGPSEnabled()) {
                    String CurrentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                    ref = FirebaseDatabase.getInstance().getReference()
                            .child("Locations").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                    LocationServices.getFusedLocationProviderClient(slideActivity.this)
                            .requestLocationUpdates(locationRequest, new LocationCallback() {
                                @Override
                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                    super.onLocationResult(locationResult);

                                    LocationServices.getFusedLocationProviderClient(slideActivity.this)
                                            .removeLocationUpdates(this);

                                    if (locationResult != null && locationResult.getLocations().size() >0){

                                        int index = locationResult.getLocations().size() - 1;
                                        final String latitude = String.valueOf(locationResult.getLocations().get(index).getLatitude());
                                        final String longitude = String.valueOf(locationResult.getLocations().get(index).getLongitude());
                                        String currentLocation=String.valueOf(latitude)+", "+String.valueOf(longitude);

                                        HashMap userInfo = new HashMap();
                                        userInfo.put("id", CurrentUserId);
                                        userInfo.put("Lat", latitude);
                                        userInfo.put("Long", longitude);


                                        ref.updateChildren(userInfo).addOnCompleteListener(new OnCompleteListener() {
                                            @Override
                                            public void onComplete(@NonNull Task task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(slideActivity.this,"Location captured",Toast.LENGTH_SHORT);
                                                }
                                                else {
                                                    Toast.makeText(slideActivity.this,task.getException().toString(),Toast.LENGTH_SHORT);
                                                }

                                            }
                                        });

                                    }

                                }

                            }, Looper.getMainLooper());


                } else {
                    turnOnGPS();
                }

            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }


    }

    private void turnOnGPS() {

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    Toast.makeText(slideActivity.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(slideActivity.this, 2);
                            } catch (IntentSender.SendIntentException ex) {
                                ex.printStackTrace();
                            }
                            break;

                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            //Device does not have location
                            break;
                    }
                }
            }
        });
    }

    private boolean isGPSEnabled() {

        LocationManager locationManager = null;
        boolean isEnabled = false;

        if (locationManager == null) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }

        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnabled;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.favorite:
                Intent intent = new Intent(slideActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.message1:
                Intent intent2 = new Intent(slideActivity.this, ShowChats.class);
                startActivity(intent2);
                break;
            case R.id.plan:
                Intent intent3 = new Intent(slideActivity.this, Organizer.class);
                startActivity(intent3);
                break;
            case R.id.show:
                Intent intent4 = new Intent(slideActivity.this, Events.class);
                startActivity(intent4);
                break;

            case R.id.locale:
                Intent intent6 = new Intent(slideActivity.this, Locale.class);
                startActivity(intent6);
                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent5 = new Intent(slideActivity
                        .this, LoginActivity.class);
                startActivity(intent5);

                Toast.makeText(slideActivity.this,"SIGNED OUT",Toast.LENGTH_SHORT).show();
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }
}