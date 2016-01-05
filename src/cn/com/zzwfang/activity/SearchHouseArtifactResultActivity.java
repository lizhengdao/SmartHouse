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
import cn.com.zzwfang.adapter.SearchHouseArtifactResultAdapter;
import cn.com.zzwfang.bean.AgentBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.bean.SearchHouseArtifactResultBean;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.util.Jumper;

import com.alibaba.fastjson.JSON;

/**
 * 搜房神器结果列表（我要咨询页）
 * @author lzd
 *
 */
public class SearchHouseArtifactResultActivity extends BaseActivity implements OnClickListener, OnItemClickListener {

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
	public static final String INTENT_SEARCH_HOUSE_TRADE_TYPE = "intent_search_house_trade_type";
	
	private TextView tvBack, tvChangeAnother;
	private ListView lstSearchHouseArtifactResult;
	private ArrayList<SearchHouseArtifactResultBean> artifactResult = new ArrayList<SearchHouseArtifactResultBean>();
	private SearchHouseArtifactResultAdapter adapter;
	private int pageIndex = 1;
	
	private int type;
	private String budget;
	private String where;
	private String monthlyPayRange;
	private String rooms;
	private String label;
	
	private String partPrice;  //   首付范围
	
	private int tradeType = 0;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		Intent intent = getIntent();
		type = intent.getIntExtra(INTENT_PAY_TYPE, 1);
		budget = intent.getStringExtra(INTENT_BUDGET);
		where = intent.getStringExtra(INTENT_WHERE);
		monthlyPayRange = intent.getStringExtra(INTENT_MONTHLY_PAY);
		rooms = intent.getStringExtra(INTENT_ROOMS);
		label = intent.getStringExtra(INTENT_REMARKS);
		tradeType = intent.getIntExtra(INTENT_SEARCH_HOUSE_TRADE_TYPE, 0);
		initView();
	}
	
	private void initView() {
		setContentView(R.layout.act_search_house_artifact_result);
		
		tvBack = (TextView) findViewById(R.id.act_search_house_artifact_result_back);
		tvChangeAnother = (TextView) findViewById(R.id.act_search_house_artifact_result_change_another);
		lstSearchHouseArtifactResult = (ListView) findViewById(R.id.lst_search_house_artifact_result);
		
		adapter = new SearchHouseArtifactResultAdapter(this, artifactResult);
		lstSearchHouseArtifactResult.setAdapter(adapter);
		
		tvBack.setOnClickListener(this);
		tvChangeAnother.setOnClickListener(this);
		
		getSearchHouseArtifactResult(true);
		lstSearchHouseArtifactResult.setOnItemClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_search_house_artifact_result_back:
			finish();
			break;
		case R.id.act_search_house_artifact_result_change_another:  // 换一批
			getSearchHouseArtifactResult(true);
			break;
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		SearchHouseArtifactResultBean temp = artifactResult.get(position);
		
		// 跳经纪人详情页
		AgentBean agentBean = new AgentBean();
		agentBean.setAgentId(temp.getId());
		agentBean.setName(temp.getName());
		agentBean.setPhoto(temp.getPhoto());
		agentBean.setTel(temp.getPhone());
		Jumper.newJumper()
        .setAheadInAnimation(R.anim.activity_push_in_right)
        .setAheadOutAnimation(R.anim.activity_alpha_out)
        .setBackInAnimation(R.anim.activity_alpha_in)
        .setBackOutAnimation(R.anim.activity_push_out_right)
        .putSerializable(BrokerInfoActivity.INTENT_AGENT_DATA, agentBean)
        .jump(this, BrokerInfoActivity.class);
	}

	
	private void getSearchHouseArtifactResult(final boolean isRefresh) {
		
		/**
		 * 返回搜房神器列表
		 * @param allPrice   总价范围
		 * @param PartPrice  首付范围
		 * @param type   类型
		 * @param area   区域
		 * @param rooms   几房
		 * @param monthlyPay  月供
		 * @param label   补充信息
		 * @param pageIndex   当前页
		 * @param callback
		 */
		
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.getSearchHouseArtifactResut(budget, partPrice, type, where, rooms, monthlyPayRange, label, tradeType, pageIndex, new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				ArrayList<SearchHouseArtifactResultBean> temp = (ArrayList<SearchHouseArtifactResultBean>) JSON.parseArray(result.getData(), SearchHouseArtifactResultBean.class);
				if (temp != null) {
					if (isRefresh) {
						artifactResult.clear();
					}
					artifactResult.addAll(temp);
					pageIndex++;
					adapter.notifyDataSetChanged();
				}
			}
		});
	}

	
}
