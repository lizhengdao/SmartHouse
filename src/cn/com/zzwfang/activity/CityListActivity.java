package cn.com.zzwfang.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.adapter.CityListAdapter;
import cn.com.zzwfang.bean.CityBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.util.ToastUtils;

import com.alibaba.fastjson.JSON;

public class CityListActivity extends BaseActivity implements OnClickListener, OnItemClickListener {

	public static String INTENT_CITY = "CityListActivity_city";
	private TextView tvBack;
	private ListView listCity;
	private LinearLayout lltHeader;
	private TextView tvLocate, tvCurrentLocation;
	private ArrayList<CityBean> cities = new ArrayList<CityBean>();
	private CityListAdapter adapter;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initView();
		getAreaList();
	}
	
	private void initView() {
		setContentView(R.layout.act_city_list);
		tvBack = (TextView) findViewById(R.id.act_city_list_back);
		listCity = (ListView) findViewById(R.id.act_city_list);
		
		lltHeader = (LinearLayout) View.inflate(this, R.layout.view_city_list_header, null);
		tvLocate = (TextView) lltHeader.findViewById(R.id.view_city_list_header_locate_tv);
		tvCurrentLocation = (TextView) lltHeader.findViewById(R.id.view_city_list_header_locate_city_tv);
		listCity.addHeaderView(lltHeader);
		adapter = new CityListAdapter(this, cities);
		listCity.setAdapter(adapter);
		listCity.setOnItemClickListener(this);
		tvBack.setOnClickListener(this);
		tvLocate.setOnClickListener(this);
		tvCurrentLocation.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_city_list_back:
			finish();
			break;
		case R.id.view_city_list_header_locate_tv:  // 定位获得当前城市
			ToastUtils.SHORT.toast(this, "定位获得当前城市");
			break;
		case R.id.view_city_list_header_locate_city_tv:  // 当前城市
			ToastUtils.SHORT.toast(this, "当前城市");
			break;
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		CityBean cityBean = cities.get(position - 1);
		Intent intent = new Intent();
		intent.putExtra(INTENT_CITY, cityBean);
		setResult(RESULT_OK, intent);
		finish();
	}
	
	private void getAreaList() {
		
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.getCityList(new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				ArrayList<CityBean> temp = (ArrayList<CityBean>) JSON.parseArray(result.getData(), CityBean.class);
				if (temp != null) {
					cities.addAll(temp);
					adapter.notifyDataSetChanged();
				}
			}
		});
	}



	
}
