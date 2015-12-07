package cn.com.zzwfang.bean;

import java.util.ArrayList;

public class NewsDetailBean extends BaseBean {

    private String Id;
    
    private ArrayList<String> Images;
    
    private String Source;
    
    private String Content;
    
    private String AddTime;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public ArrayList<String> getImages() {
        return Images;
    }

    public void setImages(ArrayList<String> images) {
        Images = images;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getAddTime() {
        return AddTime;
    }

    public void setAddTime(String addTime) {
        AddTime = addTime;
    }
    
    
}
