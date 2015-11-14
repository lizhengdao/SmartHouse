package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.action.ImageAction;
import cn.com.zzwfang.bean.AttentionBean;

/**
 * 我的关注列表Adapter
 * @author lzd
 *
 */
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
			convertView = View.inflate(context, R.layout.adapter_my_attention, null);
		}
		
		ImageView img = (ImageView) convertView.findViewById(R.id.adapter_my_attention_img);
		TextView tvTitle = (TextView) convertView.findViewById(R.id.adapter_my_attention_title);
		TextView tvDesc = (TextView) convertView.findViewById(R.id.adapter_my_attention_desc);
		TextView tvEstateName = (TextView) convertView.findViewById(R.id.adapter_my_attention_estate_name);
		TextView tvPrice = (TextView) convertView.findViewById(R.id.adapter_my_attention_price);
		TextView tvOnLineConsult = (TextView) convertView.findViewById(R.id.adapter_my_attention_online_consult);
		TextView tvCollection = (TextView) convertView.findViewById(R.id.adapter_my_attention_collection);
		
		AttentionBean attentionBean = attentions.get(position);
		tvTitle.setText(attentionBean.getTitel());
		tvEstateName.setText(attentionBean.getEstName());
		tvPrice.setText(attentionBean.getPrice() + "万");
		
		String desc = attentionBean.getTypeF() + "室" + attentionBean.getTypeT() + "厅    "
		+ attentionBean.getSquare() + "㎡   " + attentionBean.getDiretion();
		tvDesc.setText(desc);
		
		ImageAction.displayImage(attentionBean.getPhoto(), img);
		
		return convertView;
	}

}
