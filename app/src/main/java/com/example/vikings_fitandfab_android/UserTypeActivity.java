package com.example.vikings_fitandfab_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.vikings_fitandfab_android.databinding.ActivityUserTypeBinding;

public class UserTypeActivity extends AppCompatActivity {

    public static boolean user;

    ActivityUserTypeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUserTypeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user=true;
                startActivity(new Intent(UserTypeActivity.this,SignupActivity.class));

            }
        });
        binding.adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user=false;
                startActivity(new Intent(UserTypeActivity.this,LoginActivity.class));
            }
        });
    }
}