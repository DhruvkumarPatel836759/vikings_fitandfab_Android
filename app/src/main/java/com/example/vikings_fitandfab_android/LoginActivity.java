package com.example.vikings_fitandfab_android;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vikings_fitandfab_android.Admin.AdminActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.vikings_fitandfab_android.Class.UserModel;
import com.example.vikings_fitandfab_android.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    ProgressDialog progressDialog;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public static UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);
        progressDialog.setMessage("Loading Please Wait....");
        progressDialog.setCancelable(false);


        binding.eyeopenImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.passwordEditText.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                    binding.passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    binding.eyeopenImageView.setImageResource(R.drawable.hide);

                } else {
                    binding.eyeopenImageView.setImageResource(R.drawable.show);
                    binding.passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                binding.passwordEditText.setSelection(binding.passwordEditText.length());
            }
        });

        if (!UserTypeActivity.user) {
            binding.signUpText.setVisibility(View.GONE);
        } else {
            binding.signUpText.setVisibility(View.VISIBLE);
        }
        binding.signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                finish();
            }
        });


        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!UserTypeActivity.user) {
                    progressDialog.show();
                    FirebaseFirestore.getInstance()
                            .collection("admin")
                            .document("1QqXKFnkBiXGcWpz4N4d")
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if (documentSnapshot.exists()) {

                                        String email = documentSnapshot.get("mail").toString();
                                        String password = documentSnapshot.get("password").toString();

                                        if (binding.emailEditText.getText().toString().equals(email) &&
                                                binding.passwordEditText.getText().toString().equals(password)) {
                                            progressDialog.dismiss();
                                            getSharedPreferences("Gym_ref", MODE_PRIVATE).edit()
                                                    .putString("admin", "admin")
                                                    .commit();

                                            startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                                            finish();


                                        } else {
                                            progressDialog.dismiss();
                                            Toast.makeText(LoginActivity.this, "Email or Password incorrect", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                }
                            });
                } else {

                    String Email = binding.emailEditText.getText().toString();
                    String Password = binding.passwordEditText.getText().toString();
                    if (Email.isEmpty() || Password.isEmpty()) {

                        Toast.makeText(LoginActivity.this, "All Fields Required", Toast.LENGTH_SHORT).show();
                    } else if (!Email.matches(emailPattern)) {
                        Toast.makeText(LoginActivity.this, "Please Enter Valid Email", Toast.LENGTH_SHORT).show();

                    } else {
                        progressDialog.show();
                        FirebaseAuth.getInstance()
                                .signInWithEmailAndPassword(Email, Password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (!task.isSuccessful()) {
                                            progressDialog.dismiss();
                                            Toast.makeText(LoginActivity.this, "Failed to Login", Toast.LENGTH_LONG).show();
                                        }
                                        if (task.isSuccessful()) {

                                            FirebaseFirestore.getInstance()
                                                    .collection("users")
                                                    .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                    .get()
                                                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                            progressDialog.dismiss();
                                                            if (documentSnapshot != null) {
                                                                userModel = documentSnapshot.toObject(UserModel.class);
                                                                if (userModel.getGymPackage() == null) {
                                                                    startActivity(new Intent(LoginActivity.this, GymPackageActivity.class));
                                                                    finish();

                                                                } else {
                                                                    startActivity(new Intent(LoginActivity.this, DrawerActivity.class));
                                                                    finish();
                                                                }

                                                            }
                                                        }
                                                    });
                                        }
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.dismiss();
                                        Toast.makeText(LoginActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }

            }
        });
    }
}