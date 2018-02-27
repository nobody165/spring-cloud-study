package com.radlly.exception;

public class BusinessException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int status;
	
	private String msg;

	public BusinessException(int status,String msg) {
        this.status = status;
        this.msg = msg;
    }
	
	public BusinessException(int status) {
        this.status = status;
    }
	

    public BusinessException(String messageKey) {
        super(messageKey);
    }

    public BusinessException(String messageKey, Throwable cause) {
        super(messageKey, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
    
    

}
