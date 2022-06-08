package com.example.locinder;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locinder.Adapter.OrganizerAdapter;
import com.example.locinder.Model.OrganizeModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Organizer extends AppCompatActivity {

    private RecyclerView recyclerview;

    private List<OrganizeModel> userList;
    private OrganizerAdapter userAdapter;

    private DatabaseReference userRef;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer);

        //link organizer.item and activity_orgnizer and organizerAdapter

        recyclerview=findViewById(R.id.recylerView);
        progressBar=findViewById(R.id.load1);


        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerview.setLayoutManager(layoutManager);

        userList= new ArrayList<>();


        userAdapter=new OrganizerAdapter(Organizer.this, (ArrayList<OrganizeModel>) userList);

        recyclerview.setAdapter(userAdapter);


        userRef= FirebaseDatabase.getInstance().getReference("Locinder");

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                userList.clear();

                for(DataSnapshot ds: snapshot.getChildren()){

                   OrganizeModel user= ds.getValue(OrganizeModel.class);
                    userList.add(user);

                }
                userAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);




            }

            @Override
            public void onCancelled(DatabaseError error) {


                Toast.makeText(Organizer.this,error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });




    }
}