package cn.com.zzwfang.activity;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.adapter.MyBoughtHouseAdapter;
import cn.com.zzwfang.bean.MyBoughtHouseBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.pullview.AbPullToRefreshView;
import cn.com.zzwfang.pullview.AbPullToRefreshView.OnFooterLoadListener;
import cn.com.zzwfang.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import cn.com.zzwfang.util.ContentUtils;

/**
 * 我的需求 -> 我的购房
 * 
 * @author lzd
 * 
 */
public class MyBoughtHousesActivity extends BaseActivity implements
		OnClickListener, OnHeaderRefreshListener, OnFooterLoadListener {

	private TextView tvBack;
	private AbPullToRefreshView pullView;
	private ListView lstBoughtHouses;
	private MyBoughtHouseAdapter adapter;
	
	private ArrayList<MyBoughtHouseBean> houses = new ArrayList<MyBoughtHouseBean>();
	
	private int pageIndex = 1;
	private int pageTotal = 0;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initView();
		getMyBoughtHouses(true);
	}

	private void initView() {
		setContentView(R.layout.act_my_bought_houses);
		tvBack = (TextView) findViewById(R.id.act_my_bought_houses_back);

		pullView = (AbPullToRefreshView) findViewById(R.id.pull_act_my_bought_houses);
		lstBoughtHouses = (ListView) findViewById(R.id.lst_act_my_bought_houses_list);
		adapter = new MyBoughtHouseAdapter(this, houses);
		lstBoughtHouses.setAdapter(adapter);

		pullView.setPullRefreshEnable(true);
		pullView.setLoadMoreEnable(true);
		pullView.setOnHeaderRefreshListener(this);
		pullView.setOnFooterLoadListener(this);

		tvBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_my_bought_houses_back:
			finish();
			break;
		}
	}
	
	private void getMyBoughtHouses(final boolean isRefresh) {
		String userId = ContentUtils.getUserId(this);
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.getMyBoughtHouses(userId, new ResultHandlerCallback() {
			
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
				// TODO Auto-generated method stub
				int total = result.getTotal();
				pageTotal = (int) Math.ceil(((double)total / (double)10));
				ArrayList<MyBoughtHouseBean> temp = (ArrayList<MyBoughtHouseBean>) JSON.parseArray(result.getData(), MyBoughtHouseBean.class);
				if (isRefresh) {
					houses.clear();
				}
				if (temp != null) {
					houses.addAll(temp);
					adapter.notifyDataSetChanged();
				}
				
				if (isRefresh) {
					pullView.onHeaderRefreshFinish();
				} else {
					pullView.onFooterLoadFinish();
				}
			}
		});
	}

	@Override
	public void onFooterLoad(AbPullToRefreshView view) {
		if (pageIndex < pageTotal) {
			getMyBoughtHouses(false);
		} else {
			pullView.onFooterLoadFinish();
		}
	}

	@Override
	public void onHeaderRefresh(AbPullToRefreshView view) {
		getMyBoughtHouses(true);
	}
	
	
}
