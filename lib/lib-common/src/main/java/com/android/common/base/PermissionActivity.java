package com.android.common.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.android.utils.common.ToastUtils;
import com.android.utils.system.SystemUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * android 6.0 动态申请权限
 * <p>
 * shouldShowRequestPermissionRationale()说明
 * 1、shouldShowRequestPermissionRationale() 默认返回 false。
 * 2、第一次请求权限时，如果用户拒绝了，再次请求时 shouldShowRequestPermissionRationale() 返回 true。
 * 3、多次请求权限（超过一次），用户如果选择了不再提醒并拒绝，shouldShowRequestPermissionRationale() 返回 false。
 * 4、设备的策略禁止当前应用获取这个权限的授权，shouldShowRequestPermissionRationale() 返回 false。
 */
public class PermissionActivity extends AppCompatActivity {

    private static final String TAG = PermissionActivity.class.getSimpleName();
    private static final int PERMISSION_REQUEST_CODE = 1001;
    private static OnPermissionCallback mCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mCallback == null) mCallback = new MyOnPermissionCallback();
        String[] permissions = getIntent().getStringArrayExtra("permissions");
        // 检测权限是否授权
        List<String> permissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (!SystemUtils.checkPermission(this, permission)) {
                permissionList.add(permission);
            }
        }
        // 申请权限
        if (permissionList.size() <= 0) return;
        List<String> deniedList = SystemUtils.checkDeniedPermission(this, permissionList);
        if (deniedList.size() > 0) {
            ActivityCompat.requestPermissions(this, deniedList.toArray(new String[deniedList.size()]), PERMISSION_REQUEST_CODE);
        } else {
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), PERMISSION_REQUEST_CODE);
        }
    }

    public static void requestPermission(Activity activity, @NonNull String permission, OnPermissionCallback callback) {
        requestPermission(activity, new String[]{permission}, callback);
    }

    public static void requestPermissions(Activity activity, @NonNull String[] permissions, OnPermissionCallback callback) {
        requestPermission(activity, permissions, callback);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private static void requestPermission(Activity activity, @NonNull String[] permissions, OnPermissionCallback callback) {
        mCallback = callback;
        Intent intent = new Intent(activity, PermissionActivity.class);
        intent.putExtra("permissions", permissions);
        activity.startActivity(intent);
    }

    /**
     * 此方法是权限申请的回调方法，在此方法中处理权限申请成功或失败后的操作。
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) { // 申请多个权限时，只要有一个申请成功，此方法就会被回调
                    mCallback.onGranted(permissions);
                } else {
                    mCallback.onDenied(permissions);
                }
                break;
            default:
                break;
        }
    }


    public interface OnPermissionCallback {
        void onGranted(String[] permissions);

        void onDenied(String[] permissions);
    }

    class MyOnPermissionCallback implements OnPermissionCallback {
        @Override
        public void onGranted(String[] permissions) {
            finish();
        }

        @Override
        public void onDenied(String[] permissions) {
            boolean result = true;
            for (String permission : permissions) {
                /*
                 * 当多次（两次或两次以上）请求操作时，会有不再提示的选择框，如果用户选择了不再提示，
                 * shouldShowRequestPermissionRationale为false，在此判断中提示用户权限已被禁止，需要在应用管理中自行打开。
                 */
                result = ActivityCompat.shouldShowRequestPermissionRationale(PermissionActivity.this, permission);
                if (!result) break;
            }
            if (!result) {
                ToastUtils.showToast(getApplicationContext(), "权限已被禁止");
                new AlertDialog.Builder(PermissionActivity.this)
                        .setTitle("申请权限")
                        .setMessage("请在系统设置中开启APP相关权限")
                        .setCancelable(false)
                        .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                intent.setData(uri);
                                if (intent.resolveActivity(getPackageManager()) != null) {
                                    startActivity(intent);
                                } else {
                                    startActivity(new Intent(Settings.ACTION_APPLICATION_SETTINGS));
                                }
                                finish();
                            }
                        }).show();
            } else {
                finish();
            }
        }
    }

}
