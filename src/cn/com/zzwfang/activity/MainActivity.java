package cn.com.zzwfang.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import cn.com.zzwfang.R;
import cn.com.zzwfang.activity.BaseActivity.OnNewMessageListener;
import cn.com.zzwfang.adapter.MainContentPagerAdapter;
import cn.com.zzwfang.bean.CityBean;
import cn.com.zzwfang.bean.IMMessageBean;
import cn.com.zzwfang.bean.MessageBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.bean.UserInfoBean;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.fragment.MainHomeFragment.OnCitySelectedListener;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.im.MessagePool;
import cn.com.zzwfang.util.AppUtils;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.util.ToastUtils;
import cn.com.zzwfang.view.GuiderView;
import cn.com.zzwfang.view.UnScrollableViewPager;

import com.alibaba.fastjson.JSON;
import com.baidu.mapapi.SDKInitializer;
import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;

public class MainActivity extends BaseActivity implements OnPageChangeListener,
        OnClickListener, OnCitySelectedListener, OnNewMessageListener {
    
    public interface OnLoginListener {
        void onLongin();
    }

    public static final int CODE_LOGIN_MINE = 800;

    private UnScrollableViewPager contentPager;

    private MainContentPagerAdapter contentAdapter;

    private GuiderView guiderHome, guiderMapFindHouse, guiderConsultant,
            guiderMine;

    private ArrayList<GuiderView> guidIndicators = new ArrayList<GuiderView>();

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.act_main);

        initView();
        login();
    }

    private void initView() {
        contentPager = (UnScrollableViewPager) findViewById(R.id.act_main_content_pager);
        guiderHome = (GuiderView) findViewById(R.id.act_main_guider_home);
        guiderMapFindHouse = (GuiderView) findViewById(R.id.act_main_guider_map);
        guiderConsultant = (GuiderView) findViewById(R.id.act_main_guider_consultant);
        guiderMine = (GuiderView) findViewById(R.id.act_main_guider_mine);

        guidIndicators.add(guiderHome);
        guidIndicators.add(guiderMapFindHouse);
        guidIndicators.add(guiderConsultant);
        guidIndicators.add(guiderMine);

        guiderHome.setIconAlpha(1.0f);

        contentAdapter = new MainContentPagerAdapter(
                getSupportFragmentManager());
        contentPager.setOffscreenPageLimit(4);
        contentPager.setAdapter(contentAdapter);

        contentPager.setOnPageChangeListener(this);
        guiderHome.setOnClickListener(this);
        guiderMapFindHouse.setOnClickListener(this);
        guiderConsultant.setOnClickListener(this);
        guiderMine.setOnClickListener(this);

    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset,
            int positionOffsetPixels) {
        if (positionOffset > 0) {
            GuiderView left = guidIndicators.get(position);
            GuiderView right = guidIndicators.get(position + 1);
            left.setIconAlpha(1 - positionOffset);
            right.setIconAlpha(positionOffset);
        }
    }

    @Override
    public void onPageSelected(int arg0) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        case R.id.act_main_guider_home:
            clickTab(0);
            refreshStatusBarColor(R.color.color_app_theme);
            break;
        case R.id.act_main_guider_map:
            clickTab(1);
            refreshStatusBarColor(R.color.color_app_theme);
            break;
        case R.id.act_main_guider_consultant:
            clickTab(2);
            refreshStatusBarColor(R.color.white);
            break;
        case R.id.act_main_guider_mine:
            clickTab(3);
            refreshStatusBarColor(R.color.color_frag_main_mine_status_bar);
            break;
        }
    }

    private void clickTab(int position) {
        for (GuiderView guider : guidIndicators) {
            guider.setIconAlpha(0.0f);
        }
        // if (position == 3) {
        // boolean loginStatus = ContentUtils.getUserLoginStatus(this);
        // if (!loginStatus) {
        // Jumper.newJumper()
        // .setAheadInAnimation(R.anim.slide_in_style1)
        // .setAheadOutAnimation(R.anim.alpha_out_style1)
        // .setBackInAnimation(R.anim.alpha_in_style1)
        // .setBackOutAnimation(R.anim.slide_out_style1)
        // .jumpForResult(this, LoginActivity.class, CODE_LOGIN_MINE);
        // return;
        // }
        // }
        guidIndicators.get(position).setIconAlpha(1.0f);
        contentPager.setCurrentItem(position, false);
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        if (AppUtils.isDoubleClick(this)) {
//            ContentUtils.clearUserInfo(this);
            ContentUtils.setUserLoginStatus(this, false);
            exitApplication(true);
            // moveTaskToBack(true);
        } else {
            ToastUtils.SHORT.toast(this, R.string.act_main_exitapp);
        }

    }

    public void backToHomeFragment() {
        clickTab(0);
    }

    @Override
    public void onCitySelected(CityBean cityBean) {
        contentAdapter.onCitySelected(cityBean);
    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
        if (arg1 == RESULT_OK) {
            switch (arg0) {
            case CODE_LOGIN_MINE:
                clickTab(3);
                break;
            }
        }
    }

    @Override
	public void onNewMessage(MessageBean msg) {
        if (contentAdapter != null) {
            contentAdapter.updateMessageCount(msg);
        }

    }
    
    private void login() {
        boolean hasLogin = ContentUtils.getUserHasLogin(this);
        final String phoneNum = ContentUtils.getLoginPhone(this);
        final String pwd = ContentUtils.getLoginPwd(this);
        if (hasLogin && !TextUtils.isEmpty(phoneNum) && !TextUtils.isEmpty(pwd)) {
            ActionImpl actionImpl = ActionImpl.newInstance(this);
            actionImpl.login(phoneNum, pwd, new ResultHandlerCallback() {
                
                @Override
                public void rc999(RequestEntity entity, Result result) {
                }
                
                @Override
                public void rc3001(RequestEntity entity, Result result) {
                }
                
                @Override
                public void rc0(RequestEntity entity, Result result) {
                    final UserInfoBean userInfo = JSON.parseObject(result.getData(), UserInfoBean.class);
                    ContentUtils.saveUserInfo(MainActivity.this, userInfo);
                    ContentUtils.saveLoginPhone(MainActivity.this, phoneNum);
                    ContentUtils.saveLoginPwd(MainActivity.this, pwd);
                    ContentUtils.setUserHasLogin(MainActivity.this, true);
                    ActionImpl actionImpl = ActionImpl.newInstance(MainActivity.this);
                    actionImpl.createIMAccount(userInfo.getId(), userInfo.getId(), new ResultHandlerCallback() {
                        
                        @Override
                        public void rc999(RequestEntity entity, Result result) {
                        }
                        
                        @Override
                        public void rc3001(RequestEntity entity, Result result) {
                        }
                        
                        @Override
                        public void rc0(RequestEntity entity, Result result) {
                            loginOnHx(userInfo.getId(), userInfo.getId());
                        }
                    });
                }
            });
        }
    }
    
    private void loginOnHx(String easeId, String easePwd) {
        EMChatManager.getInstance().login(easeId, easePwd, new EMCallBack() {
            
            @Override
            public void onSuccess() {
                try {
//                  EMGroupManager.getInstance().loadAllGroups();
//                  EMChatManager.getInstance().loadAllConversations();
//                  EMChatManager.getInstance().
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ContentUtils.setUserLoginStatus(MainActivity.this, true);
                runOnUiThread(new Runnable() {
                    
                    @Override
                    public void run() {
                        contentAdapter.onLongin();
                        getContacts();
                    }
                });
            }
            
            @Override
            public void onProgress(int arg0, String arg1) {
                
            }
            
            @Override
            public void onError(int arg0, String arg1) {
                    runOnUiThread(new Runnable() {
                    
                    @Override
                    public void run() {
                        ContentUtils.setUserLoginStatus(MainActivity.this, false);
                    }
                });
            }
        });
    }
    
    private void getContacts() {
        String userId = ContentUtils.getUserId(this);
        ActionImpl actionImpl = ActionImpl.newInstance(this);
        actionImpl.getContactsList(userId, new ResultHandlerCallback() {
            
            @Override
            public void rc999(RequestEntity entity, Result result) {
                
            }
            
            @Override
            public void rc3001(RequestEntity entity, Result result) {
                
            }
            
            @Override
            public void rc0(RequestEntity entity, Result result) {
                ArrayList<IMMessageBean> temp = (ArrayList<IMMessageBean>) JSON.parseArray(result.getData(), IMMessageBean.class);
                if (temp != null) {
                    MessagePool.addAllContactsMessages(temp);
                }
                ContentUtils.setUserLoginStatus(MainActivity.this, true);
            }
        });
    }
}
