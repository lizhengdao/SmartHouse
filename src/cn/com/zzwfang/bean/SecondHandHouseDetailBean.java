package cn.com.zzwfang.bean;

import java.util.ArrayList;

public class SecondHandHouseDetailBean extends BaseBean {

	/**
	 * 图片
	 */
	private ArrayList<PhotoBean> Photo;
	
	/**
	 * 标题
	 */
	private String Title;
	
	/**
	 * 销售价格
	 */
	private String Price;
	
	/**
	 * 几房
	 */
	private String TypeF;
	
	/**
	 * 几厅
	 */
	private String TypeT;
	
	/**
	 * 面积
	 */
	private String Square;
	
	/**
	 * 标签
	 */
	private ArrayList<String> Label;
	
	/**
	 * 单价
	 */
	private String UnitPrice;
	
	/**
	 * 首付
	 */
	private String PartialPrice;
	
	/**
	 * 月供
	 */
	private String MonthlyPayment;
	
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
	 * 小区名称
	 */
	private String EstateName;
	
	/**
	 * 小区Id
	 */
	private String EstateId;
	
	/**
	 * 带看记录
	 */
	private ArrayList<InqFollowListBean> InqFollowList;
	
	/**
	 * 经纪人
	 */
	private AgentBean Agent;
	
	/**
	 * 360展示  网页展示
	 */
	private String Prp360;
	
	/**
	 * 沙盘图
	 */
	private String Estate360;
	
	private double Lat;
	
	private double Lng;
	
	private String MonthAddFollow;
	
	private String TotalFollow;
	
	private String Share;
	
	
	

	public String getShare() {
        return Share;
    }

    public void setShare(String share) {
        Share = share;
    }

    public String getTotalFollow() {
		return TotalFollow;
	}

	public void setTotalFollow(String totalFollow) {
		TotalFollow = totalFollow;
	}

	public String getMonthAddFollow() {
		return MonthAddFollow;
	}

	public void setMonthAddFollow(String monthAddFollow) {
		MonthAddFollow = monthAddFollow;
	}

	public ArrayList<PhotoBean> getPhoto() {
		return Photo;
	}

	public void setPhoto(ArrayList<PhotoBean> photo) {
		Photo = photo;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getPrice() {
		return Price;
	}

	public void setPrice(String price) {
		Price = price;
	}

	public String getTypeF() {
		return TypeF;
	}

	public void setTypeF(String typeF) {
		TypeF = typeF;
	}

	public String getSquare() {
		return Square;
	}

	public void setSquare(String square) {
		Square = square;
	}


	public String getUnitPrice() {
		return UnitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		UnitPrice = unitPrice;
	}

	public String getPartialPrice() {
		return PartialPrice;
	}

	public void setPartialPrice(String partialPrice) {
		PartialPrice = partialPrice;
	}

	public String getMonthlyPayment() {
		return MonthlyPayment;
	}

	public void setMonthlyPayment(String monthlyPayment) {
		MonthlyPayment = monthlyPayment;
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

	public String getTypeT() {
		return TypeT;
	}

	public void setTypeT(String typeT) {
		TypeT = typeT;
	}

	public ArrayList<String> getLabel() {
		return Label;
	}

	public void setLabel(ArrayList<String> label) {
		Label = label;
	}

	public ArrayList<InqFollowListBean> getInqFollowList() {
		return InqFollowList;
	}

	public void setInqFollowList(ArrayList<InqFollowListBean> inqFollowList) {
		InqFollowList = inqFollowList;
	}

	public AgentBean getAgent() {
		return Agent;
	}

	public void setAgent(AgentBean agent) {
		Agent = agent;
	}

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
	

}
