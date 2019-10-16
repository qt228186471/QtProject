package com.android.network;

public interface ResponseListener {
    void onSuccess(ResponseData responseData);

    void onFailed(int code, String des);
}
