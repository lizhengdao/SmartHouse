package cn.com.zzwfang.activity;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.adapter.ChatAdapter;
import cn.com.zzwfang.bean.IMMessageBean;
import cn.com.zzwfang.bean.MessageBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.util.ToastUtils;

import com.alibaba.fastjson.JSON;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMMessage;
import com.easemob.chat.EMMessage.Type;
import com.easemob.chat.TextMessageBody;

public class ChatActivity extends BaseActivity implements OnClickListener{  // , EMEventListener, OnMessageSendListener 

	public static final String INTENT_MESSAGE_TO_ID = "intent_message_to_user_id";
	public static final String INTENT_MESSAGE_TO_NAME = "intent_message_to_user_name";
	public static final String INTENT_HISTORY_MSG = "intent_history_msg";
	private TextView tvBack, tvTitle, tvSendMessage;
	private EditText edtMessage;
	private ListView listView;
	
    private ActionImpl actionImpl;
    
    /**
     * 从其他地方进来单聊，获取历史消息
     */
    private IMMessageBean imMessages;
    private ArrayList<MessageBean> messages = new ArrayList<MessageBean>();
    /**
     * 从消息列表传来
     */
    private ArrayList<MessageBean> historyMsgs;
    
    private ChatAdapter adapter;
    
    private String messageTo;
    private String messageName;
    private NewMessageBroadcastReceiver msgReceiver;
    
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
	    msgReceiver = new NewMessageBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter(EMChatManager.getInstance().getNewMessageBroadcastAction());
		intentFilter.setPriority(3);
		registerReceiver(msgReceiver, intentFilter);
		
		messageTo = getIntent().getStringExtra(INTENT_MESSAGE_TO_ID);
		messageName = getIntent().getStringExtra(INTENT_MESSAGE_TO_NAME);
		historyMsgs = (ArrayList<MessageBean>) getIntent().getSerializableExtra(INTENT_HISTORY_MSG);
		actionImpl = ActionImpl.newInstance(this);
		
		initView();
		
			
	}
	
	private void initView() {
		setContentView(R.layout.act_chat);
		tvBack = (TextView) findViewById(R.id.act_chat_back);
		tvTitle = (TextView) findViewById(R.id.act_chat_title);
		edtMessage = (EditText) findViewById(R.id.act_chat_content_edt);
		tvSendMessage = (TextView) findViewById(R.id.act_chat_send_tv);
		listView = (ListView) findViewById(R.id.lst_chat);
		
		tvTitle.setText(messageName);
		
		adapter = new ChatAdapter(this, messages);
		listView.setAdapter(adapter);
		tvBack.setOnClickListener(this);
		tvSendMessage.setOnClickListener(this);
		// 从消息列表页来
		if (historyMsgs != null && historyMsgs.size() > 0) {
			messages.addAll(historyMsgs);
			adapter.notifyDataSetChanged();
			readMessage(historyMsgs);
		} else {  //  从其他聊天页来
			if (!TextUtils.isEmpty(messageTo)) {
				getHistoryMsg(messageTo);
			}
		}
		
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

	}
	

	
	private void sendMessage() {
		final String message = edtMessage.getText().toString().trim();
		if (TextUtils.isEmpty(message)) {
			ToastUtils.SHORT.toast(this, "请输入消息内容");
			return;
		}
		final String userId = ContentUtils.getUserId(this);
		actionImpl.sendMessage(userId, messageTo, message, new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				edtMessage.setText("");
				MessageBean msg = new MessageBean();
				msg.setFromUser(userId);
				msg.setMessage(message);
				msg.setCreateDateLong(System.currentTimeMillis());
				adapter.addMessage(msg);
			}
		});
	}
	
	private class NewMessageBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
		    // 注销广播
			abortBroadcast();
	 
			// 消息id（每条消息都会生成唯一的一个id，目前是SDK生成）
			String msgId = intent.getStringExtra("msgid");
			//发送方
			String username = intent.getStringExtra("from");
			// 收到这个广播的时候，message已经在db和内存里了，可以通过id获取mesage对象
			EMMessage message = EMChatManager.getInstance().getMessage(msgId);
//			EMConversation	conversation = EMChatManager.getInstance().getConversation(username);
			
			// 如果是群聊消息，获取到group id
//			if (message.getChatType() == ChatType.GroupChat) {
//				username = message.getTo();
//			}
//			if (!username.equals(username)) {
//				// 消息不是发给当前会话，return
//				return;
//			}
			if (message.getType() == Type.TXT) {
				TextMessageBody txtBody = (TextMessageBody) message.getBody();
				MessageBean msg = new MessageBean();
				msg.setFromUser(message.getFrom());
				msg.setMessage(txtBody.getMessage());
				msg.setCreateDateLong(message.getMsgTime());
				adapter.addMessage(msg);
			}
			
			
		}
	}
	
	private void getHistoryMsg(String contactId) {
		actionImpl.getMessageRecordsWithSomeBody(contactId, new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				imMessages = JSON.parseObject(result.getData(), IMMessageBean.class);
				if (imMessages != null) {
					ArrayList<MessageBean> messages = imMessages.getMessages();
					if (messages != null) {
						adapter.addMessages(messages);
					}
				}
			}
		});
		
//		actionImpl.getContactsList(contactId, new ResultHandlerCallback() {
//			
//			@Override
//			public void rc999(RequestEntity entity, Result result) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void rc3001(RequestEntity entity, Result result) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void rc0(RequestEntity entity, Result result) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
	}
	
	private void readMessage(ArrayList<MessageBean> messages) {
		if (messages == null && messages.size() == 0) {
			return;
		}
		String ids = "";
		for (MessageBean msg : messages) {
			if (!msg.isRead()) {
				ids += msg.getId()+ ",";
			}
		}
		if (!TextUtils.isEmpty(ids) && ids.endsWith(",")) {
			ids = ids.substring(0, ids.length() - 1);
		} else {
			return;
		}
		
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.readMessage(ids, new ResultHandlerCallback() {
			
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
	
	@Override
		protected void onDestroy() {
		unregisterReceiver(msgReceiver);
			super.onDestroy();
		}
}
