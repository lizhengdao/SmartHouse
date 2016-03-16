package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.IdNameFloorBean;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EstateRoomAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<IdNameFloorBean> cells;
    
    public EstateRoomAdapter(Context context, ArrayList<IdNameFloorBean> cells) {
        this.context = context;
        this.cells = cells;
    }
    
    @Override
    public int getCount() {
        if (cells == null) {
            return 0;
        }
        return cells.size();
    }

    @Override
    public Object getItem(int position) {
        return cells.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.adapter_condition_item, null);
        }
        
        TextView tvConditionName = ViewHolder.get(convertView, R.id.adapter_conditon_name_tv);
        IdNameFloorBean temp = cells.get(position);
//        String tempStr = temp.getFloor() + "层  " + temp.getName() + "室";
        String tempStr = temp.getName() + "室";
        tvConditionName.setText(tempStr);
        return convertView;
    }

}
