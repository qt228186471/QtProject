package com.android.qtproject.home;

import android.content.Intent;
import android.os.Bundle;

import com.android.qtproject.R;
import com.android.qtproject.base.BaseActivity;
import com.android.qtproject.base.IMvpBasePresent;
import com.android.qtproject.base.IMvpBaseView;

/**
 * qt
 * 2019-09-29
 */
public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected IMvpBaseView getIBaseView() {
        return null;
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    public IMvpBasePresent createPresent() {
        return new HomeActivityPresent();
    }

    @Override
    public int getRes() {
        return R.layout.fragment_home;
    }
}
