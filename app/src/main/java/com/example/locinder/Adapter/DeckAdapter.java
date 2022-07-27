package com.example.locinder.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.locinder.Fragments.Reviews;
import com.example.locinder.Model.CourseModel2;
import com.example.locinder.R;
import com.example.locinder.moreInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DeckAdapter extends BaseAdapter  {

    // on below line we have created variables
    // for our array list and context.
    private ArrayList<CourseModel2> courseData;
    private Context context;
    private ImageView image;

    // on below line we have created constructor for our variables.
    public DeckAdapter(ArrayList<CourseModel2> courseData, Context context) {
        this.courseData = courseData;
        this.context = context;
    }

    @Override
    public int getCount() {
        // in get count method we are returning the size of our array list.
        return courseData.size();
    }

    @Override
    public Object getItem(int position) {
        // in get item method we are returning the item from our array list.
        return courseData.get(position);
    }

    @Override
    public long getItemId(int position) {
        // in get item id we are returning the position.
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        // in get view method we are inflating our layout on below line.
        View v = convertView;
        if (v == null) {
            // on below line we are inflating our layout.
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_rv_item, parent, false);
        }
        // on below line we are initializing our variables and setting data to our variables.
        ((TextView) v.findViewById(R.id.description)).setText(courseData.get(position).getdescription());
        ((TextView) v.findViewById(R.id.destination)).setText(courseData.get(position).getlocations());
        ((TextView) v.findViewById(R.id.price)).setText(courseData.get(position).getprice());
        ((TextView) v.findViewById(R.id.distance)).setText(courseData.get(position).getdistance()+ "KM");

        Picasso.get()
               .load(courseData.get(position).getphoto())
              .fit()
                .into((ImageView) v.findViewById(R.id.idIVCourse));

        v.setLongClickable(true);
        v.setClickable(true);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Location = courseData.get(position).getlocations();



                Intent intent=new Intent(context, moreInfo.class);

                intent.putExtra("Location", Location);
                context.startActivity(intent);

            }
        });
        v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "Long Clicked", Toast.LENGTH_SHORT).show();
                return false;
            }
        });




        return v;


    }



}
