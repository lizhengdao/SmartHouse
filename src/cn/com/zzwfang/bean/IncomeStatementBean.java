package cn.com.zzwfang.bean;

/**
 * 收支明细 Bean
 * @author MISS-万
 *
 */
public class IncomeStatementBean extends BaseBean {

	
	private String Price;
	
	private String Type;
	
	private boolean Invoice;
	
	private String Date;

	public String getPrice() {
		return Price;
	}

	public void setPrice(String price) {
		Price = price;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public boolean isInvoice() {
		return Invoice;
	}

	public void setInvoice(boolean invoice) {
		Invoice = invoice;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}
	
	
}
