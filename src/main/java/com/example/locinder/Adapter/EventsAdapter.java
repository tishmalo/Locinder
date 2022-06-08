package com.example.locinder.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.locinder.Model.TripModel;
import com.example.locinder.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EventsAdapter extends BaseAdapter {

    List<TripModel> userList;
    Context ctx;

    public EventsAdapter(List<TripModel> userList, Context ctx) {
        this.userList = userList;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int i) {
        return userList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     *get the items from the firebase and display it
     */

    @Override
    public View getView(int i, View view, ViewGroup parent) {

        View v = view;
        if(v==null){
            v= LayoutInflater.from(parent.getContext()).inflate(R.layout.showevents,parent,false);
        }

        ((TextView) v.findViewById(R.id.destination22)).setText(userList.get(i).getlocation());
        ((TextView)v.findViewById(R.id.date22)).setText(userList.get(i).getdate());
        Picasso.get()
                .load(userList.get(i).getphoto())
                .fit()
                .into((ImageView) v.findViewById(R.id.photo22));

        return v;
    }
}
