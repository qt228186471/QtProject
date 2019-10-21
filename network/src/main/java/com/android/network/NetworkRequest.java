package com.android.network;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.common.AppUtils;
import com.android.common.LogUtils;
import com.android.common.NetworkUtils;
import com.android.network.encrypt.EncryptUtils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.RealResponseBody;
import okio.BufferedSink;

public class NetworkRequest {
    public static final int RESPONSE_SERVER_ERROR = 0;
    public static final int RESPONSE_EMPTY = -1;
    public static final int RESPONSE_DATA_EXCEPTION = -2;
    public static final int RESPONSE_NATIVE_HANDLE_ERROR = -3;
    public static final int RESPONSE_NO_NET = -4;
    public static final int RESPONSE_REQUEST_DATA_ERROR = -5;
    public static final int RESPONSE_REQUEST_TIMEOUT = -6;
    public static final int RESPONSE_REQUEST_NETWORK_ERROR = -7;
    public static final int RESPONSE_REQUEST_FAILED = -8;
    public static final String RESPONSE_EMPTY_DES = "返回数据为空";
    public static final String RESPONSE_DATA_EXCEPTION_DES = "返回数据异常";
    public static final String RESPONSE_NATIVE_HANDLE_DES = "本地处理错误";
    public static final String RESPONSE_NO_NET_DES = "没有网络";
    public static final String RESPONSE_REQUEST_DATA_ERROR_DES = "请求格式错误";
    public static final String RESPONSE_REQUEST_TIMEOUT_DES = "请求超时";
    public static final String RESPONSE_REQUEST_NETWORK_ERROR_DES = "网络异常";
    public static final String RESPONSE_REQUEST_FAILED_DES = "请求失败";


    private static final String MEDIA_TYPE_STREAM = "application/octet-stream; charset=utf-8";
    private static final String MEDIA_TYPE_JSON = "application/json";

    private final long mDefaultOutTime = 10000;//默认的超时时间


    /**
     * K: 超时时间
     * V: 对应的OkHttpClient实例
     */
    private Map<Long, OkHttpClient> mInstanceMap = new Hashtable<>();

    private OkHttpClient getInstance(long timeout) {
        // 将new Long(timeout)修改为timeout
        if (mInstanceMap.containsKey(timeout)) {
            return mInstanceMap.get(timeout);
        } else {
            SSLSocketFactory[] socketFactory = new SSLSocketFactory[1];
            X509TrustManager[] trustManager = new X509TrustManager[1];
            SSLCertificatesInit.init(socketFactory, trustManager);

            OkHttpClient client = new OkHttpClient()
                    .newBuilder()
                    .sslSocketFactory(socketFactory[0], trustManager[0])
                    .connectTimeout(timeout, TimeUnit.MILLISECONDS)
                    .writeTimeout(timeout, TimeUnit.MILLISECONDS)
                    .readTimeout(timeout, TimeUnit.MILLISECONDS)
                    .build();
            mInstanceMap.put(timeout, client);
            return client;
        }
    }


