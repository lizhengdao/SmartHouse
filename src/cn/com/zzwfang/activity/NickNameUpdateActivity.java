package cn.com.zzwfang.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.bean.UserInfoBean;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.util.ToastUtils;

/**
 * 修改昵称页
 * @author lzd
 *
 */
public class NickNameUpdateActivity extends BaseActivity implements OnClickListener {

	private TextView tvBack, tvCommit;
	
	private EditText edtNickName;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initView();
	}
	
	private void initView() {
		setContentView(R.layout.act_nick_name_update);
		
		tvBack = (TextView) findViewById(R.id.act_nick_name_update_back);
		edtNickName = (EditText) findViewById(R.id.act_nick_name_edt);
		tvCommit = (TextView) findViewById(R.id.act_nick_name_commit);
		
		UserInfoBean userInfoBean = ContentUtils.getUserInfo(this);
		edtNickName.setText(userInfoBean.getUserName());
		
		tvBack.setOnClickListener(this);
		tvCommit.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_nick_name_update_back:
			finish();
			break;
		case R.id.act_nick_name_commit:
			updateNickName();
			break;
		}
	}
	
	private void updateNickName() {
		final String nickName = edtNickName.getText().toString();
		if (TextUtils.isEmpty(nickName)) {
			ToastUtils.SHORT.toast(this, "请输入昵称");
		}
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		String userId = ContentUtils.getUserId(this);
		actionImpl.updateUserInfo(userId, null, null, new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				ContentUtils.updateUserNickName(NickNameUpdateActivity.this, nickName);
				ToastUtils.SHORT.toast(NickNameUpdateActivity.this, "昵称修改成功");
				finish();
			}
		});
	}
}
