package com.example.locinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private TextView sign, forgot;
    private TextInputEditText email1, password1;
    private Button loginbtn;
    private ProgressBar loader;

    private ProgressDialog loader2;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mauthstatelistener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        sign = findViewById(R.id.yet2);
        email1 = findViewById(R.id.email1);
        password1 = findViewById(R.id.Password1);
        loginbtn = findViewById(R.id.loginbtn);
        loader2 = new ProgressDialog(this);
        forgot = findViewById(R.id.forgotpassword1);

        mAuth = FirebaseAuth.getInstance();
        mauthstatelistener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {


                FirebaseUser user = mAuth.getCurrentUser();

                if (user != null) {
                    Intent intent = new Intent(LoginActivity.this, slideActivity.class);
                    startActivity(intent);
                    finish();

                }
            }


        };


        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ResetPassword.class);
                startActivity(intent);
            }
        });

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email = email1.getText().toString().trim();
                final String password = password1.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    email1.setError("Email is required");
                }
                if (TextUtils.isEmpty(password)) {
                    password1.setError("password is required");
                } else {
                    loader2.setMessage("Logging you in");
                    loader2.setCanceledOnTouchOutside(false);
                    loader2.show();


                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                            if (task.isSuccessful()) {

                                Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT);
                                Intent intent = new Intent(LoginActivity.this, slideActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(LoginActivity.this,task.getException().toString(),Toast.LENGTH_SHORT);

                            }

                        }
                    });


                }
            }
        });




    }


    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mauthstatelistener);
    }

    @Override
    protected void onStop() {
        super.onStop();

        mAuth.removeAuthStateListener(mauthstatelistener);
    }
}
