package cn.com.zzwfang.bean;

public class SecondHandHouseBean extends BaseBean {

	/**
	 * 房源ID
	 */
	private String Id;
	
	/**
	 * 图片
	 */
	private String Photo;
	
	/**
	 * 标题
	 */
	private String Title;
	
	/**
	 * 几室
	 */
	private int TypeF;
	
	/**
	 * 几厅
	 */
	private int TypeT;
	
	/**
	 * 面积
	 */
	private float Square;
	
	/**
	 * 楼层
	 */
	private int Floor;
	
	/**
	 * 总楼层
	 */
	private int TotalFloor;
	
	/**
	 * 区域名称
	 */
	private String AeraName;
	
	/**
	 * 朝向
	 */
	private String Direction;
	
	/**
	 * 销售价格
	 */
	private float Price;
	
	/**
	 * 发布时间
	 */
	private String AddTime;
	
	/**
	 * 楼盘名称
	 */
	private String EstName;
	
	/**
	 * 结构（高中低层）
	 */
	private String Structure;
	
	/**
	 * 经度
	 */
	private long Lng;
	
	/**
	 * 纬度
	 */
	private long Lat;

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

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public int getTypeF() {
		return TypeF;
	}

	public void setTypeF(int typeF) {
		TypeF = typeF;
	}

	public int getTypeT() {
		return TypeT;
	}

	public void setTypeT(int typeT) {
		TypeT = typeT;
	}

	public float getSquare() {
		return Square;
	}

	public void setSquare(float square) {
		Square = square;
	}

	public int getFloor() {
		return Floor;
	}

	public void setFloor(int floor) {
		Floor = floor;
	}

	public int getTotalFloor() {
		return TotalFloor;
	}

	public void setTotalFloor(int totalFloor) {
		TotalFloor = totalFloor;
	}

	public String getAeraName() {
		return AeraName;
	}

	public void setAeraName(String aeraName) {
		AeraName = aeraName;
	}

	public String getDirection() {
		return Direction;
	}

	public void setDirection(String direction) {
		Direction = direction;
	}

	public float getPrice() {
		return Price;
	}

	public void setPrice(float price) {
		Price = price;
	}

	public String getAddTime() {
		return AddTime;
	}

	public void setAddTime(String addTime) {
		AddTime = addTime;
	}

	public String getEstName() {
		return EstName;
	}

	public void setEstName(String estName) {
		EstName = estName;
	}

	public String getStructure() {
		return Structure;
	}

	public void setStructure(String structure) {
		Structure = structure;
	}

	public long getLng() {
		return Lng;
	}

	public void setLng(long lng) {
		Lng = lng;
	}

	public long getLat() {
		return Lat;
	}

	public void setLat(long lat) {
		Lat = lat;
	}
	
	
	
}
