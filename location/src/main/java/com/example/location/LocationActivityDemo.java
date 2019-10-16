package com.example.location;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.MyLocationStyle;
import com.android.common.CollectionsUtils;
import com.example.base.BaseActivity;
import com.example.base.IMvpBasePresent;
import com.example.base.IMvpBaseView;
import java.util.ArrayList;
import java.util.List;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class LocationActivityDemo extends BaseActivity implements EasyPermissions.PermissionCallbacks, LocationConstract.ILocationView {
    private static final String TAG = "LocationActivityDemo";
    private LocationConstract.ILocationPresent present;
    private MapView mapView;
    private AMap aMap;
    private String[] permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION};
    private Bundle bundle;
    private static final int ALL_PERMISSION = 10000;
    private static final int REQUEST_SETTING_PERMISSION = 10001;


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


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (EasyPermissions.hasPermissions(this, permissions)) {
                init();
            } else {
                EasyPermissions.requestPermissions(this, "请申请必要权限！", ALL_PERMISSION, permissions);
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
        if (requestCode == ALL_PERMISSION && this.permissions.length == perms.size()) {
            init();
        }
    }

    private boolean hasAllPermission(List<String> permission) {
        if (permission == null || permission.size() == 0) {
            return false;
        }

        for (int i = 0; i < permissions.length; i++) {
            if (!permission.contains(permissions[i])) {
                return false;
            }
        }

        return true;
    }

    /**
     * 将原权限列表和目标权限列表比较，找出不包含在目标权限列表中的权限
     *
     * @param origin
     * @param target
     * @return
     */
    private String[] getNeedRequest(List<String> origin, List<String> target) {
        if (CollectionsUtils.isEmpty(target)) {
            return null;
        }

        String[] result;
        if (CollectionsUtils.isEmpty(origin)) {
            result = new String[target.size()];
            for (int i = 0; i < origin.size(); i++) {
                result[i] = origin.get(i);
            }
            return result;
        }

        List<String> needRequest = new ArrayList<>();
        for (int index = 0; index < target.size(); index++) {
            if (!origin.contains(target.get(index))) {
                needRequest.add(target.get(index));
            }
        }

        result = new String[needRequest.size()];
        for (int i = 0; i < needRequest.size(); i++) {
            result[i] = needRequest.get(i);
        }
        return result;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (perms.size() == 0) {
            init();
        } else {
            new AppSettingsDialog.Builder(this)
                    .setTitle("权限已被拒绝")
                    .setRationale("如果不打开权限则无法使用该功能,点击确定去设置中打开权限")
                    .setRequestCode(REQUEST_SETTING_PERMISSION)
                    .build()
                    .show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_SETTING_PERMISSION:
                if (EasyPermissions.hasPermissions(this, permissions)) {
                    init();
                }
                break;
        }
    }

    /**
     * 定位位置
     */
    @Override
    public void locatePosition() {
        if (aMap != null) {
            MyLocationStyle myLocationStyle = getLocateStyle();
            aMap.setMyLocationStyle(myLocationStyle);
            //设置默认定位按钮是否显示，非必需设置。
            aMap.getUiSettings().setMyLocationButtonEnabled(true);
            // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
            aMap.setMyLocationEnabled(true);
            //缩放地图到指定的缩放级别
            aMap.moveCamera(CameraUpdateFactory.zoomTo(13));
            //定位后经纬度变化信息
            aMap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
                @Override
                public void onMyLocationChange(Location location) {
                    Log.d(TAG, "经度：" + location.getAltitude() + ",纬度：" + location.getLatitude());
                }
            });
        }
    }


    private MyLocationStyle getLocateStyle() {
        MyLocationStyle myLocationStyle;
        //初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle = new MyLocationStyle();
        //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.interval(2000);
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE);
        myLocationStyle.showMyLocation(true);
        return myLocationStyle;
    }


    @Override
    public void removeMap() {

    }

    @Override
    public Context getContext() {
        return null;
    }
}
