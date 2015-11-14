package cn.com.zzwfang.fragment;

import java.io.File;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.activity.FeeHunterInfoActivity;
import cn.com.zzwfang.activity.FeedbackActivity;
import cn.com.zzwfang.activity.MainActivity;
import cn.com.zzwfang.activity.MessageActivity;
import cn.com.zzwfang.activity.MortgageCalculatorActivity;
import cn.com.zzwfang.activity.MyConcernHouseResourcesActivity;
import cn.com.zzwfang.activity.MyDemandActivity;
import cn.com.zzwfang.activity.MyHouseResourcesActivity;
import cn.com.zzwfang.activity.MyProxyActivity;
import cn.com.zzwfang.activity.SettingsActivity;
import cn.com.zzwfang.util.Jumper;
import cn.com.zzwfang.view.PathImage;

/**
 * 我的
 * @author lzd
 *
 */
public class MainMineFragment extends BasePickPhotoFragment implements OnClickListener {

	
	private TextView tvBack;

	private PathImage avatar;
	
	private FrameLayout msgFlt, attentionHouseSourceFlt, myProxyFlt,
	myDemandFlt, myHouseResourcesFlt, mortgageCalculatorFlt, feedbackFlt, settingsFlt;
	
	private ImageView imgFeeHunter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_main_mine, null);
		initView(view);
		return view;
	}

	private void initView(View view) {
		tvBack = (TextView) view.findViewById(R.id.frag_mine_back);
		avatar = (PathImage) view.findViewById(R.id.frag_mine_avatar);
		msgFlt = (FrameLayout) view.findViewById(R.id.frag_mine_msg_flt);
		attentionHouseSourceFlt = (FrameLayout) view.findViewById(R.id.frag_mine_attention_house_source_flt);
		myProxyFlt = (FrameLayout) view.findViewById(R.id.frag_mine_my_proxy_flt);
		myDemandFlt = (FrameLayout) view.findViewById(R.id.frag_mine_my_demand_flt);
		myHouseResourcesFlt = (FrameLayout) view.findViewById(R.id.frag_mine_my_house_resources_flt);
		mortgageCalculatorFlt = (FrameLayout) view.findViewById(R.id.frag_mine_mortgage_calculator_flt);
		feedbackFlt = (FrameLayout) view.findViewById(R.id.frag_mine_feedback_flt);
		settingsFlt = (FrameLayout) view.findViewById(R.id.frag_mine_settings_flt);
		imgFeeHunter = (ImageView) view.findViewById(R.id.frag_mine_fee_hunter);

		tvBack.setOnClickListener(this);
		avatar.setOnClickListener(this);
		msgFlt.setOnClickListener(this);
		attentionHouseSourceFlt.setOnClickListener(this);
		myProxyFlt.setOnClickListener(this);
		myDemandFlt.setOnClickListener(this);
		myHouseResourcesFlt.setOnClickListener(this);
		mortgageCalculatorFlt.setOnClickListener(this);
		feedbackFlt.setOnClickListener(this);
		settingsFlt.setOnClickListener(this);
		imgFeeHunter.setOnClickListener(this);
	}

	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.frag_mine_back:
			((MainActivity)getActivity()).backToHomeFragment();
			break;
		case R.id.frag_mine_fee_hunter:   // 赏金猎人个人中心
			Jumper.newJumper()
	        .setAheadInAnimation(R.anim.activity_push_in_right)
	        .setAheadOutAnimation(R.anim.activity_alpha_out)
	        .setBackInAnimation(R.anim.activity_alpha_in)
	        .setBackOutAnimation(R.anim.activity_push_out_right)
	        .jump(this, FeeHunterInfoActivity.class);
			break;
		case R.id.frag_mine_avatar:   //  修改头像
			startPickPhotoFromAlbumWithCrop();
			break;
		case R.id.frag_mine_msg_flt:  // 消息
			Jumper.newJumper()
	        .setAheadInAnimation(R.anim.activity_push_in_right)
	        .setAheadOutAnimation(R.anim.activity_alpha_out)
	        .setBackInAnimation(R.anim.activity_alpha_in)
	        .setBackOutAnimation(R.anim.activity_push_out_right)
	        .jump(this, MessageActivity.class);
			break;
		case R.id.frag_mine_attention_house_source_flt:  // 我关注的房源
			Jumper.newJumper()
	        .setAheadInAnimation(R.anim.activity_push_in_right)
	        .setAheadOutAnimation(R.anim.activity_alpha_out)
	        .setBackInAnimation(R.anim.activity_alpha_in)
	        .setBackOutAnimation(R.anim.activity_push_out_right)
	        .jump(this, MyConcernHouseResourcesActivity.class);
			break;
		case R.id.frag_mine_my_proxy_flt:  //  我的委托
			Jumper.newJumper()
	        .setAheadInAnimation(R.anim.activity_push_in_right)
	        .setAheadOutAnimation(R.anim.activity_alpha_out)
	        .setBackInAnimation(R.anim.activity_alpha_in)
	        .setBackOutAnimation(R.anim.activity_push_out_right)
	        .jump(this, MyProxyActivity.class);
			break;
