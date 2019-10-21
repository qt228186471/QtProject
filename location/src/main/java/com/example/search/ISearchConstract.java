package com.example.search;

import com.amap.api.services.poisearch.PoiSearch;
import com.example.base.IMvpBasePresent;
import com.example.base.IMvpBaseView;

/**
 * qt
 * 2019-10-18
 */
public interface ISearchConstract {
    interface ISearchView extends IMvpBaseView{

    }

    interface ISearchPresent extends IMvpBasePresent<ISearchView> {
        void search(String keyword, PoiSearch.OnPoiSearchListener poiSearchListener);
    }
}
