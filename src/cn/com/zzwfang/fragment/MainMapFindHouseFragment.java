package cn.com.zzwfang.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.zzwfang.R;
import cn.com.zzwfang.activity.BaseActivity;
import cn.com.zzwfang.activity.MainActivity;
import cn.com.zzwfang.activity.NearbyDetailActivity;
import cn.com.zzwfang.activity.SecondHandHouseActivity;
import cn.com.zzwfang.bean.CityBean;
import cn.com.zzwfang.bean.MapFindHouseBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.bean.SearchHouseItemBean;
import cn.com.zzwfang.bean.TextValueBean;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.fragment.MainHomeFragment.OnCitySelectedListener;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.location.LocationService;
import cn.com.zzwfang.location.LocationService.OnLocationListener;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.util.Jumper;
import cn.com.zzwfang.view.AutoDrawableTextView;
import cn.com.zzwfang.view.helper.PopViewHelper;
import cn.com.zzwfang.view.helper.PopViewHelper.OnConditionSelectListener;
import cn.com.zzwfang.view.helper.PopViewHelper.OnMapFindHouseMoreConditionListener;

import com.alibaba.fastjson.JSON;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.MarkerOptions.MarkerAnimateType;
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

/**
 * 地图找房  默认显示二手房
 * @author MISS-万
 *
 */
