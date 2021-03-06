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

import com.google.firebase.auth.FirebaseAuth;
import com.example.vikings_fitandfab_android.Fragments.BMIFragment;
import com.example.vikings_fitandfab_android.Fragments.HomeFragment;
import com.example.vikings_fitandfab_android.Fragments.WorkoutFragment;
import com.example.vikings_fitandfab_android.Fragments.ShopFragment;
import com.example.vikings_fitandfab_android.Fragments.StopwatchFragment;
import com.example.vikings_fitandfab_android.databinding.ActivityDrawerBinding;

public class DrawerActivity extends AppCompatActivity {

    FrameLayout fragmentLayout;
    private Fragment homeFragment = new HomeFragment();
    private Fragment stopwatchFragment = new StopwatchFragment();
    private Fragment bmiFragment = new BMIFragment();
    private Fragment shopFragment = new ShopFragment();
    private Fragment workoutFragment = new WorkoutFragment();
    private Fragment activeFragment = homeFragment;
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


        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.close();
            }
        });

        findViewById(R.id.cartButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.close();
                startActivity(new Intent(DrawerActivity.this, CartActivity.class));
            }
        });
        findViewById(R.id.orderButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.close();
                startActivity(new Intent(DrawerActivity.this, OrderActivity.class));
            }
        });
        findViewById(R.id.planButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.close();
                startActivity(new Intent(DrawerActivity.this, WeekActivity.class));
            }
        });

        findViewById(R.id.logoutButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(DrawerActivity.this, UserTypeActivity.class));
                finish();
            }
        });



        fragmentLayout = findViewById(R.id.fragmentLayout);
        fragmentManager.beginTransaction().add(R.id.fragmentLayout, shopFragment, "5").hide(shopFragment).commit();
        fragmentManager.beginTransaction().add(R.id.fragmentLayout, workoutFragment, "4").hide(workoutFragment).commit();
        fragmentManager.beginTransaction().add(R.id.fragmentLayout, bmiFragment, "3").hide(bmiFragment).commit();
        fragmentManager.beginTransaction().add(R.id.fragmentLayout, stopwatchFragment, "2").hide(stopwatchFragment).commit();
        fragmentManager.beginTransaction().add(R.id.fragmentLayout, homeFragment, "1").commit();


        home = findViewById(R.id.home);
        stopwatchImage = findViewById(R.id.stopwatchImage);
        bMIImage = findViewById(R.id.bMIImage);
        planingImage = findViewById(R.id.planingImage);
        shopImage = findViewById(R.id.shopImage);
        homeLine = findViewById(R.id.homeLine);
        stopwatchLine = findViewById(R.id.stopwatchLine);
        bMILine = findViewById(R.id.bMILine);
        planingLine = findViewById(R.id.planingLine);
        shopLine = findViewById(R.id.shopLine);

        findViewById(R.id.drawerImageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.open();
            }
        });
        findViewById(R.id.homeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activeFragment != homeFragment) {
                    fragmentManager.beginTransaction().hide(activeFragment).show(homeFragment).commit();
                    activeFragment = homeFragment;
                    home.setImageResource(R.drawable.home_sel);
                    stopwatchImage.setImageResource(R.drawable.stopwatch);
                    bMIImage.setImageResource(R.drawable.bmi);
                    planingImage.setImageResource(R.drawable.workout_uncel);
                    shopImage.setImageResource(R.drawable.suplement);
                    homeLine.setVisibility(View.VISIBLE);
                    stopwatchLine.setVisibility(View.GONE);
                    bMILine.setVisibility(View.GONE);
                    planingLine.setVisibility(View.GONE);
                    shopLine.setVisibility(View.GONE);
                }
            }
        });
        findViewById(R.id.stopwatchButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activeFragment != stopwatchFragment) {
                    fragmentManager.beginTransaction().hide(activeFragment).show(stopwatchFragment).commit();
                    activeFragment = stopwatchFragment;
                    home.setImageResource(R.drawable.home_unsel);
                    stopwatchImage.setImageResource(R.drawable.stopwatch_sel);
                    bMIImage.setImageResource(R.drawable.bmi);
                    planingImage.setImageResource(R.drawable.workout_uncel);
                    shopImage.setImageResource(R.drawable.suplement);
                    homeLine.setVisibility(View.GONE);
                    stopwatchLine.setVisibility(View.VISIBLE);
                    bMILine.setVisibility(View.GONE);
                    planingLine.setVisibility(View.GONE);
                    shopLine.setVisibility(View.GONE);
                }
            }
        });
        findViewById(R.id.bMIButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activeFragment != bmiFragment) {
                    fragmentManager.beginTransaction().hide(activeFragment).show(bmiFragment).commit();
                    activeFragment = bmiFragment;
                    home.setImageResource(R.drawable.home_unsel);
                    stopwatchImage.setImageResource(R.drawable.stopwatch);
                    bMIImage.setImageResource(R.drawable.bmi_sel);
                    planingImage.setImageResource(R.drawable.workout_uncel);
                    shopImage.setImageResource(R.drawable.suplement);
                    homeLine.setVisibility(View.GONE);
                    stopwatchLine.setVisibility(View.GONE);
                    bMILine.setVisibility(View.VISIBLE);
                    planingLine.setVisibility(View.GONE);
                    shopLine.setVisibility(View.GONE);
                }
            }
        });
        findViewById(R.id.planingButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activeFragment != workoutFragment) {
                    fragmentManager.beginTransaction().hide(activeFragment).show(workoutFragment).commit();
                    activeFragment = workoutFragment;
                    home.setImageResource(R.drawable.home_unsel);
                    stopwatchImage.setImageResource(R.drawable.stopwatch);
                    bMIImage.setImageResource(R.drawable.bmi);
                    planingImage.setImageResource(R.drawable.workout);
                    shopImage.setImageResource(R.drawable.suplement);
                    homeLine.setVisibility(View.GONE);
                    stopwatchLine.setVisibility(View.GONE);
                    bMILine.setVisibility(View.GONE);
                    planingLine.setVisibility(View.VISIBLE);
                    shopLine.setVisibility(View.GONE);
                }
            }
        });
        findViewById(R.id.shopButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activeFragment != shopFragment) {
                    fragmentManager.beginTransaction().hide(activeFragment).show(shopFragment).commit();
                    activeFragment = shopFragment;
                    home.setImageResource(R.drawable.home_unsel);
                    stopwatchImage.setImageResource(R.drawable.stopwatch);
                    bMIImage.setImageResource(R.drawable.bmi);
                    planingImage.setImageResource(R.drawable.workout_uncel);
                    shopImage.setImageResource(R.drawable.suplement_sel);
                    homeLine.setVisibility(View.GONE);
                    stopwatchLine.setVisibility(View.GONE);
                    bMILine.setVisibility(View.GONE);
                    planingLine.setVisibility(View.GONE);
                    shopLine.setVisibility(View.VISIBLE);
                }
            }
        });


    }


}