package cn.com.zzwfang.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.activity.BaseActivity;
import cn.com.zzwfang.activity.MainActivity;
import cn.com.zzwfang.activity.SecondHandHouseDetailActivity;
import cn.com.zzwfang.bean.CityBean;
import cn.com.zzwfang.bean.MapFindHouseBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.bean.SearchHouseItemBean;
import cn.com.zzwfang.bean.TextValueBean;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.pullview.AbPullToRefreshView;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.util.Jumper;
import cn.com.zzwfang.view.helper.PopViewHelper;
import cn.com.zzwfang.view.helper.PopViewHelper.OnConditionSelectListener;

import com.alibaba.fastjson.JSON;
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
import com.baidu.mapapi.model.LatLng;

public class MainMapFindHouseFragment extends BaseFragment implements OnClickListener, OnCheckedChangeListener {

	private TextView tvBack, tvArea, tvTotalPrice, tvHouseType,
	tvHouseRoomsType, tvMore;
	private EditText edtKeyWords;
	private CheckBox cbxListAndMap;
	private MapView mapView;
	private BaiduMap baiduMap;
	private FrameLayout mapViewFlt;
	private AbPullToRefreshView pullView;
	private ListView lstMapFindHouseView;
	
	private LinearLayout lltArea, lltTotalPrice, lltHouseType, lltMore;
	
//	private String cityId;
	
	private ArrayList<MapFindHouseBean> mapAreas = new ArrayList<MapFindHouseBean>();
	
	private ArrayList<SearchHouseItemBean> estates = new ArrayList<SearchHouseItemBean>();
	
