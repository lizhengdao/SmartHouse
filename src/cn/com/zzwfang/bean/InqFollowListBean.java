package cn.com.zzwfang.bean;

public class InqFollowListBean extends BaseBean {

	private String Tel;
	
	private String Head;
	
	private String Name;
	
	private String AgentId;
	
	private String Photo;

	public String getTel() {
		return Tel;
	}

	public void setTel(String tel) {
		Tel = tel;
	}

	public String getHead() {
		return Head;
	}

	public void setHead(String head) {
		Head = head;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

    public String getAgentId() {
        return AgentId;
    }

    public void setAgentId(String agentId) {
        AgentId = agentId;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
        this.Head = photo;
    }
	
}
