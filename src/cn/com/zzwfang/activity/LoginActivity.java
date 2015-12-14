package cn.com.zzwfang.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.IMMessageBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.bean.UserInfoBean;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.im.MessagePool;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.util.Jumper;
import cn.com.zzwfang.util.ToastUtils;

import com.alibaba.fastjson.JSON;
import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;

public class LoginActivity extends BaseActivity implements OnClickListener {

	private EditText edtPhoneNum, edtPwd;
	
	private TextView tvBack, tvLogin, tvRegister, tvForgetPwd;
	
	private LinearLayout lltBrokerLogin;
	
	private ActionImpl actionImpl;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		setContentView(R.layout.act_login);
		
		initView();
	}
	
	private void initView() {
		tvBack = (TextView) findViewById(R.id.act_login_back);
		edtPhoneNum = (EditText) findViewById(R.id.act_login_phone_num_edt);
		edtPwd = (EditText) findViewById(R.id.act_login_pwd_edt);
		
		tvLogin = (TextView) findViewById(R.id.act_login_login_tv);
		tvRegister = (TextView) findViewById(R.id.act_login_register);
		lltBrokerLogin = (LinearLayout) findViewById(R.id.act_login_login_llt);
		tvForgetPwd = (TextView) findViewById(R.id.act_login_forget_pwd_tv);
		
		edtPhoneNum.setText("18700827866");  //  18700827866    18683525229
		edtPwd.setText("111111");    //   111111  123456   
		
		actionImpl = ActionImpl.newInstance(this);
		tvBack.setOnClickListener(this);
		tvLogin.setOnClickListener(this);
		tvRegister.setOnClickListener(this);
		lltBrokerLogin.setOnClickListener(this);
		tvForgetPwd.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_login_back:
			finish();
			break;
		case R.id.act_login_login_tv:   //  登录
		    tvLogin.setClickable(false);
			login();
			break;
			
		case R.id.act_login_register:   // 账号注册
			Jumper.newJumper()
            .setAheadInAnimation(R.anim.activity_push_in_right)
            .setAheadOutAnimation(R.anim.activity_alpha_out)
            .setBackInAnimation(R.anim.activity_alpha_in)
            .setBackOutAnimation(R.anim.activity_push_out_right)
            .putInt(RegisterActivity.INTENT_REGISTER_TYPE, 1)
            .jump(this, RegisterActivity.class);
			break;
		
		case R.id.act_login_login_llt:  //  经纪人账号登录
			break;
			
		case R.id.act_login_forget_pwd_tv:   //  忘记密码
			Jumper.newJumper()
            .setAheadInAnimation(R.anim.activity_push_in_right)
            .setAheadOutAnimation(R.anim.activity_alpha_out)
            .setBackInAnimation(R.anim.activity_alpha_in)
            .setBackOutAnimation(R.anim.activity_push_out_right)
            .jump(this, ForgetPwdActivity.class);
			
			break;
		}
	}
	
	private void login() {
		final String phoneNum = edtPhoneNum.getText().toString();
		String pwd = edtPwd.getText().toString();
		if (TextUtils.isEmpty(phoneNum)) {
		    tvLogin.setClickable(true);
			ToastUtils.SHORT.toast(this, "请输入手机号码");
			return;
		}
		if (TextUtils.isEmpty(pwd)) {
		    tvLogin.setClickable(true);
			ToastUtils.SHORT.toast(this, "请输入密码");
			return;
		}
		
		actionImpl.login(phoneNum, pwd, new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
			    tvLogin.setClickable(true);
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
			    tvLogin.setClickable(true);
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				final UserInfoBean userInfo = JSON.parseObject(result.getData(), UserInfoBean.class);
				ContentUtils.saveUserInfo(LoginActivity.this, userInfo);
				ContentUtils.saveLoginPhone(LoginActivity.this, phoneNum);
				
				actionImpl.createIMAccount(userInfo.getId(), userInfo.getId(), new ResultHandlerCallback() {
					
					@Override
					public void rc999(RequestEntity entity, Result result) {
					    tvLogin.setClickable(true);
					}
					
					@Override
					public void rc3001(RequestEntity entity, Result result) {
					    tvLogin.setClickable(true);
					}
					
					@Override
					public void rc0(RequestEntity entity, Result result) {
						loginOnHx(userInfo.getId(), userInfo.getId());
					}
				});
			}
		});
	}
	
	private void loginOnHx(String easeId, String easePwd) {
		EMChatManager.getInstance().login(easeId, easePwd, new EMCallBack() {
			
			@Override
			public void onSuccess() {
				try {
//					EMGroupManager.getInstance().loadAllGroups();
//					EMChatManager.getInstance().loadAllConversations();
//					EMChatManager.getInstance().
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						getContacts();
					}
				});
			}
			
			@Override
			public void onProgress(int arg0, String arg1) {
				
			}
			
			@Override
			public void onError(int arg0, String arg1) {
                    runOnUiThread(new Runnable() {
                    
                    @Override
                    public void run() {
                        ContentUtils.setUserLoginStatus(LoginActivity.this, false);
                        ToastUtils.SHORT.toast(LoginActivity.this, "登录失败");
                        tvLogin.setClickable(true);
                    }
                });
			}
		});
	}
	
	private void getContacts() {
		String userId = ContentUtils.getUserId(this);
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.getContactsList(userId, new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				ArrayList<IMMessageBean> temp = (ArrayList<IMMessageBean>) JSON.parseArray(result.getData(), IMMessageBean.class);
				if (temp != null) {
					MessagePool.addAllContactsMessages(temp);
				}
				
				setResult(RESULT_OK);
				ContentUtils.setUserLoginStatus(LoginActivity.this, true);
				ToastUtils.SHORT.toast(LoginActivity.this, "登录成功");
//				Jumper.newJumper()
//	            .setAheadInAnimation(R.anim.zoom_in_style1)
//	            .setAheadOutAnimation(R.anim.zoom_out_style1)
//	            .jump(LoginActivity.this, MainActivity.class);
//				tvLogin.setClickable(true);
				finish();
			}
		});
	}

	
}
