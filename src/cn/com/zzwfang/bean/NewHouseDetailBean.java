package cn.com.zzwfang.bean;

import java.util.ArrayList;

public class NewHouseDetailBean extends BaseBean {

	private String AeraName;
	
	private AgentBean Agent;
	
	private String GreeningRate;
	
	private String FloorArea;
	
	private String MgtCompany;
	
	private String Decoration;
	
	private String Builder;
	
	private double Lat;
	
	private double Lng;
	
	private String Name;
	
	private ArrayList<PhotoBean> Photo;
	
	private String PlotRatio;
	
	private String UnitPrice;
	
	private String PropertyType;
	
	private String Remark;
	
	private String V360;
	
	private ArrayList<PhotoBean> HousePhoto;
	
	private String Share;
	

	public String getShare() {
        return Share;
    }

    public void setShare(String share) {
        Share = share;
    }

    public ArrayList<PhotoBean> getHousePhoto() {
        return HousePhoto;
    }

    public void setHousePhoto(ArrayList<PhotoBean> housePhoto) {
        HousePhoto = housePhoto;
    }

    public String getAeraName() {
		return AeraName;
	}

	public void setAeraName(String aeraName) {
		AeraName = aeraName;
	}

	public String getPropertyType() {
		return PropertyType;
	}

	public void setPropertyType(String propertyType) {
		PropertyType = propertyType;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}

	public String getV360() {
		return V360;
	}

	public void setV360(String v360) {
		V360 = v360;
	}

	public AgentBean getAgent() {
		return Agent;
	}

	public String getBuilder() {
		return Builder;
	}

	public void setBuilder(String builder) {
		Builder = builder;
	}

	public String getDecoration() {
		return Decoration;
	}

	public void setDecoration(String decoration) {
		Decoration = decoration;
	}

	public String getMgtCompany() {
		return MgtCompany;
	}

	public void setMgtCompany(String mgtCompany) {
		MgtCompany = mgtCompany;
	}

	public String getFloorArea() {
		return FloorArea;
	}

	public void setFloorArea(String floorArea) {
		FloorArea = floorArea;
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

	public double getLat() {
		return Lat;
	}

	public void setLat(double lat) {
		Lat = lat;
	}

	public double getLng() {
		return Lng;
	}

	public void setLng(double lng) {
		Lng = lng;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public ArrayList<PhotoBean> getPhoto() {
		return Photo;
	}

	public void setPhoto(ArrayList<PhotoBean> photo) {
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
