package com.android.qtproject.home;

import com.android.qtproject.model.Titles;
import com.android.qtproject.utils.AssetsUtils;

/**
 * qt
 * 2019-10-07
 */
public class HomeActivityPresent implements IHomeBaseActivityConstract.IHomeBaseActivityPresent {
    private static final String TAG = "HomeActivityPresent";
    private IHomeBaseActivityConstract.IHomeBaseActivityView view;

    @Override
    public void loadTabData() {
        Titles titles = AssetsUtils.getAssets("titles.json", Titles.class);
        if (titles == null) {
            return;
        }
        view.initTitles(titles.getAllTitles());
    }

    @Override
    public void attach(IHomeBaseActivityConstract.IHomeBaseActivityView iHomeBaseActivityView) {
        view = iHomeBaseActivityView;
    }

    @Override
    public void detach() {
        view = null;
    }
}
