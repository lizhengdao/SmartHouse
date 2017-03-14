package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.HouseSourceParamBean;
import cn.com.zzwfang.bean.NameValueBean;
import cn.com.zzwfang.view.helper.PopViewHelper.OnHouseSourceParamPickListener;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class HouseSourceParamMoreAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<HouseSourceParamBean> houseSourceParams;
	private OnHouseSourceParamPickListener listener;
	
	public HouseSourceParamMoreAdapter(Context context, ArrayList<HouseSourceParamBean> houseSourceParams, OnHouseSourceParamPickListener listener) {
		this.context = context;
		this.houseSourceParams = houseSourceParams;
		this.listener = listener;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (houseSourceParams == null) {
			return 0;
		}
				
		return houseSourceParams.size() - 3;
	}

	@Override
	public Object getItem(int position) {
		return position + 3;
	}

	@Override
	public long getItemId(int position) {
		return position + 3;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.adapter_house_source_param_more, null);
			 
		}
		
		final HouseSourceParamBean houseSourceParamBean = houseSourceParams.get(position + 3);
		
		TextView tvName = (TextView) convertView.findViewById(R.id.tv_adapter_house_source_param_name);
		GridView gridView = (GridView) convertView.findViewById(R.id.grid_adapter_house_source_param);
		
		tvName.setText(houseSourceParamBean.getName());
		final HouseSourceParamMoreItemAdapter adapter = new HouseSourceParamMoreItemAdapter(context, houseSourceParamBean.getValues());
		gridView.setAdapter(adapter);
		
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				NameValueBean nameValueBean = houseSourceParamBean.getValues().get(position);
				nameValueBean.setSelected(!nameValueBean.isSelected());
				if (nameValueBean.isSelected()) {
					ArrayList<NameValueBean> values = houseSourceParamBean.getValues();
					int size = values.size();
					for (int i = 0; i < size; i++) {
						if (i != position) {
							values.get(i).setSelected(false);
						}
					}
				}
				adapter.notifyDataSetChanged();
				if (nameValueBean.isSelected() && listener != null) {
					listener.onHouseSourceParamPick(4, houseSourceParamBean.getValues().get(position));
				}
			}
		});
		
		return convertView;
	}

}
