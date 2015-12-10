package cn.com.zzwfang.activity;

import android.os.Bundle;
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
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.util.ToastUtils;

public class ChangePwdActivity extends BaseActivity implements OnClickListener {

	private TextView tvBack, tvCommit;
	
	private EditText edtOldPwd, edtNewPwd;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		initView();
	}
	
	private void initView() {
		setContentView(R.layout.act_change_pwd);
		
		tvBack = (TextView) findViewById(R.id.act_change_pwd_back);
		tvCommit = (TextView) findViewById(R.id.act_change_pwd_commit_tv);
		edtOldPwd = (EditText) findViewById(R.id.act_change_old_pwd_edt);
		edtNewPwd = (EditText) findViewById(R.id.act_change_new_pwd_edt);
		
		tvBack.setOnClickListener(this);
		tvCommit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_change_pwd_back:
			finish();
			break;
		case R.id.act_change_pwd_commit_tv:  //  提交修改密码
			changePwd();
			break;
		}
	}
	
	private void changePwd() {
		String oldPwd = edtOldPwd.getText().toString();
		String newPwd = edtNewPwd.getText().toString();
		if (TextUtils.isEmpty(oldPwd)) {
			ToastUtils.SHORT.toast(this, "请输入原密码");
			return;
		}
		
		if (TextUtils.isEmpty(newPwd)) {
			ToastUtils.SHORT.toast(this, "请输入新密码");
			return;
		}
		
		if (newPwd.length() < 6) {
			ToastUtils.SHORT.toast(this, "请输入至少6位新密码");
			return;
		}
		
		String loginUserPhone = ContentUtils.getLoginPhone(this);
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.changePwd(loginUserPhone, oldPwd, newPwd, new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				ToastUtils.SHORT.toast(ChangePwdActivity.this, "修改密码成功");
				ContentUtils.setUserLoginStatus(ChangePwdActivity.this, false);
			    ContentUtils.clearUserInfo(ChangePwdActivity.this);
			    setResult(RESULT_OK);
			    finish();
			}
		});
	}
}
