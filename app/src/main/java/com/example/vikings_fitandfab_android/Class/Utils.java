package com.example.vikings_fitandfab_android.Class;

import android.content.res.Resources;

public final class Utils {
    public static final double DOUBLE_EPSILON = 0 ;

    private Utils() {
    }

    public static float dp2px(Resources resources, float f) {
        return (f * resources.getDisplayMetrics().density) + 0.5f;
    }

    public static float sp2px(Resources resources, float f) {
        return f * resources.getDisplayMetrics().scaledDensity;
    }
}
