package com.carrillo.movieflix.util;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utils {

    public static boolean isConnected(Activity a) {
        try {
            ConnectivityManager connMgr = (ConnectivityManager) a.getSystemService(a.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean itsEmptyString(String cadena) {
        if (cadena != null) {
            if (cadena.length() == 0) {
                return true;
            } else {
                return cadena.trim().length() == 0;
            }
        }
        return true;
    }
}