	private Marker mMarkerA;
    private Marker mMarkerB;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.frag_main_map_find_house, null);
		initView(view);
		setListener();
		return view;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		initData();
		getMapFindHouseData();
	}
	
	private void initView(View view) {
		tvBack = (TextView) view.findViewById(R.id.frag_map_back);
		edtKeyWords = (EditText) view.findViewById(R.id.frag_map_keywords_edt);
		cbxListAndMap = (CheckBox) view.findViewById(R.id.frag_map_list_map_cbx);
		
		tvArea = (TextView) view.findViewById(R.id.frag_map_find_house_area_tv);
		tvTotalPrice = (TextView) view.findViewById(R.id.frag_map_find_house_total_price_tv);
		tvHouseType = (TextView) view.findViewById(R.id.frag_map_find_house_type_tv);
//		tvHouseRoomsType = (TextView) view.findViewById(R.id.frag_map_find_house_area_tv);
		tvMore = (TextView) view.findViewById(R.id.frag_map_find_house_more_tv);
		
		lltArea = (LinearLayout) view.findViewById(R.id.frag_map_find_house_area_llt);
		lltTotalPrice = (LinearLayout) view.findViewById(R.id.frag_map_find_house_total_price_llt);
		lltHouseType = (LinearLayout) view.findViewById(R.id.frag_map_find_house_type_llt);
		lltMore = (LinearLayout) view.findViewById(R.id.frag_map_find_house_more_llt);
		
		mapViewFlt = (FrameLayout) view.findViewById(R.id.frag_map_find_house_map_flt);
		pullView = (AbPullToRefreshView) view.findViewById(R.id.pull_frag_map_find_house);
		lstMapFindHouseView = (ListView) view.findViewById(R.id.lst_frag_map_find_house);
		mapView = (MapView) view.findViewById(R.id.bmapView);
		
		mapView.showZoomControls(false);
		baiduMap = mapView.getMap();
		MapStatus status = new MapStatus.Builder().zoom(14).build();
		MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(status);
		baiduMap.setMapStatus(mapStatusUpdate);
	}
	
	private void setListener() {
		lltArea.setOnClickListener(this);
		lltTotalPrice.setOnClickListener(this);
		lltHouseType.setOnClickListener(this);
		lltMore.setOnClickListener(this);
		
		cbxListAndMap.setOnCheckedChangeListener(this);
		tvBack.setOnClickListener(this);
		
		baiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			
			@Override
			public boolean onMarkerClick(Marker marker) {
				
				Bundle bundle = marker.getExtraInfo();
				Object data = bundle.get("area");
				if (data instanceof MapFindHouseBean) {
					MapFindHouseBean area = (MapFindHouseBean) data;
					if (area != null) {
						TextValueBean textValueBeanArea = new TextValueBean();
						textValueBeanArea.setValue(area.getId());
						areaCondition = textValueBeanArea;
						getMapFindHouseEstate();
					}
				} else if (data instanceof SearchHouseItemBean) {
					SearchHouseItemBean estate = (SearchHouseItemBean) data;
					Jumper.newJumper()
			        .setAheadInAnimation(R.anim.activity_push_in_right)
			        .setAheadOutAnimation(R.anim.activity_alpha_out)
			        .setBackInAnimation(R.anim.activity_alpha_in)
			        .setBackOutAnimation(R.anim.activity_push_out_right)
			        .putString(SecondHandHouseDetailActivity.INTENT_HOUSE_SOURCE_ID, estate.getId())
			        .jump((BaseActivity)getActivity(), SecondHandHouseDetailActivity.class);
				}
				
				return true;
			}
		});
		
		onTotalPriceSelectListener = new OnConditionSelectListener() {

			@Override
			public void onConditionSelect(TextValueBean txtValueBean) {
				totalPriceCondition = txtValueBean;
				tvTotalPrice.setText(txtValueBean.getText());
				getMapFindHouseData();
//				getMapFindHouseEstate();
			}
		};

		onHouseTypeSelectListener = new OnConditionSelectListener() {

			@Override
			public void onConditionSelect(TextValueBean txtValueBean) {
				roomTypeCondition = txtValueBean;
				tvHouseType.setText(txtValueBean.getText());
				getMapFindHouseData();
//				getMapFindHouseEstate();
			}
		};

		onAreaSelectListener = new OnConditionSelectListener() {

			@Override
			public void onConditionSelect(TextValueBean txtValueBean) {
				areaCondition = txtValueBean;
				tvArea.setText(txtValueBean.getText());
				getMapFindHouseData();
//				getMapFindHouseEstate();
			}
		};
		edtKeyWords
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_SEARCH) {
							getMapFindHouseEstate();
							return true;
						}
						return false;
					}

				});
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.frag_map_back:
			((MainActivity)getActivity()).backToHomeFragment();
			break;
		case R.id.frag_map_find_house_area_llt: // 区域
			PopViewHelper.showSelectAreaPopWindow(getActivity(), lltArea, areas,
					onAreaSelectListener);
			break;
		case R.id.frag_map_find_house_total_price_llt: // 总价
			PopViewHelper.showSelectTotalPricePopWindow(getActivity(), lltTotalPrice,
					salePriceRanges, onTotalPriceSelectListener);
			break;
		case R.id.frag_map_find_house_type_llt: // 类型
			PopViewHelper.showSelectHouseTypePopWindow(getActivity(), lltHouseType,
					houseTypes, onHouseTypeSelectListener);
			break;
		case R.id.frag_map_find_house_more_tv:// 更多
			PopViewHelper.showSecondHandHouseMorePopWindow(getActivity(), moreType,
					null, null, estateLabels, lltMore);
			break;
		}
	}
	
	public static final String SalePriceRange = "SalePriceRange";
	public static final String HouseType = "HouseType";
	public static final String PrpUsage = "PrpUsage";
	public static final String EstateLabel = "EstateLabel";
	public static final String EstateStatus = "EstateStatus";
	
	private void initData() {
		moreType.add("朝向");
		moreType.add("面积");
		moreType.add("标签");
		moreType.add("楼层");
		moreType.add("房源编号");
		getConditionList(SalePriceRange);
		getConditionList(HouseType);
		getConditionList(PrpUsage);
		getConditionList(EstateLabel);
		getConditionList(EstateStatus);
		getAreaList();
	}
	
	
	
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		switch (buttonView.getId()) {
		case R.id.frag_map_list_map_cbx:
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
	
	private void getMapFindHouseData() {
		CityBean cityBean = ContentUtils.getCityBean(getActivity());
		if (cityBean == null) {
			return;
		}
		ActionImpl actionImpl = ActionImpl.newInstance(getActivity());
		actionImpl.getMapFindHouseData(0, cityBean.getSiteId(), new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				mapAreas.clear();
				ArrayList<MapFindHouseBean> temp = (ArrayList<MapFindHouseBean>) JSON.parseArray(result.getData(), MapFindHouseBean.class);
				mapAreas.addAll(temp);
				rendArea();
			}
		});
	}
	
	private void rendArea() {
		if (mapAreas != null && mapAreas.size() > 0) {
			baiduMap.clear();
			View viewAreaPoint = View.inflate(getActivity(), R.layout.view_map_point_area, null);
			TextView tvEstate = (TextView) viewAreaPoint.findViewById(R.id.view_point_title);
			TextView tvNum = (TextView) viewAreaPoint.findViewById(R.id.view_point_num);
			for (MapFindHouseBean area : mapAreas) {
				LatLng latLng = new LatLng(area.getLat(), area.getLng());
				tvEstate.setText(area.getName());
				tvNum.setText(area.getPrpCount());
				Bitmap bmpAreaPoint = getViewBitmap(viewAreaPoint);
				
		        BitmapDescriptor bdA = BitmapDescriptorFactory.fromBitmap(bmpAreaPoint);
		        Bundle bundle = new Bundle();
		        bundle.putSerializable("area", area);
		        MarkerOptions ooA = new MarkerOptions().position(latLng).icon(bdA)
		                .zIndex(9).draggable(true).extraInfo(bundle);
		        
		        ooA.animateType(MarkerAnimateType.drop);
		        mMarkerA = (Marker) (baiduMap.addOverlay(ooA));
			}
			MapFindHouseBean area = mapAreas.get(0);
			LatLng latLng = new LatLng(area.getLat(), area.getLng());
			MapStatusUpdate u = MapStatusUpdateFactory
	                .newLatLng(latLng);
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
	
	private TextValueBean areaCondition;
	private TextValueBean totalPriceCondition;
	private TextValueBean labelCondition;
	private TextValueBean statusCondition;
	private TextValueBean roomTypeCondition;
	
	
	private void getMapFindHouseEstate() {
		String keyWords = edtKeyWords.getText().toString();
		ActionImpl actionImpl = ActionImpl.newInstance(getActivity());
		actionImpl.getSearchHouseList(areaCondition, totalPriceCondition, null, null, labelCondition, statusCondition, keyWords, new ResultHandlerCallback() {
			
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
//			baiduMap.clear();
			View viewAreaPoint = View.inflate(getActivity(), R.layout.view_map_point_estate, null);
			TextView tvArea = (TextView) viewAreaPoint.findViewById(R.id.view_point_estate_title);
			TextView tvPrice = (TextView) viewAreaPoint.findViewById(R.id.view_point_estate_price);
			for (SearchHouseItemBean estate : estates) {
				LatLng latLng = new LatLng(estate.getLat(), estate.getLng());
				tvArea.setText(estate.getName());
				tvPrice.setText(estate.getPrpAvg() + estate.getRentUnitName());
				Bitmap bmpAreaPoint = getViewBitmap(viewAreaPoint);
				
		        BitmapDescriptor bdA = BitmapDescriptorFactory.fromBitmap(bmpAreaPoint);
		        Bundle bundle = new Bundle();
		        bundle.putSerializable("area", estate);
		        MarkerOptions ooA = new MarkerOptions().position(latLng).icon(bdA)
		                .zIndex(9).draggable(true).extraInfo(bundle);
		        
		        ooA.animateType(MarkerAnimateType.drop);
		        mMarkerA = (Marker) (baiduMap.addOverlay(ooA));
			}
			SearchHouseItemBean estate = estates.get(0);
			LatLng latLng = new LatLng(estate.getLat(), estate.getLng());
			MapStatusUpdate u = MapStatusUpdateFactory
	                .newLatLng(latLng);
	        baiduMap.setMapStatus(u);
		}
	}
	
	
	
	
	
	
	@Override
	public void onResume() {
		super.onResume();
		mapView.onResume();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		mapView.onPause();
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

	private Bitmap getViewBitmap(View addViewContent) {

        addViewContent.setDrawingCacheEnabled(true);

        addViewContent.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        addViewContent.layout(0, 0,
                addViewContent.getMeasuredWidth(),
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
	
	private void getAreaList() {
		
		CityBean cityBean = ContentUtils.getCityBean(getActivity());
		if (cityBean == null) {
			return;
		}
		ActionImpl actionImpl = ActionImpl.newInstance(getActivity());
		actionImpl.getAreaList(cityBean.getSiteId(), new ResultHandlerCallback() {

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

	
}
