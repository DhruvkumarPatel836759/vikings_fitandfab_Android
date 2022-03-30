package com.example.vikings_fitandfab_android.Admin;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.vikings_fitandfab_android.databinding.ActivityAdminBinding;


public class AdminActivity extends AppCompatActivity {


    ActivityAdminBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}