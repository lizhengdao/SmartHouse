package cn.com.zzwfang.activity;

import com.alibaba.fastjson.JSON;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.MyDemandInfoBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.util.ContentUtils;

/**
 * 我的需求 ->  我的需求页
 * @author lzd
 *
 */
public class MyDemandInfoActivity extends BaseActivity implements OnClickListener {

	private TextView tvBack;
	
	private TextView tvType, tvInterest, tvMonthlyIncome, tvDecoration,
	tvDirection, tvSquare, tvHouseType;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		initView();
		getMyDemandInfo();
	}
	
	private void initView() {
		setContentView(R.layout.act_my_demand_info);
		
		tvBack = (TextView) findViewById(R.id.act_my_demand_info_back);
		tvType = (TextView) findViewById(R.id.act_my_demand_info_type);
		tvInterest = (TextView) findViewById(R.id.act_my_demand_info_interest);
		tvMonthlyIncome = (TextView) findViewById(R.id.act_my_demand_info_monthly_income);
		tvDecoration = (TextView) findViewById(R.id.act_my_demand_info_decoration);
		tvDirection = (TextView) findViewById(R.id.act_my_demand_info_direction);
		tvSquare = (TextView) findViewById(R.id.act_my_demand_info_square);
		tvHouseType = (TextView) findViewById(R.id.act_my_demand_info_house_type);
		
		tvBack.setOnClickListener(this);
	}
	
	private void rendUI(MyDemandInfoBean myDemandInfoBean) {
		if (myDemandInfoBean != null) {
			tvType.setText(myDemandInfoBean.getType());
			tvInterest.setText(myDemandInfoBean.getIntention());
			tvMonthlyIncome.setText(myDemandInfoBean.getMonthlyPay());
			tvDecoration.setText(myDemandInfoBean.getPropertyDecoration());
			tvDirection.setText(myDemandInfoBean.getPropertyDirection());
			tvSquare.setText(myDemandInfoBean.getSquareMin() + "㎡至" + myDemandInfoBean.getSquareMax() + "㎡");
			tvHouseType.setText(myDemandInfoBean.getHouseType());
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_my_demand_info_back:
			finish();
			break;
		}
	}
	
	private void getMyDemandInfo() {
		
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		String userId = ContentUtils.getUserId(this);
		actionImpl.getMyDemandInfo(userId, new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				MyDemandInfoBean myDemandInfoBean = JSON.parseObject(result.getData(), MyDemandInfoBean.class);
				rendUI(myDemandInfoBean);
			}
		});
	}
}
