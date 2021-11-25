package com.defaulters.canteen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Timestamp;

public class MenuAdapter extends FirestoreRecyclerAdapter<MENU,MenuAdapter.myViewHolder> {

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public MenuAdapter(@NonNull FirestoreRecyclerOptions<MENU> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull MENU model) {


        holder.foodName.setText(model.getFoodName());
        holder.foodPrice.setText(String.valueOf(model.getFoodPrice()));
        holder.foodCategory.setText(model.getFoodCategory());
        holder.foodCategory.setText(model.getFoodCategory());
        holder.foodCount.setText("0");

        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Timestamp current = new Timestamp(System.currentTimeMillis());


                DocumentReference documentReference = db.collection("Orders").document(String.valueOf(current));




            }
        });


    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_cardview,parent,false);
        return new myViewHolder(v);

    }




    public class myViewHolder extends RecyclerView.ViewHolder {

        TextView foodName,foodPrice,foodCategory;
        EditText foodCount;
        Button addToCart;



        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            foodName = itemView.findViewById(R.id.foodName);
            foodPrice = itemView.findViewById(R.id.foodPrice);
            foodCategory = itemView.findViewById(R.id.foodCategory);
            foodCount = itemView.findViewById(R.id.foodCount);
            addToCart = itemView.findViewById(R.id.addToCart);




        }
    }
}
