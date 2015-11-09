package cn.com.zzwfang.fragment;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;

import cn.com.zzwfang.R;
import cn.com.zzwfang.activity.MainActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainMapFindHouseFragment extends BaseFragment implements OnClickListener {

	private TextView tvBack;
	private MapView mapView;
	private BaiduMap baiduMap;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.frag_main_map_find_house, null);
		initView(view);
		return view;
	}
	
	private void initView(View view) {
		tvBack = (TextView) view.findViewById(R.id.frag_map_back);
		
		tvBack.setOnClickListener(this);
		
		mapView = (MapView) view.findViewById(R.id.bmapView);
		
		mapView.showZoomControls(false);
		
		baiduMap = mapView.getMap();
		MapStatus status = new MapStatus.Builder().zoom(14).build();
		MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(status);
		baiduMap.setMapStatus(mapStatusUpdate);
		
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
