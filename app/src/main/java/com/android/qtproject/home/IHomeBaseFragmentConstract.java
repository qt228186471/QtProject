package com.android.qtproject.home;

import com.android.qtproject.base.IMvpBasePresent;
import com.android.qtproject.base.IMvpBaseView;

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
