package cn.com.zzwfang.activity;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.adapter.AttentionAdapter;
import cn.com.zzwfang.bean.AttentionBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.pullview.AbPullToRefreshView;
import cn.com.zzwfang.pullview.AbPullToRefreshView.OnFooterLoadListener;
import cn.com.zzwfang.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import cn.com.zzwfang.util.ContentUtils;

/**
 * 我关注的房源 我的关注
 * 
 * @author lzd
 * 
 */
public class MyConcernHouseResourcesActivity extends BaseActivity implements
		OnClickListener, OnHeaderRefreshListener, OnFooterLoadListener {

	private TextView tvBack;

	private AbPullToRefreshView pullView;

	private ListView lstConcern;
	
	private ArrayList<AttentionBean> attentions = new ArrayList<AttentionBean>();
	
	private AttentionAdapter adapter;
	
	private int pageIndex = 1;
	
	private int pageNum = 0;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.act_my_concern_house_resources);

		initView();
		getMyAttentionList(true);
	}

	private void initView() {
		tvBack = (TextView) findViewById(R.id.act_concern_house_back);
		pullView = (AbPullToRefreshView) findViewById(R.id.pull_my_concern);
		lstConcern = (ListView) findViewById(R.id.lst_my_concern);
		
		adapter = new AttentionAdapter(this, attentions);
		lstConcern.setAdapter(adapter);
		
		pullView.setPullRefreshEnable(true);
		pullView.setLoadMoreEnable(true);
		pullView.setOnHeaderRefreshListener(this);
		pullView.setOnFooterLoadListener(this);

		tvBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_concern_house_back: // 返回
			finish();
			break;
		}
	}
	
	@Override
	public void onHeaderRefresh(AbPullToRefreshView view) {
		getMyAttentionList(true);
	}

	@Override
	public void onFooterLoad(AbPullToRefreshView view) {

		if (pageIndex < pageNum) {
			getMyAttentionList(false);
		} else {
			pullView.onFooterLoadFinish();
			return;
		}
	}

	
	
	private void getMyAttentionList(final boolean isRefresh) {
		
		if (isRefresh) {
			pageIndex = 1;
		}
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		String userId = ContentUtils.getUserId(this);
		actionImpl.getAttentionList(userId, 10, pageIndex, new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				pageNum = result.getTotal();
				ArrayList<AttentionBean> temp = (ArrayList<AttentionBean>) JSON.parseArray(result.getData(), AttentionBean.class);
				if (isRefresh) {
					attentions.clear();
				}
				attentions.addAll(temp);
				adapter.notifyDataSetChanged();
			}
		});
	}
}
