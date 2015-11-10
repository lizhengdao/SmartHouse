package cn.com.zzwfang.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.util.Jumper;

public class FillBankCardInfoActivity extends BaseActivity implements OnClickListener {

	private TextView tvBack, tvCommit;
	
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		initView();
	}
	
	private void initView() {
		setContentView(R.layout.act_fill_bank_card_info);
		
		tvBack = (TextView) findViewById(R.id.act_fill_bank_card_info_back);
		tvCommit = (TextView) findViewById(R.id.act_fill_card_info_commit_tv);
		
		tvBack.setOnClickListener(this);
		tvCommit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_fill_bank_card_info_back:
			finish();
			break;
			
		case R.id.act_fill_card_info_commit_tv:   //  跳转赏金猎人个人中心
			Jumper.newJumper()
            .setAheadInAnimation(R.anim.activity_push_in_right)
            .setAheadOutAnimation(R.anim.activity_alpha_out)
            .setBackInAnimation(R.anim.activity_alpha_in)
            .setBackOutAnimation(R.anim.activity_push_out_right)
            .jump(this, ShangJinLieRenInfoActivity.class);
			break;
		}
	}
}
