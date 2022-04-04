package com.example.vikings_fitandfab_android.Class;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class AppPref {
    static final String HEIGHTINCH_CALCULATION = "HEIGHT_INCH_calculation";
    static final String HEIGHT_CALCULATION = "HEIGHT_calculation";
    static final String IS_DAILY = "IS_DAILY";
    static final String IS_FIRST_LUNCH = "IS_FIRST_LUNCH";
    static final String ISKG_CALCULATION = "IS_KG_calculation";
    static final String IS_METRIC_WAIST = "isMetricWaist";
    static final String IS_METRIC_WEIGHT = "isMetricWeight";
    static final String IS_RATEUS = "IS_RATEUS";
    static final String IS_SAVE = "IS_SAVE";
    static final String MY_PREFRENCE = "userPref";
    static final String REMINDER_TIME = "reminderTime";
    static final String WEIGHT_CALCULATION = "WEIGHT_calculation";




    public static void setWeightcalculation(Context context, float f) {
        SharedPreferences.Editor edit = context.getApplicationContext().getSharedPreferences(MY_PREFRENCE, 0).edit();
        edit.putFloat(WEIGHT_CALCULATION, f);
        edit.commit();
    }

    public static void setHeightinchcalculation(Context context, int i) {
        SharedPreferences.Editor edit = context.getApplicationContext().getSharedPreferences(MY_PREFRENCE, 0).edit();
        edit.putInt(HEIGHTINCH_CALCULATION, i);
        edit.commit();
    }

    public static int getHeightcalculation(Context context) {
        return context.getApplicationContext().getSharedPreferences(MY_PREFRENCE, 0).getInt(HEIGHT_CALCULATION, 165);
    }

    public static void setHeightcalculation(Context context, int i) {
        SharedPreferences.Editor edit = context.getApplicationContext().getSharedPreferences(MY_PREFRENCE, 0).edit();
        edit.putInt(HEIGHT_CALCULATION, i);
        edit.commit();
    }


    public static boolean iskglbcalcu(Context context) {
        return context.getApplicationContext().getSharedPreferences(MY_PREFRENCE, 0).getBoolean(ISKG_CALCULATION, true);
    }

    public static void setKglbcalculation(Context context, boolean z) {
        SharedPreferences.Editor edit = context.getApplicationContext().getSharedPreferences(MY_PREFRENCE, 0).edit();
        edit.putBoolean(ISKG_CALCULATION, z);
        edit.commit();
    }

}
