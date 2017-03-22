package cn.com.zzwfang.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.activity.CityListActivity;
import cn.com.zzwfang.activity.FeeHunterInfoActivity;
import cn.com.zzwfang.activity.FillBankCardInfoActivity;
import cn.com.zzwfang.activity.SearchHouseActivity;
import cn.com.zzwfang.activity.SecondHandHouseDetailActivity;
import cn.com.zzwfang.activity.ShangJinLieRenActivity;
import cn.com.zzwfang.adapter.HomeRecommendHouseAdapter;
import cn.com.zzwfang.bean.CityBean;
import cn.com.zzwfang.bean.RecommendHouseSourceBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.fragment.BaseFragment.OnFragmentViewClickListener;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.location.LocationService;
import cn.com.zzwfang.location.LocationService.OnLocationListener;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.util.Jumper;
import cn.com.zzwfang.view.ptz.PullToZoomListViewEx;

import com.alibaba.fastjson.JSON;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

/**
 * 首页Fragment
 * @author lzd
 *
 */
public class MainHomeFragment extends BaseFragment implements OnClickListener, OnItemClickListener,
    OnGetGeoCoderResultListener, OnFragmentViewClickListener {

	/**
	 * 跳转选择城市
	 */
	public static final int CODE_SELECT_CITY = 1000;
	
	public static final int CODE_LOGIN = 1001;
	
	private TextView tvLocation, edtSearchProperties;
	
	private FrameLayout zoomLlt;
	
	private PullToZoomListViewEx ptzListView;
	private LinearLayout headerLlt;
	private HomeRecommendHouseAdapter adapter;
	private ImageView imgShangjin;
	
	private String cityId;
//	private GeoCoder mSearch = null; // 搜索模块，也可去掉地图模块独立使用
	
	private ArrayList<RecommendHouseSourceBean> recommendSources = new ArrayList<RecommendHouseSourceBean>();
	private ArrayList<CityBean> cities = new ArrayList<CityBean>();
	
	private OnCitySelectedListener onCitySelectedListener;
	
	private OnFragmentViewClickListener onFragmentViewClickListener;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_main_home, null);
		initView(view);
		return view;
	}
	
	@Override
	public void onAttach(Activity activity) {
	    if (activity instanceof cn.com.zzwfang.fragment.MainHomeFragment.OnCitySelectedListener) {
	        onCitySelectedListener = (cn.com.zzwfang.fragment.MainHomeFragment.OnCitySelectedListener) activity;
	    }
	    if (activity instanceof OnFragmentViewClickListener) {
	    	onFragmentViewClickListener = (OnFragmentViewClickListener) activity;
	    }
	    super.onAttach(activity);
//	    mSearch = GeoCoder.newInstance();
//	    mSearch.setOnGetGeoCodeResultListener(MainHomeFragment.this);
	}

	
	private void initView(View view) {
		
		zoomLlt = (FrameLayout) View.inflate(getActivity(), R.layout.view_frag_home_zoom, null);
		headerLlt = (LinearLayout) View.inflate(getActivity(), R.layout.view_frag_home_header, null);
		tvLocation = (TextView) headerLlt.findViewById(R.id.tv_frag_home_location);
		edtSearchProperties = (TextView) headerLlt.findViewById(R.id.edt_search_properties);
		
		imgShangjin = (ImageView) view.findViewById(R.id.iv_frag_home_shangjinglieren);
		ptzListView = (PullToZoomListViewEx) view.findViewById(R.id.frag_home_ptz);
		ptzListView.setHeaderView(headerLlt);
		ptzListView.setZoomView(zoomLlt);
		adapter = new HomeRecommendHouseAdapter(getActivity(), recommendSources);
		ptzListView.setAdapter(adapter);
		ptzListView.setHideHeader(false);
		
//		CityBean cityBean = ContentUtils.getCityBean(getActivity());
//		if (cityBean != null) {
//			String cityName = cityBean.getName();
//			String cityId = cityBean.getSiteId();
//			
//			if (!TextUtils.isEmpty(cityName) && !TextUtils.isEmpty(cityId)) {
//				tvLocation.setText(cityBean.getName());
//				adapter.setCityId(cityId);
//				getRecommendHouseSourceList(cityId);
//			} else {
////			    getAreaList();
//			    locate();
//			}
//		} else {
////		    getAreaList();
//		    locate();
//		}
		
		
		ptzListView.setOnItemClickListener(this);
		
		tvLocation.setOnClickListener(this);
		edtSearchProperties.setOnClickListener(this);
		imgShangjin.setOnClickListener(this);
		
		locate();
		
	}
	
	@Override
	public void onResume() {
	    // TODO Auto-generated method stub
	    super.onResume();
	    CityBean cityBean = ContentUtils.getCityBean(getActivity());
	    if (cityBean != null) {
	        if (cityBean.isOpenMoney()) {
	            imgShangjin.setVisibility(View.VISIBLE);
	        } else {
	            imgShangjin.setVisibility(View.INVISIBLE);
	        }
	    } else {
	        imgShangjin.setVisibility(View.INVISIBLE);
	    }
	}
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_frag_home_location:   // 定位选择城市
			Jumper.newJumper()
            .setAheadInAnimation(R.anim.activity_push_in_right)
            .setAheadOutAnimation(R.anim.activity_alpha_out)
            .setBackInAnimation(R.anim.activity_alpha_in)
            .setBackOutAnimation(R.anim.activity_push_out_right)
            .jumpForResult(this, CityListActivity.class, CODE_SELECT_CITY);
			break;
		case R.id.edt_search_properties:    //  搜索
			Jumper.newJumper()
            .setAheadInAnimation(R.anim.activity_push_in_right)
            .setAheadOutAnimation(R.anim.activity_alpha_out)
            .setBackInAnimation(R.anim.activity_alpha_in)
            .setBackOutAnimation(R.anim.activity_push_out_right)
            .putString(HomeRecommendHouseAdapter.INTENT_CITY_ID, cityId)
            .jump(this, SearchHouseActivity.class);
			break;
		case R.id.iv_frag_home_shangjinglieren:  // 赏金猎人
			
			/**
			 * 用户类型    0经济人，1普通会员，2赏金猎人
			 */
			boolean loginStatus = ContentUtils.getUserLoginStatus(getActivity());
			if (loginStatus) {  // 已登录
				int userType = ContentUtils.getUserType(getActivity());
				boolean isBindBankCard = ContentUtils.isUserBindBankCard(getActivity());
				if (userType == 2) {  // 赏金猎人
				    if (isBindBankCard) {
				        Jumper.newJumper()
	                    .setAheadInAnimation(R.anim.activity_push_in_right)
	                    .setAheadOutAnimation(R.anim.activity_alpha_out)
	                    .setBackInAnimation(R.anim.activity_alpha_in)
	                    .setBackOutAnimation(R.anim.activity_push_out_right)
	                    .jump(this, FeeHunterInfoActivity.class);
				    } else {
				        Jumper.newJumper()
	                    .setAheadInAnimation(R.anim.activity_push_in_right)
	                    .setAheadOutAnimation(R.anim.activity_alpha_out)
	                    .setBackInAnimation(R.anim.activity_alpha_in)
	                    .setBackOutAnimation(R.anim.activity_push_out_right)
	                    .jump(this, FillBankCardInfoActivity.class);
				    }
					
				} else if (userType == 0) {  // 0经济人
//					Jumper.newJumper()
//		            .setAheadInAnimation(R.anim.activity_push_in_right)
//		            .setAheadOutAnimation(R.anim.activity_alpha_out)
//		            .setBackInAnimation(R.anim.activity_alpha_in)
//		            .setBackOutAnimation(R.anim.activity_push_out_right)
//		            .jump(this, ShangJinLieRenActivity.class);
				    
				    if (isBindBankCard) {
                        Jumper.newJumper()
                        .setAheadInAnimation(R.anim.activity_push_in_right)
                        .setAheadOutAnimation(R.anim.activity_alpha_out)
                        .setBackInAnimation(R.anim.activity_alpha_in)
                        .setBackOutAnimation(R.anim.activity_push_out_right)
                        .jump(this, FeeHunterInfoActivity.class);
                    } else {
                        Jumper.newJumper()
                        .setAheadInAnimation(R.anim.activity_push_in_right)
                        .setAheadOutAnimation(R.anim.activity_alpha_out)
                        .setBackInAnimation(R.anim.activity_alpha_in)
                        .setBackOutAnimation(R.anim.activity_push_out_right)
                        .jump(this, FillBankCardInfoActivity.class);
                    }
				    
				} else if (userType == 1) {
				    if (isBindBankCard) {
                        Jumper.newJumper()
                        .setAheadInAnimation(R.anim.activity_push_in_right)
                        .setAheadOutAnimation(R.anim.activity_alpha_out)
                        .setBackInAnimation(R.anim.activity_alpha_in)
                        .setBackOutAnimation(R.anim.activity_push_out_right)
                        .jump(this, FeeHunterInfoActivity.class);
                    } else {
                        Jumper.newJumper()
                        .setAheadInAnimation(R.anim.activity_push_in_right)
                        .setAheadOutAnimation(R.anim.activity_alpha_out)
                        .setBackInAnimation(R.anim.activity_alpha_in)
                        .setBackOutAnimation(R.anim.activity_push_out_right)
                        .jump(this, FillBankCardInfoActivity.class);
                    }
				} else {  //  非赏金猎人  1普通会员
                    Jumper.newJumper()
                    .setAheadInAnimation(R.anim.activity_push_in_right)
                    .setAheadOutAnimation(R.anim.activity_alpha_out)
                    .setBackInAnimation(R.anim.activity_alpha_in)
                    .setBackOutAnimation(R.anim.activity_push_out_right)
                    .jump(this, ShangJinLieRenActivity.class);
                }
			} else {  // 未登录
//				Jumper.newJumper()
//	            .setAheadInAnimation(R.anim.slide_in_style1)
//                .setAheadOutAnimation(R.anim.alpha_out_style1)
//                .setBackInAnimation(R.anim.alpha_in_style1)
//                .setBackOutAnimation(R.anim.slide_out_style1)
//                .jumpForResult(this, LoginActivity.class, CODE_LOGIN);
				
				Jumper.newJumper()
	            .setAheadInAnimation(R.anim.slide_in_style1)
                .setAheadOutAnimation(R.anim.alpha_out_style1)
                .setBackInAnimation(R.anim.alpha_in_style1)
                .setBackOutAnimation(R.anim.slide_out_style1)
                .jump(this, ShangJinLieRenActivity.class);
//                .jumpForResult(this, LoginActivity.class, CODE_LOGIN);
			}
			break;
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		RecommendHouseSourceBean recommendHouse = recommendSources.get(position - 2);
		Jumper.newJumper()
        .setAheadInAnimation(R.anim.activity_push_in_right)
        .setAheadOutAnimation(R.anim.activity_alpha_out)
        .setBackInAnimation(R.anim.activity_alpha_in)
        .setBackOutAnimation(R.anim.activity_push_out_right)
        .putString(SecondHandHouseDetailActivity.INTENT_HOUSE_SOURCE_ID, recommendHouse.getId())
        .jump(this, SecondHandHouseDetailActivity.class);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case CODE_SELECT_CITY:
				CityBean cityBean = (CityBean) data.getSerializableExtra(CityListActivity.INTENT_CITY);
				
				CityBean cityBeanSaved = ContentUtils.getCityBean(getActivity());
				if (cityBean != null && cityBean.equals(cityBeanSaved)) {
				    return;
				}
				if (cityBean != null) {
					cityId = cityBean.getSiteId();
					ContentUtils.saveCityBeanData(getActivity(), cityBean);
					String cityName = cityBean.getName();
					tvLocation.setText(cityName);
					adapter.setCityId(cityBean.getSiteId());
					adapter.refreshCityName(cityName);
					if (onCitySelectedListener != null) {
					    onCitySelectedListener.onCitySelected(cityBean);
					}
					getRecommendHouseSourceList(cityBean.getSiteId());
				}
				
			    // 初始化搜索模块，注册事件监听
