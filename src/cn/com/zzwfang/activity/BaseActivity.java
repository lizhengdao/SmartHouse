package cn.com.zzwfang.activity;

import java.util.ArrayList;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMMessage;
import com.easemob.chat.EMMessage.Type;
import com.easemob.chat.TextMessageBody;
import com.easemob.exceptions.EaseMobException;

import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.MessageBean;
import cn.com.zzwfang.im.MessagePool;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.util.Jumper;
import cn.com.zzwfang.util.SystemBarTintManager;
import cn.com.zzwfang.util.ToastUtils;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;


public abstract class BaseActivity extends FragmentActivity {
	
	private int backInAnimationId;
    private int backOutAnimationId;
    
	public static ArrayList<Activity> activities = new ArrayList<Activity>();
	private NewMessageBroadcastReceiver msgReceiver;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		msgReceiver = new NewMessageBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(EMChatManager.getInstance().getNewMessageBroadcastAction());
        intentFilter.setPriority(3);
        registerReceiver(msgReceiver, intentFilter);
		activities.add(this);
		
		Intent intent = getIntent();
        backInAnimationId = intent.getIntExtra(Jumper.JUMPER_ANIMATION_BACK_IN, 0);
        backOutAnimationId = intent.getIntExtra(Jumper.JUMPER_ANIMATION_BACK_OUT, 0);
	}
	
	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
		}
		
		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setStatusBarTintResource(getStatusBarTintResource());
	}
	
	@Override
	protected void onDestroy() {
	    unregisterReceiver(msgReceiver);
		super.onDestroy();
		activities.remove(this);
	}
	
	@Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(backInAnimationId, backOutAnimationId);
    }
	
	@TargetApi(Build.VERSION_CODES.KITKAT)
	private void setTranslucentStatus(boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}
	
	protected int getStatusBarTintResource() {
        return R.color.color_app_theme;
    }
	
	public void exitApplication(boolean needKillProcess) {
		
		if (activities != null && activities.size() != 0) {
			for (Activity activity : activities) {
				activity.finish();
			}
			
			if (needKillProcess) {
				Process.killProcess(Process.myPid());
		        System.exit(1);
			}
		}
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
//          EMConversation  conversation = EMChatManager.getInstance().getConversation(username);
            
            // 如果是群聊消息，获取到group id
//          if (message.getChatType() == ChatType.GroupChat) {
//              username = message.getTo();
//          }
//          if (!username.equals(username)) {
//              // 消息不是发给当前会话，return
//              return;
//          }
            
            String extMsgId = null;
            try {
                extMsgId = message.getStringAttribute("msgId");
            } catch (EaseMobException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
//            ToastUtils.SHORT.toast(context, "消息来了");
//            Log.i("--->", "消息来了");
            if (message.getType() == Type.TXT) {
                TextMessageBody txtBody = (TextMessageBody) message.getBody();
                
                MessageBean msg = new MessageBean();
                
//                msg.setId(message.getMsgId());
                if (TextUtils.isEmpty(extMsgId)) {
                    msg.setId(extMsgId);
                }
                msg.setFromUser(message.getFrom());
                msg.setToUser(message.getTo());
                msg.setMessage(txtBody.getMessage());
                msg.setCreateDateLong(message.getMsgTime());
                msg.setRead(!message.isUnread());
                msg.setUserId(message.getFrom());
                msg.setUserName(message.getUserName());
                MessagePool.addMessage(msg);
                
                boolean isReceiveMsg = ContentUtils.getMessageReceiveSetting(context);
                if (isReceiveMsg) {
                	for (Activity act : activities) {
                		if (act instanceof OnNewMessageListener) {
                			((OnNewMessageListener) act).onNewMessage(msg);
                		}
                	}
                }
            }
        }
    }
	
	public interface OnNewMessageListener {
	
		void onNewMessage(MessageBean msg);
	}

}
