package com.anta40.app.sysinfolib.collector;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.nfc.NfcAdapter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.WindowManager;

import com.anta40.app.sysinfolib.misc.MiscUtil;
import com.jaredrummler.android.device.DeviceName;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import github.nisrulz.easydeviceinfo.base.DeviceType;
import github.nisrulz.easydeviceinfo.base.EasyDeviceMod;

import static android.content.Context.TELEPHONY_SERVICE;

public class DeviceHardwareInfoCollector {

    private Context ctxt;
    private Activity act;
    private String deviceUniqueIdentifier;

    public DeviceHardwareInfoCollector(Activity act, Context ctxt){
        this.ctxt = ctxt;
        this.act = act;
        deviceUniqueIdentifier = "";
    }

    public long getTotalDiskSpace(){
        File external = Environment.getExternalStorageDirectory();
        return external.getTotalSpace();
    }

    public String getHumanReadableTotalDiskSpace(boolean use_si){
        return MiscUtil.humanReadableByteCount(getTotalDiskSpace(), use_si);
    }

    public String getHumanReadableFreeDiskSpace(boolean use_si){
        File external = Environment.getExternalStorageDirectory();
        return MiscUtil.humanReadableByteCount(external.getFreeSpace(), use_si);
    }

    public long getFreeDiskSpace(){
        File external = Environment.getExternalStorageDirectory();
        return external.getFreeSpace();
    }

    public int[] getScreenResolution(){
        WindowManager wm = (WindowManager) ctxt.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int[] resolution = {0,0};
        resolution[0] = size.x;
        resolution[1] = size.y;
        return resolution;
    }

    public String getDeviceBrandName(){
        return android.os.Build.BRAND;
    }

    public String getDeviceType(){
        EasyDeviceMod devMod = new EasyDeviceMod(act);
        int devType = devMod.getDeviceType(act);
        String deviceType = "";
        switch (devType) {
            case DeviceType.WATCH:
                deviceType = "watch";
                break;
            case DeviceType.PHONE:
                deviceType = "phone";
                break;
            case DeviceType.PHABLET:
                deviceType = "phablet";
                break;
            case DeviceType.TABLET:
                deviceType = "tablet";
                break;
            case DeviceType.TV:
                deviceType = "TV";
                break;
        }
        return deviceType;
    }

    public String getDeviceModel(){
        return "";
    }

    public String getDeviceName(){
        return DeviceName.getDeviceName();
    }

    public String getCPUUsage(){
        return "";
    }

    public String getHumanReadableTotalMemory(boolean use_si){
        return MiscUtil.humanReadableByteCount(getTotalMemory(), use_si);
    }

    public long getTotalMemory(){
        ActivityManager actManager = (ActivityManager) ctxt.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        actManager.getMemoryInfo(memInfo);
        return memInfo.totalMem;
    }

    public long getUsedMemory(){
        ActivityManager actManager = (ActivityManager) ctxt.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        actManager.getMemoryInfo(memInfo);
        return (memInfo.totalMem-memInfo.availMem);
    }

    public String getHumanReadableTUsedMemory(boolean use_si){
        return MiscUtil.humanReadableByteCount(getUsedMemory(), use_si);
    }

    public int getBatteryLevel(){
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = ctxt.registerReceiver(null, ifilter);
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        return level;
    }

    public boolean isBatteryCharging(){
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = ctxt.registerReceiver(null, ifilter);
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        return (status == BatteryManager.BATTERY_STATUS_CHARGING);
    }

    public boolean isBatteryFullyCharged(){
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = ctxt.registerReceiver(null, ifilter);
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        return (status == BatteryManager.BATTERY_STATUS_FULL);
    }

    public boolean isHeadphoneAttached(){
        AudioManager am = (AudioManager) ctxt.getSystemService(Context.AUDIO_SERVICE);
        return am.isWiredHeadsetOn();
    }

    public String getAndroidID(){
        return "";
    }

    public String getMACAddress(){
        WifiManager wifiMan = (WifiManager) ctxt.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInf = wifiMan.getConnectionInfo();
        String macAddr = wifiInf.getMacAddress();
        return macAddr;
    }

    public boolean hasWifi(){
        return (ctxt.getSystemService(Context.WIFI_SERVICE) == null);
    }

    public boolean hasData(){
        ConnectivityManager connManager = (ConnectivityManager) ctxt.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobile = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean dataStatus = false;

        if( mobile.isAvailable() && mobile.getDetailedState() == NetworkInfo.DetailedState.CONNECTED ){
            dataStatus = true;
        }

        return dataStatus;
    }

    public boolean hasPhone(){
        PackageManager pm = ctxt.getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_TELEPHONY);
    }

    public boolean hasGPS(){
        PackageManager packageManager = ctxt.getPackageManager();
        return packageManager.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);
    }

    public boolean hasBluetooth(){
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return (bluetoothAdapter == null);
    }

    public boolean hasNFC(){
        NfcAdapter adapter;
        adapter = NfcAdapter.getDefaultAdapter(ctxt.getApplicationContext());
        return (adapter == null);
    }

    public String getNFCStatus(){
        NfcAdapter mNfcAdapter;
        String nfcStatus = "";

        try{
            mNfcAdapter = NfcAdapter.getDefaultAdapter(ctxt.getApplicationContext());

            if (mNfcAdapter == null) {
                nfcStatus = "Not supported.";
            }

            if (!mNfcAdapter.isEnabled()) {
                nfcStatus = "Supported. NFC is disabled.";
            } else {
                nfcStatus = "Supported. NFC is enabled.";
            }

        }catch (Exception e) {
            nfcStatus = "No properties found";
        }

        return nfcStatus;
    }

    public String getUUID(){
        TelephonyManager tm = (TelephonyManager) ctxt.getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission") String tmSerial = tm.getSimSerialNumber();
        @SuppressLint("MissingPermission") String tmDeviceId = tm.getDeviceId();
        String androidId = android.provider.Settings.Secure.getString(ctxt.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);
        if (tmSerial  == null) tmSerial   = "1";
        if (tmDeviceId== null) tmDeviceId = "1";
        if (androidId == null) androidId  = "1";
        UUID deviceUuid = new UUID(androidId.hashCode(),
                ((long)tmDeviceId.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        return uniqueId;
    }

    public boolean isSimulator(){
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

    public String getDeviceIMEI() {

        String[] permissions = {Manifest.permission.READ_PHONE_STATE};
        String rationale = "Please provide permission to detect your IMEI";
        Permissions.Options options = new Permissions.Options()
                .setRationaleDialogTitle("Info")
                .setSettingsDialogTitle("Warning");
        Permissions.check(act, permissions, rationale, options, new PermissionHandler() {
            @SuppressLint("MissingPermission")
            @Override
            public void onGranted() {
                TelephonyManager tm = (TelephonyManager) ctxt.getSystemService(TELEPHONY_SERVICE);
                if (null != tm) {
                    deviceUniqueIdentifier = tm.getDeviceId();
                }
                if (null == deviceUniqueIdentifier || 0 == deviceUniqueIdentifier.length()) {
                    deviceUniqueIdentifier = Settings.Secure.getString(ctxt.getContentResolver(), Settings.Secure.ANDROID_ID);
                }
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                deviceUniqueIdentifier = "NULL";
            }
        });

        return deviceUniqueIdentifier;
    }
}
