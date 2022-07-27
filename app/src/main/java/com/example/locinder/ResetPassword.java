package com.example.locinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class ResetPassword extends AppCompatActivity {


    private Button button;

    private TextInputEditText email3;
    private ProgressDialog loader2;

    private Toolbar toolBar;

    private FirebaseAuth mAuth;
    private DatabaseReference userDatabaseRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        button=findViewById(R.id.forgot1btn);
        email3=findViewById(R.id.email101);





        loader2=new ProgressDialog(this);


        toolBar=findViewById(R.id.resettoolbar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setTitle("RESET PASSWORD");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });



        mAuth=FirebaseAuth.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email31=email3.getText().toString().trim();


                if(TextUtils.isEmpty(email31)){
                    email3.setError("email is required");
                    return;
                }
                else{
                    loader2.setMessage("Sending code");
                    loader2.setCanceledOnTouchOutside(false);
                    loader2.show();

                    mAuth.sendPasswordResetEmail(email31).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ResetPassword.this, "Check email to reset your password!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ResetPassword.this, "Fail to send reset password email!", Toast.LENGTH_SHORT).show();
                            }
                            finish();

                            Intent intent = new Intent(ResetPassword.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    });
                }




            }
        });





    }
}