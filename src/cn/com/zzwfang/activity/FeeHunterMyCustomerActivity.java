package cn.com.zzwfang.activity;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.adapter.FeeHunterMyCustomerAdapter;
import cn.com.zzwfang.bean.CityBean;
import cn.com.zzwfang.bean.FeeHunterMyCustomerConditionBean;
import cn.com.zzwfang.bean.FeeHunterRecommendClientBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.view.helper.PopViewHelper;
import cn.com.zzwfang.view.helper.PopViewHelper.OnMyCustomerConditionSelectListener;

/**
 * 赏金猎人   我的客户页
 * @author lzd
 *
 */
public class FeeHunterMyCustomerActivity extends BaseActivity implements OnClickListener {

	private TextView tvBack, tvCondition;
	
	private ListView lstMyCustomer;
	
	private ArrayList<FeeHunterMyCustomerConditionBean> conditions = new ArrayList<FeeHunterMyCustomerConditionBean>();
	
	private FeeHunterMyCustomerConditionBean currentConditon;
	
	private OnMyCustomerConditionSelectListener onMyCustomerConditionSelectListener;
	
	private ArrayList<FeeHunterRecommendClientBean> myCustomers = new ArrayList<FeeHunterRecommendClientBean>();
	
	private FeeHunterMyCustomerAdapter adapter;
	
	private int pageIndex = 1;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initView();
		getMyCustomerCondition();
	}
	
	
	private void initView() {
		setContentView(R.layout.act_fee_hunter_my_customer);
		
		tvBack = (TextView) findViewById(R.id.act_fee_hunter_my_customer_back);
		tvCondition = (TextView) findViewById(R.id.act_fee_hunter_my_customer_condition);
		
		lstMyCustomer = (ListView) findViewById(R.id.act_fee_hunter_my_customer_lst);
		adapter = new FeeHunterMyCustomerAdapter(this, myCustomers);
		
		lstMyCustomer.setAdapter(adapter);
		
		tvBack.setOnClickListener(this);
		tvCondition.setOnClickListener(this);
		
		onMyCustomerConditionSelectListener = new OnMyCustomerConditionSelectListener() {
			
			@Override
			public void onMyCustomerConditonSelect(
					FeeHunterMyCustomerConditionBean conditon) {
				if (!conditon.equals(currentConditon)) {
					currentConditon = conditon;
					getMyCustomer();
				}
			}
		};
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_fee_hunter_my_customer_back:
			finish();
			break;
		case R.id.act_fee_hunter_my_customer_condition:
			PopViewHelper.showSelectMyCustomerConditionPopWindow(this, tvCondition, conditions, onMyCustomerConditionSelectListener);
			break;
		}
	}
	
	private void getMyCustomer() {
	    CityBean cityBean = ContentUtils.getCityBean(this);
	    if (cityBean == null) {
	        return;
	    }
		if (currentConditon != null) {
			String userId = ContentUtils.getUserId(this);
			ActionImpl actionImpl = ActionImpl.newInstance(this);
			
			actionImpl.getFeeHunterMyCustomerList(userId, cityBean.getSiteId(), currentConditon.getId(), 10, pageIndex, new ResultHandlerCallback() {
				
				@Override
				public void rc999(RequestEntity entity, Result result) {
					
				}
				
				@Override
				public void rc3001(RequestEntity entity, Result result) {
					
				}
				
				@Override
				public void rc0(RequestEntity entity, Result result) {
					
				}
			});
		}
		
	}
	
	private void getMyCustomerCondition() {
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.getFeeHunterMyCustomerCondition(new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				ArrayList<FeeHunterMyCustomerConditionBean> temp = (ArrayList<FeeHunterMyCustomerConditionBean>) JSON.parseArray(result.getData(), FeeHunterMyCustomerConditionBean.class);
				conditions.addAll(temp);
//				if (conditions.size() > 0) {
//					currentConditon = conditions.get(0);
//					if (currentConditon.getChildren().size() > 0) {
//						currentConditon = currentConditon.getChildren().get(0);
//					}
//					getMyCustomer();
//				}
			}
		});
				
	}
}
