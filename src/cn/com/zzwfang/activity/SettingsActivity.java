package cn.com.zzwfang.activity;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.action.ImageAction;
import cn.com.zzwfang.bean.FileUploadResultBean;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.bean.UserInfoBean;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.im.MessagePool;
import cn.com.zzwfang.util.AsyncUtils;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.util.Jumper;
import cn.com.zzwfang.util.ToastUtils;
import cn.com.zzwfang.view.PathImage;
import cn.com.zzwfang.view.ToggleButton;
import cn.com.zzwfang.view.ToggleButton.OnToggleChanged;
import cn.com.zzwfang.view.helper.PopViewHelper;
import cn.com.zzwfang.view.helper.PopViewHelper.OnAvatarOptionsClickListener;

import com.alibaba.fastjson.JSON;
import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.nostra13.universalimageloader.core.ImageLoader;

public class SettingsActivity extends BasePickPhotoActivity implements OnClickListener,
    OnToggleChanged, OnAvatarOptionsClickListener {

	private static final int CODE_CHANGE_PWD = 100;
	
	private TextView tvBack, tvLogout;
	
	private FrameLayout fltChangeAvatar;
	private PathImage avatar;
	
	private TextView tvCacheSize;
	
	private FrameLayout changeNickNameFlt, changePwdFlt, clearCacheFlt, aboutUsFlt, commonQuestionFlt, checkUpdatesFlt;
	
	private ToggleButton msgPushToggleBtn;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		setContentView(R.layout.act_settings);
		
		initView();
		computeDiskCacheSize();
	}
	
	@Override
    public void onDestroy() {
        cleanFile(cacheImageDir);
        super.onDestroy();
    }
	
	private void initView() {
		
		tvBack = (TextView) findViewById(R.id.act_settings_back);
		changeNickNameFlt = (FrameLayout) findViewById(R.id.act_settings_change_nickname);
		avatar = (PathImage) findViewById(R.id.iv_settings_avatar);
		fltChangeAvatar = (FrameLayout) findViewById(R.id.act_settings_change_avatar);
		changePwdFlt = (FrameLayout) findViewById(R.id.act_settings_change_pwd);
		msgPushToggleBtn = (ToggleButton) findViewById(R.id.act_settings_msg_push_toggle);
		clearCacheFlt = (FrameLayout) findViewById(R.id.act_settings_clear_cache);
		aboutUsFlt = (FrameLayout) findViewById(R.id.act_settings_about_us);
		commonQuestionFlt = (FrameLayout) findViewById(R.id.act_settings_common_questions);
		checkUpdatesFlt = (FrameLayout) findViewById(R.id.act_settings_check_updates);
		tvLogout = (TextView) findViewById(R.id.act_settings_logout);
		tvCacheSize = (TextView) findViewById(R.id.act_settings_cache_size);
		
		msgPushToggleBtn.setOnToggleChanged(this);
		tvBack.setOnClickListener(this);
		changeNickNameFlt.setOnClickListener(this);
		changePwdFlt.setOnClickListener(this);
		clearCacheFlt.setOnClickListener(this);
		aboutUsFlt.setOnClickListener(this);
		commonQuestionFlt.setOnClickListener(this);
		checkUpdatesFlt.setOnClickListener(this);
		tvLogout.setOnClickListener(this);
		fltChangeAvatar.setOnClickListener(this);
		
		boolean isReceiveMsg = ContentUtils.getMessageReceiveSetting(this);
		if (isReceiveMsg) {
			msgPushToggleBtn.setToggleOn();
		} else {
			msgPushToggleBtn.setToggleOff();
		}
		
		UserInfoBean userInfoBean = ContentUtils.getUserInfo(this);
		if (userInfoBean != null) {
			String avatarUrl = userInfoBean.getPhoto();
	        if (!TextUtils.isEmpty(avatarUrl)) {
	            ImageAction.displayAvatar(avatarUrl, avatar);
	        }
		}
        
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_settings_back:  // 返回
			finish();
			break;
		case R.id.act_settings_change_avatar:   //  修改头像
			 PopViewHelper.showUpdateAvatarPopupWindow(this,
	                  getWindow().getDecorView(), this);
			break;
		case R.id.act_settings_change_nickname:  // 修改昵称
			Jumper.newJumper()
	        .setAheadInAnimation(R.anim.activity_push_in_right)
	        .setAheadOutAnimation(R.anim.activity_alpha_out)
	        .setBackInAnimation(R.anim.activity_alpha_in)
	        .setBackOutAnimation(R.anim.activity_push_out_right)
	        .jump(this, NickNameUpdateActivity.class);
			break;
		case R.id.act_settings_change_pwd:  // 修改密码
			Jumper.newJumper()
	        .setAheadInAnimation(R.anim.activity_push_in_right)
	        .setAheadOutAnimation(R.anim.activity_alpha_out)
	        .setBackInAnimation(R.anim.activity_alpha_in)
	        .setBackOutAnimation(R.anim.activity_push_out_right)
	        .jumpForResult(this, ChangePwdActivity.class, CODE_CHANGE_PWD);
//	        .jump(this, ChangePwdActivity.class);
			break;
		case R.id.act_settings_clear_cache:  //  清除缓存
//		    ImageAction.clearCache();
//		    ToastUtils.SHORT.toast(this, "缓存已清除");
			clearCache();
			break;
		case R.id.act_settings_about_us:  //  关于我们
			Jumper.newJumper()
	        .setAheadInAnimation(R.anim.activity_push_in_right)
	        .setAheadOutAnimation(R.anim.activity_alpha_out)
	        .setBackInAnimation(R.anim.activity_alpha_in)
	        .setBackOutAnimation(R.anim.activity_push_out_right)
	        .jump(this, AboutUsActivity.class);
			break;
		case R.id.act_settings_common_questions:  // 常见问题
			Jumper.newJumper()
	        .setAheadInAnimation(R.anim.activity_push_in_right)
	        .setAheadOutAnimation(R.anim.activity_alpha_out)
	        .setBackInAnimation(R.anim.activity_alpha_in)
	        .setBackOutAnimation(R.anim.activity_push_out_right)
	        .jump(this, CommonProblemsActivity.class);
			break;
		case R.id.act_settings_check_updates:   //  检查更新
			break;
		case R.id.act_settings_logout:   //  退出账号
		    logout();
			break;
		}
	}

	@Override
	public void onToggle(ToggleButton button, boolean on) {
		ContentUtils.setMessageReceiveSetting(this, on);
	}
	
	private void logout() {
	    ContentUtils.setUserLoginStatus(this, false);
	    ContentUtils.setUserHasLogin(this, false);
//	    ContentUtils.clearUserInfo(this);
	    logoutHX();
	    setResult(RESULT_OK);
	    MessagePool.clearAllMessages();
	    finish();
	}
	
	private void logoutHX() {
	  //此方法为异步方法
	    AsyncUtils.executeRunnable(new Runnable() {
            
            @Override
            public void run() {
                EMChatManager.getInstance().logout(new EMCallBack() {
                    
                    @Override
                    public void onSuccess() {
                        // TODO Auto-generated method stub
                 
                        runOnUiThread(new Runnable() {
                            public void run() {
                                ToastUtils.SHORT.toast(SettingsActivity.this, "已退出登录");
                            }
                        });
                        
                    }
                 
                    @Override
                    public void onProgress(int progress, String status) {
                 
                    }
                 
                    @Override
                    public void onError(int code, String message) {
                 
                    }
                });
            }
        });
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case CODE_CHANGE_PWD:
				// 修改密码后退出登录，需要重新登录
				logoutHX();
				setResult(RESULT_OK);
			    finish();
				break;
			}
		}
	}
	
	@Override
	public void onAvatarOptionClick(int action) {
		// TODO Auto-generated method stub
		switch (action) {
        case OnAvatarOptionsClickListener.ACTION_CAMERA: // 相机

            if (isCrop) {
                startPickPhotoFromCameraWithCrop();
            } else {
                startPickPhotoFromCamara();
            }

            break;
        case OnAvatarOptionsClickListener.ACTION_ALBUM: // 相册
            if (isCrop) {
                startPickPhotoFromAlbumWithCrop();
            } else {
                startPickPhotoFromAlbum();
            }
            break;
        }
	}
	
	@Override
    public void onPickedPhoto(File file, Bitmap bm) {
        avatar.setImageBitmap(bm);
        upLoadAvatar(file);
    }

    @Override
    public int getDisplayWidth() {
        return 800;
    }

    @Override
    public int getDisplayHeight() {
        return 800;
    }
    
    private void upLoadAvatar(File file) {
        ActionImpl actionImpl = ActionImpl.newInstance(this);
        actionImpl.otherFileUpload(file, new ResultHandlerCallback() {

            @Override
            public void rc999(RequestEntity entity, Result result) {
            }

            @Override
            public void rc3001(RequestEntity entity, Result result) {
            }

            @Override
            public void rc0(RequestEntity entity, Result result) {
                // TODO Auto-generated method stub
                FileUploadResultBean fileUploadResultBean = JSON.parseObject(
                        result.getData(), FileUploadResultBean.class);
                ContentUtils.updateUserAvatar(SettingsActivity.this,
                        fileUploadResultBean.getShowPath());
                updateUserInfoAvatar(fileUploadResultBean.getFilePath());
            }
        });
    }
    
    private void updateUserInfoAvatar(String avatarUrl) {
        ActionImpl actionImpl = ActionImpl.newInstance(this);
        String userId = ContentUtils.getUserId(this);
        actionImpl.updateUserInfo(userId, null, avatarUrl,
                new ResultHandlerCallback() {

                    @Override
                    public void rc999(RequestEntity entity, Result result) {
                    }

                    @Override
                    public void rc3001(RequestEntity entity, Result result) {
                    }

                    @Override
                    public void rc0(RequestEntity entity, Result result) {
                        ToastUtils.SHORT.toast(SettingsActivity.this, "头像上传成功");
                    }
                });
    }
	
	
	private void computeDiskCacheSize() {
		
        final File imageDiskCacheFile = ImageLoader.getInstance().getDiskCache().getDirectory();
        if (imageDiskCacheFile != null && imageDiskCacheFile.exists()) {
            AsyncUtils.executeRunnable(new Runnable() {
                @Override
                public void run() {
                    long imageSize = getFileSize(imageDiskCacheFile);
                    double result = imageSize / (1024 * 1024);
                    final int value = Double.valueOf(result).intValue();
                    AsyncUtils.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                        	tvCacheSize.setText(value + "M");
                        }
                    });
                }
            });
        }
    }
	
	private void clearCache() {
		final File imageDiskCacheFile = ImageLoader.getInstance().getDiskCache().getDirectory();
        AsyncUtils.executeRunnable(new Runnable() {
            @Override
            public void run() {
                AsyncUtils.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                    	tvCacheSize.setText("清理中...");
                    }
                });
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                clearFile(imageDiskCacheFile);
                computeDiskCacheSize();
            }
        });
	}
	
	public boolean clearFile(File file) {
        if (!file.exists()) {
            return false;
        }
        if (!file.isDirectory()) {
            return file.delete();
        }
        File[] files = file.listFiles();
        for (File child : files) {
            clearFile(child);
        }
        return true;
    }
	
	/**获取文件的大小
     * @param file
     * @return
     */
    public long getFileSize(File file) {
        long size = 0;
        if (file == null || !file.exists()) {
            return 0;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                size += getFileSize(f);
            }
        } else {
            size = file.length();
        }
        return size;
    }
    
    @Override
    protected int getStatusBarTintResource() {
    	// TODO Auto-generated method stub
    	return R.color.color_0ed76d;
    }


	

}
