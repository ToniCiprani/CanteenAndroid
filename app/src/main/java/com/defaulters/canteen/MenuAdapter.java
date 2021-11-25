package com.defaulters.canteen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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


        if(Integer.parseInt(model.getRating()) < 3)
        {

            holder.fav.setVisibility(View.GONE);

        }

        holder.foodName.setText(model.getFoodName());
        holder.foodPrice.setText(String.valueOf(model.getFoodPrice()));
        holder.foodCategory.setText(model.getFoodCategory());
        holder.foodCategory.setText(model.getFoodCategory());
        holder.foodCount.setText("1");


        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(holder.foodCount.getText().toString().equals("0") || holder.foodCount.getText().toString().equals(""))
                {

                    Toast.makeText(view.getContext(), "Quantity Should be 1", Toast.LENGTH_SHORT).show();

                }
                else
                {

                    Timestamp current = new Timestamp(System.currentTimeMillis());

                    String ID = generateRandomID();

                    DocumentReference documentReference = db.collection("orders").document(ID);

                    ORDER order = new ORDER();

                    order.setOrderID(ID);
                    order.setUserEmail(firebaseAuth.getCurrentUser().getEmail());
                    order.setOrderName(model.getFoodName());

                    int finalPrice = Integer.parseInt(holder.foodCount.getText().toString()) * Integer.parseInt(model.getFoodPrice());


                    order.setOrderPrice(String.valueOf(finalPrice));
                    order.setOrderQuantity(holder.foodCount.getText().toString());
                    order.setOrderStatus("WAITING");
                    order.setOrderTimestamp(current.toString());
                    order.setMenuID(model.getFoodId());
                    order.setRateFlag("0");
                    documentReference.set(order);

                    db.collection("USERS").document(firebaseAuth.getCurrentUser().getEmail())
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {

                                    int temp = Integer.parseInt(documentSnapshot.getString("money"));

                                    if(temp == 0 )
                                    {
                                        Toast.makeText(view.getContext(), "You got zero money! add Money", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        int temp2 = temp - Integer.parseInt(model.getFoodPrice());

                                        if(temp2 < 0)
                                        {

                                            Toast.makeText(view.getContext(), "Not Sufficient balance", Toast.LENGTH_SHORT).show();

                                        }
                                        else
                                        {
                                            db.collection("USERS").document(firebaseAuth.getCurrentUser().getEmail())
                                                    .update("money", String.valueOf(temp2));

                                            db.collection("menu").document(model.getFoodId())
                                                    .get()
                                                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                                                            int n = Integer.parseInt(documentSnapshot.getString("count"));

                                                            n = n+1;

                                                            db.collection("menu").document(model.getFoodId())
                                                                    .update("count",String.valueOf(n));

                                                        }
                                                    });


                                            Toast.makeText(view.getContext(), "Order Added", Toast.LENGTH_SHORT).show();

                                        }


                                    }
                                }
                            });

                }



            }
        });

    }


    public String generateRandomID()
    {
        String chars="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randID= new StringBuilder();
        for(int i=0;i<20;i++)
        {
            randID.append(chars.charAt((int) Math.floor(Math.random() * chars.length())));
        }
        return randID.toString();
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
        ImageView fav;



        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            fav = itemView.findViewById(R.id.fav);
            foodName = itemView.findViewById(R.id.foodName);
            foodPrice = itemView.findViewById(R.id.foodPrice);
            foodCategory = itemView.findViewById(R.id.foodCategory);
            foodCount = itemView.findViewById(R.id.foodCount);
            addToCart = itemView.findViewById(R.id.addToCart);

        }
    }



}
