package cn.com.zzwfang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.IdNameBean;
import cn.com.zzwfang.util.Jumper;

/**
 * 委托售房
 * @author lzd
 *
 */
public class ProxySellHouseActivity extends BaseActivity implements OnClickListener {

	
	private static final int REQUEST_ESTATE = 120;
	
	private TextView tvBack, tvSelectEstateName;
	
	private IdNameBean idNameBean;
	
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initView();
	}
	
	private void initView() {
		setContentView(R.layout.act_proxy_sell_house);
		
		tvBack = (TextView) findViewById(R.id.act_proxy_sell_house_back);
		tvSelectEstateName = (TextView) findViewById(R.id.act_proxy_sell_house_select_estate_name);
		
		tvBack.setOnClickListener(this);
		tvSelectEstateName.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_proxy_sell_house_back:
			finish();
			break;
		case R.id.act_proxy_sell_house_select_estate_name:  //  选择小区名称
			Jumper.newJumper()
			.setAheadInAnimation(R.anim.activity_push_in_right)
			.setAheadOutAnimation(R.anim.activity_alpha_out)
			.setBackInAnimation(R.anim.activity_alpha_in)
			.setBackOutAnimation(R.anim.activity_push_out_right)
			.jumpForResult(this, SelectEstateActivity.class,
					REQUEST_ESTATE);
			break;
		}
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case REQUEST_ESTATE:
				idNameBean = (IdNameBean) data
				.getSerializableExtra(SelectEstateActivity.INTENT_ESTATE);
		tvSelectEstateName.setText(idNameBean.getName());
				break;
			}
		}
	}
	
	private void entrustSell() {
		
	}
}
