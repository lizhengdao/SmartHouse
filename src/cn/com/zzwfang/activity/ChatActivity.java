package cn.com.zzwfang.activity;

import com.easemob.EMEventListener;
import com.easemob.EMGroupChangeListener;
import com.easemob.EMNotifierEvent;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMChatRoom;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMGroup;
import com.easemob.chat.EMGroupManager;
import com.easemob.chat.EMMessage;

import cn.com.zzwfang.R;
import cn.com.zzwfang.im.MessageSendCallback.OnMessageSendListener;
import cn.com.zzwfang.pullview.AbPullToRefreshView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class ChatActivity extends BaseActivity implements OnClickListener, EMEventListener, OnMessageSendListener {

	private TextView tvBack;
	private AbPullToRefreshView pullView;
	private ListView listView;
	
	private int chatType;
    private String remoteId;
    
    private EMConversation emConversation;
    private EMGroup emGroup;
    private EMGroupChangeListener emGroupChangeListener;
    private EMChatRoom emChatRoom;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		initView();
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
	
	private void initView() {
		setContentView(R.layout.act_chat);
		tvBack = (TextView) findViewById(R.id.act_chat_back);
		pullView = (AbPullToRefreshView) findViewById(R.id.pull_chat);
		listView = (ListView) findViewById(R.id.lst_chat);
		
		tvBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_chat_back:
			finish();
			break;
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
}
