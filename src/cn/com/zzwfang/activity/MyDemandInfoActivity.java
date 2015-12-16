package cn.com.zzwfang.activity;

import java.util.ArrayList;

import android.content.Intent;
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
import cn.com.zzwfang.bean.MyDemandInfoBean;
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
 * 我的需求 ->  我的需求页
 * @author lzd
 *
 */
public class MyDemandInfoActivity extends BaseActivity implements OnClickListener,
    OnHeaderRefreshListener, OnFooterLoadListener, OnItemClickListener {

	public static final int CODE_EDIT_PROXY_BUY_HOUSE_INFO = 100;
	private TextView tvBack, tvEdit;
	
	private TextView tvCourtName, tvType, tvInterest, tvTotalPrice, tvDecoration,
	tvDirection, tvSquare, tvHouseType;
	
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
		getMyDemandInfo();
		getHousesRecommendedToMe(true);
	}
	
	private void initView() {
		setContentView(R.layout.act_my_demand_info);
		
		tvBack = (TextView) findViewById(R.id.act_my_demand_info_back);
		tvEdit = (TextView) findViewById(R.id.act_my_demand_info_edit);
		tvCourtName = (TextView) findViewById(R.id.act_my_demand_info_court_name);
		tvType = (TextView) findViewById(R.id.act_my_demand_info_type);
		tvInterest = (TextView) findViewById(R.id.act_my_demand_info_interest);
		tvTotalPrice = (TextView) findViewById(R.id.act_my_demand_info_total_price);
		tvDecoration = (TextView) findViewById(R.id.act_my_demand_info_decoration);
		tvDirection = (TextView) findViewById(R.id.act_my_demand_info_direction);
		tvSquare = (TextView) findViewById(R.id.act_my_demand_info_square);
		tvHouseType = (TextView) findViewById(R.id.act_my_demand_info_house_type);
		
		pullView = (AbPullToRefreshView) findViewById(R.id.pull_act_my_demand_info_recommended_to_me);
		lstView = (ListView) findViewById(R.id.lst_act_my_demand_info_recommended_to_me);
		
		adapter = new HousesRecommendedToMeAdapter(this, recommendedHouses);
		lstView.setAdapter(adapter);
		lstView.setOnItemClickListener(this);

		pullView.setPullRefreshEnable(true);
		pullView.setLoadMoreEnable(true);
		pullView.setOnHeaderRefreshListener(this);
		pullView.setOnFooterLoadListener(this);
		
		tvBack.setOnClickListener(this);
		tvEdit.setOnClickListener(this);
	}
	
	
	private void rendUI(MyDemandInfoBean myDemandInfoBean) {
		if (myDemandInfoBean != null) {
			tvCourtName.setText(myDemandInfoBean.getEstateName());
			tvTotalPrice.setText(myDemandInfoBean.getMinPrice() + "万元  至  " + myDemandInfoBean.getMaxPrice() + "万元");
			
			
			tvType.setText(myDemandInfoBean.getType());
			tvInterest.setText(myDemandInfoBean.getIntention());
			tvDecoration.setText(myDemandInfoBean.getPropertyDecoration());
			tvDirection.setText(myDemandInfoBean.getPropertyDirection());
			tvSquare.setText(myDemandInfoBean.getSquareMin() + "㎡  至  " + myDemandInfoBean.getSquareMax() + "㎡");
			tvHouseType.setText(myDemandInfoBean.getHouseType());
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_my_demand_info_back:
			finish();
			break;
		case R.id.act_my_demand_info_edit:  // 跳转委托买房
			Jumper.newJumper()
	        .setAheadInAnimation(R.anim.activity_push_in_right)
	        .setAheadOutAnimation(R.anim.activity_alpha_out)
	        .setBackInAnimation(R.anim.activity_alpha_in)
	        .setBackOutAnimation(R.anim.activity_push_out_right)
	        .jump(this, IWantBuyHouseActivity.class);
			break;
		}
	}
	
	/**
	 * 获取自己委托买房信息
	 */
	private void getMyDemandInfo() {
		
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		String userId = ContentUtils.getUserId(this);
		actionImpl.getMyDemandInfo(userId, new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				MyDemandInfoBean myDemandInfoBean = JSON.parseObject(result.getData(), MyDemandInfoBean.class);
				if (myDemandInfoBean == null) {  // 跳转委托买房
					Jumper.newJumper()
			        .setAheadInAnimation(R.anim.activity_push_in_right)
			        .setAheadOutAnimation(R.anim.activity_alpha_out)
			        .setBackInAnimation(R.anim.activity_alpha_in)
			        .setBackOutAnimation(R.anim.activity_push_out_right)
			        .jumpForResult(MyDemandInfoActivity.this, IWantBuyHouseActivity.class, CODE_EDIT_PROXY_BUY_HOUSE_INFO);
				} else {
					rendUI(myDemandInfoBean);
				}
				
			}
		});
	}
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if (arg1 == RESULT_OK) {
			switch (arg0) {
			case CODE_EDIT_PROXY_BUY_HOUSE_INFO:
				getMyDemandInfo();
				getHousesRecommendedToMe(true);
				break;
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
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
				pageIndex++;
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

	
}
