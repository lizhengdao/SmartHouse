package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import android.content.Context;
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
	
	public AttentionAdapter(Context context, ArrayList<AttentionBean> attentions) {
		this.context = context;
		this.attentions = attentions;
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
		
		ImageView img = (ImageView) convertView.findViewById(R.id.adapter_my_attention_img);
		TextView tvTitle = (TextView) convertView.findViewById(R.id.adapter_my_attention_title);
		TextView tvDesc = (TextView) convertView.findViewById(R.id.adapter_my_attention_desc);
		TextView tvEstateName = (TextView) convertView.findViewById(R.id.adapter_my_attention_estate_name);
		TextView tvPrice = (TextView) convertView.findViewById(R.id.adapter_my_attention_price);
		TextView tvOnLineConsult = (TextView) convertView.findViewById(R.id.adapter_my_attention_online_consult);
		TextView tvCancelCollection = (TextView) convertView.findViewById(R.id.adapter_my_attention_cancel_collection);
		
		final AttentionBean attentionBean = attentions.get(position);
		tvTitle.setText(attentionBean.getTitel());
		tvEstateName.setText(attentionBean.getEstName());
		tvPrice.setText(attentionBean.getPrice() + "万");
		
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
			}
		});
	}

}
