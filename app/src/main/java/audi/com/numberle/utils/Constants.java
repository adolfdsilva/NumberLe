package audi.com.numberle.utils;

import android.util.Log;

import java.text.ParseException;

/**
 * Created by Audi on 20/03/17.
 */

public class Constants {


    private static final String TAG = "Number-le";
    public static final String SLOT_EXTRAS = "slot_extras";
    public static final String APPOINTMENTS = "Appointments";

    public static void debug(String msg) {
        Log.d(TAG, "" + msg);
    }

    public static void info(String msg) {
        Log.i(TAG, "" + msg);
    }

    public static void error(String msg) {
        Log.e(TAG, "" + msg);
    }

    public static void warning(String msg) {
        Log.w(TAG, "" + msg);
    }

    public static void exception(String msg, ParseException e) {
        Log.e(TAG, msg, e.getCause());
    }
}
