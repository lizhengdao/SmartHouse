package cn.com.zzwfang.activity;
import java.util.ArrayList;

import com.alibaba.fastjson.JSON;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.adapter.MySoldHouseAdapter;
import cn.com.zzwfang.bean.CityBean;
import cn.com.zzwfang.bean.MyProxySellHouseBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.pullview.AbPullToRefreshView;
import cn.com.zzwfang.pullview.AbPullToRefreshView.OnFooterLoadListener;
import cn.com.zzwfang.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import cn.com.zzwfang.util.ContentUtils;



/**
 * 我委托卖的房子列表
 * @author lzd
 *
 */
public class MySellHouseListActivity extends BaseActivity implements OnClickListener,
OnHeaderRefreshListener, OnFooterLoadListener {

	private TextView tvBack;
	private AbPullToRefreshView pullView;
	private ListView lstView;
	/**
	 * 我的售房
	 */
	private ArrayList<MyProxySellHouseBean> mySoldHouses = new ArrayList<MyProxySellHouseBean>();
	private MySoldHouseAdapter adapter;
	private int pageIndexSold = 1;
	private int pageTotalSold = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		getMySoldHouseList(true);
	}
	
	private void initView() {
		setContentView(R.layout.act_my_sell_house_list);
		tvBack = (TextView) findViewById(R.id.act_my_sell_house_list_back);
		pullView = (AbPullToRefreshView) findViewById(R.id.pull_my_sell_house_list);
		lstView = (ListView) findViewById(R.id.lst_my_sell_house);
		
		pullView.setPullRefreshEnable(true);
		pullView.setLoadMoreEnable(true);
		pullView.setOnHeaderRefreshListener(this);
		pullView.setOnFooterLoadListener(this);
		
		adapter = new MySoldHouseAdapter(this, mySoldHouses);
		lstView.setAdapter(adapter);
		
		tvBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_my_sell_house_list_back:
			finish();
			break;
		}
	}

	@Override
	public void onFooterLoad(AbPullToRefreshView view) {
		if (pageIndexSold < pageTotalSold) {
			getMySoldHouseList(false);
		} else {
			pullView.onFooterLoadFinish();
		}
	}

	@Override
	public void onHeaderRefresh(AbPullToRefreshView view) {
		getMySoldHouseList(true);
	}
	
	/**
	 * 委托要卖的房
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
