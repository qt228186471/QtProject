package com.android.network;

import com.android.common.LogUtils;
import com.android.network.config.AuthManager;

import java.util.HashMap;
import java.util.Map;

import okhttp3.HttpUrl;

public abstract class BaseApi{
    private HashMap<String,String> mHeaders = new HashMap<>();
    private INetworkRequest iNetworkRequest = new NetworkRequest();

    /**
     * 生成访问url
     *
     * @param suffix
     * @return
     */
    protected static String generalUrl(String prefix, String suffix) {
        return String.format("%s%s", prefix, suffix);
    }

    /**
     * post请求
     * @param url
     * @param content
     * @param responseListener
     * @return
     */
    protected void post(String url, String content, ResponseListener responseListener){
        //为了保证每次请求都是最新值
        mHeaders.put("Authorization", AuthManager.getInstance().getToken());
        LogUtils.d("header",mHeaders.toString());

        iNetworkRequest.postJson(
                url,
                mHeaders,
                content,
                responseListener);
    }

    /**
     * get请求
     * @param url
     */
    protected void get(String url,ResponseListener responseListener){
        //为了保证每次请求都是最新值
        mHeaders.put("Authorization",AuthManager.getInstance().getToken());
        LogUtils.d("header",mHeaders.toString());

        iNetworkRequest.getRequest(
                url,
                mHeaders,
                responseListener);
    }

    public String addUrlParameters(String url,HashMap<String,String> parameters){
        HttpUrl httpUrl = HttpUrl.parse(url);
        if(parameters!=null && parameters.size()>0){
            HttpUrl.Builder builder = httpUrl.newBuilder();
            for(Map.Entry<String, String> entry: parameters.entrySet()) {
                builder.addQueryParameter(entry.getKey(),entry.getValue());
            }
            httpUrl = builder.build();
        }
        return httpUrl.toString();
    }
}
