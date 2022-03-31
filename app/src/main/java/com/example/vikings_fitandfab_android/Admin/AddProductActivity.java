package com.example.vikings_fitandfab_android.Admin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.example.vikings_fitandfab_android.Class.SupplimentModel;
import com.example.vikings_fitandfab_android.R;
import com.example.vikings_fitandfab_android.databinding.ActivityAddProductBinding;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class AddProductActivity extends AppCompatActivity {


    StorageReference mstorageref;
    private Uri imageUri;
    ActivityAddProductBinding binding;
    private ProgressDialog progressDialog;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);
        progressDialog.setMessage("Please Wait....");
        progressDialog.setCancelable(false);
        mstorageref = FirebaseStorage.getInstance().getReference();

        findViewById(R.id.backImageView)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                });

        if (getIntent().getStringExtra("action").equals("edit")) {
            SupplimentModel supplimentModel = (SupplimentModel) getIntent().getSerializableExtra("product");
            binding.nameEditText.setText(supplimentModel.getName());
            binding.disEditText.setText(supplimentModel.getDiscription());
            binding.typeEditText.setText(supplimentModel.getType());
            binding.priceEditText.setText("" + supplimentModel.getPrice());
            binding.quantityEditText.setText("" + supplimentModel.getQuantity());
            Glide.with(this).load(supplimentModel.getImage()).into(binding.imgPost);
            binding.addButton.setVisibility(View.GONE);
            binding.updateButton.setVisibility(View.VISIBLE);
            id = supplimentModel.getsId();
            binding.text.setText("Edit Product");
        } else {
            binding.addButton.setVisibility(View.VISIBLE);
            binding.updateButton.setVisibility(View.GONE);
            binding.text.setText("Add Product");
        }


        binding.imgPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 100);

            }
        });
        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (imageUri == null) {
                    Toast.makeText(AddProductActivity.this, "Please add product image", Toast.LENGTH_SHORT).show();

                } else if (binding.nameEditText.getText().toString().isEmpty() || binding.quantityEditText.getText().toString().isEmpty()
                        || binding.disEditText.getText().toString().isEmpty() || binding.typeEditText.getText().toString().isEmpty()
                        || binding.priceEditText.getText().toString().isEmpty()) {
                    Toast.makeText(AddProductActivity.this, "All text fields required", Toast.LENGTH_SHORT).show();
                } else {
                    double price = Double.parseDouble(binding.priceEditText.getText().toString());
                    int quantity = Integer.parseInt(binding.quantityEditText.getText().toString());

                    hideKeyboard(AddProductActivity.this);
                    progressDialog.show();
                    final StorageReference reference = mstorageref.child("Image/-" + System.currentTimeMillis());
                    reference.putFile(imageUri)
                            .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NotNull Task<UploadTask.TaskSnapshot> task) {
                                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri productImage) {
                                            if (task.isSuccessful()) {
                                                String id = FirebaseFirestore.getInstance()
                                                        .collection("suppliments").document().getId();
                                                HashMap userHash = new HashMap<>();
                                                userHash.put("discription", binding.disEditText.getText().toString());
                                                userHash.put("image", productImage.toString());
                                                userHash.put("name", binding.nameEditText.getText().toString());
                                                userHash.put("type", binding.typeEditText.getText().toString());
                                                userHash.put("quantity", quantity);
                                                userHash.put("price", price);

//
                                                FirebaseFirestore.getInstance().collection("suppliments")
                                                        .document(id)
                                                        .set(userHash)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void unused) {

                                                                progressDialog.dismiss();
                                                                Toast.makeText(AddProductActivity.this, "Product add sucessfully", Toast.LENGTH_SHORT).show();
                                                                binding.nameEditText.setText("");
                                                                binding.typeEditText.setText("");
                                                                binding.quantityEditText.setText("");
                                                                binding.priceEditText.setText("");
                                                                binding.disEditText.setText("");
                                                                binding.imgPost.setImageURI(null);


                                                            }
                                                        });

                                            } else {
                                                progressDialog.dismiss();
                                                Toast.makeText(AddProductActivity.this, "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                    });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(AddProductActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });


                }


            }
        });
        binding.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (binding.nameEditText.getText().toString().isEmpty() || binding.typeEditText.getText().toString().isEmpty()
                        || binding.priceEditText.getText().toString().isEmpty() || binding.quantityEditText.getText().toString().isEmpty()
                        || binding.disEditText.getText().toString().isEmpty()) {
                    Toast.makeText(AddProductActivity.this, "All text fields required", Toast.LENGTH_SHORT).show();
                }
                else {
                    hideKeyboard(AddProductActivity.this);
                    double price = Double.parseDouble(binding.priceEditText.getText().toString());
                    int quantity = Integer.parseInt(binding.quantityEditText.getText().toString());
                    progressDialog.show();
                    if (imageUri == null) {
                        HashMap userHash = new HashMap<>();
                        userHash.put("discription", binding.disEditText.getText().toString());
                        userHash.put("name", binding.nameEditText.getText().toString());
                        userHash.put("type", binding.typeEditText.getText().toString());
                        userHash.put("quantity", quantity);
                        userHash.put("price", price);

//
                        FirebaseFirestore.getInstance().collection("suppliments")
                                .document(id)
                                .update(userHash)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                        progressDialog.dismiss();
                                        Toast.makeText(AddProductActivity.this, "Product Update sucessfully", Toast.LENGTH_SHORT).show();


                                    }
                                });
                    }
                    else {
                        final StorageReference reference = mstorageref.child("Image/-" + System.currentTimeMillis());
                        reference.putFile(imageUri)
                                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onComplete(@NotNull Task<UploadTask.TaskSnapshot> task) {
                                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri productImage) {
                                                if (task.isSuccessful()) {

                                                    HashMap userHash = new HashMap<>();
                                                    userHash.put("discription", binding.disEditText.getText().toString());
                                                    userHash.put("image", productImage.toString());
                                                    userHash.put("name", binding.nameEditText.getText().toString());
                                                    userHash.put("type", binding.typeEditText.getText().toString());
                                                    userHash.put("quantity", quantity);
                                                    userHash.put("price", price);
//
                                                    FirebaseFirestore.getInstance().collection("suppliments")
                                                            .document(id)
                                                            .update(userHash)
                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void unused) {

                                                                    progressDialog.dismiss();
                                                                    Toast.makeText(AddProductActivity.this, "Product Update sucessfully", Toast.LENGTH_SHORT).show();

                                                                }
                                                            });

                                                } else {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(AddProductActivity.this, "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                        });
                                    }
                                });
                    }


                }


            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            imageUri = data.getData();
            binding.imgPost.setImageURI(imageUri);
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}