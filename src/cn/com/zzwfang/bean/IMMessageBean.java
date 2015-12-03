package cn.com.zzwfang.bean;

import java.util.ArrayList;

public class IMMessageBean extends BaseBean {

	private String userId;
	
	private String userName;
	
	private ArrayList<MessageBean> messages;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public ArrayList<MessageBean> getMessages() {
		return messages;
	}

	public void setMessages(ArrayList<MessageBean> messages) {
		this.messages = messages;
	}
	
	
}
