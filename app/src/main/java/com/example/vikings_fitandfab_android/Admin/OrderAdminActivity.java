package com.example.vikings_fitandfab_android.Admin;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.example.vikings_fitandfab_android.Class.OrderModel;
import com.example.vikings_fitandfab_android.Class.UserModel;
import com.example.vikings_fitandfab_android.R;
import com.example.vikings_fitandfab_android.databinding.ActivityOrderAdminBinding;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

import java.util.ArrayList;

public class OrderAdminActivity extends AppCompatActivity {

    ActivityOrderAdminBinding binding;
    ArrayList<OrderModel> orderModelArrayList = new ArrayList<>();
    OrderModel orderModel;
    private ProgressDialog progressDialog;
    SlimAdapter slimAdapter;
    UserModel userModel;

    ListenerRegistration cartListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderAdminBinding.inflate(getLayoutInflater());
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
                .register(R.layout.item_order_admin, new SlimInjector<OrderModel>() {
                    @Override
                    public void onInject(OrderModel data, IViewInjector injector) {
//

                        String product = "";
                        Log.e("chrcajj", "" + data.getProducts().size());

                        for (int i = 0; i < data.getProducts().size(); i++) {

                            if (i != data.getProducts().size() - 1) {
                                product = product + data.getProducts().get(i).get("productName") + ":" + data.getProducts().get(i).get("productQuantity") + ",";
                            } else {
                                product = product + data.getProducts().get(i).get("productName") + ":" + data.getProducts().get(i).get("productQuantity");
                            }

                        }
                        Log.e("chrcajj", "" + product);
                        injector.text(R.id.orderProduct, product);
                        injector.text(R.id.orderId, data.getOrderId());
                        injector.text(R.id.orderPayment, "$" + data.getPayment());
                        if (data.getStatus().equals("pending")) {
                            injector.text(R.id.statusText, "Pending");
                        }
                        else {
                            injector.text(R.id.statusText, "Delivered");
                        }

                        if (data.getUserModel()!=null)
                            injector.text(R.id.userId, data.getUserId());
                        injector.text(R.id.username, data.getUserModel().getName());


                        injector.clicked(R.id.statusText, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (data.getStatus().equals("pending")){
                                    AlertDialog alertDialog = new AlertDialog.Builder(OrderAdminActivity.this).create();
                                    alertDialog.setTitle("Deliver");
                                    alertDialog.setMessage("Are you sure to Deliver the  Order?");
                                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {

                                                    FirebaseFirestore.getInstance()
                                                            .collection("order")
                                                            .document(data.getOrderId())
                                                            .update("status", "delivered")
                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void unused) {
                                                                    dialog.dismiss();
                                                                }
                                                            });
                                                }
                                            });
                                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    alertDialog.dismiss();
                                                }
                                            });
                                    alertDialog.show();
                                }

                            }
                        });
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
                                Log.e("orderModelArrayList", "" + orderModel.getStatus());
                            }


                            if (orderModelArrayList.size() != 0) {
                                getUser(0);
                            } else {
                                Log.e("orderModelArrayList", "" + orderModelArrayList.size());
                                progressDialog.dismiss();
                                slimAdapter.notifyDataSetChanged();
                            }


                        }


                    }
                });
    }

    private void getUser(int index) {
        FirebaseFirestore.getInstance().collection("users")
                .document(orderModelArrayList.get(index).getUserId()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        if (documentSnapshot.exists()) {
                            userModel = new UserModel();
                            userModel = documentSnapshot.toObject(UserModel.class);
                            userModel.setuId(documentSnapshot.getId());
                            orderModelArrayList.get(index).setUserModel(userModel);
                        }

                        if (index + 1 < orderModelArrayList.size()) {
                            getUser(index + 1);
                        } else {
                            progressDialog.dismiss();
                            slimAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}