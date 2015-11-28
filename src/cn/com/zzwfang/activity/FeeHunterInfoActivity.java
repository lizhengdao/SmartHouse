package cn.com.zzwfang.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.util.Jumper;

public class FeeHunterInfoActivity extends BaseActivity implements OnClickListener {

	private TextView tvBack, tvMyCustomer, tvRecommendHouseSourceList, tvFeeHunterMsg, tvFeeHunterRule;
	private TextView tvBindedCards;
	private LinearLayout lltRecommendCustomer, lltRecommendSell;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initView();
	}
	
	private void initView() {
		setContentView(R.layout.act_fee_hunter_info);
		tvBack = (TextView) findViewById(R.id.act_fee_hunter_info_back);
		
		lltRecommendCustomer = (LinearLayout) findViewById(R.id.act_fee_hunter_info_recommend_customer);
		lltRecommendSell = (LinearLayout) findViewById(R.id.act_fee_hunter_info_recommend_sell);
		tvMyCustomer = (TextView) findViewById(R.id.act_fee_hunter_info_my_customer);
		
		//  推荐房源列表
		tvRecommendHouseSourceList = (TextView) findViewById(R.id.act_fee_hunter_info_recommend_house_source_list);
		tvFeeHunterMsg = (TextView) findViewById(R.id.act_fee_hunter_info_fee_hunter_msg);
		tvFeeHunterRule = (TextView) findViewById(R.id.act_fee_hunter_info_hunter_rule);
		
		tvBindedCards = (TextView) findViewById(R.id.act_fee_hunter_info_binded_card);
		
		tvBack.setOnClickListener(this);
		lltRecommendCustomer.setOnClickListener(this);
		lltRecommendSell.setOnClickListener(this);
		tvMyCustomer.setOnClickListener(this);
		tvRecommendHouseSourceList.setOnClickListener(this);
		tvFeeHunterMsg.setOnClickListener(this);
		tvFeeHunterRule.setOnClickListener(this);
		tvBindedCards.setOnClickListener(this);
	}
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_fee_hunter_info_back:
			finish();
			break;
		case R.id.act_fee_hunter_info_recommend_customer:   //  推荐买房  改成 推荐客户  跳推荐二手房页
			Jumper.newJumper()
	        .setAheadInAnimation(R.anim.activity_push_in_right)
	        .setAheadOutAnimation(R.anim.activity_alpha_out)
	        .setBackInAnimation(R.anim.activity_alpha_in)
	        .setBackOutAnimation(R.anim.activity_push_out_right)
	        .jump(FeeHunterInfoActivity.this, FeeHunterRecommendCustomerActivity.class);
			break;
		case R.id.act_fee_hunter_info_recommend_sell:  //  推荐卖房 改成推荐房源  跳推荐业主表单页
			Jumper.newJumper()
	        .setAheadInAnimation(R.anim.activity_push_in_right)
	        .setAheadOutAnimation(R.anim.activity_alpha_out)
	        .setBackInAnimation(R.anim.activity_alpha_in)
	        .setBackOutAnimation(R.anim.activity_push_out_right)
	        .jump(FeeHunterInfoActivity.this, FeeHunterRecommendOwnerActivity.class);
			break;
		case R.id.act_fee_hunter_info_my_customer:   //  我的客户
			Jumper.newJumper()
	        .setAheadInAnimation(R.anim.activity_push_in_right)
	        .setAheadOutAnimation(R.anim.activity_alpha_out)
	        .setBackInAnimation(R.anim.activity_alpha_in)
	        .setBackOutAnimation(R.anim.activity_push_out_right)
	        .jump(this, FeeHunterMyCustomerActivity.class);
			break;
		case R.id.act_fee_hunter_info_recommend_house_source_list:   //  跳 房源列表
			Jumper.newJumper()
	        .setAheadInAnimation(R.anim.activity_push_in_right)
	        .setAheadOutAnimation(R.anim.activity_alpha_out)
	        .setBackInAnimation(R.anim.activity_alpha_in)
	        .setBackOutAnimation(R.anim.activity_push_out_right)
	        .jump(FeeHunterInfoActivity.this, FeeHunterRecommendHouseSourceListActivity.class);
			break;
		case R.id.act_fee_hunter_info_fee_hunter_msg:   //  赏金信息
			Jumper.newJumper()
	        .setAheadInAnimation(R.anim.activity_push_in_right)
	        .setAheadOutAnimation(R.anim.activity_alpha_out)
	        .setBackInAnimation(R.anim.activity_alpha_in)
	        .setBackOutAnimation(R.anim.activity_push_out_right)
	        .jump(FeeHunterInfoActivity.this, FeeHunterWalletActivity.class);
			break;
		case R.id.act_fee_hunter_info_hunter_rule:   // 活动规则
			Jumper.newJumper()
	        .setAheadInAnimation(R.anim.activity_push_in_right)
	        .setAheadOutAnimation(R.anim.activity_alpha_out)
	        .setBackInAnimation(R.anim.activity_alpha_in)
	        .setBackOutAnimation(R.anim.activity_push_out_right)
	        .jump(FeeHunterInfoActivity.this, FeeHunterRuleActivity.class);
			break;
		case R.id.act_fee_hunter_info_binded_card:  // 已绑定的银行卡
			Jumper.newJumper()
	        .setAheadInAnimation(R.anim.activity_push_in_right)
	        .setAheadOutAnimation(R.anim.activity_alpha_out)
	        .setBackInAnimation(R.anim.activity_alpha_in)
	        .setBackOutAnimation(R.anim.activity_push_out_right)
	        .jump(FeeHunterInfoActivity.this, FeeHunterWalletActivity.class);
			break;
		}
	}
	
	
}