    /**
     * GET请求
     * new OkHttpClient;
     * 构造Request对象；
     * 通过前两步中的对象构建Call对象；
     * 在子线程中通过Call#execute()方法来提交同步请求；
     */
    public void getRequest(String url, final ResponseListener responseListener) {
        if (TextUtils.isEmpty(url) || responseListener == null) {
            responseListener.onFailed(RESPONSE_REQUEST_DATA_ERROR, RESPONSE_REQUEST_DATA_ERROR_DES);
            return;
        }
        if (!NetworkUtils.isNetworkAvailable()) {
            if (responseListener != null) {
                responseListener.onFailed(RESPONSE_NO_NET, RESPONSE_NO_NET_DES);
            }
            return;
        }

        try {
            OkHttpClient okHttpClient = getInstance(mDefaultOutTime);
            final Request request = new Request.Builder()
                    .url(url)
                    .get()//默认就是GET请求
                    .build();
            final Call call = okHttpClient.newCall(request);
            final ResponseData responseData = new ResponseData();

            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    responseListener.onFailed(RESPONSE_SERVER_ERROR, RESPONSE_DATA_EXCEPTION_DES);
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if (response == null) {
                        responseListener.onFailed(RESPONSE_EMPTY, RESPONSE_EMPTY_DES);
                    } else {
                        responseData.setResult(response.body().string());
                        responseListener.onSuccess(responseData);
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            responseListener.onFailed(RESPONSE_NATIVE_HANDLE_ERROR, RESPONSE_NATIVE_HANDLE_DES);
        }
    }


    //post提交

    /**
     * POST方式提交String
     * 在构造 Request对象时，需要多构造一个RequestBody对象，携带要提交的数据。
     * 在构造 RequestBody 需要指定MediaType，用于描述请求/响应 body 的内容类型
     */
    private void post(String url, RequestBody requestBody, final ResponseListener responseListener) {
        if (TextUtils.isEmpty(url) || responseListener == null) {
            responseListener.onFailed(RESPONSE_REQUEST_DATA_ERROR, RESPONSE_REQUEST_DATA_ERROR_DES);
            return;
        }

        if (!NetworkUtils.isNetworkAvailable()) {
            if (responseListener != null) {
                responseListener.onFailed(RESPONSE_NO_NET, RESPONSE_NO_NET_DES);
            }
            return;
        }
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
            OkHttpClient okHttpClient = getInstance(mDefaultOutTime);
            final ResponseData responseData = new ResponseData();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    responseListener.onFailed(RESPONSE_DATA_EXCEPTION, RESPONSE_DATA_EXCEPTION_DES + ":" + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
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
                }
            });
        } catch (Exception e) {
            responseListener.onFailed(RESPONSE_NATIVE_HANDLE_ERROR, RESPONSE_NATIVE_HANDLE_DES);
            e.printStackTrace();
        }
    }

    /**
     * POST方式提交流
     */
    public void postStreamRequest(String url, final String content, ResponseListener responseListener) {
        if (TextUtils.isEmpty(content) || TextUtils.isEmpty(content) || responseListener == null) {
            responseListener.onFailed(RESPONSE_REQUEST_DATA_ERROR, RESPONSE_REQUEST_DATA_ERROR_DES);
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
        post(url, requestBody, responseListener);
    }


    /**
     * json提交
     *
     * @param url
     * @param json
     * @param responseListener
     */
    public void postJsonRequest(String url, String json, ResponseListener responseListener) {
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(json) || responseListener == null) {
            responseListener.onFailed(RESPONSE_REQUEST_DATA_ERROR, RESPONSE_REQUEST_DATA_ERROR_DES);
            return;
        }

        MediaType mediaType = MediaType.parse(MEDIA_TYPE_JSON);
        RequestBody requestBody = RequestBody.create(json, mediaType);
        post(url, requestBody, responseListener);
    }


    /**
     * 下载文件，图片的方法
     *
     * @param url               下载地址
     * @param saveDir           保存文件路径，handleStream为false才有效
     * @param handleStream，true 直接对返回的流进行操作，false 将数据保存到saveDir文件
     * @param listener          回调监听
     */
    public void download(final String url, final String saveDir, final String fileName,final boolean handleStream, @NonNull final INetworkRequest.DownloadListener listener) {
        download(url,null,saveDir,fileName,handleStream,listener);
    }

    /**
     * 下载文件
     * @param url          下载路径
     * @param headers
     * @param saveDir      保存文件路径
     * @param fileName     文件名  如果需要使用自己的命名，则传值，否则传空
     * @param handleStream 是否对流进行操作
     * @param listener     回调监听
     */
    public void download(final String url, HashMap<String, String> headers, final String saveDir, final String fileName, final boolean handleStream, @NonNull final INetworkRequest.DownloadListener listener) {
        Request.Builder builder = new Request.Builder();
        if(headers!=null){
            builder.headers(Headers.of(headers));
        }
        Request request = builder.url(url).build();

        getInstance(mDefaultOutTime).newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                // 下载失败
                listener.onDownloadFailed(call, e, "下载失败");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                InputStream is = null;
                String serverFileName = fileName;
                try {
                    if (response.code() == 200) {
                        ResponseBody responseBody = response.body();
                        if(responseBody==null || !(responseBody instanceof RealResponseBody)){
                            listener.onDownloadFailed(null,null,"Response not RealResponseBody");
                            return;
                        }
                        is = responseBody.byteStream();
                        if (handleStream) {
                            listener.onHandleStream(is);
                            return;
                        }
                        if(TextUtils.isEmpty(serverFileName)){
                            //文件名为空，使用网络名称
                            String disposition = response.header("Content-Disposition");
                            if(!TextUtils.isEmpty(disposition)){
                                String[] str = disposition.split(";");
                                if(str.length>1){
                                    //这里为了处理一些带空格的情况
                                    serverFileName = URLDecoder.decode(str[1].trim().replaceFirst("filename=","").replace("\"","").trim(),"utf-8");
                                }
                            }
                            if(TextUtils.isEmpty(serverFileName)){
                                //网络名称获取失败，使用url的md5
                                serverFileName = EncryptUtils.md5(url);
                            }
                        }

                        if(!listener.onLocalFileCheck(serverFileName)){
                            //本地没有该文件，则继续下载
                            download(is,saveDir,serverFileName,response, listener);
                        }else {
                            //本地已经存在该文件
                            listener.onDownloadSuccess(serverFileName);
                        }
                    } else {
                        listener.onDownloadFailed(null, null, "Response Code Not 200");
                    }
                } catch (FileNotFoundException e) {
                    listener.onDownloadFailed(call, e, "File Not Found");
                } catch (IOException e) {
                    listener.onDownloadFailed(call, e, "IOException");
                } catch (NullPointerException e){
                    listener.onDownloadFailed(call, e, "IOException");
                }finally {
                    try {
                        //handleStream为true情况下需要自行关闭流！！
                        if (!handleStream && is != null) {
                            is.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void upload(String url, HashMap<String, String> header, HashMap<String, String> params, HashMap<String, String> files, ResponseListener listener) {
        upload(url,header,params,files,"*/*",listener);
    }

    public void upload(String url, HashMap<String, String> header, HashMap<String, String> params, HashMap<String, String> files, String mediaType, ResponseListener callback) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

        if(params!=null){
            for(Map.Entry<String, String> entry: params.entrySet()) {
                builder.addFormDataPart(entry.getKey(),entry.getValue());
            }
        }
        if(files!=null){
            for(Map.Entry<String, String> entry: files.entrySet()) {
                File file = new File(entry.getValue());
                builder.addFormDataPart("file", URLEncoder.encode(file.getName())
                        ,RequestBody.create(MediaType.parse(mediaType), file));
            }
        }

        RequestBody requestBody = builder.build();
        Request.Builder requestBuilder = new Request.Builder()
                .url(url);
        if(header!=null){
            requestBuilder.headers(Headers.of(header));
        }
        Request request = requestBuilder.post(requestBody)
                .build();
        getInstance(mDefaultOutTime).newCall(request).enqueue(new DefaultHttpCallBack(callback));
    }

    /**
     * 提供给public的download使用
     */
    private void download(InputStream is,String saveDir,String fileName, Response response, INetworkRequest.DownloadListener listener) throws IOException {
        LogUtils.e("从网上下载数据！！");
        File dir = new File(saveDir);
        if(!dir.exists()){
            dir.mkdirs();
        }
        File file = new File(saveDir+File.separator+fileName);

        FileOutputStream fos = new FileOutputStream(file);

        byte[] buf = new byte[2048];
        long total = response.body()==null ? 0 : response.body().contentLength();
        long sum = 0;
        int len;
        while ((len = is.read(buf)) != -1) {
            fos.write(buf, 0, len);
            sum += len;
            int progress = (int) (sum * 1.0f / total * 100);
            // 下载中
            listener.onDownloading(progress);
        }
        fos.flush();
        // 下载完成
        listener.onDownloadSuccess(fileName);
    }

}
