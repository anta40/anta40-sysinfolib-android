package com.anta40.app.sysinfolib.collector;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

public class CarrierInfoCollector {

    private Context ctxt;
    private TelephonyManager tm;

    public CarrierInfoCollector(Context ctxt){
        this.ctxt = ctxt;
        tm = (TelephonyManager) ctxt.getSystemService(Context.TELEPHONY_SERVICE);
    }

    public String getCarrierName(){
        String ret = tm.getSimOperatorName()+" "+tm.getNetworkOperatorName();
        return ret;
    }

    public String getISOCountryCode(){
        return tm.getNetworkCountryIso();
    }

    public boolean isAllowVOIP(){
        return false;
    }

    public int getMCC(){
        String networkOperator = tm.getNetworkOperator();
        int mcc = 0;

        if (!TextUtils.isEmpty(networkOperator)){
            mcc = Integer.parseInt(networkOperator.substring(0, 3));
        }

        return mcc;
    }

    public int getMNC(){
        String networkOperator = tm.getNetworkOperator();
        int mnc = 0;

        if (!TextUtils.isEmpty(networkOperator)){
            mnc = Integer.parseInt(networkOperator.substring(3));
        }

        return mnc;
    }
}
