package cn.com.zzwfang.bean;

/**
 * 赏金猎人  我的客户Bean
 * @author lzd
 *
 */
public class FeeHunterMyCustomerConditionBean extends BaseBean {

	private String Id;
	
	private String Name;
	
	private String ParentId;

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

	public String getParentId() {
		return ParentId;
	}

	public void setParentId(String parentId) {
		ParentId = parentId;
	}
	
	
}
