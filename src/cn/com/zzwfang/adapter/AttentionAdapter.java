package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.action.ImageAction;
import cn.com.zzwfang.activity.BaseActivity;
import cn.com.zzwfang.activity.ChatActivity;
import cn.com.zzwfang.bean.AttentionBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.util.Jumper;

/**
 * 我的关注列表Adapter
 * @author lzd
 *
 */
public class AttentionAdapter extends BaseAdapter {

	private Context context;
	
	private ArrayList<AttentionBean> attentions;
	
	private OnConcernAllDeletedListener onConcernAllDeletedListener;
	
	public AttentionAdapter(Context context,
			OnConcernAllDeletedListener onConcernAllDeletedListener) {
		this.context = context;
		this.attentions = new ArrayList<AttentionBean>();
		this.onConcernAllDeletedListener = onConcernAllDeletedListener;
	}
	
	public void refreshData(ArrayList<AttentionBean> data) {
		if (attentions != null) {
			attentions.clear();
			attentions.addAll(data);
			notifyDataSetChanged();
		}
	}
	
	public AttentionAdapter(Context context, ArrayList<AttentionBean> attentions,
			OnConcernAllDeletedListener onConcernAllDeletedListener) {
		this.context = context;
		this.attentions = attentions;
		this.onConcernAllDeletedListener = onConcernAllDeletedListener;
	}
	
	@Override
	public int getCount() {
		if (attentions == null) {
			return 0;
		}
		return attentions.size();
	}

	@Override
	public Object getItem(int position) {
		return attentions.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.adapter_my_attention, null);
		}
		
		ImageView img = ViewHolder.get(convertView, R.id.adapter_my_attention_img);
		TextView tvTitle = ViewHolder.get(convertView, R.id.adapter_my_attention_title);
		TextView tvDesc = ViewHolder.get(convertView, R.id.adapter_my_attention_desc);
		TextView tvEstateName = ViewHolder.get(convertView, R.id.adapter_my_attention_estate_name);
		TextView tvPrice = ViewHolder.get(convertView, R.id.adapter_my_attention_price);
		TextView tvOnLineConsult = ViewHolder.get(convertView, R.id.adapter_my_attention_online_consult);
		TextView tvCancelCollection = ViewHolder.get(convertView, R.id.adapter_my_attention_cancel_collection);
		
		final AttentionBean attentionBean = attentions.get(position);
		if (!TextUtils.isEmpty(attentionBean.getTitel())) {
			tvTitle.setText(attentionBean.getTitel());
		}
		if (!TextUtils.isEmpty(attentionBean.getEstName())) {
			tvEstateName.setText(attentionBean.getEstName());
		}
		if (!TextUtils.isEmpty(attentionBean.getPrice())) {
			tvPrice.setText(attentionBean.getPrice() + "万");
		}
		
		String desc = attentionBean.getTypeF() + "室" + attentionBean.getTypeT() + "厅    "
		+ attentionBean.getSquare() + "㎡   " + attentionBean.getDiretion();
		tvDesc.setText(desc);
		
		ImageAction.displayImage(attentionBean.getPhoto(), img);
		tvCancelCollection.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				deleteCollection(attentionBean, position);
			}
		});
		
		tvOnLineConsult.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                
                Jumper.newJumper()
                .setAheadInAnimation(R.anim.activity_push_in_right)
                .setAheadOutAnimation(R.anim.activity_alpha_out)
                .setBackInAnimation(R.anim.activity_alpha_in)
                .setBackOutAnimation(R.anim.activity_push_out_right)
                .putString(ChatActivity.INTENT_MESSAGE_TO_ID, attentionBean.getAgentId())
//                .putString(ChatActivity.INTENT_MESSAGE_TO_NAME, "经纪人")
                .jump((BaseActivity)context, ChatActivity.class);
            }
        });
		
		return convertView;
	}
	
	private void deleteCollection(AttentionBean attentionBean, final int position) {
		ActionImpl actionImpl = ActionImpl.newInstance(context);
		String userId = ContentUtils.getUserId(context);
		
		actionImpl.deleteCollection(userId, attentionBean.getPropertyId(), new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				attentions.remove(position);
				notifyDataSetChanged();
				if (attentions == null || attentions.size() == 0) {
					if (onConcernAllDeletedListener != null) {
						onConcernAllDeletedListener.onConcernAllDeleted();
					}
				}
			}
		});
	}
	
	public interface OnConcernAllDeletedListener {
		void onConcernAllDeleted();
	}

}
