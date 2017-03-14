package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.ComplainLabelBean;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * @author lzd
 * 投诉标签 Adapter
 */
public class ComplainLabelAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<ComplainLabelBean> complainLabels;
	private ComplainLabelBean complainLabelBeanSelected;
	
	public ComplainLabelAdapter(Context context) {
		this.context = context;
		complainLabels = new ArrayList<ComplainLabelBean>();
	}
	
	public void refreshData(ArrayList<ComplainLabelBean> data) {
		complainLabels.clear();
		complainLabels.addAll(data);
		notifyDataSetChanged();
	}
	
	public ComplainLabelBean getSelectedComplainLabel() {
		return complainLabelBeanSelected;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (complainLabels == null) {
			return 0;
		}
		return complainLabels.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return complainLabels.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.adapter_complain_label_item, null);
		}
		
		CheckBox cbxComplainLabel = (CheckBox) convertView.findViewById(R.id.cbx_adapter_complain_label);
		
		
		ComplainLabelBean complainLabelBean = complainLabels.get(position);
		cbxComplainLabel.setText(complainLabelBean.getTitle());
		cbxComplainLabel.setChecked(complainLabelBean.isSelected());
		
		cbxComplainLabel.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					int size = complainLabels.size();
					for (int i = 0; i < size; i++) {
						ComplainLabelBean complainLabelBeanTemp = complainLabels.get(i);
						if (i == position) {
							complainLabelBeanTemp.setSelected(true);
							complainLabelBeanSelected = complainLabelBeanTemp;
						} else {
							complainLabelBeanTemp.setSelected(false);
						}
					}
					notifyDataSetChanged();
				}
			}
		});
		
		return convertView;
	}

}
