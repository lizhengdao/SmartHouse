package cn.com.zzwfang.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.util.Jumper;

/**
 * 找房神器具体筛选条件
 * @author lzd
 *
 */
public class SearchHouseArtifactRequirementActivity extends BaseActivity implements OnClickListener, OnCheckedChangeListener {

	private TextView tvBack, tvCommit;
	
	private RadioButton rbOneRoom, rbTwoRooms, rbThreeRooms, rbFourRooms;
	private CheckBox cbxSubwayHouse, cbxSchoolHouse, cbxElectricHouse, cbxCarSpaceHouse;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initView();
	}
	
	private void initView() {
		setContentView(R.layout.act_search_house_artifact_requirement);
		tvBack = (TextView) findViewById(R.id.act_search_house_artifact_requirement_back);
		rbOneRoom = (RadioButton) findViewById(R.id.rb_one_room);
		rbTwoRooms = (RadioButton) findViewById(R.id.rb_two_rooms);
		rbThreeRooms = (RadioButton) findViewById(R.id.rb_three_rooms);
		rbFourRooms = (RadioButton) findViewById(R.id.rb_four_rooms);
		
		cbxSubwayHouse = (CheckBox) findViewById(R.id.rb_subway_house);
		cbxSchoolHouse = (CheckBox) findViewById(R.id.rb_school_house);
		cbxElectricHouse = (CheckBox) findViewById(R.id.rb_electric_house);
		cbxCarSpaceHouse = (CheckBox) findViewById(R.id.rb_car_space_house);
		
		tvCommit = (TextView) findViewById(R.id.act_search_house_artifact_requirement_commit_tv);
		
		tvBack.setOnClickListener(this);
		rbOneRoom.setOnCheckedChangeListener(this);
		rbTwoRooms.setOnCheckedChangeListener(this);
		rbThreeRooms.setOnCheckedChangeListener(this);
		rbFourRooms.setOnCheckedChangeListener(this);
		
		cbxSubwayHouse.setOnCheckedChangeListener(this);
		cbxSchoolHouse.setOnCheckedChangeListener(this);
		cbxElectricHouse.setOnCheckedChangeListener(this);
		cbxCarSpaceHouse.setOnCheckedChangeListener(this);
		
		tvCommit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_search_house_artifact_requirement_back:  // 返回
			finish();
			break;
			
		case R.id.act_search_house_artifact_requirement_commit_tv:  //  提交
			Jumper.newJumper()
	        .setAheadInAnimation(R.anim.activity_push_in_right)
	        .setAheadOutAnimation(R.anim.activity_alpha_out)
	        .setBackInAnimation(R.anim.activity_alpha_in)
	        .setBackOutAnimation(R.anim.activity_push_out_right)
	        .jump(this, SearchHouseArtifactResultActivity.class);
			break;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			switch (buttonView.getId()) {
			case R.id.rb_one_room:  // 一房
				break;
			case R.id.rb_two_rooms:  // 二房
				break;
			case R.id.rb_three_rooms:  // 三房
				break;
			case R.id.rb_four_rooms:  // 四房
				break;
			case R.id.rb_subway_house:  // 地铁房
				break;
			case R.id.rb_school_house:  // 学区房
				break;
			case R.id.rb_electric_house:  // 电梯房
				break;
			case R.id.rb_car_space_house:  // 带车位
				break;
			}
		}
	}
}
