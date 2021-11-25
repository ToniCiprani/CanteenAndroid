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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;

public class RegisterActivity extends AppCompatActivity {

    Button register;
    EditText name,password,phoneNumber,department,email;
    static int flag = 0;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        name = findViewById(R.id.enterName);
        password = findViewById(R.id.enterpassword);
        phoneNumber = findViewById(R.id.enterPhone);
        department = findViewById(R.id.enterDepartment);
        email = findViewById(R.id.enterDepartment);


        register = findViewById(R.id.buttonRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validate();

            }
        });



    }

    private void validate() {


        if(TextUtils.isEmpty(name.getText().toString().trim()) && TextUtils.isEmpty(password.getText().toString().trim())
                && TextUtils.isEmpty(phoneNumber.getText().toString().trim()) && TextUtils.isEmpty(department.getText().toString().trim()))
        {
            flag = 1;
            name.setError("Enter Employee ID");
            password.setError("Enter Employee Password");
            phoneNumber.setError("Enter Employee ID");
            department.setError("Enter Employee Password");
            return;

        }

        if(TextUtils.isEmpty(name.getText().toString().trim()))
        {
            flag = 1;
            name.setError("Enter Employee ID");
            return;

        }


        if(email.getText().toString().trim().matches(emailPattern))
        {
            flag = 1;
            email.setError("Enter proper Email");
            return;

        }

        if(flag == 0)
        {

            Register();

        }


    }

    private void Register() {


        firebaseAuth.createUserWithEmailAndPassword(email.getText().toString().trim(),password.getText().toString().trim())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if(task.getException() instanceof FirebaseAuthUserCollisionException)
                        {

                            Toast.makeText(getApplicationContext(),"User Already Registered",Toast.LENGTH_SHORT).show();

                        }

                        else
                        {

                            DocumentReference documentReference = db.collection("USERS").document(email.getText().toString().trim());

                            USER user = new USER();
                            user.setName(name.getText().toString().trim());
                            user.setEmail(email.getText().toString().trim());
                            user.setPhoneNumber(phoneNumber.getText().toString().trim());
                            user.setDepartment(department.getText().toString().trim());
                            documentReference.set(user);

                            Toast.makeText(getApplicationContext(), "Register Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));

                        }

                    }
                });

    }
}
