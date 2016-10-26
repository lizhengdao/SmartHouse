package cn.com.zzwfang.activity;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;
import com.tencent.mm.sdk.platformtools.Log;

import cn.com.zzwfang.R;
import cn.com.zzwfang.adapter.CompanyAnnouncementDetailCommentAdapter;
import cn.com.zzwfang.bean.CompanyAnnouncementBean;
import cn.com.zzwfang.bean.CompanyAnnouncementCommentBean;
import cn.com.zzwfang.bean.CompanyAnnouncementDetailBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.bean.UserInfoBean;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.pullview.AbPullToRefreshView;
import cn.com.zzwfang.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.util.ToastUtils;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

/**
 * @author lzd
 * 公司公告列表详情
 *
 */
public class FeeHunterCompanyAnnouncementDetailActivity extends BaseActivity implements OnClickListener,
    OnHeaderRefreshListener {  // OnFooterLoadListener

    public static String INTENT_Company_Announcement = "cn.com.zzwfang.activity.FeeHunterCompanyAnnouncementDetailActivity";
    private TextView tvBack;
    private AbPullToRefreshView pullView;
    private ListView lstCompanyAnnouncement;
    
    private LinearLayout lltHeaderDetail;
    private TextView tvHeaderTitle, tvHeaderTime, tvHeaderCommentsNum;
    private WebView webView;
    private EditText edtComment;
    private TextView tvCommentNum;
    
    private CompanyAnnouncementDetailCommentAdapter adapter;
    private CompanyAnnouncementDetailBean companyAnnouncementDetailBean;
    
//    private int pageIndex = 1;
//    private int pageTotal = 0;
    
    private CompanyAnnouncementBean companyAnnouncementBean;
    private ArrayList<CompanyAnnouncementCommentBean> comments = new ArrayList<CompanyAnnouncementCommentBean>();
    
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        
        companyAnnouncementBean = (CompanyAnnouncementBean) getIntent().getSerializableExtra(INTENT_Company_Announcement);
        
        setContentView(R.layout.act_fee_hunter_company_announcement_detail);
        
        tvBack = (TextView) findViewById(R.id.act_fee_hunter_company_announcement_detail_back);
        edtComment = (EditText) findViewById(R.id.act_fee_hunter_company_announcement_detail_comment_edt);
        tvCommentNum = (TextView) findViewById(R.id.act_fee_hunter_company_announcement_detail_comment_num);
        
        pullView = (AbPullToRefreshView) findViewById(R.id.pull_act_fee_hunter_company_announcement_detail);
        lstCompanyAnnouncement = (ListView) findViewById(R.id.lst_act_fee_hunter_company_announcement_detail_comments);
        
        
        lltHeaderDetail = (LinearLayout) View.inflate(this, R.layout.header_company_announcement_detail, null);
        tvHeaderTitle = (TextView) lltHeaderDetail.findViewById(R.id.header_company_announcement_detail_title);
        tvHeaderTime = (TextView) lltHeaderDetail.findViewById(R.id.header_company_announcement_detail_time);
        webView = (WebView) lltHeaderDetail.findViewById(R.id.header_company_announcement_detail_content);
        tvHeaderCommentsNum = (TextView) lltHeaderDetail.findViewById(R.id.header_company_announcement_detail_comments_num);
        
        pullView.setPullRefreshEnable(true);
        pullView.setLoadMoreEnable(false);
        pullView.setOnHeaderRefreshListener(this);
//        pullView.setOnFooterLoadListener(this);
        
        WebSettings ws = webView.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setDefaultTextEncodingName("UTF-8");//设置默认为utf-8  
        
        lstCompanyAnnouncement.addHeaderView(lltHeaderDetail, null, false);
        
        adapter = new CompanyAnnouncementDetailCommentAdapter(this, comments);
        lstCompanyAnnouncement.setAdapter(adapter);
        
        
        tvBack.setOnClickListener(this);
        
        edtComment.addTextChangedListener(new TextWatcher() {
            
            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                
            }
            
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                    int arg3) {
                
            }
            
            @Override
            public void afterTextChanged(Editable arg0) {
                String content = arg0.toString();
                tvCommentNum.setText(content.length() + "/300");
            }
        });
        
        
