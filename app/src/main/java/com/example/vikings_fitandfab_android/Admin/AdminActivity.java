package com.example.vikings_fitandfab_android.Admin;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.vikings_fitandfab_android.LoginActivity;
import com.example.vikings_fitandfab_android.R;
import com.example.vikings_fitandfab_android.UserTypeActivity;
import com.example.vikings_fitandfab_android.databinding.ActivityAdminBinding;


public class AdminActivity  extends AppCompatActivity {


    ActivityAdminBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSharedPreferences("Gym_ref", MODE_PRIVATE).edit()
                        .putString("admin", "")
                        .commit();
                startActivity(new Intent(AdminActivity.this, UserTypeActivity.class));
                finish();
            }
        });
        binding.AddProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this,ProductActivity.class));
            }
        });
//
        binding.orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this, OrderAdminActivity.class));
            }
        });
        binding.AddQuotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this, QuotesActivity.class));
            }
        });
    }
}