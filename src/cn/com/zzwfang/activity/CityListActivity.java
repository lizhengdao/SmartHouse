package cn.com.zzwfang.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.adapter.CityListAdapter;
import cn.com.zzwfang.bean.CityBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.location.LocationService;
import cn.com.zzwfang.location.LocationService.OnLocationListener;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.util.ToastUtils;

import com.alibaba.fastjson.JSON;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeOption;

public class CityListActivity extends BaseActivity implements OnClickListener, OnItemClickListener {

	public static String INTENT_CITY = "CityListActivity_city";
	private TextView tvBack;
	private ListView listCity;
	private LinearLayout lltHeader;
	private TextView tvLocate, tvCurrentLocation;
	private ArrayList<CityBean> cities = new ArrayList<CityBean>();
	private CityListAdapter adapter;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initView();
//		getCurrentCityByGps();
//		getAreaList();
		locate();
	}
	
	private void initView() {
		setContentView(R.layout.act_city_list);
		tvBack = (TextView) findViewById(R.id.act_city_list_back);
		listCity = (ListView) findViewById(R.id.act_city_list);
		
		lltHeader = (LinearLayout) View.inflate(this, R.layout.view_city_list_header, null);
		tvLocate = (TextView) lltHeader.findViewById(R.id.view_city_list_header_locate_tv);
		tvCurrentLocation = (TextView) lltHeader.findViewById(R.id.view_city_list_header_locate_city_tv);
		listCity.addHeaderView(lltHeader);
		adapter = new CityListAdapter(this, cities);
		listCity.setAdapter(adapter);
		listCity.setOnItemClickListener(this);
		tvBack.setOnClickListener(this);
		tvLocate.setOnClickListener(this);
		tvCurrentLocation.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_city_list_back:
			finish();
			break;
		case R.id.view_city_list_header_locate_tv:  // 定位获得当前城市
//			ToastUtils.SHORT.toast(this, "定位获得当前城市");
			break;
		case R.id.view_city_list_header_locate_city_tv:  // 当前城市
//			ToastUtils.SHORT.toast(this, "当前城市");
			break;
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		CityBean cityBean = cities.get(position - 1);
		Intent intent = new Intent();
		intent.putExtra(INTENT_CITY, cityBean);
		setResult(RESULT_OK, intent);
		finish();
	}
	
	private void getAreaList(final String city) {
		
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.getCityList(new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				ArrayList<CityBean> temp = (ArrayList<CityBean>) JSON.parseArray(result.getData(), CityBean.class);
				if (temp != null) {
					cities.addAll(temp);
					adapter.notifyDataSetChanged();
					
					 if (cities != null && cities.size() > 0) {
	                        CityBean cityBean = null;
	                        if (!TextUtils.isEmpty(city)) {
	                            for (CityBean cityBeanTemp : cities) {
	                                if (cityBeanTemp.getName().contains(city) || city.contains(cityBeanTemp.getName())) {
	                                    cityBean = cityBeanTemp;
	                                    break;
	                                }
	                            }
	                        }
	                        
	                        if (cityBean == null) {
	                            cityBean = cities.get(0);
	                        }
	                        
	                        String cityName = cityBean.getName();
	                        tvCurrentLocation.setText(cityName);
	                        
//	                        cityId = cityBean.getSiteId();
//	                        ContentUtils.saveCityBeanData(getActivity(), cityBean);
//	                        tvLocation.setText(cityBean.getName());
//	                        adapter.setCityId(cityBean.getSiteId());
//	                        
//	                        
//	                        if (onCitySelectedListener != null) {
//	                            onCitySelectedListener.onCitySelected(cityBean);
//	                        }
//	                        getRecommendHouseSourceList(cityBean.getSiteId());
//	                        // 初始化搜索模块，注册事件监听
//	                        if (cityBean != null) {
//	                            String cityName = cityBean.getName();
//	                            if (!TextUtils.isEmpty(cityName)) {
//	                                mSearch.geocode(new GeoCodeOption().city(cityName).address(cityName));
//	                            }
//	                        }
	                    }
				}
			}
		});
	}
	
	private void getCurrentCityByGps(double lat, double lng) {
	    ActionImpl actionImpl = ActionImpl.newInstance(this);
//	    LatLng latLng = ContentUtils.getSelectedCityLatLng(this);
	    if (lng > 0 || lng > 0) {
	        actionImpl.getCityByGps(lat, lng, new ResultHandlerCallback() {
                
                @Override
                public void rc999(RequestEntity entity, Result result) {
                    // TODO Auto-generated method stub
                    
                }
                
                @Override
                public void rc3001(RequestEntity entity, Result result) {
                    // TODO Auto-generated method stub
                    
                }
                
                @Override
                public void rc0(RequestEntity entity, Result result) {
                    // TODO Auto-generated method stub
                    String city = result.getData();
                    getAreaList(city);
                }
            });
	    }
	    
	}
	
	private void locate() {
        final LocationService locationService = LocationService
                .getInstance(this);
        locationService.startLocationService(new OnLocationListener() {

            @Override
            public void onLocationCompletion(BDLocation location) {
//                LatLng curLatLng = new LatLng(location.getLatitude(), location
//                        .getLongitude());
//                Log.i("--->", "定位    lat == " + location.getLatitude() + "   lng == " + location.getLongitude());
//                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(curLatLng);
                locationService.stopLocationService();
                
                getCurrentCityByGps(location.getLatitude(), location.getLongitude());

            }
        });
    }



	
}
