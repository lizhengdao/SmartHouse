package cn.com.zzwfang.bean;

/**
 * 地图找房  Bean
 * @author lzd
 *
 */
public class MapFindHouseBean extends BaseBean {

	/**
	 * 区域Id
	 */
	private String Id;
	
	/**
	 * 纬度
	 */
	private double Lat;
	
	/**
	 * 经度
	 */
	private double Lng;
	
	/**
	 * 区域名称
	 */
	private String Name;
	
	/**
	 * 区域平均
	 */
	private String PrpAvg;
	
	/**
	 * 区域总量
	 */
	private String PrpCount;
	
	/**
	 * 单位
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
