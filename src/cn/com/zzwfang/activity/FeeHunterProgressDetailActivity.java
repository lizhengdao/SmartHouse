package cn.com.zzwfang.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.FeeHunterHouseSourceProgress;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;

import com.alibaba.fastjson.JSON;

/**
 * 赏金猎人  进度详情
 * @author lzd
 *
 */
public class FeeHunterProgressDetailActivity extends BaseActivity implements OnClickListener {

	public static final String INTENT_HOUSE_SOURCE_ID = "house_source_id";
	private TextView tvBack, tvProgressStateOne, tvProgressStateTwo, tvProgressStateThree, tvProgressStateFour;
	private ImageView imgProgress;
	
	private TextView tvEstateName, tvTime, tvOperator;
	
	private String houseSourceId;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		houseSourceId = getIntent().getStringExtra(INTENT_HOUSE_SOURCE_ID);
		initView();
		initData();
	}
	
	private void initView() {
		setContentView(R.layout.act_fee_hunter_progress_detail);
		
		tvBack = (TextView) findViewById(R.id.act_fee_hunter_progress_detail_back);
		tvProgressStateOne = (TextView) findViewById(R.id.act_fee_hunter_progress_one);
		tvProgressStateTwo = (TextView) findViewById(R.id.act_fee_hunter_progress_two);
		tvProgressStateThree = (TextView) findViewById(R.id.act_fee_hunter_progress_three);
		tvProgressStateFour = (TextView) findViewById(R.id.act_fee_hunter_progress_four);
		
		imgProgress = (ImageView) findViewById(R.id.act_fee_hunter_progress_img);
		
		tvEstateName = (TextView) findViewById(R.id.act_fee_hunter_progress_estate_name);
		tvTime = (TextView) findViewById(R.id.act_fee_hunter_progress_time);
		tvOperator = (TextView) findViewById(R.id.act_fee_hunter_progress_recommend_operator);
		
		tvBack.setOnClickListener(this);
	}
	
	private void initData() {
		if (!TextUtils.isEmpty(houseSourceId)) {
			getHouseSourceProgress();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_fee_hunter_progress_detail_back:
			finish();
			break;
		}
	}
	
	private void rendUI(FeeHunterHouseSourceProgress progressData) {
		if (progressData != null) {
			tvEstateName.setText(progressData.getEstName());
			tvTime.setText("时间：" + progressData.getPublishDate());
			tvOperator.setText(progressData.getHandUser());
			ArrayList<String> states = progressData.getState();
			int size = states.size();
			if (size >= 4) {
				tvProgressStateOne.setText(states.get(0));
				tvProgressStateTwo.setText(states.get(1));
				tvProgressStateThree.setText(states.get(2));
				tvProgressStateFour.setText(states.get(3));
			}
			String selectState = progressData.getSelectedState();
			int position = -1;
			for (int i = 0; i < size; i++) {
				if (states.get(i).equals(selectState)) {
					position = i;
					break;
				}
			}
			if (position > -1) {
				switch (position) {
				case 0:
					imgProgress.setImageResource(R.drawable.ic_progress_one);
					tvProgressStateOne.setTextColor(getResources().getColor(R.color.color_f8a20d));
					break;
				case 1:
					imgProgress.setImageResource(R.drawable.ic_progress_two);
					tvProgressStateTwo.setTextColor(getResources().getColor(R.color.color_f8a20d));
					break;
				case 2:
					imgProgress.setImageResource(R.drawable.ic_progress_three);
					tvProgressStateThree.setTextColor(getResources().getColor(R.color.color_f8a20d));
					break;
				case 3:
					imgProgress.setImageResource(R.drawable.ic_progress_four);
					tvProgressStateFour.setTextColor(getResources().getColor(R.color.color_f8a20d));
					break;
				}
			}
		}
	}
	
	private void getHouseSourceProgress() {
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.getHouseSourceProgress(houseSourceId, new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				FeeHunterHouseSourceProgress progressData = JSON.parseObject(result.getData(), FeeHunterHouseSourceProgress.class);
				rendUI(progressData);
			}
		});
	}
	
}
