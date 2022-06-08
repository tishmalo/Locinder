package com.example.locinder.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.locinder.Model.ReviewHelper;
import com.example.locinder.R;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ReviewHelper> userList;
    private DatabaseReference userRef;

    public ReviewAdapter(Context context, ArrayList<ReviewHelper> userList) {
        this.context = context;
        this.userList = userList;
        this.userRef = userRef;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.review_selected, parent, false);
        return new ReviewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final ReviewHelper user= userList.get(position);

      //  holder.rating.setText(user.getRATING());
        holder.review.setText(user.getREVIEW());





    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView rating,review;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

//            rating=itemView.findViewById(R.id.select_rating2);
            review=itemView.findViewById(R.id.select_review2);


        }
    }
}
