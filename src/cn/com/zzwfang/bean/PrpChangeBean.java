package cn.com.zzwfang.bean;

import java.util.ArrayList;

public class PrpChangeBean extends BaseBean {

	private ArrayList<PrpChangeEmployeBean> employes;
	
	private ArrayList<PrpChangeFollowBean> follow;

	public ArrayList<PrpChangeEmployeBean> getEmployes() {
		return employes;
	}

	public void setEmployes(ArrayList<PrpChangeEmployeBean> employes) {
		this.employes = employes;
	}

	public ArrayList<PrpChangeFollowBean> getFollow() {
		return follow;
	}

	public void setFollow(ArrayList<PrpChangeFollowBean> follow) {
		this.follow = follow;
	}
	
	
}
