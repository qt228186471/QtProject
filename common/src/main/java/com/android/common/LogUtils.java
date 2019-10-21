package com.android.common;


import androidx.annotation.Nullable;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * 全局日志类
 * Created by gulei
 */
public class LogUtils {
    
    public static void init(final boolean isDebug) {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(0)         // (Optional) How many method line to show. Default 2
                .methodOffset(5)        // (Optional) Hides internal method calls up to offset. Default 5
//                .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("geely")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy){
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return isDebug;
            }
        });
    }
    
    public static void i(String tag, String msg) {
        Logger.t(tag).i(msg);
    }
    
    public static void d(String tag, String msg) {
        Logger.t(tag).d(msg);
    }
    
    public static void d(String msg) {
        Logger.d(msg);
    }
    
    public static void v(String tag, String msg) {
        Logger.t(tag).v(msg);
    }
    
    public static void e(String tag, String msg) {
        Logger.t(tag).e(msg);
    }
    
    public static void e(String msg) {
        Logger.e(msg);
    }
    
    public static void e(String tag, String msg, Exception e) {
        Logger.t(tag).e(e,msg);
    }
    
    public static void w(String tag, String msg) {
        Logger.t(tag).w(msg);
    }
    
    public static void json(String msg) {
        Logger.json(msg);
    }
    
    public static void xml(String tag, String msg) {
        Logger.t(tag).xml(msg);
    }
}
