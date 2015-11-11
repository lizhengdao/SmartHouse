package cn.com.zzwfang.activity;

import com.alibaba.fastjson.JSON;

import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.NewHouseDetailBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class NewHouseDetailActivity extends BaseActivity implements OnClickListener {

	public static final String INTENT_ESTATE_ID = "intent_estate_id";
	
	private TextView tvBack, tvPageTitle;
	
	private String estateId;
	
	private NewHouseDetailBean newHouseDetailBean;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		estateId = getIntent().getStringExtra(INTENT_ESTATE_ID);
		initView();
		getNewHouseDetail(estateId);
	}
	
	private void initView() {
		setContentView(R.layout.act_new_house_detail);
		
		tvBack = (TextView) findViewById(R.id.act_new_house_detail_back);
		tvPageTitle = (TextView) findViewById(R.id.act_new_house_detail_title);
		
		tvBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_new_house_detail_back:
			finish();
			break;
		}
	}
	
	private void rendUI() {
		if (newHouseDetailBean != null) {
			tvPageTitle.setText(newHouseDetailBean.getName());
		}
		
	}
	
	private void getNewHouseDetail(String estateId) {
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.getNewHouseDetail(estateId, new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				newHouseDetailBean = JSON.parseObject(result.getData(), NewHouseDetailBean.class);
				rendUI();
			}
		});
	}
	
	
}
