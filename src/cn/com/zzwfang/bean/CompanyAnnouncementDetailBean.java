package cn.com.zzwfang.bean;

import java.util.ArrayList;

public class CompanyAnnouncementDetailBean extends BaseBean {

    private String AddTime;
    
    private String Content;
    
    private String Id;
    
    private String Source;
    
    private String Title;
    
    private ArrayList<CompanyAnnouncementCommentBean> CommentArr;

    public String getAddTime() {
        return AddTime;
    }

    public void setAddTime(String addTime) {
        AddTime = addTime;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public ArrayList<CompanyAnnouncementCommentBean> getCommentArr() {
        return CommentArr;
    }

    public void setCommentArr(ArrayList<CompanyAnnouncementCommentBean> commentArr) {
        CommentArr = commentArr;
    }
    
}
