package cn.com.zzwfang.bean;

import java.util.ArrayList;

public class HouseSourceParamBean {

	private String filed;
	private String name;
	private ArrayList<NameValueBean> values;
	public String getFiled() {
		return filed;
	}
	public void setFiled(String filed) {
		this.filed = filed;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<NameValueBean> getValues() {
		return values;
	}
	public void setValues(ArrayList<NameValueBean> values) {
		this.values = values;
	}
	
	
}
