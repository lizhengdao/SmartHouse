package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import cn.com.zzwfang.R;
import cn.com.zzwfang.action.ImageAction;
import cn.com.zzwfang.bean.MyHouseBean;
import cn.com.zzwfang.view.AutoDrawableTextView;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 我的 -> 我的房源  Adapter
 * @author doer06
 *
 */
public class MyHouseAdapter extends BaseAdapter {

	
	private Context context;
	
	private ArrayList<MyHouseBean> myHouses;
	
	public MyHouseAdapter(Context context, ArrayList<MyHouseBean> myHouses) {
		this.context = context;
		this.myHouses = myHouses;
	}
	 
	@Override
	public int getCount() {
		if (myHouses == null) {
			return 0;
		}
		return myHouses.size();
	}

	@Override
	public Object getItem(int position) {
		return myHouses.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			
			convertView = View.inflate(context, R.layout.adapter_my_house, null);
		}
		MyHouseBean myHouseBean = myHouses.get(position);
		
		ImageView img = (ImageView) convertView.findViewById(R.id.adapter_my_house_img);
		TextView tvTitle = (TextView) convertView.findViewById(R.id.adapter_my_house_title);
		TextView tvDesc = (TextView) convertView.findViewById(R.id.adapter_my_house_desc);
		TextView tvDate = (TextView) convertView.findViewById(R.id.adapter_my_house_date);
		TextView tvPrice = (TextView) convertView.findViewById(R.id.adapter_my_house_price);
		AutoDrawableTextView infoChanges = (AutoDrawableTextView) convertView.findViewById(R.id.adapter_my_house_source_info_change);
		
		tvTitle.setText(myHouseBean.getTitle());
		String desc = "";
		if (!TextUtils.isEmpty(myHouseBean.getTypeF())) {
		    desc += myHouseBean.getTypeF() + "室";
		}
		if (!TextUtils.isEmpty(myHouseBean.getTypeT())) {
		    desc += myHouseBean.getTypeT() + "厅";
		}
		if (!TextUtils.isEmpty(myHouseBean.getTypeW())) {
		    desc += myHouseBean.getTypeW() + "卫";
		}
		if (!TextUtils.isEmpty(myHouseBean.getTypeY())) {
		    desc += myHouseBean.getTypeY() + "阳台  ";
		}
		if (!TextUtils.isEmpty(myHouseBean.getSquare())) {
		    desc += myHouseBean.getSquare() + "㎡   ";
		}
		if (!TextUtils.isEmpty(myHouseBean.getDirection())) {
		    desc += myHouseBean.getDirection();
		}
		
		tvDesc.setText(desc);
		tvDate.setText("接盘时间：" + myHouseBean.getPublishDate());
		tvPrice.setText(myHouseBean.getPrice() + "万");
		
		String url = myHouseBean.getImagePath();
	    if (!TextUtils.isEmpty(url)) {
	        ImageAction.displayImage(url, img);
	    }
	    infoChanges.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                
            }
        });
		return convertView;
	}

}
