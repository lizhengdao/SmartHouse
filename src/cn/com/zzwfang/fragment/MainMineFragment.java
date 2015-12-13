package cn.com.zzwfang.fragment;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.action.ImageAction;
import cn.com.zzwfang.activity.FeeHunterInfoActivity;
import cn.com.zzwfang.activity.FeedbackActivity;
import cn.com.zzwfang.activity.LoginActivity;
import cn.com.zzwfang.activity.MainActivity;
import cn.com.zzwfang.activity.MessageActivity;
import cn.com.zzwfang.activity.MortgageCalculatorActivity;
import cn.com.zzwfang.activity.MyConcernHouseResourcesActivity;
import cn.com.zzwfang.activity.MyDemandInfoActivity;
import cn.com.zzwfang.activity.MyHouseListActivity;
import cn.com.zzwfang.activity.MyProxyActivity;
import cn.com.zzwfang.activity.SettingsActivity;
import cn.com.zzwfang.activity.ShangJinLieRenActivity;
import cn.com.zzwfang.bean.FileUploadResultBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.bean.UserInfoBean;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.util.Jumper;
import cn.com.zzwfang.util.ToastUtils;
import cn.com.zzwfang.view.PathImage;
import cn.com.zzwfang.view.helper.PopViewHelper;
import cn.com.zzwfang.view.helper.PopViewHelper.OnAvatarOptionsClickListener;

import com.alibaba.fastjson.JSON;

/**
 * 我的
 * @author lzd
 *
 */
public class MainMineFragment extends BasePickPhotoFragment implements OnClickListener, OnAvatarOptionsClickListener {

    public static final int CODE_SETTING = 100;
	
	private TextView tvBack, tvUserName, tvPhone, tvLoginRegister;

	private LinearLayout lltUserInfo, myProxyllt, myDemandLlt;
	private PathImage avatar;
	
	private FrameLayout msgFlt, attentionHouseSourceFlt,
	myHouseResourcesFlt, mortgageCalculatorFlt, feedbackFlt, settingsFlt;
	
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
		tvUserName = (TextView) view.findViewById(R.id.view_userinfo_username);
		tvPhone = (TextView) view.findViewById(R.id.frag_mine_phone);
		avatar = (PathImage) view.findViewById(R.id.frag_mine_avatar);
		msgFlt = (FrameLayout) view.findViewById(R.id.frag_mine_msg_flt);
		attentionHouseSourceFlt = (FrameLayout) view.findViewById(R.id.frag_mine_attention_house_source_flt);
		myProxyllt = (LinearLayout) view.findViewById(R.id.frag_mine_my_proxy_llt);
		myDemandLlt = (LinearLayout) view.findViewById(R.id.frag_mine_my_demand_llt);
		myHouseResourcesFlt = (FrameLayout) view.findViewById(R.id.frag_mine_my_house_resources_flt);
		mortgageCalculatorFlt = (FrameLayout) view.findViewById(R.id.frag_mine_mortgage_calculator_flt);
		feedbackFlt = (FrameLayout) view.findViewById(R.id.frag_mine_feedback_flt);
		settingsFlt = (FrameLayout) view.findViewById(R.id.frag_mine_settings_flt);
		imgFeeHunter = (ImageView) view.findViewById(R.id.frag_mine_fee_hunter);
		tvLoginRegister = (TextView) view.findViewById(R.id.frag_mine_login_register);
		lltUserInfo = (LinearLayout) view.findViewById(R.id.frag_mine_user_info_llt);

