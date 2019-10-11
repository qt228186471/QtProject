package com.android.qtproject;

import android.app.Application;
import android.content.Context;

import com.android.qtproject.utils.AppUtils;

/**
 * qt
 * 2019-09-28
 */
public class QtApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppUtils.setApplication(this);
    }
}
