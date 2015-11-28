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
 * 我的需求 -> 推荐房源（列表页）
 * 
 * @author lzd
 * 
 */
public class RecommendHouseListActivity extends BaseActivity implements
		OnClickListener, OnHeaderRefreshListener, OnFooterLoadListener {

	private TextView tvBack;
	private AbPullToRefreshView pullView;
	private ListView lstRecommendHouse;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initView();
	}

	private void initView() {
		setContentView(R.layout.act_recommend_house_list);

		tvBack = (TextView) findViewById(R.id.act_recommend_house_list_back);
		pullView = (AbPullToRefreshView) findViewById(R.id.pull_recommend_house_list);
		lstRecommendHouse = (ListView) findViewById(R.id.lst_act_recommend_house_list);

		pullView.setPullRefreshEnable(true);
		pullView.setLoadMoreEnable(true);
		pullView.setOnHeaderRefreshListener(this);
		pullView.setOnFooterLoadListener(this);

		tvBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_recommend_house_list_back:
			finish();
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
}
