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

import com.example.locinder.Presentation.ChatRoom;
import com.example.locinder.Model.TripModel;
import com.example.locinder.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private Context context;
    private ArrayList<TripModel> list;

    public ChatAdapter(Context context, ArrayList<TripModel> list) {
        this.context = context;
        this.list = list;
    }

    /***
     *Inflate layout- the organize_items.xml. It incorporates a layout background into another background
     *
     */

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.chats, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final TripModel user= list.get(position);

        holder.location.setText(user.getlocation());
        holder.date.setText(user.getdate());

        Picasso.get()
                .load(user.getphoto())
                .fit()
                .into(holder.photo);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(context, ChatRoom.class);
                intent.putExtra("location",user.getlocation());
                intent.putExtra("date",user.getdate());
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        /**
         *Link Adapter to xml
         */
        private TextView location,date;
        private ImageView photo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            location=itemView.findViewById(R.id.select_location1);
            date=itemView.findViewById(R.id.select_date1);
            photo=itemView.findViewById(R.id.select_photo1);


        }
    }
}
