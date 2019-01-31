package com.android.utils.system;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.telephony.TelephonyManager;

import java.util.ArrayList;
import java.util.List;


public class SystemUtils {

    private static final String TAG = SystemUtils.class.getSimpleName();

    /*
     * 通过Service的类名来判断是否启动某个服务
     */
    public static boolean getServiceStatus(Context context, String serviceName) {
        // 1、活动管理器
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null)
            return false;
        // 2、获取当前服务，指定返回100条
        List<ActivityManager.RunningServiceInfo> runningServices = am.getRunningServices(100);
        for (ActivityManager.RunningServiceInfo runningService : runningServices) {
            String name = runningService.service.getClassName();
            if (serviceName.equals(name)) {
                return true;
            }
        }
        return false;
    }

    /*
     * 查询手机内非系统应用
     */
    public static List<PackageInfo> getAllApps(Context context) {
        List<PackageInfo> apps = new ArrayList<>();
        PackageManager pManager = context.getPackageManager();
        // 获取手机内所有应用
        List<PackageInfo> packlist = pManager.getInstalledPackages(0);
        for (int i = 0; i < packlist.size(); i++) {
            PackageInfo pak = packlist.get(i);
            // 判断是否为非系统预装的应用程序
            if ((pak.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
                // customs applications
                apps.add(pak);
            }
        }
        return apps;
    }

    /*
     * 获取 packageInfo
     */
    public static PackageInfo getPackageInfo(Context context) throws PackageManager.NameNotFoundException {
        return context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
    }

    /*
     * 获取版本Code
     */
    public static String getVersionCode(Context context) {
        try {
            PackageInfo info = getPackageInfo(context);
            return String.valueOf(info.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        try {
            PackageInfo info = getPackageInfo(context);
            return String.valueOf(info.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     * 获取targetSdkVersion
     */
    public static int getTargetSdkVersion(Context context) {
        try {
            PackageInfo info = getPackageInfo(context);
            return info.applicationInfo.targetSdkVersion;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /*
     * 获取最大内存
     */
    public static long getMaxMemory() {
        return Runtime.getRuntime().maxMemory() / 1024 / 1024;
    }

    // IMEI码
    private static String getIMIEStatus(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (!checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
            return "";
        } else {
            return tm.getDeviceId();
        }
    }

    // Android Id
    private static String getAndroidId(Context context) {
        String androidId = Settings.Secure.getString(
                context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return androidId;
    }

    /*
     * Android6.0之后，权限分为install时的权限跟运行时权限，如果我们的targetSdkVersion>=23，install权限同runtime权限是分开的
     * targetSdkVersion < 23 时 即便运行在android6及以上设备 ContextWrapper.checkSelfPermission和Context.checkSelfPermission失效
     * 返回值始终为PERMISSION_GRANTED，此时必须使用PermissionChecker.checkSelfPermission
     */
    public static boolean checkPermission(Context context, String permission) {
        boolean result = true;
        // android 6.0 以下会在安装时自动获取权限
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return result;
        int targetSdkVersion = getTargetSdkVersion(context);
        if (targetSdkVersion >= Build.VERSION_CODES.M) {
            result = context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        } else {
            result = PermissionChecker.checkSelfPermission(context, permission) == PermissionChecker.PERMISSION_GRANTED;
        }
        return result;
    }

    public static List<String> checkDeniedPermission(Activity activity, List<String> list) {
        List<String> deniedList = new ArrayList<>();
        for (String permission : list) {
            /*
             * 当第一次申请权限时 shouldShowRequestPermissionRationale返回false，
             * 第一次用户拒绝，再次申请的时候返回true，在此判断中提示用户为什么要申请这个权限。
             */
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                deniedList.add(permission);
            }
        }
        return deniedList;
    }


    @TargetApi(Build.VERSION_CODES.M)
    public static void requestPermission(Activity activity, @NonNull String[] permissions) {
        // 检测权限是否授权
        final List<String> permissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (!SystemUtils.checkPermission(activity, permission)) {
                permissionList.add(permission);
            }
        }
        // 申请权限
        if (permissionList.size() <= 0) return;
        final List<String> deniedList = checkDeniedPermission(activity, permissionList);
        if (deniedList.size() > 0) {
            ActivityCompat.requestPermissions(activity, deniedList.toArray(new String[deniedList.size()]), 0);
        } else {
            ActivityCompat.requestPermissions(activity, permissionList.toArray(new String[permissionList.size()]), 0);
        }
    }

}

