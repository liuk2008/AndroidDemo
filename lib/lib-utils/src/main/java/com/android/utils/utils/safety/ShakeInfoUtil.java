package com.android.utils.utils.safety;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.utils.utils.exception.ExceptionManager;
import com.android.utils.utils.system.Contexts;

import java.util.List;

/**
 */

public class ShakeInfoUtil {
    private static Activity currentActivity;
    private static boolean isInited;
    private static boolean isDialogShowing;

    public static void init(Application application) {
        try {
            if (isInited) return;
            isInited = true;
            application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

                }

                @Override
                public void onActivityStarted(Activity activity) {

                }

                @Override
                public void onActivityResumed(Activity activity) {
                    currentActivity = activity;
                }

                @Override
                public void onActivityPaused(Activity activity) {
                    if (currentActivity == activity) {
                        currentActivity = null;
                    }
                }

                @Override
                public void onActivityStopped(Activity activity) {

                }

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

                }

                @Override
                public void onActivityDestroyed(Activity activity) {

                }
            });
            SensorManager sensorManager = (SensorManager) application.getSystemService(Context.SENSOR_SERVICE);
            sensorManager.registerListener(new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    if (Math.abs(event.values[0]) + Math.abs(event.values[1]) > 30) {
                        if (null != currentActivity && !isDialogShowing) {
                            isDialogShowing = true;
                            Dialog dialog = new Dialog(currentActivity);
                            ScrollView scrollView = new ScrollView(currentActivity);
                            TextView textView = new TextView(currentActivity);
                            textView.setText(getPageInfo());
                            textView.setTextColor(Color.BLACK);
                            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
                            scrollView.addView(textView);
                            dialog.setContentView(scrollView);
                            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    isDialogShowing = false;
                                }
                            });
                            dialog.show();
                        }
                    }
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                }
            }, sensorManager.getDefaultSensor(SensorManager.SENSOR_STATUS_ACCURACY_LOW), 70);
        } catch (Exception e) {
            ExceptionManager.handle(e);
        }
    }

    public static String getPageInfo() {
        if (null == currentActivity) {
            currentActivity = Contexts.getFrontActivity();
            if (null == currentActivity) return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(currentActivity.getClass().getSimpleName());
        if (currentActivity instanceof AppCompatActivity) {
            FragmentManager supportFragmentManager = ((AppCompatActivity) currentActivity).getSupportFragmentManager();
            //noinspection RestrictedApi
            getFragmentInfo(1, sb, supportFragmentManager);
        }
        return sb.toString();
    }

    private static void getFragmentInfo(int level, StringBuilder sb, FragmentManager supportFragmentManager) {
        if (null != supportFragmentManager) {
            //noinspection RestrictedApi
            List<Fragment> fragments = supportFragmentManager.getFragments();
            if (null != fragments && fragments.size() > 0) {
                for (Fragment fragment : fragments) {
                    if (null != fragment) {
                        sb.append('\n')
                                .append(getOffset(level))
                                .append(fragment.getClass().getSimpleName())
                                .append(fragment.isVisible() ? 'ⓥ' : "");
                        getFragmentInfo(level + 1, sb, fragment.getChildFragmentManager());
                    }
                }
            }
        }
    }

    private static String getOffset(int level) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('┣');
        while (level-- > 1) {
            stringBuilder.append('━');
        }
        stringBuilder.append('┫');
        return stringBuilder.toString();
    }

}
