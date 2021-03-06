package cn.com.zzwfang.bean;

/**
 * 委托买房  自己填的  委托买房需求信息
 * @author lzd
 *
 */
public class MyDemandInfoBean extends BaseBean {

	private String EstateName;
	
	private String MaxPrice;
	
	private String MinPrice;
	
	/**
	 * 类型
	 */
	private String Type;
	/**
	 * 客源类型
	 */
	private String Intention;
	/**
	 * 买房预算
	 */
	private String Budget;
	/**
	 * 最小面积
	 */
	private String SquareMin;
	/**
	 * 最大面积
	 */
	private String SquareMax;
	/**
	 * 户型
	 */
	private String HouseType;
	/**
	 * 装修
	 */
	private String PropertyDecoration;
	/**
	 * 朝向
	 */
	private String PropertyDirection;
	/**
	 * 月供
	 */
	private String MonthlyPay;
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public String getIntention() {
		return Intention;
	}
	public void setIntention(String intention) {
		Intention = intention;
	}
	public String getBudget() {
		return Budget;
	}
	public void setBudget(String budget) {
		Budget = budget;
	}
	public String getSquareMin() {
		return SquareMin;
	}
	public void setSquareMin(String squareMin) {
		SquareMin = squareMin;
	}
	public String getSquareMax() {
		return SquareMax;
	}
	public void setSquareMax(String squareMax) {
		SquareMax = squareMax;
	}
	public String getHouseType() {
		return HouseType;
	}
	public void setHouseType(String houseType) {
		HouseType = houseType;
	}
	public String getPropertyDecoration() {
		return PropertyDecoration;
	}
	public void setPropertyDecoration(String propertyDecoration) {
		PropertyDecoration = propertyDecoration;
	}
	public String getPropertyDirection() {
		return PropertyDirection;
	}
	public void setPropertyDirection(String propertyDirection) {
		PropertyDirection = propertyDirection;
	}
	public String getMonthlyPay() {
		return MonthlyPay;
	}
	public void setMonthlyPay(String monthlyPay) {
		MonthlyPay = monthlyPay;
	}
	public String getEstateName() {
		return EstateName;
	}
	public void setEstateName(String estateName) {
		EstateName = estateName;
	}
	public String getMaxPrice() {
		return MaxPrice;
	}
	public void setMaxPrice(String maxPrice) {
		MaxPrice = maxPrice;
	}
	public String getMinPrice() {
		return MinPrice;
	}
	public void setMinPrice(String minPrice) {
		MinPrice = minPrice;
	}
	
	
	
}
