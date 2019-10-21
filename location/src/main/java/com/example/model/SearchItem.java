package com.example.model;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;

/**
 * qt
 * 2019-10-21
 */
public class SearchItem {
    private SuggestionCity suggestionCity;
    private PoiItem poiItem;

    public SuggestionCity getSuggestionCity() {
        return suggestionCity;
    }

    public void setSuggestionCity(SuggestionCity suggestionCity) {
        this.suggestionCity = suggestionCity;
    }

    public PoiItem getPoiItem() {
        return poiItem;
    }

    public void setPoiItem(PoiItem poiItem) {
        this.poiItem = poiItem;
    }
}
