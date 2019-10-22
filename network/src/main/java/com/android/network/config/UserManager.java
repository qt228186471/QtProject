package com.android.network.config;

import android.content.Context;

import com.android.common.SharedPrefUtil;
import com.android.common.UserInfo;

/**
 *
 * Created by 顾磊 on 2019/1/22.
 */
public class UserManager {
    private static UserManager sInstance;

    private final String KEY_USER = "key_user_info";
    private final String KEY_BIND_DEVICE_TOKEN="key_device_token";

    private SharedPrefUtil mSharedPre;
    private UserInfo mUserInfo;

    private UserManager(Context context){
        mSharedPre = new SharedPrefUtil(context,"user");
        mUserInfo = mSharedPre.getValue(UserInfo.class,KEY_USER);
    }

    public static void init(Context context){
        if(sInstance == null){
            sInstance = new UserManager(context);
        }
    }

    public static UserManager getInstance(){
//        if(sInstance==null){
//            throw new Exception("没有初始化");
//        }
            return sInstance;
    }

    /**
     * 不要全局储存这个对象，地址对象会改变，存储之后没办法及时刷新
     * @return
     */
    public UserInfo getUserInfo(){
        if(mUserInfo==null){
            mUserInfo = mSharedPre.getValue(UserInfo.class,KEY_USER);
        }
        return mUserInfo;
    }

    public void setUserInfo(UserInfo userInfo){
        mSharedPre.saveValue(KEY_USER,userInfo);
        mUserInfo = userInfo;
    }

    public void setDeviceToken(String deviceId){
        mSharedPre.saveValue(KEY_BIND_DEVICE_TOKEN,deviceId);
    }

    public String getDeviceToken(){
        return mSharedPre.getValue(String.class,KEY_BIND_DEVICE_TOKEN);
    }
    public void clear(){
        mSharedPre.clear();
        mUserInfo=null;
    }
}
