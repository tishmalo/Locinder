package com.example.locinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.daprlabs.cardstack.SwipeDeck;
import com.example.locinder.Adapter.DeckAdapter;
import com.example.locinder.Model.CourseModal;
import com.example.locinder.Model.CourseModel2;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.realm.Realm;

public class Locale extends AppCompatActivity {


    private DrawerLayout drawerLayout;
    private Toolbar toolBar;
    private SwipeDeck cardStack;

    private ArrayList<CourseModel2> courseModalArrayList2;
    private NavigationView nav_view;
    private Realm realm;
    private TextView location, description,latitude,longitude;



    private FirebaseAuth mAuth;
    private DatabaseReference userDatabaseRef;

    private DatabaseReference userRef;
    private DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locale);




        toolBar = (Toolbar) findViewById(R.id.toolBar2);
        setSupportActionBar(toolBar);
        getSupportActionBar().setTitle("LOCINDER");
        nav_view = findViewById(R.id.navigationBar1);
        drawerLayout = findViewById(R.id.drawerLayout1);
        location = findViewById(R.id.destination);

        description = findViewById(R.id.description);

        courseModalArrayList2=new ArrayList<>();

        cardStack =findViewById(R.id.swipe_deck);


        // on below line we are adding data to our array list.


        // on below line we are creating a variable for our adapter class and passing array list to it.
        final DeckAdapter adapter = new DeckAdapter(courseModalArrayList2, this);


        // on below line we are setting adapter to our card stack.
        cardStack.setAdapter(adapter);
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Distance");
        Query query= rootRef.orderByChild("distance").startAt(0).endAt(50);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
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


        cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {
                Toast.makeText(Locale.this, "Card Swiped Left", Toast.LENGTH_SHORT).show();


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
                Toast.makeText(Locale.this, "No more cards", Toast.LENGTH_SHORT).show();
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
}