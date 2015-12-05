package cn.com.zzwfang.activity;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.MarkerOptions.MarkerAnimateType;
import com.baidu.mapapi.model.LatLng;

import cn.com.zzwfang.R;
import cn.com.zzwfang.adapter.HomeRecommendHouseAdapter;
import cn.com.zzwfang.adapter.NewHouseAdapter;
import cn.com.zzwfang.bean.CityBean;
import cn.com.zzwfang.bean.MapFindHouseBean;
import cn.com.zzwfang.bean.NewHouseBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.bean.SearchHouseItemBean;
import cn.com.zzwfang.bean.TextValueBean;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.pullview.AbPullToRefreshView;
import cn.com.zzwfang.pullview.AbPullToRefreshView.OnFooterLoadListener;
import cn.com.zzwfang.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.util.Jumper;
import cn.com.zzwfang.view.helper.PopViewHelper;
import cn.com.zzwfang.view.helper.PopViewHelper.OnConditionSelectListener;
import cn.com.zzwfang.view.helper.PopViewHelper.OnNewHouseMoreConditionListener;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

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
		OnItemClickListener {
	
	public static final String INTENT_KEY_WORDS = "NewHouseActivity.intent_key_words";

    private int MODE_LIST = 1;
	
	private int MODE_MAP = 2;
	
	/**
	 * 显示列表还是地图
	 */
	private int mode = MODE_LIST;
	private TextView tvBack, tvArea, tvTotalPrice, tvHouseType, tvMore;
	private EditText edtKeyWords;
	private CheckBox cbxListAndMap;
	private MapView mapView;
	private BaiduMap baiduMap;
	private FrameLayout mapViewFlt;
	private LinearLayout lltArea, lltTotalPrice, lltHouseType, lltMore;
	

	private AbPullToRefreshView pullView;
	private ListView lstNewHouseView;
	
	private ArrayList<NewHouseBean> newHouses = new ArrayList<NewHouseBean>();
	private NewHouseAdapter adapter;

	private String cityId = "";

	public static final String SalePriceRange = "SalePriceRange";
	public static final String HouseType = "HouseType";
	
	// TODO 这个 房屋用途 的字符串是多少要问问
	public static final String HouseUsage = "House"; // 房屋用途
	public static final String PrpUsage = "PrpUsage";
	public static final String EstateLabel = "EstateLabel";
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
	// 房屋用途
	private ArrayList<TextValueBean> houseUsages = new ArrayList<TextValueBean>();
	// 物业类型
//	private ArrayList<TextValueBean> prpUsages = new ArrayList<TextValueBean>();
	// 特色标签
	private ArrayList<TextValueBean> estateLabels = new ArrayList<TextValueBean>();
	// 售卖状态
	private ArrayList<TextValueBean> estateStatus = new ArrayList<TextValueBean>();
	// 楼层范围
//	private ArrayList<TextValueBean> floorRanges = new ArrayList<TextValueBean>();
    // 租价范围
//	private ArrayList<TextValueBean> rentPriceRanges = new ArrayList<TextValueBean>();
	//  朝向
//	private ArrayList<TextValueBean> directions = new ArrayList<TextValueBean>();
	// 排序
//	private ArrayList<TextValueBean> sorts = new ArrayList<TextValueBean>();

	// 区域监听
	private OnConditionSelectListener onAreaSelectListener;
	// 总价监听
	private OnConditionSelectListener onTotalPriceSelectListener;
	// 房型监听
	private OnConditionSelectListener onHouseTypeSelectListener;

	private ArrayList<String> moreType = new ArrayList<String>();
	
	private OnNewHouseMoreConditionListener onNewHouseMoreConditionListener;

	private TextValueBean areaCondition;
	private TextValueBean totalPriceCondition;
//	private TextValueBean squareCondition;
	private TextValueBean labelCondition;   //  特色标签
	private TextValueBean roomTypeCondition;  //  房屋类型
	private TextValueBean usageCondition;  // 用途
	private TextValueBean statusCondition;  // 售卖状态
	private String proNum;    //  buildYear, floor, sort
	private int pageIndex = 1;
	private int pageTotal = 0;
	private String keyWords;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.act_new_house);
		cityId = getIntent().getStringExtra(
				HomeRecommendHouseAdapter.INTENT_CITY_ID);
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

		lltArea = (LinearLayout) findViewById(R.id.act_new_house_area_llt);
		lltTotalPrice = (LinearLayout) findViewById(R.id.act_new_house_total_price_llt);
		lltHouseType = (LinearLayout) findViewById(R.id.act_new_house_type_llt);
		lltMore = (LinearLayout) findViewById(R.id.act_new_house_more_llt);
		
		tvArea = (TextView) findViewById(R.id.act_new_house_area_tv);
		tvTotalPrice = (TextView) findViewById(R.id.act_new_house_total_price_tv);
		tvHouseType = (TextView) findViewById(R.id.act_new_house_type_tv);
		tvMore = (TextView) findViewById(R.id.act_new_house_more_tv);
		
		baiduMap = mapView.getMap();
		adapter = new NewHouseAdapter(this, newHouses);
		lstNewHouseView.setAdapter(adapter);
		lstNewHouseView.setOnItemClickListener(this);

		mapView.showZoomControls(false);

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
		getNewHouseList(cityId, areaCondition, totalPriceCondition,
				roomTypeCondition, usageCondition, labelCondition,
				statusCondition, keyWords, 10, true);
	}

	private void setListener() {

		onTotalPriceSelectListener = new OnConditionSelectListener() {

			@Override
			public void onConditionSelect(TextValueBean txtValueBean) {
				totalPriceCondition = txtValueBean;
				tvTotalPrice.setText(txtValueBean.getText());
				keyWords = edtKeyWords.getText().toString();
				getNewHouseList(cityId, areaCondition,
						totalPriceCondition, roomTypeCondition,
						usageCondition, labelCondition,
						statusCondition, keyWords, 10,
						true);
			}
		};

		onHouseTypeSelectListener = new OnConditionSelectListener() {

			@Override
			public void onConditionSelect(TextValueBean txtValueBean) {
				roomTypeCondition = txtValueBean;
				tvHouseType.setText(txtValueBean.getText());
				keyWords = edtKeyWords.getText().toString();
				getNewHouseList(cityId, areaCondition,
						totalPriceCondition, roomTypeCondition,
						usageCondition, labelCondition,
						statusCondition, keyWords, 10,
						true);
			}
		};

		onAreaSelectListener = new OnConditionSelectListener() {

			@Override
			public void onConditionSelect(TextValueBean txtValueBean) {
				if (areaCondition == null || areaCondition.getValue() == null || !areaCondition.getValue().equals(txtValueBean.getValue())) {
					areaCondition = txtValueBean;
					tvArea.setText(txtValueBean.getText());
					getNewHouseList(cityId, areaCondition,
							totalPriceCondition, roomTypeCondition,
							usageCondition, labelCondition,
							statusCondition, keyWords, 10,
							true);
					
				    keyWords = edtKeyWords.getText().toString();
					getMapFindHouseDataArea();
				}
			}
		};
		
		onNewHouseMoreConditionListener = new OnNewHouseMoreConditionListener() {
			
			@Override
			public void onNewHouseMoreConditon(
					TextValueBean houseUsageConditionData,
					TextValueBean labelConditionData,
					TextValueBean saleStatusConditonData) {
				// TODO Auto-generated method stub
				usageCondition = houseUsageConditionData;
				labelCondition = labelConditionData;
				statusCondition = saleStatusConditonData;
				getNewHouseList(cityId, areaCondition,
						totalPriceCondition, roomTypeCondition,
						usageCondition, labelCondition,
						statusCondition, keyWords, 10,
						true);
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
						// 点击了某小区域区域   加载该区域楼盘
						TextValueBean textValueBeanArea = new TextValueBean();
						textValueBeanArea.setValue(area.getId());
						areaCondition = textValueBeanArea;
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
					
					getNewHouseList(cityId, areaCondition,
							totalPriceCondition, roomTypeCondition,
							usageCondition, labelCondition,
							statusCondition, keyWords, 10,
							true);
					cbxListAndMap.setChecked(true);
				}

				return true;
			}
		});
	}
	
	private void initData() {
		
//		moreType.add("排序");
//		moreType.add("朝向");
//		moreType.add("面积");
//		moreType.add("标签");
//		moreType.add("楼层");
//		moreType.add("房源编号");
		moreType.add("房屋用途");
		moreType.add("特色标签");
		moreType.add("售卖状态");
		getConditionList(SalePriceRange);  // 价格范围
		getConditionList(HouseType);  // 房型
		getConditionList(HouseUsage);  // 房屋用途
		
		getConditionList(PrpUsage);
		getConditionList(EstateLabel);
		getConditionList(EstateStatus);
//		getConditionList(FloorRange);
//		getConditionList(RentPriceRange);
//		getConditionList(Direction);
//		getConditionList(Sort);
		getAreaList();
		
	}
	
	@Override
	public void onHeaderRefresh(AbPullToRefreshView view) {
		getNewHouseList(cityId, areaCondition,
				totalPriceCondition, roomTypeCondition,
				usageCondition, labelCondition,
				statusCondition, keyWords, 10,
				true);
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
		getNewHouseList(cityId, areaCondition,
				totalPriceCondition, roomTypeCondition,
				usageCondition, labelCondition,
				statusCondition, keyWords, 10,
				false);
	}

	

	private void getNewHouseList(String cityId, TextValueBean areaCondition,
			TextValueBean priceCondition, TextValueBean roomTypeCondition,
			TextValueBean usageCondition, TextValueBean labelCondition,
			TextValueBean statusCondition, String keyWords, int pageSize,
			final boolean isRefresh) {

		if (isRefresh) {
			pageIndex = 0;
		}
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.getNewHouseList(cityId, areaCondition, priceCondition,
				roomTypeCondition, usageCondition, labelCondition,
				statusCondition, keyWords, 10, pageIndex, new ResultHandlerCallback() {

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
			PopViewHelper.showSelectAreaPopWindow(this, lltArea, areas,
					onAreaSelectListener);
			break;
		case R.id.act_new_house_total_price_llt: // 总价
			PopViewHelper.showSelectTotalPricePopWindow(this, lltTotalPrice,
					salePriceRanges, onTotalPriceSelectListener);
			break;
		case R.id.act_new_house_type_llt: // 房型
			PopViewHelper.showSelectHouseTypePopWindow(this, lltHouseType,
					houseTypes, onHouseTypeSelectListener);
			break;
		case R.id.act_new_house_more_llt: // 更多
			// TODO
//			PopViewHelper.showSecondHandHouseMorePopWindow(this, moreType,
//					sorts, directions, estateLabels, lltMore);
			PopViewHelper.showNewHouseMorePopWindow(this, moreType,
					houseUsages, estateLabels,
					estateStatus, lltMore, onNewHouseMoreConditionListener);
			break;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.act_new_house_list_map:
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
						} else if (HouseUsage.equals(conditionName)) {
							houseUsages.addAll(temp);
						}
//						else if (PrpUsage.equals(conditionName)) {
//							prpUsages.addAll(temp);
//						}
						else if (EstateLabel.equals(conditionName)) {
							estateLabels.addAll(temp);
						} else if (EstateStatus.equals(conditionName)) {
							estateStatus.addAll(temp);
						}
						
//						else if (FloorRange.equals(conditionName)) {
//							floorRanges.addAll(temp);
//						} else if (RentPriceRange.equals(conditionName)) {
//							rentPriceRanges.addAll(temp);
//						} else if (Direction.equals(conditionName)) {
//							directions.addAll(temp);
//						} else if (Sort.equals(conditionName)) {
//							sorts.addAll(temp);
//						}
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
				ArrayList<TextValueBean> temp = (ArrayList<TextValueBean>) JSON.parseArray(result.getData(), TextValueBean.class);
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
	 * 获取某大区域的子区域列表， 获取后在地图上以圆形图标显示  已检查
	 */
	private void getMapFindHouseDataArea() {
		CityBean cityBean = ContentUtils.getCityBean(this);
		if (cityBean == null) {
			return;
		}
		ActionImpl actionImpl = ActionImpl.newInstance(this);
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
				null, labelCondition, null, null,
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

		return bitmap;
	}

	
}
