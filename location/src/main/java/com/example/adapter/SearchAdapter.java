package com.example.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.example.location.R;
import com.example.model.SearchItem;
import java.util.ArrayList;
import java.util.List;

/**
 * qt
 * 2019-10-21
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private List<SearchItem> searchItems = new ArrayList<>();

    public SearchAdapter() {

    }

    public List<SearchItem> getSearchItems() {
        return searchItems;
    }

    public void setSearchItems(List<SearchItem> searchItems) {
        this.searchItems = searchItems;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.search_item, null);
        SearchViewHolder searchViewHolder = new SearchViewHolder(view);
        return searchViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        if (holder != null) {
            holder.bindData(searchItems.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (searchItems == null) {
            return 0;
        }
        return searchItems.size();
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder {
        private ImageView icon;
        private TextView itemTitle;
        private TextView itemContent;


        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            initView();
        }

        private void initView() {
            if (itemView != null) {
                icon = itemView.findViewById(R.id.search_item_icon);
                itemTitle = itemView.findViewById(R.id.search_item_title);
                itemContent = itemView.findViewById(R.id.search_item_content);
            }
        }

        public void bindData(SearchItem searchItem) {
            if (searchItem != null) {
                SuggestionCity suggestionCity = searchItem.getSuggestionCity();
                PoiItem poiItem = searchItem.getPoiItem();

                if (suggestionCity != null) {
                    itemContent.setText(suggestionCity.getCityName());
                    itemTitle.setText(suggestionCity.getAdCode());
                } else if (poiItem != null) {
                    itemTitle.setText(poiItem.getCityName());
                    itemContent.setText(poiItem.getTypeDes());
                } else {

                }
            }
        }
    }
}
