package com.example.locinder.Adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.locinder.Model.ChatMessage;
import com.example.locinder.Model.CourseModal;
import com.example.locinder.R;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends ArrayAdapter<ChatMessage> {

    private ArrayList<ChatMessage> courseData;
    private int resourceLayout;
    private Context mContext;

    public MessageAdapter(@NonNull Context context, @NonNull List<ChatMessage> objects) {
        super(context, 0, objects);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {



        View v = convertView;
        if (v == null) {
            // on below line we are inflating our layout.
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message, parent, false);
        }

        // Get references to the views of message.xml
        TextView messageText = (TextView)v.findViewById(R.id.message_text);
        TextView messageUser = (TextView)v.findViewById(R.id.message_user);
        TextView messageTime = (TextView)v.findViewById(R.id.message_time);

        ChatMessage model = getItem(position);
        // Set their text
        messageText.setText(model.getMessageText());
        messageUser.setText(model.getMessageUser());

        // Format the date before showing it
        messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                model.getMessageTime()));


        return v;
    }
}

