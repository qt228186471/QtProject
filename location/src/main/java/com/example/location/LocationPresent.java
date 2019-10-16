package com.example.location;

import com.amap.api.maps.model.MyLocationStyle;

public class LocationPresent implements LocationConstract.ILocationPresent {
    private LocationConstract.ILocationView iLocationView;

    @Override
    public void loadData() {

    }

    @Override
    public void attach(LocationConstract.ILocationView iLocationView) {
        this.iLocationView = iLocationView;
    }

    @Override
    public void detach() {
        iLocationView = null;
    }
}
