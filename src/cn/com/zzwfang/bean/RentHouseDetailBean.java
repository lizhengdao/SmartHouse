package cn.com.zzwfang.bean;

import java.util.ArrayList;

public class RentHouseDetailBean extends BaseBean {

	private AgentBean Agent;
	/**
	 * 图片
	 */
	private ArrayList<PhotoBean> Photo;
	
	/**
	 * 标题
	 */
	private String Title;
	
	/**
	 * 小区名称
	 */
	private String EstateName;
	
	/**
	 * 小区id
	 */
	private String EstateId;
	
	/**
	 * 几房
	 */
	private String TypeF;
	
	/**
	 * 几厅
	 */
	private String TypeT;
	
	/**
	 * 租金
	 */
	private String RentPrice;
	
	/**
	 * 面积
	 */
	private String Square;
	
	/**
	 * 楼层
	 */
	private String Floor;
	
	/**
	 * 总楼层
	 */
	private String TotalFloor;
	
	/**
	 * 朝向
	 */
	private String Direction;
	
	/**
	 * 装修
	 */
	private String Decoration;
	
	/**
	 * 年代
	 */
	private String BuildYear;
	
	private double Lat;
	
	private double Lng;
	
	private String No;
	
	private String MonthAddFollow;
	
	/**
     * 360展示  网页展示
     */
    private String Prp360;
    
    /**
     * 沙盘图
     */
    private String Estate360;
    
    
	

	public String getPrp360() {
        return Prp360;
    }

    public void setPrp360(String prp360) {
        Prp360 = prp360;
    }

    public String getEstate360() {
        return Estate360;
    }

    public void setEstate360(String estate360) {
        Estate360 = estate360;
    }

    public String getMonthAddFollow() {
        return MonthAddFollow;
    }

    public void setMonthAddFollow(String monthAddFollow) {
        MonthAddFollow = monthAddFollow;
    }

    public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getEstateName() {
		return EstateName;
	}

	public void setEstateName(String estateName) {
		EstateName = estateName;
	}

	public String getEstateId() {
		return EstateId;
	}

	public void setEstateId(String estateId) {
		EstateId = estateId;
	}

	public String getTypeF() {
		return TypeF;
	}

	public void setTypeF(String typeF) {
		TypeF = typeF;
	}

	public String getTypeT() {
		return TypeT;
	}

	public void setTypeT(String typeT) {
		TypeT = typeT;
	}

	public String getRentPrice() {
		return RentPrice;
	}

	public void setRentPrice(String rentPrice) {
		RentPrice = rentPrice;
	}

	public String getSquare() {
		return Square;
	}

	public void setSquare(String square) {
		Square = square;
	}

	public String getFloor() {
		return Floor;
	}

	public void setFloor(String floor) {
		Floor = floor;
	}

	public String getTotalFloor() {
		return TotalFloor;
	}

	public void setTotalFloor(String totalFloor) {
		TotalFloor = totalFloor;
	}

	public String getDirection() {
		return Direction;
	}

	public void setDirection(String direction) {
		Direction = direction;
	}

	public String getDecoration() {
		return Decoration;
	}

	public void setDecoration(String decoration) {
		Decoration = decoration;
	}

	public String getBuildYear() {
		return BuildYear;
	}

	public void setBuildYear(String buildYear) {
		BuildYear = buildYear;
	}

	public AgentBean getAgent() {
		return Agent;
	}

	public void setAgent(AgentBean agent) {
		Agent = agent;
	}

	public ArrayList<PhotoBean> getPhoto() {
		return Photo;
	}

	public void setPhoto(ArrayList<PhotoBean> photo) {
		Photo = photo;
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

	public String getNo() {
		return No;
	}

	public void setNo(String no) {
		No = no;
	}

	
	
}
