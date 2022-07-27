package com.example.locinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private TextView already2;
    private Button button;

    private TextInputEditText username3, email3, phone3, password3, password4;


    private ProgressDialog loader2;


    private FirebaseAuth mAuth;
    private DatabaseReference userDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        already2=findViewById(R.id.already2);
        button=findViewById(R.id.registerbtn);

        username3=findViewById(R.id.username);
        email3=findViewById(R.id.email);

        password3=findViewById(R.id.password);
        password4=findViewById(R.id.password2);


        loader2=new ProgressDialog(this);

        mAuth=FirebaseAuth.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username31 = username3.getText().toString().trim();
                final String email31=email3.getText().toString().trim();

                final String password31=password3.getText().toString().trim();
                final String password41=password4.getText().toString().trim();
                if(TextUtils.isEmpty(username31)){
                    username3.setError("Username is required");
                    return;
                }
                if(TextUtils.isEmpty(email31)){
                    email3.setError("email is required");
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email31).matches()){
                    email3.setError("provide valid email");
                    return;

                }

                if (TextUtils.isEmpty(password31)){
                    password3.setError("password is required");
                    return;
                }
                if(TextUtils.isEmpty(password41)){
                    password4.setError("confirm password");
                    return;

                } else{

                    loader2.setMessage("Registering you");
                    loader2.setCanceledOnTouchOutside(false);
                    loader2.show();


mAuth.createUserWithEmailAndPassword(email31,password31).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {

        if(!task.isSuccessful()){
            String error=task.getException().toString();
            Toast.makeText(RegisterActivity.this,"Error"+error,Toast.LENGTH_SHORT);
        }else {

                String CurrentUserId = mAuth.getCurrentUser().getUid();
                userDatabaseRef = FirebaseDatabase.getInstance().getReference()
                        .child("users").child(CurrentUserId);

                HashMap userInfo = new HashMap();
                userInfo.put("id", CurrentUserId);
                userInfo.put("name", username31);
                userInfo.put("email", email31);

            userDatabaseRef.updateChildren(userInfo).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {

                    if(task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this,"Data set successfully",Toast.LENGTH_SHORT);
                    }
                    else {
                        Toast.makeText(RegisterActivity.this,task.getException().toString(),Toast.LENGTH_SHORT);
                    }
                    finish();

                }
            });

            Intent intent=new Intent(RegisterActivity.this, slideActivity.class);
            startActivity(intent);
            finish();
            loader2.dismiss();
        }

    }
});


                }
            }
        });




        already2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }




    }
