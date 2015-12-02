package cn.com.zzwfang.share;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;

public class TencentQQShareHelper {

    public void shareImageAndText(Context context, Tencent tencent,
            IUiListener iUiListener) {
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE,
                QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "要分享的标题");
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "要分享的摘要");
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,
                "http://www.qq.com/news/1.html");
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,
                "http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "测试应用222222");
        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, 1);
        tencent.shareToQQ((Activity) context, params, iUiListener);
    }

    public void shareToQzone (Context context, Tencent tencent, IUiListener iUiListener) {
        //分享类型
        Bundle params = new Bundle();
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, "标题");//必填
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, "摘要");//选填
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, "跳转URL");//必填
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, null);
        tencent.shareToQzone((Activity) context, params, iUiListener);
        
      }
}
