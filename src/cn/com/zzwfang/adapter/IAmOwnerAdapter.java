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
        ImageView statusImg = ViewHolder.get(convertView, R.id.adapter_i_am_owner_status);
        TextView tvTitle = ViewHolder.get(convertView, R.id.adapter_i_am_owner_title);
        TextView tvDesc = ViewHolder.get(convertView, R.id.adapter_i_am_owner_desc);
        TextView tvPrice = ViewHolder.get(convertView, R.id.adapter_i_am_owner_money);
        
        AutoDrawableTextView action1 = ViewHolder.get(convertView, R.id.adapter_i_am_owner_action1);
        AutoDrawableTextView action2 = ViewHolder.get(convertView, R.id.adapter_i_am_owner_action2);
        
        int status = temp.getStatus();
        if (status == 0) { // 已售
            statusImg.setBackgroundResource(R.drawable.ic_saled);
            action1.setVisibility(View.VISIBLE);
            action2.setVisibility(View.VISIBLE);
            action1.setText("售后查询");
            action2.setText("财务明细");
        } else if (status == 1) { // 未售
            action1.setVisibility(View.GONE);
            action2.setVisibility(View.VISIBLE);
            action2.setText("带看记录");
            statusImg.setBackgroundResource(R.drawable.ic_unsaled);
        }
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
