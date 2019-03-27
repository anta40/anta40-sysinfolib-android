package com.anta40.app.anta40_sysinfolib_android;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.widget.EditText;

import com.anta40.app.sysinfolib.collector.AppInfoCollector;
import com.anta40.app.sysinfolib.collector.CarrierInfoCollector;
import com.anta40.app.sysinfolib.collector.DeviceHardwareInfoCollector;
import com.anta40.app.sysinfolib.collector.DeviceOSInfoCollector;
import com.anta40.app.sysinfolib.collector.GeographicInformationCollector;
import com.anta40.app.sysinfolib.collector.NetworkInfoCollector;

public class MainActivity extends AppCompatActivity {

    CarrierInfoCollector carrierInfo;
    AppInfoCollector appInfo;
    DeviceHardwareInfoCollector hardwareInfo;
    NetworkInfoCollector networkInfo;
    DeviceOSInfoCollector osInfo;
    GeographicInformationCollector geoInfo;
    EditText edContent;

    Context ctxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctxt = getApplicationContext();

        edContent = (EditText) findViewById(R.id.edtContent);

        carrierInfo = new CarrierInfoCollector(ctxt);
        appInfo = new AppInfoCollector(ctxt);
        hardwareInfo = new DeviceHardwareInfoCollector(this, ctxt);
        networkInfo = new NetworkInfoCollector(ctxt);
        osInfo = new DeviceOSInfoCollector(ctxt);
        geoInfo = new GeographicInformationCollector(this, ctxt);

        displayHardwareInfo();
        displayCarrierInfo();
        displayOSInfo();
        displayNetworkInfo();
        displayAppInfo();
    }

    public void displayNetworkInfo(){
        String str = "";
        str += "\n\nNETWORK INFO";

        str += "\nIP address: "+networkInfo.getIPAddress();
        str += "\nNetmask address: "+networkInfo.getNetmaskAddress();
        str += "\nCell/wifi: "+networkInfo.getNetworkStatus();
        str += "\n2G/3G/4G: "+networkInfo.getCellMode();

        str += "\nGSM/CDMA: "+networkInfo.getNetworkType();
        str += "\nBase station number: "+networkInfo.getBaseStationNumber();
        str += "\nBase station single status: "+networkInfo.getBaseStationSingleStatus();

        edContent.append(str);
    }

    public void displayAppInfo(){
        String str = "";
        str += "\n\nAPP INFO";

        str += "\nApplication version: "+ appInfo.getApplicationVersion();
        str += "\nApplication build version: "+appInfo.getApplicationBuildVersion();
        str += "\nApp name: "+ appInfo.getApplicationName();
        str += "\nDebugger attached: "+appInfo.isDebuggerAttached();
        str += "\nApp status: "+appInfo.getAppStatus();
        str += "\nLogin time: "+appInfo.getLoginTime();

        edContent.append(str);
    }

    public void displayOSInfo(){
        String str = "";
        str += "\n\nDEVICE OS INFO";

        str += "\nSystem name: "+ osInfo.getSystemName();
        str += "\nSystem device type: "+osInfo.getSystemDeviceType();
        str += "\nSystem version: " + osInfo.getSystemVersion();
        str += "\nLanguage: "+ osInfo.getLanguage();
        str += "\nCountry: "+ osInfo.getCountry();

        str += "\nTime zone: "+osInfo.getTimezone();
        str += "\nRoot: "+osInfo.isRooted();

        edContent.append(str);
    }


    public void displayCarrierInfo(){
        String str = "";
        str += "\n\nCARRIER INFO";

        str += "\nCarrier name: "+carrierInfo.getCarrierName();
        str += "\nAllows VOIP: "+carrierInfo.isAllowVOIP();
        str += "\nCarrier ISO country code: "+carrierInfo.getISOCountryCode();

        str += "\nCarrier mobile country code: "+carrierInfo.getMCC();
        str += "\nCarrier mobile network code: "+carrierInfo.getMNC();

        edContent.append(str);
    }

    public void displayHardwareInfo(){
        String str = "";
        str += "\n\n --- DEVICE HARDWARE INFORMATION ---";
        str += "\nDisk space: "+hardwareInfo.getHumanReadableTotalDiskSpace(true);
        str += "\nFree disk space: "+hardwareInfo.getHumanReadableFreeDiskSpace(true);

        // Screen resolution
        int[] resolution = hardwareInfo.getScreenResolution();
        str += "\nResolution: "+resolution[0]+"x"+resolution[1];

        // Device brand name
        str += "\nDevice brand name: "+hardwareInfo.getDeviceBrandName();

        // Device type
        str += "\nDevice type: "+hardwareInfo.getDeviceType();

        // Device model
        str += "\nDevice model: "+hardwareInfo.getDeviceModel();

        // Device name
        str += "\nDevice name: "+hardwareInfo.getDeviceName();

        // CPU usage
        str += "\nCPU usage: "+hardwareInfo.getCPUUsage();

        // memory
        str += "\nTotal memory: "+hardwareInfo.getHumanReadableTotalMemory(true);
        str += "\nUsed memory: "+hardwareInfo.getHumanReadableTUsedMemory(true);

        // battery
        str += "\nBattery level: "+hardwareInfo.getBatteryLevel()+"%";
        str += "\nCharging: "+ hardwareInfo.isBatteryCharging();
        str += "\nFully charged: "+hardwareInfo.isBatteryFullyCharged();

        // earphone/headphone
        str += "\nHeadphone is attached: "+hardwareInfo.isHeadphoneAttached();

        // Android ID
        str += "\nAndroid ID: "+hardwareInfo.getAndroidID();

        // simulator?
        str += "\nIs simulator: "+hardwareInfo.isSimulator();

        // IMEI
        str += "\nIMEI: "+hardwareInfo.getDeviceIMEI();

        // WiFI
        str += "\nWiFi component: "+hardwareInfo.hasWifi();

        // Data
        str += "\nData component: "+hardwareInfo.hasData();

        // GPS
        str += "\nGPS component: "+hardwareInfo.hasGPS();

        // Phone
        str += "\nPhone component: "+hardwareInfo.hasPhone();

        // Bluetooth
        str += "\nBluetooth component: "+hardwareInfo.hasBluetooth();

        // Earphone
        str += "\nEarphone component: "+hardwareInfo.isHeadphoneAttached();

        // NFC
        str += "\nNFC component: "+hardwareInfo.hasNFC();
        if (hardwareInfo.hasNFC()) str += "\nNFC status: "+hardwareInfo.getNFCStatus();

        edContent.append(str);
    }
}
