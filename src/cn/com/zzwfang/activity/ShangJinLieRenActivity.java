package cn.com.zzwfang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.util.Jumper;

public class ShangJinLieRenActivity extends BaseActivity implements OnClickListener {

	/**
	 * 登录
	 */
	public static final int CODE_LOGIN_FROM_Fee_ShangJinLieRen = 120;
	
	public static final int CODE_REGISTER_FEE_HUNTER = 121;
	
    private static final int CODE_BIND_CARD_INFO = 100;
	private TextView tvBack, tvRegister, tvLogin;
	
	
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		initView();
	}
	
	private void initView() {
		setContentView(R.layout.act_shangjinlieren);
		
		tvBack = (TextView) findViewById(R.id.act_shangjinlieren_back);
		tvRegister = (TextView) findViewById(R.id.act_shangjinlieren_register_tv);
		tvLogin = (TextView) findViewById(R.id.act_shangjinlieren_login_tv);
		
		tvBack.setOnClickListener(this);
		tvRegister.setOnClickListener(this);
		tvLogin.setOnClickListener(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		/**
		 * 用户类型    0经济人，1普通会员，2赏金猎人
		 */
		boolean loginStatus = ContentUtils.getUserLoginStatus(this);
		if (loginStatus) {  // 已登录
			int userType = ContentUtils.getUserType(this);
			if (userType == 1) {  // 普通会员
				// TODO 已登录的普通会员还显示登录按钮不
				tvLogin.setVisibility(View.GONE);
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_shangjinlieren_back:
			finish();
			break;
		case R.id.act_shangjinlieren_register_tv:  // 注册赏金猎人
			Jumper.newJumper()
            .setAheadInAnimation(R.anim.activity_push_in_right)
            .setAheadOutAnimation(R.anim.activity_alpha_out)
            .setBackInAnimation(R.anim.activity_alpha_in)
            .setBackOutAnimation(R.anim.activity_push_out_right)
            .putInt(RegisterActivity.INTENT_REGISTER_TYPE, 2)
            .jumpForResult(this, RegisterActivity.class, CODE_REGISTER_FEE_HUNTER);
//            .jump(this, RegisterActivity.class);
			
//			Jumper.newJumper()
//            .setAheadInAnimation(R.anim.activity_push_in_right)
//            .setAheadOutAnimation(R.anim.activity_alpha_out)
//            .setBackInAnimation(R.anim.activity_alpha_in)
//            .setBackOutAnimation(R.anim.activity_push_out_right)
//            .jumpForResult(this, FillBankCardInfoActivity.class, CODE_BIND_CARD_INFO);
			break;
		case R.id.act_shangjinlieren_login_tv:  // 登录
			
			Jumper.newJumper()
            .setAheadInAnimation(R.anim.slide_in_style1)
            .setAheadOutAnimation(R.anim.alpha_out_style1)
            .setBackInAnimation(R.anim.alpha_in_style1)
            .setBackOutAnimation(R.anim.slide_out_style1)
            .jumpForResult(this, LoginActivity.class, CODE_LOGIN_FROM_Fee_ShangJinLieRen);
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
	    super.onActivityResult(arg0, arg1, arg2);
	    if (arg1 == RESULT_OK) {
	        switch (arg0) {
	        case CODE_BIND_CARD_INFO:
	            finish();
	            break;
	        case CODE_REGISTER_FEE_HUNTER:  // 点击注册赏金猎人，注册成功后返回
	        	// TODO 注册成功后，此处要跳登录，还是跳填写银行卡信息呢
	        	// TODO 注册赏金猎人成功，觉得应该跳填写银行卡信息页
				Jumper.newJumper()
	            .setAheadInAnimation(R.anim.activity_push_in_right)
	            .setAheadOutAnimation(R.anim.activity_alpha_out)
	            .setBackInAnimation(R.anim.activity_alpha_in)
	            .setBackOutAnimation(R.anim.activity_push_out_right)
	            .jumpForResult(this, FillBankCardInfoActivity.class, CODE_BIND_CARD_INFO);
	        	break;
	        case CODE_LOGIN_FROM_Fee_ShangJinLieRen:  // 点击登录，登录成功后
	        	// TODO 登录成功后
	        	/**
	    		 * 用户类型    0经济人，1普通会员，2赏金猎人
	    		 */
	    		boolean loginStatus = ContentUtils.getUserLoginStatus(this);
	    		if (loginStatus) {  // 已登录
	    			tvLogin.setVisibility(View.GONE);
	    			int userType = ContentUtils.getUserType(this);
	    			if (userType == 2) { // 赏金猎人
	    				Jumper.newJumper()
			            .setAheadInAnimation(R.anim.activity_push_in_right)
			            .setAheadOutAnimation(R.anim.activity_alpha_out)
			            .setBackInAnimation(R.anim.activity_alpha_in)
			            .setBackOutAnimation(R.anim.activity_push_out_right)
			            .jump(this, FeeHunterInfoActivity.class);
	    				finish();
	    			} else { // TODO 登录成功的非赏金猎人，此处做什么操作，感觉此处就不操作了
//	    				Jumper.newJumper()
//	    	            .setAheadInAnimation(R.anim.activity_push_in_right)
//	    	            .setAheadOutAnimation(R.anim.activity_alpha_out)
//	    	            .setBackInAnimation(R.anim.activity_alpha_in)
//	    	            .setBackOutAnimation(R.anim.activity_push_out_right)
//	    	            .jumpForResult(this, FillBankCardInfoActivity.class, CODE_BIND_CARD_INFO);
	    			}
	    		}
	        	break;
	        }
	    }
	}

}
