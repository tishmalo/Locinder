package com.example.locinder.Presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.locinder.Model.TripModel;
import com.example.locinder.Organizer;
import com.example.locinder.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class FinishOrganizing extends AppCompatActivity {

    private EditText location;
    private TextView date, photo;
    private Button setup;
    private String title="";
    private DatabaseReference ref;
    DatePickerDialog.OnDateSetListener setListener;
    EditText people, price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_organizing);

        /***
         Link java to xml: front-end to back-end

         */
        people= findViewById(R.id.people);
        price=findViewById(R.id.price);

        location=findViewById(R.id.chooselocation);
        date=findViewById(R.id.choosedate);
        photo=findViewById(R.id.choosePhoto);
        setup=findViewById(R.id.setup);


        if(getIntent().getExtras()!=null){

            /**
             pass data to the location textview....will be useful in sorting the data.Better algorithm needed
             The second parameter below is the default string returned if the value is not there.
             */
            //
            title=getIntent().getStringExtra("locations");
            String photoz= getIntent().getStringExtra("photo");


            photo.setText(photoz);
            location.setText(title);


        }
        /**
         Convert the textView to pick a day for the planned excursion
         */
        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);



        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(FinishOrganizing.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                datePickerDialog.show();



            }
        });

        setListener= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                month= month+1;
                String fullDate= day+"/"+month+"/"+year;

                date.setText(fullDate);

                /**
                 *Implmenent firebase and set the event to database...Let's gooo
                 * First let's set Onclick event on button
                 *
                 */
            setup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    ref= FirebaseDatabase.getInstance().getReference("Excursions");
                    String CurrentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    String people2= people.getText().toString().trim();
                    String price2= price.getText().toString().trim();
                    final String finalLocation= location.getText().toString().trim();
                    final String finalDate= fullDate.trim();
                    final String finalPhoto= photo.getText().toString().trim();

                    TripModel user=new TripModel(CurrentUserId,finalLocation,finalDate,finalPhoto,people2, price2);

                    ref.push().setValue(user);
                    Toast.makeText(FinishOrganizing.this, "Trip booked", Toast.LENGTH_SHORT).show();

                    Intent intent= new Intent(FinishOrganizing.this, Organizer.class);
                    startActivity(intent);
                    finish();


                }
            });

            }
        };





    }
}