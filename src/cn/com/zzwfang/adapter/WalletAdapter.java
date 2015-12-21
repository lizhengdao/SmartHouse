package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.WalletDetailItemBean;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WalletAdapter extends BaseAdapter {

	private Context context;
	
	private ArrayList<WalletDetailItemBean> walletInfos;
	
	public WalletAdapter(Context context, ArrayList<WalletDetailItemBean> walletInfos) {
		this.context = context;
		this.walletInfos = walletInfos;
	}
	
	@Override
	public int getCount() {
		if (walletInfos == null) {
			return 0;
		}
		return walletInfos.size();
	}

	@Override
	public Object getItem(int position) {
		return walletInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.adapter_wallet, null);
		}
		
		TextView tvDesc = ViewHolder.get(convertView, R.id.adapter_wallet_desc);
		TextView tvMoney = ViewHolder.get(convertView, R.id.adapter_wallet_money);
		
		WalletDetailItemBean walletDetailItemBean = walletInfos.get(position);
		String desc = walletDetailItemBean.getEstateName() + walletDetailItemBean.getBuildName() + "栋" +
				walletDetailItemBean.getUnit() + "单元";
		tvDesc.setText(desc);
		tvMoney.setText(walletDetailItemBean.getPrice() + "元");
		return convertView;
	}

}
