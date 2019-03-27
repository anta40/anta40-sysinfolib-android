package com.anta40.app.sysinfolib.collector;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

public class AppInfoCollector {

    private Context ctxt;
    private final String PACKAGE_NAME = "cn.com.paic.smartagent2";
    private PackageInfo pinfo;
    private PackageManager pm;

    public AppInfoCollector(Context ctxt){
        this.ctxt = ctxt;
        try {
            pinfo = ctxt.getPackageManager().getPackageInfo(PACKAGE_NAME, 0);
            pm = ctxt.getApplicationContext().getPackageManager();
        }
        catch (PackageManager.NameNotFoundException nfe){

        }
   }

    public String getApplicationVersion(){
        if (pinfo == null) return "NULL";
        return pinfo.versionName;
    }

    public int getApplicationBuildVersion(){
        if (pinfo == null) return 0;
        return pinfo.versionCode;
    }

    public String getApplicationName(){
        String appName = "";
        try {
            if (pm != null) appName = (String) pm.getApplicationLabel(pm.getApplicationInfo(PACKAGE_NAME,
                    PackageManager.GET_META_DATA));
        }
        catch (PackageManager.NameNotFoundException nfe){
            appName = "Not found...";
        }

        return appName;
    }

    public String getAppStatus(){
        return "";
    }

    public String getLoginTime(){
        return "";
    }

    public boolean isDebuggerAttached() {
        boolean result =
                Build.FINGERPRINT.startsWith("generic")//
                        ||Build.FINGERPRINT.startsWith("unknown")//
                        ||Build.MODEL.contains("google_sdk")//
                        ||Build.MODEL.contains("Emulator")//
                        ||Build.MODEL.contains("Android SDK built for x86");
        if (result)
            return true;
        result |= Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic");

        if(result)
            return true;
        result |= "google_sdk".equals(Build.PRODUCT);

        return result;
    }

}
