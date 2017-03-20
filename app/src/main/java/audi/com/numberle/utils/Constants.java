package audi.com.numberle.utils;

import android.util.Log;

/**
 * Created by Audi on 20/03/17.
 */

public class Constants {


    private static final String TAG = "Number-le";

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

}
