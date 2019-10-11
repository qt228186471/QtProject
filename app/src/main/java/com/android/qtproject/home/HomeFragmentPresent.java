package com.android.qtproject.home;

import android.util.Log;

import com.android.qtproject.model.Titles;
import com.android.qtproject.utils.AssetsUtils;

/**
 * qt
 * 2019-09-28
 */
public class HomeFragmentPresent implements IHomeBaseFragmentConstract.IHomeBaseFragmentPresent {
    private static final String TAG = "HomeFragmentPresent";
    private IHomeBaseFragmentConstract.IHomeBaseFragmentView view;
    private static final String FILE_NAME = "titles.json";

    @Override
    public void loadHomeData() {
        Titles titles = AssetsUtils.getAssets(FILE_NAME, Titles.class);
        if (titles != null) {
            Log.d(TAG, titles.getDefaults().toString());
        }
    }

    @Override
    public void attach(IHomeBaseFragmentConstract.IHomeBaseFragmentView iHomeBaseFragmentView) {
        view = iHomeBaseFragmentView;
    }

    @Override
    public void detach() {
        view = null;
    }
}
