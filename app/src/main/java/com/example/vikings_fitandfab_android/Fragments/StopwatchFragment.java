package com.example.vikings_fitandfab_android.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.vikings_fitandfab_android.R;
import com.yashovardhan99.timeit.Stopwatch;



public class StopwatchFragment extends Fragment {


    TextView stopwatchText;
    Button startButton;
    boolean start=false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stopwatch, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        stopwatchText=view.findViewById(R.id.stopwatchText);
        startButton=view.findViewById(R.id.startButton);


        Stopwatch stopwatch = new Stopwatch();
        stopwatch.setTextView(stopwatchText);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             if (!start){
                 startButton.setText("Stop");
                 stopwatch.start();
                 start=true;
             }else{
                 startButton.setText("Start");
                 stopwatch.stop();
                 start=false;
             }


            }
        });





    }
}