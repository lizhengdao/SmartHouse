package cn.com.zzwfang.activity;

import java.util.ArrayList;

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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import cn.com.zzwfang.R;
import cn.com.zzwfang.adapter.HomeRecommendHouseAdapter;
import cn.com.zzwfang.adapter.SecondHandHouseAdapter;
import cn.com.zzwfang.bean.CityBean;
import cn.com.zzwfang.bean.MapFindHouseBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.bean.SearchHouseItemBean;
import cn.com.zzwfang.bean.SecondHandHouseBean;
import cn.com.zzwfang.bean.TextValueBean;
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
import cn.com.zzwfang.view.helper.PopViewHelper.OnConditionSelectListener;
import cn.com.zzwfang.view.helper.PopViewHelper.OnSecondHandHouseMoreConditionListener;

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
 * 二手房列表 获取二手房列表页， 输入城市ID、区域ID、朝向、面积、标签、 楼龄（0：不限 1：5年内 2:5-10年 3:10-20年
 * 4:20年以上）、 楼层（0：不限 1：低楼层 2：中楼层 3：高楼层）、 房源编号、 排序（0：默认 1：总价低到高 2：总价高到低 3：面积小到大
 * 4：面积大到小）、 分页大小、页码. 条件： 朝向 Direction 面积范围 标签 楼龄
 * 传1（5年以内），2（5-10），3（10-20），4（大于20） 楼层 传1（低楼层）2（中楼层）3（高楼层） 排序
 * 1（按价格升）、2（按价格降）、3(按面积升)、4（按面积降） 价格 户型
 * 
 * @author lzd
 * 
 */
