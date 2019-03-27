package com.anta40.app.sysinfolib.collector;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;

public class NetworkInfoCollector {

    private Context context;
    private WifiManager wm;

    public NetworkInfoCollector (Context context) {
        this.context = context;
        wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    public String getIPAddress(){
        String ip = "";
        ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        return ip;
    }

    public String getNetmaskAddress(){
        return "";
    }

    public String getNetworkStatus(){
        String networkStatus = "";

        // Get connect mangaer
        final ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        // check for wifi
        final android.net.NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        // check for mobile data
        final android.net.NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifi.isAvailable() ) {
            networkStatus = "wifi";
        } else if (mobile.isAvailable() ) {
            networkStatus = "cell";
        } else {
            networkStatus = "no network";
        }

        return networkStatus;
    }

    public String getCellMode(){
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        String res = "Not available";

        if ((tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_HSDPA)) {
            res="3G";
        } else if ((tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_HSPAP)) {
            res="4G";
        }else if ((tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_EDGE)) {
            res="2G";
        }

        return res;
    }

    public String getNetworkType(){
        String networkType = "";

        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        switch(tm.getPhoneType()){
            case TelephonyManager.PHONE_TYPE_CDMA:
                networkType = "CDMA";
                break;

            case TelephonyManager.PHONE_TYPE_GSM:
                networkType = "GSM";
                break;
        }

        return networkType;
    }

    public String getBaseStationNumber(){
        return "";
    }

    public String getBaseStationSingleStatus(){
        return "";
    }
}
