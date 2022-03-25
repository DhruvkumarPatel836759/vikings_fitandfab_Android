package com.example.vikings_fitandfab_android.Class;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class AppPref {static final String HEIGHTINCH_CALCULATION = "HEIGHT_INCH_calculation";
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

    public static boolean rateus(Context context) {
        return context.getApplicationContext().getSharedPreferences(MY_PREFRENCE, 0).getBoolean(IS_RATEUS, false);
    }

    public static void setRateUs(Context context, boolean z) {
        SharedPreferences.Editor edit = context.getApplicationContext().getSharedPreferences(MY_PREFRENCE, 0).edit();
        edit.putBoolean(IS_RATEUS, z);
        edit.commit();
}}
