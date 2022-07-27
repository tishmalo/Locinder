package com.example.locinder.Presentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.daprlabs.cardstack.SwipeDeck;
import com.example.locinder.Adapter.EventsAdapter;
import com.example.locinder.Model.TripModel;
import com.example.locinder.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Events extends AppCompatActivity {

    private SwipeDeck cardStack;
    private Toolbar toolbar;
    ArrayList<TripModel> courseModalArrayList;
    private DatabaseReference ref;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);


        /**
         * Link java to xml
         */
        cardStack= findViewById(R.id.swipe_deck2);
        toolbar= findViewById(R.id.toolBar22);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("EVENTS");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        courseModalArrayList= new ArrayList<TripModel>();

        /**
         * Set new adapter to this activity, that is, the swipeDeck
         */
        EventsAdapter adapter= new EventsAdapter(courseModalArrayList,this);



        /**
         * Retrieve Data from the firebase through the pojo class
         */

        ref= FirebaseDatabase.getInstance().getReference("Excursions");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        TripModel  user = ds.getValue(TripModel.class);

                        courseModalArrayList.add(user);
                    }
                    cardStack.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        /**
         * The data is fetched, now the action based on the motion of the cards is implemented
         */

        cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {

                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds: snapshot.getChildren()){
                            String place= courseModalArrayList.get(position).getlocation();
                            String places2= ds.child("location").getValue().toString();

                            if(place.equals(places2)){
                                ds.getRef().removeValue();
                                Toast.makeText(Events.this, "Event cancelled", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




            }

            @Override
            public void cardSwipedRight(int position) {
                String  location, date,photo;
                String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                      location=courseModalArrayList.get(position).getlocation();
                      date=courseModalArrayList.get(position).getdate();
                      photo=courseModalArrayList.get(position).getphoto();
                    String people="";
                    String price="";
                      ref=FirebaseDatabase.getInstance().getReference("ChoseTrips");
                      TripModel tp= new TripModel(currentUser,location,date,photo,people,price);
                      ref.push().setValue(tp);





            }

            @Override
            public void cardsDepleted() {

            }

            @Override
            public void cardActionDown() {

            }

            @Override
            public void cardActionUp() {

            }
        });

    }
}