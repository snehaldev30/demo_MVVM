package com.demomvvm.utils;

import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;

import com.demomvvm.MainApplication;

public class MySharedPreferences {
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    public static void init() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainApplication.getContext());
    }

    public static void putString(String key, String value) {
        editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(String key) {
        return sharedPreferences.getString(key, "");
    }
}
