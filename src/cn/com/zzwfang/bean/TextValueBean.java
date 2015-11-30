package cn.com.zzwfang.bean;

public class TextValueBean extends BaseBean {

	/**
	 * 显示
	 */
	private String Text;
	
	/**
	 * 值
	 */
	private String Value;
	
	private boolean selected;
	
	

	public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getText() {
		return Text;
	}

	public void setText(String text) {
		Text = text;
	}

	public String getValue() {
		return Value;
	}

	public void setValue(String value) {
		Value = value;
	}
	
	
}
