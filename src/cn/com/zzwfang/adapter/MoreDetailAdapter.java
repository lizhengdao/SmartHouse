package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.TextValueBean;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class MoreDetailAdapter extends BaseAdapter {

	private Context context;
	
	private ArrayList<TextValueBean> moreDetails;
	
	public MoreDetailAdapter(Context context, ArrayList<TextValueBean> moreDetails) {
		this.context = context;
		this.moreDetails = moreDetails;
	}
	
	@Override
	public int getCount() {
		if (moreDetails == null) {
			return 0;
		}
		return moreDetails.size();
	}

	@Override
	public Object getItem(int position) {
		return moreDetails.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.adapter_more_detail, null);
		}
		
		TextView tvMoreDetail = ViewHolder.get(convertView, R.id.adapter_more_detail_txt);
		CheckBox cbx = ViewHolder.get(convertView, R.id.adapter_more_detail_cbx);
		
		TextValueBean tvb = moreDetails.get(position);
		tvMoreDetail.setText(tvb.getText());
		cbx.setChecked(tvb.isSelected());
		
		return convertView;
	}

}
