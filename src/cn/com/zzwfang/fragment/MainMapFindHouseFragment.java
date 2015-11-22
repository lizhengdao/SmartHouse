package cn.com.zzwfang.fragment;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.easemob.chat.core.ac;

import cn.com.zzwfang.R;
import cn.com.zzwfang.activity.MainActivity;
import cn.com.zzwfang.bean.CityBean;
import cn.com.zzwfang.bean.MapFindHouseBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.pullview.AbPullToRefreshView;
import cn.com.zzwfang.util.ContentUtils;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class MainMapFindHouseFragment extends BaseFragment implements OnClickListener, OnCheckedChangeListener {

	private TextView tvBack;
	private CheckBox cbxListAndMap;
	private MapView mapView;
	private BaiduMap baiduMap;
	private FrameLayout mapViewFlt;
	private AbPullToRefreshView pullView;
	private ListView lstMapFindHouseView;
	
	private String cityId;
	
	private ArrayList<MapFindHouseBean> areas = new ArrayList<MapFindHouseBean>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.frag_main_map_find_house, null);
		initView(view);
		return view;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		getMapFindHouseData();
	}
	
	private void initView(View view) {
		tvBack = (TextView) view.findViewById(R.id.frag_map_back);
		cbxListAndMap = (CheckBox) view.findViewById(R.id.frag_map_list_map_cbx);
		mapViewFlt = (FrameLayout) view.findViewById(R.id.frag_map_find_house_map_flt);
		pullView = (AbPullToRefreshView) view.findViewById(R.id.pull_frag_map_find_house);
		lstMapFindHouseView = (ListView) view.findViewById(R.id.lst_frag_map_find_house);
		mapView = (MapView) view.findViewById(R.id.bmapView);
		
		mapView.showZoomControls(false);
		baiduMap = mapView.getMap();
		MapStatus status = new MapStatus.Builder().zoom(14).build();
		MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(status);
		baiduMap.setMapStatus(mapStatusUpdate);
		
		cbxListAndMap.setOnCheckedChangeListener(this);
		tvBack.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.frag_map_back:
			((MainActivity)getActivity()).backToHomeFragment();
			break;
		}
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
				ArrayList<MapFindHouseBean> temp = (ArrayList<MapFindHouseBean>) JSON.parseArray(result.getData(), MapFindHouseBean.class);
				areas.addAll(temp);
			}
		});
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

	

	
	
}
