package com.example.vikings_fitandfab_android;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.vikings_fitandfab_android.databinding.ActivitySignupBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class SignupActivity extends AppCompatActivity {

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    ActivitySignupBinding binding;

    String gender="Male";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        progressDialog = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);
        progressDialog.setMessage("Loading Please Wait....");
        progressDialog.setCancelable(false);



        String[] genderlist = {"Male", "Female"};


        ArrayAdapter<String> genderadapter =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,genderlist);
        binding.genderSpinner.setAdapter(genderadapter);
        binding.genderSpinner.setDropDownVerticalOffset(100);
        binding.genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                gender = binding.genderSpinner.getSelectedItem().toString();

            }

            public void onNothingSelected(AdapterView<?> parent) {


            }
        });



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
        findViewById(R.id.eyecloseImageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.confrimEdithText.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                    binding.confrimEdithText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    binding.eyecloseImageView.setImageResource(R.drawable.hide);

                } else {
                    binding.confrimEdithText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    binding.eyecloseImageView.setImageResource(R.drawable.show);
                }
                binding.confrimEdithText.setSelection(binding.confrimEdithText.length());
            }
        });

        findViewById(R.id.logonText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                finish();
            }
        });

        findViewById(R.id.registorButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Name = binding.nameEditText.getText().toString();
                String Email = binding.emailEditText.getText().toString();
                String age = binding.ageEditText.getText().toString();
                String allergie = binding.alergiesEditText.getText().toString();
                String weight = binding.weightEditText.getText().toString();
                String height = binding.heightEditText.getText().toString();
                String Password = binding.passwordEditText.getText().toString();
                String confirmPassword = binding.confrimEdithText.getText().toString();

                if (Name.isEmpty() || Email.isEmpty() || age.isEmpty() || Password.isEmpty() ||height.isEmpty() || allergie.isEmpty() || weight.isEmpty() || confirmPassword.isEmpty()) {

                    Toast.makeText(SignupActivity.this, "All Fields Required", Toast.LENGTH_SHORT).show();
                } else if (!Email.matches(emailPattern)) {
                    Toast.makeText(SignupActivity.this, "Please Enter Valid Email", Toast.LENGTH_SHORT).show();

                }
                else if (!Password.equals(confirmPassword)) {
                    Toast.makeText(SignupActivity.this, "Password not matches", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.show();
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(Email, Password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {


                                        HashMap<String, Object> hashMap = new HashMap<>();
                                        hashMap.put("name", Name);
                                        hashMap.put("email", Email);
                                        hashMap.put("age", age);
                                        hashMap.put("height", height);
                                        hashMap.put("weight", weight);
                                        hashMap.put("gender", gender);
                                        hashMap.put("allergie", allergie);

                                        FirebaseFirestore.getInstance()
                                                .collection("users")
                                                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .set(hashMap)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            progressDialog.dismiss();
                                                            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                                                            startActivity(intent);
                                                            finish();

                                                        }
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {

                                                        Log.e("SignUp", "faliureupload" + e.getLocalizedMessage());
                                                    }
                                                });


                                    }
                                }


                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });
                }

            }
        });


    }



}