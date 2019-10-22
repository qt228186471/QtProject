package com.android.network;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.RequestBody;

/**
 * qt
 * 2019-10-21
 */
public interface INetworkRequest {
    /**
     * GET请求
     * new OkHttpClient;
     * 构造Request对象；
     * 通过前两步中的对象构建Call对象；
     * 在子线程中通过Call#execute()方法来提交同步请求；
     */
    void getRequest(String url, final Map<String,String> headers, final ResponseListener responseListener);


    /**
     * POST方式提交流
     */
    void postStream(String url, final Map<String,String> headers, final String content, ResponseListener responseListener);

    /**
     * json提交
     *
     * @param url
     * @param json
     * @param responseListener
     */
    void postJson(String url, final Map<String,String> headers, String json, ResponseListener responseListener);

    /**
     * 下载文件，图片的方法
     *
     * @param url               下载地址
     * @param saveDir           保存文件路径，handleStream为false才有效
     * @param handleStream，true 直接对返回的流进行操作，false 将数据保存到saveDir文件
     * @param listener          回调监听
     */
    void download(final String url, final String saveDir, final String fileName, final boolean handleStream, @NonNull final DownloadListener listener);


    /**
     * 下载文件
     *
     * @param url          下载路径
     * @param headers
     * @param saveDir      保存文件路径
     * @param fileName     文件名  如果需要使用自己的命名，则传值，否则传空
     * @param handleStream 是否对流进行操作
     * @param listener     回调监听
     */
    void download(final String url, HashMap<String, String> headers,
                  final String saveDir, final String fileName, final boolean handleStream,
                  @NonNull final DownloadListener listener);


    void upload(String url, HashMap<String, String> header, HashMap<String, String> params, HashMap<String, String> files, ResponseListener listener);

    /**
     * 监听文件下载接口
     */
    interface DownloadListener {
        /**
         * 下载成功
         *
         * @param fileName 实际的文件名
         */
        void onDownloadSuccess(String fileName);

        /**
         * 自行处理流
         *
         * @param is 对流进行操作,不对流操作的话可为null
         */
        void onHandleStream(InputStream is);

        /**
         * @param progress 下载进度
         */
        void onDownloading(int progress);

        /**
         * 检查本地文件
         *
         * @param fileName
         * @return
         */
        boolean onLocalFileCheck(String fileName);

        /**
         * 下载失败
         */
        void onDownloadFailed(@Nullable Call call, @Nullable Exception e, @Nullable String errMsg);
    }

}