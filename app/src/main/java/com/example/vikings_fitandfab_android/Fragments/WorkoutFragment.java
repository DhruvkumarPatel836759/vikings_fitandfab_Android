package com.example.vikings_fitandfab_android.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vikings_fitandfab_android.R;
import com.example.vikings_fitandfab_android.WorkoutActivity;

import com.example.vikings_fitandfab_android.databinding.FragmentShopBinding;
import com.example.vikings_fitandfab_android.databinding.FragmentWorkoutBinding;


public class WorkoutFragment extends Fragment {

    FragmentWorkoutBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWorkoutBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.chestCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), WorkoutActivity.class)
                .putExtra("ex","chest"));
            }
        });
        binding.backCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), WorkoutActivity.class)
                .putExtra("ex","back"));
            }
        });
        binding.shouldersCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), WorkoutActivity.class)
                .putExtra("ex","shoulder"));
            }
        });
        binding.fullCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), WorkoutActivity.class)
                .putExtra("ex","full"));
            }
        });
        binding.tricepsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), WorkoutActivity.class)
                .putExtra("ex","triceps"));
            }
        });
        binding.bicepsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), WorkoutActivity.class)
                .putExtra("ex","biceps"));
            }
        });
        binding.glutesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), WorkoutActivity.class)
                .putExtra("ex","glutes"));
            }
        });
        binding.legsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), WorkoutActivity.class)
                .putExtra("ex","legs"));
            }
        });
        binding.absCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), WorkoutActivity.class)
                .putExtra("ex","abs"));
            }
        });
    }
}