//				if (cityBean != null) {
//				    String cityName = cityBean.getName();
//				    if (!TextUtils.isEmpty(cityName)) {
//				        
//		                mSearch.geocode(new GeoCodeOption().city(cityName).address(cityName));
//				    }
//				}
				break;
			case CODE_LOGIN:  // 登录成功
				int userType = ContentUtils.getUserType(getActivity());
				boolean isBindBankCard = ContentUtils.isUserBindBankCard(getActivity());
				if (userType == 2) {  // 赏金猎人
				    
				    if (isBindBankCard) {
                        Jumper.newJumper()
                        .setAheadInAnimation(R.anim.activity_push_in_right)
                        .setAheadOutAnimation(R.anim.activity_alpha_out)
                        .setBackInAnimation(R.anim.activity_alpha_in)
                        .setBackOutAnimation(R.anim.activity_push_out_right)
                        .jump(this, FeeHunterInfoActivity.class);
                    } else {
                        Jumper.newJumper()
                        .setAheadInAnimation(R.anim.activity_push_in_right)
                        .setAheadOutAnimation(R.anim.activity_alpha_out)
                        .setBackInAnimation(R.anim.activity_alpha_in)
                        .setBackOutAnimation(R.anim.activity_push_out_right)
                        .jump(this, FillBankCardInfoActivity.class);
                    }
				} else if (userType == 0) {  // 0经济人
				    if (isBindBankCard) {
                        Jumper.newJumper()
                        .setAheadInAnimation(R.anim.activity_push_in_right)
                        .setAheadOutAnimation(R.anim.activity_alpha_out)
                        .setBackInAnimation(R.anim.activity_alpha_in)
                        .setBackOutAnimation(R.anim.activity_push_out_right)
                        .jump(this, FeeHunterInfoActivity.class);
                    } else {
                        Jumper.newJumper()
                        .setAheadInAnimation(R.anim.activity_push_in_right)
                        .setAheadOutAnimation(R.anim.activity_alpha_out)
                        .setBackInAnimation(R.anim.activity_alpha_in)
                        .setBackOutAnimation(R.anim.activity_push_out_right)
                        .jump(this, FillBankCardInfoActivity.class);
                    }
				} else {
//					Jumper.newJumper()
//		            .setAheadInAnimation(R.anim.activity_push_in_right)
//		            .setAheadOutAnimation(R.anim.activity_alpha_out)
//		            .setBackInAnimation(R.anim.activity_alpha_in)
//		            .setBackOutAnimation(R.anim.activity_push_out_right)
//		            .jump(this, ShangJinLieRenActivity.class);
					
					Jumper.newJumper()
                    .setAheadInAnimation(R.anim.activity_push_in_right)
                    .setAheadOutAnimation(R.anim.activity_alpha_out)
                    .setBackInAnimation(R.anim.activity_alpha_in)
                    .setBackOutAnimation(R.anim.activity_push_out_right)
                    .jump(this, FillBankCardInfoActivity.class);
				}
				break;
			}
		}
	}
	
	/**
	 * 获取推荐列表
	 * @param cityId
	 */
	private void getRecommendHouseSourceList(String cityId) {
		ActionImpl actionImpl = ActionImpl.newInstance(getActivity());
		actionImpl.getRecommendHouseSource(cityId, new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
				
			}
			 
			@Override
			public void rc3001(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				ArrayList<RecommendHouseSourceBean> temp = (ArrayList<RecommendHouseSourceBean>) JSON.parseArray(result.getData(), RecommendHouseSourceBean.class);
				recommendSources.clear();
				if (temp != null) {
					recommendSources.addAll(temp);
				}
				adapter.notifyDataSetChanged();
			}
		});
	}


	public interface OnCitySelectedListener {
        void onCitySelected(CityBean cityBean);
    }


    @Override
    public void onGetGeoCodeResult(GeoCodeResult result) {
        // TODO Auto-generated method stub
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
//            Toast.makeText(getActivity(), "抱歉，未能找到结果", Toast.LENGTH_LONG)
//                    .show();
            return;
        }
