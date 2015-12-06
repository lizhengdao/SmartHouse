package cn.com.zzwfang.bean;

public class FeeHunterRecommendClientBean extends BaseBean {

	private static final long serialVersionUID = -1236347681005886495L;

	/**
     * 客户ID
     */
    private String Id;
    
    /**
     * 客户名称
     */
    private String Name;
    
    /**
     * 客户电话
     */
    private String Tel;
    
    /**
     * 楼盘名称
     */
    private String EstName;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTel() {
        return Tel;
    }

    public void setTel(String tel) {
        Tel = tel;
    }

    public String getEstName() {
        return EstName;
    }

    public void setEstName(String estName) {
        EstName = estName;
    }
    
    
}
