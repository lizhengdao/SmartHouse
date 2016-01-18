package cn.com.zzwfang.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.activity.BaseActivity.OnNewMessageListener;
import cn.com.zzwfang.adapter.ChatAdapter;
import cn.com.zzwfang.bean.IMContactBean;
import cn.com.zzwfang.bean.IMMessageBean;
import cn.com.zzwfang.bean.MessageBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.im.MessagePool;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.util.ToastUtils;

import com.alibaba.fastjson.JSON;

public class ChatActivity extends BaseActivity implements OnClickListener, OnNewMessageListener {

	public static final String INTENT_MESSAGE_TO_ID = "intent_message_to_user_id";
	public static final String INTENT_MESSAGE_TO_NAME = "intent_message_to_user_name";
	public static final String INTENT_HISTORY_MSG = "intent_history_msg";
	public static final String INTENT_IM_MESSAGE = "intent_im_message";
	
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
    
    /**
     * 当程序在后台运行，收到消息，出现Notification，点击跳转过来
     */
    private MessageBean msg;
    
//    private ArrayList<IMContactBean> contactBeans;
    
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		Intent intent = getIntent();
		messageTo = intent.getStringExtra(INTENT_MESSAGE_TO_ID);
		messageName = intent.getStringExtra(INTENT_MESSAGE_TO_NAME);
		historyMsgs = (ArrayList<MessageBean>) intent.getSerializableExtra(INTENT_HISTORY_MSG);
		msg = (MessageBean) intent.getSerializableExtra(INTENT_IM_MESSAGE);
		
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
		
		if (!TextUtils.isEmpty(messageName)) {
			tvTitle.setText(messageName);
		}
		
		adapter = new ChatAdapter(this, messages);
		listView.setAdapter(adapter);
		tvBack.setOnClickListener(this);
		tvSendMessage.setOnClickListener(this);
		// 从消息列表页来
		if (historyMsgs != null && historyMsgs.size() > 0) {
			messages.addAll(historyMsgs);
			adapter.notifyDataSetChanged();
//			readMessage(historyMsgs);
//			listView.setSelection(ListView.FOCUS_DOWN);
			listView.setSelection(adapter.getCount());
		} else {  //  从其他聊天页来
			if (!TextUtils.isEmpty(messageTo)) {
				imMessages = MessagePool.getMessagesByContactId(messageTo);
				if (imMessages != null) {
					ArrayList<MessageBean> messages = imMessages.getMessages();
					if (messages != null) {
						adapter.addMessages(messages);
//						listView.setSelection(ListView.FOCUS_DOWN);
						listView.setSelection(adapter.getCount());
					}
				}
				getHistoryMsg(messageTo);
			}
		}
		
		// 从Notification来
		if (msg != null) {
			imMessages = MessagePool.getMessagesByContactId(msg.getUserId());
			if (imMessages != null) {
				ArrayList<MessageBean> messages = imMessages.getMessages();
				if (messages != null) {
					adapter.addMessages(messages);
//					listView.setSelection(ListView.FOCUS_DOWN);
					listView.setSelection(adapter.getCount());
				}
			}
			getHistoryMsg(msg.getUserId());
		}
		
//		readMessage(messages);
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
				listView.setSelection(adapter.getCount());
			}
		});
	}
	
	@Override
	public void onNewMessage(MessageBean msg) {
	    adapter.addMessage(msg);
	    listView.setSelection(adapter.getCount());
	    ArrayList<MessageBean> temp = new ArrayList<MessageBean>();
	    temp.add(msg);
	    readMessage(temp);
	}
	
	private void getHistoryMsg(String contactId) {
		if (TextUtils.isEmpty(contactId)) {
			return;
		}
		actionImpl.getMessageRecordsWithSomeBody(contactId, new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				ArrayList<IMContactBean> contactBeans = (ArrayList<IMContactBean>) JSON.parseArray(result.getData(), IMContactBean.class);
				if (contactBeans != null && contactBeans.size() > 0) {
					IMContactBean contact = contactBeans.get(0);
					if (msg != null) {
						msg.setUserName(contact.getUserName());
					}
					tvTitle.setText(contact.getUserName());
				}
			}
		});
		
	}
	
	private void readMessage(final ArrayList<MessageBean> contactMessages) {
		if (contactMessages == null || contactMessages.size() == 0) {
			return;
		}
		String ids = "";
		for (MessageBean msg : contactMessages) {
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
                for (MessageBean msgBean : messages) {
                	msgBean.setRead(true);
				}
			}
		});
		
	}
	
}
