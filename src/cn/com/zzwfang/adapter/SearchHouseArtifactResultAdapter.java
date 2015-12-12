package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import cn.com.zzwfang.R;
import cn.com.zzwfang.action.ImageAction;
import cn.com.zzwfang.activity.BaseActivity;
import cn.com.zzwfang.activity.ChatActivity;
import cn.com.zzwfang.bean.SearchHouseArtifactResultBean;
import cn.com.zzwfang.util.Jumper;
import cn.com.zzwfang.view.AutoDrawableTextView;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchHouseArtifactResultAdapter extends BaseAdapter{

	private Context context;
	private ArrayList<SearchHouseArtifactResultBean> artifactResults;
	
	public SearchHouseArtifactResultAdapter(Context context, ArrayList<SearchHouseArtifactResultBean> artifactResult) {
		this.context = context;
		this.artifactResults = artifactResult;
	}
	
	@Override
	public int getCount() {
		if (artifactResults == null) {
			return 0;
		}
		return artifactResults.size();
	}

	@Override
	public Object getItem(int position) {
		return artifactResults.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.adapter_search_house_artifact_result, null);
		}
		
		ImageView imgAvatar = (ImageView) convertView.findViewById(R.id.adapter_search_house_artifact_avatar);
		TextView tvName = (TextView) convertView.findViewById(R.id.adapter_search_house_artifact_name);
		TextView tvPhone = (TextView) convertView.findViewById(R.id.adapter_search_house_artifact_phone);
		TextView tvSecondHandHouse = (TextView) convertView.findViewById(R.id.adapter_search_house_artifact_second_hand_houses);
		TextView tvRentHouse = (TextView) convertView.findViewById(R.id.adapter_search_house_artifact_rent_houses);
		
		// 浏览量
		TextView tvAccessQuantity = (TextView) convertView.findViewById(R.id.adapter_search_house_artifact_access_quantity);
		AutoDrawableTextView tvConsult = (AutoDrawableTextView) convertView.findViewById(R.id.adapter_search_house_artifact_consult);
		AutoDrawableTextView tvDial = (AutoDrawableTextView) convertView.findViewById(R.id.adapter_search_house_artifact_dial);
		
		final SearchHouseArtifactResultBean artifactResult = artifactResults.get(position);
		String url = artifactResult.getPhoto();
		if (!TextUtils.isEmpty(url) && url.startsWith("http")) {
			ImageAction.displayImage(artifactResult.getPhoto(), imgAvatar);
		}
		tvName.setText(artifactResult.getName());
		tvPhone.setText(artifactResult.getPhone());
		tvSecondHandHouse.setText(artifactResult.getSecondNum());
		tvRentHouse.setText(artifactResult.getRentNum());
		tvAccessQuantity.setText(artifactResult.getPairCount() + "");
		
		
		tvDial.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + artifactResult.getPhone()));
				context.startActivity(intent);
			}
		});
		tvConsult.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                
                Jumper.newJumper()
                .setAheadInAnimation(R.anim.activity_push_in_right)
                .setAheadOutAnimation(R.anim.activity_alpha_out)
                .setBackInAnimation(R.anim.activity_alpha_in)
                .setBackOutAnimation(R.anim.activity_push_out_right)
                .putString(ChatActivity.INTENT_MESSAGE_TO_ID, artifactResult.getId())
                .putString(ChatActivity.INTENT_MESSAGE_TO_NAME, artifactResult.getName())
                .jump((BaseActivity)context, ChatActivity.class);
            }
        });
		return convertView;
	}


}
