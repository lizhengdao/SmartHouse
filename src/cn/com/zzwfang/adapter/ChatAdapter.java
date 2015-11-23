package cn.com.zzwfang.adapter;


import com.easemob.chat.EMMessage;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ChatAdapter extends BaseAdapter {
	
	static class ViewType {
        private static final int TYPE_COUNT = 6 * 2;
        
        private static final int TYPE_TXT_REV = 0;
        private static final int TYPE_TXT_SENT = 1;
        private static final int TYPE_IMAGE_REV = 2;
        private static final int TYPE_IMAGE_SENT = 3;
        private static final int TYPE_LOCATION_REV = 4;
        private static final int TYPE_LOCATION_SENT = 5;
        private static final int TYPE_AUDIO_REV = 6;
        private static final int TYPE_AUDIO_SENT = 7;
        private static final int TYPE_VIDEO_REV = 8;
        private static final int TYPE_VIDEO_SENT = 9;
        private static final int TYPE_FILE_REV = 10;
        private static final int TYPE_FILE_SENT = 11;
    }
	
	@Override
    public int getViewTypeCount() {
        return ViewType.TYPE_COUNT;
    }
	
	@Override
    public int getItemViewType(int position) {
        Object obj = getItem(position);
        if (obj == null || !(obj instanceof EMMessage)) {
            return -1;
        }
        EMMessage message = (EMMessage) obj;
        switch (message.getType()) {
            case CMD: return -1;
            case FILE: return message.direct == EMMessage.Direct.RECEIVE ? ViewType.TYPE_FILE_REV : ViewType.TYPE_FILE_SENT;
            case IMAGE: return message.direct == EMMessage.Direct.RECEIVE ? ViewType.TYPE_IMAGE_REV : ViewType.TYPE_IMAGE_SENT;
            case LOCATION: return message.direct == EMMessage.Direct.RECEIVE ? ViewType.TYPE_LOCATION_REV : ViewType.TYPE_LOCATION_SENT;
            case TXT: return message.direct == EMMessage.Direct.RECEIVE ? ViewType.TYPE_TXT_REV : ViewType.TYPE_TXT_SENT;
            case VIDEO: return message.direct == EMMessage.Direct.RECEIVE ? ViewType.TYPE_VIDEO_REV : ViewType.TYPE_VIDEO_SENT;
            case VOICE: return message.direct == EMMessage.Direct.RECEIVE ? ViewType.TYPE_AUDIO_REV : ViewType.TYPE_AUDIO_SENT;
            default: return message.direct == EMMessage.Direct.RECEIVE ? ViewType.TYPE_TXT_REV : ViewType.TYPE_TXT_SENT;
        }
    }

	@Override
	public int getCount() {
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return null;
	}

}
