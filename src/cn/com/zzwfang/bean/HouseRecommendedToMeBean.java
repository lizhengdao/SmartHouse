package cn.com.zzwfang.bean;

public class HouseRecommendedToMeBean extends BaseBean {
	
	/**
	 * 房源ID
	 */
	private String Id;
	
	/**
	 * 图片
	 */
	private String Photo;
	
	/**
	 * 楼盘名称
	 */
	private String EstateName;
	
	/**
	 * 价格
	 */
	private String Price;
	
	private String HouseType;
	
	/**
	 * 面积
	 */
	private String Square;
	
	/**
	 * 接盘时间
	 */
	private String TrustDate;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getPhoto() {
		return Photo;
	}

	public void setPhoto(String photo) {
		Photo = photo;
	}
	

	public String getHouseType() {
		return HouseType;
	}

	public void setHouseType(String houseType) {
		HouseType = houseType;
	}

	public String getEstateName() {
		return EstateName;
	}

	public void setEstateName(String estateName) {
		EstateName = estateName;
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

	public String getTrustDate() {
		return TrustDate;
	}

	public void setTrustDate(String trustDate) {
		TrustDate = trustDate;
	}
	
	

}
