package com.example.search;

import com.amap.api.services.poisearch.PoiSearch;

/**
 * qt
 * 2019-10-18
 */
public class SearchPresent implements ISearchConstract.ISearchPresent {
    private ISearchConstract.ISearchView view;
    private PoiSearch poiSearch;

    @Override
    public void search(String keyword, PoiSearch.OnPoiSearchListener poiSearchListener) {
        PoiSearch.Query query = new PoiSearch.Query(keyword, "", "");
        //keyWord表示搜索字符串，
        //第二个参数表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码，码表可以参考下方（而非文字）
        //cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
        query.setPageSize(10);// 设置每页最多返回多少条poiitem
        query.setPageNum(0);//设置查询页码

        //构造 PoiSearch 对象，并设置监听
        poiSearch = new PoiSearch(view.getContext(), query);


        //设置监听
        if (poiSearchListener != null) {
            poiSearch.setOnPoiSearchListener(poiSearchListener);
        }

        //调用 PoiSearch 的 searchPOIAsyn() 方法发送请求
        poiSearch.searchPOIAsyn();
    }

    @Override
    public void attach(ISearchConstract.ISearchView iSearchView) {
        view = iSearchView;
    }

    @Override
    public void detach() {
        view = null;
    }
}
