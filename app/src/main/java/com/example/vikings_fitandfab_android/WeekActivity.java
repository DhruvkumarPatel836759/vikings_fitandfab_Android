package com.example.vikings_fitandfab_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.vikings_fitandfab_android.databinding.ActivityWeekBinding;

public class WeekActivity extends AppCompatActivity {

    ActivityWeekBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityWeekBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        findViewById(R.id.backImageView)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                });
        binding.mondayCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WeekActivity.this, DietPlanActivity.class)
                        .putExtra("day","1"));
            }
        });
        binding.tusCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WeekActivity.this, DietPlanActivity.class)
                        .putExtra("day","2"));
            }
        });
        binding.wedCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WeekActivity.this, DietPlanActivity.class)
                        .putExtra("day","3"));
            }
        });
        binding.thuCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WeekActivity.this, DietPlanActivity.class)
                        .putExtra("day","4"));
            }
        });
        binding.friCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WeekActivity.this, DietPlanActivity.class)
                        .putExtra("day","5"));
            }
        });
        binding.satCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WeekActivity.this, DietPlanActivity.class)
                        .putExtra("day","6"));
            }
        });
        binding.sunCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WeekActivity.this, DietPlanActivity.class)
                        .putExtra("day","7"));
            }
        });

    }
}