package cn.com.zzwfang.adapter;


import java.util.ArrayList;

import cn.com.zzwfang.R;
import cn.com.zzwfang.action.ImageAction;
import cn.com.zzwfang.bean.IMMessageBean;
import cn.com.zzwfang.util.ContentUtils;

import com.easemob.chat.EMMessage;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ChatAdapter extends BaseAdapter {
	
	private Context context;
	
	private ArrayList<IMMessageBean> imMessages;
	
	private String myId;
	
	private ViewHolderLeft viewHolderLeft = null;
	private ViewHolderRight viewHolderRight = null;
	
	public ChatAdapter(Context context, ArrayList<IMMessageBean> imMessages) {
		this.context = context;
		this.imMessages = imMessages;
		myId = ContentUtils.getUserId(context);
	}
	
	static class ViewType {
        private static final int TYPE_COUNT = 2;
        
        private static final int TYPE_TXT_REV = 0;
        private static final int TYPE_TXT_SENT = 1;
//        private static final int TYPE_IMAGE_REV = 2;
//        private static final int TYPE_IMAGE_SENT = 3;
//        private static final int TYPE_LOCATION_REV = 4;
//        private static final int TYPE_LOCATION_SENT = 5;
//        private static final int TYPE_AUDIO_REV = 6;
//        private static final int TYPE_AUDIO_SENT = 7;
//        private static final int TYPE_VIDEO_REV = 8;
//        private static final int TYPE_VIDEO_SENT = 9;
//        private static final int TYPE_FILE_REV = 10;
//        private static final int TYPE_FILE_SENT = 11;
    }
	
	@Override
    public int getViewTypeCount() {
        return ViewType.TYPE_COUNT;
    }
	
	@Override
    public int getItemViewType(int position) {
        Object obj = getItem(position);
        if (obj == null || !(obj instanceof IMMessageBean)) {
        	return -1;
        }
        IMMessageBean imMessageBean = (IMMessageBean) obj;
        if (myId.equals(imMessageBean.getUserId())) {
        	return ViewType.TYPE_TXT_SENT;
        } else {
        	return ViewType.TYPE_TXT_REV;
        }
        
        
//        if (obj == null || !(obj instanceof EMMessage)) {
//            return -1;
//        }
//        EMMessage message = (EMMessage) obj;
//        switch (message.getType()) {
//            case CMD: return -1;
//            case FILE: return message.direct == EMMessage.Direct.RECEIVE ? ViewType.TYPE_FILE_REV : ViewType.TYPE_FILE_SENT;
//            case IMAGE: return message.direct == EMMessage.Direct.RECEIVE ? ViewType.TYPE_IMAGE_REV : ViewType.TYPE_IMAGE_SENT;
//            case LOCATION: return message.direct == EMMessage.Direct.RECEIVE ? ViewType.TYPE_LOCATION_REV : ViewType.TYPE_LOCATION_SENT;
//            case TXT: return message.direct == EMMessage.Direct.RECEIVE ? ViewType.TYPE_TXT_REV : ViewType.TYPE_TXT_SENT;
//            case VIDEO: return message.direct == EMMessage.Direct.RECEIVE ? ViewType.TYPE_VIDEO_REV : ViewType.TYPE_VIDEO_SENT;
//            case VOICE: return message.direct == EMMessage.Direct.RECEIVE ? ViewType.TYPE_AUDIO_REV : ViewType.TYPE_AUDIO_SENT;
//            default: return message.direct == EMMessage.Direct.RECEIVE ? ViewType.TYPE_TXT_REV : ViewType.TYPE_TXT_SENT;
//        }
    }

	@Override
	public int getCount() {
		if (imMessages == null) {
			return 0;
		}
		return imMessages.size();
	}

	@Override
	public Object getItem(int position) {
		return imMessages.get(position);
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
				viewHolderLeft.ivAvatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
				viewHolderLeft.tvName = (TextView) convertView.findViewById(R.id.tv_name);
				viewHolderLeft.tvMsgContent = (TextView) convertView.findViewById(R.id.tv_msg_content);
				convertView.setTag(viewHolderLeft);
				break;
			case 1:   // 右边
				viewHolderRight = new ViewHolderRight();
				convertView = View.inflate(context, R.layout.item_appointment_msg_right, null);
				viewHolderRight.ivAvatarRight = (ImageView) convertView.findViewById(R.id.iv_avatar_right);
				viewHolderRight.tvMsgRight = (TextView) convertView.findViewById(R.id.tv_msg_content_right);
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
		
//		IMMessageBean imMessageBean = imMessages.get(position);
//		String avatarUrl = msg.getAvatar();
//		switch (type) {
//		case 0:
//			viewHolderLeft.tvName.setText(msg.getNickname());
//			viewHolderLeft.tvMsgContent.setText(msg.getContent());
//			
//			if (TextUtils.isEmpty(avatarUrl)) {
//				ImageAction.displayAvatar(uri, imageAware);
//				Picasso.with(getActivity()).load(R.drawable.icon_avatar_default).into(viewHolderLeft.ivAvatar);
//			} else {
//				Picasso.with(getActivity()).load(avatarUrl).error(R.drawable.icon_avatar_default).into(viewHolderLeft.ivAvatar);
//			}
//			break;
//		case 1:
//			viewHolderRight.tvMsgRight.setText(msg.getContent());
//			if (TextUtils.isEmpty(avatarUrl)) {
//				Picasso.with(getActivity()).load(R.drawable.icon_avatar_default).into(viewHolderRight.ivAvatarRight);
//			} else {
//				Picasso.with(getActivity()).load(avatarUrl).error(R.drawable.icon_avatar_default).into(viewHolderRight.ivAvatarRight);
//			}
//			
//			break;
//		}
		return convertView;
	}
	
    static class ViewHolderLeft {
		
		ImageView ivAvatar;
		
		TextView tvName;
		
		TextView tvMsgContent;
		
	}
	
	static class ViewHolderRight {
		
		ImageView ivAvatarRight;
		
		TextView tvMsgRight;
	}

}
