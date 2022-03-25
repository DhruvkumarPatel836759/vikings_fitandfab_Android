package com.example.vikings_fitandfab_android;

import static com.example.vikings_fitandfab_android.LoginActivity.userModel;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.vikings_fitandfab_android.databinding.ActivityProfileBinding;

import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {

    ActivityProfileBinding binding;
    ProgressDialog progressDialog;
    String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);
        progressDialog.setMessage("Updating Profile...");
        progressDialog.setCancelable(false);


        findViewById(R.id.backImageView)
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.nameEditText.setText(userModel.getName());
        binding.ageEditText.setText(userModel.getAge());
        binding.alergiesEditText.setText(userModel.getAllergie());
        binding.emailEditText.setText(userModel.getEmail());
        binding.weightEditText.setText(userModel.getWeight());
        binding.heightEditText.setText(userModel.getHeight());
        gender = userModel.getGender();

        binding.emailEditText.setEnabled(false);

        String[] genderlist = {"Male", "Female"};

        ArrayAdapter<String> genderadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genderlist);
        binding.genderSpinner.setAdapter(genderadapter);
        if (gender.equals("Male")) {
            binding.genderSpinner.setSelection(0);
        } else {
            binding.genderSpinner.setSelection(1);
        }
        binding.genderSpinner.setDropDownVerticalOffset(100);
        binding.genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                gender = binding.genderSpinner.getSelectedItem().toString();

            }

            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        binding.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                String Name = binding.nameEditText.getText().toString();
                String age = binding.ageEditText.getText().toString();
                String allergie = binding.alergiesEditText.getText().toString();
                String weight = binding.weightEditText.getText().toString();
                String height = binding.heightEditText.getText().toString();


                if (Name.isEmpty() || age.isEmpty() || height.isEmpty() || allergie.isEmpty() || weight.isEmpty()) {
                    progressDialog.dismiss();
                    Toast.makeText(ProfileActivity.this, "All Fields Required", Toast.LENGTH_SHORT).show();
                } else {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("name", Name);
                    hashMap.put("age", age);
                    hashMap.put("height", height);
                    hashMap.put("weight", weight);
                    hashMap.put("gender", gender);
                    hashMap.put("allergie", allergie);

                    FirebaseFirestore.getInstance().collection("users")
                            .document(FirebaseAuth.getInstance().getCurrentUser().getUid()).update(hashMap)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    userModel.setAge(age);
                                    userModel.setName(Name);
                                    userModel.setHeight(height);
                                    userModel.setGender(gender);
                                    userModel.setAllergie(allergie);
                                    userModel.setWeight(weight);
                                    progressDialog.dismiss();
                                    Toast.makeText(ProfileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });


    }
}