package com.example.gentlepad.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class CommonUtils {

    //Toast Message Display ...EMP 32
    public static void showToastMessage(Context context, String s) {
        if (context != null)
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show();

    }

    public static void showToastMessageLong(Context context, String s) {
        if (context != null)
            Toast.makeText(context, s, Toast.LENGTH_LONG).show();

    }

    public static String getDate() {
        Date date = Calendar.getInstance().getTime();
        String pattern = "dd MMM yyyy hh:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    public static void setFont(Context context, TextView textView) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Satisfy-Regular.ttf");
        textView.setTypeface(font);
    }

    public static void saveBoolean(Context context, String key, boolean value)
    {
        SharedPreferences preferences = context.getSharedPreferences(" SHARED_PREFERENCES_NAME ", android.content.Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getBoolean(Context context, String key)
    {
        SharedPreferences preferences = context.getSharedPreferences(" SHARED_PREFERENCES_NAME ", android.content.Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }

}
