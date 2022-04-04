package com.example.vikings_fitandfab_android.Admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.example.vikings_fitandfab_android.Class.QuotesClass;
import com.example.vikings_fitandfab_android.R;
import com.example.vikings_fitandfab_android.databinding.ActivityQuotesBinding;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

import java.util.ArrayList;

public class QuotesActivity extends AppCompatActivity {

    ArrayList<QuotesClass> quotesClassArrayList = new ArrayList<>();
    QuotesClass quotesClass;
    SlimAdapter slimAdapter;
    ActivityQuotesBinding binding;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=    ActivityQuotesBinding.inflate(getLayoutInflater());
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

        binding.addQuotesImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuotesActivity.this, AddQuotesActivity.class));

            }
        });

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(mLayoutManager);

        slimAdapter = SlimAdapter.create()
                .register(R.layout.item_quotes, new SlimInjector<QuotesClass>() {
                    @Override
                    public void onInject(QuotesClass data, IViewInjector injector) {


                        Glide.with(QuotesActivity.this).load(data.getImage())
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