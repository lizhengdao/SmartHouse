package cn.com.zzwfang.activity;

import com.easemob.chat.core.ac;

import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 收支明细
 * 我的需求 -> 我的购房 -> 财务明细 （收支明细）
 * @author lzd
 *
 */
public class IncomeStatementActivity extends BaseActivity implements OnClickListener {

	public static final String INTENT_HOUSE_SOURCE_ID = "intent_house_source_id";
	
	private TextView tvBack;
	private ListView lstIncomeStatement;
	
	private String houseSourceId;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		houseSourceId = getIntent().getStringExtra(INTENT_HOUSE_SOURCE_ID);
		initView();
		getIncomeStatement();
	}
	
	private void initView() {
		
		setContentView(R.layout.act_income_statement);
		tvBack = (TextView) findViewById(R.id.act_income_statement_back);
		lstIncomeStatement = (ListView) findViewById(R.id.act_income_statement_lst);
		
		tvBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_income_statement_back:
			finish();
			break;
		}
		
	}
	
	private void getIncomeStatement() {
		if (TextUtils.isEmpty(houseSourceId)) {
			return;
		}
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.getIncomeStatement(houseSourceId, new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
