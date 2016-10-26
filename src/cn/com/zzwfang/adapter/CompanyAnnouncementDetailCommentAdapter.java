package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.CompanyAnnouncementCommentBean;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CompanyAnnouncementDetailCommentAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<CompanyAnnouncementCommentBean> comments;
    
    public CompanyAnnouncementDetailCommentAdapter(Context context, ArrayList<CompanyAnnouncementCommentBean> comments) {
        this.context = context;
        this.comments = comments;
    }
    
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (comments == null) {
            return 0;
        }
        return comments.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return comments.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int arg0, View convertView, ViewGroup arg2) {
        // TODO Auto-generated method stub
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.adapter_company_announcement_detail_comment, null);
        }
        
        TextView tvName = ViewHolder.get(convertView, R.id.adapter_company_announcement_detail_comment_name);
        TextView tvTime = ViewHolder.get(convertView, R.id.adapter_company_announcement_detail_comment_time);
        TextView tvContent = ViewHolder.get(convertView, R.id.adapter_company_announcement_detail_comment_content);
        
        CompanyAnnouncementCommentBean companyAnnouncementCommentBean = comments.get(arg0);
        tvName.setText(companyAnnouncementCommentBean.getCommenter().getName());
        tvTime.setText(companyAnnouncementCommentBean.getAddTime());
        tvContent.setText(companyAnnouncementCommentBean.getContent());
        
        return convertView;
    }

}
