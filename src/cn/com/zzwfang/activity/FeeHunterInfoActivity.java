package cn.com.zzwfang.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.util.Jumper;
import cn.com.zzwfang.view.helper.PopViewHelper;
import cn.com.zzwfang.view.helper.PopViewHelper.OnFeeHunterRecommendHouseTypeSelecetListener;

public class FeeHunterInfoActivity extends BaseActivity implements OnClickListener {

	private TextView tvBack, tvMyCustomer, tvBuyProgress, tvOwnerProgress, tvFeeHunterMsg, tvFeeHunterRule;
	
	private TextView tvBindedCards;
	
	private LinearLayout lltRecommendBuy, lltRecommendSell, lltRecommendCustomer, lltRecommendOwner;
	
	private OnFeeHunterRecommendHouseTypeSelecetListener recommendBuyListener;
	
	private OnFeeHunterRecommendHouseTypeSelecetListener recommendCustomerListener;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initView();
		initListener();
	}
	
	private void initView() {
		setContentView(R.layout.act_fee_hunter_info);
		tvBack = (TextView) findViewById(R.id.act_fee_hunter_info_back);
		
		lltRecommendBuy = (LinearLayout) findViewById(R.id.act_fee_hunter_info_recommend_buy);
		lltRecommendSell = (LinearLayout) findViewById(R.id.act_fee_hunter_info_recommend_sell);
		lltRecommendCustomer = (LinearLayout) findViewById(R.id.act_fee_hunter_info_recommend_customer);
		lltRecommendOwner = (LinearLayout) findViewById(R.id.act_fee_hunter_info_recommend_owner);
		tvMyCustomer = (TextView) findViewById(R.id.act_fee_hunter_info_my_customer);
		
		tvBuyProgress = (TextView) findViewById(R.id.act_fee_hunter_info_buy_progress);
		tvOwnerProgress = (TextView) findViewById(R.id.act_fee_hunter_info_owner_progress);
		tvFeeHunterMsg = (TextView) findViewById(R.id.act_fee_hunter_info_fee_hunter_msg);
		tvFeeHunterRule = (TextView) findViewById(R.id.act_fee_hunter_info_hunter_rule);
		
		tvBindedCards = (TextView) findViewById(R.id.act_fee_hunter_info_binded_card);
		
		tvBack.setOnClickListener(this);
		
		lltRecommendBuy.setOnClickListener(this);
		lltRecommendSell.setOnClickListener(this);
		lltRecommendCustomer.setOnClickListener(this);
		lltRecommendOwner.setOnClickListener(this);
		tvMyCustomer.setOnClickListener(this);
		tvBuyProgress.setOnClickListener(this);
		tvOwnerProgress.setOnClickListener(this);
		tvFeeHunterMsg.setOnClickListener(this);
		tvFeeHunterRule.setOnClickListener(this);
		tvBindedCards.setOnClickListener(this);
	}
	
	private void initListener() {
		recommendBuyListener = new OnFeeHunterRecommendHouseTypeSelecetListener() {
			
			@Override
			public void onFeeHunterRecommendHouseTypeSelecet(int type) {
				switch (type) {
				case OnFeeHunterRecommendHouseTypeSelecetListener.FEE_HUNTER_HOUSE_TYPE_NEW_HOUSE:  // 推荐新房
					Jumper.newJumper()
			        .setAheadInAnimation(R.anim.activity_push_in_right)
			        .setAheadOutAnimation(R.anim.activity_alpha_out)
			        .setBackInAnimation(R.anim.activity_alpha_in)
			        .setBackOutAnimation(R.anim.activity_push_out_right)
			        .jump(FeeHunterInfoActivity.this, FeeHunterRecommendNewHouseActivity.class);
					break;
				case OnFeeHunterRecommendHouseTypeSelecetListener.FEE_HUNTER_HOUSE_TYPE_SECOND_HAND_HOUSE:  //  推荐二手房
					Jumper.newJumper()
			        .setAheadInAnimation(R.anim.activity_push_in_right)
			        .setAheadOutAnimation(R.anim.activity_alpha_out)
			        .setBackInAnimation(R.anim.activity_alpha_in)
			        .setBackOutAnimation(R.anim.activity_push_out_right)
			        .jump(FeeHunterInfoActivity.this, FeeHunterRecommendSecondHandHouseActivity.class);
					break;
				}
			}
		};
		
		recommendCustomerListener = new OnFeeHunterRecommendHouseTypeSelecetListener() {
			
			@Override
			public void onFeeHunterRecommendHouseTypeSelecet(int type) {
				switch (type) {
				case OnFeeHunterRecommendHouseTypeSelecetListener.FEE_HUNTER_HOUSE_TYPE_NEW_HOUSE:  // 推荐新房
					Jumper.newJumper()
			        .setAheadInAnimation(R.anim.activity_push_in_right)
			        .setAheadOutAnimation(R.anim.activity_alpha_out)
			        .setBackInAnimation(R.anim.activity_alpha_in)
			        .setBackOutAnimation(R.anim.activity_push_out_right)
			        .jump(FeeHunterInfoActivity.this, FeeHunterRecommendNewHouseActivity.class);
					break;
				case OnFeeHunterRecommendHouseTypeSelecetListener.FEE_HUNTER_HOUSE_TYPE_SECOND_HAND_HOUSE:  //  推荐二手房
					Jumper.newJumper()
			        .setAheadInAnimation(R.anim.activity_push_in_right)
			        .setAheadOutAnimation(R.anim.activity_alpha_out)
			        .setBackInAnimation(R.anim.activity_alpha_in)
			        .setBackOutAnimation(R.anim.activity_push_out_right)
			        .jump(FeeHunterInfoActivity.this, FeeHunterRecommendSecondHandHouseActivity.class);
					break;
				}
			}
		};
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_fee_hunter_info_back:
			finish();
			break;
		case R.id.act_fee_hunter_info_recommend_buy:   //  推荐买房  推荐客户一样的逻辑
			PopViewHelper.showFeeHunterRecommendBuyAndSell(this, getWindow().getDecorView(), recommendBuyListener);
			break;
		case R.id.act_fee_hunter_info_recommend_sell:  //  推荐卖房
			Jumper.newJumper()
	        .setAheadInAnimation(R.anim.activity_push_in_right)
	        .setAheadOutAnimation(R.anim.activity_alpha_out)
	        .setBackInAnimation(R.anim.activity_alpha_in)
	        .setBackOutAnimation(R.anim.activity_push_out_right)
	        .jump(FeeHunterInfoActivity.this, FeeHunterRecommendSecondHandHouseActivity.class);
			break;
		case R.id.act_fee_hunter_info_recommend_customer:  // 推荐客户
			PopViewHelper.showFeeHunterRecommendBuyAndSell(this, getWindow().getDecorView(), recommendCustomerListener);
			break;
		case R.id.act_fee_hunter_info_recommend_owner:   //  推荐业主
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
		case R.id.act_fee_hunter_info_buy_progress:    //   购买进度
			Jumper.newJumper()
	        .setAheadInAnimation(R.anim.activity_push_in_right)
	        .setAheadOutAnimation(R.anim.activity_alpha_out)
	        .setBackInAnimation(R.anim.activity_alpha_in)
	        .setBackOutAnimation(R.anim.activity_push_out_right)
	        .jump(FeeHunterInfoActivity.this, FeeHunterBuyProgressActivity.class);
			break;
		case R.id.act_fee_hunter_info_owner_progress:   //  业主进度
			Jumper.newJumper()
	        .setAheadInAnimation(R.anim.activity_push_in_right)
	        .setAheadOutAnimation(R.anim.activity_alpha_out)
	        .setBackInAnimation(R.anim.activity_alpha_in)
	        .setBackOutAnimation(R.anim.activity_push_out_right)
	        .jump(FeeHunterInfoActivity.this, FeeHunterOwnerProgressActivity.class);
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
