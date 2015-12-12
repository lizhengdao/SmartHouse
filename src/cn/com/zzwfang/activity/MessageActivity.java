package cn.com.zzwfang.activity;
import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.adapter.MessageAdapter;
import cn.com.zzwfang.bean.IMMessageBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.util.Jumper;

import com.alibaba.fastjson.JSON;


/**
 * 消息列表页
 * @author lzd
 *
 */
public class MessageActivity extends BaseActivity implements OnClickListener, OnItemClickListener {

	private TextView tvBack;
	private ListView lstMessage;
	
	private ArrayList<IMMessageBean> imMessageBeans = new ArrayList<IMMessageBean>();
	private MessageAdapter adapter;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.act_message);
		initView();
	}
	
	private void initView() {
		tvBack = (TextView) findViewById(R.id.act_message_back);
		lstMessage = (ListView) findViewById(R.id.act_message_lst);
		
		adapter = new MessageAdapter(this, imMessageBeans);
		lstMessage.setAdapter(adapter);
		
		tvBack.setOnClickListener(this);
		lstMessage.setOnItemClickListener(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		getContacts();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_message_back:   //  返回
			finish();
			break;
		}
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
				Log.i("--->", "msg activity data -- " + result.getData());
				
				ArrayList<IMMessageBean> temp = (ArrayList<IMMessageBean>) JSON.parseArray(result.getData(), IMMessageBean.class);
				imMessageBeans.clear();
				imMessageBeans.addAll(temp);
				adapter.notifyDataSetChanged();
			}
		});
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		IMMessageBean imMessageBean = imMessageBeans.get(position);
		Jumper.newJumper()
		.setAheadInAnimation(R.anim.activity_push_in_right)
		.setAheadOutAnimation(R.anim.activity_alpha_out)
		.setBackInAnimation(R.anim.activity_alpha_in)
		.setBackOutAnimation(R.anim.activity_push_out_right)
		.putString(ChatActivity.INTENT_MESSAGE_TO_ID, imMessageBean.getUserId())
		.putString(ChatActivity.INTENT_MESSAGE_TO_NAME, imMessageBean.getUserName())
		.putSerializable(ChatActivity.INTENT_HISTORY_MSG, imMessageBean.getMessages())
		.jump(this, ChatActivity.class);
	}
}
