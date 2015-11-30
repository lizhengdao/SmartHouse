package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.TextValueBean;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class SearchHouseArtifactRequirementAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<TextValueBean> additionalInfo;
    
    public SearchHouseArtifactRequirementAdapter(Context context, ArrayList<TextValueBean> additionalInfo) {
        this.context = context;
        this.additionalInfo = additionalInfo;
    }
    @Override
    public int getCount() {
        if (additionalInfo == null) {
            return 0;
        }
        return additionalInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return additionalInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
        	convertView = View.inflate(context, R.layout.adapter_search_house_artifact_requirement, null);
        }
        CheckBox cbx = (CheckBox) convertView.findViewById(R.id.adapter_search_house_artifact_requirement_cbx);
        final TextValueBean textValueBean = additionalInfo.get(position);
        cbx.setChecked(textValueBean.isSelected());
        cbx.setText(textValueBean.getText());
        cbx.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				textValueBean.setSelected(isChecked);
				notifyDataSetChanged();
			}
		});
        return convertView;
    }

}
