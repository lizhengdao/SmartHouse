package cn.com.zzwfang.bean;

import java.util.ArrayList;

public class NewHouseDetailBean extends BaseBean {

	private AgentBean Agent;
	
	private String GreeningRate;
	
	private long Lat;
	
	private long Lng;
	
	private String Name;
	
	private ArrayList<String> Photo;
	
	private String PlotRatio;
	
	private String UnitPrice;
	
//	private ArrayList<PriceTrendBean> priceTrend;

	public AgentBean getAgent() {
		return Agent;
	}

	public void setAgent(AgentBean agent) {
		Agent = agent;
	}

	public String getGreeningRate() {
		return GreeningRate;
	}

	public void setGreeningRate(String greeningRate) {
		GreeningRate = greeningRate;
	}

	public long getLat() {
		return Lat;
	}

	public void setLat(long lat) {
		Lat = lat;
	}

	public long getLng() {
		return Lng;
	}

	public void setLng(long lng) {
		Lng = lng;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public ArrayList<String> getPhoto() {
		return Photo;
	}

	public void setPhoto(ArrayList<String> photo) {
		Photo = photo;
	}

	public String getPlotRatio() {
		return PlotRatio;
	}

	public void setPlotRatio(String plotRatio) {
		PlotRatio = plotRatio;
	}

	public String getUnitPrice() {
		return UnitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		UnitPrice = unitPrice;
	}

//	public ArrayList<PriceTrendBean> getPriceTrend() {
//		return priceTrend;
//	}
//
//	public void setPriceTrend(ArrayList<PriceTrendBean> priceTrend) {
//		this.priceTrend = priceTrend;
//	}
	
	
}
