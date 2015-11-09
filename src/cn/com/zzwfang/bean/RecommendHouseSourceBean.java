package cn.com.zzwfang.bean;

public class RecommendHouseSourceBean extends BaseBean {

	/**
	 * 图片
	 */
	private String Photo;
    /**
     * 标题
     */
    private String Title;
    /**
     * 区域名称
     */
    private String AreaName;
    /**
     * 小区名称
     */
    private String EstateName;
    /**
     * 几房
     */
    private int TypeF;
    /**
     * 几厅
     */
    private int TypeT;
    /**
     * 面积
     */
    private double Square;
    /**
     * 价格
     */
    private double Price;
    /**
     * 房源id
     */
    private String Id;
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
	public String getAreaName() {
		return AreaName;
	}
	public void setAreaName(String areaName) {
		AreaName = areaName;
	}
	public String getEstateName() {
		return EstateName;
	}
	public void setEstateName(String estateName) {
		EstateName = estateName;
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
	public double getSquare() {
		return Square;
	}
	public void setSquare(double square) {
		Square = square;
	}
	public double getPrice() {
		return Price;
	}
	public void setPrice(double price) {
		Price = price;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
    
}
