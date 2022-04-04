package com.example.vikings_fitandfab_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.vikings_fitandfab_android.DrawerActivity;
import com.example.vikings_fitandfab_android.OrderActivity;
import com.example.vikings_fitandfab_android.databinding.ActivityOrderConformationBinding;
import com.example.vikings_fitandfab_android. databinding.ActivityOrderConformationBinding;

public class OrderConformationActivity extends AppCompatActivity {


    ActivityOrderConformationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityOrderConformationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrderConformationActivity.this, DrawerActivity.class));
                finish();
            }
        });
        binding.orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrderConformationActivity.this, OrderActivity.class));
                finish();
            }
        });

    }
}