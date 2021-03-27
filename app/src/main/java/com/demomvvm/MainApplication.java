package com.demomvvm;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.demomvvm.utils.MySharedPreferences;

public class MainApplication extends Application {
    private static MainApplication application;
    private RequestQueue mRequestQueue;
    public static final String TAG = MainApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        MySharedPreferences.init();
        MySharedPreferences sharedPreferences = new MySharedPreferences();
    }

    public static Context getContext() {
        return application;
    }

    public static MainApplication getInstance() {
        return application;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }
}
