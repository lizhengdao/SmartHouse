package cn.com.zzwfang.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;


/**
 * @author MISS-万
 *
 */
public class MessageBean extends BaseBean {

    private static final long serialVersionUID = -7566845628369120895L;

    private String id;

    private String fromUser;

    private String toUser;

    private String message;

    private String createDate;

    private long createDateLong;

    @JSONField(name = "isRead")
    private boolean read;

    private String userId;

    private String userName;

    public long getCreateDateLong() {
        return createDateLong;
    }

    public void setCreateDateLong(long createDateLong) {
        this.createDateLong = createDateLong;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
        try {
            date = sdf.parse(createDate);
            setCreateDateLong(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean isRead) {
        this.read = isRead;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
