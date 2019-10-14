package com.android.network;

import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.example.base.GeelyThreadPool;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class NetworkManager {
    public static final int RESPONSE_EMPTY = -1;
    public static final int RESPONSE_DATA_EXCEPTION = -2;
    public static final String RESPONSE_EMPTY_DES = "返回数据为空";
    public static final String RESPONSE_DATA_EXCEPTION_DES = "返回数据异常";


    private static final String MEDIA_TYPE_STREAM = "application/octet-stream; charset=utf-8";
    private static final String MEDIA_TYPE_JSON = "application/json";

    /**
     * GET请求
     * new OkHttpClient;
     * 构造Request对象；
     * 通过前两步中的对象构建Call对象；
     * 在子线程中通过Call#execute()方法来提交同步请求；
     */
    public void getRequests(String url, final ResponseListener responseListener) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求
                .build();
        final Call call = okHttpClient.newCall(request);
        final ResponseData responseData = new ResponseData();
        GeelyThreadPool.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                //直接execute call
                Response response = null;
                try {
                    response = call.execute();
                    if (response != null && response.isSuccessful()) {
                        responseData.setResult(response.body().string());
                        responseListener.onSuccess(responseData);
                    } else {
                        if (response == null) {
                            responseListener.onFailed(RESPONSE_EMPTY, RESPONSE_EMPTY_DES);
                        } else {
                            responseListener.onFailed(response.code(), response.message());
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    //post提交

    /**
     * POST方式提交String
     * 在构造 Request对象时，需要多构造一个RequestBody对象，携带要提交的数据。
     * 在构造 RequestBody 需要指定MediaType，用于描述请求/响应 body 的内容类型
     */
    private void postString(String url, RequestBody requestBody, final ResponseListener responseListener) {
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        final ResponseData responseData = new ResponseData();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                responseListener.onFailed(RESPONSE_DATA_EXCEPTION, RESPONSE_DATA_EXCEPTION_DES + ":" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    response = call.execute();
                    if (response != null && response.isSuccessful()) {
                        responseData.setResult(response.body().toString());
                        responseListener.onSuccess(responseData);
                    } else {
                        if (response == null) {
                            responseListener.onFailed(RESPONSE_EMPTY, RESPONSE_EMPTY_DES);
                        } else {
                            responseListener.onFailed(response.code(), response.message());
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * POST方式提交流
     */
    public void postStream(String url, final String content, ResponseListener responseListener) {
        if (TextUtils.isEmpty(content)) {
            return;
        }
        RequestBody requestBody = new RequestBody() {
            @Nullable
            @Override
            public MediaType contentType() {
                return MediaType.parse(MEDIA_TYPE_STREAM);
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                sink.writeUtf8(content);
            }
        };
        postString(url, requestBody, responseListener);
    }


    /**
     * json提交
     * @param url
     * @param json
     * @param responseListener
     */
    public void postJson(String url, String json, ResponseListener responseListener) {
        MediaType mediaType = MediaType.parse(MEDIA_TYPE_JSON);
        RequestBody requestBody = RequestBody.create(json, mediaType);
        postString(url, requestBody, responseListener);
    }
}
