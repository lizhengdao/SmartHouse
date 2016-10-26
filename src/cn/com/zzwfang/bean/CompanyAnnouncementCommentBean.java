package cn.com.zzwfang.bean;

public class CompanyAnnouncementCommentBean extends BaseBean {

    private String AddTime;
    
    private String Address;
    
    private CompanyAnnouncementCommenterBean Commenter;
    
    private String Content;
    
    private int Floor;
    
    private String IP;

    public String getAddTime() {
        return AddTime;
    }

    public void setAddTime(String addTime) {
        AddTime = addTime;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public CompanyAnnouncementCommenterBean getCommenter() {
        return Commenter;
    }

    public void setCommenter(CompanyAnnouncementCommenterBean commenter) {
        Commenter = commenter;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public int getFloor() {
        return Floor;
    }

    public void setFloor(int floor) {
        Floor = floor;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String iP) {
        IP = iP;
    }
    
    
}
