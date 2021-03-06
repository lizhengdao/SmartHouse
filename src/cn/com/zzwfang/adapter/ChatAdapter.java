package cn.com.zzwfang.adapter;


import java.util.ArrayList;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.action.ImageAction;
import cn.com.zzwfang.bean.MessageBean;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.util.TimeUtils;
import cn.com.zzwfang.view.PathImage;

public class ChatAdapter extends BaseAdapter {
	
	private Context context;
	
	private ArrayList<MessageBean> messages;
	
	private String myId;
	private String myAvaterUrl;
	
	private ViewHolderLeft viewHolderLeft = null;
	private ViewHolderRight viewHolderRight = null;
	
	public ChatAdapter(Context context, ArrayList<MessageBean> messages) {
		this.context = context;
		this.messages = messages;
		myId = ContentUtils.getUserId(context);
		myAvaterUrl = ContentUtils.getUserAvatar(context);
	}
	
	public void addMessage(MessageBean msg) {
		if (msg != null && messages != null) {
			messages.add(msg);
			notifyDataSetChanged();
		}
	}
	
	public void addMessages(ArrayList<MessageBean> msgs) {
		if (messages != null && msgs != null && msgs.size() > 0) {
			messages.addAll(msgs);
			notifyDataSetChanged();
		}
	}
	
	
	static class ViewType {
        private static final int TYPE_COUNT = 2;
        
        private static final int TYPE_TXT_REV = 0;
        private static final int TYPE_TXT_SENT = 1;
    }
	
	@Override
    public int getViewTypeCount() {
        return ViewType.TYPE_COUNT;
    }
	
	@Override
    public int getItemViewType(int position) {
        Object obj = getItem(position);
        if (obj == null || !(obj instanceof MessageBean)) {
        	return -1;
        }
        MessageBean messageBean = (MessageBean) obj;
        if (myId.equals(messageBean.getFromUser())) {
        	return ViewType.TYPE_TXT_SENT;
        } else {
        	return ViewType.TYPE_TXT_REV;
        }
        
    }

	@Override
	public int getCount() {
		if (messages == null) {
			return 0;
		}
		return messages.size();
	}

	@Override
	public Object getItem(int position) {
		return messages.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int type = getItemViewType(position);
		if (convertView == null) {
			switch (type) {
			case 0:   // 左边
				viewHolderLeft = new ViewHolderLeft();
				convertView = View.inflate(context, R.layout.item_appointment_msg_left, null);
				viewHolderLeft.ivAvatar = (PathImage) convertView.findViewById(R.id.adapter_chat_left_avatar);
				viewHolderLeft.tvTime = (TextView) convertView.findViewById(R.id.adapter_chat_left_time);
				viewHolderLeft.tvMsgContent = (TextView) convertView.findViewById(R.id.tv_msg_content);
				convertView.setTag(viewHolderLeft);
				break;
			case 1:   // 右边
				viewHolderRight = new ViewHolderRight();
				convertView = View.inflate(context, R.layout.item_appointment_msg_right, null);
				viewHolderRight.ivAvatarRight = (PathImage) convertView.findViewById(R.id.iv_avatar_right);
				viewHolderRight.tvMsgRight = (TextView) convertView.findViewById(R.id.tv_msg_content_right);
				viewHolderRight.tvTime = (TextView) convertView.findViewById(R.id.adapter_chat_right_time);
				convertView.setTag(viewHolderRight);
				break;
			}
		} else {
			switch (type) {
			case 0:
				viewHolderLeft = (ViewHolderLeft) convertView.getTag();
				break;
			case 1:
				viewHolderRight = (ViewHolderRight) convertView.getTag();
				break;
			}
			
		}
		
		MessageBean msg = messages.get(position);
//		String avatarUrl = msg.getAvatar();
		switch (type) {
		case 0:
			viewHolderLeft.tvMsgContent.setText(msg.getMessage());
			String time = TimeUtils.levelSimple(context, msg.getCreateDateLong());
			viewHolderLeft.tvTime.setText(time);
			
//			if (!TextUtils.isEmpty(avatarUrl)) {
//				ImageAction.displayAvatar(avatarUrl, viewHolderLeft.ivAvatar);
//			}
			break;
		case 1:
			viewHolderRight.tvMsgRight.setText(msg.getMessage());
			String timeRight = TimeUtils.levelSimple(context, msg.getCreateDateLong());
			viewHolderRight.tvTime.setText(timeRight);
			
			if (!TextUtils.isEmpty(myAvaterUrl)) {
				ImageAction.displayAvatar(myAvaterUrl, viewHolderRight.ivAvatarRight);
			}
			break;
		}
		return convertView;
	}
	
    static class ViewHolderLeft {
		
    	PathImage ivAvatar;
    	
    	TextView tvTime;
		
		TextView tvMsgContent;
		
	}
	
	static class ViewHolderRight {
		
		PathImage ivAvatarRight;
		
		TextView tvMsgRight;
		
		TextView tvTime;
	}

}
