package com.demo.swt.mystudyappshop.Fragment;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.demo.swt.mystudyappshop.Listener.MyOrientationListener;
import com.demo.swt.mystudyappshop.R;

/**
 * 介绍：这里写介绍
 * 作者：sweet
 * 邮箱：sunwentao@imcoming.cn
 * 时间: 2017/2/20
 */

public class BaiDuMapFragment extends Fragment implements View.OnClickListener {
    public static BaiDuMapFragment newInstance() {

        Bundle args = new Bundle();

        BaiDuMapFragment fragment = new BaiDuMapFragment();
        fragment.setArguments(args);
        return fragment;
    }


    private MapView mMapView = null;
    private BaiduMap mBaiduMap;
    private Button type_button;
    private Dialog dialog;
    private PopupWindow mPopupWindow;
    private LocationManager mgr;
    private Button dingwei;
    /**
     * 定位的客户端
     */
    private LocationClient mLocationClient;
    /**
     * 定位的监听器
     */
    public MyLocationListener mMyLocationListener;
    /**
     * 当前定位的模式
     */
    private MyLocationConfiguration.LocationMode mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
    /***
     * 是否是第一次定位
     */
    private volatile boolean isFristLocation = true;

    /**
     * 最新一次的经纬度
     */
    private double mCurrentLantitude;
    private double mCurrentLongitude;
    /**
     * 当前的精度
     */
    private float mCurrentAccracy;
    /**
     * 方向传感器的监听器
     */
    private MyOrientationListener myOrientationListener;
    /**
     * 方向传感器X方向的值
     */
    private int mXDirection;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.shopcart, container, false);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
//        SDKInitializer.initialize(this);
        //获取地图控件引用

        mMapView = (MapView) view.findViewById(R.id.mapView);
        mBaiduMap = mMapView.getMap();
        type_button = (Button) view.findViewById(R.id.select_type);
        type_button.setOnClickListener(this);
        dingwei = (Button) view.findViewById(R.id.dingwei);
        dingwei.setOnClickListener(this);
        initOritationListener();
        initMyLocation();
        return view;
    }


    private void initOritationListener() {
        myOrientationListener = new MyOrientationListener(getActivity());
        myOrientationListener
                .setOnOrientationListener(new MyOrientationListener.OnOrientationListener() {
                    @Override
                    public void onOrientationChanged(float x) {
                        mXDirection = (int) x;

                        // 构造定位数据
                        MyLocationData locData = new MyLocationData.Builder()
                                .accuracy(mCurrentAccracy)
                                // 此处设置开发者获取到的方向信息，顺时针0-360
                                .direction(mXDirection)
                                .latitude(mCurrentLantitude)
                                .longitude(mCurrentLongitude).build();
                        // 设置定位数据
                        mBaiduMap.setMyLocationData(locData);
                        // 设置自定义图标
                        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
                                .fromResource(R.mipmap.navi_map_gps_locked);
                        MyLocationConfiguration config = new MyLocationConfiguration(mCurrentMode, true, mCurrentMarker);
                        mBaiduMap.setMyLocationConfigeration(config);

                    }
                });
    }

    /**
     * 初始化定位相关代码
     */
    private void initMyLocation() {
        // 定位初始化
        mLocationClient = new LocationClient(getActivity());
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
        // 设置定位的相关配置
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocationClient.setLocOption(option);
    }


    @Override
    public void onStart() {
        // 开启图层定位
        mBaiduMap.setMyLocationEnabled(true);
        if (!mLocationClient.isStarted()) {
            mLocationClient.start();
        }
        // 开启方向传感器
        myOrientationListener.start();
        super.onStart();
    }

    @Override
    public void onStop() {
        // 关闭图层定位
        mBaiduMap.setMyLocationEnabled(false);
        mLocationClient.stop();

        // 关闭方向传感器
        myOrientationListener.stop();
        super.onStop();
    }

    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {

            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null)
                return;
            // 构造定位数据
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mXDirection).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mCurrentAccracy = location.getRadius();
            // 设置定位数据
            mBaiduMap.setMyLocationData(locData);
            mCurrentLantitude = location.getLatitude();
            mCurrentLongitude = location.getLongitude();
            // 设置自定义图标
            BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
                    .fromResource(R.mipmap.navi_map_gps_locked);
            MyLocationConfiguration config = new MyLocationConfiguration(mCurrentMode, true, mCurrentMarker);
            mBaiduMap.setMyLocationConfigeration(config);
            // 第一次定位时，将地图位置移动到当前位置
            if (isFristLocation) {
                isFristLocation = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(u);
            }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.select_type:
                //这是对话框展示，都可以实现，稍微调整一下位置
           /*     View typeview = LayoutInflater.from(this).inflate(R.layout.type, null);
                dialog = new Dialog(this, R.style.CustomDialog);
                dialog.setContentView(typeview);
                Window dialogWindow = dialog.getWindow();
//                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                dialogWindow.setGravity(Gravity.BOTTOM | Gravity.LEFT);
                dialog.show();*/
                //这是popwindows方式展示
                View typeview = LayoutInflater.from(getActivity()).inflate(R.layout.type, null);
                typeview.findViewById(R.id.weixin).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //卫星地图
                        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                    }
                });
                typeview.findViewById(R.id.nomal).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //正常地图
                        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                    }
                });
                typeview.findViewById(R.id.kong).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //空白地图
                        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NONE);
                    }
                });
                typeview.findViewById(R.id.jiaotong).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //交通地图
                        mBaiduMap.setTrafficEnabled(true);
                    }
                });
                typeview.findViewById(R.id.hotjiaotong).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //热力交通地图
                        mBaiduMap.setBaiduHeatMapEnabled(true);
                    }
                });
                mPopupWindow = new PopupWindow(typeview, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
                mPopupWindow.setTouchable(true);
                mPopupWindow.setOutsideTouchable(true);
                mPopupWindow.getContentView().setFocusableInTouchMode(true);
                mPopupWindow.getContentView().setFocusable(true);
                mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));//这句加上之后，点击背景才能去掉popwindows
                mPopupWindow.showAtLocation(type_button, Gravity.BOTTOM | Gravity.LEFT, 0, 0);
                break;
            case R.id.dingwei:
                LatLng ll = new LatLng(mCurrentLantitude, mCurrentLongitude);
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(u);
                break;
        }
    }


}
