package cn.com.zzwfang.bean;

/**
 * @author MISS-万
 *
 */
public class UserInfoBean extends BaseBean {

	private String Id;
	
	private String UserName;
	
	private String Phone;
	
	private String Email;
	
	private String Province;
	
	private String City;
	
	private String Photo;
	
	private String Profession;
	
	private boolean IsBindBank;
	
	private int RecommendClientsNum;
	
	private int RecommendOwners;
	
	private int Bounty;
	
	private int serType;
	
	private boolean Sex;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getProvince() {
		return Province;
	}

	public void setProvince(String province) {
		Province = province;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	public String getPhoto() {
		return Photo;
	}

	public void setPhoto(String photo) {
		Photo = photo;
	}

	public String getProfession() {
		return Profession;
	}

	public void setProfession(String profession) {
		Profession = profession;
	}

	public boolean isSex() {
		return Sex;
	}

	public void setSex(boolean sex) {
		Sex = sex;
	}

	public boolean isIsBindBank() {
		return IsBindBank;
	}

	public void setIsBindBank(boolean isBindBank) {
		IsBindBank = isBindBank;
	}

	public int getRecommendClientsNum() {
		return RecommendClientsNum;
	}

	public void setRecommendClientsNum(int recommendClientsNum) {
		RecommendClientsNum = recommendClientsNum;
	}

	public int getRecommendOwners() {
		return RecommendOwners;
	}

	public void setRecommendOwners(int recommendOwners) {
		RecommendOwners = recommendOwners;
	}

	public int getBounty() {
		return Bounty;
	}

	public void setBounty(int bounty) {
		Bounty = bounty;
	}

	public int getSerType() {
		return serType;
	}

	public void setSerType(int serType) {
		this.serType = serType;
	}
	
	
	
}
