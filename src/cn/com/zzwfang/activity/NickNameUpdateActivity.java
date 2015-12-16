package cn.com.zzwfang.activity;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.Result;
import cn.com.zzwfang.bean.UserInfoBean;
import cn.com.zzwfang.controller.ActionImpl;
import cn.com.zzwfang.controller.ResultHandler.ResultHandlerCallback;
import cn.com.zzwfang.http.RequestEntity;
import cn.com.zzwfang.util.ContentUtils;
import cn.com.zzwfang.util.ToastUtils;

/**
 * 修改昵称页
 * 
 * @author lzd
 * 
 */
public class NickNameUpdateActivity extends BaseActivity implements
		OnClickListener {

	// 匹配非表情符号的正则表达式
	// private final String reg =
	// "^([a-z]|[A-Z]|[0-9]|[\u2E80-\u9FFF]){3,}|@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?|[wap.]{4}|[www.]{4}|[blog.]{5}|[bbs.]{4}|[.com]{4}|[.cn]{3}|[.net]{4}|[.org]{4}|[http://]{7}|[ftp://]{6}$";
	private TextView tvBack, tvCommit;

	private EditText edtNickName;

	// private Pattern pattern;
	// 输入表情前的光标位置
	// private int cursorPos;
	// 输入表情前EditText中的文本
	// private String tmp;
	// 是否重置了EditText的内容
	// private boolean resetText;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initView();
	}

	private void initView() {
		setContentView(R.layout.act_nick_name_update);

		tvBack = (TextView) findViewById(R.id.act_nick_name_update_back);
		edtNickName = (EditText) findViewById(R.id.act_nick_name_edt);
		tvCommit = (TextView) findViewById(R.id.act_nick_name_commit);

		UserInfoBean userInfoBean = ContentUtils.getUserInfo(this);
		edtNickName.setText(userInfoBean.getUserName());

		tvBack.setOnClickListener(this);
		tvCommit.setOnClickListener(this);

		// pattern = Pattern.compile(reg);

		TextWatcher textWatcher = new TextWatcher() {
			String tmp = "";
			String digits = "/\\:*?<>|\"\n\t";

//			private int editStart;
//			private int editEnd;
//			private int maxLen = 10; // the max byte

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				edtNickName.setSelection(s.length());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				tmp = s.toString();
			}

			@Override
			public void afterTextChanged(Editable s) {

				String str = s.toString();
				if (str.equals(tmp)) {
					return;
				}
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < str.length(); i++) {
					if (digits.indexOf(str.charAt(i)) < 0) {
						sb.append(str.charAt(i));
					}
				}
				tmp = sb.toString();
				edtNickName.setText(tmp);
				
				
//				editStart = edtNickName.getSelectionStart();
//				editEnd = edtNickName.getSelectionEnd();
//				// 先去掉监听器，否则会出现栈溢出
//				edtNickName.removeTextChangedListener(this);
//				if (!TextUtils.isEmpty(edtNickName.getText())) {
//					String etstring = edtNickName.getText().toString().trim();
//					while (calculateLength(s.toString()) > maxLen) {
//						s.delete(editStart - 1, editEnd);
//						editStart--;
//						editEnd--;
//					}
//
//				}
//				// edtNickName.setText(s);
//
//				edtNickName.setText(tmp);
//				edtNickName.setSelection(editStart);
//
//				// 恢复监听器
//				edtNickName.addTextChangedListener(this);
			}
		};

		edtNickName.addTextChangedListener(textWatcher);

	}

	private int calculateLength(String etstring) {
		char[] ch = etstring.toCharArray();

		int varlength = 0;
		for (int i = 0; i < ch.length; i++) {
			// changed by zyf 0825 , bug 6918，加入中文标点范围 ， TODO 标点范围有待具体化
			if ((ch[i] >= 0x2E80 && ch[i] <= 0xFE4F)
					|| (ch[i] >= 0xA13F && ch[i] <= 0xAA40) || ch[i] >= 0x80) { // 中文字符范围0x4e00
																				// 0x9fbb
				varlength = varlength + 2;
			} else {
				varlength++;
			}
		}
		// 这里也可以使用getBytes,更准确嘛
		// varlength = etstring.getBytes(CharSet.forName("GBK")).lenght;//
		// 编码根据自己的需求，注意u8中文占3个字节...
		return varlength;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_nick_name_update_back:
			finish();
			break;
		case R.id.act_nick_name_commit:
			updateNickName();
			break;
		}
	}

	private void updateNickName() {
		final String nickName = edtNickName.getText().toString();
		if (TextUtils.isEmpty(nickName)) {
			ToastUtils.SHORT.toast(this, "请输入昵称");
			return;
		}
		if (nickName.getBytes().length > 14) {
			ToastUtils.SHORT.toast(this, "您输入的昵称过长");
			return;
		}
		ActionImpl actionImpl = ActionImpl.newInstance(this);
		String userId = ContentUtils.getUserId(this);
		actionImpl.updateUserInfo(userId, nickName, null,
				new ResultHandlerCallback() {

					@Override
					public void rc999(RequestEntity entity, Result result) {
					}

					@Override
					public void rc3001(RequestEntity entity, Result result) {
					}

					@Override
					public void rc0(RequestEntity entity, Result result) {
						ContentUtils.updateUserNickName(
								NickNameUpdateActivity.this, nickName);
						ToastUtils.SHORT.toast(NickNameUpdateActivity.this,
								"昵称修改成功");
						finish();
					}
				});
	}

}
