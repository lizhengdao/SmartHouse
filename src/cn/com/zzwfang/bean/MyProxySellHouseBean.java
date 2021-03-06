package cn.com.zzwfang.bean;

public class MyProxySellHouseBean extends BaseBean {

	/**
	 * 朝向
	 */
	private String Direction;
	
	/**
	 * 经纪人ID
	 */
	private String EmployeId;
	
	/**
	 * 图片地址
	 */
	private String ImagePath;
	
	private boolean Lock;
	
	/**
	 * 价格
	 */
	private String Price;
	
	/**
	 * 发布时间
	 */
	private String PublishDate;
	
	/**
	 * 面积
	 */
	private String Square;
	
    private int Status;
	
	/**
	 * 房源标题
	 */
	private String Title;
	
	/**
	 * 几房
	 */
	private String TypeF;
	
	/**
	 * 几厅
	 */
	private String TypeT;
	
	/**
	 * 几卫
	 */
	private String TypeW;
	
	/**
	 * 几阳
	 */
	private String TypeY;
	
	/**
	 * 房源id
	 */
	private String id;
	

	public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        this.Status = status;
    }

    public boolean isLock() {
		return Lock;
	}

	public void setLock(boolean lock) {
		Lock = lock;
	}

	public String getDirection() {
		return Direction;
	}

	public void setDirection(String direction) {
		Direction = direction;
	}

	public String getEmployeId() {
		return EmployeId;
	}

	public void setEmployeId(String employeId) {
		EmployeId = employeId;
	}

	public String getImagePath() {
		return ImagePath;
	}

	public void setImagePath(String imagePath) {
		ImagePath = imagePath;
	}

	public String getPrice() {
		return Price;
	}

	public void setPrice(String price) {
		Price = price;
	}

	public String getPublishDate() {
		return PublishDate;
	}

	public void setPublishDate(String publishDate) {
		PublishDate = publishDate;
	}

	public String getSquare() {
		return Square;
	}

	public void setSquare(String square) {
		Square = square;
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

	public String getTypeY() {
		return TypeY;
	}

	public void setTypeY(String typeY) {
		TypeY = typeY;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	
}
