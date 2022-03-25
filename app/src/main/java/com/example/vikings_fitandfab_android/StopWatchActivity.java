package com.example.vikings_fitandfab_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.common.base.Stopwatch;
import com.example.vikings_fitandfab_android.databinding.ActivityStopWatchBinding;

import java.util.concurrent.TimeUnit;

public class StopWatchActivity extends AppCompatActivity {

    ActivityStopWatchBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityStopWatchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



    }
}