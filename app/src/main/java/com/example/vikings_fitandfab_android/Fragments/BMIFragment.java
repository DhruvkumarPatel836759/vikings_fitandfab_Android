package com.example.vikings_fitandfab_android.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.vikings_fitandfab_android.Class.AppPref;
import com.example.vikings_fitandfab_android.Class.Formula;
import com.example.vikings_fitandfab_android.Class.Utils;
import com.example.vikings_fitandfab_android.R;

import java.util.Objects;

public class BMIFragment extends Fragment implements View.OnClickListener, TextWatcher {
    //
    Button btnkgbmi;
    Button btnlbbmi;
    EditText heightEditText;
    TextView heightTextbmi;
    EditText heightinchedittext;
    TextView heightinchtextbmi;
    String kglbval;
    //    ImageView popupBMIButton, btnBack;
    TextView resulthealthyweighttextView;
    TextView resultidealweighttextView;
    TextView resultoverweighttextView;
    SeekBar seekBar;
    TextView txtbmical;
    TextView txtbmivalue;
    EditText weightEditText;
    TextView weightTextbmi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_b_m_i, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        weightEditText = view.findViewById(R.id.weightEditTextbmi);
        heightEditText = view.findViewById(R.id.heightEditTextbmi);
        weightEditText.addTextChangedListener(this);
        heightEditText.addTextChangedListener(this);
        heightinchedittext = view.findViewById(R.id.height_inch_EditTextbmi);
        heightinchedittext.addTextChangedListener(this);
        resulthealthyweighttextView = view.findViewById(R.id.result_healthyWeight_textView);
        resultoverweighttextView = view.findViewById(R.id.result_overWeight_textView);
        resultidealweighttextView = view.findViewById(R.id.result_idealWeight_textView);
        seekBar = view.findViewById(R.id.seekBarIndicator);
        seekBar.setEnabled(false);
        weightTextbmi = view.findViewById(R.id.weightTextbmi);
        heightTextbmi = view.findViewById(R.id.heightTextbmi);
        heightinchtextbmi = view.findViewById(R.id.height_inch_Textbmi);
        txtbmical = view.findViewById(R.id.txtbmi_cal);
        txtbmivalue = view.findViewById(R.id.txtbmi_value);
//        popupBMIButton = view.findViewById(R.id.popupBMIButton);
//        popupBMIButton.setOnClickListener(this);
        btnkgbmi = view.findViewById(R.id.btnKG_BMI);
        btnkgbmi.setOnClickListener(this);
        btnlbbmi = view.findViewById(R.id.btnLB_BMI);
        btnlbbmi.setOnClickListener(this);
    }

    public void onClick(View view) {
//        if (view.getId() == this.popupBMIButton.getId()) {
//            PopupMenu popupMenu = new PopupMenu(getActivity(), view);
//            popupMenu.getMenuInflater().inflate(R.menu.popup_menu_frag_bmi, popupMenu.getMenu());
//            popupMenu.show();
//            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                public boolean onMenuItemClick(MenuItem menuItem) {
//                    final Dialog dialog = new Dialog(getActivity());
//                    dialog.setContentView(R.layout.dialog_bmi_chart_table);
//                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
//                    dialog.findViewById(R.id.buttonokbmi_table).setOnClickListener(new View.OnClickListener() {
//                        public void onClick(View view) {
//                            dialog.dismiss();
//                        }
//                    });
//                    dialog.show();
//                    return false;
//                }
//            });
//        }
        if (view.getId() == this.btnkgbmi.getId()) {
            AppPref.setKglbcalculation(getActivity(), true);
            setBtnBack();
            this.heightinchedittext.setVisibility(View.GONE);
            this.heightinchtextbmi.setVisibility(View.GONE);
            this.weightEditText.setText("");
            this.heightEditText.setText("");
            this.heightinchedittext.setText("");
            this.heightTextbmi.setText("cm");
            this.weightTextbmi.setText(getActivity().getResources().getString(R.string.Kg));
            setTextViewDefault();
        }
        if (view.getId() == this.btnlbbmi.getId()) {
            this.weightTextbmi.setText(getActivity().getResources().getString(R.string.lb));
            AppPref.setKglbcalculation(getActivity(), false);
            this.heightinchedittext.setVisibility(View.VISIBLE);
            this.weightEditText.setText("");
            this.heightEditText.setText("");
            this.heightinchedittext.setText("");
            this.heightinchtextbmi.setVisibility(View.VISIBLE);
            this.heightinchtextbmi.setText("in");
            this.heightTextbmi.setText("ft");
            setTextViewDefault();
            setBtnBack();
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void setBtnBack() {
        this.btnkgbmi.setBackground(AppPref.iskglbcalcu(requireActivity()) ?
                getResources().getDrawable(R.drawable.a2_grad) :
                getResources().getDrawable(R.drawable.maleback));
        this.btnkgbmi.setTextColor(AppPref.iskglbcalcu(getActivity()) ? getResources().getColor(R.color.white) : getResources().getColor(R.color.black));
        this.btnlbbmi.setTextColor(!AppPref.iskglbcalcu(getActivity()) ? getResources().getColor(R.color.white) : getResources().getColor(R.color.black));
        this.btnlbbmi.setBackground(!AppPref.iskglbcalcu(getActivity()) ? getResources().getDrawable(R.drawable.a2_grad) : getResources().getDrawable(R.drawable.maleback));
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    public void afterTextChanged(Editable editable) {
        if (!bmiValidation()) {
            setTextViewDefault();
        } else {
            getBmiCalculation();
        }
    }

    private boolean bmiValidation() {
        if (AppPref.iskglbcalcu(getActivity()) && (this.weightEditText.getText().toString().isEmpty() || this.heightEditText.getText().toString().isEmpty())) {
            return false;
        }
        if (!AppPref.iskglbcalcu(getActivity()) && (this.weightEditText.getText().toString().isEmpty() || this.heightEditText.getText().toString().isEmpty() || this.heightinchedittext.getText().toString().isEmpty())) {
            Log.d("isKblb", "" + AppPref.iskglbcalcu(getActivity()));
            return false;
        } else if (Double.parseDouble(this.weightEditText.getText().toString()) == Utils.DOUBLE_EPSILON || Integer.parseInt(this.heightEditText.getText().toString()) == 0) {
            return false;
        } else {
            return true;
        }
    }

    private void setTextViewDefault() {
        this.resultoverweighttextView.setText("-");
        this.resulthealthyweighttextView.setText("-");
        this.resultidealweighttextView.setText("-");
        this.txtbmical.setText("0");
        this.seekBar.setProgress(0);
        this.txtbmivalue.setText("");
    }

    @SuppressLint("SetTextI18n")
    public void getBmiCalculation() {
        double d;
        double d2;
        double d3;
        double parseFloat = (double) Float.parseFloat(this.weightEditText.getText().toString());
        int parseInt = Integer.parseInt(this.heightEditText.getText().toString());
        if (!AppPref.iskglbcalcu(requireActivity())) {
            this.kglbval = " " + getActivity().getResources().getString(R.string.lb);
            int parseInt2 = Integer.parseInt(this.heightinchedittext.getText().toString());
            double convertFootToInch = Formula.convertFootToInch(parseInt);
            double d4 = (double) parseInt2;
            Double.isNaN(d4);
            double d5 = convertFootToInch + d4;
            d3 = Formula.getWeightFromBMIinLb(18.5d, d5);
            d2 = Formula.getWeightFromBMIinLb(24.9d, d5);
            AppPref.setHeightinchcalculation(getActivity(), Integer.parseInt(this.heightinchedittext.getText().toString()));
            d = Formula.bmicalculatorlb(parseFloat, d5);
        } else {
            this.kglbval = " " + getActivity().getResources().getString(R.string.Kg);
            double weightFromBMIinKg = Formula.getWeightFromBMIinKg(18.5d, Formula.convertCmtoMeter(parseInt));
            d2 = Formula.getWeightFromBMIinKg(24.9d, Formula.convertCmtoMeter(parseInt));
            d = Formula.bmicalculatorkg(parseFloat, Formula.convertCmtoMeter(parseInt));
            d3 = weightFromBMIinKg;
        }
        AppPref.setWeightcalculation(getActivity(), Float.parseFloat(this.weightEditText.getText().toString()));
        AppPref.setHeightcalculation(getActivity(), Integer.parseInt(this.heightEditText.getText().toString()));
        double idealWeight = Formula.getIdealWeight(d3, d2);
        double overWeight = Formula.getOverWeight(parseFloat, d2);
        if (overWeight < Utils.DOUBLE_EPSILON) {
            TextView textView = this.resultoverweighttextView;
            textView.setText("0" + this.kglbval);
        } else {
            TextView textView2 = this.resultoverweighttextView;
            textView2.setText(String.valueOf(Formula.format(overWeight)) + this.kglbval);
        }
        int i = 0;
        if (d >= 18.5d && d < 25.0d) {
            i = setProgresbar(18.5d, 25.0d, d, 20);
            this.txtbmivalue.setText("Normal");
            this.txtbmivalue.setTextColor(getActivity().getResources().getColor(R.color.color_range_orange));
        } else if (d >= 25.0d && d < 30.0d) {
            i = setProgresbar(25.0d, 30.0d, d, 40);
            this.txtbmivalue.setText("Over Weight");
            this.txtbmivalue.setTextColor(getActivity().getResources().getColor(R.color.color_range_yellow));
        } else if (d >= 30.0d && d < 40.0d) {
            i = setProgresbar(30.0d, 40.0d, d, 60);
            this.txtbmivalue.setText("Obese");
            this.txtbmivalue.setTextColor(getActivity().getResources().getColor(R.color.color_range_green));
        } else if (d >= 40.0d) {
            i = 90;
            this.txtbmivalue.setText("Morbidly Obese");
            this.txtbmivalue.setTextColor(getActivity().getResources().getColor(R.color.color_range_blue));
        } else {
            this.txtbmivalue.setText("");
        }
        this.seekBar.setProgress(i);
        this.txtbmical.setText(Formula.format(d));
        TextView textView3 = this.resultidealweighttextView;
        textView3.setText(Formula.format(idealWeight) + this.kglbval);
        TextView textView4 = this.resulthealthyweighttextView;
        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(Formula.round(d3, 1) + " - " + Formula.round(d2, 1)));
        sb.append(this.kglbval);
        textView4.setText(sb.toString());
    }

    public static int setProgresbar(double d, double d2, double d3, int i) {
        double d4 = i;
        Double.isNaN(d4);
        return (int) (d4 + (((((d3 - d) / (d2 - d)) * 100.0d) * 20.0d) / 100.0d));
    }


}