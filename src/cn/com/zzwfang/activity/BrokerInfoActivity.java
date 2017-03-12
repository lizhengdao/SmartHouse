package cn.com.zzwfang.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.action.ImageAction;
import cn.com.zzwfang.adapter.AgentInfoAdapter;
import cn.com.zzwfang.bean.AgentBean;
import cn.com.zzwfang.bean.AgentInfoDetailBean;
import cn.com.zzwfang.bean.AgentInfoItemBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.util.Jumper;
import cn.com.zzwfang.view.PathImage;
import cn.com.zzwfang.view.helper.PopViewHelper;
import cn.com.zzwfang.view.helper.PopViewHelper.OnBrokerConsultClickListener;

import com.alibaba.fastjson.JSON;

/**
 * 中介信息  (改为经纪人店铺)
 * @author lzd
 *
 */
public class BrokerInfoActivity extends BaseActivity implements OnClickListener, OnItemClickListener, OnBrokerConsultClickListener {

	public static final String INTENT_AGENT_DATA = "intent_agent_date";
	
	private TextView tvBack, tvName;  // tvTitle
	private TextView tvSecondHandHouses, tvRentHouses, tvAccessAmount, tvPhone, tvMotto, tvConsult;
	private PathImage imgAvatar;
	
//	private LinearLayout lltDial, lltMsg;
	
	private ListView lstAgentInfo;
	private AgentInfoAdapter adapter;
	private ArrayList<AgentInfoItemBean> listProperty = new ArrayList<AgentInfoItemBean>();
	
