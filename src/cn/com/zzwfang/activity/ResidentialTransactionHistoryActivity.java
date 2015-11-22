package cn.com.zzwfang.activity;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;
import com.easemob.chat.core.ac;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.adapter.ResidentialTransactionHistoryAdapter;
import cn.com.zzwfang.bean.ResidentialTransactionHistoryBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.bean.TextValueBean;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.view.helper.PopViewHelper;
import cn.com.zzwfang.view.helper.PopViewHelper.OnConditionSelectListener;

/**
 * 小区成交历史
 * @author lzd
 *
 */
public class ResidentialTransactionHistoryActivity extends BaseActivity implements OnClickListener {
	
	public static final String INTENT_ESTATE_NAME = "intent_estate_name";
	
	public static final String INTENT_ESTATE_ID = "intent_estate_id";

	private TextView tvBack, tvTitle, tvFilter;
	private ListView lstHistory;
	
	private String estateId, estateName;
	
	private ArrayList<TextValueBean> filterConditions = new ArrayList<TextValueBean>();
	private OnConditionSelectListener onConditionSelectListener;
	private TextValueBean currentCondition;
	
	private ArrayList<ResidentialTransactionHistoryBean> historys = new ArrayList<ResidentialTransactionHistoryBean>();
	private ResidentialTransactionHistoryAdapter adapter;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		estateId = getIntent().getStringExtra(INTENT_ESTATE_ID);
		estateName = getIntent().getStringExtra(INTENT_ESTATE_NAME);
		initView();
		getTransactionHistoryList();
	}
	
	private void initView() {
		setContentView(R.layout.act_residential_transaction_history);
		
		tvBack = (TextView) findViewById(R.id.act_residential_transaction_history_back);
		tvTitle = (TextView) findViewById(R.id.act_residential_transaction_history_title);
		tvFilter = (TextView) findViewById(R.id.act_residential_transaction_history_filter);
		lstHistory = (ListView) findViewById(R.id.act_residential_transaction_history_lst);
		tvTitle.setText(estateName + "成交历史");
		
		adapter = new ResidentialTransactionHistoryAdapter(this, historys);
		lstHistory.setAdapter(adapter);
		
		TextValueBean zero = new TextValueBean();
		TextValueBean one = new TextValueBean();
		TextValueBean two = new TextValueBean();
		TextValueBean three = new TextValueBean();
		TextValueBean four = new TextValueBean();
		currentCondition = zero;
		
		zero.setText("不限");
		one.setText("一居");
		two.setText("二居");
		three.setText("三居");
		four.setText("四居");
		
		zero.setValue("0");
		one.setValue("1");
		two.setValue("2");
		three.setValue("3");
		four.setValue("4");
		
		filterConditions.add(zero);
		filterConditions.add(one);
		filterConditions.add(two);
		filterConditions.add(three);
		filterConditions.add(four);
		
		tvBack.setOnClickListener(this);
		tvFilter.setOnClickListener(this);
		
		onConditionSelectListener = new OnConditionSelectListener() {
			
			@Override
			public void onConditionSelect(TextValueBean txtValueBean) {
				currentCondition = txtValueBean;
				getTransactionHistoryList();
			}
		};
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_residential_transaction_history_back:
			finish();
			break;
		case R.id.act_residential_transaction_history_filter:
			PopViewHelper.showSelectTransactionHistoryFilterPopWindow(this, tvFilter, filterConditions, onConditionSelectListener);
			break;
		}
	}
	
	public void getTransactionHistoryList() {
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.getResidentialTransactionHistory(estateId, currentCondition.getValue(), new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				historys.clear();
				ArrayList<ResidentialTransactionHistoryBean> temp = (ArrayList<ResidentialTransactionHistoryBean>) JSON.parseArray(result.getData(), ResidentialTransactionHistoryBean.class);
				historys.addAll(temp);
				adapter.notifyDataSetChanged();
			}
		});
	}
}
