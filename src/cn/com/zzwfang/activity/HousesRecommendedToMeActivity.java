package cn.com.zzwfang.activity;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.adapter.HousesRecommendedToMeAdapter;
import cn.com.zzwfang.bean.HouseRecommendedToMeBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.pullview.AbPullToRefreshView;
import cn.com.zzwfang.pullview.AbPullToRefreshView.OnFooterLoadListener;
import cn.com.zzwfang.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.util.Jumper;

/**
 * 推荐给我的房源
 * 
 * @author lzd
 * 
 */
public class HousesRecommendedToMeActivity extends BaseActivity implements
		OnClickListener, OnHeaderRefreshListener, OnFooterLoadListener, OnItemClickListener {

	private TextView tvBack;

	private AbPullToRefreshView pullView;

	private ListView lstView;
	private HousesRecommendedToMeAdapter adapter;
	
	private ArrayList<HouseRecommendedToMeBean> recommendedHouses = new ArrayList<HouseRecommendedToMeBean>();
	
	private int pageIndex = 1;
	private int pageTotal = 0;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initView();
		getHousesRecommendedToMe(true);
	}

	private void initView() {
		setContentView(R.layout.act_houses_recommended_to_me);

		tvBack = (TextView) findViewById(R.id.act_houses_recommended_to_me_back);
		pullView = (AbPullToRefreshView) findViewById(R.id.pull_act_houses_recommended_to_me);
		lstView = (ListView) findViewById(R.id.lst_act_houses_recommended_to_me);
		
		adapter = new HousesRecommendedToMeAdapter(this, recommendedHouses);
		lstView.setAdapter(adapter);
		lstView.setOnItemClickListener(this);

		pullView.setPullRefreshEnable(true);
		pullView.setLoadMoreEnable(true);
		pullView.setOnHeaderRefreshListener(this);
		pullView.setOnFooterLoadListener(this);

		tvBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_houses_recommended_to_me_back:
			finish();
			break;
		}
	}

	@Override
	public void onFooterLoad(AbPullToRefreshView view) {
		if (pageIndex < pageTotal) {
			getHousesRecommendedToMe(false);
		} else {
			pullView.onFooterLoadFinish();
		}
		
	}

	@Override
	public void onHeaderRefresh(AbPullToRefreshView view) {
		getHousesRecommendedToMe(true);
	}
	
	private void getHousesRecommendedToMe(final boolean isRefresh) {
		if (isRefresh) {
			pageIndex = 1;
		}
		String userId = ContentUtils.getUserId(this);
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.getHousesRecommendedToMe(userId, 10, pageIndex, new ResultHandlerCallback() {
			
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
				
				ArrayList<HouseRecommendedToMeBean> temp = (ArrayList<HouseRecommendedToMeBean>) JSON.parseArray(result.getData(), HouseRecommendedToMeBean.class);
				if (isRefresh) {
					recommendedHouses.clear();
				}
				recommendedHouses.addAll(temp);
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
		
		//  推荐的都是二手房，跳二手房详情
		HouseRecommendedToMeBean recommendedHouseBean = recommendedHouses.get(position);
		Jumper.newJumper()
        .setAheadInAnimation(R.anim.activity_push_in_right)
        .setAheadOutAnimation(R.anim.activity_alpha_out)
        .setBackInAnimation(R.anim.activity_alpha_in)
        .setBackOutAnimation(R.anim.activity_push_out_right)
        .putString(SecondHandHouseDetailActivity.INTENT_HOUSE_SOURCE_ID, recommendedHouseBean.getId())
        .jump(this, SecondHandHouseDetailActivity.class);
	}
}
