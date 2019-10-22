package com.android.network.config;


import android.content.Context;

import com.android.common.SharedPrefUtil;
import com.android.network.ApiEnv;
import com.android.network.BuildConfig;
import com.android.network.GlobalConfig;

/**
 * 全局配置类
 */
public class AppConfig {

    private static AppConfig I;

    public static void init(AppConfig i) {
        if (I != null) {
            throw new RuntimeException("canot init twice!");
        }
        I = i;
    }

    public static AppConfig getInstance() {
        if (I == null) {
            throw new RuntimeException("need invoke init() first!");
        }
        return I;
    }

    /**
     * 网关环境
     */
    private @ApiEnv
    int mApiEnv = BuildConfig.IN_JENKINS?ApiEnv.TEST:ApiEnv.PROD;
    /**
     * 是否开启全局日志
     */
    private boolean isOpenLog;

    /**
     * 是否开启bugtags上报
     */
    private boolean isReportBug = false;
   
    /**
     * APP版本号
     */
    private String mAppVersion;

    // 需要配置到配置文件，不能动态切换
    // debuggable、混淆、

    public @ApiEnv
    int getApiEnv() {
        return mApiEnv;
    }

    public void setApiEnv(Context context, @ApiEnv int mApiEnv) {
        SharedPrefUtil apiSharePref = new SharedPrefUtil(context,"api_config");
        apiSharePref.saveValue("api",mApiEnv);
        this.mApiEnv = mApiEnv;
    }

    public boolean isOpenLog() {
        return isOpenLog;
    }

    public void setOpenLog(boolean openLog) {
        isOpenLog = openLog;
        // 设置 base 是否开启日志开关
        GlobalConfig.setOpenLog(isOpenLog);
    }

    public boolean isReportBug() {
        return isReportBug;
    }

    public void setReportBug(boolean reportBug) {
        isReportBug = reportBug;
    }

    public String getAppVersion() {
        return mAppVersion;
    }

    public void setAppVersion(String mAppVersion) {
        this.mAppVersion = mAppVersion;
    }

    public static class Builder {

        public static Builder newInstance() {
            return new Builder();
        }

        private AppConfig mConfig = new AppConfig();

        public Builder setOpenLog(boolean isOpen) {
            mConfig.setOpenLog(isOpen);
            return this;
        }

        public Builder setReportBug(boolean isReportBug) {
            mConfig.setReportBug(isReportBug);
            return this;
        }
        
        public Builder setAppVersion(String appVersion) {
            mConfig.setAppVersion(appVersion);
            return this;
        }

        public AppConfig build(Context context) {
            if(!BuildConfig.IN_JENKINS){
                mConfig.setApiEnv(context,ApiEnv.PROD);
            }else {
                SharedPrefUtil apiSharePref = new SharedPrefUtil(context,"api_config");
                int api = apiSharePref.getValue(Integer.class,"api");
                if(api!=-1){
                    mConfig.setApiEnv(context,api);
                }
            }
            return mConfig;
        }
    }
}
