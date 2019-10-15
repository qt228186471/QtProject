package com.example.location;

import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.MyLocationStyle;
import com.example.base.BaseActivity;
import com.example.base.IMvpBasePresent;
import com.example.base.IMvpBaseView;
import java.util.Arrays;
import java.util.List;
import pub.devrel.easypermissions.EasyPermissions;

public class LocationActivityDemo extends BaseActivity implements EasyPermissions.PermissionCallbacks, LocationConstract.ILocationView {
    private LocationConstract.ILocationPresent present;
    private MapView mapView;
    private AMap aMap;
    private String[] permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION};
    private Bundle bundle;


    @Override
    protected IMvpBaseView getIBaseView() {
        return new LocationView();
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        bundle = savedInstanceState;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        mapView = findViewById(R.id.map);


        if (Build.VERSION.SDK_INT >= 23) {
            if (EasyPermissions.hasPermissions(this, permissions)) {
                init();
            } else {
                EasyPermissions.requestPermissions(this, "请申请必要权限！", 0, permissions);
            }
        } else {
            init();
        }

    }

    private void init() {
        if (mapView != null) {
            mapView.onCreate(bundle);
            aMap = mapView.getMap();
            locatePosition();
        }
    }

    @Override
    public IMvpBasePresent createPresent() {
        present = new LocationPresent();
        return present;
    }

    @Override
    public int getRes() {
        return R.layout.location_layout;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        if (aMap != null) {
            mapView.onDestroy();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (aMap != null) {
            //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
            mapView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (aMap != null) {
            //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
            mapView.onPause();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        bundle = outState;
        mapView.onSaveInstanceState(bundle);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        List<String> list = Arrays.asList(permissions);
        if (perms.containsAll(list)) {
            init();
        } else {
            Toast.makeText(getApplicationContext(), "缺少必要权限!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        finish();
    }

    @Override
    public void locatePosition() {
        if (aMap != null) {
            MyLocationStyle myLocationStyle;
            myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
            myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE);
            aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
            aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
            aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        }
    }

    @Override
    public void removeMap() {

    }

    @Override
    public Context getContext() {
        return null;
    }
}
