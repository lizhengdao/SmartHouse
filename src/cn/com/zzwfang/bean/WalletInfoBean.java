package cn.com.zzwfang.bean;

import java.util.ArrayList;

public class WalletInfoBean extends BaseBean {

	/**
	 * 总收益
	 */
	private String Cumulative;
	
	/**
	 * 上月收益
	 */
	private String Monthly;
	
	private ArrayList<WalletDetailItemBean> Detailed;

	public String getCumulative() {
		return Cumulative;
	}

	public void setCumulative(String cumulative) {
		Cumulative = cumulative;
	}

	public String getMonthly() {
		return Monthly;
	}

	public void setMonthly(String monthly) {
		Monthly = monthly;
	}

	public ArrayList<WalletDetailItemBean> getDetailed() {
		return Detailed;
	}

	public void setDetailed(ArrayList<WalletDetailItemBean> detailed) {
		Detailed = detailed;
	}
	
	
}
