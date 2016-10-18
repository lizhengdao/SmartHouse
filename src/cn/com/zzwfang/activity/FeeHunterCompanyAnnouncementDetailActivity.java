package cn.com.zzwfang.activity;

import cn.com.zzwfang.R;
import cn.com.zzwfang.pullview.AbPullToRefreshView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author lzd
 * 公司公告列表详情
 *
 */
public class FeeHunterCompanyAnnouncementDetailActivity extends BaseActivity implements OnClickListener {

    private TextView tvBack;
    private AbPullToRefreshView pullView;
    private ListView lstCompanyAnnouncement;
    
    private int pageIndex = 1;
    private int pageTotal = 0;
    
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        
        setContentView(R.layout.act_fee_hunter_company_announcement_detail);
        
        tvBack = (TextView) findViewById(R.id.act_fee_hunter_company_announcement_detail_back);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.act_fee_hunter_company_announcement_detail_back:
            finish();
            break;
        }
    }
}
