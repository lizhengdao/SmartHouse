package cn.com.zzwfang.activity;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;
import com.tencent.mm.sdk.platformtools.Log;

import cn.com.zzwfang.R;
import cn.com.zzwfang.adapter.FeeHunterCompanyAnnouncementListAdapter;
import cn.com.zzwfang.bean.CompanyAnnouncementBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.pullview.AbPullToRefreshView;
import cn.com.zzwfang.pullview.AbPullToRefreshView.OnFooterLoadListener;
import cn.com.zzwfang.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import cn.com.zzwfang.util.Jumper;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author lzd
 * 公司公告列表
 *
 */
public class FeeHunterCompanyAnnouncementActivity extends BaseActivity implements OnClickListener,
OnHeaderRefreshListener, OnFooterLoadListener, OnItemClickListener {

    
    private TextView tvBack;
    private AbPullToRefreshView pullView;
    private ListView lstCompanyAnnouncement;
    
    private FeeHunterCompanyAnnouncementListAdapter adapter;
    
    private int pageIndex = 1;
    private int pageTotal = 0;
    
    private ArrayList<CompanyAnnouncementBean> companyAnnouncements = new ArrayList<CompanyAnnouncementBean>();
    
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        
        setContentView(R.layout.act_fee_hunter_company_announcement);
        
        tvBack = (TextView) findViewById(R.id.act_fee_hunter_company_announcement_back);
        pullView = (AbPullToRefreshView) findViewById(R.id.pull_act_fee_hunter_company_announcement);
        lstCompanyAnnouncement = (ListView) findViewById(R.id.lst_act_fee_hunter_company_announcement);
        
        adapter = new FeeHunterCompanyAnnouncementListAdapter(this, companyAnnouncements);
        lstCompanyAnnouncement.setAdapter(adapter);
        lstCompanyAnnouncement.setOnItemClickListener(this);
        
        tvBack.setOnClickListener(this);
        pullView.setPullRefreshEnable(true);
        pullView.setLoadMoreEnable(true);
        pullView.setOnHeaderRefreshListener(this);
        pullView.setOnFooterLoadListener(this);
        
        getCompanyAnnocementList(true);
    }
    
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
        case R.id.act_fee_hunter_company_announcement_back:
            finish();
            break;
        }
    }
    
    
    private void getCompanyAnnocementList(final boolean isRefresh) {
        
        ActionImpl actionImpl = ActionImpl.newInstance(this);
        if (isRefresh) {
            pageIndex = 1;
        }
        
        actionImpl.getCompanyAnnocementList(pageIndex, 10, new ResultHandlerCallback() {
            
            @Override
            public void rc999(RequestEntity entity, Result result) {
                // TODO Auto-generated method stub
                if (isRefresh) {
                    pullView.onHeaderRefreshFinish();
                } else {
                    pullView.onFooterLoadFinish();
                }
            }
            
            @Override
            public void rc3001(RequestEntity entity, Result result) {
                // TODO Auto-generated method stub
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
                pageTotal = (int) Math
                        .ceil(((double) total / (double) 10));
                
                ArrayList<CompanyAnnouncementBean> temp = (ArrayList<CompanyAnnouncementBean>) JSON
                        .parseArray(result.getData(),
                                CompanyAnnouncementBean.class);
                if (isRefresh) {
                    companyAnnouncements.clear();
                }
                companyAnnouncements.addAll(temp);
                if (temp != null) {
                    pageIndex++;
                }
                adapter.notifyDataSetChanged();
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
        // TODO Auto-generated method stub
        if (pageIndex > pageTotal) {
            pullView.onFooterLoadFinish();
            return;
        }
        
        getCompanyAnnocementList(false);
    }

    @Override
    public void onHeaderRefresh(AbPullToRefreshView view) {
        // TODO Auto-generated method stub
        getCompanyAnnocementList(true);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        CompanyAnnouncementBean temp = companyAnnouncements.get(arg2);
        Jumper.newJumper()
        .setAheadInAnimation(R.anim.activity_push_in_right)
        .setAheadOutAnimation(R.anim.activity_alpha_out)
        .setBackInAnimation(R.anim.activity_alpha_in)
        .setBackOutAnimation(R.anim.activity_push_out_right)
        .putSerializable(FeeHunterCompanyAnnouncementDetailActivity.INTENT_Company_Announcement, temp)
        .jump(FeeHunterCompanyAnnouncementActivity.this, FeeHunterCompanyAnnouncementDetailActivity.class);
    }


    
}
