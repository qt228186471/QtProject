package com.android.qtproject.home;

import android.util.Log;

import com.android.network.NetworkManager;
import com.android.network.ResponseData;
import com.android.network.ResponseListener;
import com.android.qtproject.model.Titles;
import com.android.qtproject.model.Weathers;
import com.android.qtproject.utils.AssetsUtils;
import com.google.gson.Gson;

/**
 * qt
 * 2019-09-28
 */
public class HomeFragmentPresent implements IHomeBaseFragmentConstract.IHomeBaseFragmentPresent {
    private static final String TAG = "HomeFragmentPresent";
    private IHomeBaseFragmentConstract.IHomeBaseFragmentView view;
    private static final String FILE_NAME = "titles.json";
    private static final String URL = "http://api.help.bj.cn/apis/aqi3?id=changchun";

    @Override
    public void loadHomeData() {
        NetworkManager networkManager = new NetworkManager();
        networkManager.getRequests(URL, new ResponseListener() {
            @Override
            public void onSuccess(ResponseData responseData) {
                Gson gson = new Gson();
                Weathers weathers = gson.fromJson(responseData.getResult(), Weathers.class);
                Log.d(TAG, weathers.toString());
            }

            @Override
            public void onFailed(int code, String des) {
                Log.d(TAG, "failed:" + code + "," + des);
            }
        });
    }

    @Override
    public void attach(IHomeBaseFragmentConstract.IHomeBaseFragmentView iHomeBaseFragmentView) {
        view = iHomeBaseFragmentView;
    }

    @Override
    public void detach() {
        view = null;
    }
}
