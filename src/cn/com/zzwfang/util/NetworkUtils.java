package cn.com.zzwfang.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.provider.Settings;

public class NetworkUtils {
    /**
     * 是否有可用的网络连接.
     *
     * @param context
     * @return
     */
    static public boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        String action = ConnectivityManager.CONNECTIVITY_ACTION;
        if (cm.getActiveNetworkInfo() != null) {
            return cm.getActiveNetworkInfo().isAvailable();
        }
        return false;
    }

    /**
     * 获取wifi状态
     *
     * @param context
     */
    static public State getWifiState(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //wifi
        State wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        return wifi;
    }

    /**
     * 获取移动网络状态
     *
     * @param context
     * @return
     */
    static public State getMobileState(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //mobile 3G Data Network
        State mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        return mobile;
    }

    /**
     * 打开系统的网络设置页面
     *
     * @param activity
     */
    static public void openNetworkSettings(Activity activity) {
        activity.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
    }


    /**
     * 判断当前的网络是否连接
     *
     * @param activity
     * @return
     */
    public boolean isNetworkAvailable_(Activity activity) {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
}
