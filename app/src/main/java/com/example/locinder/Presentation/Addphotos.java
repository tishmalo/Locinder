package com.example.locinder.Presentation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.locinder.Model.ShereheModel;
import com.example.locinder.R;
import com.example.locinder.slideActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Addphotos extends AppCompatActivity {

    ImageView site;
    Spinner name;
    Button upload, submit;

    DatabaseReference userDatabaseRef;
    String name1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addphotos);

        site= findViewById(R.id.site);
        name=findViewById(R.id.location);
        upload= findViewById(R.id.upload);
        submit=findViewById(R.id.submit);


        upload.setOnClickListener(new View.OnClickListener() {

                static final int REQUEST_IMAGE_GET = 1;

                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, REQUEST_IMAGE_GET);


                }
            });




    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {






            Bitmap thumbnail = data.getParcelableExtra("data");
            Uri fullphotoUri = data.getData();

            final String photouri = fullphotoUri.getPath().trim();

            Glide.with(getApplicationContext()).load(fullphotoUri).into(site);

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    name1=name.getSelectedItem().toString();

                    userDatabaseRef= FirebaseDatabase.getInstance().getReference("sherehe");







                    if (fullphotoUri != null) {
                        final StorageReference filepath = FirebaseStorage.getInstance().getReference()
                                .child("sherehe").child(name1);
                        Bitmap bitmap = null;
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), fullphotoUri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
                        byte[] data = byteArrayOutputStream.toByteArray();

                        UploadTask uploadTask = filepath.putBytes(data);

                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Upload failed", Toast.LENGTH_SHORT).show();


                            }
                        });
                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                if (taskSnapshot.getMetadata() != null && taskSnapshot.getMetadata().getReference() != null) {
                                    Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                    result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String imageuri = uri.toString();


                                         ShereheModel sm= new ShereheModel();
                                         userDatabaseRef.push().setValue(sm);

                                            Toast.makeText(Addphotos.this, "PHOTO ADDED", Toast.LENGTH_SHORT).show();
                                           Intent intent =new Intent(Addphotos.this, slideActivity.class);
                                           startActivity(intent);

                                            finish();
                                        }

                                    });

                                }

                            }
                        });


                    }

                }
            });

        }

    }

}