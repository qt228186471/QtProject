package com.example.search;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.example.adapter.SearchAdapter;
import com.example.base.BaseActivity;
import com.example.base.IMvpBasePresent;
import com.example.base.IMvpBaseView;
import com.example.location.R;
import com.example.util.ModelTransferUtil;

import java.util.ArrayList;
import java.util.List;

public class LocationSearchActivity extends BaseActivity implements ISearchConstract.ISearchView, PoiSearch.OnPoiSearchListener {
    private SearchView searchView;
    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;
    private LinearLayoutManager layoutManager;
    private View mSearchPlate;
    private View mSubmitArea;
    private ISearchConstract.ISearchPresent searchPresent;
    private final int SEARCH_SUCCESS = 1000;

    @Override
    protected IMvpBaseView getIBaseView() {
        return this;
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initView() {
        searchView = findViewById(R.id.sv);
        mSearchPlate = findViewById(R.id.search_plate);
        mSubmitArea = findViewById(R.id.submit_area);

        searchView.setIconified(false);
        mSearchPlate.setBackground(null);
        mSubmitArea.setBackground(null);
//        searchView.setIconifiedByDefault(false);
//        searchView.onActionViewExpanded();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!TextUtils.isEmpty(query)) {
                    searchPresent.search(query, LocationSearchActivity.this);
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)) {
                    searchPresent.search(newText, LocationSearchActivity.this);
                }

                return false;
            }
        });

        recyclerView = findViewById(R.id.rl);

        searchAdapter = new SearchAdapter();
        recyclerView.setAdapter(searchAdapter);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public IMvpBasePresent createPresent() {
        searchPresent = new SearchPresent();
        return searchPresent;
    }

    @Override
    public int getRes() {
        return R.layout.activity_location_search;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        //返回结果成功或者失败的响应码。1000为成功，其他为失败（详细信息参见网站开发指南-实用工具-错误码对照表）
        if (i == SEARCH_SUCCESS) {
            if (poiResult == null) {
                return;
            } else if (poiResult.getPois() == null || poiResult.getPois().size() == 0) {
                List<SuggestionCity> suggestionCities = poiResult.getSearchSuggestionCitys();
                searchAdapter.setSearchItems(ModelTransferUtil.transSuggestionCityToSearchItem(suggestionCities));
                searchAdapter.notifyDataSetChanged();

                return;
            } else {
                ArrayList<PoiItem> poiItems = poiResult.getPois();
                searchAdapter.setSearchItems(ModelTransferUtil.transPoiItemToSearchItem(poiItems));
                searchAdapter.notifyDataSetChanged();

                return;
            }
        } else {
            return;
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

}
