package com.example.locinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ControlActivity extends AppCompatActivity {

    DatabaseReference ref;

    DatabaseReference userRef;
    private TextView control;
    Button check;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        String currentuserid= FirebaseAuth.getInstance().getCurrentUser().getUid();

        control= findViewById(R.id.distance);
        String title= "Did it";
        check= findViewById(R.id.check);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * Get data from firebase
                 */


                if(getIntent().getExtras()!=null) {


                    String Lat = getIntent().getStringExtra("Lat");
                    String Long = getIntent().getStringExtra("Long");


                    control.setText(Lat);

                    /**
                     * Get data from the Locations firebase
                     *
                     */


                    userRef=FirebaseDatabase.getInstance().getReference("Locations");
                    userRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {


                            String Lat2= snapshot.child("Lat").getValue().toString();

                            String Long2= snapshot.child("Long").getValue().toString();


                            /**
                             * Initialize the variables
                             */



                            final Double R = 6371.0088;

                            Double Latitude1= Double.parseDouble(Lat);
                            Double Longitude1= Double.parseDouble(Long);
                            Double Latitude2= Double.parseDouble(Lat2);
                            Double Longitude2= Double.parseDouble(Long2);


                            Double LatDistance= Math.toRadians(Latitude1-Latitude2);
                            Double LongDistance= Math.toRadians(Longitude1-Longitude2);

                            Double a = Math.sin(LatDistance / 2) * Math.sin(LatDistance / 2) +
                                    Math.cos(Math.toRadians(Latitude1)) * Math.cos(Math.toRadians(Latitude2)) *
                                            Math.sin(LongDistance/ 2) * Math.sin(LongDistance/ 2);
                            Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
                            Double distance = R * c;



                            Toast.makeText(ControlActivity.this, "The distance between two lat and long is::" + distance, Toast.LENGTH_SHORT).show();



                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



                }


            }
        });






    }
}