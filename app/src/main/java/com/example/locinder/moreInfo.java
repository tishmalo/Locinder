package com.example.locinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.locinder.Adapter.ReviewAdapter;
import com.example.locinder.Adapter.shereheAdapter;
import com.example.locinder.Fragments.Reviews;
import com.example.locinder.Model.CourseModal;
import com.example.locinder.Model.ReviewHelper;
import com.example.locinder.Model.ShereheModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class moreInfo extends AppCompatActivity {

    FrameLayout f1,f2;
    private TextView price;
    Toolbar toolbar;
    String title;
    ReviewAdapter userAdapter;
    List<ReviewHelper> userList;
    List<ShereheModel> list;
    RecyclerView photos,mesorecycler;
    shereheAdapter adapter;
    com.google.android.material.floatingactionbutton.FloatingActionButton direction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);

        toolbar= findViewById(R.id.toolBar22);

        openText();

        Intent intent=getIntent();
        title=intent.getStringExtra("Location");


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        mesorecycler= findViewById(R.id.reviews);
        mesorecycler.setHasFixedSize(true);
        mesorecycler.setLayoutManager(new LinearLayoutManager(this));
        photos= findViewById(R.id.photos);


        photos.setHasFixedSize(true);
        photos.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false));
        
        list= new ArrayList<>();
        adapter=new shereheAdapter(moreInfo.this, (ArrayList<ShereheModel>)list);
        showPhotos();
        
        
        userList= new ArrayList<>();
        userAdapter=new ReviewAdapter(moreInfo.this, (ArrayList<ReviewHelper>) userList);

        readUsers();

        direction=findViewById(R.id.direction);

        direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1= new Intent(moreInfo.this, MapsActivity.class);
                intent1.putExtra("Location",title);
                moreInfo.this.startActivity(intent1);

            }
        });



    }

    private void openText() {




    }

    private void showPhotos() {

        DatabaseReference reference;
        reference= FirebaseDatabase.getInstance().getReference("Attraction");
        Query v= reference.orderByChild("locations").equalTo(title);
        v.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot ds: snapshot.getChildren()){
                    ShereheModel cm= ds.getValue(ShereheModel.class);
                    list.add(cm);


                }
                photos.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void readUsers() {

        DatabaseReference userBase;

        userBase= FirebaseDatabase.getInstance().getReference().child("reviews");

        //retrieve data from only one location-common sense people

        userBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("reviews");




                Query query= reference.orderByChild("location").equalTo(title);

                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        userList.clear();
                        for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                            ReviewHelper user=dataSnapshot.getValue(ReviewHelper.class);
                            userList.add(user);

                        }

                        mesorecycler.setAdapter(userAdapter);


                        if(userList.isEmpty()){
                            Toast.makeText(moreInfo.this,"No Reviews found",Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        Reviews a= new Reviews();
        Bundle bundle=new Bundle();
        bundle.putString("Location", title);
        a.setArguments(bundle);

    }
}