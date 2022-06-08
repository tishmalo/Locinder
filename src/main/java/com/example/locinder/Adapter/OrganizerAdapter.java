package com.example.locinder.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.locinder.FinishOrganizing;
import com.example.locinder.Model.CourseModal;
import com.example.locinder.Model.OrganizeModel;
import com.example.locinder.Organizer;
import com.example.locinder.R;
import com.example.locinder.ReviewActivity;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OrganizerAdapter extends RecyclerView.Adapter<OrganizerAdapter.ViewHolder> {

    private Context context;
    private ArrayList<OrganizeModel> list;
    private DatabaseReference dataref;

    public OrganizerAdapter(Context context, ArrayList<OrganizeModel> list) {
        this.context = context;
        this.list = list;
        this.dataref = dataref;
    }

    /***
     *Inflate layout- the organize_items.xml. It incorporates a layout background into another background
     *
     */

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.organize_items,parent,false);

        return new ViewHolder(view);
    }

    /**
     * The magic of the operation. This displays the data retrieved from the firebase..Let's gooo

     */
    @Override
    public void onBindViewHolder(@NonNull OrganizerAdapter.ViewHolder holder, int position) {

       final OrganizeModel user= list.get(position);

        holder.location.setText(user.getLOCATIONS());
       // holder.description.setText(user.getDESCRIPTION());
        Picasso.get()
                .load(user.getPHOTO())
                .fit()
                .into(holder.photo);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, FinishOrganizing.class);
                intent.putExtra("locations",user.getLOCATIONS());
                intent.putExtra("photo", user.getPHOTO());
                context.startActivity(intent);
            }
        });

    }

    /**
     *
     * Hii inarudisha all the contents of the list.
     */
    @Override
    public int getItemCount() {
        return list.size();
    }


    /**
     * Initialize the holders from the organize item layout
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        //link Adapter class to organizeitems

        public ImageView photo;
        public TextView location,description,rating,review;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            photo= itemView.findViewById(R.id.locationimage);
            location=itemView.findViewById(R.id.select_location);
         //   description=itemView.findViewById(R.id.select_description);



        }
    }
}
