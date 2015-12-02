package cn.com.zzwfang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.zzwfang.R;
import cn.com.zzwfang.location.LocationService;
import cn.com.zzwfang.location.LocationService.OnLocationListener;
import cn.com.zzwfang.view.AutoDrawableTextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
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
		OnGetSuggestionResultListener {
	
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

		// 初始化搜索模块，注册搜索事件监听
		mPoiSearch = PoiSearch.newInstance();
		mPoiSearch.setOnGetPoiSearchResultListener(this);
		mSuggestionSearch = SuggestionSearch.newInstance();
		mSuggestionSearch.setOnGetSuggestionResultListener(this);
		mBaiduMap = mapView.getMap();
		
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
		if (curLatLng != null) {
			mPoiSearch.searchNearby(new PoiNearbySearchOption()
					.location(curLatLng).keyword(keyWords).pageNum(10)
					.radius(10000));
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
}
