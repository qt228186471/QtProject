package com.android.qtproject.base;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
    protected IMvpBasePresent iMvpBasePresent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
