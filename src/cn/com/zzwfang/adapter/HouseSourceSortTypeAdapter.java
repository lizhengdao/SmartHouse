package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.FieldNameValueBean;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HouseSourceSortTypeAdapter extends BaseAdapter {
	
	private Context context;
//  排序参数
	private ArrayList<FieldNameValueBean> sortParamList;
	
	public HouseSourceSortTypeAdapter(Context context, ArrayList<FieldNameValueBean> data) {
		this.context = context;
		this.sortParamList = data;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (sortParamList == null) {
			return 0;
		}
		return sortParamList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return sortParamList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.adapter_house_source_sort_type_item, null);
		}
		
		TextView tvSortType = (TextView) convertView.findViewById(R.id.tv_adapter_house_source_sort_type);
		FieldNameValueBean fieldNameValueBean = sortParamList.get(position);
		tvSortType.setText(fieldNameValueBean.getName());
		
		if (fieldNameValueBean.isSelected()) {
			tvSortType.setTextColor(context.getResources().getColor(R.color.color_frag_main_mine_status_bar));
		} else {
			tvSortType.setTextColor(context.getResources().getColor(R.color.color_333333));
		}
		
		return convertView;
	}

}
