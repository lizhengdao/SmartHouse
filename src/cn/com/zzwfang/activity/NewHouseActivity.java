package cn.com.zzwfang.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import cn.com.zzwfang.R;
import cn.com.zzwfang.adapter.HomeRecommendHouseAdapter;
import cn.com.zzwfang.adapter.NewHouseAdapter;
import cn.com.zzwfang.bean.CityBean;
import cn.com.zzwfang.bean.FieldNameValueBean;
import cn.com.zzwfang.bean.HouseSourceParamBean;
import cn.com.zzwfang.bean.MapFindHouseBean;
import cn.com.zzwfang.bean.NameValueBean;
import cn.com.zzwfang.bean.NewHouseBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.bean.SearchHouseItemBean;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.location.LocationService;
import cn.com.zzwfang.location.LocationService.OnLocationListener;
import cn.com.zzwfang.pullview.AbPullToRefreshView;
import cn.com.zzwfang.pullview.AbPullToRefreshView.OnFooterLoadListener;
import cn.com.zzwfang.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.util.Jumper;
import cn.com.zzwfang.view.AutoDrawableTextView;
import cn.com.zzwfang.view.helper.PopViewHelper;
import cn.com.zzwfang.view.helper.PopViewHelper.OnHouseSourceParamPickListener;
import cn.com.zzwfang.view.helper.PopViewHelper.OnHouseSourceSortTypeClickListener;

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
import com.baidu.mapapi.map.MarkerOptions.MarkerAnimateType;
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

/**
 * 新房列表页
 * 
 * 获取新房列表页，
 * 输入区域ID、
 * 价格区间（用“-”进行分隔，如60-80代表60-80万）、
 * 房屋用途（0：不限 1：普通住宅 2：别墅 3：商住两用）、
 * 特色（0：不限 1：精装 2：地铁房）、
 * 售卖状态（0：不限 1：即将开盘 2：排卡中 3：在售 4：售罄）、
 * 城市ID、页码、页面大小
 * 和所查房型（0：不限 1：一居 2：两居 3：三居 4：四居）
 * 区域
 * 
 * 价格区间
 * 房型
 * 房屋用途
 * 特色
 * 售卖状态
 * 
 * @author lzd
 *
 */