		tvBack.setOnClickListener(this);
		avatar.setOnClickListener(this);
		msgFlt.setOnClickListener(this);
		attentionHouseSourceFlt.setOnClickListener(this);
		myProxyllt.setOnClickListener(this);
		myDemandLlt.setOnClickListener(this);
		myHouseResourcesFlt.setOnClickListener(this);
		mortgageCalculatorFlt.setOnClickListener(this);
		feedbackFlt.setOnClickListener(this);
		settingsFlt.setOnClickListener(this);
		imgFeeHunter.setOnClickListener(this);
		tvLoginRegister.setOnClickListener(this);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		
		boolean loginStatus = ContentUtils.getUserLoginStatus(getActivity());
		if (loginStatus) { // 已登录
			tvLoginRegister.setVisibility(View.GONE);
			lltUserInfo.setVisibility(View.VISIBLE);
			UserInfoBean userInfoBean = ContentUtils.getUserInfo(getActivity());
			tvUserName.setText(userInfoBean.getUserName());
			tvPhone.setText(userInfoBean.getPhone());
			String avatarUrl = userInfoBean.getPhoto();
			if (!TextUtils.isEmpty(avatarUrl)) {
				ImageAction.displayAvatar(avatarUrl, avatar);
			}
		} else { // 未登录
			tvLoginRegister.setVisibility(View.VISIBLE);
			lltUserInfo.setVisibility(View.GONE);
//			Jumper.newJumper()
//            .setAheadInAnimation(R.anim.slide_in_style1)
//            .setAheadOutAnimation(R.anim.alpha_out_style1)
//            .setBackInAnimation(R.anim.alpha_in_style1)
//            .setBackOutAnimation(R.anim.slide_out_style1)
//            .jumpForResult(this, LoginActivity.class, CODE_LOGIN_MINE);
		}
		
	}

	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.frag_mine_back:
			((MainActivity)getActivity()).backToHomeFragment();
			break;
		case R.id.frag_mine_fee_hunter:   // 赏金猎人个人中心
			/**
			 * 用户类型    0经济人，1普通会员，2赏金猎人
			 */
			int userType = ContentUtils.getUserType(getActivity());
			if (userType == 2) {
				Jumper.newJumper()
	            .setAheadInAnimation(R.anim.activity_push_in_right)
	            .setAheadOutAnimation(R.anim.activity_alpha_out)
	            .setBackInAnimation(R.anim.activity_alpha_in)
	            .setBackOutAnimation(R.anim.activity_push_out_right)
	            .jump(this, FeeHunterInfoActivity.class);
			} else {
				Jumper.newJumper()
	            .setAheadInAnimation(R.anim.activity_push_in_right)
	            .setAheadOutAnimation(R.anim.activity_alpha_out)
	            .setBackInAnimation(R.anim.activity_alpha_in)
	            .setBackOutAnimation(R.anim.activity_push_out_right)
	            .jump(this, ShangJinLieRenActivity.class);
			}
			break;
		case R.id.frag_mine_avatar:   //  修改头像
			if (checkLoginStatus()) {
				PopViewHelper.showUpdateAvatarPopupWindow(getActivity(), getView(), this);
			}
			break;
		case R.id.frag_mine_login_register:  // 未登录/注册
			Jumper.newJumper()
            .setAheadInAnimation(R.anim.slide_in_style1)
            .setAheadOutAnimation(R.anim.alpha_out_style1)
            .setBackInAnimation(R.anim.alpha_in_style1)
            .setBackOutAnimation(R.anim.slide_out_style1)
            .jump(this, LoginActivity.class);
//            .jumpForResult(this, LoginActivity.class, CODE_LOGIN);
			break;
		case R.id.frag_mine_msg_flt:  // 消息
			if (checkLoginStatus()) {
				Jumper.newJumper()
		        .setAheadInAnimation(R.anim.activity_push_in_right)
		        .setAheadOutAnimation(R.anim.activity_alpha_out)
		        .setBackInAnimation(R.anim.activity_alpha_in)
		        .setBackOutAnimation(R.anim.activity_push_out_right)
		        .jump(this, MessageActivity.class);
			}
			break;
		case R.id.frag_mine_attention_house_source_flt:  // 我关注的房源
			if (checkLoginStatus()) {
				Jumper.newJumper()
		        .setAheadInAnimation(R.anim.activity_push_in_right)
		        .setAheadOutAnimation(R.anim.activity_alpha_out)
		        .setBackInAnimation(R.anim.activity_alpha_in)
		        .setBackOutAnimation(R.anim.activity_push_out_right)
		        .jump(this, MyConcernHouseResourcesActivity.class);
			}
			break;
		case R.id.frag_mine_my_proxy_llt:  //  我的委托  （帮你卖房（我是业主））
			if (checkLoginStatus()) {
				Jumper.newJumper()
		        .setAheadInAnimation(R.anim.activity_push_in_right)
		        .setAheadOutAnimation(R.anim.activity_alpha_out)
		        .setBackInAnimation(R.anim.activity_alpha_in)
		        .setBackOutAnimation(R.anim.activity_push_out_right)
		        .jump(this, MyProxyActivity.class);
			}
			break;
		case R.id.frag_mine_my_demand_llt:  // 我的需求  (帮你找房  我是客户)
			if (checkLoginStatus()) {
//				Jumper.newJumper()
//		        .setAheadInAnimation(R.anim.activity_push_in_right)
//		        .setAheadOutAnimation(R.anim.activity_alpha_out)
//		        .setBackInAnimation(R.anim.activity_alpha_in)
//		        .setBackOutAnimation(R.anim.activity_push_out_right)
//		        .jump(this, MyDemandActivity.class);
				
				Jumper.newJumper()
		        .setAheadInAnimation(R.anim.activity_push_in_right)
		        .setAheadOutAnimation(R.anim.activity_alpha_out)
		        .setBackInAnimation(R.anim.activity_alpha_in)
		        .setBackOutAnimation(R.anim.activity_push_out_right)
		        .jump(this, MyDemandInfoActivity.class);
			}
			break;
		case R.id.frag_mine_my_house_resources_flt:  // 我的房源
