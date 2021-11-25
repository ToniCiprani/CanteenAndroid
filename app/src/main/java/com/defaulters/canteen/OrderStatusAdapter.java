package com.defaulters.canteen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class OrderStatusAdapter  extends FirestoreRecyclerAdapter<ORDER,OrderStatusAdapter.myViewHolder> implements AdapterView.OnItemSelectedListener{


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    String selectedRating;


    public OrderStatusAdapter(@NonNull FirestoreRecyclerOptions<ORDER> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull ORDER model) {



        db.collection("orders").document(model.getOrderID())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        String rateFlag = documentSnapshot.getString("rateFlag");


                        if(rateFlag.equals("1"))
                        {

                            holder.rateView.setVisibility(View.GONE);
                            holder.rateSpinner.setVisibility(View.GONE);
                            holder.rate.setVisibility(View.GONE);

                        }


                        checkStatus(model.getOrderStatus(),holder);

                        holder.orderName.setText(model.getOrderName());
                        holder.orderQuantity.setText(model.getOrderQuantity());
                        holder.orderStatus.setText(model.getOrderStatus());
                        holder.orderPrice.setText(String.valueOf(model.getOrderPrice()));

                        holder.rate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if(selectedRating.equals("Rate") || selectedRating.equals(""))
                                {

                                    Toast.makeText(view.getContext(), "Select a Rating", Toast.LENGTH_SHORT).show();

                                }
                                else
                                {


                                    db.collection("menu").document(model.getMenuID())
                                            .get()
                                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                @Override
                                                public void onSuccess(DocumentSnapshot documentSnapshot) {

                                                    int rating = Integer.parseInt(documentSnapshot.getString("Rating"));
                                                    int count = Integer.parseInt(documentSnapshot.getString("count"));

                                                    rating = (rating+Integer.parseInt(selectedRating))/count;

                                                    db.collection("menu").document(model.getMenuID())
                                                            .update("Rating",String.valueOf(rating),
                                                                    "rateFlag","1");


                                                    Toast.makeText(view.getContext(), "Thanks for rating", Toast.LENGTH_SHORT).show();



                                                }
                                            });

                                }


                            }
                        });

                    }
                });





    }

    private void spinner(View itemView, Spinner rateSpinner) {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(itemView.getContext(), R.array.Rating,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rateSpinner.setAdapter(adapter);
        rateSpinner.setOnItemSelectedListener(this);

    }

    private void checkStatus(String orderStatus, myViewHolder holder) {

        if(orderStatus.equals("DELIVERED"))
        {

            holder.rateSpinner.setVisibility(View.VISIBLE);

        }


    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_status_cardview,parent,false);
        return new myViewHolder(v);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        selectedRating = adapterView.getItemAtPosition(i).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        TextView orderName,orderPrice,orderQuantity,orderStatus,rateView;
        Spinner rateSpinner;
        Button rate;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            rate = itemView.findViewById(R.id.submitRating);
            rateView = itemView.findViewById(R.id.rateView);
            rateSpinner = itemView.findViewById(R.id.spinnerRate);
            orderName =itemView.findViewById(R.id.orderName);
            orderPrice =itemView.findViewById(R.id.orderPrice);
            orderQuantity =itemView.findViewById(R.id.orderQuantity);
            orderStatus =itemView.findViewById(R.id.orderStatus);

            spinner(itemView,rateSpinner);


        }
    }
}
