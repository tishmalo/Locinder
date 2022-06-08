package com.example.locinder.Adapter;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.locinder.ControlActivity;
import com.example.locinder.Model.CourseModal;
import com.example.locinder.Model.CourseModel2;
import com.example.locinder.Model.LocationHelper;
import com.example.locinder.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static io.realm.Realm.getApplicationContext;
import static java.lang.String.*;

public class DeckAdapter extends BaseAdapter {

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
        ((TextView) v.findViewById(R.id.distance)).setText(courseData.get(position).getdistance()+ "KM");

        Picasso.get()
               .load(courseData.get(position).getphoto())
              .fit()
                .into((ImageView) v.findViewById(R.id.idIVCourse));




        return v;


    }


}
