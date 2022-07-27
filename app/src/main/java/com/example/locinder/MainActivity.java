package com.example.locinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.locinder.Adapter.TravelAdapter;
import com.example.locinder.Model.CourseModal;
import com.example.locinder.Model.CourseModel2;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerview;

    private List<CourseModel2> userList;
    private TravelAdapter userAdapter;

    private DatabaseReference userRef;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerview=findViewById(R.id.recylerView);
        String mCurrentUserId=FirebaseAuth.getInstance().getCurrentUser().getUid();
        progressBar=findViewById(R.id.load1);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerview.setLayoutManager(layoutManager);

        userList= new ArrayList<>();


        userAdapter=new TravelAdapter(MainActivity.this, (ArrayList<CourseModel2>) userList);

        recyclerview.setAdapter(userAdapter);


        setUpItemTouchHelper();

        userRef= FirebaseDatabase.getInstance().getReference().child("fav").child(
                FirebaseAuth.getInstance().getCurrentUser().getUid());



        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                userList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){

                    CourseModel2 user = dataSnapshot.getValue(CourseModel2.class);
                    userList.add(user);


                }
                //userList.clear();
                userAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);

                if(userList.isEmpty()){
                    Toast.makeText(MainActivity.this,"No Wish Location found",Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void setUpItemTouchHelper() {





        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                String deletedItems= userList.get(viewHolder.getAdapterPosition()).getlocations();


                userRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (!snapshot.exists()) {

                        } else {

                            for (DataSnapshot ds : snapshot.getChildren()) {

                                String location = ds.child("locations").getValue().toString();

                                if (location.equals(deletedItems)) {

                                    userList.remove(viewHolder.getAdapterPosition());

                                    userAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());

                                    ds.getRef().removeValue();

                                }

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

        }).attachToRecyclerView(recyclerview);




    }
}