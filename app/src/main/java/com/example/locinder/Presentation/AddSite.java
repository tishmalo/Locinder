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
import com.example.locinder.R;
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

public class AddSite extends AppCompatActivity {

    private EditText name;
    private EditText description;
    private EditText price;

    Button loginbtn;
    ImageView site;
   Spinner county;
    String  Lat1;
    String Long1;
    DatabaseReference userDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_site);
        county= findViewById(R.id.county);
        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        price = findViewById(R.id.price);

        Button upload = findViewById(R.id.upload);
        loginbtn= findViewById(R.id.loginbtn);
        site= findViewById(R.id.site);

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

            loginbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final String name1=name.getText().toString().trim();
                    final String description1=description.getText().toString().trim();
                    final String price1=price.getText().toString().trim();
                    final String county2=county.getSelectedItem().toString();

                    if(county2.equals("Nairobi")){
                        Lat1="-1.2999313";
                        Long1="36.8110348";

                    }   if(county2.equals("Kiambu")){
                        Lat1="-1.1726699";
                        Long1="36.8233758";

                    }   if(county2.equals("Nakuru")){
                        Lat1="-0.3154676";
                        Long1="35.9387717";

                    }   if(county2.equals("Kakamega")){
                        Lat1="0.2819034";
                        Long1="34.7286168";

                    }   if(county2.equals("Bungoma")){
                        Lat1="0.573704";
                        Long1="34.5181839";

                    }   if(county2.equals("Meru")){
                        Lat1="0.0484085";
                        Long1="37.6260278";

                    }   if(county2.equals("Kilifi")){
                        Lat1="-3.536916";
                        Long1="39.7565473";

                    }   if(county2.equals("Machakos")){
                        Lat1="-1.5128045";
                        Long1="37.2369496";

                    }   if(county2.equals("Kisii")){
                        Lat1="-0.6803312";
                        Long1="34.7600382";

                    }   if(county2.equals("Mombasa")){
                        Lat1="-4.0350145";
                        Long1="39.5962221";

                    }

                    userDatabaseRef= FirebaseDatabase.getInstance().getReference("Locinder2").child(FirebaseAuth.getInstance().getCurrentUser().getUid());


                    HashMap user=new HashMap();
                    user.put("LOCATIONS", name1);
                    user.put("DESCRIPTION", description1);
                    user.put("PRICE", price1);
                    user.put("Lat",Lat1);
                    user.put("Long",Long1);


                    userDatabaseRef.updateChildren(user).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "Photos added successfully", Toast.LENGTH_SHORT).show();
                            }


                        }
                    });






                    if (fullphotoUri != null) {
                        final StorageReference filepath = FirebaseStorage.getInstance().getReference()
                                .child("Locidata").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
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
                                            Map getimagemap = new HashMap();
                                            getimagemap.put("PHOTO", imageuri);

                                            userDatabaseRef.updateChildren(getimagemap).addOnCompleteListener(new OnCompleteListener() {
                                                @Override
                                                public void onComplete(@NonNull Task task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(getApplicationContext(), "Image uploaded successfuly", Toast.LENGTH_SHORT);
                                                    } else {
                                                        Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_SHORT);
                                                    }

                                                }
                                            });

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