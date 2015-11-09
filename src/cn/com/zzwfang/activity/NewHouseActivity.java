package cn.com.zzwfang.activity;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;
import com.baidu.mapapi.map.MapView;

import cn.com.zzwfang.R;
import cn.com.zzwfang.adapter.HomeRecommendHouseAdapter;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.bean.TextValueBean;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.pullview.AbPullToRefreshView;
import cn.com.zzwfang.pullview.AbPullToRefreshView.OnFooterLoadListener;
import cn.com.zzwfang.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import cn.com.zzwfang.util.ToastUtils;
import cn.com.zzwfang.view.helper.PopViewHelper;
import cn.com.zzwfang.view.helper.PopViewHelper.OnConditionSelectListener;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class NewHouseActivity extends BaseActivity implements OnClickListener,
OnHeaderRefreshListener, OnFooterLoadListener, OnCheckedChangeListener {

	private TextView tvBack;
	private CheckBox cbxListAndMap;
	private MapView mapView;
	private FrameLayout mapViewFlt;
	private LinearLayout lltArea, lltTotalPrice, lltHouseType, lltMore;

	private AbPullToRefreshView pullView;
	private ListView lstSecondHandHouseView;
	
	private String cityId = "";
	
	public static final String SalePriceRange = "SalePriceRange";
	public static final String HouseType = "HouseType";
	public static final String PrpUsage = "PrpUsage";
	public static final String EstateLabel = "EstateLabel";
	public static final String EstateStatus = "EstateStatus";
	public static final String FloorRange = "FloorRange";
	public static final String RentPriceRange = "RentPriceRange";
	public static final String Direction ="Direction";
	public static final String Sort = "Sort";
	
	/**
	 * 区域
	 */
	private ArrayList<TextValueBean> areas = new ArrayList<TextValueBean>();
	/**
	 * 总价
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
	 * 楼层范围
	 */
	private ArrayList<TextValueBean> floorRanges = new ArrayList<TextValueBean>();
	
	/**
	 * 租价范围
	 */
	private ArrayList<TextValueBean> rentPriceRanges = new ArrayList<TextValueBean>();
	
	/**
	 * 朝向
	 */
	private ArrayList<TextValueBean> directions = new ArrayList<TextValueBean>();
	
	/**
	 * 排序
	 */
	private ArrayList<TextValueBean> sorts = new ArrayList<TextValueBean>();
	
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
	
	private TextValueBean areaCondition;
	private TextValueBean totalPriceCondition;
	private TextValueBean squareCondition;
	private TextValueBean labelCondition;
	private TextValueBean roomTypeCondition;
	private String buildYear, floor, proNum, sort;
	private int pageIndex = 0;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.act_new_house);
		cityId = getIntent().getStringExtra(HomeRecommendHouseAdapter.INTENT_CITY_ID);
		initView();
		
		getConditionList(SalePriceRange);
		getConditionList(HouseType);
		getConditionList(PrpUsage);
		getConditionList(EstateLabel);
		getConditionList(EstateStatus);
		getConditionList(FloorRange);
		getConditionList(RentPriceRange);
		getConditionList(Direction);
		getConditionList(Sort);
	}
	
	private void initView() {
		tvBack = (TextView) findViewById(R.id.act_new_house_back);
		cbxListAndMap = (CheckBox) findViewById(R.id.act_new_house_list_map);
		pullView = (AbPullToRefreshView) findViewById(R.id.pull_new_house);
		lstSecondHandHouseView = (ListView) findViewById(R.id.lst_new_house);
		mapView = (MapView) findViewById(R.id.act_new_house_map);
		mapViewFlt = (FrameLayout) findViewById(R.id.act_new_house_map_flt);
		
		lltArea = (LinearLayout) findViewById(R.id.act_new_house_area_llt);
		lltTotalPrice = (LinearLayout) findViewById(R.id.act_new_house_total_price_llt);
		lltHouseType = (LinearLayout) findViewById(R.id.act_new_house_type_llt);
		lltMore = (LinearLayout) findViewById(R.id.act_new_house_more_llt);
		
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
		getNewHouseList();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.act_new_house_back: // 返回
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
			finish();
			break;
		case R.id.act_new_house_area_llt:  // 区域
			PopViewHelper.showSelectAreaPopWindow(this, lltArea, areas, onAreaSelectListener);
			break;
		case R.id.act_new_house_total_price_llt:   //  总价
			PopViewHelper.showSelectTotalPricePopWindow(this, lltTotalPrice, salePriceRanges, onTotalPriceSelectListener);
			break;
		case R.id.act_new_house_type_llt:  //  房型
			PopViewHelper.showSelectHouseTypePopWindow(this, lltHouseType, houseTypes, onHouseTypeSelectListener);
			break;
		case R.id.act_new_house_more_llt:  //  更多
			PopViewHelper.showSecondHandHouseMorePopWindow(this, moreType, sorts, directions, estateLabels, lltMore);
			break;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.act_new_house_list_map:
			if (isChecked) {  // 列表
				mapViewFlt.setVisibility(View.GONE);
				pullView.setVisibility(View.VISIBLE);
			} else {  // 地图
				mapViewFlt.setVisibility(View.VISIBLE);
				pullView.setVisibility(View.GONE);
			}
			break;
		}
		
	}

	@Override
	public void onFooterLoad(AbPullToRefreshView view) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onHeaderRefresh(AbPullToRefreshView view) {
		// TODO Auto-generated method stub
		
	}
	
	private void getNewHouseList() {
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.getNewHouseList(cityId, "6000", 1, 1, 1, 1, "1", 10, 0, new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				ToastUtils.SHORT.toast(NewHouseActivity.this, "aaa");
			}
		});
	}
	
	public void getConditionList(final String conditionName) {
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.getConditionByName(conditionName, new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				ArrayList<TextValueBean> temp = (ArrayList<TextValueBean>) JSON.parseArray(result.getData(), TextValueBean.class);
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
				} else if (FloorRange.equals(conditionName)) {
					floorRanges.addAll(temp);
				} else if (RentPriceRange.equals(conditionName)) {
					rentPriceRanges.addAll(temp);
				} else if (Direction.equals(conditionName)) {
					directions.addAll(temp);
				} else if (Sort.equals(conditionName)) {
					sorts.addAll(temp);
				}
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
}
