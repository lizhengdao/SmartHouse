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
	
	private double Lng;
	
	private double Lat;
	
	private boolean OpenMoney;
	
	

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

    public boolean isOpenMoney() {
        return OpenMoney;
    }

    public void setOpenMoney(boolean openMoney) {
        OpenMoney = openMoney;
    }

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
	
	@Override
	public boolean equals(Object o) {
	    if (o != null && o instanceof CityBean) {
	        String cityId = ((CityBean) o).getSiteId();
	        if (cityId != null && cityId.equals(this.SiteId)) {
	            return true;
	        }
	    }
	    return false;
	}
	
}
