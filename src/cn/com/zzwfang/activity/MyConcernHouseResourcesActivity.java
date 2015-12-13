package cn.com.zzwfang.activity;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.adapter.AttentionAdapter;
import cn.com.zzwfang.adapter.AttentionAdapter.OnConcernAllDeletedListener;
import cn.com.zzwfang.bean.AttentionBean;
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
 * 我关注的房源 我的关注
 * 
 * @author lzd
 * 
 */
public class MyConcernHouseResourcesActivity extends BaseActivity implements
		OnClickListener, OnHeaderRefreshListener, OnFooterLoadListener,
		OnItemClickListener, OnConcernAllDeletedListener {

	private TextView tvBack, tvGoToSee;
	private LinearLayout lltNoConcern;

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
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		getMyAttentionList(true);
	}

	private void initView() {
		tvBack = (TextView) findViewById(R.id.act_concern_house_back);
		tvGoToSee = (TextView) findViewById(R.id.act_concern_house_go_to_see);
		lltNoConcern = (LinearLayout) findViewById(R.id.act_concern_house_no_concern_llt);
		pullView = (AbPullToRefreshView) findViewById(R.id.pull_my_concern);
		lstConcern = (ListView) findViewById(R.id.lst_my_concern);
		
		adapter = new AttentionAdapter(this, attentions, this);
		lstConcern.setAdapter(adapter);
		
		lstConcern.setOnItemClickListener(this);
		
		pullView.setPullRefreshEnable(true);
		pullView.setLoadMoreEnable(true);
		pullView.setOnHeaderRefreshListener(this);
		pullView.setOnFooterLoadListener(this);

		tvBack.setOnClickListener(this);
		tvGoToSee.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_concern_house_back: // 返回
			finish();
			break;
		case R.id.act_concern_house_go_to_see: // 跳二手房列表
			Jumper.newJumper()
	        .setAheadInAnimation(R.anim.activity_push_in_right)
	        .setAheadOutAnimation(R.anim.activity_alpha_out)
	        .setBackInAnimation(R.anim.activity_alpha_in)
	        .setBackOutAnimation(R.anim.activity_push_out_right)
	        .jump(this, SecondHandHouseActivity.class);
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
				if (isRefresh) {
					pullView.onHeaderRefreshFinish();
				} else {
					pullView.onFooterLoadFinish();
				}
				if (attentions.size() == 0) {
					pullView.setVisibility(View.GONE);
					lltNoConcern.setVisibility(View.VISIBLE);
				} else {
					pullView.setVisibility(View.VISIBLE);
					lltNoConcern.setVisibility(View.GONE);
				}
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
				if (isRefresh) {
					pullView.onHeaderRefreshFinish();
				} else {
					pullView.onFooterLoadFinish();
				}
				if (attentions.size() == 0) {
					pullView.setVisibility(View.GONE);
					lltNoConcern.setVisibility(View.VISIBLE);
				} else {
					pullView.setVisibility(View.VISIBLE);
					lltNoConcern.setVisibility(View.GONE);
				}
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				int total = result.getTotal();
				pageNum = (int) Math.ceil(((double)total / (double)10));
				ArrayList<AttentionBean> temp = (ArrayList<AttentionBean>) JSON.parseArray(result.getData(), AttentionBean.class);
				if (isRefresh) {
					attentions.clear();
				}
				attentions.addAll(temp);
				adapter.notifyDataSetChanged();
				if (isRefresh) {
					pullView.onHeaderRefreshFinish();
				} else {
					pullView.onFooterLoadFinish();
				}
				
				if (attentions.size() == 0) {
					pullView.setVisibility(View.GONE);
					lltNoConcern.setVisibility(View.VISIBLE);
				} else {
					pullView.setVisibility(View.VISIBLE);
					lltNoConcern.setVisibility(View.GONE);
				}
			}
		});
	}

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
        // TODO Auto-generated method stub
        AttentionBean attentionBean = attentions.get(position);
        Jumper.newJumper()
        .setAheadInAnimation(R.anim.activity_push_in_right)
        .setAheadOutAnimation(R.anim.activity_alpha_out)
        .setBackInAnimation(R.anim.activity_alpha_in)
        .setBackOutAnimation(R.anim.activity_push_out_right)
        .putString(SecondHandHouseDetailActivity.INTENT_HOUSE_SOURCE_ID, attentionBean.getPropertyId())
        .jump(this, SecondHandHouseDetailActivity.class);
    }

	@Override
	public void onConcernAllDeleted() {
		// TODO Auto-generated method stub
		if (attentions.size() == 0) {
			pullView.setVisibility(View.GONE);
			lltNoConcern.setVisibility(View.VISIBLE);
		} else {
			pullView.setVisibility(View.VISIBLE);
			lltNoConcern.setVisibility(View.GONE);
		}
	}
}
