package com.demomvvm.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.demomvvm.activity.BaseActivity;

/**
 * Created by Snehal on 1/15/2020.
 */
public class KeyboardUtils {
    public static void closeKeyboard(Context context) {
        if (context instanceof BaseActivity) {
            View view = ((BaseActivity) context).getCurrentFocus();
            if (view != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null)
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
}
