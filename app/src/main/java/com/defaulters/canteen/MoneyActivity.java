package com.defaulters.canteen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MoneyActivity extends AppCompatActivity {


    Button addAmount;
    TextView showCurrentAmount;
    EditText getAmount;
    static String amount;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);

        showCurrentAmount = findViewById(R.id.currentAmount);
        getAmount = findViewById(R.id.enterAmount);


        currentBalance();

        addAmount = findViewById(R.id.buttonAddAmount);
        addAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(getAmount.getText().toString().trim()))
                {

                    getAmount.setError("Enter Amount");
                    return;

                }
                else
                {

                    addMoney();

                }

            }
        });





    }

    private void addMoney() {


        db.collection("USERS").document(firebaseAuth.getCurrentUser().getEmail())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        amount  = documentSnapshot.getString("money");


                        int currentMoney = Integer.parseInt(amount);

                        int newMoney = Integer.parseInt(getAmount.getText().toString().trim());

                        currentMoney += newMoney;


                        db.collection("USERS").document(firebaseAuth.getCurrentUser().getEmail())
                                .update("money",String.valueOf(currentMoney));


                        showCurrentAmount.setText(String.valueOf(currentMoney));

                        Toast.makeText(getApplicationContext(), "Amount Added", Toast.LENGTH_SHORT).show();


                    }
                });





    }

    private void currentBalance() {


        db.collection("USERS").document(firebaseAuth.getCurrentUser().getEmail())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        amount = documentSnapshot.getString("money");

                        showCurrentAmount.setText(amount);

                    }
                });



    }
}