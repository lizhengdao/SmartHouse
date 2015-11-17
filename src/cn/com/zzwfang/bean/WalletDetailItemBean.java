package cn.com.zzwfang.bean;

public class WalletDetailItemBean extends BaseBean {

	/**
	 * 栋号
	 */
	private String BuildName;
	
	/**
	 * 楼盘名称
	 */
	private String EstateName;
	
	/**
	 * 金额
	 */
	private String Price;
	
	/**
	 * 单元
	 */
	private String Unit;

	public String getBuildName() {
		return BuildName;
	}

	public void setBuildName(String buildName) {
		BuildName = buildName;
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

	public String getUnit() {
		return Unit;
	}

	public void setUnit(String unit) {
		Unit = unit;
	}
	
	
}
