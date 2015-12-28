package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import cn.com.zzwfang.R;
import cn.com.zzwfang.action.ImageAction;
import cn.com.zzwfang.bean.MyProxySellHouseBean;
import cn.com.zzwfang.view.AutoDrawableTextView;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class IAmOwnerAdapter extends BaseAdapter {

    
    private Context context;
    private ArrayList<MyProxySellHouseBean> mySoldHouses;
    
    public IAmOwnerAdapter(Context context, ArrayList<MyProxySellHouseBean> mySoldHouses) {
        this.context = context;
        this.mySoldHouses = mySoldHouses;
    }
    
    @Override
    public int getCount() {
        if (mySoldHouses == null) {
            return 0;
        }
        return mySoldHouses.size();
    }

    @Override
    public Object getItem(int position) {
        return mySoldHouses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.adapter_i_am_owner, null);
        }
        
        MyProxySellHouseBean temp = mySoldHouses.get(position);
        
        ImageView photo = ViewHolder.get(convertView, R.id.adapter_i_am_owner_photo);
        TextView tvTitle = ViewHolder.get(convertView, R.id.adapter_i_am_owner_title);
        TextView tvDesc = ViewHolder.get(convertView, R.id.adapter_i_am_owner_desc);
        TextView tvPrice = ViewHolder.get(convertView, R.id.adapter_i_am_owner_money);
        
//        AutoDrawableTextView houseProgress = ViewHolder.get(convertView, R.id.adapter_my_house_source_progress);
//        AutoDrawableTextView seeHouseRecord = ViewHolder.get(convertView, R.id.adapter_my_house_source_see_house_record);
        
        tvTitle.setText(temp.getTitle());
        String desc = "";
        if (!TextUtils.isEmpty(temp.getTypeF())) {
            desc += temp.getTypeF() + "室";
        }
        if (!TextUtils.isEmpty(temp.getTypeT())) {
            desc += temp.getTypeT() + "厅";
        }
        if (!TextUtils.isEmpty(temp.getTypeW())) {
            desc += temp.getTypeW() + "卫";
        }
        if (!TextUtils.isEmpty(temp.getTypeY())) {
            desc += temp.getTypeY() + "阳台  ";
        }
        if (!TextUtils.isEmpty(temp.getSquare())) {
            desc += temp.getSquare() + "㎡   ";
        }
        if (!TextUtils.isEmpty(temp.getDirection())) {
            desc += temp.getDirection();
        }
        
        tvDesc.setText(desc);
        if (!TextUtils.isEmpty(temp.getPrice())) {
            tvPrice.setText(temp.getPrice() + "万");
        }
        
        ImageAction.displayImage(temp.getImagePath(), photo);
        return convertView;
    }

}
