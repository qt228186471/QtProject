package com.android.qtproject.home;

import com.android.qtproject.model.Title;
import com.example.base.IMvpBasePresent;
import com.example.base.IMvpBaseView;

import java.util.List;

/**
 * qt
 * 2019-10-07
 */
public interface IHomeBaseActivityConstract {
    public interface IHomeBaseActivityView extends IMvpBaseView{
        void showTabViews();
        void initTitles(List<Title> titles);
    }

    public interface IHomeBaseActivityPresent extends IMvpBasePresent<IHomeBaseActivityView>{
        void loadTabData();
    }
}
