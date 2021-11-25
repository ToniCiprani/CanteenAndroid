package com.defaulters.canteen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class OrderStatusActivity extends AppCompatActivity {


    RecyclerView recyclerViewStatus;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    OrderStatusAdapter orderStatusAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);


    recyclerViewStatus = findViewById(R.id.recyclerViewStatus);

        Query query = db.collection("orders").whereEqualTo("userEmail",firebaseAuth.getCurrentUser().getEmail())
                .orderBy("orderTimestamp", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<ORDER> options = new FirestoreRecyclerOptions.Builder<ORDER>()
                .setQuery(query, ORDER.class)
                .build();

        orderStatusAdapter = new OrderStatusAdapter(options);
        recyclerViewStatus.setHasFixedSize(true);
        recyclerViewStatus.setLayoutManager(new WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewStatus.setAdapter(orderStatusAdapter);

    }


    @Override
    public void onStart() {
        super.onStart();
        orderStatusAdapter.startListening();
    }


    @Override
    public void onStop() {
        super.onStop();
        orderStatusAdapter.stopListening();
    }

}