package cn.com.zzwfang.im;

import java.util.ArrayList;

import android.text.TextUtils;
import cn.com.zzwfang.bean.IMMessageBean;
import cn.com.zzwfang.bean.MessageBean;

public class MessagePool {

    private static ArrayList<IMMessageBean> imMessageBeans = new ArrayList<IMMessageBean>();
    
    public static void addAllContactsMessages(ArrayList<IMMessageBean> allContactsMessages) {
    	if (imMessageBeans == null) {
    		imMessageBeans = new ArrayList<IMMessageBean>();
    	}
    	imMessageBeans.addAll(allContactsMessages);
    }
    
    public static void addMessage(MessageBean msg) {
    	if (msg == null) {
    		return;
    	}
    	if (imMessageBeans == null) {
    		imMessageBeans = new ArrayList<IMMessageBean>();
    	}
    	boolean flag = true;
    	for (IMMessageBean immsgBean : imMessageBeans) {
    		if (immsgBean.getUserId().equals(msg.getUserId())) {
    			immsgBean.getMessages().add(immsgBean.getMessages().size(), msg);
    			flag = false;
    			break;
    		}
    	}
    	if (flag) {
    		IMMessageBean imMessageBean = new IMMessageBean();
    		imMessageBean.setUserId(msg.getUserId());
    		imMessageBean.setUserName(msg.getUserName());
    		
    		ArrayList<MessageBean> messages = new ArrayList<MessageBean>();
    		messages.add(msg);
    		imMessageBean.setMessages(messages);
    	}
    }
    
    public static ArrayList<IMMessageBean> getAllContactsMessages() {
    	return imMessageBeans;
    }
    
    
    public static IMMessageBean getMessagesByContactId(String contactId) {
    	if (TextUtils.isEmpty(contactId)) {
    		return null;
    	}
    	if (imMessageBeans == null) {
    		return null;
    	} else {
    		for (IMMessageBean immsgBean : imMessageBeans) {
        		if (immsgBean.getUserId().equals(contactId)) {
        			return immsgBean;
        		}
        	}
    	}
    	return null;
    }
    
    public static void clearAllMessages() {
    	if (imMessageBeans != null) {
    		imMessageBeans.clear();
    	}
    }
    
}
