package com.defaulters.canteen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {


    EditText userEmail,userPassword;
    Button login,register;
    static int flag = 0;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        userEmail = findViewById(R.id.loginEmailAddress);
        userPassword = findViewById(R.id.loginPassword);

        login = findViewById(R.id.buttonLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                flag = 0;

                validate();


            }
        });


        register = findViewById(R.id.buttonRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));

            }
        });


    }

    private void validate() {

            if(TextUtils.isEmpty(userEmail.getText().toString().trim()) && TextUtils.isEmpty(userPassword.getText().toString().trim()))
            {
                flag = 1;
                userEmail.setError("Enter Email");
                userPassword.setError("Enter Password");
                return;

            }

            if(TextUtils.isEmpty(userEmail.getText().toString().trim()))
            {
                flag = 1;
                userEmail.setError("Enter Email");
                return;

            }

            if( TextUtils.isEmpty(userPassword.getText().toString().trim()))
            {
                flag = 1;
                userPassword.setError("Enter Password");
                return;

            }


        if(!userEmail.getText().toString().trim().matches(emailPattern))
        {
            flag = 1;
            userEmail.setError("Enter proper Email");
            return;

        }



            if(flag == 0)
            {

                login();

            }


    }

    private void login() {


        firebaseAuth.signInWithEmailAndPassword(userEmail.getText().toString().trim(),userPassword.getText().toString().trim())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {

                            Toast.makeText(getApplicationContext(),"ADMIN Logged in Successfully!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),UserHomeActivity.class));

                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "EMAIL and Password does not match!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });



    }
}