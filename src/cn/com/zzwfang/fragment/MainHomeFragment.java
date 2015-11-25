package cn.com.zzwfang.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.activity.CityListActivity;
import cn.com.zzwfang.activity.SearchHouseActivity;
import cn.com.zzwfang.activity.SecondHandHouseDetailActivity;
import cn.com.zzwfang.activity.ShangJinLieRenActivity;
import cn.com.zzwfang.adapter.HomeRecommendHouseAdapter;
import cn.com.zzwfang.bean.CityBean;
import cn.com.zzwfang.bean.RecommendHouseSourceBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.util.Jumper;
import cn.com.zzwfang.view.ptz.PullToZoomListViewEx;

import com.alibaba.fastjson.JSON;

/**
 * 首页Fragment
 * @author lzd
 *
 */
public class MainHomeFragment extends BaseFragment implements OnClickListener, OnItemClickListener {

	/**
	 * 跳转选择城市
	 */
	public static final int CODE_SELECT_CITY = 1000;
	
	private TextView tvLocation, edtSearchProperties;
	
	private FrameLayout zoomLlt;
	
	private PullToZoomListViewEx ptzListView;
	private LinearLayout headerLlt;
	private HomeRecommendHouseAdapter adapter;
	private ImageView imgShangjin;
	
	private String cityId;
	
	private ArrayList<RecommendHouseSourceBean> recommendSources = new ArrayList<RecommendHouseSourceBean>();
	
	private OnCitySelectedListener onCitySelectedListener;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_main_home, null);
		initView(view);
		return view;
	}
	
	@Override
	public void onAttach(Activity activity) {
	    if (activity instanceof cn.com.zzwfang.fragment.MainHomeFragment.OnCitySelectedListener) {
	        onCitySelectedListener = (cn.com.zzwfang.fragment.MainHomeFragment.OnCitySelectedListener) activity;
	    }
	    super.onAttach(activity);
	}

	
	private void initView(View view) {
		
		zoomLlt = (FrameLayout) View.inflate(getActivity(), R.layout.view_frag_home_zoom, null);
		headerLlt = (LinearLayout) View.inflate(getActivity(), R.layout.view_frag_home_header, null);
		tvLocation = (TextView) headerLlt.findViewById(R.id.tv_frag_home_location);
		edtSearchProperties = (TextView) headerLlt.findViewById(R.id.edt_search_properties);
		
		imgShangjin = (ImageView) view.findViewById(R.id.iv_frag_home_shangjinglieren);
		ptzListView = (PullToZoomListViewEx) view.findViewById(R.id.frag_home_ptz);
		ptzListView.setHeaderView(headerLlt);
		ptzListView.setZoomView(zoomLlt);
		adapter = new HomeRecommendHouseAdapter(getActivity(), recommendSources);
		ptzListView.setAdapter(adapter);
		ptzListView.setHideHeader(false);
		
		CityBean cityBean = ContentUtils.getCityBean(getActivity());
		if (cityBean != null) {
			String cityName = cityBean.getName();
			String cityId = cityBean.getSiteId();
			if (!TextUtils.isEmpty(cityName) && !TextUtils.isEmpty(cityId)) {
				tvLocation.setText(cityBean.getName());
				adapter.setCityId(cityId);
				getRecommendHouseSourceList(cityId);
			}
		}
		
		ptzListView.setOnItemClickListener(this);
		
		tvLocation.setOnClickListener(this);
		edtSearchProperties.setOnClickListener(this);
		imgShangjin.setOnClickListener(this);
		
//		RSAUtil.testRsa();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_frag_home_location:   // 定位选择城市
			Jumper.newJumper()
            .setAheadInAnimation(R.anim.activity_push_in_right)
            .setAheadOutAnimation(R.anim.activity_alpha_out)
            .setBackInAnimation(R.anim.activity_alpha_in)
            .setBackOutAnimation(R.anim.activity_push_out_right)
            .jumpForResult(this, CityListActivity.class, CODE_SELECT_CITY);
			break;
		case R.id.edt_search_properties:    //  搜索
			Jumper.newJumper()
            .setAheadInAnimation(R.anim.activity_push_in_right)
            .setAheadOutAnimation(R.anim.activity_alpha_out)
            .setBackInAnimation(R.anim.activity_alpha_in)
            .setBackOutAnimation(R.anim.activity_push_out_right)
            .putString(HomeRecommendHouseAdapter.INTENT_CITY_ID, cityId)
            .jump(this, SearchHouseActivity.class);
			break;
		case R.id.iv_frag_home_shangjinglieren:  // 赏金猎人
			Jumper.newJumper()
            .setAheadInAnimation(R.anim.activity_push_in_right)
            .setAheadOutAnimation(R.anim.activity_alpha_out)
            .setBackInAnimation(R.anim.activity_alpha_in)
            .setBackOutAnimation(R.anim.activity_push_out_right)
            .jump(this, ShangJinLieRenActivity.class);
			break;
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		RecommendHouseSourceBean recommendHouse = recommendSources.get(position - 2);
		Jumper.newJumper()
        .setAheadInAnimation(R.anim.activity_push_in_right)
        .setAheadOutAnimation(R.anim.activity_alpha_out)
        .setBackInAnimation(R.anim.activity_alpha_in)
        .setBackOutAnimation(R.anim.activity_push_out_right)
        .putString(SecondHandHouseDetailActivity.INTENT_HOUSE_SOURCE_ID, recommendHouse.getId())
        .jump(this, SecondHandHouseDetailActivity.class);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case CODE_SELECT_CITY:
				CityBean cityBean = (CityBean) data.getSerializableExtra(CityListActivity.INTENT_CITY);
				cityId = cityBean.getSiteId();
				ContentUtils.saveCityBeanData(getActivity(), cityBean);
				tvLocation.setText(cityBean.getName());
				adapter.setCityId(cityBean.getSiteId());
				if (onCitySelectedListener != null) {
				    onCitySelectedListener.onCitySelected(cityBean);
				}
				getRecommendHouseSourceList(cityBean.getSiteId());
				break;
			}
		}
	}
	
	/**
	 * 获取推荐列表
	 * @param cityId
	 */
	private void getRecommendHouseSourceList(String cityId) {
		ActionImpl actionImpl = ActionImpl.newInstance(getActivity());
		actionImpl.getRecommendHouseSource(cityId, new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
				
			}
			 
			@Override
			public void rc3001(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				ArrayList<RecommendHouseSourceBean> temp = (ArrayList<RecommendHouseSourceBean>) JSON.parseArray(result.getData(), RecommendHouseSourceBean.class);
				if (temp != null) {
					recommendSources.clear();
					recommendSources.addAll(temp);
					adapter.notifyDataSetChanged();
				}
				
			}
		});
	}


	public interface OnCitySelectedListener {
        void onCitySelected(CityBean cityBean);
    }

	
}
