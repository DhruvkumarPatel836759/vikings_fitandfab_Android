package com.example.vikings_fitandfab_android.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.example.vikings_fitandfab_android.Class.QuotesClass;
import com.example.vikings_fitandfab_android.R;
import com.example.vikings_fitandfab_android.databinding.FragmentHomeBinding;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    ArrayList<QuotesClass> quotesClassArrayList = new ArrayList<>();
    QuotesClass quotesClass;
    SlimAdapter slimAdapter;
    FragmentHomeBinding binding;
    private ProgressDialog progressDialog;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressDialog = new ProgressDialog(getActivity(), R.style.AppCompatAlertDialogStyle);
        progressDialog.setMessage("Loading Please Wait....");
        progressDialog.setCancelable(false);


        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        binding.recyclerView.setLayoutManager(mLayoutManager);

        slimAdapter = SlimAdapter.create()
                .register(R.layout.item_quotes, new SlimInjector<QuotesClass>() {
                    @Override
                    public void onInject(QuotesClass data, IViewInjector injector) {


                        Glide.with(getActivity()).load(data.getImage())
                                .into((ImageView) injector
                                        .findViewById(R.id.background));
                        injector.text(R.id.quoteText, "\""+data.getQuotes()+"\"");
                        injector.text(R.id.authorNameText, data.getAuthorName());


                    }
                })
                .attachTo(binding.recyclerView);
        slimAdapter.updateData(quotesClassArrayList);

        getQuotes();
    }

    void getQuotes() {
        progressDialog.show();
        FirebaseFirestore.getInstance()
                .collection("quotes")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("shopfragmentrError", error.getMessage());
                        }

                        if (value != null) {
                            quotesClassArrayList.clear();
                            for (DocumentSnapshot snapshot : value.getDocuments()) {
                                quotesClass = new QuotesClass();
                                quotesClass = snapshot.toObject(QuotesClass.class);
                                quotesClass.setQId(snapshot.getId());
                                quotesClassArrayList.add(quotesClass);
                            }
                            slimAdapter.notifyDataSetChanged();
                            progressDialog.dismiss();


                        }


                    }
                });
    }
}