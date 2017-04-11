package cn.com.zzwfang.bean;


import com.alibaba.fastjson.JSON;

public class Result extends BaseBean {

	
	private static final long serialVersionUID = 6810038430975058375L;

	/**
	 *   状态码   1为成功   0为失败
	 */
    private String code;
    
    /**
	 *   错误信息
	 */
    private String message;
    
    /**
     * 总页数（分页时用）
     */
    
    private int total;
    /**
	 *   返回结果
	 */
	private String data;
    
    public static Result parseToReuslt(String json){
        try{
            return JSON.parseObject(json, Result.class);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public String toJSON(){
        try{
            return JSON.toJSONString(this);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    
    public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Result [code=" + code + ", message=" + message + ", total="
				+ total + ", data=" + data + "]";
	}
	
	
    
}
