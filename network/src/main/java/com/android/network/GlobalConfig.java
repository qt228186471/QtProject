package com.android.network;

/**
 * 一些全局的配置信息
 * Created by 顾磊 on 2018/12/5.
 *
 */
public class GlobalConfig {
    /**
     * 是否开启日志打印（运行时改变，需要重启应用）
     */
    private static boolean IS_OPEN_LOG = false;
    
    public static void setOpenLog(boolean isOpenLog) {
        IS_OPEN_LOG = isOpenLog;
    }
    
    public static boolean isOpenLog() {
        return IS_OPEN_LOG;
    }
}
