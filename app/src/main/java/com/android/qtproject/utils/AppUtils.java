package com.android.qtproject.utils;

import android.app.Application;

/**
 * qt
 * 2019-09-28
 */
public class AppUtils {
    private static Application app;

    public static void setApplication(Application application) {
        if (application == null) {
            return;
        }
        app = application;
    }

    public static Application getApplication() {
        return app;
    }
}
