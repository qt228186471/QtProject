package com.android.network.config;

import android.content.Context;
import android.text.TextUtils;

import com.android.common.SharedPrefUtil;


/**
 * Created by 顾磊 on 2019/1/14.
 */
public class AuthManager {

    private String mAdmsToken;

    private volatile String mToken;  //通过volatile来实现对其他线程的可见性

    private static AuthManager sInstance;

    private SharedPrefUtil mSharedPre;

    private final String KEY_TOKEN = "auth_token";

    private final String KEY_ADMS_TOKEN = "auth_adms_token";


    private AuthManager(Context context){
        mSharedPre = new SharedPrefUtil(context,"auth");
        mToken = mSharedPre.getValue(String.class,KEY_TOKEN);
        mAdmsToken = mSharedPre.getValue(String.class,KEY_ADMS_TOKEN);
    }
    public static void init(Context context){
        if(sInstance == null){
            sInstance = new AuthManager(context);
        }
    }

    public static AuthManager getInstance() {
//        if(sInstance==null){
//            throw new Exception("没有初始化");
//        }
        return sInstance;
    }
    public boolean isLogin(){
        return !TextUtils.isEmpty(mToken);
    }

    public void clear(){
        mSharedPre.clear();
        mAdmsToken = "";
        mToken = "";
    }

    public void setAuthInfo(AuthInfo info){
        synchronized (this){
            mToken = info.token;
            mAdmsToken = info.admsToken;
            mSharedPre.saveValue(KEY_TOKEN,info.token);
            mSharedPre.saveValue(KEY_ADMS_TOKEN,info.admsToken);
        }
    }

    public String getToken(){
        synchronized (this){
            if(TextUtils.isEmpty(mToken)){
                mToken = mSharedPre.getValue(String.class,KEY_TOKEN);
                return mToken;
            }
            return mToken;
        }
    }

    public String getAdmsToken(){
        if(TextUtils.isEmpty(mToken)){
            mAdmsToken = mSharedPre.getValue(String.class,KEY_ADMS_TOKEN);
            return mAdmsToken;
        }
        return mAdmsToken;
    }
}
