package cn.com.zzwfang.bean;

import java.util.ArrayList;

public class ClientProgressBean extends BaseBean {

	private String EstName;
	
	private String HandUser;
	
	private String OwnerName;
	
	private String PublishDate;
	
	private String SelectedState;
	
	private ArrayList<String> State;

	public String getEstName() {
		return EstName;
	}

	public void setEstName(String estName) {
		EstName = estName;
	}

	public String getHandUser() {
		return HandUser;
	}

	public void setHandUser(String handUser) {
		HandUser = handUser;
	}

	public String getOwnerName() {
		return OwnerName;
	}

	public void setOwnerName(String ownerName) {
		OwnerName = ownerName;
	}

	public String getPublishDate() {
		return PublishDate;
	}

	public void setPublishDate(String publishDate) {
		PublishDate = publishDate;
	}

	public String getSelectedState() {
		return SelectedState;
	}

	public void setSelectedState(String selectedState) {
		SelectedState = selectedState;
	}

	public ArrayList<String> getState() {
		return State;
	}

	public void setState(ArrayList<String> state) {
		State = state;
	}
	
	
}