//        double lat = result.getLocation().latitude;
//        double lng = result.getLocation().longitude;
//        ContentUtils.saveSelectedCityLatLng(getActivity(), lat, lng);
//        getCurrentCityByGps(lat, lng);
    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult arg0) {
        // TODO Auto-generated method stub
//        AddressComponent componet = arg0.getAddressDetail();
//        String city = componet.city;
//        ContentUtils.saveLocatedCity(getActivity(), city);
    }
    
    private void getAreaList(final String city) {
        
        ActionImpl actionImpl = ActionImpl.newInstance(getActivity());
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
                    cities.clear();
                    cities.addAll(temp);
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
                        
                        cityId = cityBean.getSiteId();
                        ContentUtils.saveCityBeanData(getActivity(), cityBean);
                        tvLocation.setText(cityBean.getName());
                        adapter.setCityId(cityBean.getSiteId());
                        
                        
                        if (onCitySelectedListener != null) {
                            onCitySelectedListener.onCitySelected(cityBean);
                        }
                        getRecommendHouseSourceList(cityBean.getSiteId());
                        // 初始化搜索模块，注册事件监听
//                        if (cityBean != null) {
//                            String cityName = cityBean.getName();
//                            if (!TextUtils.isEmpty(cityName)) {
//                                mSearch.geocode(new GeoCodeOption().city(cityName).address(cityName));
//                            }
//                        }
                    }
                }
            }
        });
    }
    
    private void getCurrentCityByGps(double lat, double lng) {
        ActionImpl actionImpl = ActionImpl.newInstance(getActivity());
        if (lat > 0 || lng > 0) {
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
                .getInstance(getActivity());
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

	@Override
	public void onFragmentViewClick(View view) {
		// TODO Auto-generated method stub
		if (onFragmentViewClickListener != null) {
			onFragmentViewClickListener.onFragmentViewClick(view);
		}
	}

	
}
