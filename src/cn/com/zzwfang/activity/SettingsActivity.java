package cn.com.zzwfang.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.util.Jumper;
import cn.com.zzwfang.view.ToggleButton;
import cn.com.zzwfang.view.ToggleButton.OnToggleChanged;

public class SettingsActivity extends BaseActivity implements OnClickListener, OnToggleChanged {

	private TextView tvBack, tvLogout;
	
	private FrameLayout changeNickNameFlt, changePwdFlt, clearCacheFlt, aboutUsFlt, commonQuestionFlt, checkUpdatesFlt;
	
	private ToggleButton msgPushToggleBtn;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		setContentView(R.layout.act_settings);
		
		initView();
	}
	
	private void initView() {
		
		tvBack = (TextView) findViewById(R.id.act_settings_back);
		changeNickNameFlt = (FrameLayout) findViewById(R.id.act_settings_change_nickname);
		changePwdFlt = (FrameLayout) findViewById(R.id.act_settings_change_pwd);
		msgPushToggleBtn = (ToggleButton) findViewById(R.id.act_settings_msg_push_toggle);
		clearCacheFlt = (FrameLayout) findViewById(R.id.act_settings_clear_cache);
		aboutUsFlt = (FrameLayout) findViewById(R.id.act_settings_about_us);
		commonQuestionFlt = (FrameLayout) findViewById(R.id.act_settings_common_questions);
		checkUpdatesFlt = (FrameLayout) findViewById(R.id.act_settings_check_updates);
		tvLogout = (TextView) findViewById(R.id.act_settings_logout);
		
		msgPushToggleBtn.setOnToggleChanged(this);
		tvBack.setOnClickListener(this);
		changeNickNameFlt.setOnClickListener(this);
		changePwdFlt.setOnClickListener(this);
		clearCacheFlt.setOnClickListener(this);
		aboutUsFlt.setOnClickListener(this);
		commonQuestionFlt.setOnClickListener(this);
		checkUpdatesFlt.setOnClickListener(this);
		tvLogout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_settings_back:  // 返回
			finish();
			break;
		case R.id.act_settings_change_nickname:  // 修改昵称
			Jumper.newJumper()
	        .setAheadInAnimation(R.anim.activity_push_in_right)
	        .setAheadOutAnimation(R.anim.activity_alpha_out)
	        .setBackInAnimation(R.anim.activity_alpha_in)
	        .setBackOutAnimation(R.anim.activity_push_out_right)
	        .jump(this, NickNameUpdateActivity.class);
			break;
		case R.id.act_settings_change_pwd:  // 修改密码
			Jumper.newJumper()
	        .setAheadInAnimation(R.anim.activity_push_in_right)
	        .setAheadOutAnimation(R.anim.activity_alpha_out)
	        .setBackInAnimation(R.anim.activity_alpha_in)
	        .setBackOutAnimation(R.anim.activity_push_out_right)
	        .jump(this, ChangePwdActivity.class);
			break;
		case R.id.act_settings_clear_cache:  //  清除缓存
			break;
		case R.id.act_settings_about_us:  //  关于我们
			Jumper.newJumper()
	        .setAheadInAnimation(R.anim.activity_push_in_right)
	        .setAheadOutAnimation(R.anim.activity_alpha_out)
	        .setBackInAnimation(R.anim.activity_alpha_in)
	        .setBackOutAnimation(R.anim.activity_push_out_right)
	        .jump(this, AboutUsActivity.class);
			break;
		case R.id.act_settings_common_questions:  // 常见问题
			Jumper.newJumper()
	        .setAheadInAnimation(R.anim.activity_push_in_right)
	        .setAheadOutAnimation(R.anim.activity_alpha_out)
	        .setBackInAnimation(R.anim.activity_alpha_in)
	        .setBackOutAnimation(R.anim.activity_push_out_right)
	        .jump(this, CommonProblemsActivity.class);
			break;
		case R.id.act_settings_check_updates:   //  检查更新
			break;
		case R.id.act_settings_logout:   //  退出账号
			break;
		}
	}

	@Override
	public void onToggle(ToggleButton button, boolean on) {
		
	}
}
