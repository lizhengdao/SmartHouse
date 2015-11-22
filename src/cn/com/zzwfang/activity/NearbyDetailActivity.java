package cn.com.zzwfang.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.zzwfang.R;
import cn.com.zzwfang.view.AutoDrawableTextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
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

	private TextView tvBack;

	private MapView mapView;

	private AutoDrawableTextView tvBank, tvBus, tvSubway, tvSchool, tvHostipal,
			tvLeisure, tvShopping, tvBodyBuilding, tvFoods;

	private PoiSearch mPoiSearch = null;
	private SuggestionSearch mSuggestionSearch = null;
	private BaiduMap mBaiduMap = null;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
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
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_nearby_detail_back:
			finish();
			break;
		case R.id.act_nearby_detail_bank: // 银行
			break;
		case R.id.act_nearby_detail_bus: // 公交
			break;
		case R.id.act_nearby_detail_subway: // 地铁
			break;
		case R.id.act_nearby_detail_school: // 教育
			break;
		case R.id.act_nearby_detail_hostipal: // 医院
			break;
		case R.id.act_nearby_detail_leisure: // 休闲
			break;
		case R.id.act_nearby_detail_shopping: // 购物
			break;
		case R.id.act_nearby_detail_body_building: // 健身
			break;
		case R.id.act_nearby_detail_foods: // 美食
			break;

		}
	}
	
	private void searchNearby() {
//	    LatLng latlng = new LatLng(arg0, arg1)
//	    mPoiSearch.searchNearby(new PoiNearbySearchOption().location(arg0))
	}

	@Override
	public void onGetPoiDetailResult(PoiDetailResult result) {
		// TODO Auto-generated method stub

		if (result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(this, "抱歉，未找到结果", Toast.LENGTH_SHORT)
					.show();
		} else {
			Toast.makeText(this, result.getName() + ": " + result.getAddress(), Toast.LENGTH_SHORT)
			.show();
		}
	}
	
	@Override
	public void onGetPoiResult(PoiResult result) {
		// TODO Auto-generated method stub

		if (result == null
				|| result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
			Toast.makeText(this, "未找到结果", Toast.LENGTH_LONG)
			.show();
			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			mBaiduMap.clear();
			PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
			mBaiduMap.setOnMarkerClickListener(overlay);
			overlay.setData(result);
			overlay.addToMap();
			overlay.zoomToSpan();
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
			Toast.makeText(this, strInfo, Toast.LENGTH_LONG)
					.show();
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
		// TODO Auto-generated method stub

	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		mPoiSearch.destroy();
		mSuggestionSearch.destroy();
		super.onDestroy();
	}
}
