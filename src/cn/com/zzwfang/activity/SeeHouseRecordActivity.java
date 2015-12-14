package cn.com.zzwfang.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.adapter.SeeHouseRecordAdapter;
import cn.com.zzwfang.bean.InqFollowListBean;
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
 * 看房记录页  （从二手房详情跳来）
 * 带看记录
 * @author doer06
 *
 */
public class SeeHouseRecordActivity extends BaseActivity implements
        OnClickListener, OnItemClickListener, OnHeaderRefreshListener, OnFooterLoadListener {

    public static final String INTENT_RECORD = "intent_record";
    
    public static final String INTENT_HOUSE_SOURCE_ID = "intent_house_source_id";

    private TextView tvBack, tvRecentRecord, tvComplain;

    private AbPullToRefreshView pullView;
    private ListView lstRecord;

    private SeeHouseRecordAdapter adapter;

    private ArrayList<InqFollowListBean> inqFollowList;
    
    private String houseSourceId;
    
    private int pageIndex = 1;
    
    private int pageTotal = 0;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        inqFollowList = (ArrayList<InqFollowListBean>) getIntent()
                .getSerializableExtra(INTENT_RECORD);
        houseSourceId = getIntent().getStringExtra(INTENT_HOUSE_SOURCE_ID);
        initView();
    }

    private void initView() {
        setContentView(R.layout.act_see_house_record);

        tvBack = (TextView) findViewById(R.id.act_see_house_record_back);
        pullView = (AbPullToRefreshView) findViewById(R.id.pull_act_see_house_record_list);
        lstRecord = (ListView) findViewById(R.id.act_see_house_record_lst);
        tvRecentRecord = (TextView) findViewById(R.id.act_see_house_record_recent);
        tvComplain = (TextView) findViewById(R.id.act_see_house_record_complain);
        
        pullView.setPullRefreshEnable(true);
		pullView.setLoadMoreEnable(true);
		pullView.setOnHeaderRefreshListener(this);
		pullView.setOnFooterLoadListener(this);

        if (inqFollowList != null) {
        	int times = 0;
            if (inqFollowList != null) {
                times = inqFollowList.size();
            }
            String str1 = "<font color=#000000>最近看房记录为</font>";
            String str2 = "<font color=#de6843>" + times + "</font>";
            String str3 = "<font color=#000000>次</font>";

            tvRecentRecord.setText(Html.fromHtml(str1 + str2 + str3));
        } else {
        	inqFollowList = new ArrayList<InqFollowListBean>();
        }
        
        adapter = new SeeHouseRecordAdapter(this, inqFollowList);
        lstRecord.setAdapter(adapter);
        lstRecord.setOnItemClickListener(this);
        
        if (!TextUtils.isEmpty(houseSourceId)) {
        	getHouseSourceFollowList(true);
        }
        tvBack.setOnClickListener(this);
        tvComplain.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.act_see_house_record_back:
            finish();
            break;
        case R.id.act_see_house_record_complain:  // 投诉
        	boolean loginStatus = ContentUtils.getUserLoginStatus(this);
        	if (loginStatus) {
        		Jumper.newJumper()
                .setAheadInAnimation(R.anim.activity_push_in_right)
                .setAheadOutAnimation(R.anim.activity_alpha_out)
                .setBackInAnimation(R.anim.activity_alpha_in)
                .setBackOutAnimation(R.anim.activity_push_out_right)
                .putBoolean(FeedbackActivity.INTENT_COMPLAIN, true)
                .jump(this, FeedbackActivity.class);
        	} else {
        		Jumper.newJumper()
                .setAheadInAnimation(R.anim.activity_push_in_right)
                .setAheadOutAnimation(R.anim.activity_alpha_out)
                .setBackInAnimation(R.anim.activity_alpha_in)
                .setBackOutAnimation(R.anim.activity_push_out_right)
                .putBoolean(FeedbackActivity.INTENT_COMPLAIN, true)
                .jump(this, LoginActivity.class);
        	}
        	
        	break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
        InqFollowListBean temp = inqFollowList.get(position);
        String phone = temp.getTel();
        if (!TextUtils.isEmpty(phone)) {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + phone));
            startActivity(intent);
        }
    }
    
    /**
     * 获取  客户看房记录
     * @param isRefresh
     */
    private void getHouseSourceFollowList(final boolean isRefresh) {
    	if (isRefresh) {
			pageIndex = 1;
		}
    	ActionImpl actionImpl = ActionImpl.newInstance(this);
    	actionImpl.getHouseSourceFollowList(houseSourceId, pageIndex, 10, new ResultHandlerCallback() {
			
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
				
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				// TODO 接口返回的数据看房记录和二手房详情返回的数据结构不一样，明天问问
				int total = result.getTotal();
				pageTotal = (int) Math.ceil(((double)total / (double)10));
				if (isRefresh) {
					inqFollowList.clear();
				}
				ArrayList<InqFollowListBean> temp = (ArrayList<InqFollowListBean>) JSON.parseArray(result.getData(), InqFollowListBean.class);
				pageIndex++;
				if (temp != null) {
					inqFollowList.addAll(temp);
				}
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
		
		if (pageIndex < pageTotal) {
			getHouseSourceFollowList(false);
		} else {
			pullView.onFooterLoadFinish();
		}
	}

	@Override
	public void onHeaderRefresh(AbPullToRefreshView view) {
		if (!TextUtils.isEmpty(houseSourceId)) {
			getHouseSourceFollowList(true);
		} else {
			pullView.onHeaderRefreshFinish();
		}
		
	}

}
