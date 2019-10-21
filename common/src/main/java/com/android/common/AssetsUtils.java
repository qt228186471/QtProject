package com.android.common;

import android.app.Application;
import android.content.res.AssetManager;


import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * qt
 * 2019-09-28
 */
public class AssetsUtils {
    public static <T> T getAssets(String fileName, Class<T> cls) {
        Application application = AppUtils.getApplication();
        T t = null;
        if (application == null) {
            return null;
        }

        AssetManager assetManager = application.getAssets();
        try {
            InputStream inputStream = assetManager.open(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuffer stringBuffer = new StringBuffer();
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                stringBuffer.append(str);
            }

            Gson gson = new Gson();
            t = gson.fromJson(stringBuffer.toString(), cls);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }
}
