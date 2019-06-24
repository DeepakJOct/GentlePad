package com.example.gentlepad.common;

import android.content.Context;
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
        String pattern = "dd MMM yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

}
