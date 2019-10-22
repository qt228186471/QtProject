package com.android.common;

import com.android.common.ISerializer;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by yangbing 2017/3/11.
 */
public class GsonSerializer implements ISerializer {
    
    private com.google.gson.Gson mGson = new Gson();
    
    @Override
    public String toJson(Map object) {
        return mGson.toJson(object);
    }
    
    @Override
    public String toJson(Object object) {
        return mGson.toJson(object);
    }
    
    @Override
    public <T> T fromJson(String json, Class<T> cls) {
        return mGson.fromJson(json, cls);
    }
    
    @Override
    public <T> T fromJson(String json, Type type) {
        return mGson.fromJson(json, type);
    }
}
