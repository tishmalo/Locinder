package com.example.locinder.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.locinder.Model.ReviewHelper;
import com.example.locinder.R;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

DatabaseReference ref;
ref= FirebaseDatabase.getInstance().getReference().child("reviews");
ref.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
       for(DataSnapshot ds: snapshot.getChildren()){

           String Email= ds.child("currentuserid").getValue().toString();

           AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
           builder1.setMessage(Email);
           builder1.setCancelable(true);

           builder1.setPositiveButton(
                   "CONTACT",
                   new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int id) {
                           dialog.cancel();
                       }
                   });

           builder1.setNegativeButton(
                   "VIEW",
                   new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int id) {
                           dialog.cancel();
                       }
                   });

           AlertDialog alert11 = builder1.create();
           alert11.show();
       }
       }



    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});

        }





    });
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
