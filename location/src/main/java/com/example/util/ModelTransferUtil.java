package com.example.util;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.android.common.CollectionsUtils;
import com.example.model.SearchItem;

import java.util.ArrayList;
import java.util.List;

/**
 * qt
 * 2019-10-21
 */
public class ModelTransferUtil {
    public static List<SearchItem> transSuggestionCityToSearchItem(List<SuggestionCity> suggestionCities) {
        List<SearchItem> searchItems = new ArrayList<>();
        if (!CollectionsUtils.isEmpty(suggestionCities)) {
            for (int i = 0; i < suggestionCities.size(); i++) {
                SuggestionCity suggestionCity = suggestionCities.get(i);
                if (suggestionCity != null) {
                    SearchItem searchItem = new SearchItem();
                    searchItem.setSuggestionCity(suggestionCity);

                    searchItems.add(searchItem);
                }
            }
        }
        return searchItems;
    }


    public static List<SearchItem> transPoiItemToSearchItem(List<PoiItem> poiItems) {
        List<SearchItem> searchItems = new ArrayList<>();
        if (!CollectionsUtils.isEmpty(poiItems)) {
            for (int i = 0; i < poiItems.size(); i++) {
                PoiItem poiItem = poiItems.get(i);
                if (poiItem != null) {
                    SearchItem searchItem = new SearchItem();
                    searchItem.setPoiItem(poiItem);

                    searchItems.add(searchItem);
                }
            }
        }
        return searchItems;
    }


}
