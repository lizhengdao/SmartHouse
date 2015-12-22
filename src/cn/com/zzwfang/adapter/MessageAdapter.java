package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.IMMessageBean;
import cn.com.zzwfang.bean.MessageBean;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MessageAdapter extends BaseAdapter {

	private Context context;
	
	private ArrayList<IMMessageBean> imMessageBeans;
	
	public MessageAdapter(Context context, ArrayList<IMMessageBean> imMessageBeans) {
		this.context = context;
		this.imMessageBeans = imMessageBeans;
	}
	
	@Override
	public int getCount() {
		if (imMessageBeans == null) {
			return 0;
		}
		return imMessageBeans.size();
	}

	@Override
	public Object getItem(int position) {
		return imMessageBeans.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.adapter_message, null);
		}
		
//		PathImage avatar = ViewHolder.get(convertView, R.id.adapter_message_avatar);
		TextView tvName = ViewHolder.get(convertView, R.id.adapter_message_name);
		TextView tvMsg = ViewHolder.get(convertView, R.id.adapter_message_content);
		
		IMMessageBean msgBean = imMessageBeans.get(position);
		tvName.setText(msgBean.getUserName());
		ArrayList<MessageBean> msgs = msgBean.getMessages();
		if (msgs != null && msgs.size() > 0) {
			MessageBean msg = msgs.get(msgs.size() - 1);
			if (msg != null) {
				tvMsg.setText(msg.getMessage());
			}
		}
		
		return convertView;
	}

}
