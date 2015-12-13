package cn.com.zzwfang.activity;

import java.util.regex.Pattern;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
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

public class FeedbackActivity extends BaseActivity implements OnClickListener {
	
	/**
	 * 投诉
	 */
	public static final String INTENT_COMPLAIN = "intent_complain";

    /**手机号码输入匹配正则*/
    public static final String REGX_MOBILE = "^1$|^1[34578]$|^1[3-8]\\d{0,9}$" ;
    /**手机号码提交验证正则*/
    public static final String REGX_MOBILE_FINAL = "^1[34578]\\d{9}$";
    
	private TextView tvBack, tvTitle, tvCommt;
	private EditText edtContent, edtPhone;
	private boolean isComplain = false;
	
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		isComplain = getIntent().getBooleanExtra(INTENT_COMPLAIN, false);
		setContentView(R.layout.act_feedback);
		
		initView();
	}
	
	private void initView() {
		
		tvBack = (TextView) findViewById(R.id.act_feedback_back);
		
		tvTitle = (TextView) findViewById(R.id.act_feedback_title);
		edtContent = (EditText) findViewById(R.id.act_feedback_content_edt);
		edtPhone = (EditText) findViewById(R.id.act_feedback_phone_edt);
		tvCommt = (TextView) findViewById(R.id.act_feedback_commit);
		
		if (isComplain) {
			tvTitle.setText("投诉");
		}
		
		edtPhone.setFilters(new InputFilter [] { new InputFilter(){

            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                       Spanned dest, int dstart, int dend) {
                 String content = dest.toString().substring(0, dstart)
                             + source
                             + dest.toString().substring(dend,
                                         dest.toString().length());
                  if (TextUtils.isEmpty(content))
                        return source;
                  if (content.matches(REGX_MOBILE))
                        return source;
                  else
                        return "" ;
           }
     }});
		
		tvBack.setOnClickListener(this);
		tvCommt.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_feedback_back:
			finish();
			break;
		case R.id.act_feedback_commit:
			commitFeedBck();
			break;
		}
	}
	
	private void commitFeedBck() {
		String content = edtContent.getText().toString();
		String phone = edtPhone.getText().toString();
		String userId = ContentUtils.getUserId(this);
		
		if (TextUtils.isEmpty(content)) {
			ToastUtils.SHORT.toast(FeedbackActivity.this, "请输入反馈内容");
			return;
		}
		
//		if (TextUtils.isEmpty(phone)) {
//			ToastUtils.SHORT.toast(FeedbackActivity.this, "请输入电话号码");
//			return;
//		}
		
		if (!TextUtils.isEmpty(phone)) {
		    if (!phone.matches(REGX_MOBILE_FINAL)) {
                ContentUtils. showMsg(this, "请输入正确的手机号" );
                 return;
            }
		}
		
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		actionImpl.commitFeedback(userId, content, phone, new ResultHandlerCallback() {
			
			@Override
			public void rc999(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc3001(RequestEntity entity, Result result) {
			}
			
			@Override
			public void rc0(RequestEntity entity, Result result) {
				ToastUtils.SHORT.toast(FeedbackActivity.this, "反馈成功");
			}
		});
	}
	
	//判断，返回布尔值  
	private boolean isPhoneNumber(String input){  
	    String regex="1([\\d]{10})|((\\+[0-9]{2,4})?\\(?[0-9]+\\)?-?)?[0-9]{7,8}";  
	    Pattern p = Pattern.compile(regex);
	    return p.matches(regex, input);
	}  
}
