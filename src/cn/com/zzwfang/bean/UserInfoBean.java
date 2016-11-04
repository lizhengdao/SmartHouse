package cn.com.zzwfang.bean;

/**
 * 用户登录接口返回用户信息  Bean
 * @author MISS-万
 *
 */
public class UserInfoBean extends BaseBean {

	/**
	 * 用户ID
	 */
	private String Id;
	
	/**
	 * 用户名
	 */
	private String UserName;
	
	/**
	 * 手机号
	 */
	private String Phone;
	
	
	/**
	 * 头像
	 */
	private String Photo;
	
	/**
	 * 是否绑定银行卡
	 */
	private boolean IsBindBank;
	
	/**
	 * 推荐客户数量
	 */
	private int RecommendClientsNum;
	
	/**
	 * 推荐房源数量
	 */
	private int RecommendOwners;
	
	/**
	 * 猎人赏金
	 */
	private String Bounty;
	
	/**
	 * 用户类型    0经济人，1普通会员，2赏金猎人
	 */
	private int UserType;
	
	
	private boolean Sex;
	
	public String getId() {
		return Id;
	}

	public int getUserType() {
		return UserType;
	}

	public void setUserType(int userType) {
		UserType = userType;
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

	public String getPhoto() {
		return Photo;
	}

	public void setPhoto(String photo) {
		Photo = photo;
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

	public String getBounty() {
		return Bounty;
	}

	public void setBounty(String bounty) {
		Bounty = bounty;
	}

    @Override
    public String toString() {
        return "UserInfoBean [Id=" + Id + ", UserName=" + UserName + ", Phone="
                + Phone + ", Photo=" + Photo + ", IsBindBank=" + IsBindBank
                + ", RecommendClientsNum=" + RecommendClientsNum
                + ", RecommendOwners=" + RecommendOwners + ", Bounty=" + Bounty
                + ", UserType=" + UserType + ", Sex=" + Sex + "]";
    }

//	public int getSerType() {
//		return serType;
//	}
//
//	public void setSerType(int serType) {
//		this.serType = serType;
//	}
	
	
	
}
