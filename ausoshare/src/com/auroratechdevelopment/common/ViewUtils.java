/**
 * 
 */
package com.auroratechdevelopment.common;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;

import com.auroratechdevelopment.ausoshare.CustomApplication;
import com.auroratechdevelopment.ausoshare.ui.ActivityBase;
import com.auroratechdevelopment.ausoshare.util.Constants;


/**
 * @author Edward
 *
 */
public class ViewUtils {

    /**
     * Jump to other pages
     *
     * @param bundle
     *           StoreItem the parameters passed to the other page objects
     * @param activity
     *            Jump to activity classes
     */
    public static void startPage(Bundle bundle,Context context, Class<?> activity)
    {
        if (context instanceof ActivityBase) {
            ActivityBase base = (ActivityBase)context;
            base.dismissCustomAlertDialog();
        }
        DebugLogUtil.LogD("startPage " + activity.getSimpleName());

        Class<?> activityClass = getExtendedClass(activity);
        Intent intent = new Intent();
        if (null != bundle)
        {
            intent.putExtras(bundle);
        }
        intent.setClass(context, activityClass);
        context.startActivity(intent);
    }
    /**
     * Jump to other pages
     *
     * @param bundle
     *           StoreItem the parameters passed to the other page objects
     * @param activity
     *            Jump to activity classes
     */
    public static void startPageWithClearStack(Bundle bundle,Activity context, Class<?> activity)
    {
        if (context instanceof ActivityBase) {
            ActivityBase base = (ActivityBase)context;
            base.dismissCustomAlertDialog();
        }
        Class<?> activityClass = getExtendedClass(activity);
        Intent intent = new Intent();
        if (null == bundle)
        {
            bundle = new Bundle();
        }
        DebugLogUtil.LogD("start page clear " + activity.getSimpleName());
        bundle.putString(Constants.ACTIVITY_TAG, activity.getSimpleName());
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setClass(context, activityClass);
        context.startActivity(intent);
        context.finish();
    }

    /**
     * Jump to other activity and wait for result
     *
     * @param bundle
     *           StoreItem the parameters passed to the other page objects
     * @param activity
     *            Jump to activity classes
     */
    public static void startPageForResult(Bundle bundle, Activity context, int requestCode, Class<?> activity)
    {
        if (context instanceof ActivityBase) {
            ActivityBase base = (ActivityBase)context;
            base.dismissCustomAlertDialog();
        }
        DebugLogUtil.LogD("startPage " + activity.getSimpleName());
        Class<?> activityClass = getExtendedClass(activity);

        Intent intent = new Intent();
        if (null != bundle)
        {
            intent.putExtras(bundle);
        }
        intent.setClass(context, activityClass);
        context.startActivityForResult(intent, requestCode);
    }

    // Check for extended activity
    public static Class<?> getExtendedClass(Class<?> originalClass) {
        Class<?> extClass = originalClass;
        //no branding for VisaBuxx
//	    String extClassName = originalClass.getName() + "Extended";
//	    try {
//	        extClass = Class.forName(extClassName);
//	    } catch (ClassNotFoundException e) {
//	    	// Extended class is not found return base
//	    }
        return extClass;
    }

    public static int getPixels(float dps){
        final float scale = CustomApplication.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dps * scale + 0.5f);
    }

    public static int dpToPx(int dp) {
        DisplayMetrics displayMetrics = CustomApplication.getInstance().getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public static int pxToDp(int px) {
        DisplayMetrics displayMetrics = CustomApplication.getInstance().getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public static Point getScreenSize() {
        Point size = new Point();
        WindowManager windowManager = (WindowManager) CustomApplication.getInstance().getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            Display display = windowManager.getDefaultDisplay();
            display.getSize(size);
        } else {
            Display display = windowManager.getDefaultDisplay();
            size.set(display.getWidth(), display.getHeight());
        }
        return size;
    }

    public static void toggleToggleButtonText(Context context, CompoundButton view, boolean isChecked){
        if (isChecked){
            view.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            view.setTextColor(context.getResources().getColor(android.R.color.white));
        }else {
            view.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            view.setTextColor(context.getResources().getColor(android.R.color.darker_gray));
        }
    }


    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    public static void setBackgroundDrawable(View v, Drawable drawable) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            v.setBackgroundDrawable(drawable);
        } else {
            v.setBackground(drawable);
        }
    }


}
