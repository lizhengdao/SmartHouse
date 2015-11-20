package cn.com.zzwfang.bean;

/**
 * 搜房列表Item Bean
 * @author lzd
 *
 */
public class SearchHouseItemBean extends BaseBean {

	/**
	 * 楼盘Id
	 */
	private String Id;
	
	/**
	 * 楼盘名称
	 */
	private String Name;
	
	/**
	 * 经度
	 */
	private double Lng;
	
	/**
	 * 纬度
	 */
	private double Lat;
	
	/**
	 * 图片
	 */
	private String Photo;
	
	/**
	 * 平均售价
	 */
	private String PrpAvg;
	
	/**
	 * 销售总量
	 */
	private String PrpCount;
	
	/**
	 * 租房单位-元/（年/月）
	 */
	private String RentUnitName;
	
	/**
	 * 出售/出租
	 */
	private String Trade;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public double getLng() {
		return Lng;
	}

	public void setLng(double lng) {
		Lng = lng;
	}

	public double getLat() {
		return Lat;
	}

	public void setLat(double lat) {
		Lat = lat;
	}

	public String getPhoto() {
		return Photo;
	}

	public void setPhoto(String photo) {
		Photo = photo;
	}

	public String getPrpAvg() {
		return PrpAvg;
	}

	public void setPrpAvg(String prpAvg) {
		PrpAvg = prpAvg;
	}

	public String getPrpCount() {
		return PrpCount;
	}

	public void setPrpCount(String prpCount) {
		PrpCount = prpCount;
	}

	public String getRentUnitName() {
		return RentUnitName;
	}

	public void setRentUnitName(String rentUnitName) {
		RentUnitName = rentUnitName;
	}

	public String getTrade() {
		return Trade;
	}

	public void setTrade(String trade) {
		Trade = trade;
	}
	
	
}
