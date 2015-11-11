package cn.com.zzwfang.bean;

import java.util.ArrayList;

public class NewHouseBean extends BaseBean {

	private String Address;
	
	private String Id;
	
	private ArrayList<NewHouseLabelBean> Label;
	
	private long Lat;
	
	private long Lng;
	
	private String Name;
	
	private String Photo;
	
	private String Price;
	
	private String Square;

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public ArrayList<NewHouseLabelBean> getLabel() {
		return Label;
	}

	public void setLabel(ArrayList<NewHouseLabelBean> label) {
		Label = label;
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

	public String getPhoto() {
		return Photo;
	}

	public void setPhoto(String photo) {
		Photo = photo;
	}

	public String getPrice() {
		return Price;
	}

	public void setPrice(String price) {
		Price = price;
	}

	public String getSquare() {
		return Square;
	}

	public void setSquare(String square) {
		Square = square;
	}
	
	
	
}
