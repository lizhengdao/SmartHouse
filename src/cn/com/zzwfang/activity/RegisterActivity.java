package cn.com.zzwfang.activity;

import android.os.Bundle;
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
import cn.com.zzwfang.util.MD5Util;
import cn.com.zzwfang.util.ToastUtils;

public class RegisterActivity extends BaseActivity implements OnClickListener {

	private TextView tvBack, tvFetchAuthCode, tvRegister, tvProtocol;
	
	private EditText edtPhone, edtAuthCode, edtPwd;
	
	private CheckBox cbxProtocol;
	
	private String authCode = "1234";
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		setContentView(R.layout.act_register);
		
		initView();
		checkPhoneNumRegistered();
	}
	
	private void initView() {
		tvBack = (TextView) findViewById(R.id.act_register_back);
		edtPhone = (EditText) findViewById(R.id.act_register_phone_num_edt);
		edtAuthCode = (EditText) findViewById(R.id.act_register_input_auth_code_edt);
		edtPwd = (EditText) findViewById(R.id.act_register_input_pwd_edt);
		tvFetchAuthCode = (TextView) findViewById(R.id.act_register_auth_code_tv);
		tvProtocol = (TextView) findViewById(R.id.act_register_protocol_tv);
		tvRegister = (TextView) findViewById(R.id.act_register_tv);
		cbxProtocol = (CheckBox) findViewById(R.id.act_register_protocol_cbx);
		
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
			getAuthCode();
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
		if (TextUtils.isEmpty(phoneNum)) {
			ToastUtils.SHORT.toast(this, "请输入手机号码");
			return;
		}
		if (TextUtils.isEmpty(pwd)) {
			ToastUtils.SHORT.toast(this, "请输入密码");
			return;
		}
		
		pwd = MD5Util.md5(pwd);
		action.register(phoneNum, pwd, authCode, new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				
			}
		});
	}
	
	/**
	 * 获取验证码    测试成功
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
				
			}
		});
	}
	
	private void checkPhoneNumRegistered() {
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.checkPhoneNumRegistered("13882034203", new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				
			}
		});
	}
	
}
