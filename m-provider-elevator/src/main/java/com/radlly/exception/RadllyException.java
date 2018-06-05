package com.radlly.exception;

public class RadllyException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int status;
	
	private String msg;

	public RadllyException(int status,String msg) {
        this.status = status;
        this.msg = msg;
    }
	
	public RadllyException(int status) {
        this.status = status;
    }
	

    public RadllyException(String message) {
        super(message);
    }

    public RadllyException(String message, Throwable cause) {
        super(message, cause);
    }

    public RadllyException(Throwable cause) {
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
