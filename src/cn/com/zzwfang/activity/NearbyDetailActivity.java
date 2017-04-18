package cn.com.zzwfang.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.CityBean;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.view.AutoDrawableTextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.BusLineOverlay;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.busline.BusLineResult;
import com.baidu.mapapi.search.busline.BusLineResult.BusStation;
import com.baidu.mapapi.search.busline.BusLineSearch;
import com.baidu.mapapi.search.busline.BusLineSearchOption;
import com.baidu.mapapi.search.busline.OnGetBusLineSearchResultListener;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;

/**
 * 周边详情
 * 
 * @author MISS-万
 * 
 */
public class NearbyDetailActivity extends BaseActivity implements
		OnClickListener, OnGetPoiSearchResultListener,
		OnGetSuggestionResultListener, OnGetBusLineSearchResultListener {
	
	public static final String INTENT_LAT = "intent_lat";
	
	public static final String INTENT_LNG = "intent_lng";

	private TextView tvBack;

	private MapView mapView;

	private AutoDrawableTextView tvBank, tvBus, tvSubway, tvSchool, tvHostipal,
			tvLeisure, tvShopping, tvBodyBuilding, tvFoods;

	private PoiSearch mPoiSearch = null;
	private SuggestionSearch mSuggestionSearch = null;
	private BaiduMap mBaiduMap = null;
	
	private double lat;
	
	private double lng;

	private LatLng curLatLng;
	
	private BusLineSearch busLineSearch;
	
	private List<BusStation>stations;//用于存放某条公交线路中的所有站点信息  
	private List<String> busLineUidList;//公交路线的uid集合
	private int buslineIndex = 0;// 标记第几个路线
	
	private boolean isSearchSubway = false;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		Intent intent = getIntent();
		lat = intent.getDoubleExtra(INTENT_LAT, 0);
		lng = intent.getDoubleExtra(INTENT_LNG, 0);
		initView();
	}

	private void initView() {
		setContentView(R.layout.act_nearby_detail);
		tvBack = (TextView) findViewById(R.id.act_nearby_detail_back);
		mapView = (MapView) findViewById(R.id.act_nearby_detail_map_view);
		tvBank = (AutoDrawableTextView) findViewById(R.id.act_nearby_detail_bank);
		tvBus = (AutoDrawableTextView) findViewById(R.id.act_nearby_detail_bus);
		tvSubway = (AutoDrawableTextView) findViewById(R.id.act_nearby_detail_subway);
		tvSchool = (AutoDrawableTextView) findViewById(R.id.act_nearby_detail_school);
		tvHostipal = (AutoDrawableTextView) findViewById(R.id.act_nearby_detail_hostipal);
		tvLeisure = (AutoDrawableTextView) findViewById(R.id.act_nearby_detail_leisure);
		tvShopping = (AutoDrawableTextView) findViewById(R.id.act_nearby_detail_shopping);
		tvBodyBuilding = (AutoDrawableTextView) findViewById(R.id.act_nearby_detail_body_building);
		tvFoods = (AutoDrawableTextView) findViewById(R.id.act_nearby_detail_foods);

		tvBack.setOnClickListener(this);
		tvBank.setOnClickListener(this);
		tvBus.setOnClickListener(this);
		tvSubway.setOnClickListener(this);
		tvSchool.setOnClickListener(this);
		tvHostipal.setOnClickListener(this);
		tvLeisure.setOnClickListener(this);
		tvShopping.setOnClickListener(this);
		tvBodyBuilding.setOnClickListener(this);
		tvFoods.setOnClickListener(this);

		mapView.showZoomControls(false);
		
		busLineUidList = new ArrayList<String>();
		
		// 初始化搜索模块，注册搜索事件监听
		mPoiSearch = PoiSearch.newInstance();
		mPoiSearch.setOnGetPoiSearchResultListener(this);
		mSuggestionSearch = SuggestionSearch.newInstance();
		mSuggestionSearch.setOnGetSuggestionResultListener(this);
		mBaiduMap = mapView.getMap();
		
		
		
		busLineSearch = BusLineSearch.newInstance();
		busLineSearch.setOnGetBusLineSearchResultListener(this);
		
		curLatLng = new LatLng(lat, lng);
		MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(curLatLng);
		mBaiduMap.animateMapStatus(u);

		// 构建Marker图标
		BitmapDescriptor bitmap = BitmapDescriptorFactory
				.fromResource(R.drawable.ic_cur_location);
		// 构建MarkerOption，用于在地图上添加Marker
		OverlayOptions option = new MarkerOptions().position(curLatLng)
				.icon(bitmap);
		// 在地图上添加Marker，并显示
		mBaiduMap.addOverlay(option);

//		final LocationService locationService = LocationService
//				.getInstance(this);
//		locationService.startLocationService(new OnLocationListener() {
//
//			@Override
//			public void onLocationCompletion(BDLocation location) {
//				curLatLng = new LatLng(location.getLatitude(), location
//						.getLongitude());
//				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(curLatLng);
//				mBaiduMap.animateMapStatus(u);
//				locationService.stopLocationService();
//
//				// OverlayOptions curOption = new
//				// mBaiduMap.addOverlay(option)
//
//				// 构建Marker图标
//				BitmapDescriptor bitmap = BitmapDescriptorFactory
//						.fromResource(R.drawable.ic_cur_location);
//				// 构建MarkerOption，用于在地图上添加Marker
//				OverlayOptions option = new MarkerOptions().position(curLatLng)
//						.icon(bitmap);
//				// 在地图上添加Marker，并显示
//				mBaiduMap.addOverlay(option);
//			}
//		});

	}

	@Override
	public void onClick(View v) {
		isSearchSubway = false;
		switch (v.getId()) {
		case R.id.act_nearby_detail_back:
			finish();
			break;
		case R.id.act_nearby_detail_bank: // 银行
			searchNearby("银行");
			break;
		case R.id.act_nearby_detail_bus: // 公交
			searchNearby("公交");
			break;
		case R.id.act_nearby_detail_subway: // 地铁
			isSearchSubway = true;
			searchNearby("地铁");
			break;
		case R.id.act_nearby_detail_school: // 教育
			searchNearby("教育");
			break;
		case R.id.act_nearby_detail_hostipal: // 医院
			searchNearby("医院");
			break;
		case R.id.act_nearby_detail_leisure: // 休闲
			searchNearby("休闲");
			break;
		case R.id.act_nearby_detail_shopping: // 购物
			searchNearby("购物");
			break;
		case R.id.act_nearby_detail_body_building: // 健身
			searchNearby("健身");
			break;
		case R.id.act_nearby_detail_foods: // 美食
			searchNearby("美食");
			break;

		}
	}

	private void searchNearby(String keyWords) {
		
//		com.baidu.mapapi.search.busline.BusLineSearchOption
		if (curLatLng != null) {
			if ("地铁".equals(keyWords)) {
//				String city = ContentUtils.getLocatedCity(this);
//				mPoiSearch.searchNearby(new PoiNearbySearchOption()
//				.location(curLatLng).keyword(keyWords).pageNum(10)
//				.radius(10000).);
				keyWords = "地铁";
				CityBean cityBean = ContentUtils.getCityBean(this);
				if (cityBean != null) {
					String city = cityBean.getName();
					if (!TextUtils.isEmpty(city)) {
						Toast.makeText(NearbyDetailActivity.this, "搜索 " + city + keyWords, Toast.LENGTH_LONG).show();
//						mPoiSearch.searchInCity(new PoiCitySearchOption().city(city).keyword(keyWords).pageNum(10).pageCapacity(10));
						
//						mPoiSearch.searchInCity(new PoiCitySearchOption().city(city).keyword(keyWords));
						
						mPoiSearch.searchNearby(new PoiNearbySearchOption()
						.location(curLatLng).keyword(keyWords).radius(20000));
						
//						poiSearch.searchInCity(new PoiCitySearchOption().city(cityName).keyword(busLine));
					} else {
						mPoiSearch.searchNearby(new PoiNearbySearchOption()
						.location(curLatLng).keyword(keyWords).pageNum(10)
						.radius(50000));
					}
					
				}
				
			} else {
				mPoiSearch.searchNearby(new PoiNearbySearchOption()
				.location(curLatLng).keyword(keyWords).pageNum(10)
				.radius(10000));
			}
			
		}
	}

	@Override
	public void onGetPoiDetailResult(PoiDetailResult result) {
		// TODO Auto-generated method stub

		if (result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, result.getName() + ": " + result.getAddress(),
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onGetPoiResult(PoiResult result) {
		// TODO Auto-generated method stub

		if (result == null
				|| result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
			Toast.makeText(this, "未找到结果", Toast.LENGTH_LONG).show();
			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			
			
			if (isSearchSubway) {
				// 遍历所有poi，找到类型为公交线路的poi
//			    busLineUidList.clear();//先清空一下该uid集合,因为可能留有上一次中的数据
//			    
//			    List<PoiInfo> resultPoiList = result.getAllPoi();
//			    Toast.makeText(NearbyDetailActivity.this, "resultPoiList.size == " + resultPoiList.size(), Toast.LENGTH_LONG).show();
//				for (PoiInfo poi : result.getAllPoi()) {//遍历返回的所有的poi,这其中的poi有很多类型,很多数据就好比关键字搜索返回多种多样数据结果一样,并且返回的POI中还会带有一个uid,
//					if (poi.type == PoiInfo.POITYPE.BUS_LINE || poi.type == PoiInfo.POITYPE.SUBWAY_LINE) {//先在众多类型的数据中,筛选出公交线路类型,地铁线路类型
//						busLineUidList.add(poi.uid);//然后在到筛选得到的pi中取出uid,并加入到公交线路集合中去
//					}
//				}
//				
//				Toast.makeText(NearbyDetailActivity.this, "busLineUidList.size == " + busLineUidList.size(), Toast.LENGTH_LONG).show();
//				if (busLineUidList.size() > 0) {
//					mBaiduMap.clear();
//					searchBusline();
//				}
				
				mBaiduMap.clear();
				PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
				mBaiduMap.setOnMarkerClickListener(overlay);
				overlay.setData(result);
				overlay.addToMap();
				overlay.zoomToSpan();
				BitmapDescriptor bitmap = BitmapDescriptorFactory
						.fromResource(R.drawable.ic_cur_location);
				// 构建MarkerOption，用于在地图上添加Marker
				OverlayOptions option = new MarkerOptions().position(curLatLng)
						.icon(bitmap);
				// 在地图上添加Marker，并显示
				mBaiduMap.addOverlay(option);
			} else {
				mBaiduMap.clear();
				PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
				mBaiduMap.setOnMarkerClickListener(overlay);
				overlay.setData(result);
				overlay.addToMap();
				overlay.zoomToSpan();
				BitmapDescriptor bitmap = BitmapDescriptorFactory
						.fromResource(R.drawable.ic_cur_location);
				// 构建MarkerOption，用于在地图上添加Marker
				OverlayOptions option = new MarkerOptions().position(curLatLng)
						.icon(bitmap);
				// 在地图上添加Marker，并显示
				mBaiduMap.addOverlay(option);
			}
			
			return;
		}
		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {

			// 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
			String strInfo = "在";
			for (CityInfo cityInfo : result.getSuggestCityList()) {
				strInfo += cityInfo.city;
				strInfo += ",";
			}
			strInfo += "找到结果";
			Toast.makeText(this, strInfo, Toast.LENGTH_LONG).show();
		}
		
	}

	private class MyPoiOverlay extends PoiOverlay {

		public MyPoiOverlay(BaiduMap baiduMap) {
			super(baiduMap);
		}

		@Override
		public boolean onPoiClick(int index) {
			super.onPoiClick(index);
			PoiInfo poi = getPoiResult().getAllPoi().get(index);
			// if (poi.hasCaterDetails) {
			mPoiSearch.searchPoiDetail((new PoiDetailSearchOption())
					.poiUid(poi.uid));
			// }
			return true;
		}
	}

	@Override
	public void onGetSuggestionResult(SuggestionResult arg0) {

	}

	@Override
	protected void onPause() {
		mapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		mapView.onDestroy();
		mPoiSearch.destroy();
		mSuggestionSearch.destroy();
		super.onDestroy();
	}

	@Override
	public void onGetBusLineResult(BusLineResult busLineResult) {
		// TODO Auto-generated method stub
		if (busLineResult.error == SearchResult.ERRORNO.NO_ERROR) {//表示得到结果类型是NO_ERROR,没有错误,就说明返回了公交线路的信息  
			mBaiduMap.clear();  
            BusLineOverlay overlay = new MyBuslineOverlay(mBaiduMap);// 用于显示一条公交详情结果的Overlay,定义一个公交线路的图层用于显示公交线路  
            overlay.setData(busLineResult);  
            overlay.addToMap();// 将overlay添加到地图上  
            overlay.zoomToSpan();// 缩放地图，使所有overlay都在合适的视野范围内  
            mBaiduMap.setOnMarkerClickListener(overlay);  
            // 公交线路名称  
             stations =  busLineResult.getStations();//通过getStations方法可以得到该条公交线路中途径的所有的站台  
        /*  for (int i=0;i<stations.size();i++) { 
                toast("第"+(i+1)+"站: "+stations.get(i).getTitle()); 
            } 
            toast("线路:"+busLineResult.getBusLineName()+"   首班车时间:"+busLineResult.getStartTime()+" 末班车时间："+busLineResult.getEndTime());*/  
        } else {//否则提示未找到结果  
            Toast.makeText(NearbyDetailActivity.this, "抱歉，未找到结果",  
                    Toast.LENGTH_SHORT).show();  

        }  
    }  
	
	/**
	 * @author mikyou
	 * 搜索公交线路
	 * */
	private void searchBusline() {
		if (buslineIndex >= busLineUidList.size()) {//表示上一次的数据buslineIndex没有清空,所以就对buslineIndex进行初始化
			buslineIndex = 0;
		}
		if (buslineIndex >= 0 && buslineIndex < busLineUidList.size()
				&& busLineUidList.size() > 0) {
			//下面表示如果检索到了相应的公交线路,就返回true,否则返回false;这里的cityName就是传入的城市名,
			//cityName是通过MainActivity中的定位得到城市,所以也就实现了默认查询你所处城市的公交路线,
			//uid就传入通过poiSearch检索到的兴趣点中并筛选出的公交或地铁类型的uid,然后就通过OnGetBusLineSearchResultListener监听器
			//如果监听有公交线路信息,就返回一个true 
			CityBean cityBean = ContentUtils.getCityBean(this);
			if (cityBean != null) {
				String cityName = cityBean.getName();
				boolean flag = busLineSearch.searchBusLine((new BusLineSearchOption().city(cityName).uid(busLineUidList.get(buslineIndex))));
				if (flag) {
					Toast.makeText(NearbyDetailActivity.this, "检索成功~", 1000)
					.show();
				} else {
					Toast.makeText(NearbyDetailActivity.this, "检索失败~", 1000)
					.show();
				}
				buslineIndex++;
			}
			
		}
	}
	
	class MyBuslineOverlay extends BusLineOverlay {  
		  
        public MyBuslineOverlay(BaiduMap arg0) {  
            super(arg0);  
        }  
  
        /** 
         * 站点点击事件 
         */  
        @Override  
        public boolean onBusStationClick(int position) {//对显示在地图上的公交图层设置监听事件,position就是传入的是位置序号  
            MarkerOptions options = (MarkerOptions) getOverlayOptions().get(position);  
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLng(options.getPosition()));  
            Toast.makeText(NearbyDetailActivity.this, stations.get(position).getTitle()+"站", Toast.LENGTH_LONG).show();//实现点击某个站点图标弹出该站点的名称  
            return true;  
        }  
    }  

}
