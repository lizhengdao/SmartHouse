package cn.com.zzwfang.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.pullview.AbPullToRefreshView;
import cn.com.zzwfang.pullview.AbPullToRefreshView.OnFooterLoadListener;
import cn.com.zzwfang.pullview.AbPullToRefreshView.OnHeaderRefreshListener;

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

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initView();
	}

	private void initView() {
		setContentView(R.layout.act_my_bought_houses);
		tvBack = (TextView) findViewById(R.id.act_my_bought_houses_back);

		pullView = (AbPullToRefreshView) findViewById(R.id.pull_act_my_bought_houses);
		lstBoughtHouses = (ListView) findViewById(R.id.lst_act_my_bought_houses_list);

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

	@Override
	public void onFooterLoad(AbPullToRefreshView view) {

	}

	@Override
	public void onHeaderRefresh(AbPullToRefreshView view) {

	}
	
	
}