	private AgentBean agentBean;
	private AgentInfoDetailBean agentInfoDetailBean;
	
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		agentBean = (AgentBean) getIntent().getSerializableExtra(INTENT_AGENT_DATA);
		initView();
		getAgentInfoDetail();
	}
	
	private void initView() {
		setContentView(R.layout.act_broker_info);
		tvBack = (TextView) findViewById(R.id.act_broker_info_back);
//		tvTitle = (TextView) findViewById(R.id.act_broker_info_title);
		imgAvatar = (PathImage) findViewById(R.id.act_broker_info_avatar);
		tvName = (TextView) findViewById(R.id.act_broker_info_name);
		
		tvSecondHandHouses = (TextView) findViewById(R.id.act_broker_info_second_hand_houses);
		tvRentHouses = (TextView) findViewById(R.id.act_broker_info_rent_houses);
		tvAccessAmount = (TextView) findViewById(R.id.act_broker_info_shop_access_amount);
		tvPhone = (TextView) findViewById(R.id.act_broker_info_phone);
		tvMotto = (TextView) findViewById(R.id.act_broker_info_motto);
		tvConsult = (TextView) findViewById(R.id.tv_broker_info_consult);
		
//		lltDial = (LinearLayout) findViewById(R.id.act_broker_info_phone_agent_dial);
//		lltMsg = (LinearLayout) findViewById(R.id.act_broker_info_phone_agent_msg);
		
		lstAgentInfo = (ListView) findViewById(R.id.act_broker_info_lst);
		adapter = new AgentInfoAdapter(this, listProperty);
		lstAgentInfo.setAdapter(adapter);
		
		lstAgentInfo.setOnItemClickListener(this);
		
		if (agentBean != null) {
//			tvTitle.setText(agentBean.getName());
			tvName.setText(agentBean.getName());
			tvPhone.setText(agentBean.getTel());
			ImageAction.displayBrokerAvatar(agentBean.getPhoto(),
					imgAvatar);
//			ImageAction.displayImage(agentBean.getPhoto(), imgAvatar);
		}
		
		
		tvBack.setOnClickListener(this);
//		lltDial.setOnClickListener(this);
//		lltMsg.setOnClickListener(this);
		tvConsult.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_broker_info_back:
			finish();
			break;
//		case R.id.act_broker_info_phone_agent_dial:
//			if (agentBean != null) {
//				String phone = agentBean.getTel();
//				if (!TextUtils.isEmpty(phone)) {
//					Intent intent = new Intent(Intent.ACTION_CALL);
//					intent.setData(Uri.parse("tel:" + phone));
//					startActivity(intent);
//				}
//			}
//			break;
//		case R.id.act_broker_info_phone_agent_msg: // 发消息
//		    if (agentBean != null) {
//		        Jumper.newJumper()
//	            .setAheadInAnimation(R.anim.activity_push_in_right)
//	            .setAheadOutAnimation(R.anim.activity_alpha_out)
//	            .setBackInAnimation(R.anim.activity_alpha_in)
//	            .setBackOutAnimation(R.anim.activity_push_out_right)
//	            .putString(ChatActivity.INTENT_MESSAGE_TO_ID, agentBean.getAgentId())
//	            .putString(ChatActivity.INTENT_MESSAGE_TO_NAME, agentBean.getName())
//	            .jump(this, ChatActivity.class);
//		    }
//			break;
		case R.id.tv_broker_info_consult:
			PopViewHelper.showBrokerConsultPopupWindow(this, getWindow().getDecorView(), this);
			break;
		}
	}
	
	private void rendUI() {
		if (agentInfoDetailBean != null) {
		    ImageAction.displayImage(agentInfoDetailBean.getPhoto(), imgAvatar);
			tvSecondHandHouses.setText(agentInfoDetailBean.getSaleCount());
			tvRentHouses.setText(agentInfoDetailBean.getRentCount());
			tvAccessAmount.setText(agentInfoDetailBean.getClickCount());
			tvPhone.setText(agentInfoDetailBean.getTel());
			tvMotto.setText(agentInfoDetailBean.getRemark());
			listProperty.addAll(agentInfoDetailBean.getListProperty());
			adapter.notifyDataSetChanged();
		}
		
	}
	
	private void getAgentInfoDetail() {
		if (agentBean == null) {
			return;
		}
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.getAgentInfoDetail(agentBean.getAgentId(), new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				agentInfoDetailBean = JSON.parseObject(result.getData(), AgentInfoDetailBean.class);
				rendUI();
			}
		});
	}

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {
        // TODO Auto-generated method stub
        AgentInfoItemBean agentInfoItemBean = agentInfoDetailBean.getListProperty().get(position);
        Jumper.newJumper()
        .setAheadInAnimation(R.anim.activity_push_in_right)
        .setAheadOutAnimation(R.anim.activity_alpha_out)
        .setBackInAnimation(R.anim.activity_alpha_in)
        .setBackOutAnimation(R.anim.activity_push_out_right)
        .putString(SecondHandHouseDetailActivity.INTENT_HOUSE_SOURCE_ID, agentInfoItemBean.getPropertyId())
        .jump(this, SecondHandHouseDetailActivity.class);
    }
    
    @Override
    protected int getStatusBarTintResource() {
    	// TODO Auto-generated method stub
    	return R.color.white;
    }

	@Override
	public void onAvatarOptionClick(int action) {
		// TODO Auto-generated method stub
		switch (action) {
		case 0:   //  打电话
			if (agentBean != null) {
				String phone = agentBean.getTel();
				if (!TextUtils.isEmpty(phone)) {
					Intent intent = new Intent(Intent.ACTION_CALL);
					intent.setData(Uri.parse("tel:" + phone));
					startActivity(intent);
				}
			}
			break;
		case 1:   // 聊天
			if (agentBean != null) {
		        Jumper.newJumper()
	            .setAheadInAnimation(R.anim.activity_push_in_right)
	            .setAheadOutAnimation(R.anim.activity_alpha_out)
	            .setBackInAnimation(R.anim.activity_alpha_in)
	            .setBackOutAnimation(R.anim.activity_push_out_right)
	            .putString(ChatActivity.INTENT_MESSAGE_TO_ID, agentBean.getAgentId())
	            .putString(ChatActivity.INTENT_MESSAGE_TO_NAME, agentBean.getName())
	            .jump(this, ChatActivity.class);
		    }
			break;
		case 2:   // 发短消息
			if (agentBean != null) {
				String phone = agentBean.getTel();
			    if (!TextUtils.isEmpty(phone)) {
			    	Uri smsToUri = Uri.parse("smsto:" + phone);
					Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
//					intent.putExtra("sms_body", "测试发送短信");
					startActivity(intent);
			    }
		    }
			break;
		}
	}

}
