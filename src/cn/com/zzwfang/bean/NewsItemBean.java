package cn.com.zzwfang.bean;

import java.util.ArrayList;

/**
 * 咨询列表Item Bean
 * @author lzd
 *
 */
public class NewsItemBean extends BaseBean {

	/**
	 * 咨询ID
	 */
	private String Id;
	
	/**
	 * Title
	 */
	private String Title;
	
	/**
	 * 图片集
	 */
//	private ArrayList<String> Images;
	private String Images;
	
	/**
	 * 内容
	 */
	private String Content;
	
	/**
	 * 发表时间
	 */
	private String AddTime;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

//	public ArrayList<String> getImages() {
//		return Images;
//	}
//
//	public void setImages(ArrayList<String> images) {
//		Images = images;
//	}
	
	

	public String getContent() {
		return Content;
	}

	public String getImages() {
		return Images;
	}

	public void setImages(String images) {
		Images = images;
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