//			Jumper.newJumper()
//	        .setAheadInAnimation(R.anim.activity_push_in_right)
//	        .setAheadOutAnimation(R.anim.activity_alpha_out)
//	        .setBackInAnimation(R.anim.activity_alpha_in)
//	        .setBackOutAnimation(R.anim.activity_push_out_right)
//	        .jump(this, MyHouseResourcesActivity.class);
			
			if (checkLoginStatus()) {
				Jumper.newJumper()
		        .setAheadInAnimation(R.anim.activity_push_in_right)
		        .setAheadOutAnimation(R.anim.activity_alpha_out)
		        .setBackInAnimation(R.anim.activity_alpha_in)
		        .setBackOutAnimation(R.anim.activity_push_out_right)
		        .jump(this, MyHouseListActivity.class);
			}
			break;
		case R.id.frag_mine_mortgage_calculator_flt:  // 房贷计算器
			if (checkLoginStatus()) {
				Jumper.newJumper()
		        .setAheadInAnimation(R.anim.activity_push_in_right)
		        .setAheadOutAnimation(R.anim.activity_alpha_out)
		        .setBackInAnimation(R.anim.activity_alpha_in)
		        .setBackOutAnimation(R.anim.activity_push_out_right)
		        .jump(this, MortgageCalculatorActivity.class);
			}
			break;
		case R.id.frag_mine_feedback_flt:  // 意见反馈
			if (checkLoginStatus()) {
				Jumper.newJumper()
		        .setAheadInAnimation(R.anim.activity_push_in_right)
		        .setAheadOutAnimation(R.anim.activity_alpha_out)
		        .setBackInAnimation(R.anim.activity_alpha_in)
		        .setBackOutAnimation(R.anim.activity_push_out_right)
		        .jump(this, FeedbackActivity.class);
			}
			break;
		case R.id.frag_mine_settings_flt:  // 设置
			if (checkLoginStatus()) {
				Jumper.newJumper()
		        .setAheadInAnimation(R.anim.activity_push_in_right)
		        .setAheadOutAnimation(R.anim.activity_alpha_out)
		        .setBackInAnimation(R.anim.activity_alpha_in)
		        .setBackOutAnimation(R.anim.activity_push_out_right)
		        .jumpForResult(this, SettingsActivity.class, CODE_SETTING);
			}
			break;
		}
	}
	
	private boolean checkLoginStatus() {
		boolean loginStatus = ContentUtils.getUserLoginStatus(getActivity());
		if (!loginStatus) {
			Jumper.newJumper()
            .setAheadInAnimation(R.anim.slide_in_style1)
            .setAheadOutAnimation(R.anim.alpha_out_style1)
            .setBackInAnimation(R.anim.alpha_in_style1)
            .setBackOutAnimation(R.anim.slide_out_style1)
            .jump(this, LoginActivity.class);
			return false;
		}
		return true;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    if (resultCode == Activity.RESULT_OK) {
	        switch (requestCode) {
	        case CODE_SETTING:
//	            ((MainActivity)getActivity()).backToHomeFragment();
	            break;
	        }
	    }
	}

	@Override
	public void onPickedPhoto(File file, Bitmap bm) {
		// TODO Auto-generated method stub
		avatar.setImageBitmap(bm);
		upLoadAvatar(file);
	}

	@Override
	public int getDisplayWidth() {
		return 800;
	}

	@Override
	public int getDisplayHeight() {
		return 800;
	}

	@Override
	public void onAvatarOptionClick(int action) {
		// TODO Auto-generated method stub
		switch (action) {
        case OnAvatarOptionsClickListener.ACTION_CAMERA:   // 相机
            
            if (isCrop) {
                startPickPhotoFromCameraWithCrop();
            } else {
                startPickPhotoFromCamara();
            }
            
            break;
        case OnAvatarOptionsClickListener.ACTION_ALBUM:   // 相册
            if (isCrop) {
                startPickPhotoFromAlbumWithCrop();
            } else {
                startPickPhotoFromAlbum();
            }
            break;
        }
	}
	
	private void upLoadAvatar(File file) {
		ActionImpl actionImpl = ActionImpl.newInstance(getActivity());
		actionImpl.otherFileUpload(file, new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				// TODO Auto-generated method stub
				FileUploadResultBean fileUploadResultBean = JSON.parseObject(result.getData(), FileUploadResultBean.class);
				ContentUtils.updateUserAvatar(getActivity(), fileUploadResultBean.getShowPath());
				updateUserInfoAvatar(fileUploadResultBean.getFilePath());
			}
		});
	}
	
	private void updateUserInfoAvatar(String avatarUrl) {
		ActionImpl actionImpl = ActionImpl.newInstance(getActivity());
		String userId = ContentUtils.getUserId(getActivity());
		actionImpl.updateUserInfo(userId, null, avatarUrl, new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				ToastUtils.SHORT.toast(getActivity(), "头像上传成功");
			}
		});
	}
	
	@Override
	public void onDestroy() {
		cleanFile(cacheImageDir);
		super.onDestroy();
	}



}
