package cn.com.zzwfang.bean;

import java.util.ArrayList;

public class AgentInfoDetailBean extends BaseBean {

	/**
	 * 浏览量
	 */
	private String ClickCount;
	
	private String Name;
	
	/**
	 * 头像
	 */
	private String Photo;
	
	/**
	 * 租房数量
	 */
	private String RentCount;
	
	/**
	 * 销售量
	 */
	private String SaleCount;
	
	/**
	 * 电话
	 */
	private String Tel;
	
	/**
	 * 座右铭
	 */
	private String Remark;
	
	private ArrayList<AgentInfoItemBean> ListProperty;

	public String getClickCount() {
		return ClickCount;
	}

	public void setClickCount(String clickCount) {
		ClickCount = clickCount;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getPhoto() {
		return Photo;
	}

	public void setPhoto(String photo) {
		Photo = photo;
	}

	public String getRentCount() {
		return RentCount;
	}

	public void setRentCount(String rentCount) {
		RentCount = rentCount;
	}

	public String getSaleCount() {
		return SaleCount;
	}

	public void setSaleCount(String saleCount) {
		SaleCount = saleCount;
	}

	public String getTel() {
		return Tel;
	}

	public void setTel(String tel) {
		Tel = tel;
	}

	public ArrayList<AgentInfoItemBean> getListProperty() {
		return ListProperty;
	}

	public void setListProperty(ArrayList<AgentInfoItemBean> listProperty) {
		ListProperty = listProperty;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}
	
	
	
	
}
