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

import com.example.locinder.Model.CourseModal;
import com.example.locinder.Model.CourseModel2;
import com.example.locinder.R;
import com.example.locinder.ReviewActivity;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static io.realm.Realm.getApplicationContext;

public class TravelAdapter extends RecyclerView.Adapter<TravelAdapter.ViewHolder> {

    private Context context;
    private ArrayList<CourseModel2> userList;
    private DatabaseReference userRef;


    public TravelAdapter(Context context, ArrayList<CourseModel2> userList) {
        this.context = context;
        this.userList = userList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.location_designed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final CourseModel2 user= userList.get(position);
        holder.name.setText(user.getlocations());
        holder.description.setText(user.getdescription());


        Picasso.get()
                .load(user.getphoto())
                .fit()
                .into(holder.photo);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ReviewActivity.class);
                intent.putExtra("locations",user.getlocations());
                context.startActivity(intent);


                


            }
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView photo;
        public TextView name,description,rating,review;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.select_location);
            description=itemView.findViewById(R.id.select_description);
            rating=itemView.findViewById(R.id.select_rating);
            review=itemView.findViewById(R.id.select_review);
            photo=itemView.findViewById(R.id.locationimage);


        }
    }
}
