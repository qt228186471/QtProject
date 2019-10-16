package com.android.qtproject.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Weathers {
    /*
    	"city": "长春",
                "level": "一级（优）",
                "update": "2019-08-1211:00:00",
                "aqi": "19",
                "pm25": "6",
                "pm10": "18",
                "co": "0.42",
                "no2": "13",
                "031h": "43",
                "038h": "20",
                "so2": "3",

     */
    public String city;
    public String level;
    public String update;
    public String aqi;
    public String pm25;
    public String pm10;
    public String co;
    public String no2;
    @SerializedName("031h")
    public String H031;
    @SerializedName("038h")
    public String H038;
    public String so2;
    public List<Area> data;


}
