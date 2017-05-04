package com.siziksu.ru.common;

import android.text.TextUtils;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class DatesManager {

    public static String getYear(String year) {
        if (TextUtils.isEmpty(year)) {
            return Constants.NA;
        }
        String yearFormatted = "";
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = format.parse(year);
            yearFormatted = new SimpleDateFormat("yyyy", Locale.getDefault()).format(date);
        } catch (Exception e) {
            Log.e(Constants.TAG, e.getMessage(), e);
        }
        return !TextUtils.isEmpty(yearFormatted) ? yearFormatted : year;
    }


    public static String getRegisteredDate(String year) {
        if (TextUtils.isEmpty(year)) {
            return Constants.NA;
        }
        String yearFormatted = "";
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = format.parse(year);
            yearFormatted = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date);
        } catch (Exception e) {
            Log.e(Constants.TAG, e.getMessage(), e);
        }
        return !TextUtils.isEmpty(yearFormatted) ? yearFormatted : year;
    }
}
