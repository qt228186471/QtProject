package com.android.userlogin;

import java.util.ArrayList;
import java.util.List;

/**
 * qt
 * 2019-10-11
 */
public class LoginManager {
    private static volatile LoginManager mInstance;
    private List<LoginCallback> loginCallbacks = new ArrayList<>();

    private LoginManager() {

    }

    public static LoginManager getInstance() {
        if (mInstance != null) {
            synchronized (LoginManager.class) {
                if (mInstance != null) {
                    mInstance = new LoginManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 增加登录监听
     * 注：需手动remove，否则有可能发生内存泄漏
     *
     * @param loginCallback
     */
    public synchronized void addLoginListener(LoginCallback loginCallback) {
        if (loginCallback == null) {
            return;
        }
        loginCallbacks.add(loginCallback);
    }

    /**
     * 删除登录监听
     *
     * @param loginCallback
     */
    public synchronized void removeLoginListener(LoginCallback loginCallback) {
        if (loginCallback == null) {
            return;
        }
        loginCallbacks.remove(loginCallback);
    }

    /**
     * 增加登录监听
     * 注：回调一次后自动remove，不会发生内存泄漏
     *
     * @param loginCallback
     */
    public synchronized void addLoginListenerOnce(LoginCallback loginCallback) {
        if (loginCallback == null) {
            return;
        }
        loginCallback.useOnce = true;
    }

    /**
     * 登录成功回调
     */
    public synchronized void loginSuccess() {
        for (int i = 0; i < loginCallbacks.size(); i++) {
            LoginCallback loginCallback = loginCallbacks.get(i);
            loginCallback.success();
            if (loginCallback.useOnce) {
                loginCallbacks.remove(loginCallback);
            }
        }

    }

    /**
     * 登录失败回调
     */
    public synchronized void loginFailed(int code, String error) {
        for (int i = 0; i < loginCallbacks.size(); i++) {
            LoginCallback loginCallback = loginCallbacks.get(i);
            loginCallback.failed(code, error);
            if (loginCallback.useOnce) {
                loginCallbacks.remove(loginCallback);
            }
        }
    }

    public abstract class LoginCallback {
        public boolean useOnce = false;

        abstract void success();

        abstract void failed(int code, String errMsg);
    }

}
