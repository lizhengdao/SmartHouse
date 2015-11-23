/**
 *        http://www.june.com
 * Copyright © 2015 June.Co.Ltd. All Rights Reserved.
 */
package cn.com.zzwfang.im;

import cn.com.zzwfang.util.AsyncUtils;

import com.easemob.EMCallBack;
import com.easemob.chat.EMMessage;

/**
 * @author Soo
 */
public class MessageSendCallback implements EMCallBack {
    
    public interface OnMessageSendListener {
        
       void onSuccess(EMMessage message);
       
       void onProgress(EMMessage message, int arg0, String arg1);
       
       void onError(EMMessage message, int arg0, String arg1);
    }
    
    private final OnMessageSendListener listener;
    private final EMMessage message;
    
    public MessageSendCallback(EMMessage message, OnMessageSendListener listener) {
        this.message = message;
        this.listener = listener;
    }

    @Override
    public void onError(int arg0, String arg1) {
        if (listener != null) {
            AsyncUtils.postRunnable(new InnerRunnable(message, arg0, arg1, listener, InnerRunnable.CMD_ERROR));
        }
    }

    @Override
    public void onProgress(int arg0, String arg1) {
        if (listener != null) {
            AsyncUtils.postRunnable(new InnerRunnable(message, arg0, arg1, listener, InnerRunnable.CMD_PROGRESS));
        }
    }

    @Override
    public void onSuccess() {
        if (listener != null) {
            AsyncUtils.postRunnable(new InnerRunnable(message, 1, "success", listener, InnerRunnable.CMD_SUCCESS));
        }
    }
    
    static class InnerRunnable implements Runnable {
        
        static final int CMD_ERROR = 1;
        static final int CMD_SUCCESS = 2;
        static final int CMD_PROGRESS = 3;
        
        final EMMessage message;
        final int arg0;
        final String arg1;
        final OnMessageSendListener listener;
        final int cmd;
        
        InnerRunnable(EMMessage message, int arg0, String arg1, OnMessageSendListener listener, int cmd) {
            this.message = message;
            this.arg0 = arg0;
            this.arg1 = arg1;
            this.listener = listener;
            this.cmd = cmd;
        }
        
        @Override
        public void run() {
            if (listener == null) {
                return;
            }
            switch (cmd) {
                case CMD_ERROR:
                    listener.onError(message, arg0, arg1);
                    break;
                case CMD_SUCCESS:
                    listener.onSuccess(message);
                    break;
                case CMD_PROGRESS:
                    listener.onProgress(message, arg0, arg1);
                    break;
            }
        }
    }
}
