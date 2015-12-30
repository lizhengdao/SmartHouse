package cn.com.zzwfang.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.util.ToastUtils;

public class SupportActivity extends BaseActivity implements OnClickListener {

    private TextView tvBack;
    private EditText edtSupportContent;
    private TextView tvCommit;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        
        initView();
    }
    
    private void initView() {
        setContentView(R.layout.act_support);
        
        tvBack = (TextView) findViewById(R.id.act_support_back);
        edtSupportContent = (EditText) findViewById(R.id.act_support_content_edt);
        tvCommit = (TextView) findViewById(R.id.act_support_commit);
        
        tvBack.setOnClickListener(this);
        tvCommit.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_support_back:
			finish();
			break;
		case R.id.act_support_commit:
			commitSupport();
			break;
		}
	}
	
	private void commitSupport() {
		
		String content = edtSupportContent.getText().toString();
		if (TextUtils.isEmpty(content)) {
			ToastUtils.SHORT.toast(this, "请输入赞内容");
			return;
		}
		
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		String id = null;
		int supportType = 1;
		String userId = ContentUtils.getUserId(this);
		// supportType  1(财务)，2（带看），3（进度）
		actionImpl.support(id, supportType, userId, content, new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
				
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				ToastUtils.SHORT.toast(SupportActivity.this, "赞成功");
			}
		});
	}
}
