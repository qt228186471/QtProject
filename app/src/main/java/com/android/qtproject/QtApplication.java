package com.android.qtproject;

import android.app.Application;

import androidx.multidex.MultiDex;

import com.android.common.AppUtils;

/**
 * qt
 * 2019-09-28
 */
public class QtApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppUtils.setApplication(this);
        MultiDex.install(this);
    }
}
