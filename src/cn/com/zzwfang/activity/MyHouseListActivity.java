package cn.com.zzwfang.activity;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.adapter.MyHouseAdapter;
import cn.com.zzwfang.bean.MyHouseBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.pullview.AbPullToRefreshView;
import cn.com.zzwfang.pullview.AbPullToRefreshView.OnFooterLoadListener;
import cn.com.zzwfang.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import cn.com.zzwfang.util.ContentUtils;

/**
 * 我的房 列表页
 * 
 * @author MISS-万
 * 
 */
public class MyHouseListActivity extends BaseActivity implements
		OnClickListener, OnHeaderRefreshListener, OnFooterLoadListener {

	private TextView tvBack;

	private AbPullToRefreshView pullView;

	private ListView lstView;
	
	private MyHouseAdapter adapter;
	
	private ArrayList<MyHouseBean> myHouses = new ArrayList<MyHouseBean>();
	
	private int pageIndex = 1;
	private int pageTotal = 0;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initView();
		getMyHouseList(true);
	}

	private void initView() {
		setContentView(R.layout.act_my_house_list);
		tvBack = (TextView) findViewById(R.id.act_my_house_list_back);
		pullView = (AbPullToRefreshView) findViewById(R.id.pull_my_house_list);
		lstView = (ListView) findViewById(R.id.lst_my_house);
		
		
		pullView.setPullRefreshEnable(true);
		pullView.setLoadMoreEnable(true);
		pullView.setOnHeaderRefreshListener(this);
		pullView.setOnFooterLoadListener(this);
		
		adapter = new MyHouseAdapter(this, myHouses);
		lstView.setAdapter(adapter);

		tvBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.act_my_house_list_back:
			finish();
			break;

		default:
			break;
		}
	}
	
	@Override
	public void onHeaderRefresh(AbPullToRefreshView view) {
		getMyHouseList(true);
	}

	@Override
	public void onFooterLoad(AbPullToRefreshView view) {
		if (pageIndex < pageTotal) {
			getMyHouseList(false);
		} else {
			pullView.onFooterLoadFinish();
		}
	}
	
	
	public void getMyHouseList(final boolean isRefresh) {
		if (isRefresh) {
			pageIndex = 1;
		}
		String userPhone = ContentUtils.getLoginPhone(this);
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.getMyHouseList(userPhone, 10, pageIndex, new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				pageTotal = result.getTotal();
				ArrayList<MyHouseBean> temp = (ArrayList<MyHouseBean>) JSON.parseArray(result.getData(), MyHouseBean.class);
				if (isRefresh) {
					myHouses.clear();
				}
				myHouses.addAll(temp);
				adapter.notifyDataSetChanged();
				if (isRefresh) {
					pullView.onHeaderRefreshFinish();
				} else {
					pullView.onFooterLoadFinish();
				}
			}
		});
	}
}
