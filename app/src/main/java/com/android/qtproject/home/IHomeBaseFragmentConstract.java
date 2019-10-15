package com.android.qtproject.home;

import com.example.base.IMvpBasePresent;
import com.example.base.IMvpBaseView;

/**
 * qt
 * 2019-09-28
 */
public interface IHomeBaseFragmentConstract {
    interface IHomeBaseFragmentPresent extends IMvpBasePresent<IHomeBaseFragmentView>{
        void loadHomeData();
    }

    interface IHomeBaseFragmentView extends IMvpBaseView{
        void showHomeView();
    }

}
