package com.example.vikings_fitandfab_android.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.example.vikings_fitandfab_android.Class.SupplimentModel;
import com.example.vikings_fitandfab_android.R;
import com.example.vikings_fitandfab_android.databinding.FragmentShopBinding;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

import java.util.ArrayList;
import java.util.HashMap;

public class ShopFragment extends Fragment {

    ArrayList<SupplimentModel> supplimentModelArrayList = new ArrayList<>();
    SupplimentModel supplimentModel;
    SlimAdapter slimAdapter;


    FragmentShopBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentShopBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        binding.recyclerView.setLayoutManager(gridLayoutManager);

        slimAdapter = SlimAdapter.create()
                .register(R.layout.item_shop, new SlimInjector<SupplimentModel>() {
                    @Override
                    public void onInject(SupplimentModel data, IViewInjector injector) {

                        injector.text(R.id.id2, data.getName());
                        injector.text(R.id.id3, data.getDiscription());
                        injector.text(R.id.id4, data.getType());
                        injector.text(R.id.id5, "$" + data.getPrice());

                        injector.clicked(R.id.id6, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String cartId = FirebaseAuth.getInstance().getCurrentUser().getUid() + ";;" + data.getsId();
                                FirebaseFirestore.getInstance().collection("cart").document(cartId).get()
                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                if (documentSnapshot.exists()) {
                                                    Toast.makeText(getContext(), "Product already in cart", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    HashMap<String, Object> hashMap = new HashMap<>();
                                                    hashMap.put("productId", data.getsId());
                                                    hashMap.put("userId", FirebaseAuth.getInstance().getCurrentUser().getUid());
                                                    FirebaseFirestore.getInstance().collection("cart").document(cartId)
                                                            .set(hashMap)
                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void unused) {
                                                                    Toast.makeText(getContext(), "Product add in cart successfully", Toast.LENGTH_SHORT).show();

                                                                }
                                                            });

                                                }
                                            }
                                        });


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