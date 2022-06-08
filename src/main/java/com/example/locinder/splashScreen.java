package com.example.locinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.locinder.Model.CourseModal;
import com.example.locinder.Model.CourseModel2;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class splashScreen extends AppCompatActivity {

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(splashScreen.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },4000);

    }

    @Override
    protected void onStart() {
        super.onStart();

         DatabaseReference userRef= FirebaseDatabase.getInstance().getReference("Locinder");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {



                for(DataSnapshot dt: snapshot.getChildren()){

                    String Lat= dt.child("Lat").getValue().toString();
                    String Long=dt.child("Long").getValue().toString();
                    String Location=dt.child("LOCATIONS").getValue().toString();
                    String Description=dt.child("DESCRIPTION").getValue().toString();
                    String photo=dt.child("PHOTO").getValue().toString();


                    DatabaseReference ref;

                    ref= FirebaseDatabase.getInstance().getReference(
                            "Locations");
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds : snapshot.getChildren()) {


                                String Lat2 = ds.child("Lat").getValue().toString();
                                String Long2 = ds.child("Long").getValue().toString();

                                final Double R = 6371.0088;

                                Double Latitude1 = Double.parseDouble(Lat);
                                Double Longitude1 = Double.parseDouble(Long);
                                Double Latitude2 = Double.parseDouble(Lat2);
                                Double Longitude2 = Double.parseDouble(Long2);


                                Double LatDistance = Math.toRadians(Latitude1 - Latitude2);
                                Double LongDistance = Math.toRadians(Longitude1 - Longitude2);

                                Double a = Math.sin(LatDistance / 2) * Math.sin(LatDistance / 2) +
                                        Math.cos(Math.toRadians(Latitude1)) * Math.cos(Math.toRadians(Latitude2)) *
                                                Math.sin(LongDistance / 2) * Math.sin(LongDistance / 2);
                                Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
                                Double distance = R * c;



                                DatabaseReference fer;
                                fer= FirebaseDatabase.getInstance().getReference("Distance");

                                fer.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                        if(!snapshot.exists()) {
                                            DatabaseReference tish;
                                            tish = FirebaseDatabase.getInstance().getReference("Distance");
                                            String CurrentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                            String rating = "";
                                            String review = "";
                                            String coordinates = "";


                                            CourseModel2 cm = new CourseModel2(coordinates,CurrentUserId,Description,Lat,Location,Long,photo,rating,review,distance);
                                            tish.push().setValue(cm);

                                        }



                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });


                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}