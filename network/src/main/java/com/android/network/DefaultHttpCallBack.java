package com.android.network;

import com.android.common.LogUtils;
import java.io.IOException;
import java.net.SocketTimeoutException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DefaultHttpCallBack implements Callback {
    private static final String DEBUG_FORMAT = "RESPCODE:%1$s, JSON:%2$s, EXCEPTION:%3$s";
    private ResponseListener mListener;
    public DefaultHttpCallBack(ResponseListener listener){
        mListener = listener;
    }
    @Override
    public void onFailure(Call call, IOException e) {
        try {
            LogUtils.d("ApiRequester", String.format(DEBUG_FORMAT, String.valueOf("0"), call.request().body()==null?"":call.request().body().toString(), e.getMessage()));
            if (mListener != null) {
                if (e instanceof SocketTimeoutException) {
                    mListener.onFailed(NetworkRequest.RESPONSE_REQUEST_TIMEOUT, NetworkRequest.RESPONSE_REQUEST_TIMEOUT_DES);
                } else {
                    mListener.onFailed(NetworkRequest.RESPONSE_REQUEST_NETWORK_ERROR, NetworkRequest.RESPONSE_REQUEST_NETWORK_ERROR_DES);
                }
            }
        } catch (final Exception e1) {
            LogUtils.e("ApiRequester", String.format(DEBUG_FORMAT, String.valueOf("-1"), String.valueOf(e1.getMessage()), e1.getMessage()));
            if (mListener != null) {
                try {
                    mListener.onFailed(NetworkRequest.RESPONSE_REQUEST_TIMEOUT, NetworkRequest.RESPONSE_REQUEST_TIMEOUT_DES);
                } catch (Exception ex) {
                    LogUtils.e("ApiRequester", String.format(DEBUG_FORMAT, String.valueOf("0"), "", ex.getMessage()));
                }
            }
        }
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        try {
            String result = response.body() == null ? "null" : response.body().string();
            LogUtils.d(response.request().url().toString());
            LogUtils.d("response","code:"+response.code()+"====X-B3-TraceId："+response.header("X-B3-TraceId")+"===body:"+result);
            if (mListener != null) {
                ResponseData responseData = new ResponseData();
                responseData.setResult(response.body().string());
                mListener.onSuccess(responseData);
            }
        } catch (final Exception e2) {
            LogUtils.e("ApiRequester", "Response Exception -> " + String.format(DEBUG_FORMAT, String.valueOf(response.code()), "null", String.valueOf(e2.getMessage())));
            if (mListener != null) {
                try {
                    mListener.onFailed(NetworkRequest.RESPONSE_REQUEST_FAILED, NetworkRequest.RESPONSE_REQUEST_FAILED_DES);
                } catch (Exception e) {
                    LogUtils.e("ApiRequester", String.format(DEBUG_FORMAT, String.valueOf("0"), "", e.getMessage()));
                }
            }
        }
    }
}
