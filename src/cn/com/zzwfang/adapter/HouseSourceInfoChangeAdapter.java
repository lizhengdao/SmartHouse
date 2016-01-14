package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.PrpChangeFollowBean;

public class HouseSourceInfoChangeAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<PrpChangeFollowBean> clientInfoChanges;
	
	public HouseSourceInfoChangeAdapter(Context context, ArrayList<PrpChangeFollowBean> clientInfoChanges) {
		
		this.context = context;
		this.clientInfoChanges = clientInfoChanges;
	}
	@Override
	public int getCount() {
		if (clientInfoChanges == null) {
			return 0;
		}
		return clientInfoChanges.size();
	}

	@Override
	public Object getItem(int position) {
		return clientInfoChanges.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.adapter_client_info_change, null);
		}
		
		TextView tvDateAndType = ViewHolder.get(convertView, R.id.adapter_client_info_change_date_type);
		TextView tvName = ViewHolder.get(convertView, R.id.adapter_client_info_change_name);
		TextView tvContent = ViewHolder.get(convertView, R.id.adapter_client_info_change_content);
		
		PrpChangeFollowBean clientInfoChange = clientInfoChanges.get(position);
		
		tvDateAndType.setText(clientInfoChange.getDate() + "-" + clientInfoChange.getType());
		tvName.setText(clientInfoChange.getName());
		tvContent.setText(clientInfoChange.getContent());
		return convertView;
	}

}
