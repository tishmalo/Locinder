package com.example.locinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.locinder.Adapter.ReviewAdapter;
import com.example.locinder.Adapter.TravelAdapter;
import com.example.locinder.Model.CourseModal;
import com.example.locinder.Model.ReviewHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
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

public class ReviewActivity extends AppCompatActivity {

    private RecyclerView recyclerview;

    private List<ReviewHelper> userList;
    private ReviewAdapter userAdapter;
    private EditText review;
    private ImageButton send;
    private RatingBar ratingBar;
    private TextView location2;

    private FirebaseAuth mAuth;
    private DatabaseReference userDatabaseRef;
    private DatabaseReference userBase;
    private ProgressBar progressBar;
    private String title="";
    private Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        review= findViewById(R.id.text_send);
        send=findViewById(R.id.btn_send);

        progressBar=findViewById(R.id.load1);



        recyclerview=findViewById(R.id.mesorecycler);

        toolBar=findViewById(R.id.mesotoolbar);


        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       toolBar.setNavigationOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               finish();

           }
       });

        String mCurrentUserId= FirebaseAuth.getInstance().getCurrentUser().getUid();

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerview.setLayoutManager(layoutManager);

        userList= new ArrayList<>();


        userAdapter=new ReviewAdapter(ReviewActivity.this, (ArrayList<ReviewHelper>) userList);

        recyclerview.setAdapter(userAdapter);

        //pass data to the location textview....will be useful in sorting the data.Better algorithm needed

      //  Intent i = getIntent();
//The second parameter below is the default string returned if the value is not there.
     //   String txtData = i.getExtras().getString("locations","");
     //   location2.setText(txtData);
        if(getIntent().getExtras()!=null){
            title=getIntent().getStringExtra("locations");
            getSupportActionBar().setTitle(title);


            readUsers();



        }


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myrating= "";
                final String review31=review.getText().toString().trim();


                mAuth=FirebaseAuth.getInstance();


                String CurrentUserId = mAuth.getCurrentUser().getEmail();
                userDatabaseRef = FirebaseDatabase.getInstance().getReference("reviews");

                ReviewHelper cm = new ReviewHelper(CurrentUserId,myrating,review31,title);
                userDatabaseRef.push().setValue(cm);;

                finish();


            }
        });





    }

    private void readUsers() {


        userBase= FirebaseDatabase.getInstance().getReference().child("reviews").child(
                FirebaseAuth.getInstance().getCurrentUser().getUid());

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
                        userAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);

                        if(userList.isEmpty()){
                            Toast.makeText(ReviewActivity.this,"No Reviews found",Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
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
}