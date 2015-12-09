package cn.com.zzwfang.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.util.DevUtils;

public class FirstAdapter extends BaseAdapter {

	private int pics[] = {R.drawable.pic_first_one, R.drawable.pic_first_two, R.drawable.pic_first_three, R.drawable.pic_first_four};
	private int descOne[] = {R.string.page_one_desc_one, R.string.page_two_desc_one, R.string.page_three_desc_one,R.string.page_four_desc_one};
	private int descTwo[] = {R.string.page_one_desc_two, R.string.page_two_desc_two, R.string.page_three_desc_two,R.string.page_four_desc_two};
	
	private Context context;
	
	public FirstAdapter(Context context) {
		this.context = context;
	}
	
	@Override
	public int getCount() {
		return pics.length;
	}

	@Override
	public Object getItem(int position) {
		return pics[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    
	    ViewHoler viewHoler = null;
		if (convertView == null) {
		    viewHoler = new ViewHoler();
			convertView = View.inflate(context, R.layout.adapter_first_lst, null);
			
			viewHoler.lltContainer = (LinearLayout) convertView.findViewById(R.id.adapter_first_lst_container);
			viewHoler.img = (ImageView) convertView.findViewById(R.id.adapter_first_lst_img);
			viewHoler.tvDescOne = (TextView) convertView.findViewById(R.id.adapter_first_desc_one);
			viewHoler.tvDescTwo = (TextView) convertView.findViewById(R.id.adapter_first_desc_two);
			viewHoler.tvDescThree = (TextView) convertView.findViewById(R.id.adapter_first_desc_three);
			viewHoler.tvDescFour = (TextView) convertView.findViewById(R.id.adapter_first_desc_four);
			
			convertView.setTag(viewHoler);
		} else {
		    viewHoler = (ViewHoler) convertView.getTag();
		}
		
		LinearLayout.LayoutParams lp = (LayoutParams) viewHoler.lltContainer.getLayoutParams();
		lp.width = LayoutParams.MATCH_PARENT;
		lp.height = (int) (DevUtils.getScreenTools(context).getScreenHeight() * 1.2);
		
		if (position == 3) {
		    viewHoler.tvDescThree.setText(descOne[position]);
		    viewHoler.tvDescFour.setText(descTwo[position]);
			
		    viewHoler.tvDescThree.setVisibility(View.VISIBLE);
		    viewHoler.tvDescFour.setVisibility(View.VISIBLE);
		    viewHoler.tvDescOne.setVisibility(View.GONE);
		    viewHoler.tvDescTwo.setVisibility(View.GONE);
		} else {
		    viewHoler.tvDescOne.setText(descOne[position]);
		    viewHoler.tvDescTwo.setText(descTwo[position]);
		    viewHoler.tvDescOne.setVisibility(View.VISIBLE);
		    viewHoler.tvDescTwo.setVisibility(View.VISIBLE);
		    viewHoler.tvDescThree.setVisibility(View.GONE);
		    viewHoler.tvDescFour.setVisibility(View.GONE);
		}
		viewHoler.img.setImageResource(pics[position]);
		return convertView;
	}
	
	public static class ViewHoler {
	    LinearLayout lltContainer;
	    ImageView img;
	    TextView tvDescOne;
	    TextView tvDescTwo;
	    TextView tvDescThree;
	    TextView tvDescFour;
	}

}
