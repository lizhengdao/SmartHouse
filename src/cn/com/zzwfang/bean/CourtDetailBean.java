package cn.com.zzwfang.bean;

import java.util.ArrayList;

public class CourtDetailBean extends BaseBean {

    
    /**
     * 经纪人信息
     */
    private AgentBean Agent;
    
    /**
     * 绿化率
     */
    private String GreeningRate;
    
    private double Lat;
    
    private double Lng;
    
    private String Name;
    
    /**
     * 图片集
     */
    private ArrayList<PhotoBean> Photo;
    
    /**
     * 容积率
     */
    private String PlotRatio;
    
    /**
     * 房源数
     */
    private String PropertyNum;
    
    /**
     * 房屋类型
     */
    private String PropertyType;
    
    /**
     * 楼栋数
     */
    private String UintNum;
    
    /**
     * 建造年代
     */
    private String buildYear;
    
    /**
     * 建筑类型
     */
    private String buildType;
    
    /**
     * 物业费
     */
    private String MgtPrice;

    public AgentBean getAgent() {
        return Agent;
    }

    public void setAgent(AgentBean agent) {
        Agent = agent;
    }

    public String getGreeningRate() {
        return GreeningRate;
    }

    public void setGreeningRate(String greeningRate) {
        GreeningRate = greeningRate;
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

    public ArrayList<PhotoBean> getPhoto() {
        return Photo;
    }

    public void setPhoto(ArrayList<PhotoBean> photo) {
        Photo = photo;
    }

    public String getPlotRatio() {
        return PlotRatio;
    }

    public void setPlotRatio(String plotRatio) {
        PlotRatio = plotRatio;
    }

    public String getPropertyNum() {
        return PropertyNum;
    }

    public void setPropertyNum(String propertyNum) {
        PropertyNum = propertyNum;
    }

    public String getPropertyType() {
        return PropertyType;
    }

    public void setPropertyType(String propertyType) {
        PropertyType = propertyType;
    }

    public String getUintNum() {
        return UintNum;
    }

    public void setUintNum(String uintNum) {
        UintNum = uintNum;
    }

    public String getBuildYear() {
        return buildYear;
    }

    public void setBuildYear(String buildYear) {
        this.buildYear = buildYear;
    }

    public String getBuildType() {
        return buildType;
    }

    public void setBuildType(String buildType) {
        this.buildType = buildType;
    }

    public String getMgtPrice() {
        return MgtPrice;
    }

    public void setMgtPrice(String mgtPrice) {
        MgtPrice = mgtPrice;
    }
    
    
    
}
