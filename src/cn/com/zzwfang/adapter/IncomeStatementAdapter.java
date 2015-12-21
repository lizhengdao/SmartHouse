package cn.com.zzwfang.adapter;

import java.util.ArrayList;

import cn.com.zzwfang.R;
import cn.com.zzwfang.bean.IncomeStatementBean;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class IncomeStatementAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<IncomeStatementBean> incomeStatements;
	
	public IncomeStatementAdapter(Context context,  ArrayList<IncomeStatementBean> incomeStatements) {
		this.context = context;
		this.incomeStatements = incomeStatements;
	}
	@Override
	public int getCount() {
		if (incomeStatements != null) {
			return 0;
		}
		return incomeStatements.size();
	}

	@Override
	public Object getItem(int position) {
		return incomeStatements.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.adapter_income_statement, null);
		}
		
		IncomeStatementBean temp = incomeStatements.get(position);
		
		TextView tvDate = ViewHolder.get(convertView, R.id.adapter_income_statement_date);
		TextView tvType = ViewHolder.get(convertView, R.id.adapter_income_statement_type);
		TextView tvPrice = ViewHolder.get(convertView, R.id.adapter_income_statement_price);
		TextView tvInvoiceState = ViewHolder.get(convertView, R.id.adapter_income_statement_invoice_state);
		TextView tvBrokerName = ViewHolder.get(convertView, R.id.adapter_income_statement_broker_name);
		
		tvDate.setText(temp.getDate());
		tvType.setText(temp.getType() + ":");
		if (temp.isInvoice()) {
			tvInvoiceState.setText("已开");
		} else {
			tvInvoiceState.setText("未开");
		}
		tvPrice.setText(temp.getPrice());
		tvBrokerName.setText(temp.getBrokerage());
		return convertView;
	}

}
