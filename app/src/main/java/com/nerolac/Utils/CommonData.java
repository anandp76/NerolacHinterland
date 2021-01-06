package com.nerolac.Utils;

import android.app.Activity;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

public class CommonData {
    public static void setTranceprent(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(activity, color));
        }
    }
}
