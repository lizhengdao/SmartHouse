package cn.com.zzwfang.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.im.MessageSendCallback.OnMessageSendListener;
import cn.com.zzwfang.pullview.AbPullToRefreshView;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.util.ToastUtils;

import com.easemob.EMEventListener;
import com.easemob.EMGroupChangeListener;
import com.easemob.EMNotifierEvent;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMChatRoom;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMGroup;
import com.easemob.chat.EMGroupManager;
import com.easemob.chat.EMMessage;

public class ChatActivity extends BaseActivity implements OnClickListener, EMEventListener, OnMessageSendListener {

	public static final String INTENT_MESSAGE_TO_ID = "intent_message_to_user_id";
	public static final String INTENT_MESSAGE_TO_NAME = "intent_message_to_user_name";
	private TextView tvBack, tvTitle, tvSendMessage;
	private EditText edtMessage;
	private AbPullToRefreshView pullView;
	private ListView listView;
	
	private int chatType;
    private String remoteId;
    
    private ActionImpl actionImpl;
    
    private EMConversation emConversation;
    private EMGroup emGroup;
    private EMGroupChangeListener emGroupChangeListener;
    private EMChatRoom emChatRoom;
    
    private String messageTo;
    private String messageName;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		messageTo = getIntent().getStringExtra(INTENT_MESSAGE_TO_ID);
		messageName = getIntent().getStringExtra(INTENT_MESSAGE_TO_NAME);
		actionImpl = ActionImpl.newInstance(this);
		initView();
	}
	
	private void initView() {
		setContentView(R.layout.act_chat);
		tvBack = (TextView) findViewById(R.id.act_chat_back);
		tvTitle = (TextView) findViewById(R.id.act_chat_title);
		edtMessage = (EditText) findViewById(R.id.act_chat_content_edt);
		tvSendMessage = (TextView) findViewById(R.id.act_chat_send_tv);
		pullView = (AbPullToRefreshView) findViewById(R.id.pull_chat);
		listView = (ListView) findViewById(R.id.lst_chat);
		
		tvTitle.setText(messageName);
		
		tvBack.setOnClickListener(this);
		tvSendMessage.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_chat_back:
			finish();
			break;
		case R.id.act_chat_send_tv:
			sendMessage();
			break;
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		EMChatManager.getInstance().registerEventListener(
                this,
                new EMNotifierEvent.Event[] { EMNotifierEvent.Event.EventNewMessage,EMNotifierEvent.Event.EventOfflineMessage,
                        EMNotifierEvent.Event.EventDeliveryAck, EMNotifierEvent.Event.EventReadAck });
	}
	
	@Override
    protected void onStop() {
        EMChatManager.getInstance().unregisterEventListener(this);
        super.onStop();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(emGroupChangeListener != null){
            EMGroupManager.getInstance().removeGroupChangeListener(emGroupChangeListener);
        }
    }
	
	

	

	@Override
	public void onEvent(EMNotifierEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSuccess(EMMessage message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProgress(EMMessage message, int arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(EMMessage message, int arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}
	
	private void sendMessage() {
		String message = edtMessage.getText().toString().trim();
		if (TextUtils.isEmpty(message)) {
			ToastUtils.SHORT.toast(this, "请输入消息内容");
			return;
		}
		String userId = ContentUtils.getUserId(this);
		actionImpl.sendMessage(userId, messageTo, message, new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
			}
		});
	}
}
