package cn.com.zzwfang.bean;

import java.util.ArrayList;

/**
 * 赏金猎人  我的客户Bean
 * @author lzd
 *
 */
public class FeeHunterMyCustomerConditionBean extends BaseBean {

	private String Id;
	
	private String Name;
	
	private ArrayList<FeeHunterMyCustomerConditionBean> Children;

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

	public ArrayList<FeeHunterMyCustomerConditionBean> getChildren() {
		return Children;
	}

	public void setChildren(ArrayList<FeeHunterMyCustomerConditionBean> children) {
		Children = children;
	}

	
	
	
}
