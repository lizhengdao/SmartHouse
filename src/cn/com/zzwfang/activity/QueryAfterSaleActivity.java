package cn.com.zzwfang.activity;

import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.view.PathImage;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class QueryAfterSaleActivity extends BaseActivity implements OnClickListener {

    private TextView tvBack;
    private TextView tvTitle;
    private PathImage avatar1, avatar2, avatar3;
    private TextView tvName1, tvName2, tvName3;
    private TextView tvComplain1, tvComplain2, tvComplain3;
    private TextView tvSupport1, tvSupport2, tvSupport3;
    private ListView listView;
    
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        initView();
    }
    
    private void initView() {
        
        setContentView(R.layout.act_query_after_sale);
        tvBack = (TextView) findViewById(R.id.act_query_after_sale_back);
        tvTitle = (TextView) findViewById(R.id.act_query_after_sale_title);
        
        avatar1 = (PathImage) findViewById(R.id.act_query_after_sale_avatar1);
        avatar2 = (PathImage) findViewById(R.id.act_query_after_sale_avatar2);
        avatar3 = (PathImage) findViewById(R.id.act_query_after_sale_avatar3);
        
        tvName1 = (TextView) findViewById(R.id.act_query_after_sale_name1);
        tvName2 = (TextView) findViewById(R.id.act_query_after_sale_name2);
        tvName3 = (TextView) findViewById(R.id.act_query_after_sale_name3);
        
        tvComplain1 = (TextView) findViewById(R.id.act_query_after_sale_complain1);
        tvComplain2 = (TextView) findViewById(R.id.act_query_after_sale_complain2);
        tvComplain3 = (TextView) findViewById(R.id.act_query_after_sale_complain3);
        
        tvSupport1 = (TextView) findViewById(R.id.act_query_after_sale_support1);
        tvSupport2 = (TextView) findViewById(R.id.act_query_after_sale_support2);
        tvSupport3 = (TextView) findViewById(R.id.act_query_after_sale_support3);
        
        tvBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.act_query_after_sale_back:
            finish();
            break;
        }
    }
    
    private void seeHouseExperience() {
        ActionImpl actionImpl = ActionImpl.newInstance(this);
        String userId = ContentUtils.getUserId(this);
        actionImpl.seeHouseExperience(userId, new ResultHandlerCallback() {
            
            @Override
            public void rc999(RequestEntity entity, Result result) {
                
            }
            
            @Override
            public void rc3001(RequestEntity entity, Result result) {
                
            }
            
            @Override
            public void rc0(RequestEntity entity, Result result) {
                
            }
        });
    }
}
