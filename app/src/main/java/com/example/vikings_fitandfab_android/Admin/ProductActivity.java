package com.example.vikings_fitandfab_android.Admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.example.vikings_fitandfab_android.Class.SupplimentModel;
import com.example.vikings_fitandfab_android.DrawerActivity;
import com.example.vikings_fitandfab_android.LoginActivity;
import com.example.vikings_fitandfab_android.R;
import com.example.vikings_fitandfab_android.databinding.ActivityProductBinding;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductActivity extends AppCompatActivity {

    ActivityProductBinding binding;

    ArrayList<SupplimentModel> supplimentModelArrayList = new ArrayList<>();
    SupplimentModel supplimentModel;
    SlimAdapter slimAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        findViewById(R.id.backImageView)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                });
        binding.addProdcutImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProductActivity.this, AddProductActivity.class)
                        .putExtra("action","add"));

            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        binding.recyclerView.setLayoutManager(gridLayoutManager);

        slimAdapter = SlimAdapter.create()
                .register(R.layout.item_shop_admin, new SlimInjector<SupplimentModel>() {
                    @Override
                    public void onInject(SupplimentModel data, IViewInjector injector) {


                        Glide.with(ProductActivity.this).load(data.getImage())
                                .into((ImageView) injector
                                        .findViewById(R.id.id1));

                        injector.text(R.id.id2, data.getName());
                        injector.text(R.id.id3, data.getDiscription());
                        injector.text(R.id.id4, data.getType());
                        injector.text(R.id.id5, "$" + data.getPrice());

                        injector.clicked(R.id.id6, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(ProductActivity.this,AddProductActivity.class)
                                        .putExtra("action","edit")
                                        .putExtra("product",data));
                            }
                        });

                    }
                })
                .attachTo(binding.recyclerView);
        slimAdapter.updateData(supplimentModelArrayList);

        getSuppliments();
    }

    void getSuppliments() {
        FirebaseFirestore.getInstance()
                .collection("suppliments")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("shopfragmentrError", error.getMessage());
                        }

                        if (value != null) {
                            supplimentModelArrayList.clear();
                            for (DocumentSnapshot snapshot : value.getDocuments()) {
                                supplimentModel = new SupplimentModel();
                                supplimentModel = snapshot.toObject(SupplimentModel.class);
                                supplimentModel.setsId(snapshot.getId());
                                supplimentModelArrayList.add(supplimentModel);
                            }
                            slimAdapter.notifyDataSetChanged();


                            Log.e("shopfragmentrError", "" + supplimentModelArrayList.size());

                        }


                    }
                });
    }
}