package cn.com.zzwfang.bean;

/**
 * @author lzd
 *
 */
public class SearchHouseArtifactResultBean extends BaseBean {

	/**
	 * 代理人Id
	 */
	private String Id;
	
	/**
	 * 等级
	 */
	private String Level;
	/**
	 * 头像
	 */
	private String Photo;
	/**
	 * 名称
	 */
	private String Name;
	/**
	 * 电话
	 */
	private String Phone;
	/**
	 * 浏览量
	 */
	private int PairCount;
	
	/**
	 * 租房成交量
	 */
	private String RentNum;
	
	/**
	 * 二手房成交量
	 */
	private String SecondNum;
	
	
	
	public String getLevel() {
		return Level;
	}
	public void setLevel(String level) {
		Level = level;
	}
	public String getRentNum() {
		return RentNum;
	}
	public void setRentNum(String rentNum) {
		RentNum = rentNum;
	}
	public String getSecondNum() {
		return SecondNum;
	}
	public void setSecondNum(String secondNum) {
		SecondNum = secondNum;
	}
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
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}
	public int getPairCount() {
		return PairCount;
	}
	public void setPairCount(int pairCount) {
		PairCount = pairCount;
	}
	
	
}
