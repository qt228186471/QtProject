package com.android.network;

import androidx.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * API环境类型
 * Created by bingyang 2018-04-11  15:14
 */

@IntDef({
        ApiEnv.PROD,           /* 生产环境 */
        ApiEnv.PRE,            /* 预发环境 */
        ApiEnv.TEST,           /* 测试环境 */
        ApiEnv.DEV             /* 调试环境 */
})
@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
public @interface ApiEnv {
    int PROD = 0;
    int PRE = 1;
    int TEST = 2;
    int DEV = 3;
}
