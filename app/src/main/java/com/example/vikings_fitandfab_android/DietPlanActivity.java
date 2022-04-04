package com.example.vikings_fitandfab_android;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vikings_fitandfab_android.databinding.ActivityDietPlanBinding;

public class DietPlanActivity extends AppCompatActivity {

    ActivityDietPlanBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDietPlanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        findViewById(R.id.backImageView)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                });

        if (getIntent().getStringExtra("day").equals("1")) {
            binding.dayText.setText("Day1");
            binding.planImage.setImageResource(R.drawable.day1);

        } else if (getIntent().getStringExtra("day").equals("2")) {
            binding.dayText.setText("Day2");
            binding.planImage.setImageResource(R.drawable.day2);
        } else if (getIntent().getStringExtra("day").equals("3")) {
            binding.dayText.setText("Day3");
            binding.planImage.setImageResource(R.drawable.day3);
        } else if (getIntent().getStringExtra("day").equals("4")) {
            binding.dayText.setText("Day4");
            binding.planImage.setImageResource(R.drawable.day4);
        } else if (getIntent().getStringExtra("day").equals("5")) {
            binding.dayText.setText("Day5");
            binding.planImage.setImageResource(R.drawable.day5);
        } else if (getIntent().getStringExtra("day").equals("6")) {
            binding.dayText.setText("Day6");
            binding.planImage.setImageResource(R.drawable.day6);
        } else if (getIntent().getStringExtra("day").equals("7")) {
            binding.dayText.setText("Day7");
            binding.planImage.setImageResource(R.drawable.day7);
        }


    }
}