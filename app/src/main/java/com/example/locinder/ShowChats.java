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

import com.example.locinder.Adapter.ChatAdapter;
import com.example.locinder.Adapter.OrganizerAdapter;
import com.example.locinder.Model.CourseModel2;
import com.example.locinder.Model.OrganizeModel;
import com.example.locinder.Model.TripModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowChats extends AppCompatActivity {
    private RecyclerView recyclerview;

    private List<TripModel> userList;
    private List<TripModel> chatUsers;
    private ChatAdapter userAdapter;

    private DatabaseReference userRef;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_chats);

        /**
         * link java to xml
         */

        progressBar= findViewById(R.id.load1);
        recyclerview=findViewById(R.id.recylerView);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerview.setLayoutManager(layoutManager);

        userList= new ArrayList<>();

        setUpItemTouchHelper();







        /**
         * Link to firebase NB remember to get the user id
         */
        userRef= FirebaseDatabase.getInstance().getReference("ChoseTrips");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for(DataSnapshot ds: snapshot.getChildren()){
                    TripModel user= ds.getValue(TripModel.class);
                    userList.add(user);
                }
                userAdapter= new ChatAdapter(ShowChats.this, (ArrayList<TripModel>)userList);
                recyclerview.setAdapter(userAdapter);
                ChatList();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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

                String deletedItems= userList.get(viewHolder.getAdapterPosition()).getlocation();
                userRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot ds: snapshot.getChildren()){

                            String location= ds.child("location").getValue().toString();

                            if (location.equals(deletedItems)){

                                userList.remove(viewHolder.getAdapterPosition());

                                userAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());

                                ds.getRef().removeValue();

                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



                int position=viewHolder.getAdapterPosition();



            }

        }).attachToRecyclerView(recyclerview);

    }

    /**
     * show all the chats from the database matching the description.
     */


    private void ChatList() {

        /**
         *
         */

/**
 * The most compex part in this module. Compare date and time in both chosen trip and Excursions databases
 */



    }
}