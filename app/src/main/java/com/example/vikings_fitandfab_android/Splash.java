package com.example.vikings_fitandfab_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;

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
                    UserTypeActivity.user=true;
                    startActivity(new Intent(Splash.this, MainActivity.class));
                    finish();
//                    FirebaseFirestore.getInstance()
//                            .collection("users")
//                            .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                            .get()
//                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                                @Override
//                                public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                    if (documentSnapshot != null) {
//                                        userModel = documentSnapshot.toObject(UserModel.class);
//                                        if (userModel.isVerification()) {
//                                            startActivity(new Intent(Splash.this, UserMainActivity.class));
//                                            finish();
//                                        }
//                                        else {
//                                            FirebaseAuth.getInstance().signOut();
//                                            startActivity(new Intent(Splash.this, UserTypeActivity.class));
//                                            finish();
//                                            Toast.makeText(Splash.this, "Admin remove Verification. Please contact admin ", Toast.LENGTH_SHORT).show();
//                                        }
//
//                                    }
//                                }
//                            });


                }
                else if (admin.equals("admin")) {
                    UserTypeActivity.user=false;
                    startActivity(new Intent(Splash.this, MainActivity.class));
                    finish();

                }
                else {
                    startActivity(new Intent(Splash.this, UserTypeActivity.class));
                    finish();
                }

            }
        }, 2500);
    }
}
