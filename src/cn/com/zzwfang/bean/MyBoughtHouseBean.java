package cn.com.zzwfang.bean;

/**
 * 我的购屋列表
 * @author lzd
 *
 */
public class MyBoughtHouseBean extends BaseBean {

	/**
	 * 房源ID
	 */
	private String Id;
	
	/**
	 * 图片
	 */
	private String Photo;
	
	/**
	 * 标题
	 */
	private String Title;
	
	/**
	 * 户型-房
	 */
	private String TypeF;
	
	/**
	 * 户型-厅
	 */
	private String TypeT;
	
	/**
	 * 户型-卫
	 */
	private String TypeW;
	
	/**
	 * 面积
	 */
	private String Square;
	
	/**
	 * 朝向
	 */
	private String Direction;
	
	/**
	 * 发布时间
	 */
	private String PublishDate;
	
	/**
	 * 价格
	 */
	private String Price;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getPhoto() {
		return Photo;
	}

	public void setPhoto(String photo) {
		Photo = photo;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getTypeF() {
		return TypeF;
	}

	public void setTypeF(String typeF) {
		TypeF = typeF;
	}

	public String getTypeT() {
		return TypeT;
	}

	public void setTypeT(String typeT) {
		TypeT = typeT;
	}

	public String getTypeW() {
		return TypeW;
	}

	public void setTypeW(String typeW) {
		TypeW = typeW;
	}

	public String getSquare() {
		return Square;
	}

	public void setSquare(String square) {
		Square = square;
	}

	public String getDirection() {
		return Direction;
	}

	public void setDirection(String direction) {
		Direction = direction;
	}

	public String getPublishDate() {
		return PublishDate;
	}

	public void setPublishDate(String publishDate) {
		PublishDate = publishDate;
	}

	public String getPrice() {
		return Price;
	}

	public void setPrice(String price) {
		Price = price;
	}
	
	
}