//		case R.id.frag_mine_browse_record_flt:  //  浏览记录
//			Jumper.newJumper()
//	        .setAheadInAnimation(R.anim.activity_push_in_right)
//	        .setAheadOutAnimation(R.anim.activity_alpha_out)
//	        .setBackInAnimation(R.anim.activity_alpha_in)
//	        .setBackOutAnimation(R.anim.activity_push_out_right)
//	        .jump(this, BrowseRecordActivity.class);
//			break;
		case R.id.frag_mine_my_demand_flt:  // 我的需求
			Jumper.newJumper()
	        .setAheadInAnimation(R.anim.activity_push_in_right)
	        .setAheadOutAnimation(R.anim.activity_alpha_out)
	        .setBackInAnimation(R.anim.activity_alpha_in)
	        .setBackOutAnimation(R.anim.activity_push_out_right)
	        .jump(this, MyDemandActivity.class);
			break;
		case R.id.frag_mine_my_house_resources_flt:  // 我的房源
			Jumper.newJumper()
	        .setAheadInAnimation(R.anim.activity_push_in_right)
	        .setAheadOutAnimation(R.anim.activity_alpha_out)
	        .setBackInAnimation(R.anim.activity_alpha_in)
	        .setBackOutAnimation(R.anim.activity_push_out_right)
	        .jump(this, MyHouseResourcesActivity.class);
			break;
		case R.id.frag_mine_mortgage_calculator_flt:  // 房贷计算器
			Jumper.newJumper()
	        .setAheadInAnimation(R.anim.activity_push_in_right)
	        .setAheadOutAnimation(R.anim.activity_alpha_out)
	        .setBackInAnimation(R.anim.activity_alpha_in)
	        .setBackOutAnimation(R.anim.activity_push_out_right)
	        .jump(this, MortgageCalculatorActivity.class);
			break;
		case R.id.frag_mine_feedback_flt:  // 意见反馈
			Jumper.newJumper()
	        .setAheadInAnimation(R.anim.activity_push_in_right)
	        .setAheadOutAnimation(R.anim.activity_alpha_out)
	        .setBackInAnimation(R.anim.activity_alpha_in)
	        .setBackOutAnimation(R.anim.activity_push_out_right)
	        .jump(this, FeedbackActivity.class);
			break;
		case R.id.frag_mine_settings_flt:  // 设置
			Jumper.newJumper()
	        .setAheadInAnimation(R.anim.activity_push_in_right)
	        .setAheadOutAnimation(R.anim.activity_alpha_out)
	        .setBackInAnimation(R.anim.activity_alpha_in)
	        .setBackOutAnimation(R.anim.activity_push_out_right)
	        .jump(this, SettingsActivity.class);
			break;
		}
	}

	@Override
	public void onPickedPhoto(File file, Bitmap bm) {
		// TODO Auto-generated method stub
		Log.e("--->", "onPickedPhoto bitmap = " + bm);
	}

	@Override
	public int getDisplayWidth() {
		return 800;
	}

	@Override
	public int getDisplayHeight() {
		return 800;
	}



}
