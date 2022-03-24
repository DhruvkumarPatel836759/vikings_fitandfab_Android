package com.example.vikings_fitandfab_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.vikings_fitandfab_android.databinding.ActivityGymPackageBinding;

public class GymPackageActivity extends AppCompatActivity {

    ActivityGymPackageBinding binding;

    String gymPackage = "sliver";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGymPackageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.sliverPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gymPackage = "sliver";
                binding.sliverImage.setImageResource(R.drawable.checkbox_chkd);
                binding.goldImage.setImageResource(R.drawable.checkbox_unchecked);
                binding.platinumImage.setImageResource(R.drawable.checkbox_unchecked);
            }
        });
        binding.goldPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gymPackage = "gold";
                binding.goldImage.setImageResource(R.drawable.checkbox_chkd);
                binding.sliverImage.setImageResource(R.drawable.checkbox_unchecked);
                binding.platinumImage.setImageResource(R.drawable.checkbox_unchecked);
            }
        });
        binding.platinumPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gymPackage = "platinum";
                binding.platinumImage.setImageResource(R.drawable.checkbox_chkd);
                binding.goldImage.setImageResource(R.drawable.checkbox_unchecked);
                binding.sliverImage.setImageResource(R.drawable.checkbox_unchecked);
            }
        });

        binding.countinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.userModel.setGymPackage(gymPackage);
                FirebaseFirestore.getInstance().collection("users")
                        .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .update("gymPackage", gymPackage)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                startActivity(new Intent(GymPackageActivity.this, MainActivity.class));
                            }
                        });
            }
        });

    }
}