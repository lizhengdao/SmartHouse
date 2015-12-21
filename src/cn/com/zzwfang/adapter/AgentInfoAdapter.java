package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import cn.com.zzwfang.R;
import cn.com.zzwfang.action.ImageAction;
import cn.com.zzwfang.bean.AgentInfoItemBean;
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
		
		ImageView imgPhoto = ViewHolder.get(convertView, R.id.adapter_agent_info_photo);
		TextView tvTitle  = ViewHolder.get(convertView, R.id.adapter_agent_info_title);
		TextView tvDesc = ViewHolder.get(convertView, R.id.adapter_agent_info_desc);
		TextView tvEstName = ViewHolder.get(convertView, R.id.adapter_agent_info_est_name);
		TextView tvTotalPrice = ViewHolder.get(convertView, R.id.adapter_agent_info_total_price);
		TextView tvPublishTime = ViewHolder.get(convertView, R.id.adapter_agent_info_publish_time);
		
		ImageAction.displayImage(agentInfoItemBean.getPhoto(), imgPhoto);
		if (!TextUtils.isEmpty(agentInfoItemBean.getTitle())) {
			tvTitle.setText(agentInfoItemBean.getTitle());
		}
		
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
		
		if (!TextUtils.isEmpty(agentInfoItemBean.getEstName())) {
			tvEstName.setText(agentInfoItemBean.getEstName());
		}
		if (!TextUtils.isEmpty(agentInfoItemBean.getPrice())) {
			tvTotalPrice.setText(agentInfoItemBean.getPrice() + "万");
		}
		
//		String time = DateUtils.formatDate(agentInfoItemBean.getAddDate());
		if (!TextUtils.isEmpty(agentInfoItemBean.getAddDate())) {
			tvPublishTime.setText(agentInfoItemBean.getAddDate());
		}
		
		return convertView;
	}

}
