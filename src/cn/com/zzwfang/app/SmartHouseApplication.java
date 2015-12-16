package cn.com.zzwfang.app;

import java.util.Iterator;
import java.util.List;

import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMChatOptions;
import com.easemob.chat.EMMessage;
import com.easemob.chat.OnNotificationClickListener;
import com.easemob.chat.TextMessageBody;
import com.easemob.chat.EMMessage.Type;

import cn.com.zzwfang.action.ImageAction;
import cn.com.zzwfang.activity.ChatActivity;
import cn.com.zzwfang.activity.MessageActivity;
import cn.com.zzwfang.activity.BaseActivity.OnNewMessageListener;
import cn.com.zzwfang.bean.MessageBean;
import cn.com.zzwfang.config.AppConfiger;
import cn.com.zzwfang.im.MessagePool;
import cn.com.zzwfang.util.ContentUtils;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageManager;

public class SmartHouseApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		
		AppConfiger.registContext(getApplicationContext());
		ImageAction.init(getApplicationContext());
		
		
		int pid = android.os.Process.myPid();
		String processAppName = getAppName(pid);
		// 如果app启用了远程的service，此application:onCreate会被调用2次
		// 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
		// 默认的app会在以包名为默认的process name下运行，如果查到的process name不是app的process name就立即返回
		if (processAppName == null ||!processAppName.equalsIgnoreCase(getPackageName())) {
		    //"com.easemob.chatuidemo"为demo的包名，换到自己项目中要改成自己包名
		    // 则此application::onCreate 是被service 调用的，直接返回
		    return;
		}
		
		EMChat.getInstance().init(getApplicationContext());
		EMChatOptions options = EMChatManager.getInstance().getChatOptions();
		options.setAcceptInvitationAlways(true);
		options.setNoticeBySound(true);
		options.setNoticedByVibrate(true);
		
		options.setOnNotificationClickListener(new OnNotificationClickListener() {
            
            @Override
            public Intent onNotificationClick(EMMessage message) {
            	
            	Intent intent = null;
            	
            	if (message.getType() == Type.TXT) {
                    TextMessageBody txtBody = (TextMessageBody) message.getBody();
                    MessageBean msg = new MessageBean();
                    msg.setId(message.getMsgId());
                    msg.setFromUser(message.getFrom());
                    msg.setToUser(message.getTo());
                    msg.setMessage(txtBody.getMessage());
                    msg.setCreateDateLong(message.getMsgTime());
                    msg.setRead(!message.isUnread());
                    msg.setUserId(message.getFrom());
                    msg.setUserName(message.getUserName());
                    
                    MessagePool.addMessage(msg);
                    intent = new Intent(getApplicationContext(), ChatActivity.class);
                    intent.putExtra(ChatActivity.INTENT_IM_MESSAGE, msg);
                }
            	
                return intent;
            }
        });
	}
	
	private String getAppName(int pID) {
		String processName = null;
		ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
		List l = am.getRunningAppProcesses();
		Iterator i = l.iterator();
		PackageManager pm = this.getPackageManager();
		while (i.hasNext()) {
			ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
			try {
				if (info.pid == pID) {
//					CharSequence c = pm.getApplicationLabel(pm.getApplicationInfo(info.processName, PackageManager.GET_META_DATA));
					// Log.d("Process", "Id: "+ info.pid +" ProcessName: "+
					// info.processName +"  Label: "+c.toString());
					// processName = c.toString();
					processName = info.processName;
					return processName;
				}
			} catch (Exception e) {
				// Log.d("Process", "Error>> :"+ e.toString());
			}
		}
		return processName;
	}

}
