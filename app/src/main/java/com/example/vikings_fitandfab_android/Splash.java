package com.example.vikings_fitandfab_android;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vikings_fitandfab_android.Admin.AdminActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.vikings_fitandfab_android.Admin.AdminActivity;
import com.example.vikings_fitandfab_android.Class.UserModel;

public class Splash extends AppCompatActivity {
    String admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        admin = getSharedPreferences("Gym_ref", MODE_PRIVATE).getString("admin", "");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    UserTypeActivity.user = true;
                    FirebaseFirestore.getInstance()
                            .collection("users")
                            .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {

                                    if (documentSnapshot != null) {
                                        LoginActivity.userModel = documentSnapshot.toObject(UserModel.class);
                                        if (LoginActivity.userModel.getGymPackage() == null) {
                                            startActivity(new Intent(Splash.this, GymPackageActivity.class));
                                            finish();

                                        } else {
                                            startActivity(new Intent(Splash.this, DrawerActivity.class));
                                            finish();
                                        }

                                    }
                                }
                            });

                } else if (admin.equals("admin")) {
                    UserTypeActivity.user = false;
                    startActivity(new Intent(Splash.this, AdminActivity.class));
                    finish();

                } else {
                    startActivity(new Intent(Splash.this, UserTypeActivity.class));
                    finish();
                }

            }
        }, 2500);
    }
}