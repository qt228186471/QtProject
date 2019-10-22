package com.android.common;



/**
 * Created by yangbing 2017/3/10.
 */
public class SerializerFactory {
    
    private static ISerializer mInstance;
    
    public static ISerializer getInstance() {
        synchronized (SerializerFactory.class) {
            if (mInstance == null) {
                synchronized (SerializerFactory.class) {
                    mInstance = new GsonSerializer();
                }
            }
        }
        return mInstance;
    }
}
