package com.example.location;

import com.example.base.IMvpBasePresent;
import com.example.base.IMvpBaseView;

public interface LocationConstract {
    interface ILocationPresent extends IMvpBasePresent<ILocationView>{
        void loadData();
    }

    interface ILocationView extends IMvpBaseView{
        void locatePosition();
        void removeMap();
    }
}
