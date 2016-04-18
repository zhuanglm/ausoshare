package com.auroratechdevelopment.common.persistent;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.auroratechdevelopment.ausoshare.CustomApplication;
import com.auroratechdevelopment.common.DebugLogUtil;


public class SharedPreferenceManager {

    public static SharedPreferences preferences = PreferenceManager
            .getDefaultSharedPreferences(CustomApplication.getInstance());

    public static String getString(String key) {
        String strValue = preferences.getString(key, "");
        DebugLogUtil.LogD(String.format(
                "get persistent value, key: %s; getValue: %s", key, strValue));
        return strValue;
    }

    public static boolean setString(String key, String value) {
        boolean bSuccess = false;
        DebugLogUtil.LogD(String.format(
                "Save persistent value, key: %s; value %s", key, value));

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        bSuccess = editor.commit();
        DebugLogUtil.LogD(String.format(
                "Save persistent value, key: %s; value %s; result: %s", key,
                value, bSuccess));
        return bSuccess;
    }

    public static boolean getBoolean(String key) {
        boolean strValue = preferences.getBoolean(key, false);
        DebugLogUtil.LogD(String.format(
                "get persistent value, key: %s; getValue: %s", key, strValue));
        return strValue;
    }

    public static boolean setBoolean(String key, boolean value) {
        boolean bSuccess = false;
        DebugLogUtil.LogD(String.format(
                "Save persistent value, key: %s; value %s", key, value));

        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        bSuccess = editor.commit();
        DebugLogUtil.LogD(String.format(
                "Save persistent value, key: %s; value %s; result: %s", key,
                value, bSuccess));
        return bSuccess;
    }

    public static int getInt(String key) {
        int val = preferences.getInt(key, 0);
        DebugLogUtil.LogD(String.format(
                "get persistent value, key: %s; getValue: %s", key, val));
        return val;
    }

    public static boolean setInt(String key, int value) {
        boolean bSuccess = false;
        DebugLogUtil.LogD(String.format(
                "Save persistent value, key: %s; value %s", key, value));

        if (TextUtils.isEmpty(key)) {
            return bSuccess;
        }

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        bSuccess = editor.commit();
        DebugLogUtil.LogD(String.format(
                "Save persistent value, key: %s; value %s; result: %s", key,
                value, bSuccess));
        return bSuccess;
    }

    public static long getLong(String key) {
        long val = preferences.getLong(key, 0);
        return val;
    }

    public static boolean setLong(String key, long value) {
        boolean bSuccess = false;
        if (TextUtils.isEmpty(key)) {
            return bSuccess;
        }

        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value);
        bSuccess = editor.commit();
        return bSuccess;
    }

    public static boolean clearAll() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        return editor.commit();

    }
}
