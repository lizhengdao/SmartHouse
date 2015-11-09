package cn.com.zzwfang.view;

import cn.com.zzwfang.R;
import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

public class Dialog_1 extends Dialog {

	private String message = "";

	private TextView mTextView = null;

	public Dialog_1(Context context) {
		this(context, R.style.DialogStyle_1);
	}

	public Dialog_1(Context context, int theme) {
		super(context, theme);
		setContentView(R.layout.dialog_1);
		initialViews();
	}

	private void initialViews() {
		mTextView = (TextView) findViewById(R.id.dialog_1_text);
	}

	public void setMessage(String message) {
		this.message = message;
		mTextView.setText(message);
	}

	public String getMessage() {
		return message;
	}

	@Override
	public void show() {
		super.show();
	}
}
