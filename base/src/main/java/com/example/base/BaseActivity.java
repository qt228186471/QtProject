package com.example.base;

import android.os.Bundle;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
    protected IMvpBasePresent iMvpBasePresent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置没有titlebar的主题
        setTheme(R.style.Theme_AppCompat_Light_NoActionBar);
        setContentView(getRes());
        iMvpBasePresent = createPresent();
        iMvpBasePresent.attach(getIBaseView());
        initView();
        loadData();
    }

    protected abstract IMvpBaseView getIBaseView();

    protected abstract void loadData();

    protected abstract void initView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iMvpBasePresent.detach();
    }

    public abstract IMvpBasePresent createPresent();

    public abstract int getRes();

}