public class SecondHandHouseActivity extends BaseActivity implements
		OnClickListener, OnHeaderRefreshListener, OnFooterLoadListener,
		OnCheckedChangeListener, OnItemClickListener,
		OnGetPoiSearchResultListener {

	private int MODE_LIST = 1;

	private int MODE_MAP = 2;

	/**
	 * 显示列表还是地图
	 */
	private int mode = MODE_LIST;
	public static final String INTENT_KEYWORDS = "second_hand_house_key_words";

	public static final String INTENT_PRO_NUM = "intent_pro_num";

	private TextView tvBack, tvArea, tvTotalPrice, tvHouseType;
	private EditText edtKeyWords;
	private CheckBox cbxListAndMap;
	private MapView mapView;
	private BaiduMap baiduMap;
	private FrameLayout mapViewFlt;
	private AutoDrawableTextView autoTvLocate, autoTvSubway, autoTvNearby;

	private LinearLayout lltArea, lltTotalPrice, lltHouseType, lltMore;
	private AbPullToRefreshView pullView;
	private ListView lstSecondHandHouseView;
	private SecondHandHouseAdapter adapter;

	private PoiSearch mPoiSearch = null;
	private LatLng curLatLng;

	private String cityId = "";
	private ArrayList<SecondHandHouseBean> secondHandHouses = new ArrayList<SecondHandHouseBean>();

	public static final String SalePriceRange = "SalePriceRange";
	public static final String HouseType = "HouseType";
	public static final String PrpUsage = "PrpUsage";
	public static final String EstateLabel = "SecondLabel"; // SecondLabel
															// EstateLabel
	public static final String EstateStatus = "EstateStatus";
	public static final String FloorRange = "FloorRange";
	public static final String RentPriceRange = "RentPriceRange";
	public static final String Direction = "Direction";
	public static final String Sort = "Sort";

	// 区域
	private ArrayList<TextValueBean> areas = new ArrayList<TextValueBean>();
	// 总价
	private ArrayList<TextValueBean> salePriceRanges = new ArrayList<TextValueBean>();
	// 户型
	private ArrayList<TextValueBean> houseTypes = new ArrayList<TextValueBean>();
	// 排序
	private ArrayList<TextValueBean> sorts = new ArrayList<TextValueBean>();
	// 朝向
	private ArrayList<TextValueBean> directions = new ArrayList<TextValueBean>();
	// 面积
	private ArrayList<TextValueBean> squares = new ArrayList<TextValueBean>();
	// 特色标签
	private ArrayList<TextValueBean> estateLabels = new ArrayList<TextValueBean>();
	// 楼龄
	private ArrayList<TextValueBean> buildingAges = new ArrayList<TextValueBean>();
	// 楼层范围
	private ArrayList<TextValueBean> floorRanges = new ArrayList<TextValueBean>();

	// 物业类型
	// private ArrayList<TextValueBean> prpUsages = new
	// ArrayList<TextValueBean>();

	// 售卖状态
	private ArrayList<TextValueBean> estateStatus = new ArrayList<TextValueBean>();

	// 租价范围
	private ArrayList<TextValueBean> rentPriceRanges = new ArrayList<TextValueBean>();

	private TextValueBean areaCondition; // 区域
	private TextValueBean totalPriceCondition; // 总价
	private TextValueBean houseTypeCondition; // 房型

	// private TextValueBean sortCondition; // 排序
	// private TextValueBean directionCondition; // 朝向
	private TextValueBean squareCondition; // 面积
	private TextValueBean labelCondition; // 标签

	// 区域选择监听
	private OnConditionSelectListener onAreaSelectListener;
	// 总价选择监听
	private OnConditionSelectListener onTotalPriceSelectListener;
	// 房型选择监听
	private OnConditionSelectListener onHouseTypeSelectListener;
	// 更多
	private ArrayList<String> moreType = new ArrayList<String>();
	private OnSecondHandHouseMoreConditionListener onSecondHandHouseMoreConditionListener;

	private String buildYear, floor, proNum, sort, direction;
	private int pageIndex = 1;
	private int pageTotal = 0;
	private String key;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.act_second_hand_house);
		cityId = getIntent().getStringExtra(
				HomeRecommendHouseAdapter.INTENT_CITY_ID);
		key = getIntent().getStringExtra(INTENT_KEYWORDS);
		proNum = getIntent().getStringExtra(INTENT_PRO_NUM);
		if (TextUtils.isEmpty(cityId)) {
			CityBean cityBean = ContentUtils.getCityBean(this);
			cityId = cityBean.getSiteId();
		}

		initView();
		setListener();
		initData();
	}

	private void initView() {
		tvBack = (TextView) findViewById(R.id.act_second_hand_house_back);
		edtKeyWords = (EditText) findViewById(R.id.act_second_hand_house_key_words);
		cbxListAndMap = (CheckBox) findViewById(R.id.act_second_hand_house_list_map);
		pullView = (AbPullToRefreshView) findViewById(R.id.pull_second_hand_house);
		lstSecondHandHouseView = (ListView) findViewById(R.id.lst_second_hand_house);
		mapView = (MapView) findViewById(R.id.act_second_hand_house_map);
		mapViewFlt = (FrameLayout) findViewById(R.id.act_second_hand_house_map_flt);
		tvArea = (TextView) findViewById(R.id.act_second_hand_house_area_tv);
		tvTotalPrice = (TextView) findViewById(R.id.act_second_hand_house_total_price_tv);
		tvHouseType = (TextView) findViewById(R.id.act_second_hand_house_type_tv);

		lltArea = (LinearLayout) findViewById(R.id.act_second_hand_house_area_llt);
		lltTotalPrice = (LinearLayout) findViewById(R.id.act_second_hand_house_total_price_llt);
		lltHouseType = (LinearLayout) findViewById(R.id.act_second_hand_house_type_llt);
		lltMore = (LinearLayout) findViewById(R.id.act_second_hand_house_more_llt);

		autoTvLocate = (AutoDrawableTextView) findViewById(R.id.act_second_hand_house_locate);
		autoTvSubway = (AutoDrawableTextView) findViewById(R.id.act_second_hand_house_subway);
		autoTvNearby = (AutoDrawableTextView) findViewById(R.id.act_second_hand_house_nearby);

		if (!TextUtils.isEmpty(key)) {
			edtKeyWords.setText(key);
		}

		baiduMap = mapView.getMap();
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
	}

	private void setListener() {
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

		adapter = new SecondHandHouseAdapter(this, secondHandHouses);
		lstSecondHandHouseView.setAdapter(adapter);
		lstSecondHandHouseView.setOnItemClickListener(this);

		edtKeyWords.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					key = edtKeyWords.getText().toString();
					getSecondHandHouseList(cityId, areaCondition, direction,
							squareCondition, labelCondition,
							totalPriceCondition, houseTypeCondition, buildYear,
							floor, proNum, sort, key, 10, true);
					return true;
				}
				return false;
			}
		});

		onTotalPriceSelectListener = new OnConditionSelectListener() {

			@Override
			public void onConditionSelect(TextValueBean txtValueBean) {
				totalPriceCondition = txtValueBean;
				tvTotalPrice.setText(txtValueBean.getText());
				key = edtKeyWords.getText().toString();
				getSecondHandHouseList(cityId, areaCondition, direction,
						squareCondition, labelCondition, totalPriceCondition,
						houseTypeCondition, buildYear, floor, proNum, sort,
						key, 10, true);
			}
		};

		onHouseTypeSelectListener = new OnConditionSelectListener() {

			@Override
			public void onConditionSelect(TextValueBean txtValueBean) {
				houseTypeCondition = txtValueBean;
				tvHouseType.setText(txtValueBean.getText());
				key = edtKeyWords.getText().toString();
				getSecondHandHouseList(cityId, areaCondition, direction,
						squareCondition, labelCondition, totalPriceCondition,
						houseTypeCondition, buildYear, floor, proNum, sort,
						key, 10, true);
			}
		};

		onAreaSelectListener = new OnConditionSelectListener() {

			@Override
			public void onConditionSelect(TextValueBean txtValueBean) {

				if (areaCondition == null
						|| areaCondition.getValue() == null
						|| !areaCondition.getValue().equals(
								txtValueBean.getValue())) {
					areaCondition = txtValueBean;
					tvArea.setText(txtValueBean.getText());

					key = edtKeyWords.getText().toString();
					getSecondHandHouseList(cityId, areaCondition, direction,
							squareCondition, labelCondition,
							totalPriceCondition, houseTypeCondition, buildYear,
							floor, proNum, sort, key, 10, true);

					getMapFindHouseDataArea();
				}
			}
		};

		onSecondHandHouseMoreConditionListener = new OnSecondHandHouseMoreConditionListener() {

			@Override
			public void onSecondHandHouseMoreConditon(
					TextValueBean sortConditionData,
					TextValueBean directionConditionData,
					TextValueBean squareConditionData,
					TextValueBean labelConditionData,
					TextValueBean buildingAgeConditionData,
					TextValueBean floorRangeConditionData) {
				// sortCondition = sortConditionData;
				if (sortConditionData != null) {
					sort = sortConditionData.getValue();
				}
				// directionCondition = directionConditionData;
				if (directionConditionData != null) {
					direction = directionConditionData.getValue();
				}
				squareCondition = squareConditionData;
				labelCondition = labelConditionData;
				if (buildingAgeConditionData != null) {
					buildYear = buildingAgeConditionData.getValue();
				}
				if (floorRangeConditionData != null) {
					floor = floorRangeConditionData.getValue();
				}

				key = edtKeyWords.getText().toString();
				getSecondHandHouseList(cityId, areaCondition, direction,
						squareCondition, labelCondition, totalPriceCondition,
						houseTypeCondition, buildYear, floor, proNum, sort,
						key, 10, true);
			}
		};

		baiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(Marker marker) {

				Bundle bundle = marker.getExtraInfo();
				Object data = bundle.get("area");
				if (data instanceof MapFindHouseBean) {
					MapFindHouseBean area = (MapFindHouseBean) data;
					if (area != null) {
						// 点击了某小区域区域 加载该区域楼盘
						TextValueBean textValueBeanArea = new TextValueBean();
						textValueBeanArea.setValue(area.getId());
						areaCondition = textValueBeanArea;
						getMapFindHouseEstate();
					}
				} else if (data instanceof SearchHouseItemBean) {
					// 点击某一楼盘，加载该楼盘 二手房列表页
					SearchHouseItemBean estate = (SearchHouseItemBean) data;
					key = estate.getName();
					edtKeyWords.setText(key);
					if (TextUtils.isEmpty(cityId)) {
						CityBean cityBean = ContentUtils
								.getCityBean(SecondHandHouseActivity.this);
						cityId = cityBean.getSiteId();
					}
					getSecondHandHouseList(cityId, areaCondition, direction,
							squareCondition, labelCondition,
							totalPriceCondition, houseTypeCondition, buildYear,
							floor, proNum, sort, key, 10, true);
					cbxListAndMap.setChecked(true);

				}

				return true;
			}
		});
	}

	/**
	 * 二手房列表 条件： 朝向 Direction 面积范围 标签 楼龄 传1（5年以内），2（5-10），3（10-20），4（大于20） 楼层
	 * 传1（低楼层）2（中楼层）3（高楼层） 排序 1（按价格升）、2（按价格降）、3(按面积升)、4（按面积降） 价格 户型
	 * 
	 * @author lzd
	 * 
	 */

	// 排序
	// 朝向
	// 面积范围
	// 标签
	// 楼龄
	// 楼层

	private void initData() {
		moreType.add("排序");
		initSortsData();
		moreType.add("朝向");
		moreType.add("面积");
		moreType.add("标签");
		moreType.add("楼龄");
		initBuildingAgesData();
		moreType.add("楼层");
		initFloorRangeData();

		getConditionList(SalePriceRange); // 总价
		getConditionList(HouseType); // 户型
		// getConditionList(PrpUsage); // 物业类型
		getConditionList(EstateLabel); // 特色标签
		getConditionList(EstateStatus); // 售卖状态
		// getConditionList(FloorRange); // 楼层范围 现在写死了的，妈的，到底哪个对
		getConditionList(RentPriceRange); // 租价范围
		getConditionList(Direction); // 朝向
		getAreaList();
		getSecondHandHouseList(cityId, areaCondition, direction,
				squareCondition, labelCondition, totalPriceCondition,
				houseTypeCondition, buildYear, floor, proNum, sort, key, 10,
				true);
	}

	/**
	 * 二手房列表 条件： 朝向 Direction 面积范围 标签 楼龄 传1（5年以内），2（5-10），3（10-20），4（大于20） 楼层
	 * 传1（低楼层）2（中楼层）3（高楼层） 排序 1（按价格升）、2（按价格降）、3(按面积升)、4（按面积降） 价格 户型
	 * 
	 * @author lzd
	 * 
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_second_hand_house_back: // 返回
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(getWindow().getDecorView()
					.getWindowToken(), 0);
			finish();
			break;
		case R.id.act_second_hand_house_area_llt: // 区域
			PopViewHelper.showSelectAreaPopWindow(this, lltArea, areas,
					onAreaSelectListener);
			break;
		case R.id.act_second_hand_house_total_price_llt: // 总价
			PopViewHelper.showSelectTotalPricePopWindow(this, lltTotalPrice,
					salePriceRanges, onTotalPriceSelectListener);
			break;
		case R.id.act_second_hand_house_type_llt: // 房型
			PopViewHelper.showSelectHouseTypePopWindow(this, lltHouseType,
					houseTypes, onHouseTypeSelectListener);
			break;
		case R.id.act_second_hand_house_more_llt: // 更多
			// TODO
			PopViewHelper.showSecondHandHouseMorePopWindow(this, moreType,
					sorts, directions, squares, estateLabels, buildingAges,
					floorRanges, lltMore,
					onSecondHandHouseMoreConditionListener);
			break;
		case R.id.act_second_hand_house_locate: // 定位
			locate();
			break;
		case R.id.act_second_hand_house_subway: // 地铁
			searchNearby("地铁");
			break;
		case R.id.act_second_hand_house_nearby: // 周边
			if (curLatLng != null) {
				Jumper.newJumper()
						.setAheadInAnimation(R.anim.activity_push_in_right)
						.setAheadOutAnimation(R.anim.activity_alpha_out)
						.setBackInAnimation(R.anim.activity_alpha_in)
						.setBackOutAnimation(R.anim.activity_push_out_right)
						.putDouble(NearbyDetailActivity.INTENT_LAT,
								curLatLng.latitude)
						.putDouble(NearbyDetailActivity.INTENT_LNG,
								curLatLng.longitude)
						.jump(this, NearbyDetailActivity.class);
			}
			break;
		}
	}

	@Override
	public void onHeaderRefresh(AbPullToRefreshView view) { // 下拉刷新
		String keyWords = edtKeyWords.getText().toString();
		getSecondHandHouseList(cityId, areaCondition, direction,
				squareCondition, labelCondition, totalPriceCondition,
				houseTypeCondition, buildYear, floor, proNum, sort, keyWords,
				10, true);
	}

	@Override
	public void onFooterLoad(AbPullToRefreshView view) { // 上拉加载更多

		if (pageIndex > pageTotal) {
			pullView.onFooterLoadFinish();
			return;
		}
		key = edtKeyWords.getText().toString();
		getSecondHandHouseList(cityId, areaCondition, direction,
				squareCondition, labelCondition, totalPriceCondition,
				houseTypeCondition, buildYear, floor, proNum, sort, key, 10,
				false);

	}

	private void getSecondHandHouseList(String cityId,
			TextValueBean areaCondition, String direction,
			TextValueBean squareCondition, TextValueBean labelCondition,
			TextValueBean priceCondition, TextValueBean roomTypeCondition,
			String buildYear, String floor, String proNum, String sort,
			String keyWords, int pageSize, final boolean isRefresh) {
		if (isRefresh) {
			pageIndex = 1;
		}
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.getSecondHandHouseList(cityId, areaCondition, direction,
				squareCondition, labelCondition, priceCondition,
				roomTypeCondition, buildYear, floor, proNum, sort, keyWords,
				pageSize, pageIndex, new ResultHandlerCallback() {

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
						pageTotal = (int) Math
								.ceil(((double) total / (double) 10));

						ArrayList<SecondHandHouseBean> temp = (ArrayList<SecondHandHouseBean>) JSON
								.parseArray(result.getData(),
										SecondHandHouseBean.class);
						if (isRefresh) {
							secondHandHouses.clear();
						}
						secondHandHouses.addAll(temp);
						if (temp != null) {
							pageIndex++;
						}
						adapter.notifyDataSetChanged();
						if (isRefresh) {
							pullView.onHeaderRefreshFinish();
						} else {
							pullView.onFooterLoadFinish();
						}
					}
				});
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		SecondHandHouseBean temp = secondHandHouses.get(position);
		Jumper.newJumper()
				.setAheadInAnimation(R.anim.activity_push_in_right)
				.setAheadOutAnimation(R.anim.activity_alpha_out)
				.setBackInAnimation(R.anim.activity_alpha_in)
				.setBackOutAnimation(R.anim.activity_push_out_right)
				.putString(
						SecondHandHouseDetailActivity.INTENT_HOUSE_SOURCE_ID,
						temp.getId())
				.jump(this, SecondHandHouseDetailActivity.class);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.act_second_hand_house_list_map:
			if (isChecked) { // 列表
				mode = MODE_LIST;
				mapViewFlt.setVisibility(View.GONE);
				pullView.setVisibility(View.VISIBLE);
			} else { // 地图
				mode = MODE_MAP;
				mapViewFlt.setVisibility(View.VISIBLE);
				pullView.setVisibility(View.GONE);
			}
			break;
		}
	}

	public void getConditionList(final String conditionName) {
		ActionImpl actionImpl = ActionImpl.newInstance(this);
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
						}
						// else if (PrpUsage.equals(conditionName)) {
						// prpUsages.addAll(temp);
						// }
						else if (EstateLabel.equals(conditionName)) {
							estateLabels.addAll(temp);
						} else if (EstateStatus.equals(conditionName)) {
							estateStatus.addAll(temp);
						} else if (FloorRange.equals(conditionName)) {
							floorRanges.addAll(temp);
						} else if (RentPriceRange.equals(conditionName)) {
							rentPriceRanges.addAll(temp);
						} else if (Direction.equals(conditionName)) {
							directions.addAll(temp);
						}
					}
				});
	}

	private void getAreaList() {
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.getAreaList(cityId, new ResultHandlerCallback() {

			@Override
			public void rc999(RequestEntity entity, Result result) {

			}

			@Override
			public void rc3001(RequestEntity entity, Result result) {

			}

			@Override
			public void rc0(RequestEntity entity, Result result) {
				// TODO Auto-generated method stub
				ArrayList<TextValueBean> temp = (ArrayList<TextValueBean>) JSON
						.parseArray(result.getData(), TextValueBean.class);
				areas.addAll(temp);
			}
		});
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
	 * 获取某大区域的子区域列表， 获取后在地图上以圆形图标显示 已检查
	 */
	private void getMapFindHouseDataArea() {
		CityBean cityBean = ContentUtils.getCityBean(this);
		if (cityBean == null) {
			return;
		}
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		// 默认传0，0代表出售，1代表出租
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
	 * 在地图上以圆形展示子区域 已检查
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
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.getSearchHouseList(areaCondition, totalPriceCondition, null,
				null, labelCondition, null, null, new ResultHandlerCallback() {

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

	/**
	 * 排序 1（按价格升）、2（按价格降）、3(按面积升)、4（按面积降）
	 */
	private void initSortsData() {
		TextValueBean tv1 = new TextValueBean();
		tv1.setText("不限");
		tv1.setValue("");
		tv1.setSelected(true);

		TextValueBean tv2 = new TextValueBean();
		tv2.setText("价格升");
		tv2.setValue("1");

		TextValueBean tv3 = new TextValueBean();
		tv3.setText("价格降");
		tv3.setValue("2");

		TextValueBean tv4 = new TextValueBean();
		tv4.setText("面积升");
		tv4.setValue("3");

		TextValueBean tv5 = new TextValueBean();
		tv5.setText("面积降");
		tv5.setValue("4");

		sorts.add(tv1);
		sorts.add(tv2);
		sorts.add(tv3);
		sorts.add(tv4);
		sorts.add(tv5);
	}

	/**
	 * 楼层 传1（低楼层）2（中楼层）3（高楼层）
	 */
	private void initFloorRangeData() {
		TextValueBean tv1 = new TextValueBean();
		tv1.setText("不限");
		tv1.setValue("");
		tv1.setSelected(true);

		TextValueBean tv2 = new TextValueBean();
		tv2.setText("低楼层");
		tv2.setValue("1");

		TextValueBean tv3 = new TextValueBean();
		tv3.setText("中楼层");
		tv3.setValue("2");

		TextValueBean tv4 = new TextValueBean();
		tv4.setText("高楼层");
		tv4.setValue("3");

		floorRanges.add(tv1);
		floorRanges.add(tv2);
		floorRanges.add(tv3);
		floorRanges.add(tv4);
	}

	/**
	 * 楼龄 传1（5年以内），2（5-10），3（10-20），4（大于20）
	 */
	private void initBuildingAgesData() {
		TextValueBean tv1 = new TextValueBean();
		tv1.setText("不限");
		tv1.setValue("");
		tv1.setSelected(true);

		TextValueBean tv2 = new TextValueBean();
		tv2.setText("5年以内");
		tv2.setValue("1");

		TextValueBean tv3 = new TextValueBean();
		tv3.setText("5-10年");
		tv3.setValue("2");

		TextValueBean tv4 = new TextValueBean();
		tv4.setText("10-20年");
		tv4.setValue("3");

		TextValueBean tv5 = new TextValueBean();
		tv5.setText("大于20年");
		tv5.setValue("4");

		buildingAges.add(tv1);
		buildingAges.add(tv2);
		buildingAges.add(tv3);
		buildingAges.add(tv4);
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
}
