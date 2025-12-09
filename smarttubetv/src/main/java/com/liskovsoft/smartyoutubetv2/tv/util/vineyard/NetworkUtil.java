package com.liskovsoft.smartyoutubetv2.tv.util.vineyard;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * The Network util class.
 *
 */
public class NetworkUtil {
    /**
     * Checks if Network connected.
     *
     * @param context The application context.
     * @return True if is network connected, false otherwise.
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}