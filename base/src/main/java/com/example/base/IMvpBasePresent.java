package com.example.base;


/**
 * qt
 * 2019-09-16
 */
public interface IMvpBasePresent<V extends IMvpBaseView> {
    void attach(V v);

    void detach();

}
