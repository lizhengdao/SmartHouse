package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import cn.com.zzwfang.bean.TextValueBean;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

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
            
        }
        
        return convertView;
    }

}
