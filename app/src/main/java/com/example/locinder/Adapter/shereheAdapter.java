package com.example.locinder.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.locinder.Model.CourseModal;
import com.example.locinder.Model.ShereheModel;
import com.example.locinder.R;

import org.w3c.dom.Text;

import java.util.List;

public class shereheAdapter extends RecyclerView.Adapter<shereheAdapter.ViewHolder> {
    Context context;
    List<ShereheModel> userList;


    public shereheAdapter(Context context, List<ShereheModel> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.sherehe,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

       ShereheModel user= userList.get(position);

        holder.contact.setText(user.getContact());
       // holder.how.setText(user.getHow());
        holder.park.setText(user.getPark());
        holder.attraction.setText(user.getAttractions());


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView how, attraction, park, contact;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

           how= itemView.findViewById(R.id.select_location);
            attraction=itemView.findViewById(R.id.attraction);
            park=itemView.findViewById(R.id.price);
            contact=itemView.findViewById(R.id.select_description);
        }
    }
}
