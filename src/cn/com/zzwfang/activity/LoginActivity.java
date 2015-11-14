package cn.com.zzwfang.activity;

import org.apache.http.Header;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.utils.L;

import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.bean.UserInfoBean;
import cn.com.zzwfang.constant.URL;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.util.Jumper;
import cn.com.zzwfang.util.MD5Util;
import cn.com.zzwfang.util.RSAUtil;
import cn.com.zzwfang.util.ToastUtils;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LoginActivity extends BaseActivity implements OnClickListener {

	private EditText edtPhoneNum, edtPwd;
	
	private TextView tvLogin, tvRegister, tvForgetPwd;
	
	private LinearLayout lltBrokerLogin;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		setContentView(R.layout.act_login);
		
		initView();
	}
	
	private void initView() {
		
		edtPhoneNum = (EditText) findViewById(R.id.act_login_phone_num_edt);
		edtPwd = (EditText) findViewById(R.id.act_login_pwd_edt);
		
		tvLogin = (TextView) findViewById(R.id.act_login_login_tv);
		tvRegister = (TextView) findViewById(R.id.act_login_register);
		lltBrokerLogin = (LinearLayout) findViewById(R.id.act_login_login_llt);
		tvForgetPwd = (TextView) findViewById(R.id.act_login_forget_pwd_tv);
		
		edtPhoneNum.setText("18683525229");
		edtPwd.setText("123456");
		
		tvLogin.setOnClickListener(this);
		tvRegister.setOnClickListener(this);
		lltBrokerLogin.setOnClickListener(this);
		tvForgetPwd.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_login_login_tv:   //  登录
//			Jumper.newJumper()
//            .setAheadInAnimation(R.anim.alpha_out_style1)
//            .setAheadOutAnimation(R.anim.alpha_in_style1)
//            .jump(this, MainActivity.class);
			login();
			break;
			
		case R.id.act_login_register:   // 账号注册
			Jumper.newJumper()
            .setAheadInAnimation(R.anim.alpha_out_style1)
            .setAheadOutAnimation(R.anim.alpha_in_style1)
            .jump(this, RegisterActivity.class);
			break;
		
		case R.id.act_login_login_llt:  //  经纪人账号登录
			break;
			
		case R.id.act_login_forget_pwd_tv:   //  忘记密码
			Jumper.newJumper()
            .setAheadInAnimation(R.anim.alpha_out_style1)
            .setAheadOutAnimation(R.anim.alpha_in_style1)
            .jump(this, ForgetPwdActivity.class);
			break;
		}
	}
	
	private void login() {
		final String phoneNum = edtPhoneNum.getText().toString();
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
//		pwd = "E10ADC3949BA59ABBE56E057F20F883E";
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.login(phoneNum, pwd, new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				UserInfoBean userInfo = JSON.parseObject(result.getData(), UserInfoBean.class);
				ContentUtils.saveUserInfo(LoginActivity.this, userInfo);
				ContentUtils.saveLoginPhone(LoginActivity.this, phoneNum);
				ToastUtils.SHORT.toast(LoginActivity.this, "登录成功");
				Jumper.newJumper()
	            .setAheadInAnimation(R.anim.zoom_in_style1)
	            .setAheadOutAnimation(R.anim.zoom_out_style1)
	            .jump(LoginActivity.this, MainActivity.class);
			}
		});
	}
	
}
