package cn.com.zzwfang.activity;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.adapter.FeeHunterMyCustomerAdapter;
import cn.com.zzwfang.bean.CityBean;
import cn.com.zzwfang.bean.FeeHunterMyCustomerConditionBean;
import cn.com.zzwfang.bean.FeeHunterRecommendClientBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.pullview.AbPullToRefreshView;
import cn.com.zzwfang.pullview.AbPullToRefreshView.OnFooterLoadListener;
import cn.com.zzwfang.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.view.helper.PopViewHelper;
import cn.com.zzwfang.view.helper.PopViewHelper.OnMyCustomerConditionSelectListener;

/**
 * 赏金猎人   我的客户页
 * @author lzd
 *
 */
public class FeeHunterMyCustomerActivity extends BaseActivity implements OnClickListener, OnHeaderRefreshListener,
OnFooterLoadListener {

	private TextView tvBack, tvCondition;
	
	private ListView lstMyCustomer;
	
	private AbPullToRefreshView pullView;
	
	private ArrayList<FeeHunterMyCustomerConditionBean> conditions = new ArrayList<FeeHunterMyCustomerConditionBean>();
	
	private FeeHunterMyCustomerConditionBean currentConditon;
	
	private OnMyCustomerConditionSelectListener onMyCustomerConditionSelectListener;
	
	private ArrayList<FeeHunterRecommendClientBean> myCustomers = new ArrayList<FeeHunterRecommendClientBean>();
	
	private FeeHunterMyCustomerAdapter adapter;
	
	private int pageIndex = 1;
	private int pageTotal = 0;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initView();
		getMyCustomerCondition();
	}
	
	
	private void initView() {
		setContentView(R.layout.act_fee_hunter_my_customer);
		
		tvBack = (TextView) findViewById(R.id.act_fee_hunter_my_customer_back);
		tvCondition = (TextView) findViewById(R.id.act_fee_hunter_my_customer_condition);
		
		lstMyCustomer = (ListView) findViewById(R.id.act_fee_hunter_my_customer_lst);
		pullView = (AbPullToRefreshView) findViewById(R.id.pull_act_fee_hunter_my_customer);
		
		pullView.setPullRefreshEnable(true);
		pullView.setLoadMoreEnable(true);
		pullView.setOnHeaderRefreshListener(this);
		pullView.setOnFooterLoadListener(this);
		
		adapter = new FeeHunterMyCustomerAdapter(this, myCustomers);
		lstMyCustomer.setAdapter(adapter);
		
		tvBack.setOnClickListener(this);
		tvCondition.setOnClickListener(this);
		
		onMyCustomerConditionSelectListener = new OnMyCustomerConditionSelectListener() {
			
			@Override
			public void onMyCustomerConditonSelect(
					FeeHunterMyCustomerConditionBean conditon) {
				if (!conditon.equals(currentConditon)) {
					currentConditon = conditon;
					getMyCustomer(true);
				}
			}
		};
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_fee_hunter_my_customer_back:
			finish();
			break;
		case R.id.act_fee_hunter_my_customer_condition:
			PopViewHelper.showSelectMyCustomerConditionPopWindow(this, tvCondition, conditions, onMyCustomerConditionSelectListener);
			break;
		}
	}
	
	private void getMyCustomer(final boolean isRefresh) {
		if (isRefresh) {
			pageIndex = 1;
		}
	    CityBean cityBean = ContentUtils.getCityBean(this);
	    if (cityBean == null) {
	        return;
	    }
		if (currentConditon != null) {
			String userId = ContentUtils.getUserId(this);
			ActionImpl actionImpl = ActionImpl.newInstance(this);
			
			actionImpl.getFeeHunterMyCustomerList(userId, cityBean.getSiteId(), currentConditon.getId(), 10, pageIndex, new ResultHandlerCallback() {
				
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
						myCustomers.clear();
					}
					ArrayList<FeeHunterRecommendClientBean> temp = (ArrayList<FeeHunterRecommendClientBean>) JSON.parseArray(result.getData(), FeeHunterRecommendClientBean.class);
					myCustomers.addAll(temp);
					adapter.notifyDataSetChanged();
					if (isRefresh) {
						pullView.onHeaderRefreshFinish();
					} else {
						pullView.onFooterLoadFinish();
					}
				}
			});
		} else {
			if (isRefresh) {
				pullView.onHeaderRefreshFinish();
			} else {
				pullView.onFooterLoadFinish();
			}
		}
		
	}
	
	private void getMyCustomerCondition() {
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.getFeeHunterMyCustomerCondition(new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				ArrayList<FeeHunterMyCustomerConditionBean> temp = (ArrayList<FeeHunterMyCustomerConditionBean>) JSON.parseArray(result.getData(), FeeHunterMyCustomerConditionBean.class);
				conditions.addAll(temp);
			}
		});
				
	}


	@Override
	public void onFooterLoad(AbPullToRefreshView view) {
		if (pageIndex > pageTotal) {
			pullView.onFooterLoadFinish();
			return;
		}
		getMyCustomer(false);
	}


	@Override
	public void onHeaderRefresh(AbPullToRefreshView view) {
		getMyCustomer(true);
	}
}
