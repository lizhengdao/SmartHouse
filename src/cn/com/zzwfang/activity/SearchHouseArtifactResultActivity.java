package cn.com.zzwfang.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.adapter.SearchHouseArtifactResultAdapter;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.bean.SearchHouseArtifactResultBean;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.pullview.AbPullToRefreshView;
import cn.com.zzwfang.pullview.AbPullToRefreshView.OnFooterLoadListener;
import cn.com.zzwfang.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import cn.com.zzwfang.util.Jumper;

import com.alibaba.fastjson.JSON;

/**
 * 搜房神器结果列表（我要咨询页）
 * @author lzd
 *
 */
public class SearchHouseArtifactResultActivity extends BaseActivity implements OnClickListener,
OnHeaderRefreshListener, OnFooterLoadListener, OnItemClickListener {

	public static final String INTENT_SEARCH_HOUSE_ARTIFACT_PROXY = "search_house_artifact_proxy";
	/**
     * 付款方式一次性或者按揭（Type,值为1或者0）
     */
    public static final String INTENT_PAY_TYPE = "intent_pay_type";
	public static final String INTENT_BUDGET = "intent_budget";
	public static final String INTENT_WHERE = "intent_where";
	public static final String INTENT_MONTHLY_PAY = "intent_monthly_pay";
	public static final String INTENT_ROOMS = "intent_rooms";
	public static final String INTENT_REMARKS = "intent_remarks";
	
	private TextView tvBack, tvChangeAnother;
	private AbPullToRefreshView pullView;
	private ListView lstSearchHouseArtifactResult;
	private ArrayList<SearchHouseArtifactResultBean> artifactResult = new ArrayList<SearchHouseArtifactResultBean>();
	private SearchHouseArtifactResultAdapter adapter;
	private int pageIndex = 1;
	private String house = "1";
	private int pageTotal = 0;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initView();
	}
	
	private void initView() {
		setContentView(R.layout.act_search_house_artifact_result);
		
		tvBack = (TextView) findViewById(R.id.act_search_house_artifact_result_back);
		tvChangeAnother = (TextView) findViewById(R.id.act_search_house_artifact_result_change_another);
		pullView = (AbPullToRefreshView) findViewById(R.id.pull_search_house_artifact_result);
		lstSearchHouseArtifactResult = (ListView) findViewById(R.id.lst_search_house_artifact_result);
		
		adapter = new SearchHouseArtifactResultAdapter(this, artifactResult);
		lstSearchHouseArtifactResult.setAdapter(adapter);
		
		tvBack.setOnClickListener(this);
		tvChangeAnother.setOnClickListener(this);
		
		pullView.setPullRefreshEnable(true);
		pullView.setLoadMoreEnable(true);
		pullView.setOnHeaderRefreshListener(this);
		pullView.setOnFooterLoadListener(this);
		
		getSearchHouseArtifactResult(house, true);
		lstSearchHouseArtifactResult.setOnItemClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_search_house_artifact_result_back:
			finish();
			break;
		case R.id.act_search_house_artifact_result_change_another:  // 换一批
			break;
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		SearchHouseArtifactResultBean temp = artifactResult.get(position);
		Jumper.newJumper()
        .setAheadInAnimation(R.anim.activity_push_in_right)
        .setAheadOutAnimation(R.anim.activity_alpha_out)
        .setBackInAnimation(R.anim.activity_alpha_in)
        .setBackOutAnimation(R.anim.activity_push_out_right)
        .putSerializable(INTENT_SEARCH_HOUSE_ARTIFACT_PROXY, temp)
        .jump(this, ProxyDetailActivity.class);
	}

	@Override
	public void onFooterLoad(AbPullToRefreshView view) {
		getSearchHouseArtifactResult(house, false);
	}

	@Override
	public void onHeaderRefresh(AbPullToRefreshView view) {
		getSearchHouseArtifactResult(house, true);
	}
	
	private void getSearchHouseArtifactResult(String house, final boolean isRefresh) {
		if (isRefresh) {
			pageIndex = 1;
		} else {
			if (pageIndex > pageTotal) {
				pullView.onHeaderRefreshFinish();
				return;
			}
		}
		
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.getSearchHouseArtifactResut(house, pageIndex, new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				int total = result.getTotal();
				pageTotal = (int) Math.ceil(((double)total / (double)10));
				ArrayList<SearchHouseArtifactResultBean> temp = (ArrayList<SearchHouseArtifactResultBean>) JSON.parseArray(result.getData(), SearchHouseArtifactResultBean.class);
				if (temp != null) {
					if (isRefresh) {
						artifactResult.clear();
					}
					artifactResult.addAll(temp);
					pageIndex++;
					adapter.notifyDataSetChanged();
				}
				if (isRefresh) {
					pullView.onHeaderRefreshFinish();
				} else {
					pullView.onFooterLoadFinish();
				}
			}
		});
	}

	
}
