package com.android.qtproject.home;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import com.android.qtproject.R;
import com.android.qtproject.adapter.HomeTitlePagerAdapter;
import com.android.qtproject.model.Title;
import com.android.qtproject.base.BaseActivity;
import com.android.qtproject.base.IMvpBasePresent;
import com.android.qtproject.base.IMvpBaseView;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity implements IHomeBaseActivityConstract.IHomeBaseActivityView {
    private IHomeBaseActivityConstract.IHomeBaseActivityPresent iHomeBaseActivityPresent;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment> fragmentList = new ArrayList<>();
    private static final int LIMIT_NUMBER = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected IMvpBaseView getIBaseView() {
        return this;
    }

    @Override
    protected void loadData() {
        iHomeBaseActivityPresent.loadTabData();
    }

    @Override
    protected void initView() {
        tabLayout = findViewById(R.id.tl);
        viewPager = findViewById(R.id.vp);
    }

    @Override
    public IMvpBasePresent createPresent() {
        iHomeBaseActivityPresent = new HomeActivityPresent();
        return iHomeBaseActivityPresent;
    }

    @Override
    public int getRes() {
        return R.layout.activity_home;
    }

    @Override
    public void showTabViews() {

    }

    @Override
    public void initTitles(List<Title> titles) {
        if (titles != null && titles.size() != 0) {
            FragmentManager fragmentManager = getSupportFragmentManager();

            for (int i = 0; i < titles.size(); i++) {
                Fragment homeFragment = HomeFragment.newInstance(fragmentManager,getApplicationContext(),titles.get(i).getName());
                fragmentList.add(homeFragment);
            }

            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

            viewPager.setAdapter(new HomeTitlePagerAdapter(fragmentManager, fragmentList));
            viewPager.setOffscreenPageLimit(LIMIT_NUMBER);
            tabLayout.setupWithViewPager(viewPager);
        }

    }

    @Override
    public Context getContext() {
        return null;
    }
}
