package cn.com.zzwfang.activity;

import com.alibaba.fastjson.JSON;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.bean.SecondHandHouseDetailBean;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;

/**
 * 二手房详情
 * @author lzd
 *
 */
public class SecondHandHouseDetailActivity extends BaseActivity implements OnClickListener {

	private TextView tvBack, tvTitle, tvShare;
	
	private SecondHandHouseDetailBean secondHandHouseDetail;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initView();
		getSecondHandHouseDetail();
	}
	
	private void initView() {
		setContentView(R.layout.act_second_hand_house_detail);
		
		tvBack = (TextView) findViewById(R.id.act_search_house_artifact_back);
		tvTitle = (TextView) findViewById(R.id.act_search_house_artifact_title);
		tvShare = (TextView) findViewById(R.id.act_search_house_artifact_share);
		
		tvBack.setOnClickListener(this);
		tvShare.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_search_house_artifact_back:
			finish();
			break;
		case R.id.act_search_house_artifact_title:  // 详情title
			break;
		case R.id.act_search_house_artifact_share:   //  分享
			break;
		}
	}
	
	private void rendUI() {
		if (secondHandHouseDetail != null) {
			tvTitle.setText(secondHandHouseDetail.getTitle());
		}
	}
	
	private void getSecondHandHouseDetail() {
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.getSecondHandHouseDetail("111111", new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				secondHandHouseDetail = JSON.parseObject(result.getData(), SecondHandHouseDetailBean.class);
				rendUI();
			}
		});
	}
}
