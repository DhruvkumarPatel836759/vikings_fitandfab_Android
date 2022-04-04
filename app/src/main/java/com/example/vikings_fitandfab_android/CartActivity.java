package com.example.vikings_fitandfab_android;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.example.vikings_fitandfab_android.Class.CartModel;
import com.example.vikings_fitandfab_android.Class.SupplimentModel;
import com.example.vikings_fitandfab_android.databinding.ActivityStopWatchBinding;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class CartActivity extends AppCompatActivity {

    ArrayList<CartModel> cartModelArrayList = new ArrayList<>();
    ArrayList<CartModel> paymentArraylist = new ArrayList<>();
    CartModel cartModel;
    SupplimentModel supplimentModel;

    ActivityStopWatchBinding binding;

    SlimAdapter slimAdapter, paymentAdapter;
    private ProgressDialog progressDialog;

    double totalAmount;
    SkuDetails skuDetails;
    String sku;
    BillingClient billingClient;

    ListenerRegistration cartListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStopWatchBinding.inflate(getLayoutInflater());
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
                .register(R.layout.item_cart, new SlimInjector<CartModel>() {
                    @Override
                    public void onInject(CartModel data, IViewInjector injector) {

                        if (data.isSelection()) {
                            injector.image(R.id.checkImage, R.drawable.red_tick);
                        } else {
                            injector.image(R.id.checkImage, R.drawable.gray_tick);
                        }
                        if (data.getSupplimentModel() != null) {
                            injector.text(R.id.itemname, data.getSupplimentModel().getName());
                            injector.text(R.id.itemprice, "$" + data.getSupplimentModel().getPrice());
                            injector.text(R.id.stockText, "In stock " + data.getSupplimentModel().getQuantity());
                            Glide.with(CartActivity.this).load(data.getSupplimentModel().getImage())
                                    .into((ImageView) injector
                                            .findViewById(R.id.itemimage));
                        }
                        injector.clicked(R.id.deleteImageView, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                FirebaseFirestore.getInstance().collection("cart")
                                        .document(data.getcId())
                                        .delete();
                            }
                        });

                        TextView quantityText = (TextView) injector.findViewById(R.id.quantityText);

                        injector.clicked(R.id.checkImage, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (data.isSelection()) {
                                    data.setSelection(false);
                                    slimAdapter.notifyDataSetChanged();
                                } else {
                                    data.setSelection(true);
                                    slimAdapter.notifyDataSetChanged();
                                }

                            }
                        });
                        injector.clicked(R.id.minusImage, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (Integer.parseInt(quantityText.getText().toString()) > 1) {
                                    quantityText.setText("" + (Integer.parseInt(quantityText.getText().toString()) - 1));
                                }
                                data.getSupplimentModel().setSelectQunatity(Integer.parseInt(quantityText.getText().toString()));
                                data.getSupplimentModel().setRemainQuantity(data.getSupplimentModel().getQuantity() - Integer.parseInt(quantityText.getText().toString()));
                            }
                        });
                        injector.clicked(R.id.plusImage, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (Integer.parseInt(quantityText.getText().toString()) < data.getSupplimentModel().getQuantity()) {
                                    quantityText.setText("" + (Integer.parseInt(quantityText.getText().toString()) + 1));
                                }
                                data.getSupplimentModel().setRemainQuantity(data.getSupplimentModel().getQuantity() - Integer.parseInt(quantityText.getText().toString()));
                                data.getSupplimentModel().setSelectQunatity(Integer.parseInt(quantityText.getText().toString()));
                                Log.e("getRemainQuantity", "plus:" + data.getSupplimentModel().getRemainQuantity());
                            }
                        });

                        data.getSupplimentModel().setSelectQunatity(Integer.parseInt(quantityText.getText().toString()));
                        data.getSupplimentModel().setRemainQuantity(data.getSupplimentModel().getQuantity() - Integer.parseInt(quantityText.getText().toString()));


                    }
                })
                .attachTo(binding.recyclerView);
        slimAdapter.updateData(cartModelArrayList);


        LinearLayoutManager pLayoutManager = new LinearLayoutManager(this);
        binding.recyclerViewItem.setLayoutManager(pLayoutManager);
        paymentAdapter = SlimAdapter.create()
                .register(R.layout.conform_item, new SlimInjector<CartModel>() {
                    @Override
                    public void onInject(CartModel data, IViewInjector injector) {

                        Glide.with(CartActivity.this).load(data.getSupplimentModel().getImage())
                                .into((ImageView) injector.findViewById(R.id.itemimage));
                        injector.text(R.id.productName, data.getSupplimentModel().getName());
                        injector.text(R.id.priceText, "$" + data.getSupplimentModel().getPrice());
                        injector.text(R.id.totalPrice, data.getSupplimentModel().getPrice() + "*" + data.getSupplimentModel().getSelectQunatity() + "=" + " $" + (data.getSupplimentModel().getPrice() * data.getSupplimentModel().getSelectQunatity()));
                    }
                })
                .attachTo(binding.recyclerViewItem);


        binding.checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cartModelArrayList.size() != 0) {
                    totalAmount=0;
                    paymentArraylist.clear();
                    for (int i = 0; i < cartModelArrayList.size(); i++) {
                        if (cartModelArrayList.get(i).isSelection()) {
                            totalAmount = totalAmount + (cartModelArrayList.get(i).getSupplimentModel().getPrice()
                                    * cartModelArrayList.get(i).getSupplimentModel().getSelectQunatity());

                            paymentArraylist.add(cartModelArrayList.get(i));
                        }

                        Log.e("totalAmount", "" + totalAmount);
                    }
                    paymentAdapter.updateData(paymentArraylist);
                    paymentAdapter.notifyDataSetChanged();
                    binding.conformPaymentGroup.setVisibility(View.VISIBLE);
                    binding.TextTotalPayment.setText("You need to pay $" + totalAmount + " for this order");
                } else {
                    Toast.makeText(CartActivity.this, "Please select the Product", Toast.LENGTH_SHORT).show();
                }


            }

        });

        binding.cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.conformPaymentGroup.setVisibility(View.GONE);
            }
        });

        binding.conformPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.conformPaymentGroup.setVisibility(View.GONE);
                payBill();
            }
        });

        getCart();

    }

    void getCart() {
        progressDialog.show();
        cartListener = FirebaseFirestore.getInstance()
                .collection("cart")
                .whereEqualTo("userId", FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("shopfragmentrError", error.getMessage());
                        }

                        if (value != null) {
                            cartModelArrayList.clear();
                            for (DocumentSnapshot snapshot : value.getDocuments()) {
                                cartModel = new CartModel();
                                cartModel = snapshot.toObject(CartModel.class);
                                cartModel.setcId(snapshot.getId());
                                cartModelArrayList.add(cartModel);
                            }

                            Log.e("cartModelArrayList", "" + cartModelArrayList.size());
//
                            if (cartModelArrayList.size() != 0) {
                                getSuppliment(0);
                            } else {
                                progressDialog.dismiss();
                                slimAdapter.notifyDataSetChanged();
                            }

                        }


                    }
                });
    }

    private void getSuppliment(int index) {
        FirebaseFirestore.getInstance()
                .collection("suppliments")
                .document(cartModelArrayList.get(index).getProductId()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        supplimentModel = new SupplimentModel();
                        supplimentModel = documentSnapshot.toObject(SupplimentModel.class);
                        supplimentModel.setsId(documentSnapshot.getId());
                        cartModelArrayList.get(index).setSupplimentModel(supplimentModel);

                        if (index + 1 < cartModelArrayList.size()) {
                            getSuppliment(index + 1);
                        } else {
                            progressDialog.dismiss();
                            slimAdapter.notifyDataSetChanged();
                        }


                    }
                });
    }

    void payBill() {

        PurchasesUpdatedListener purchasesUpdatedListenerpayment = new PurchasesUpdatedListener() {
            @Override
            public void onPurchasesUpdated(BillingResult billingResult, List<Purchase> purchases) {

                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    progressDialog.show();
                    cartListener.remove();
                    ArrayList<HashMap<String, Object>> orderArray = new ArrayList<>();
                    for (int i = 0; i < cartModelArrayList.size(); i++) {
                        if (cartModelArrayList.get(i).isSelection()) {
                            int finalI = i;
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("productPrice", cartModelArrayList.get(finalI).getSupplimentModel().getPrice());
                            hashMap.put("productQuantity", cartModelArrayList.get(finalI).getSupplimentModel().getSelectQunatity());
                            hashMap.put("productName", cartModelArrayList.get(finalI).getSupplimentModel().getName());
                            hashMap.put("productImage", cartModelArrayList.get(finalI).getSupplimentModel().getImage());
                            hashMap.put("productId", cartModelArrayList.get(finalI).getSupplimentModel().getsId());
                            orderArray.add(hashMap);
                            FirebaseFirestore.getInstance().collection("suppliments")
                                    .document(cartModelArrayList.get(i).getProductId())
                                    .update("quantity", cartModelArrayList.get(finalI)
                                            .getSupplimentModel().getRemainQuantity())
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            FirebaseFirestore.getInstance().collection("cart")
                                                    .document(cartModelArrayList.get(finalI).getcId())
                                                    .delete();
                                        }
                                    });
                        }

                    }

                    String time = String.valueOf(System.currentTimeMillis());
                    HashMap<String, Object> hashMap2 = new HashMap<>();
                    hashMap2.put("products", orderArray);
                    hashMap2.put("orderId", "ID" + time);
                    hashMap2.put("userId", FirebaseAuth.getInstance().getCurrentUser().getUid());
                    hashMap2.put("status","pending");
                    hashMap2.put("payment",totalAmount);

                    FirebaseFirestore.getInstance()
                            .collection("order").document("ID" + time)
                            .set(hashMap2).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            startActivity(new Intent(CartActivity.this, OrderConformationActivity.class));
                            finish();
                        }
                    });


                }
            }
        };

        billingClient = BillingClient.newBuilder(CartActivity.this)
                .setListener(purchasesUpdatedListenerpayment)
                .enablePendingPurchases()
                .build();

        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                List<String> skuList = new ArrayList<>();
                skuList.add("android.test.purchased");
//

                SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
                params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
                billingClient.querySkuDetailsAsync(params.build(),
                        new SkuDetailsResponseListener() {
                            @Override
                            public void onSkuDetailsResponse(BillingResult billingResult,
                                                             List<SkuDetails> skuDetailsList) {

                                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && skuDetailsList != null) {
                                    for (Object skuDetailsObject : skuDetailsList) {
                                        skuDetails = (SkuDetails) skuDetailsObject;
                                        sku = skuDetails.getSku();

                                        if ("android.test.purchased".equals(sku)) {
                                            BillingFlowParams flowParams = BillingFlowParams.newBuilder().setSkuDetails(skuDetails).build();
                                            billingClient.launchBillingFlow(Objects.requireNonNull(Objects.requireNonNull(CartActivity.this)), flowParams);

                                        } else {
                                            Log.d("TAG", "Sku is null");
                                        }

                                    }
                                }
                            }
                        });
            }

            @Override
            public void onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        });
    }

}