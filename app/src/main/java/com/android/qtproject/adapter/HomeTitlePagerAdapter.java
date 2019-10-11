package com.android.qtproject.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.android.qtproject.home.HomeFragment;

import java.util.List;

/**
 * qt
 * 2019-10-08
 */
public class HomeTitlePagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;


    public HomeTitlePagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        if (fragments == null || fragments.get(position) == null) {
            return null;
        }
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        if (fragments == null) {
            return 0;
        }
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).getArguments().getString(HomeFragment.KEY);
    }
}
