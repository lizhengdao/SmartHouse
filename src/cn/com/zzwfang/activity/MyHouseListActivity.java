package cn.com.zzwfang.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.adapter.MyBoughtHouseAdapter;
import cn.com.zzwfang.adapter.MySoldHouseAdapter;
import cn.com.zzwfang.bean.CityBean;
import cn.com.zzwfang.bean.MyBoughtHouseBean;
import cn.com.zzwfang.bean.MyProxySellHouseBean;
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
 * 我的房子 （我买的房  和  我委托卖的房）
 * 
 * @author MISS-万
 * 
 */
public class MyHouseListActivity extends BaseActivity implements
		OnClickListener, OnHeaderRefreshListener, OnFooterLoadListener,
		OnCheckedChangeListener {

	// 没有售房，跳委托售房
	public static final int CODE_PROXY_SELL_HOUSE = 100;
	
	private TextView tvBack, tvNoHousePrompt, tvGo;
	private LinearLayout lltNoHousePrompt;
	
	private RadioButton rbMyBoughtHouse, rbMySoldHouse;
	private AbPullToRefreshView pullView;

	private ListView lstView;
	
	
	/**
	 * 0 我的购房，   1  我的售房
	 */
	private int houseType = 0;
	
	/**
	 * 我的售房
	 */
	private ArrayList<MyProxySellHouseBean> mySoldHouses = new ArrayList<MyProxySellHouseBean>();
	private MySoldHouseAdapter adapterMySoldHouse;
	
	private int pageIndexSold = 1;
	private int pageTotalSold = 0;
	
	private int pageIndexBuy = 1;
	private int pageTotalBuy = 0;
	
	/**
	 * 我买的房(通过Erp线下买的)
	 */
	private ArrayList<MyBoughtHouseBean> myBoughthouses = new ArrayList<MyBoughtHouseBean>();
	private MyBoughtHouseAdapter adapterMyBoughtHouse;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initView();
		getMySoldHouseList(true);
	}

	private void initView() {
		setContentView(R.layout.act_my_house_list);
		tvBack = (TextView) findViewById(R.id.act_my_house_list_back);
		pullView = (AbPullToRefreshView) findViewById(R.id.pull_my_house_list);
		lstView = (ListView) findViewById(R.id.lst_my_house);
		lltNoHousePrompt = (LinearLayout) findViewById(R.id.act_my_house_list_no_house_prompt_llt);
		tvNoHousePrompt = (TextView) findViewById(R.id.act_my_house_list_no_house_prompt_tv);
		tvGo = (TextView) findViewById(R.id.act_my_house_list_go_to_see);
		rbMyBoughtHouse = (RadioButton) findViewById(R.id.act_my_house_list_my_bought);
		rbMySoldHouse = (RadioButton) findViewById(R.id.act_my_house_list_my_sold);
		
		pullView.setPullRefreshEnable(true);
		pullView.setLoadMoreEnable(true);
		pullView.setOnHeaderRefreshListener(this);
		pullView.setOnFooterLoadListener(this);
		
		adapterMyBoughtHouse = new MyBoughtHouseAdapter(this, myBoughthouses);
		adapterMySoldHouse = new MySoldHouseAdapter(this, mySoldHouses);
		lstView.setAdapter(adapterMyBoughtHouse);

		tvBack.setOnClickListener(this);
		rbMyBoughtHouse.setOnCheckedChangeListener(this);
		rbMySoldHouse.setOnCheckedChangeListener(this);
		tvGo.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.act_my_house_list_back:
			finish();
			break;
		case R.id.act_my_house_list_go_to_see:
			if (houseType == 0) {  // 跳二手房列表
				Jumper.newJumper()
	            .setAheadInAnimation(R.anim.activity_push_in_right)
	            .setAheadOutAnimation(R.anim.activity_alpha_out)
	            .setBackInAnimation(R.anim.activity_alpha_in)
	            .setBackOutAnimation(R.anim.activity_push_out_right)
	            .jump(this, SecondHandHouseActivity.class);
			} else if (houseType == 1) {  // 跳委托
				Jumper.newJumper()
	            .setAheadInAnimation(R.anim.activity_push_in_right)
	            .setAheadOutAnimation(R.anim.activity_alpha_out)
	            .setBackInAnimation(R.anim.activity_alpha_in)
	            .setBackOutAnimation(R.anim.activity_push_out_right)
	            .jumpForResult(this, ProxySellHouseActivity.class, CODE_PROXY_SELL_HOUSE);
			}
			
			break;
		}
	}
	
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			switch (buttonView.getId()) {
			case R.id.act_my_house_list_my_bought:  // 通过erp买的房(我的 购房)
				houseType = 0;
				lstView.setAdapter(adapterMyBoughtHouse);
				
				if (myBoughthouses.size() == 0) {
					tvNoHousePrompt.setText("您还没有购房，先去看看房源吧！");
					tvGo.setText("去看看");
					
					pullView.setVisibility(View.GONE);
					lltNoHousePrompt.setVisibility(View.VISIBLE);
				} else {
					pullView.setVisibility(View.VISIBLE);
					lltNoHousePrompt.setVisibility(View.GONE);
				}
				
				break;
			case R.id.act_my_house_list_my_sold:  // 委托卖的房(我的售房)
				houseType = 1;
				lstView.setAdapter(adapterMySoldHouse);
				if (mySoldHouses.size() == 0) {
					tvNoHousePrompt.setText("您还没有委托房源信息，先去委托哦！");
					tvGo.setText("去委托");
					
					pullView.setVisibility(View.GONE);
					lltNoHousePrompt.setVisibility(View.VISIBLE);
				} else {
					pullView.setVisibility(View.VISIBLE);
					lltNoHousePrompt.setVisibility(View.GONE);
				}
				break;
			}
		}
	}
	
	private void refreshPromptUI() {
		switch (houseType) {
		case 0:
			if (myBoughthouses.size() == 0) {
				tvNoHousePrompt.setText("您还没有购房，先去看看房源吧！");
				tvGo.setText("去看看");
				pullView.setVisibility(View.GONE);
				lltNoHousePrompt.setVisibility(View.VISIBLE);
			} else {
				pullView.setVisibility(View.VISIBLE);
				lltNoHousePrompt.setVisibility(View.GONE);
			}
			break;
		case 1:
			if (mySoldHouses.size() == 0) {
				tvNoHousePrompt.setText("您还没有委托房源信息，先去委托哦！");
				tvGo.setText("去委托");
				pullView.setVisibility(View.GONE);
				lltNoHousePrompt.setVisibility(View.VISIBLE);
			} else {
				pullView.setVisibility(View.VISIBLE);
				lltNoHousePrompt.setVisibility(View.GONE);
			}
			break;
		}
	}
	
	@Override
	public void onHeaderRefresh(AbPullToRefreshView view) {
		switch (houseType) {
		case 0:  // 我的 购房
			getMyBoughtHouses(true);
			break;
		case 1:  // 我的售房
			getMySoldHouseList(true);
			break;
		}
		
	}

	@Override
	public void onFooterLoad(AbPullToRefreshView view) {
		switch (houseType) {
		case 0:  // 我的 购房
			if (pageIndexBuy < pageTotalBuy) {
				getMyBoughtHouses(false);
			} else {
				pullView.onFooterLoadFinish();
			}
			break;
		case 1:  // 我的售房
			if (pageIndexSold < pageTotalSold) {
				getMySoldHouseList(false);
			} else {
				pullView.onFooterLoadFinish();
			}
			break;
		}
		
	}
	
	
	/**
	 * 委托要卖的房(我的售房)
	 * @param isRefresh
	 */
	public void getMySoldHouseList(final boolean isRefresh) {
		if (isRefresh) {
			pageIndexSold = 1;
		}
		String userPhone = ContentUtils.getLoginPhone(this);
		CityBean cityBean = ContentUtils.getCityBean(this);
		if (cityBean == null) {
			return;
		}
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.getMySellHouseList(userPhone, cityBean.getSiteId(), 10, pageIndexSold, new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
			    if (isRefresh) {
                    pullView.onHeaderRefreshFinish();
                } else {
                    pullView.onFooterLoadFinish();
                }
			    refreshPromptUI();
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
				pageTotalSold = (int) Math.ceil(((double)total / (double)10));
				ArrayList<MyProxySellHouseBean> temp = (ArrayList<MyProxySellHouseBean>) JSON.parseArray(result.getData(), MyProxySellHouseBean.class);
				if (isRefresh) {
					mySoldHouses.clear();
				}
				pageIndexSold++;
				MyProxySellHouseBean test = new MyProxySellHouseBean();
				temp.add(test);
				mySoldHouses.addAll(temp);
				adapterMySoldHouse.notifyDataSetChanged();
				if (isRefresh) {
					pullView.onHeaderRefreshFinish();
				} else {
					pullView.onFooterLoadFinish();
				}
				refreshPromptUI();
			}
		});
	}
	
	/**
	 * 获取 我的购房数据(我的购房)
	 * @param isRefresh
	 */
	private void getMyBoughtHouses(final boolean isRefresh) {
		if (isRefresh) {
			pageIndexBuy = 1;
		}
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
				refreshPromptUI();
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
				pageTotalBuy = (int) Math.ceil(((double)total / (double)10));
				ArrayList<MyBoughtHouseBean> temp = (ArrayList<MyBoughtHouseBean>) JSON.parseArray(result.getData(), MyBoughtHouseBean.class);
				if (isRefresh) {
					myBoughthouses.clear();
				}
				pageIndexBuy++;
				if (temp != null) {
					myBoughthouses.addAll(temp);
					adapterMyBoughtHouse.notifyDataSetChanged();
				}
				
				if (isRefresh) {
					pullView.onHeaderRefreshFinish();
				} else {
					pullView.onFooterLoadFinish();
				}
				refreshPromptUI();
			}
		});
	}
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if (arg1 == RESULT_OK) {
			switch (arg0) {
			case CODE_PROXY_SELL_HOUSE:
				getMySoldHouseList(true);
				break;
			}
		}
	}

	
}
