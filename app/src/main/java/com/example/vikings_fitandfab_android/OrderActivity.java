package com.example.vikings_fitandfab_android;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.example.vikings_fitandfab_android.Class.CartModel;
import com.example.vikings_fitandfab_android.Class.OrderModel;
import com.example.vikings_fitandfab_android.databinding.ActivityOrderBinding;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class OrderActivity extends AppCompatActivity {


    ActivityOrderBinding binding;
    ArrayList<OrderModel> orderModelArrayList = new ArrayList<>();
    OrderModel orderModel;
    private ProgressDialog progressDialog;
    SlimAdapter slimAdapter;

    ListenerRegistration cartListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        progressDialog = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);
        progressDialog.setMessage("Loading Please Wait....");
        progressDialog.setCancelable(false);

        findViewById(R.id.backImageView)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                });

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(mLayoutManager);

        slimAdapter = SlimAdapter.create()
                .register(R.layout.item_order, new SlimInjector<OrderModel>() {
                    @Override
                    public void onInject(OrderModel data, IViewInjector injector) {
//

                        String product = null;
                        for (int i = 0; i < data.getProducts().size();i++) {

                            if (i != data.getProducts().size() - 1) {
                                product = data.getProducts().get(i).get("productName") +":"+data.getProducts().get(i).get("productQuantity")  + ",";
                            } else {
                                product = data.getProducts().get(i).get("productName") +":"+data.getProducts().get(i).get("productQuantity");
                            }

                        }
                        injector.text(R.id.orderProduct,product);
                        injector.text(R.id.orderId,data.getOrderId());
                        injector.text(R.id.orderPayment,"$"+data.getPayment());
                        if (data.getStatus().equals("pending")){
                            injector.text(R.id.statusText,"Pending");
                        }else{
                            injector.text(R.id.statusText,"Delivered");
                        }

                    }
                })
                .attachTo(binding.recyclerView);
        slimAdapter.updateData(orderModelArrayList);


        getCart();
    }

    void getCart() {
        progressDialog.show();
        cartListener = FirebaseFirestore.getInstance()
                .collection("order")
                .whereEqualTo("userId", FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("shopfragmentrError", error.getMessage());
                        }

                        if (value != null) {
                            orderModelArrayList.clear();
                            for (DocumentSnapshot snapshot : value.getDocuments()) {
                                orderModel = new OrderModel();
                                orderModel = snapshot.toObject(OrderModel.class);
                                orderModelArrayList.add(orderModel);
                                Log.e("orderModelArrayList",""+orderModel.getStatus());
                            }
                            Log.e("orderModelArrayList",""+orderModelArrayList.size());
                            progressDialog.dismiss();
                            slimAdapter.notifyDataSetChanged();



                        }


                    }
                });
    }
}