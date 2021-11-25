package com.defaulters.canteen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class UserHomeActivity extends AppCompatActivity {


    Button money,menu,orderStatus,logout;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);


        money = findViewById(R.id.buttonMoney);
        money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(),MoneyActivity.class));

            }
        });


        menu = findViewById(R.id.buttonMenu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(),MenuActivity.class));

            }
        });

        orderStatus = findViewById(R.id.buttonStatus);
        orderStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(),OrderStatusActivity.class));

            }
        });


        logout = findViewById(R.id.buttonLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseAuth.signOut();

            }
        });


    }
}