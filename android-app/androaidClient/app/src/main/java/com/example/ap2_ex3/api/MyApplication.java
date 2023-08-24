package com.example.ap2_ex3.api;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {
    private static int currentTheme;
    private static List<Activity> activityList = new ArrayList<>();
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        currentTheme = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        context = getApplicationContext();
    }

    public static int getCurrentTheme() {
        return currentTheme;
    }

    public static void setCurrentTheme(int theme) {
        currentTheme = theme;
    }

    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    public static void recreateAllActivities() {
        for (Activity activity : activityList) {
            if (!activity.isFinishing()) {
                activity.recreate();
            }
        }
    }
}
