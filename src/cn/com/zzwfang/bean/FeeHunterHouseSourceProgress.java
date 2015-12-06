package cn.com.zzwfang.bean;

import java.util.ArrayList;

/**
 * @author lzd
 *
 */
public class FeeHunterHouseSourceProgress extends BaseBean {

	/**
	 * 状态集合
	 */
	private ArrayList<String> State;
	
	/**
	 * 当前状态
	 */
	private String SelectedState;
	
	/**
	 * 客户名称
	 */
	private String Name;
	
	/**
	 * 楼盘名称
	 */
	private String EstName;
	
	/**
	 * 时间
	 */
	private String Date;
	
	private String PublishDate;
	
	/**
	 * 操作人
	 */
	private String HandUser;

	
	public String getPublishDate() {
		return PublishDate;
	}

	public void setPublishDate(String publishDate) {
		PublishDate = publishDate;
	}

	public ArrayList<String> getState() {
		return State;
	}

	public void setState(ArrayList<String> state) {
		State = state;
	}

	public String getSelectedState() {
		return SelectedState;
	}

	public void setSelectedState(String selectedState) {
		SelectedState = selectedState;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getEstName() {
		return EstName;
	}

	public void setEstName(String estName) {
		EstName = estName;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public String getHandUser() {
		return HandUser;
	}

	public void setHandUser(String handUser) {
		HandUser = handUser;
	}
	
	
}
