package cn.com.zzwfang.activity;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.util.ToastUtils;

public class ForgetPwdActivity extends BaseActivity implements OnClickListener {

	private TextView tvBack, tvResendCode, tvNextStep;
	
	private EditText edtPhoneNum, edtPwd, edtInputAuthCode;
	
	private MyCount count;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initView();
	}
	
	private void initView() {
		setContentView(R.layout.act_forget_pwd);
		
		tvBack = (TextView) findViewById(R.id.act_forget_pwd_back);
		edtPhoneNum = (EditText) findViewById(R.id.act_forget_pwd_phone_num_edt);
		edtPwd = (EditText) findViewById(R.id.act_forget_pwd_input_pwd_edt);
		tvResendCode = (TextView) findViewById(R.id.act_forget_pwd_resend_code);
		edtInputAuthCode = (EditText) findViewById(R.id.act_forget_pwd_input_auth_code_edt);
		tvNextStep = (TextView) findViewById(R.id.act_forget_pwd_next_step_tv);
		
		tvBack.setOnClickListener(this);
		tvResendCode.setOnClickListener(this);
		tvNextStep.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_forget_pwd_back:
			finish();
			break;
		case R.id.act_forget_pwd_resend_code:  //  发送验证码
			getAuthCode();
			break;
		case R.id.act_forget_pwd_next_step_tv:  //   下一步
			resetPwd();
			break;
		}
	}
	
	private void resetPwd() {
		String phone = edtPhoneNum.getText().toString();
		String pwd = edtPwd.getText().toString();
		String code = edtInputAuthCode.getText().toString();
		
		if (TextUtils.isEmpty(phone)) {
			ToastUtils.SHORT.toast(this, "请输入手机号码");
			return;
		}
		if (TextUtils.isEmpty(pwd)) {
			ToastUtils.SHORT.toast(this, "请输入密码");
			return;
		}
		if (pwd.length() < 6) {
			ToastUtils.SHORT.toast(this, "密码至少6位");
			return;
		}
		if (TextUtils.isEmpty(code)) {
			ToastUtils.SHORT.toast(this, "请输入短信验证码");
			return;
		}
		
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.resetPwd(phone, pwd, code, new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				ToastUtils.SHORT.toast(ForgetPwdActivity.this, "重置密码成功");
			}
		});
	}
	
	/**
	 * 获取验证码    测试成功
	 */
	private void getAuthCode() {
		String phoneNum = edtPhoneNum.getText().toString();
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
				ToastUtils.SHORT.toast(ForgetPwdActivity.this, "短信验证码已发送!");
			}
		});
	}
	
	class MyCount extends CountDownTimer {

		public MyCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);

		}

		@Override
		public void onTick(long millisUntilFinished) {
			tvResendCode.setText("请等待(" + millisUntilFinished / 1000
					+ ")...");
			tvResendCode.setClickable(false);
		}

		@Override
		public void onFinish() {
			// 在这里进行设置解决时间停留的问题
			tvResendCode.setText("获取验证码");
			tvResendCode.setClickable(true);
		}
	}

	
}
