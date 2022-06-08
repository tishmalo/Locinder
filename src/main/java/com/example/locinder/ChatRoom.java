package com.example.locinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.example.locinder.Adapter.ChatAdapter;
import com.example.locinder.Adapter.MessageAdapter;
import com.example.locinder.Model.ChatMessage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatRoom extends AppCompatActivity {

    private FloatingActionButton fab;
    private Toolbar toolbar;
    private String title="";
    List<ChatMessage> chatList;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);


        fab= findViewById(R.id.fab);

        /**
         * Retrieve text from showChats activity. It will make sense later.
         */


        if(getIntent().getExtras()!=null){

            /**
             pass data to the location textview....will be useful in sorting the data.Better algorithm needed
             The second parameter below is the default string returned if the value is not there.
             */
            //
            final String location1 =getIntent().getStringExtra("location");
            final String date1= getIntent().getStringExtra("date");

            title= location1 + "" + date1;




        }

        toolbar= findViewById(R.id.toolBar22);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        /**
         * Set onclick event on the floating button to save data
         */


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText)findViewById(R.id.input);
                final String location2 =getIntent().getStringExtra("location");
                final String date2= getIntent().getStringExtra("date");
                final String currentuser= FirebaseAuth.getInstance().getCurrentUser().getEmail();


                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database
                FirebaseDatabase.getInstance()
                        .getReference("Message")

                        .push()
                        .setValue(new ChatMessage(input.getText().toString(),
                                         currentuser
                                       ,location2.toString()
                                        ,date2.toString()

                                            )
                        );

displayMessage();
                // Clear the input
                input.setText("");
            }
        });


    }

    private void displayMessage() {

        /**
         * Retrieve data from firebase to Listview
         *
         * Get the text from the intent event from the sho chats activity
         *
         */

        final String location2 =getIntent().getStringExtra("location");
        final String date2= getIntent().getStringExtra("date");

        chatList=new ArrayList<>();
        ListView listOfMessages = (ListView)findViewById(R.id.list_of_messages);
        ref=FirebaseDatabase.getInstance().getReference("Message");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatList.clear();

                for (DataSnapshot ds: snapshot.getChildren()){
                    ChatMessage user= ds.getValue(ChatMessage.class);
                    if(ds.child("location").getValue().equals(location2)&&ds.child("date").getValue().equals(date2)){

                        chatList.add(user);
                    }


                   MessageAdapter adapter= new MessageAdapter(ChatRoom.this, chatList);

                   listOfMessages.setAdapter(adapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}