package com.example.vikings_fitandfab_android;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.example.vikings_fitandfab_android.Class.CartModel;
import com.example.vikings_fitandfab_android.Class.SupplimentModel;
import com.example.vikings_fitandfab_android.databinding.ActivityStopWatchBinding;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    ArrayList<CartModel> cartModelArrayList = new ArrayList<>();
    CartModel cartModel;
    SupplimentModel supplimentModel;

    ActivityStopWatchBinding binding;

    SlimAdapter slimAdapter;
    private ProgressDialog progressDialog;

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
                        }
                        injector.clicked(R.id.deleteImageView, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                FirebaseFirestore.getInstance().collection("cart")
                                        .document(data.getcId())
                                        .delete();
                            }
                        });

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
                    }
                })
                .attachTo(binding.recyclerView);
        slimAdapter.updateData(cartModelArrayList);

        getCart();

    }

    void getCart() {
        progressDialog.show();
        FirebaseFirestore.getInstance()
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

}