package cn.com.zzwfang.bean;

import java.util.ArrayList;

public class PriceTrendBean extends BaseBean {

	private String name;
	
	private ArrayList<Float> data;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Float> getData() {
		return data;
	}

	public void setData(ArrayList<Float> data) {
		this.data = data;
	}
	
	
}