//        edtComment.setOnKeyListener(new OnKeyListener() {
//            
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
//                    Log.i("--->", "KEYCODE_ENTER");
//                    commentDetail();
//                    return true;
//                }
//                return false;
//            }
//        });
        
        edtComment.setOnEditorActionListener(new OnEditorActionListener() {
            
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                    KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    commentDetail();
                    return true;
                }
                return false;
            }
        });
        
        
        
        getCompanyAnnouncementDetail(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.act_fee_hunter_company_announcement_detail_back:
            finish();
            break;
        }
    }
    


    @Override
    public void onHeaderRefresh(AbPullToRefreshView view) {
        // TODO Auto-generated method stub
        getCompanyAnnouncementDetail(true);
    }
    
    private void getCompanyAnnouncementDetail(final boolean isRefresh) {
        if (companyAnnouncementBean == null) {
            return;
        }
        ActionImpl actionImpl = ActionImpl.newInstance(this);
        actionImpl.getCompanyAnnocementDetail(companyAnnouncementBean.getId(), new ResultHandlerCallback() {
            
            @Override
            public void rc999(RequestEntity entity, Result result) {
                // TODO Auto-generated method stub
                if (isRefresh) {
                    pullView.onHeaderRefreshFinish();
                } else {
                    pullView.onFooterLoadFinish();
                }
            }
            
            @Override
            public void rc3001(RequestEntity entity, Result result) {
                // TODO Auto-generated method stub
                if (isRefresh) {
                    pullView.onHeaderRefreshFinish();
                } else {
                    pullView.onFooterLoadFinish();
                }
            }
            
            @Override
            public void rc0(RequestEntity entity, Result result) {
                // TODO Auto-generated method stub
                
                if (isRefresh) {
                    pullView.onHeaderRefreshFinish();
                } else {
                    pullView.onFooterLoadFinish();
                }
                String resultStr = result.getData();
                companyAnnouncementDetailBean = JSON.parseObject(resultStr, CompanyAnnouncementDetailBean.class);
                comments.clear();
                
                if (companyAnnouncementDetailBean != null) {
                    comments.addAll(companyAnnouncementDetailBean.getCommentArr());
                    tvHeaderTitle.setText(companyAnnouncementDetailBean.getTitle());
                    tvHeaderTime.setText(companyAnnouncementDetailBean.getAddTime());
//                    webView.loadData(companyAnnouncementDetailBean.getContent(), "text/html", "UTF-8");
                    webView.loadDataWithBaseURL(null, companyAnnouncementDetailBean.getContent(), "text/html", "utf-8", null);
//                    tvHeaderContent.setText(companyAnnouncementDetailBean.getContent());
                }
                
                adapter.notifyDataSetChanged();
                
                if (comments != null) {
                    tvHeaderCommentsNum.setText(comments.size() + "");
                }
                
                
            }
        });
    }
    
    
    private void commentDetail() {
        
        if (companyAnnouncementBean == null) {
            return;
        }
        
        ActionImpl actionImpl = ActionImpl.newInstance(this);
        
        String id = companyAnnouncementBean.getId();
        String content = edtComment.getText().toString();
        if (TextUtils.isEmpty(content)) {
            ToastUtils.SHORT.toast(this, "请输入评论内容");
            return;
        }
        
        UserInfoBean userInfo = ContentUtils.getUserInfo(this);
        String commentId = null;
        String commentName = null;
        if (userInfo != null) {
            commentId = userInfo.getId();
            commentName = userInfo.getUserName();
        }
         
        
        actionImpl.commentCompanyAnnocementDetail(id, content, commentId, commentName,
                new ResultHandlerCallback() {
                    
                    @Override
                    public void rc999(RequestEntity entity, Result result) {
                        // TODO Auto-generated method stub
                        
                    }
                    
                    @Override
                    public void rc3001(RequestEntity entity, Result result) {
                        // TODO Auto-generated method stub
                        
                    }
                    
                    @Override
                    public void rc0(RequestEntity entity, Result result) {
                        // TODO Auto-generated method stub
                        edtComment.setText("");
                        getCompanyAnnouncementDetail(true);
                    }
                });
        
    }
    
}
