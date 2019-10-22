package com.android.common;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by yangbing 2017/3/10.
 */
public interface ISerializer {
    
    String toJson(Map object);
    
    String toJson(Object object);
    
    <T> T fromJson(String json, Class<T> cls);
    
    <T> T fromJson(String json, Type type);
}
