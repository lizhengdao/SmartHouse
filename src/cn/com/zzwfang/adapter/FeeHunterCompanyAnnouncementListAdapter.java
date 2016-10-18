package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.CompanyAnnouncementBean;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author lzd
 * 公司公告列表Adapter
 *
 */
public class FeeHunterCompanyAnnouncementListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<CompanyAnnouncementBean> companyAnnouncements;
    
    public FeeHunterCompanyAnnouncementListAdapter(Context context, ArrayList<CompanyAnnouncementBean> companyAnnouncements) {
        this.context = context;
        this.companyAnnouncements = companyAnnouncements;
    }
    @Override
    public int getCount() {
        if (companyAnnouncements == null) {
            return 0;
        }
        return companyAnnouncements.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return companyAnnouncements.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.adapter_fee_hunter_company_announcement, null);
        }
        
        TextView tvTitle = ViewHolder.get(convertView, R.id.adapter_fee_hunter_company_announcement_title);
        TextView tvContent = ViewHolder.get(convertView, R.id.adapter_fee_hunter_company_announcement_content);
        TextView tvTime = ViewHolder.get(convertView, R.id.adapter_fee_hunter_company_announcement_time);
        
        CompanyAnnouncementBean companyAnnouncementBean = companyAnnouncements.get(position);
        
        tvTitle.setText(companyAnnouncementBean.getTitle());
        tvContent.setText(companyAnnouncementBean.getContent());
        tvTime.setText(companyAnnouncementBean.getAddTime());
        
        return convertView;
    }
    

}
