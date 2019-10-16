package com.android.qtproject.detail;

import android.os.Bundle;

import com.android.qtproject.R;
import com.example.base.BaseActivity;
import com.example.base.IMvpBasePresent;
import com.example.base.IMvpBaseView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import androidx.appcompat.widget.Toolbar;

public class NewPageActivity extends BaseActivity {
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private SmartRefreshLayout smartRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        toolbar = findViewById(R.id.toolbar);
        fab = findViewById(R.id.fab);
        smartRefreshLayout = findViewById(R.id.srl);

//        smartRefreshLayout.setRefreshHeader()
    }

    @Override
    public IMvpBasePresent createPresent() {
        return null;
    }

    @Override
    public int getRes() {
        return R.layout.activity_new_page;
    }

}
