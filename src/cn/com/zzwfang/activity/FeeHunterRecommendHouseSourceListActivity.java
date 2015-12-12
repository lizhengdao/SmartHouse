package cn.com.zzwfang.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.adapter.FeeHunterRecommendHouseSourceListAdapter;
import cn.com.zzwfang.bean.CityBean;
import cn.com.zzwfang.bean.FeeHunterRecommendHouseSourceListItem;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.pullview.AbPullToRefreshView;
import cn.com.zzwfang.pullview.AbPullToRefreshView.OnFooterLoadListener;
import cn.com.zzwfang.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.util.Jumper;

import com.alibaba.fastjson.JSON;

/**
 * 赏金猎人 推荐房源 房屋列表
 * 
 * @author lzd
 * 
 */
public class FeeHunterRecommendHouseSourceListActivity extends BaseActivity
		implements OnClickListener, OnHeaderRefreshListener,
		OnFooterLoadListener, OnItemClickListener {

	private TextView tvBack;

	private AbPullToRefreshView pullView;
	private ListView lstView;
	private FeeHunterRecommendHouseSourceListAdapter adapter;

	private ArrayList<FeeHunterRecommendHouseSourceListItem> houseSources = new ArrayList<FeeHunterRecommendHouseSourceListItem>();

	private int pageIndex = 1;

	private int pageTotal = 0;

	private ActionImpl actionImpl;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initView();
		getRecommendHouseSourceList(true);
	}

	private void initView() {
		setContentView(R.layout.act_fee_hunter_recommend_house_source_list);

		tvBack = (TextView) findViewById(R.id.act_fee_hunter_recommend_house_source_list_back);
		pullView = (AbPullToRefreshView) findViewById(R.id.pull_fee_hunter_recommend_house_source_list);
		lstView = (ListView) findViewById(R.id.lst_fee_hunter_recommend_house_source_list);

		adapter = new FeeHunterRecommendHouseSourceListAdapter(this,
				houseSources);
		lstView.setAdapter(adapter);
		lstView.setOnItemClickListener(this);

		pullView.setPullRefreshEnable(true);
		pullView.setLoadMoreEnable(true);
		pullView.setOnHeaderRefreshListener(this);
		pullView.setOnFooterLoadListener(this);

		actionImpl = ActionImpl.newInstance(this);
		tvBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_fee_hunter_recommend_house_source_list_back:
			finish();
			break;
		}
	}

	private void getRecommendHouseSourceList(final boolean isRefresh) {

		if (isRefresh) {
			pageIndex = 1;
		}
		String userId = ContentUtils.getUserId(this);
		if (TextUtils.isEmpty(userId)) {
			return;
		}
		CityBean cityBean = ContentUtils.getCityBean(this);
		if (cityBean == null) {
			return;
		}
		actionImpl.getFeeHunterRecommendHouseSourceList(userId, cityBean.getSiteId(), 10, pageIndex,
				new ResultHandlerCallback() {

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
						pageTotal = (int) Math
								.ceil(((double) total / (double) 10));
						if (isRefresh) {
							houseSources.clear();
						}
						ArrayList<FeeHunterRecommendHouseSourceListItem> temp = (ArrayList<FeeHunterRecommendHouseSourceListItem>) JSON.parseArray(
								result.getData(),
								FeeHunterRecommendHouseSourceListItem.class);
						houseSources.addAll(temp);
						adapter.notifyDataSetChanged();
						pageIndex++;
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
		if (pageIndex > pageTotal) {
			pullView.onFooterLoadFinish();
			return;
		}
		getRecommendHouseSourceList(false);
	}

	@Override
	public void onHeaderRefresh(AbPullToRefreshView view) {
		getRecommendHouseSourceList(true);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		FeeHunterRecommendHouseSourceListItem date = houseSources.get(position);
		Jumper.newJumper()
        .setAheadInAnimation(R.anim.activity_push_in_right)
        .setAheadOutAnimation(R.anim.activity_alpha_out)
        .setBackInAnimation(R.anim.activity_alpha_in)
        .setBackOutAnimation(R.anim.activity_push_out_right)
        .putString(FeeHunterProgressDetailActivity.INTENT_HOUSE_SOURCE_ID, date.getId())
        .jump(this, FeeHunterProgressDetailActivity.class);
	}
}
