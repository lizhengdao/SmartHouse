package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import cn.com.zzwfang.bean.AttentionBean;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class AttentionAdapter extends BaseAdapter {

	private Context context;
	
	private ArrayList<AttentionBean> attentions;
	
	public AttentionAdapter(Context context, ArrayList<AttentionBean> attentions) {
		this.context = context;
		this.attentions = attentions;
	}
	
	@Override
	public int getCount() {
		if (attentions == null) {
			return 0;
		}
		return attentions.size();
	}

	@Override
	public Object getItem(int position) {
		return attentions.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			
		}
		return convertView;
	}

}
