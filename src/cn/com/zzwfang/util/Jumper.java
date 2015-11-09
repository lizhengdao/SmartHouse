/**
 *        http://www.june.com
 * Copyright © 2015 June.Co.Ltd. All Rights Reserved.
 */
package cn.com.zzwfang.util;

import java.io.Serializable;

import cn.com.zzwfang.activity.BaseActivity;
import cn.com.zzwfang.fragment.BaseFragment;
import android.content.Intent;


/**
 * ClassName: Jumper <br/>
 * Function: 负责简化Activity跳转. <br/>
 */
public class Jumper {

    public static final String JUMPER_ANIMATION_BACK_IN = "Jumper_animation_back_in";
    public static final String JUMPER_ANIMATION_BACK_OUT = "Jumper_animation_back_out";

    private Intent intent = null;

    private int aheadInAnimation;
    private int aheadOutAnimation;

    private int backInAnimation;
    private int backOutAnimation;

    private Jumper() {
        this.intent = new Intent();
    }

    public static Jumper newJumper() {
        // TODO reused the intent which out of date...
        return new Jumper();
    }

    public Jumper putInt(String key, int value) {
        intent.putExtra(key, value);
        return this;
    }

    public Jumper putLong(String key, long value) {
        intent.putExtra(key, value);
        return this;
    }

    public Jumper putString(String key, String value) {
        intent.putExtra(key, value);
        return this;
    }

    public Jumper putSerializable(String key, Serializable value) {
        intent.putExtra(key, value);
        return this;
    }

    public Jumper putBoolean(String key, boolean value) {
        intent.putExtra(key, value);
        return this;
    }

    public Jumper setAheadInAnimation(int animation) {
        this.aheadInAnimation = animation;
        return this;
    }

    public Jumper setAheadOutAnimation(int animation) {
        this.aheadOutAnimation = animation;
        return this;
    }

    public Jumper setBackInAnimation(int animation) {
        this.backInAnimation = animation;
        return this;
    }

    public Jumper setBackOutAnimation(int animation) {
        this.backOutAnimation = animation;
        return this;
    }

    public Jumper setIntentFlag(int flag) {
        intent.setFlags(flag);
        return this;
    }

    public Jumper addIntentFlag(int flag) {
        intent.addFlags(flag);
        return this;
    }

    public void jump(BaseActivity fromActivity, Class<? extends BaseActivity> toActivityClass) {
        intent.setClass(fromActivity, toActivityClass);
        intent.putExtra(JUMPER_ANIMATION_BACK_IN, backInAnimation);
        intent.putExtra(JUMPER_ANIMATION_BACK_OUT, backOutAnimation);
        fromActivity.startActivity(intent);
        fromActivity.overridePendingTransition(aheadInAnimation, aheadOutAnimation);
    }
    
    public void jumpForResult(BaseActivity fromActivity, Class<? extends BaseActivity> toActivityClass, int code) {
        intent.setClass(fromActivity, toActivityClass);
        intent.putExtra(JUMPER_ANIMATION_BACK_IN, backInAnimation);
        intent.putExtra(JUMPER_ANIMATION_BACK_OUT, backOutAnimation);
        fromActivity.startActivityForResult(intent, code);
        fromActivity.overridePendingTransition(aheadInAnimation, aheadOutAnimation);
    }

    public void jump(BaseFragment fromFragment, Class<? extends BaseActivity> toActivityClass) {
        intent.setClass(fromFragment.getActivity(), toActivityClass);
        intent.putExtra(JUMPER_ANIMATION_BACK_IN, backInAnimation);
        intent.putExtra(JUMPER_ANIMATION_BACK_OUT, backOutAnimation);
        fromFragment.startActivity(intent);
        fromFragment.getActivity().overridePendingTransition(aheadInAnimation, aheadOutAnimation);
    }

    public void jumpForResult(BaseFragment fromFragment, Class<? extends BaseActivity> toActivityClass, int code) {
        intent.setClass(fromFragment.getActivity(), toActivityClass);
        intent.putExtra(JUMPER_ANIMATION_BACK_IN, backInAnimation);
        intent.putExtra(JUMPER_ANIMATION_BACK_OUT, backOutAnimation);
        fromFragment.startActivityForResult(intent, code);
        fromFragment.getActivity().overridePendingTransition(aheadInAnimation, aheadOutAnimation);
    }

    public void clear() {
        // TODO
    }

}
