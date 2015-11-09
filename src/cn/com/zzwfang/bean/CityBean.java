package cn.com.zzwfang.bean;

public class CityBean extends BaseBean {

	/**
	 * 城市名称
	 */
	private String Name;
	
	/**
	 * 城市编号
	 */
	private String SiteId;

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getSiteId() {
		return SiteId;
	}

	public void setSiteId(String siteId) {
		SiteId = siteId;
	}
	
	
}
