package cn.com.zzwfang.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.adapter.ClientInfoChangeAdapter;
import cn.com.zzwfang.adapter.HouseSourceInfoChangeAdapter;
import cn.com.zzwfang.bean.ClientInfoChangeBean;
import cn.com.zzwfang.bean.ClientProgressBean;
import cn.com.zzwfang.bean.FeeHunterHouseSourceProgress;
import cn.com.zzwfang.bean.PrpChangeBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.util.Jumper;

import com.alibaba.fastjson.JSON;

/**
 * 赏金猎人  进度详情 (房源进度和客户进度一个界面，接口不一样)
 * @author lzd
 *
 */
public class FeeHunterProgressDetailActivity extends BaseActivity implements OnClickListener {

	/**
	 * 从推荐房源（房源列表）跳进来，查看房源进度
	 */
	public static final String INTENT_HOUSE_SOURCE_ID = "house_source_id";
	
	/**
	 * 从我的客户（推荐客户）跳进来，查看客户进度
	 */
	public static final String INTENT_CLIENT_ID = "intent_client_id";
	
	
	private TextView tvBack, tvProgressStateOne, tvProgressStateTwo, tvProgressStateThree, tvProgressStateFour;
	private ImageView imgProgress;
	
	private TextView tvRecommendClientName, tvEstateName, tvTime, tvOperator, tvComplain;
	
	private ListView lstInfoChange;
	
	private ClientInfoChangeAdapter clientInfoChangeAdapter;
	
	private HouseSourceInfoChangeAdapter houseInfoChangeAdapter;
	
	private String houseSourceId;
	
	private String clientId;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		houseSourceId = getIntent().getStringExtra(INTENT_HOUSE_SOURCE_ID);
		clientId = getIntent().getStringExtra(INTENT_CLIENT_ID);
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
		
		tvRecommendClientName = (TextView) findViewById(R.id.act_fee_hunter_progress_recommend_name);
		tvEstateName = (TextView) findViewById(R.id.act_fee_hunter_progress_estate_name);
		tvTime = (TextView) findViewById(R.id.act_fee_hunter_progress_time);
		tvOperator = (TextView) findViewById(R.id.act_fee_hunter_progress_recommend_operator);
		tvComplain = (TextView) findViewById(R.id.act_fee_hunter_progress_complain);
		
		lstInfoChange = (ListView) findViewById(R.id.act_fee_hunter_progress_info_change);
		
		tvBack.setOnClickListener(this);
		tvComplain.setOnClickListener(this);
	}
	
	private void initData() {
		if (!TextUtils.isEmpty(houseSourceId)) {
			getHouseSourceProgress();
			getHouseSourceInfoChange();
		} else if (!TextUtils.isEmpty(clientId)) {
		    getClientSourceProgress();
		    getClientInfoChange();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_fee_hunter_progress_detail_back:
			finish();
			break;
		case R.id.act_fee_hunter_progress_complain: // 投诉
			boolean loginStatus = ContentUtils.getUserLoginStatus(this);
        	if (loginStatus) {
        		// 1(财务)，2（带看），3（进度）
        		String complainType = "3";
        		String complianId = null;
        		if (!TextUtils.isEmpty(houseSourceId)) {
        			complianId = houseSourceId;
        		} else if (!TextUtils.isEmpty(clientId)) {
        			complianId = clientId;
        		}
        		
        		if (TextUtils.isEmpty(complianId)) {
        			return;
        		}
        		Jumper.newJumper()
                .setAheadInAnimation(R.anim.activity_push_in_right)
                .setAheadOutAnimation(R.anim.activity_alpha_out)
                .setBackInAnimation(R.anim.activity_alpha_in)
                .setBackOutAnimation(R.anim.activity_push_out_right)
                .putString(ComplainActivity.INTENT_COMPLAIN_ID, complianId)
                .putString(ComplainActivity.INTENT_COMPLAIN_TYPE, complainType)
                .jump(this, ComplainActivity.class);
        	} else {
        		Jumper.newJumper()
                .setAheadInAnimation(R.anim.activity_push_in_right)
                .setAheadOutAnimation(R.anim.activity_alpha_out)
                .setBackInAnimation(R.anim.activity_alpha_in)
                .setBackOutAnimation(R.anim.activity_push_out_right)
                .jump(this, LoginActivity.class);
        	}
			break;
		}
	}
	
	private void rendUI(FeeHunterHouseSourceProgress progressData) {
		if (progressData != null) {
			tvRecommendClientName.setText(progressData.getName());
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
	
	/**
	 * 客户进度加载数据
	 * @param progressData
	 */
	private void rendClientProgressUI(ClientProgressBean progressData) {
		if (progressData != null) {
			tvRecommendClientName.setText(progressData.getOwnerName());
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
	
	
	
	
	/**
	 * 房源进度
	 */
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
	
	/**
	 * 房源信息变动   跟客户信息变动返回的数据结构一样
	 */
	private void getHouseSourceInfoChange() {
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		
		/**
         * 用户类型 0经济人，1普通会员，2赏金猎人
         */
        int userType = ContentUtils.getUserType(this);
        boolean isSale = false;
        if (userType == 1) {
            isSale = true;
        }
		actionImpl.getHouseSourceInfoChange(houseSourceId, isSale, new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				// TODO 房源信息变动
			    
			    PrpChangeBean prpChangeBean = JSON.parseObject(result.getData(), PrpChangeBean.class);
                if (prpChangeBean != null) {
                    houseInfoChangeAdapter = new HouseSourceInfoChangeAdapter(FeeHunterProgressDetailActivity.this, prpChangeBean.getFollow());
                    lstInfoChange.setAdapter(houseInfoChangeAdapter);
                }
			}
		});
	}
	
	/**
	 * 客户进度
	 */
	private void getClientSourceProgress() {
	    ActionImpl actionImpl = ActionImpl.newInstance(this);
	    actionImpl.getClientProgress(clientId, new ResultHandlerCallback() {
            
            @Override
            public void rc999(RequestEntity entity, Result result) {
            }
            
            @Override
            public void rc3001(RequestEntity entity, Result result) {
            }
            
            @Override
            public void rc0(RequestEntity entity, Result result) {
            	ClientProgressBean progressData = JSON.parseObject(result.getData(), ClientProgressBean.class);
                rendClientProgressUI(progressData);
            }
        });
	}
	
	/**
	 * 客户信息变动
	 */
	private void getClientInfoChange() {
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.getClientInfoChange(clientId, new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				ArrayList<ClientInfoChangeBean> clientInfoChanges = (ArrayList<ClientInfoChangeBean>) JSON.parseArray(result.getData(), ClientInfoChangeBean.class);
				clientInfoChangeAdapter = new ClientInfoChangeAdapter(FeeHunterProgressDetailActivity.this, clientInfoChanges);
				lstInfoChange.setAdapter(clientInfoChangeAdapter);
			}
		});
	}

}
