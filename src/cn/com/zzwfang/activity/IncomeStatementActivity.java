package cn.com.zzwfang.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.adapter.IncomeStatementAdapter;
import cn.com.zzwfang.bean.IncomeStatementBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.util.ContentUtils;

import com.alibaba.fastjson.JSON;

/**
 * 收支明细
 * 我的需求 -> 我的购房 -> 财务明细 （收支明细）
 * @author lzd
 *
 */
public class IncomeStatementActivity extends BaseActivity implements OnClickListener {

    public static final String INTENT_TYPE = "IncomeStatementActivity.type";
	public static final String INTENT_HOUSE_SOURCE_ID = "intent_house_source_id";
	
	private TextView tvBack;
	private ListView lstIncomeStatement;
	
	private ArrayList<IncomeStatementBean> incomeStatements = new ArrayList<IncomeStatementBean>();
	private IncomeStatementAdapter adapter;
	
	private String houseSourceId;
	
	/**
     * User/Income 新增参数 type 字符串
       在我是客户、我是业主 房源收支明细传递
       我是客户 传递“客户”字符串  我是业主 传递“业主” 字符串
     */
	private String type;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		type = getIntent().getStringExtra(INTENT_TYPE);
		houseSourceId = getIntent().getStringExtra(INTENT_HOUSE_SOURCE_ID);
		initView();
		getIncomeStatement();
	}
	
	private void initView() {
		
		setContentView(R.layout.act_income_statement);
		tvBack = (TextView) findViewById(R.id.act_income_statement_back);
		lstIncomeStatement = (ListView) findViewById(R.id.act_income_statement_lst);
		
		adapter = new IncomeStatementAdapter(this, incomeStatements);
		lstIncomeStatement.setAdapter(adapter);
		
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
		
		/**
         * 用户类型 0经济人，1普通会员，2赏金猎人
         */
        int userType = ContentUtils.getUserType(this);
        if (userType != 1) {
            type = null;
        }
		
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.getIncomeStatement(houseSourceId, type, new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				// TODO Auto-generated method stub
				ArrayList<IncomeStatementBean> temp = (ArrayList<IncomeStatementBean>) JSON.parseArray(result.getData(), IncomeStatementBean.class);
				if (temp != null) {
					incomeStatements.addAll(temp);
					adapter.notifyDataSetChanged();
				}
				
			}
		});
	}

}