public class MainMapFindHouseFragment extends BaseFragment implements
		OnClickListener, OnCitySelectedListener, OnGetPoiSearchResultListener {
	
	private TextView tvBack, tvArea, tvTotalPrice, tvHouseType;
	private AutoDrawableTextView autoTvLocate, autoTvSubway, autoTvNearby;
	private MapView mapView;
	private BaiduMap baiduMap;
	private LinearLayout lltArea, lltTotalPrice, lltHouseType, lltMore;
	private ArrayList<MapFindHouseBean> mapAreas = new ArrayList<MapFindHouseBean>();
	private ArrayList<SearchHouseItemBean> estates = new ArrayList<SearchHouseItemBean>();
	private OnMapFindHouseMoreConditionListener onMapFindHouseMoreConditionListener;
	
	private PoiSearch mPoiSearch = null;
	private LatLng curLatLng;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_main_map_find_house, null);
		initView(view);
		setListener();
		initMapData();
		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		initData();
		getMapFindHouseDataArea();
	}

	private void initView(View view) {
		tvBack = (TextView) view.findViewById(R.id.frag_map_back);
		tvArea = (TextView) view.findViewById(R.id.frag_map_find_house_area_tv);
		tvTotalPrice = (TextView) view
				.findViewById(R.id.frag_map_find_house_total_price_tv);
		tvHouseType = (TextView) view
				.findViewById(R.id.frag_map_find_house_type_tv);

		lltArea = (LinearLayout) view
				.findViewById(R.id.frag_map_find_house_area_llt);
		lltTotalPrice = (LinearLayout) view
				.findViewById(R.id.frag_map_find_house_total_price_llt);
		lltHouseType = (LinearLayout) view
				.findViewById(R.id.frag_map_find_house_type_llt); 
		lltMore = (LinearLayout) view
				.findViewById(R.id.frag_map_find_house_more_llt);
		
		autoTvLocate = (AutoDrawableTextView) view.findViewById(R.id.frag_map_find_house_locate);
		autoTvSubway = (AutoDrawableTextView) view.findViewById(R.id.frag_map_find_house_subway);
		autoTvNearby = (AutoDrawableTextView) view.findViewById(R.id.frag_map_find_house_nearby);
		
		mapView = (MapView) view.findViewById(R.id.bmapView);
		mapView.showZoomControls(false);
		baiduMap = mapView.getMap();
		
		
		
		MapStatus status = new MapStatus.Builder().zoom(14).build();
		MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory
				.newMapStatus(status);
		baiduMap.setMapStatus(mapStatusUpdate);
		
		curLatLng = ContentUtils.getSelectedCityLatLng(getActivity());
		if (curLatLng != null) {
		    // 构建Marker图标
	        BitmapDescriptor bitmap = BitmapDescriptorFactory
	                .fromResource(R.drawable.ic_cur_location);
	        // 构建MarkerOption，用于在地图上添加Marker
	        OverlayOptions option = new MarkerOptions().position(curLatLng)
	                .icon(bitmap);
	        // 在地图上添加Marker，并显示
	        baiduMap.addOverlay(option);
		}
		
	}

	private void setListener() {
		tvBack.setOnClickListener(this);
		lltArea.setOnClickListener(this);
		lltTotalPrice.setOnClickListener(this);
		lltHouseType.setOnClickListener(this);
		lltMore.setOnClickListener(this);
		
		autoTvLocate.setOnClickListener(this);
		autoTvSubway.setOnClickListener(this);
		autoTvNearby.setOnClickListener(this);
		

		baiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(Marker marker) {

				Bundle bundle = marker.getExtraInfo();
				Object data = bundle.get("area");
				if (data instanceof MapFindHouseBean) {
					MapFindHouseBean area = (MapFindHouseBean) data;
					if (area != null) {
						// 点击了某小区域区域   加载该区域楼盘
						TextValueBean textValueBeanArea = new TextValueBean();
						textValueBeanArea.setValue(area.getId());
						areaCondition = textValueBeanArea;
						getMapFindHouseEstate();
					}
				} else if (data instanceof SearchHouseItemBean) {
					//  点击某一楼盘，加载该楼盘  二手房列表页
					SearchHouseItemBean estate = (SearchHouseItemBean) data;
					Jumper.newJumper()
							.setAheadInAnimation(R.anim.activity_push_in_right)
							.setAheadOutAnimation(R.anim.activity_alpha_out)
							.setBackInAnimation(R.anim.activity_alpha_in)
							.setBackOutAnimation(R.anim.activity_push_out_right)
							.putString(
									SecondHandHouseActivity.INTENT_KEYWORDS,
									estate.getName())
							.jump((BaseActivity) getActivity(),
									SecondHandHouseActivity.class);
				}

				return true;
			}
		});

		onTotalPriceSelectListener = new OnConditionSelectListener() {

			@Override
			public void onConditionSelect(TextValueBean txtValueBean) {
				totalPriceCondition = txtValueBean;
				tvTotalPrice.setText(txtValueBean.getText());
				getMapFindHouseDataArea();
				// getMapFindHouseEstate();
			}
		};

		onHouseTypeSelectListener = new OnConditionSelectListener() {

			@Override
			public void onConditionSelect(TextValueBean txtValueBean) {
				roomTypeCondition = txtValueBean;
				tvHouseType.setText(txtValueBean.getText());
				getMapFindHouseDataArea();
				// getMapFindHouseEstate();
			}
		};

		onAreaSelectListener = new OnConditionSelectListener() {

			@Override
			public void onConditionSelect(TextValueBean txtValueBean) {
				areaCondition = txtValueBean;
				tvArea.setText(txtValueBean.getText());
				getMapFindHouseDataArea();
			}
		};
		
		onMapFindHouseMoreConditionListener = new OnMapFindHouseMoreConditionListener() {
			
			@Override
			public void onMapFindHouseMoreConditon(TextValueBean typeConditionData,
					TextValueBean labelConditionData,
					TextValueBean saleStatusConditonData) {
				usageTypeCondition = typeConditionData;
				labelCondition = labelConditionData;
				statusCondition = saleStatusConditonData;
			}
		};
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.frag_map_back:
			((MainActivity) getActivity()).backToHomeFragment();
			break;
		case R.id.frag_map_find_house_area_llt: // 区域
			PopViewHelper.showSelectAreaPopWindow(getActivity(), lltArea,
					areas, onAreaSelectListener);
			break;
		case R.id.frag_map_find_house_total_price_llt: // 总价
			PopViewHelper.showSelectTotalPricePopWindow(getActivity(),
					lltTotalPrice, salePriceRanges, onTotalPriceSelectListener);
			break;
		case R.id.frag_map_find_house_type_llt: // 类型
			PopViewHelper.showSelectHouseTypePopWindow(getActivity(),
					lltHouseType, houseTypes, onHouseTypeSelectListener);
			break;
		case R.id.frag_map_find_house_more_llt:// 更多
			// TODO
			
			PopViewHelper.showMapFindHouseMorePopWindow(getActivity(), moreType,
					prpUsages, estateLabels, estateStatus, lltMore, onMapFindHouseMoreConditionListener);
			break;
		case R.id.frag_map_find_house_locate:  //  定位
			locate();
			break;
		case R.id.frag_map_find_house_subway:  // 地铁
			searchNearby("地铁");
			break;
		case R.id.frag_map_find_house_nearby:  // 周边
			if (curLatLng != null) {
				Jumper.newJumper()
				.setAheadInAnimation(R.anim.activity_push_in_right)
				.setAheadOutAnimation(R.anim.activity_alpha_out)
				.setBackInAnimation(R.anim.activity_alpha_in)
				.setBackOutAnimation(R.anim.activity_push_out_right)
				.putDouble(NearbyDetailActivity.INTENT_LAT, curLatLng.latitude)
				.putDouble(NearbyDetailActivity.INTENT_LNG, curLatLng.longitude)
				.jump(this, NearbyDetailActivity.class);
			} else {
			    curLatLng = ContentUtils.getSelectedCityLatLng(getActivity());
			    if (curLatLng != null) {
			        Jumper.newJumper()
	                .setAheadInAnimation(R.anim.activity_push_in_right)
	                .setAheadOutAnimation(R.anim.activity_alpha_out)
	                .setBackInAnimation(R.anim.activity_alpha_in)
	                .setBackOutAnimation(R.anim.activity_push_out_right)
	                .putDouble(NearbyDetailActivity.INTENT_LAT, curLatLng.latitude)
	                .putDouble(NearbyDetailActivity.INTENT_LNG, curLatLng.longitude)
	                .jump(this, NearbyDetailActivity.class);
			    }
			}
			break;
		}
	}
	
	private void initMapData() {
		// 初始化搜索模块，注册搜索事件监听
		mPoiSearch = PoiSearch.newInstance();
		mPoiSearch.setOnGetPoiSearchResultListener(this);
	}
	
	private void searchNearby(String keyWords) {
		if (curLatLng != null) {
			mPoiSearch.searchNearby(new PoiNearbySearchOption()
					.location(curLatLng).keyword(keyWords).pageNum(10)
					.radius(10000));
		}
	}
	
	private void locate() {
		final LocationService locationService = LocationService
				.getInstance(getActivity());
		locationService.startLocationService(new OnLocationListener() {

			@Override
			public void onLocationCompletion(BDLocation location) {
				curLatLng = new LatLng(location.getLatitude(), location
						.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(curLatLng);
				baiduMap.animateMapStatus(u);
				locationService.stopLocationService();

				// 构建Marker图标
				BitmapDescriptor bitmap = BitmapDescriptorFactory
						.fromResource(R.drawable.ic_cur_location);
				// 构建MarkerOption，用于在地图上添加Marker
				OverlayOptions option = new MarkerOptions().position(curLatLng)
						.icon(bitmap);
				// 在地图上添加Marker，并显示
				baiduMap.addOverlay(option);
			}
		});
	}

	public static final String SalePriceRange = "SalePriceRange";
	public static final String HouseType = "HouseType";
	public static final String PrpUsage = "PrpUsage";
	public static final String EstateLabel = "EstateLabel";
	public static final String EstateStatus = "EstateStatus";

	private void initData() {
		moreType.add("类型");
		moreType.add("特色标签");
		moreType.add("售卖状态");
		getConditionList(SalePriceRange);
		getConditionList(HouseType);
		getConditionList(PrpUsage);
		getConditionList(EstateLabel);
		getConditionList(EstateStatus);
		getAreaList();
	}


	/**
	 * 获取某大区域的子区域列表， 获取后在地图上以圆形图标显示  已检查
	 */
	private void getMapFindHouseDataArea() {
		CityBean cityBean = ContentUtils.getCityBean(getActivity());
		if (cityBean == null) {
			return;
		}
		ActionImpl actionImpl = ActionImpl.newInstance(getActivity());
		//  默认传0，0代表出售，1代表出租
		actionImpl.getMapFindHouseData(0, cityBean.getSiteId(),
				new ResultHandlerCallback() {

					@Override
					public void rc999(RequestEntity entity, Result result) {
					}

					@Override
					public void rc3001(RequestEntity entity, Result result) {
					}

					@Override
					public void rc0(RequestEntity entity, Result result) {
						mapAreas.clear();
						ArrayList<MapFindHouseBean> temp = (ArrayList<MapFindHouseBean>) JSON
								.parseArray(result.getData(),
										MapFindHouseBean.class);
						mapAreas.addAll(temp);
						rendArea();
					}
				});
	}

	/**
	 * 在地图上以圆形展示子区域  已检查
	 */
	private void rendArea() {
		if (mapAreas != null && mapAreas.size() > 0 && baiduMap != null) {
			baiduMap.clear();
			
			for (MapFindHouseBean area : mapAreas) {
			    
			    View viewAreaPoint = View.inflate(getActivity(),
	                    R.layout.view_map_point_area, null);
	            TextView tvEstate = (TextView) viewAreaPoint
	                    .findViewById(R.id.view_point_title);
	            TextView tvNum = (TextView) viewAreaPoint
	                    .findViewById(R.id.view_point_num);
				LatLng latLng = new LatLng(area.getLat(), area.getLng());
				tvEstate.setText(area.getName());
				tvNum.setText(area.getPrpCount());
				Bitmap bmpAreaPoint = getViewBitmap(viewAreaPoint);

				BitmapDescriptor bdA = BitmapDescriptorFactory
						.fromBitmap(bmpAreaPoint);
				Bundle bundle = new Bundle();
				bundle.putSerializable("area", area);
				MarkerOptions ooA = new MarkerOptions().position(latLng)
						.icon(bdA).zIndex(9).draggable(true).extraInfo(bundle);

				ooA.animateType(MarkerAnimateType.drop);
				baiduMap.addOverlay(ooA);
			}
			MapFindHouseBean area = mapAreas.get(0);
			LatLng latLng = new LatLng(area.getLat(), area.getLng());
			MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latLng);
			baiduMap.setMapStatus(u);
		}
	}

	/**
	 * 区域
	 */
	private ArrayList<TextValueBean> areas = new ArrayList<TextValueBean>();
	/**
	 * 总价范围
	 */
	private ArrayList<TextValueBean> salePriceRanges = new ArrayList<TextValueBean>();
	/**
	 * 户型
	 */
	private ArrayList<TextValueBean> houseTypes = new ArrayList<TextValueBean>();
	/**
	 * 物业类型
	 */
	private ArrayList<TextValueBean> prpUsages = new ArrayList<TextValueBean>();

	/**
	 * 特色标签
	 */
	private ArrayList<TextValueBean> estateLabels = new ArrayList<TextValueBean>();

	/**
	 * 售卖状态
	 */
	private ArrayList<TextValueBean> estateStatus = new ArrayList<TextValueBean>();

	/**
	 * 区域
	 */
	private OnConditionSelectListener onAreaSelectListener;
	/**
	 * 总价
	 */
	private OnConditionSelectListener onTotalPriceSelectListener;
	/**
	 * 房型
	 */
	private OnConditionSelectListener onHouseTypeSelectListener;

	private ArrayList<String> moreType = new ArrayList<String>();
	

	/**
	 * 区域
	 */
	private TextValueBean areaCondition;
	/**
	 * 总价
	 */
	private TextValueBean totalPriceCondition;
	/**
	 * 标签
	 */
	private TextValueBean labelCondition;
	/**
	 * 售卖状态
	 */
	private TextValueBean statusCondition;
	/**
	 * 房型
	 */
	private TextValueBean roomTypeCondition;  // 房型
	
	/**
	 * 用途类型
	 */
	private TextValueBean usageTypeCondition;

	/**
	 * 获取子区域楼盘数据(调用楼盘搜索列表接口)
	 */
	private void getMapFindHouseEstate() {
		ActionImpl actionImpl = ActionImpl.newInstance(getActivity());
		actionImpl.getSearchHouseList(areaCondition, totalPriceCondition, roomTypeCondition,
				usageTypeCondition, labelCondition, statusCondition, null,
				new ResultHandlerCallback() {

					@Override
					public void rc999(RequestEntity entity, Result result) {

					}

					@Override
					public void rc3001(RequestEntity entity, Result result) {

					}

					@Override
					public void rc0(RequestEntity entity, Result result) {
						estates.clear();
						ArrayList<SearchHouseItemBean> temp = (ArrayList<SearchHouseItemBean>) JSON
								.parseArray(result.getData(),
										SearchHouseItemBean.class);
						estates.addAll(temp);
						rendEstate();
					}
				});

	}

	private void rendEstate() {
		if (estates != null && estates.size() > 0) {
			 baiduMap.clear();
			
			for (SearchHouseItemBean estate : estates) {
			    View viewAreaPoint = View.inflate(getActivity(),
	                    R.layout.view_map_point_estate, null);
	            TextView tvArea = (TextView) viewAreaPoint
	                    .findViewById(R.id.view_point_estate_title);
	            TextView tvPrice = (TextView) viewAreaPoint
	                    .findViewById(R.id.view_point_estate_price);
				LatLng latLng = new LatLng(estate.getLat(), estate.getLng());
				tvArea.setText(estate.getName());
				tvPrice.setText(estate.getPrpAvg() + estate.getRentUnitName());
				Bitmap bmpAreaPoint = getViewBitmap(viewAreaPoint);

				BitmapDescriptor bdA = BitmapDescriptorFactory
						.fromBitmap(bmpAreaPoint);
				Bundle bundle = new Bundle();
				bundle.putSerializable("area", estate);
				MarkerOptions ooA = new MarkerOptions().position(latLng)
						.icon(bdA).zIndex(9).draggable(true).extraInfo(bundle);

				ooA.animateType(MarkerAnimateType.drop);
				baiduMap.addOverlay(ooA);
			}
			SearchHouseItemBean estate = estates.get(0);
			LatLng latLng = new LatLng(estate.getLat(), estate.getLng());
			MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latLng);
//			baiduMap.setMapStatus(u);
			
            baiduMap.animateMapStatus(u);
		}
	}

	@Override
	public void onResume() {
        if (mapView != null) {
            mapView.onResume();
        }
		super.onResume();
	}

	@Override
	public void onPause() {
	    if (mapView != null) {
	        mapView.onPause();
	    }
		super.onPause();
	}

	@Override
	public void onDestroy() {
	    if (mapView != null) {
	        mapView.onDestroy();
	    }
		super.onDestroy();
	}

	private Bitmap getViewBitmap(View addViewContent) {

		addViewContent.setDrawingCacheEnabled(true);

		addViewContent.measure(View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
				.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
		addViewContent.layout(0, 0, addViewContent.getMeasuredWidth(),
				addViewContent.getMeasuredHeight());

		addViewContent.buildDrawingCache();
		Bitmap cacheBitmap = addViewContent.getDrawingCache();
		Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

		return bitmap;
	}

	public void getConditionList(final String conditionName) {
		ActionImpl actionImpl = ActionImpl.newInstance(getActivity());
		actionImpl.getConditionByName(conditionName,
				new ResultHandlerCallback() {

					@Override
					public void rc999(RequestEntity entity, Result result) {
					}

					@Override
					public void rc3001(RequestEntity entity, Result result) {
					}

					@Override
					public void rc0(RequestEntity entity, Result result) {
						ArrayList<TextValueBean> temp = (ArrayList<TextValueBean>) JSON
								.parseArray(result.getData(),
										TextValueBean.class);
						if (SalePriceRange.equals(conditionName)) {
							salePriceRanges.addAll(temp);
						} else if (HouseType.equals(conditionName)) {
							houseTypes.addAll(temp);
						} else if (PrpUsage.equals(conditionName)) {
							prpUsages.addAll(temp);
						} else if (EstateLabel.equals(conditionName)) {
							estateLabels.addAll(temp);
						} else if (EstateStatus.equals(conditionName)) {
							estateStatus.addAll(temp);
						}
					}
				});
	}

	

	@Override
	public void onCitySelected(CityBean cityBean) {
	    baiduMap.clear();
	    areaCondition = null;
        tvArea.setText("不限");
        getAreaList();
        getMapFindHouseDataArea();
	}
	
	
	/**
	 * 获取某一城市区域列表   此处已检查
	 */
	private void getAreaList() {

		CityBean cityBean = ContentUtils.getCityBean(getActivity());
		if (cityBean == null) {
			return;
		}
		ActionImpl actionImpl = ActionImpl.newInstance(getActivity());
		actionImpl.getAreaList(cityBean.getSiteId(),
				new ResultHandlerCallback() {

					@Override
					public void rc999(RequestEntity entity, Result result) {
					}

					@Override
					public void rc3001(RequestEntity entity, Result result) {
					}

					@Override
					public void rc0(RequestEntity entity, Result result) {
						areas.clear();
						ArrayList<TextValueBean> temp = (ArrayList<TextValueBean>) JSON
								.parseArray(result.getData(),
										TextValueBean.class);
						areas.addAll(temp);
					}
				});
	}

	@Override
	public void onGetPoiDetailResult(PoiDetailResult result) {
		// TODO Auto-generated method stub
		if (result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(getActivity(), "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(getActivity(), result.getName() + ": " + result.getAddress(),
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onGetPoiResult(PoiResult result) {
		// TODO Auto-generated method stub
		if (result == null
				|| result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
			Toast.makeText(getActivity(), "未找到结果", Toast.LENGTH_LONG).show();
			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			baiduMap.clear();
			PoiOverlay overlay = new MyPoiOverlay(baiduMap);
			baiduMap.setOnMarkerClickListener(overlay);
			overlay.setData(result);
			overlay.addToMap();
			overlay.zoomToSpan();
			BitmapDescriptor bitmap = BitmapDescriptorFactory
					.fromResource(R.drawable.ic_cur_location);
			// 构建MarkerOption，用于在地图上添加Marker
			OverlayOptions option = new MarkerOptions().position(curLatLng)
					.icon(bitmap);
			// 在地图上添加Marker，并显示
			baiduMap.addOverlay(option);
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
			Toast.makeText(getActivity(), strInfo, Toast.LENGTH_LONG).show();
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

}
