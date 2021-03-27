package com.demomvvm.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.demomvvm.R;
import com.demomvvm.customView.CustomProgressDialog;
import com.demomvvm.utils.KeyboardUtils;
import java.util.List;

public class BaseActivity extends AppCompatActivity {
    public static Toast toast;
    private boolean doubleBackToExitPressedOnce = false;
    public static CustomProgressDialog customProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static void showToast(Context context, String message) {
        try {
            if (toast != null) {
                toast.cancel();
                toast = null;
            }
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * show progress  dialog on screen
     * @param context :
     */
    public static void showProgressDialog(Context context) {
        try {
            if (context instanceof Activity) {
                customProgressDialog = new CustomProgressDialog(context);
                customProgressDialog.CreateDialog(context);
                if (!customProgressDialog.isShowing()) {
                    customProgressDialog.setCancelable(true);
                    customProgressDialog.show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * hide progress dialog
     */
    public static void dismissProgressDialog() {
        try {
            if (customProgressDialog != null && customProgressDialog.isShowing()) {
                customProgressDialog.dismiss();
                customProgressDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        // close keyboard
        KeyboardUtils.closeKeyboard(this);

        ActivityManager activityManager = (ActivityManager) getSystemService( ACTIVITY_SERVICE );
        List<ActivityManager.RunningTaskInfo> taskList = activityManager.getRunningTasks(10);
        if(taskList.get(0).numActivities == 1 && taskList.get(0).topActivity.getClassName().equals(this.getClass().getName())) {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        } else {
            super.onBackPressed();
            overridePendingTransition(R.anim.slide_from_left_to_center, R.anim.slide_from_center_to_right);
        }
    }
}