public class NewHouseActivity extends BaseActivity implements OnClickListener,
		OnHeaderRefreshListener, OnFooterLoadListener, OnCheckedChangeListener,
		OnItemClickListener, OnGetPoiSearchResultListener,
		OnHouseSourceSortTypeClickListener, OnHouseSourceParamPickListener {
	
	public static final String INTENT_KEY_WORDS = "NewHouseActivity.intent_key_words";

	private TextView tvBack, tvArea, tvTotalPrice, tvHouseType, tvMore;
	private EditText edtKeyWords;
	private CheckBox cbxListAndMap;
	private MapView mapView;
	private BaiduMap baiduMap;
	private FrameLayout mapViewFlt;
	private LinearLayout lltArea, lltTotalPrice, lltHouseType, lltMore;
	private ImageView imgClearKeyWords;

	private AbPullToRefreshView pullView;
	private ListView lstNewHouseView;
	private AutoDrawableTextView tvSort;
	
	private View lineAnchor, lineOne, lineTwo, lineThree;
	private LinearLayout lltNewHouseSourceParam;
	
	private AutoDrawableTextView autoTvLocate, autoTvSubway, autoTvNearby;
	private PoiSearch mPoiSearch = null;
	private LatLng curLatLng;
	
	private ArrayList<NewHouseBean> newHouses = new ArrayList<NewHouseBean>();
	private NewHouseAdapter adapter;

	private String cityId = "";

	private int pageIndex = 1;
	private int pageTotal = 0;
	private String keyWords;
	
//  排序参数
	private ArrayList<FieldNameValueBean> sortParamList;
	/**
	 * 排序（已选择的）
	 */
	private FieldNameValueBean sortTypeBean;
	private ArrayList<HouseSourceParamBean> houseSourceParams;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.act_new_house);
		cityId = getIntent().getStringExtra(
				HomeRecommendHouseAdapter.INTENT_CITY_ID);
		keyWords = getIntent().getStringExtra(INTENT_KEY_WORDS);
		initView();
		setListener();
		initData();
	}

	private void initView() {
		tvBack = (TextView) findViewById(R.id.act_new_house_back);
		edtKeyWords = (EditText) findViewById(R.id.act_new_house_key_word_edt);
		cbxListAndMap = (CheckBox) findViewById(R.id.act_new_house_list_map);
		pullView = (AbPullToRefreshView) findViewById(R.id.pull_new_house);
		lstNewHouseView = (ListView) findViewById(R.id.lst_new_house);
		mapView = (MapView) findViewById(R.id.act_new_house_map);
		mapViewFlt = (FrameLayout) findViewById(R.id.act_new_house_map_flt);
		imgClearKeyWords = (ImageView) findViewById(R.id.act_new_house_clear_key_wrods);

		lltArea = (LinearLayout) findViewById(R.id.act_new_house_area_llt);
		lltTotalPrice = (LinearLayout) findViewById(R.id.act_new_house_total_price_llt);
		lltHouseType = (LinearLayout) findViewById(R.id.act_new_house_type_llt);
		lltMore = (LinearLayout) findViewById(R.id.act_new_house_more_llt);
		
		tvArea = (TextView) findViewById(R.id.act_new_house_area_tv);
		tvTotalPrice = (TextView) findViewById(R.id.act_new_house_total_price_tv);
		tvHouseType = (TextView) findViewById(R.id.act_new_house_type_tv);
		tvMore = (TextView) findViewById(R.id.act_new_house_more_tv);
		
		lineAnchor = findViewById(R.id.line_new_house_anchor);
		lineOne = findViewById(R.id.line_new_house_one);
		lineTwo = findViewById(R.id.line_new_house_two);
		lineThree = findViewById(R.id.line_new_house_three);
		
		lltNewHouseSourceParam = (LinearLayout) findViewById(R.id.llt_new_house_source_params);
		
		autoTvLocate = (AutoDrawableTextView) findViewById(R.id.act_new_house_locate);
		autoTvSubway = (AutoDrawableTextView) findViewById(R.id.act_new_house_subway);
		autoTvNearby = (AutoDrawableTextView) findViewById(R.id.act_new_house_nearby);
		
		tvSort = (AutoDrawableTextView) findViewById(R.id.tv_new_house_sort);
		
		if (!TextUtils.isEmpty(keyWords)) {
            edtKeyWords.setText(keyWords);
        }
		
		baiduMap = mapView.getMap();
		adapter = new NewHouseAdapter(this, newHouses);
		lstNewHouseView.setAdapter(adapter);
		lstNewHouseView.setOnItemClickListener(this);

		mapView.showZoomControls(false);
		// 初始化搜索模块，注册搜索事件监听
	    mPoiSearch = PoiSearch.newInstance();
	    mPoiSearch.setOnGetPoiSearchResultListener(this);
	    
	    MapStatus status = new MapStatus.Builder().zoom(14).build();
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory
                .newMapStatus(status);
        baiduMap.setMapStatus(mapStatusUpdate);
        
        curLatLng = ContentUtils.getSelectedCityLatLng(this);
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

		tvBack.setOnClickListener(this);
		cbxListAndMap.setOnCheckedChangeListener(this);
		pullView.setPullRefreshEnable(true);
		pullView.setLoadMoreEnable(true);
		pullView.setOnHeaderRefreshListener(this);
		pullView.setOnFooterLoadListener(this);
		lltArea.setOnClickListener(this);
		lltTotalPrice.setOnClickListener(this);
		lltHouseType.setOnClickListener(this);
		lltMore.setOnClickListener(this);
		autoTvLocate.setOnClickListener(this);
		autoTvSubway.setOnClickListener(this);
		autoTvNearby.setOnClickListener(this);
		imgClearKeyWords.setOnClickListener(this);
		tvSort.setOnClickListener(this);
		
	}

	private void setListener() {
		
        edtKeyWords.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId  == EditorInfo.IME_ACTION_SEARCH) {
					keyWords = edtKeyWords.getText().toString();
					getNewHouseList(cityId, keyWords, 10, true);
					return true;
				}
				return false;
			}
		});



		
		
		baiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(Marker marker) {

				Bundle bundle = marker.getExtraInfo();
				Object data = bundle.get("area");
				if (data instanceof MapFindHouseBean) {
					MapFindHouseBean area = (MapFindHouseBean) data;
					if (area != null) {
						// 点击了某小区域区域   加载该区域楼盘
//						TextValueBean textValueBeanArea = new TextValueBean();
//						textValueBeanArea.setValue(area.getId());
						getMapFindHouseEstate();
					}
				} else if (data instanceof SearchHouseItemBean) {
					//  点击某一楼盘，加载该楼盘  二手房列表页
					SearchHouseItemBean estate = (SearchHouseItemBean) data;
					keyWords = estate.getName();
					edtKeyWords.setText(keyWords);
					if (TextUtils.isEmpty(cityId)) {
						CityBean cityBean = ContentUtils.getCityBean(NewHouseActivity.this);
						cityId = cityBean.getSiteId();
					}
					
					getNewHouseList(cityId, keyWords, 10, true);
					cbxListAndMap.setChecked(true);
				}

				return true;
			}
		});
	}
	
	private void initData() {
		
		getHouseSourceParam();  // 获取房源参数
		getHouseSourceSort();   //  获取房源排序参数
		keyWords = edtKeyWords.getText().toString().trim();
		getNewHouseList(cityId, keyWords, 10, true);
	}
	
	@Override
	public void onHeaderRefresh(AbPullToRefreshView view) {
		keyWords = edtKeyWords.getText().toString().trim();
		getNewHouseList(cityId, keyWords, 10, true);
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		NewHouseBean newHouseBean = newHouses.get(position);
		
		Jumper.newJumper()
        .setAheadInAnimation(R.anim.activity_push_in_right)
        .setAheadOutAnimation(R.anim.activity_alpha_out)
        .setBackInAnimation(R.anim.activity_alpha_in)
        .setBackOutAnimation(R.anim.activity_push_out_right)
        .putString(NewHouseDetailActivity.INTENT_ESTATE_ID, newHouseBean.getId())
        .jump(this, NewHouseDetailActivity.class);
	}
	
	
	@Override
	public void onFooterLoad(AbPullToRefreshView view) {
		if (pageIndex > pageTotal) {
			pullView.onFooterLoadFinish();
			return;
		}
		getNewHouseList(cityId, keyWords, 10, false);
	}

	

	private void getNewHouseList(String cityId, String keyWords, int pageSize,
			final boolean isRefresh) {

		if (isRefresh) {
			pageIndex = 1;
		}
		
        HashMap<String, String> requestParams = new HashMap<String, String>();
		
		if (houseSourceParams != null) {
			for (HouseSourceParamBean para : houseSourceParams) {
				ArrayList<NameValueBean> nameValues = para.getValues();
				for (NameValueBean nameValueBean : nameValues) {
					if (nameValueBean.isSelected()) {
						requestParams.put(para.getFiled(), nameValueBean.getValue());
						break;
					}
				}
			}
		}
		
		if (sortTypeBean != null) {
			requestParams.put(sortTypeBean.getFiled(), sortTypeBean.getValue());
		}
		
		
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.getNewHouseList(cityId, requestParams, keyWords, 10, pageIndex, new ResultHandlerCallback() {

					@Override
					public void rc999(RequestEntity entity, Result result) {
						if (isRefresh) {
							pullView.onHeaderRefreshFinish();
						} else {
							pullView.onFooterLoadFinish();
						}
					}

					@Override
					public void rc3001(RequestEntity entity, Result result) {
						if (isRefresh) {
							pullView.onHeaderRefreshFinish();
						} else {
							pullView.onFooterLoadFinish();
						}
					}

					@Override
					public void rc0(RequestEntity entity, Result result) {
						int total = result.getTotal();
						pageTotal = (int) Math.ceil(((double)total / (double)10));
						ArrayList<NewHouseBean> temp = (ArrayList<NewHouseBean>) JSON.parseArray(result.getData(), NewHouseBean.class);
						if (isRefresh) {
							newHouses.clear();
						}
						newHouses.addAll(temp);
						adapter.notifyDataSetChanged();
						pageIndex++;
						if (isRefresh) {
							pullView.onHeaderRefreshFinish();
						} else {
							pullView.onFooterLoadFinish();
						}
					}
				});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_new_house_back: // 返回
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(getWindow().getDecorView()
					.getWindowToken(), 0);
			finish();
			break;
		case R.id.act_new_house_area_llt: // 区域
//			PopViewHelper.showSelectAreaPopWindow(this, lltArea, areas,
//					onAreaSelectListener);
			
			if (houseSourceParams != null) {
				PopViewHelper.showPickHouseSourceParamPopWindow(this, lineAnchor, 0, houseSourceParams, this);
			}
			break;
		case R.id.act_new_house_total_price_llt: // 总价
//			PopViewHelper.showSelectTotalPricePopWindow(this, lltTotalPrice,
//					salePriceRanges, onTotalPriceSelectListener);
			
			if (houseSourceParams != null) {
            	PopViewHelper.showPickHouseSourceParamPopWindow(this, lineAnchor, 1, houseSourceParams, this);
			}
			break;
		case R.id.act_new_house_type_llt: // 房型
//			PopViewHelper.showSelectHouseTypePopWindow(this, lltHouseType,
//					houseTypes, onHouseTypeSelectListener);
			if (houseSourceParams != null) {
            	PopViewHelper.showPickHouseSourceParamPopWindow(this, lineAnchor, 2, houseSourceParams, this);
			}
			break;
		case R.id.act_new_house_more_llt: // 更多
			// TODO
//			PopViewHelper.showSecondHandHouseMorePopWindow(this, moreType,
//					sorts, directions, estateLabels, lltMore);
//			PopViewHelper.showNewHouseMorePopWindow(this, moreType,
//					houseUsages, estateLabels,
//					estateStatus, lltMore, onNewHouseMoreConditionListener);
			
			if (houseSourceParams != null) {
				if (houseSourceParams.size() > 4) {
					PopViewHelper.showPickHouseSourceParamMorePopWindow(this, lineAnchor, 4, houseSourceParams, this);
				} else {
					PopViewHelper.showPickHouseSourceParamPopWindow(this, lineAnchor, 3, houseSourceParams, this);
				}
				
			}
			break;
		case R.id.act_new_house_locate:  // 定位
			locate();
			break;
		case R.id.act_new_house_subway:  // 地铁
			searchNearby("地铁");
			break;
		case R.id.act_new_house_nearby:  // 周边
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
			break;
		case R.id.act_new_house_clear_key_wrods:
			keyWords = "";
			edtKeyWords.setText("");
			break;
			
		case R.id.tv_new_house_sort:   // 排序
			//  TODO  排序
			
			break;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.act_new_house_list_map:
			if (isChecked) { // 列表
				mapViewFlt.setVisibility(View.GONE);
				pullView.setVisibility(View.VISIBLE);
				tvSort.setVisibility(View.VISIBLE);
			} else { // 地图
				mapViewFlt.setVisibility(View.VISIBLE);
				pullView.setVisibility(View.GONE);
				tvSort.setVisibility(View.GONE);
			}
			break;
		}

	}



	@Override
	protected void onPause() {
		super.onPause();
		// activity 暂停时同时暂停地图控件
		mapView.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// activity 恢复时同时恢复地图控件
		mapView.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// activity 销毁时同时销毁地图控件
		mapView.onDestroy();
	}
	
	private ArrayList<MapFindHouseBean> mapAreas = new ArrayList<MapFindHouseBean>();

	private ArrayList<SearchHouseItemBean> estates = new ArrayList<SearchHouseItemBean>();

	/**
	 * 获取某大区域的子区域列表， 获取后在地图上以圆形图标显示  已检查
	 */
	private void getMapFindHouseDataArea() {
		
		String id = null;
		if (houseSourceParams != null) {
			for (HouseSourceParamBean houseSourceParamBean : houseSourceParams) {
				if ("区域".equals(houseSourceParamBean.getName())) {
					ArrayList<NameValueBean> values = houseSourceParamBean.getValues();
					for (NameValueBean nameValueBean : values) {
						if (nameValueBean.isSelected()) {
							id = nameValueBean.getValue();
							break;
						}
					}
					break;
				}
			}
		}
		if (TextUtils.isEmpty(id)) {
			CityBean cityBean = ContentUtils.getCityBean(this);
			if (cityBean == null) {
				return;
			} else {
				id = cityBean.getSiteId();
			}
		} else {
            getMapFindHouseEstate();
            return;
        }
		
		if (TextUtils.isEmpty(id)) {
			return;
		}
		
//		CityBean cityBean = ContentUtils.getCityBean(this);
//		if (cityBean == null) {
//			return;
//		}
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		//  默认传0，0代表出售，1代表出租
		actionImpl.getMapFindHouseData(0, id,
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
		if (mapAreas != null && mapAreas.size() > 0) {
			baiduMap.clear();
			View viewAreaPoint = View.inflate(this,
					R.layout.view_map_point_area, null);
			TextView tvEstate = (TextView) viewAreaPoint
					.findViewById(R.id.view_point_title);
			TextView tvNum = (TextView) viewAreaPoint
					.findViewById(R.id.view_point_num);
			for (MapFindHouseBean area : mapAreas) {
				
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
	 * 获取子区域楼盘数据(调用楼盘搜索列表接口)
	 */
	private void getMapFindHouseEstate() {
		
		HashMap<String, String> requestParams = new HashMap<String, String>();
		if (houseSourceParams != null) {
			for (HouseSourceParamBean para : houseSourceParams) {
				ArrayList<NameValueBean> nameValues = para.getValues();
				for (NameValueBean nameValueBean : nameValues) {
					if (nameValueBean.isSelected()) {
						requestParams.put(para.getFiled(), nameValueBean.getValue());
						break;
					}
				}
			}
		}
		
		keyWords = edtKeyWords.getText().toString();
		
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.getSearchHouseList(requestParams, keyWords,
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
			View viewAreaPoint = View.inflate(this,
					R.layout.view_map_point_estate, null);
			TextView tvArea = (TextView) viewAreaPoint
					.findViewById(R.id.view_point_estate_title);
			TextView tvPrice = (TextView) viewAreaPoint
					.findViewById(R.id.view_point_estate_price);
			for (SearchHouseItemBean estate : estates) {
				
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
			baiduMap.setMapStatus(u);
		}
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
		addViewContent.setDrawingCacheEnabled(false);
		return bitmap;
	}
	
	private void locate() {
		final LocationService locationService = LocationService
				.getInstance(this);
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
			Toast.makeText(this, strInfo, Toast.LENGTH_LONG).show();
		}
	}
	
	private void searchNearby(String keyWords) {
		if (curLatLng != null) {
			mPoiSearch.searchNearby(new PoiNearbySearchOption()
					.location(curLatLng).keyword(keyWords).pageNum(10)
					.radius(10000));
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

	private void getHouseSourceParam() {
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.getHouseSourceParameter(new ResultHandlerCallback() {
			
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
				// TODO getHouseSourceParam
//				Log.i("--->", "getHouseSourceParam result: " + result.getData());
				houseSourceParams = (ArrayList<HouseSourceParamBean>) JSON.parseArray(result.getData(), HouseSourceParamBean.class);
				if (houseSourceParams != null) {
					
					Iterator<HouseSourceParamBean> iterator = houseSourceParams.iterator();
					while (iterator.hasNext()) {
						HouseSourceParamBean houseSourceParamBean = iterator.next();
						if ("租价".equals(houseSourceParamBean.getName())) {
							iterator.remove();
						} else {
							ArrayList<NameValueBean> values = houseSourceParamBean.getValues();
							
							if (values != null && values.size() > 0) {
								NameValueBean nameValueBean = values.get(0);
								if ("不限".equals(nameValueBean.getName())) {
									nameValueBean.setSelected(true);
								}
							}
						}
						
					}
					
					int size = houseSourceParams.size();
					if (size == 0) {
						lltNewHouseSourceParam.setVisibility(View.GONE);
					} else if (size == 1) {
						tvArea.setText(houseSourceParams.get(0).getName());
						lltArea.setVisibility(View.VISIBLE);
						lltTotalPrice.setVisibility(View.GONE);
						lltHouseType.setVisibility(View.GONE);
						lltMore.setVisibility(View.GONE);
						
						lineOne.setVisibility(View.GONE);
						lineTwo.setVisibility(View.GONE);
						lineThree.setVisibility(View.GONE);
					} else if (size == 2) {
						tvArea.setText(houseSourceParams.get(0).getName());
						tvTotalPrice.setText(houseSourceParams.get(1).getName());
						lltArea.setVisibility(View.VISIBLE);
						lltTotalPrice.setVisibility(View.VISIBLE);
						lltHouseType.setVisibility(View.GONE);
						lltMore.setVisibility(View.GONE);
						
						lineTwo.setVisibility(View.GONE);
						lineThree.setVisibility(View.GONE);
					} else if (size == 3) {
						tvArea.setText(houseSourceParams.get(0).getName());
						tvTotalPrice.setText(houseSourceParams.get(1).getName());
						tvHouseType.setText(houseSourceParams.get(2).getName());
						lltArea.setVisibility(View.VISIBLE);
						lltTotalPrice.setVisibility(View.VISIBLE);
						lltHouseType.setVisibility(View.VISIBLE);
						lltMore.setVisibility(View.GONE);
						
						lineThree.setVisibility(View.GONE);
						
					} else if (size == 4) {
						tvArea.setText(houseSourceParams.get(0).getName());
						tvTotalPrice.setText(houseSourceParams.get(1).getName());
						tvHouseType.setText(houseSourceParams.get(2).getName());
						tvMore.setText(houseSourceParams.get(3).getName());
						lltArea.setVisibility(View.VISIBLE);
						lltTotalPrice.setVisibility(View.VISIBLE);
						lltHouseType.setVisibility(View.VISIBLE);
						lltMore.setVisibility(View.VISIBLE);
					} else if (size > 4) {
						lltArea.setVisibility(View.VISIBLE);
						lltTotalPrice.setVisibility(View.VISIBLE);
						lltHouseType.setVisibility(View.VISIBLE);
						lltMore.setVisibility(View.VISIBLE);
						tvArea.setText(houseSourceParams.get(0).getName());
						tvTotalPrice.setText(houseSourceParams.get(1).getName());
						tvHouseType.setText(houseSourceParams.get(2).getName());
					}
				}
			}
		});
	}
	
	private void getHouseSourceSort() {
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.getHouseSourceSort(new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				// TODO getHouseSourceSort
//				Log.i("--->", "getHouseSourceSort result: " + result.getData());
				sortParamList = (ArrayList<FieldNameValueBean>) JSON.parseArray(result.getData(), FieldNameValueBean.class);
				if (sortParamList != null && sortParamList.size() > 0) {
					
					
					sortTypeBean = sortParamList.get(0);
					sortTypeBean.setSelected(true);
				}
			}
		});
	}

	@Override
	public void onHouseSourceParamPick(int fieldPosition,
			NameValueBean houseSourceParam) {
		// TODO Auto-generated method stub
		keyWords = edtKeyWords.getText().toString();
		getNewHouseList(cityId, keyWords, 10, true);
		switch (fieldPosition) {
		case 0:  // 第一个
			if (houseSourceParam != null && "区域".equals(houseSourceParam.getName())) {
				getMapFindHouseDataArea();
			}
			break;
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;
		}
	}

	@Override
	public void onHouseSourceSortTypeClick(FieldNameValueBean sortType) {
		// TODO 排序
		sortTypeBean = sortType;
	    keyWords = edtKeyWords.getText().toString();
		getNewHouseList(cityId, keyWords, 10, true);
		
	}
	
	@Override
	protected int getStatusBarTintResource() {
		// TODO Auto-generated method stub
		return R.color.white;
	}
	
}
