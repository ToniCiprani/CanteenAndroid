package com.defaulters.canteen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MenuActivity extends AppCompatActivity {

    RecyclerView menuRecyclerView;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    MenuAdapter menuAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        menuRecyclerView = findViewById(R.id.recyclerViewMenu);

        Query query = db.collection("menu");

        FirestoreRecyclerOptions<MENU> options = new FirestoreRecyclerOptions.Builder<MENU>()
                .setQuery(query, MENU.class)
                .build();

        menuAdapter = new MenuAdapter(options);
        menuRecyclerView.setHasFixedSize(true);
        menuRecyclerView.setLayoutManager(new WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        menuRecyclerView.setAdapter(menuAdapter);

    }


    @Override
    public void onStart() {
        super.onStart();
        menuAdapter.startListening();
    }


    @Override
    public void onStop() {
        super.onStop();
        menuAdapter.stopListening();
    }


}