package com.example.vikings_fitandfab_android;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;

import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.example.vikings_fitandfab_android.databinding.ActivityDrawerBinding;
public class DrawerActivity extends AppCompatActivity {


    FrameLayout fragmentLayout;
//    private Fragment homeFragment = new HomeFragment();
//    private Fragment stopwatchFragment = new StopwatchFragment();
//    private Fragment bmiFragment = new BMIFragment();
//    private Fragment shopFragment = new ShopFragment();
//    private Fragment planFragment = new PlanFragment();
//    private Fragment activeFragment = homeFragment;
    FragmentManager fragmentManager = getSupportFragmentManager();
    private ActivityDrawerBinding binding;

    ImageView home, stopwatchImage, bMIImage, planingImage, shopImage;
    ImageView homeLine, stopwatchLine, bMILine, planingLine, shopLine;
    ImageView profileImageView, profileImageViewdrawer;
    TextView nameTextView, emailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDrawerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        profileImageView = findViewById(R.id.profileImageView);

        profileImageViewdrawer = findViewById(R.id.profileImageViewdrawer);

        nameTextView = findViewById(R.id.nameTextView);

        emailTextView = findViewById(R.id.emailTextView);


        nameTextView.setText(LoginActivity.userModel.getName());
        emailTextView.setText(LoginActivity.userModel.getEmail());


        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DrawerActivity.this, ProfileActivity.class));
            }
        });

        DrawerLayout drawer = binding.drawerLayout;


        findViewById(R.id.back).

                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        drawer.close();
                    }
                });

        findViewById(R.id.drawerImageView).

                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        drawer.open();
                    }
                });
    }
}