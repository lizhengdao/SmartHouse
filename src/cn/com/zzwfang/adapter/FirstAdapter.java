package cn.com.zzwfang.adapter;

import cn.com.zzwfang.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.adapter_first_lst, null);
		}
		ImageView img = (ImageView) convertView.findViewById(R.id.adapter_first_lst_img);
		TextView tvDescOne = (TextView) convertView.findViewById(R.id.adapter_first_desc_one);
		TextView tvDescTwo = (TextView) convertView.findViewById(R.id.adapter_first_desc_two);
		TextView tvDescThree = (TextView) convertView.findViewById(R.id.adapter_first_desc_three);
		TextView tvDescFour = (TextView) convertView.findViewById(R.id.adapter_first_desc_four);
		if (position == 3) {
			tvDescThree.setText(descOne[position]);
			tvDescFour.setText(descTwo[position]);
			
			tvDescThree.setVisibility(View.VISIBLE);
			tvDescFour.setVisibility(View.VISIBLE);
			tvDescOne.setVisibility(View.GONE);
			tvDescTwo.setVisibility(View.GONE);
		} else {
			tvDescOne.setText(descOne[position]);
			tvDescTwo.setText(descTwo[position]);
			tvDescOne.setVisibility(View.VISIBLE);
			tvDescTwo.setVisibility(View.VISIBLE);
			tvDescThree.setVisibility(View.GONE);
			tvDescFour.setVisibility(View.GONE);
		}
		img.setImageResource(pics[position]);
		return convertView;
	}

}
