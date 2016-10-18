package cn.com.zzwfang.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.util.Jumper;
import cn.com.zzwfang.util.ToastUtils;

public class RegisterActivity extends BaseActivity implements OnClickListener {
	
	public static final String INTENT_REGISTER_TYPE = "intent_register_type";

	private TextView tvBack, tvFeeHunter, tvFetchAuthCode, tvRegister, tvProtocol;
	
	private EditText edtPhone, edtAuthCode, edtPwd, edtRecommendPhone;
	
	private CheckBox cbxProtocol;
	
	private String authCode;
	
	private MyCount count;
	
	/**
	 * 1（普通会员），2（赏金猎人）
	 */
	private int registerType = 1;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		registerType = getIntent().getIntExtra(INTENT_REGISTER_TYPE, 1);
		setContentView(R.layout.act_register);
		
		initView();
	}
	
	private void initView() {
		tvBack = (TextView) findViewById(R.id.act_register_back);
		tvFeeHunter = (TextView) findViewById(R.id.act_register_fee_hunter_tv);
		edtPhone = (EditText) findViewById(R.id.act_register_phone_num_edt);
		edtAuthCode = (EditText) findViewById(R.id.act_register_input_auth_code_edt);
		edtPwd = (EditText) findViewById(R.id.act_register_input_pwd_edt);
		edtRecommendPhone = (EditText) findViewById(R.id.act_register_phone_recommend_num_edt);
		tvFetchAuthCode = (TextView) findViewById(R.id.act_register_auth_code_tv);
		tvProtocol = (TextView) findViewById(R.id.act_register_protocol_tv);
		tvRegister = (TextView) findViewById(R.id.act_register_tv);
		cbxProtocol = (CheckBox) findViewById(R.id.act_register_protocol_cbx);
		
		if (registerType == 2) {
			tvFeeHunter.setVisibility(View.VISIBLE);
			edtRecommendPhone.setVisibility(View.VISIBLE);
		}
		
		tvBack.setOnClickListener(this);
		tvFetchAuthCode.setOnClickListener(this);
		tvProtocol.setOnClickListener(this);
		tvRegister.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_register_back:
			finish();
			break;
		case R.id.act_register_auth_code_tv:  //  获取验证码
			checkPhoneNumRegistered();
			break;
			
		case R.id.act_register_protocol_tv:  //  智住网协议
			Jumper.newJumper()
	        .setAheadInAnimation(R.anim.activity_push_in_right)
	        .setAheadOutAnimation(R.anim.activity_alpha_out)
	        .setBackInAnimation(R.anim.activity_alpha_in)
	        .setBackOutAnimation(R.anim.activity_push_out_right)
	        .jump(this, SmartHouseProtocolActivity.class);
			break;
			
		case R.id.act_register_tv:  // 注册
			register();
			break;
		}
	}
	
	private void register() {
		ActionImpl action = ActionImpl.newInstance(this);
		String phoneNum = edtPhone.getText().toString();
		String pwd = edtPwd.getText().toString();
		authCode = edtAuthCode.getText().toString().trim();
		if (TextUtils.isEmpty(phoneNum)) {
			ToastUtils.SHORT.toast(this, "请输入手机号码");
			return;
		}
		if (TextUtils.isEmpty(pwd)) {
			ToastUtils.SHORT.toast(this, "请输入密码");
			return;
		}
		
		String recommendPhone = edtRecommendPhone.getText().toString().trim();
		
//		pwd = MD5Util.md5(pwd);
		action.register(phoneNum, pwd, authCode, registerType, recommendPhone, new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				setResult(RESULT_OK);
				ToastUtils.SHORT.toast(RegisterActivity.this, "注册成功");
				finish();
			}
		});
	}
	
	/**
	 * 检查手机号是否已被注册
	 */
	private void checkPhoneNumRegistered() {
		String phoneNum = edtPhone.getText().toString();
		if (TextUtils.isEmpty(phoneNum)) {
			ToastUtils.SHORT.toast(this, "请输入手机号码");
			return;
		}
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.checkPhoneNumRegistered(phoneNum, new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				getAuthCode();
			}
		});
	}
	
	/**
	 * 获取验证码
	 */
	private void getAuthCode() {
		String phoneNum = edtPhone.getText().toString();
		if (TextUtils.isEmpty(phoneNum)) {
			ToastUtils.SHORT.toast(this, "请输入手机号码");
			return;
		}
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.fetchVerifyCode(phoneNum, new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				count = new MyCount(60000, 1000);
				count.start();
				ToastUtils.SHORT.toast(RegisterActivity.this, "短信验证码已发送!");
			}
		});
	}
	
	class MyCount extends CountDownTimer {

		public MyCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);

		}

		@Override
		public void onTick(long millisUntilFinished) {
			tvFetchAuthCode.setText("请等待(" + millisUntilFinished / 1000
					+ ")...");
			tvFetchAuthCode.setClickable(false);
		}

		@Override
		public void onFinish() {
			// 在这里进行设置解决时间停留的问题
			tvFetchAuthCode.setText("获取验证码");
			tvFetchAuthCode.setClickable(true);
		}
	}

	
}
