package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.ClientInfoChangeBean;
import cn.com.zzwfang.bean.PrpChangeFollowBean;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class QueryAfterSaleAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<PrpChangeFollowBean> houseSourceInfoChanges;
	
	public QueryAfterSaleAdapter(Context context, ArrayList<PrpChangeFollowBean> houseSourceInfoChanges) {
		this.context = context;
		this.houseSourceInfoChanges = houseSourceInfoChanges;
	}
	
	@Override
	public int getCount() {
		if (houseSourceInfoChanges == null) {
			return 0;
		} else {
			return houseSourceInfoChanges.size();
		}
		
	}

	@Override
	public Object getItem(int position) {
		return houseSourceInfoChanges.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.adapter_query_after_sale, null);
		}
		
		TextView tvName = ViewHolder.get(convertView, R.id.adapter_query_after_sale_name);
		TextView tvOperatorName = ViewHolder.get(convertView, R.id.adapter_query_after_sale_operator);
		TextView tvContent = ViewHolder.get(convertView, R.id.adapter_query_after_sale_content);
		TextView tvDate = ViewHolder.get(convertView, R.id.adapter_query_after_sale_date);
		
		PrpChangeFollowBean temp = houseSourceInfoChanges.get(position);
		tvName.setText(temp.getType());
		tvOperatorName.setText("操作员:" + temp.getName());
		tvContent.setText(temp.getContent());
		tvDate.setText(temp.getDate());
		return convertView;
	}

}
