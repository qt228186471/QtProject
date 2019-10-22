package com.android.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * SharedPreferences存取工具类
 */
public class SharedPrefUtil {
    
    //定义一个SharePreference对象
    SharedPreferences mSharedPreferences;
    
    /**
     * 构造方法
     * @param context
     * @param fileName
     */
    public SharedPrefUtil(Context context, String fileName) {
        //实例化SharePreference对象，使用的是get方法，而不是new创建
        //第一个参数是文件的名字
        //第二个参数是存储的模式，一般都是使用私有方式：Context.MODE_PRIVATE
        mSharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }
    
    /**
     * 存储值
     * @param key
     * @param value
     */
    public boolean saveValue(String key, Object value) {
        Editor editor = mSharedPreferences.edit();
        if(value == null){
            editor.remove(key);
        }else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else {
            // 存储自定义对象
            String jsonStr = SerializerFactory.getInstance().toJson(value);
            editor.putString(key,jsonStr);
        }
        return editor.commit();
    }
    
    /**
     * 获取值
     * @param type
     * @param key
     * @param <T>
     * @return
     */
    public <T> T getValue(Class<T> type, String key) {
        Object value = null;
        
        if (type.getSimpleName().equals(Float.class.getSimpleName())) {
            value = mSharedPreferences.getFloat(key, -1);
        } else if (type.getSimpleName().equals(Integer.class.getSimpleName())) {
            value = mSharedPreferences.getInt(key, -1);
        } else if (type.getSimpleName().equals(String.class.getSimpleName())) {
            value = mSharedPreferences.getString(key, "");
        } else if (type.getSimpleName().equals(Long.class.getSimpleName())) {
            value = mSharedPreferences.getLong(key, -1);
        } else if (type.getSimpleName().equals(Boolean.class.getSimpleName())) {
            value = mSharedPreferences.getBoolean(key, false);
        } else {
            // 读取自定义对象
            String jsonStr = mSharedPreferences.getString(key,"");
            if(TextUtils.isEmpty(jsonStr)) {
                return null;
            } else {
                return SerializerFactory.getInstance().fromJson(jsonStr,type);
            }
        }
        return (T) value;
    }
    /**
     * 保存List
     * @param tag
     * @param datalist
     */
    public <T> void saveList(String tag, List<T> datalist) {
        Editor editor = mSharedPreferences.edit();
        if (null == datalist || datalist.size() <= 0){
            editor.remove(tag);
            return;
        }

        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
        editor.putString(tag, strJson);
        editor.commit();
    }

    /**
     * 获取List
     * @param tag
     * @param type 为了避免泛型擦除，这里将type作为参数传入
     * @return
     */
    public <T> List<T> getList(String tag, Type type) {
        List<T> datalist=new ArrayList<T>();
        String strJson = mSharedPreferences.getString(tag, null);
        if (null == strJson) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, type);
        return datalist;

    }

    /**
     * 获取值
     * @param type
     * @param key
     * @param <T>
     * @return
     */
    public <T> T getValue(Class<T> type, String key, Object defaultValue) {
        Object value = null;

        if (type.getSimpleName().equals(Float.class.getSimpleName())) {
            value = mSharedPreferences.getFloat(key, (float)defaultValue);
        } else if (type.getSimpleName().equals(Integer.class.getSimpleName())) {
            value = mSharedPreferences.getInt(key, (int)defaultValue);
        } else if (type.getSimpleName().equals(String.class.getSimpleName())) {
            value = mSharedPreferences.getString(key, (String)defaultValue);
        } else if (type.getSimpleName().equals(Long.class.getSimpleName())) {
            value = mSharedPreferences.getLong(key, (long)defaultValue);
        } else if (type.getSimpleName().equals(Boolean.class.getSimpleName())) {
            value = mSharedPreferences.getBoolean(key, (boolean)defaultValue);
        } else {
            // 读取自定义对象
            String jsonStr = mSharedPreferences.getString(key,"");
            if(TextUtils.isEmpty(jsonStr)) {
                return null;
            } else {
                return SerializerFactory.getInstance().fromJson(jsonStr,type);
            }
        }
        return (T) value;
    }
    /**
     * 删除
     * @param key
     */
    public boolean remove(String key) {
        try {
            Editor editor = mSharedPreferences.edit();
            editor.remove(key);
            return editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * 清空
     */
    public boolean clear(){
        try {
            Editor editor = mSharedPreferences.edit();
            editor.clear();
            return editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
}
