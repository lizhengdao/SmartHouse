package cn.com.zzwfang.controller;

import cn.com.zzwfang.R;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.view.Dialog_1;
import android.content.Context;
import android.content.DialogInterface;

public class ProgressDialogResultHandler extends ResultHandler implements
		DialogInterface.OnDismissListener {

	private Context context;

	private String message;

	private Dialog_1 dialog;

	public ProgressDialogResultHandler(Context context, String message) {
		this.context = context;
		this.message = message;
		dialog = new Dialog_1(context, R.style.DialogStyle_1);
		dialog.setMessage(message);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setOnDismissListener(this);
	}

	@Override
	public void onPreExecute(RequestEntity entity) {
		super.onPreExecute(entity);
		if (!dialog.isShowing()) {
			dialog.show();
		}
	}

	@Override
	public void onProcess(RequestEntity entity, int process) {
		super.onProcess(entity, process);
	}

	@Override
	public void onPostExecute(Context context, RequestEntity entity, byte[] data) {
		super.onPostExecute(context, entity, data);
		if (dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	@Override
	public void onCancelled(RequestEntity entity) {
		super.onCancelled(entity);
		if (dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		intercept();
	}

	public String getMessage() {
		return message;
	}

	public Context getContext() {
		return context;
	}
}
