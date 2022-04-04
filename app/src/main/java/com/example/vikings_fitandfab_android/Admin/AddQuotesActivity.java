package com.example.vikings_fitandfab_android.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.example.vikings_fitandfab_android.R;
import com.example.vikings_fitandfab_android.databinding.ActivityAddProductBinding;
import com.example.vikings_fitandfab_android.databinding.ActivityAddQuotesBinding;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class AddQuotesActivity extends AppCompatActivity {

    StorageReference mstorageref;
    private Uri imageUri;
    ActivityAddQuotesBinding binding;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddQuotesBinding.inflate(getLayoutInflater());
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
                    Toast.makeText(AddQuotesActivity.this, "Please add Background image", Toast.LENGTH_SHORT).show();

                } else if (binding.nameEditText.getText().toString().isEmpty() || binding.quoteEditText.getText().toString().isEmpty()) {
                    Toast.makeText(AddQuotesActivity.this, "All text fields required", Toast.LENGTH_SHORT).show();
                } else {

                    hideKeyboard(AddQuotesActivity.this);
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
                                                        .collection("quotes").document().getId();
                                                HashMap userHash = new HashMap<>();
                                                userHash.put("quotes", binding.quoteEditText.getText().toString());
                                                userHash.put("image", productImage.toString());
                                                userHash.put("authorName", binding.nameEditText.getText().toString());


//
                                                FirebaseFirestore.getInstance().collection("quotes")
                                                        .document(id)
                                                        .set(userHash)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void unused) {

                                                                progressDialog.dismiss();
                                                                Toast.makeText(AddQuotesActivity.this, "Quote add successfully", Toast.LENGTH_SHORT).show();
                                                                binding.nameEditText.setText("");
                                                                binding.quoteEditText.setText("");
                                                                binding.imgPost.setImageURI(null);


                                                            }
                                                        });

                                            } else {
                                                progressDialog.dismiss();
                                                Toast.makeText(AddQuotesActivity.this, "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                    });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(AddQuotesActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });


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