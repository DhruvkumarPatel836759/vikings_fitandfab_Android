package com.example.vikings_fitandfab_android;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.example.vikings_fitandfab_android.databinding.ActivityWorkoutBinding;

public class WorkoutActivity extends AppCompatActivity {

    ActivityWorkoutBinding binding;

    private MaterialButton btnNext;
    private TextView exNumber;
    private int counter = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWorkoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        btnNext = findViewById(R.id.btnNext);
        exNumber = findViewById(R.id.exNumber);

        findViewById(R.id.backImageView)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                });

        if (getIntent().getStringExtra("ex").equals("chest")) {
            binding.chestFlipper.setVisibility(View.VISIBLE);
            binding.backFlipper.setVisibility(View.GONE);
            binding.shoulderFlipper.setVisibility(View.GONE);
            binding.fullBodyFlipper.setVisibility(View.GONE);
            binding.tricepesFlipper.setVisibility(View.GONE);
            binding.bicepsFlipper.setVisibility(View.GONE);
            binding.glutesFlipper.setVisibility(View.GONE);
            binding.exNumber.setText("WORKOUT :  1 / 15");
            binding.text.setText("CHEST");




//
//




            btnNext.setOnClickListener(new View.OnClickListener() {
                @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
                @Override
                public void onClick(View v) {
                    if (binding.chestFlipper.getDisplayedChild() <= 15) {
                        binding.chestFlipper.setInAnimation(WorkoutActivity.this, R.anim.slide_right_animation);
                        binding.chestFlipper.setOutAnimation(WorkoutActivity.this, R.anim.slide_left_animation);
                        binding.chestFlipper.showNext();

                        counter++;
                        exNumber.setText("WORKOUT : " + " " + (counter) + " " + "/ 15");
                        if (counter > 14) {
                            btnNext.setClickable(false);
                            btnNext.setText("DONE");
                            btnNext.setIcon(getResources().getDrawable(R.drawable.ic_done));
                            btnNext.setBackgroundColor(Color.GREEN);


                        }
                    }
                }
            });


        }
        else if (getIntent().getStringExtra("ex").equals("back")) {
            binding.chestFlipper.setVisibility(View.GONE);
            binding.backFlipper.setVisibility(View.VISIBLE);
            binding.shoulderFlipper.setVisibility(View.GONE);
            binding.fullBodyFlipper.setVisibility(View.GONE);
            binding.tricepesFlipper.setVisibility(View.GONE);
            binding.bicepsFlipper.setVisibility(View.GONE);
            binding.glutesFlipper.setVisibility(View.GONE);
            binding.exNumber.setText("WORKOUT :  1 / 17");
            binding.text.setText("BACK");

            btnNext.setOnClickListener(new View.OnClickListener() {
                @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
                @Override
                public void onClick(View v) {
                    if (binding.backFlipper.getDisplayedChild() <= 17) {
                        binding.backFlipper.setInAnimation(WorkoutActivity.this, R.anim.slide_right_animation);
                        binding.backFlipper.setOutAnimation(WorkoutActivity.this, R.anim.slide_left_animation);
                        binding.backFlipper.showNext();

                        counter++;
                        exNumber.setText("WORKOUT : " + " " + (counter) + " " + "/ 17");
                        if (counter > 16) {
                            btnNext.setClickable(false);
                            btnNext.setText("DONE");
                            btnNext.setIcon(getResources().getDrawable(R.drawable.ic_done));
                            btnNext.setBackgroundColor(Color.GREEN);


                        }
                    }
                }
            });

        }
        else if (getIntent().getStringExtra("ex").equals("shoulder")) {
            binding.chestFlipper.setVisibility(View.GONE);
            binding.backFlipper.setVisibility(View.GONE);
            binding.shoulderFlipper.setVisibility(View.VISIBLE);
            binding.fullBodyFlipper.setVisibility(View.GONE);
            binding.tricepesFlipper.setVisibility(View.GONE);
            binding.bicepsFlipper.setVisibility(View.GONE);
            binding.glutesFlipper.setVisibility(View.GONE);
            binding.exNumber.setText("WORKOUT :  1 / 17");
            binding.text.setText("SHOULDERS");

            btnNext.setOnClickListener(new View.OnClickListener() {
                @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
                @Override
                public void onClick(View v) {
                    if (binding.shoulderFlipper.getDisplayedChild() <= 19) {
                        binding.shoulderFlipper.setInAnimation(WorkoutActivity.this, R.anim.slide_right_animation);
                        binding.shoulderFlipper.setOutAnimation(WorkoutActivity.this, R.anim.slide_left_animation);
                        binding.shoulderFlipper.showNext();

                        counter++;
                        exNumber.setText("WORKOUT : " + " " + (counter) + " " + "/ 19");
                        if (counter > 18) {
                            btnNext.setClickable(false);
                            btnNext.setText("DONE");
                            btnNext.setIcon(getResources().getDrawable(R.drawable.ic_done));
                            btnNext.setBackgroundColor(Color.GREEN);


                        }
                    }
                }
            });

        }
        else if (getIntent().getStringExtra("ex").equals("full")) {
            binding.chestFlipper.setVisibility(View.GONE);
            binding.backFlipper.setVisibility(View.GONE);
            binding.shoulderFlipper.setVisibility(View.GONE);
            binding.fullBodyFlipper.setVisibility(View.VISIBLE);
            binding.tricepesFlipper.setVisibility(View.GONE);
            binding.bicepsFlipper.setVisibility(View.GONE);
            binding.glutesFlipper.setVisibility(View.GONE);
            binding.exNumber.setText("WORKOUT :  1 / 13");
            binding.text.setText("FULLBODY");

            btnNext.setOnClickListener(new View.OnClickListener() {
                @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
                @Override
                public void onClick(View v) {
                    if (binding.fullBodyFlipper.getDisplayedChild() <= 13) {
                        binding.fullBodyFlipper.setInAnimation(WorkoutActivity.this, R.anim.slide_right_animation);
                        binding.fullBodyFlipper.setOutAnimation(WorkoutActivity.this, R.anim.slide_left_animation);
                        binding.fullBodyFlipper.showNext();

                        counter++;
                        exNumber.setText("WORKOUT : " + " " + (counter) + " " + "/ 13");
                        if (counter > 12) {
                            btnNext.setClickable(false);
                            btnNext.setText("DONE");
                            btnNext.setIcon(getResources().getDrawable(R.drawable.ic_done));
                            btnNext.setBackgroundColor(Color.GREEN);


                        }
                    }
                }
            });
        }
        else if (getIntent().getStringExtra("ex").equals("triceps")) {
            binding.chestFlipper.setVisibility(View.GONE);
            binding.backFlipper.setVisibility(View.GONE);
            binding.shoulderFlipper.setVisibility(View.GONE);
            binding.fullBodyFlipper.setVisibility(View.GONE);
            binding.tricepesFlipper.setVisibility(View.VISIBLE);
            binding.bicepsFlipper.setVisibility(View.GONE);
            binding.glutesFlipper.setVisibility(View.GONE);
            binding.exNumber.setText("WORKOUT :  1 / 13");
            binding.text.setText("TRICEPS");

            btnNext.setOnClickListener(new View.OnClickListener() {
                @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
                @Override
                public void onClick(View v) {
                    if (binding.tricepesFlipper.getDisplayedChild() <= 13) {
                        binding.tricepesFlipper.setInAnimation(WorkoutActivity.this, R.anim.slide_right_animation);
                        binding.tricepesFlipper.setOutAnimation(WorkoutActivity.this, R.anim.slide_left_animation);
                        binding.tricepesFlipper.showNext();

                        counter++;
                        exNumber.setText("WORKOUT : " + " " + (counter) + " " + "/ 13");
                        if (counter > 12) {
                            btnNext.setClickable(false);
                            btnNext.setText("DONE");
                            btnNext.setIcon(getResources().getDrawable(R.drawable.ic_done));
                            btnNext.setBackgroundColor(Color.GREEN);


                        }
                    }
                }
            });
        }
        else if (getIntent().getStringExtra("ex").equals("biceps")) {
            binding.chestFlipper.setVisibility(View.GONE);
            binding.backFlipper.setVisibility(View.GONE);
            binding.shoulderFlipper.setVisibility(View.GONE);
            binding.fullBodyFlipper.setVisibility(View.GONE);
            binding.tricepesFlipper.setVisibility(View.GONE);
            binding.bicepsFlipper.setVisibility(View.VISIBLE);
            binding.glutesFlipper.setVisibility(View.GONE);
            binding.text.setText("BICEPS");

            binding.exNumber.setText("WORKOUT :  1 / 14");
            btnNext.setOnClickListener(new View.OnClickListener() {
                @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
                @Override
                public void onClick(View v) {
                    if (binding.bicepsFlipper.getDisplayedChild() <= 14) {
                        binding.bicepsFlipper.setInAnimation(WorkoutActivity.this, R.anim.slide_right_animation);
                        binding.bicepsFlipper.setOutAnimation(WorkoutActivity.this, R.anim.slide_left_animation);
                        binding.bicepsFlipper.showNext();

                        counter++;
                        exNumber.setText("WORKOUT : " + " " + (counter) + " " + "/ 14");
                        if (counter > 13) {
                            btnNext.setClickable(false);
                            btnNext.setText("DONE");
                            btnNext.setIcon(getResources().getDrawable(R.drawable.ic_done));
                            btnNext.setBackgroundColor(Color.GREEN);


                        }
                    }
                }
            });

        }
        else if (getIntent().getStringExtra("ex").equals("glutes")) {
            binding.chestFlipper.setVisibility(View.GONE);
            binding.backFlipper.setVisibility(View.GONE);
            binding.shoulderFlipper.setVisibility(View.GONE);
            binding.fullBodyFlipper.setVisibility(View.GONE);
            binding.tricepesFlipper.setVisibility(View.GONE);
            binding.bicepsFlipper.setVisibility(View.GONE);
            binding.glutesFlipper.setVisibility(View.VISIBLE);
            binding.text.setText("GLUTES");

            binding.exNumber.setText("WORKOUT :  1 / 15");

            btnNext.setOnClickListener(new View.OnClickListener() {
                @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
                @Override
                public void onClick(View v) {
                    if (binding.glutesFlipper.getDisplayedChild() <= 5) {
                        binding.glutesFlipper.setInAnimation(WorkoutActivity.this, R.anim.slide_right_animation);
                        binding.glutesFlipper.setOutAnimation(WorkoutActivity.this, R.anim.slide_left_animation);
                        binding.glutesFlipper.showNext();

                        counter++;
                        exNumber.setText("WORKOUT : " + " " + (counter) + " " + "/ 5");
                        if (counter > 4) {
                            btnNext.setClickable(false);
                            btnNext.setText("DONE");
                            btnNext.setIcon(getResources().getDrawable(R.drawable.ic_done));
                            btnNext.setBackgroundColor(Color.GREEN);


                        }
                    }
                }
            });

        }
        else if (getIntent().getStringExtra("ex").equals("legs")) {
            binding.chestFlipper.setVisibility(View.GONE);
            binding.backFlipper.setVisibility(View.GONE);
            binding.shoulderFlipper.setVisibility(View.GONE);
            binding.fullBodyFlipper.setVisibility(View.GONE);
            binding.tricepesFlipper.setVisibility(View.GONE);
            binding.bicepsFlipper.setVisibility(View.GONE);
            binding.glutesFlipper.setVisibility(View.GONE);
            binding.legsFlipper.setVisibility(View.VISIBLE);
            binding.absFlipper.setVisibility(View.GONE);
            binding.text.setText("LEGS");

            binding.exNumber.setText("WORKOUT :  1 / 8");

            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (binding.legsFlipper.getDisplayedChild() <= 8) {
                        binding.legsFlipper.setInAnimation(WorkoutActivity.this, R.anim.slide_right_animation);
                        binding.legsFlipper.setOutAnimation(WorkoutActivity.this, R.anim.slide_left_animation);
                        binding.legsFlipper.showNext();

                        counter++;
                        exNumber.setText("WORKOUT : " + " " + (counter) + " " + "/ 8");
                        if (counter > 7) {
                            btnNext.setClickable(false);
                            btnNext.setText("DONE");
                            btnNext.setIcon(getResources().getDrawable(R.drawable.ic_done));
                            btnNext.setBackgroundColor(Color.GREEN);


                        }
                    }
                }
            });

        }
        else if (getIntent().getStringExtra("ex").equals("abs")) {
            binding.chestFlipper.setVisibility(View.GONE);
            binding.backFlipper.setVisibility(View.GONE);
            binding.shoulderFlipper.setVisibility(View.GONE);
            binding.fullBodyFlipper.setVisibility(View.GONE);
            binding.tricepesFlipper.setVisibility(View.GONE);
            binding.bicepsFlipper.setVisibility(View.GONE);
            binding.glutesFlipper.setVisibility(View.GONE);
            binding.legsFlipper.setVisibility(View.GONE);
            binding.absFlipper.setVisibility(View.VISIBLE);
            binding.text.setText("ABS");

            binding.exNumber.setText("WORKOUT :  1 / 7");

            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (binding.absFlipper.getDisplayedChild() <= 7) {
                        binding.absFlipper.setInAnimation(WorkoutActivity.this, R.anim.slide_right_animation);
                        binding.absFlipper.setOutAnimation(WorkoutActivity.this, R.anim.slide_left_animation);
                        binding.absFlipper.showNext();

                        counter++;
                        exNumber.setText("WORKOUT : " + " " + (counter) + " " + "/ 7");
                        if (counter > 6) {
                            btnNext.setClickable(false);
                            btnNext.setText("DONE");
                            btnNext.setIcon(getResources().getDrawable(R.drawable.ic_done));
                            btnNext.setBackgroundColor(Color.GREEN);


                        }
                    }
                }
            });
        }



    }
}