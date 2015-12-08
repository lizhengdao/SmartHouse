package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import cn.com.zzwfang.R;
import cn.com.zzwfang.action.ImageAction;
import cn.com.zzwfang.bean.AgentInfoItemBean;
import cn.com.zzwfang.util.DateUtils;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AgentInfoAdapter extends BaseAdapter {

	private Context context;
	
	private ArrayList<AgentInfoItemBean> listProperty;
	
	public AgentInfoAdapter(Context context, ArrayList<AgentInfoItemBean> listProperty) {
		this.context = context;
		this.listProperty = listProperty;
	}
	
	@Override
	public int getCount() {
		if (listProperty == null) {
			return 0;
		}
		return listProperty.size();
	}

	@Override
	public Object getItem(int position) {
		return listProperty.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.adapter_agent_info, null);
		}
		
		
		AgentInfoItemBean agentInfoItemBean = listProperty.get(position);
		
		ImageView imgPhoto = (ImageView) convertView.findViewById(R.id.adapter_agent_info_photo);
//		ImageAction.displayImage(agentInfoItemBean.getPhoto(), imgPhoto);
		TextView tvTitle  = (TextView) convertView.findViewById(R.id.adapter_agent_info_title);
		tvTitle.setText(agentInfoItemBean.getTitle());
		TextView tvDesc = (TextView) convertView.findViewById(R.id.adapter_agent_info_desc);
		String desc = "";
		if (!TextUtils.isEmpty(agentInfoItemBean.getTypeF())) {
		    desc += agentInfoItemBean.getTypeF() + "室";
		}
		if (!TextUtils.isEmpty(agentInfoItemBean.getTypeT())) {
		    desc += agentInfoItemBean.getTypeT() + "厅  ";
		}
		if (!TextUtils.isEmpty(agentInfoItemBean.getDiretion())) {
		    desc += agentInfoItemBean.getDiretion() + "  ";
		}
		if (!TextUtils.isEmpty(agentInfoItemBean.getFloor())) {
		    desc += agentInfoItemBean.getFloor();
		}
		if (!TextUtils.isEmpty(agentInfoItemBean.getTotalFloor())) {
		    desc += "/" + agentInfoItemBean.getTotalFloor() + "层";
		}
		tvDesc.setText(desc);
		
		TextView tvEstName = (TextView) convertView.findViewById(R.id.adapter_agent_info_est_name);
		tvEstName.setText(agentInfoItemBean.getEstName());
		
		TextView tvTotalPrice = (TextView) convertView.findViewById(R.id.adapter_agent_info_total_price);
		tvTotalPrice.setText(agentInfoItemBean.getPrice() + "万");
		
		TextView tvPublishTime = (TextView) convertView.findViewById(R.id.adapter_agent_info_publish_time);
//		String time = DateUtils.formatDate(agentInfoItemBean.getAddDate());
		tvPublishTime.setText(agentInfoItemBean.getAddDate());
		
		return convertView;
	}

}
