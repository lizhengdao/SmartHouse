package cn.com.zzwfang.bean;

import java.util.ArrayList;

/**
 * 小区成交历史 Item  Bean
 * @author lzd
 *
 */
public class ResidentialTransactionHistoryBean extends BaseBean {

	private String AgentId;
	
	private String AgentName;
	
	private String Direction;
	
	private String Floor;
	
	private String Lat;
	
	private String Lng;
	
	private ArrayList<PhotoBean> Photo;
	
	private String Price;
	
	private String PropertyId;
	
	private String SaleDate;
	
	private String Square;
	
	private String TotalFloor;
	
	private String TypeF;
	
	private String TypeT;

	public String getAgentId() {
		return AgentId;
	}

	public void setAgentId(String agentId) {
		AgentId = agentId;
	}

	public String getAgentName() {
		return AgentName;
	}

	public void setAgentName(String agentName) {
		AgentName = agentName;
	}

	public String getDirection() {
		return Direction;
	}

	public void setDirection(String direction) {
		Direction = direction;
	}

	public String getFloor() {
		return Floor;
	}

	public void setFloor(String floor) {
		Floor = floor;
	}

	public String getLat() {
		return Lat;
	}

	public void setLat(String lat) {
		Lat = lat;
	}

	public String getLng() {
		return Lng;
	}

	public void setLng(String lng) {
		Lng = lng;
	}

	public ArrayList<PhotoBean> getPhoto() {
		return Photo;
	}

	public void setPhoto(ArrayList<PhotoBean> photo) {
		Photo = photo;
	}

	public String getPrice() {
		return Price;
	}

	public void setPrice(String price) {
		Price = price;
	}

	public String getPropertyId() {
		return PropertyId;
	}

	public void setPropertyId(String propertyId) {
		PropertyId = propertyId;
	}

	public String getSaleDate() {
		return SaleDate;
	}

	public void setSaleDate(String saleDate) {
		SaleDate = saleDate;
	}

	public String getSquare() {
		return Square;
	}

	public void setSquare(String square) {
		Square = square;
	}

	public String getTotalFloor() {
		return TotalFloor;
	}

	public void setTotalFloor(String totalFloor) {
		TotalFloor = totalFloor;
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
	
	
}
