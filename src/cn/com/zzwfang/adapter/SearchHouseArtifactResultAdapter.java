package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import cn.com.zzwfang.R;
import cn.com.zzwfang.action.ImageAction;
import cn.com.zzwfang.bean.SearchHouseArtifactResultBean;
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
		TextView tvAccessQuantity = (TextView) convertView.findViewById(R.id.adapter_search_house_artifact_access_quantity);
		TextView tvConsult = (TextView) convertView.findViewById(R.id.adapter_search_house_artifact_consult);
		TextView tvDial = (TextView) convertView.findViewById(R.id.adapter_search_house_artifact_dial);
		
		final SearchHouseArtifactResultBean artifactResult = artifactResults.get(position);
		String url = artifactResult.getPhoto();
		if (!TextUtils.isEmpty(url) && url.startsWith("http")) {
			ImageAction.displayImage(artifactResult.getPhoto(), imgAvatar);
		}
		tvName.setText(artifactResult.getName());
		tvPhone.setText(artifactResult.getPhone());
		tvAccessQuantity.setText(artifactResult.getPairCount() + "");
		tvDial.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + artifactResult.getPhone()));
				context.startActivity(intent);
			}
		});
		return convertView;
	}


}
