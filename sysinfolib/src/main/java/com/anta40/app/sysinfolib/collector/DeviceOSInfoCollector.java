package com.anta40.app.sysinfolib.collector;

import android.content.Context;
import android.os.Build;

import java.util.Locale;
import java.util.TimeZone;

import github.nisrulz.easydeviceinfo.base.EasyDeviceMod;

public class DeviceOSInfoCollector {

    private Context ctxt;
    private EasyDeviceMod easyDeviceMod;

    public DeviceOSInfoCollector(Context ctxt){
        this.ctxt = ctxt;
        easyDeviceMod = new EasyDeviceMod(ctxt);
    }

    public String getSystemName(){
        return Build.BRAND;
    }

    public String getSystemDeviceType(){
        String result = android.os.Build.MODEL + " (" + android.os.Build.PRODUCT + ")";
        return result;
    }

    public String getSystemVersion(){
        return Build.VERSION.RELEASE;
    }

    public String getLanguage(){
        return Locale.getDefault().getDisplayLanguage();
    }

    public String getCountry(){
        return Locale.getDefault().getCountry();
    }

    public String getTimezone(){
        TimeZone tz = TimeZone.getDefault();
        return tz.getID();
    }

    public boolean isRooted(){
        return easyDeviceMod.isDeviceRooted();
    }
}
