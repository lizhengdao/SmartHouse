package cn.com.zzwfang.activity;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;

import cn.com.zzwfang.R;
import cn.com.zzwfang.adapter.IAmOwnerAdapter;
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
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 我是业主 2015-12-28
 * @author doer06
 *
 */
public class IAmOwnerActicity extends BaseActivity implements OnClickListener,
    OnHeaderRefreshListener, OnFooterLoadListener{

    public static final String INTENT_TYPE = "IAmOwnerActicity.type";
    private TextView tvBack;
    private AbPullToRefreshView pullView;
    private ListView listView;
    /**
     * User/Income 新增参数 type 字符串
       在我是客户、我是业主 房源收支明细传递
       我是客户 传递“客户”字符串  我是业主 传递“业主” 字符串
     */
    private String type;
    
    /**
     * 我的售房
     */
    private ArrayList<MyProxySellHouseBean> mySoldHouses = new ArrayList<MyProxySellHouseBean>();
    private IAmOwnerAdapter adapter;
    private int pageIndexSold = 1;
    private int pageTotalSold = 0;
    
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        type = getIntent().getStringExtra(INTENT_TYPE);
        initView();
    }
    
    private void initView() {
        setContentView(R.layout.act_i_am_owner);
        tvBack = (TextView) findViewById(R.id.act_i_am_owner_back);
        pullView = (AbPullToRefreshView) findViewById(R.id.pull_act_i_am_owner);
        listView = (ListView) findViewById(R.id.act_i_am_owner_lst);
        
        adapter = new IAmOwnerAdapter(this, mySoldHouses);
        listView.setAdapter(adapter);
        
        tvBack.setOnClickListener(this);
        pullView.setPullRefreshEnable(true);
        pullView.setLoadMoreEnable(true);
        pullView.setOnHeaderRefreshListener(this);
        pullView.setOnFooterLoadListener(this);
        
        getMySoldHouseList(true);
    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.act_i_am_owner_back:
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
