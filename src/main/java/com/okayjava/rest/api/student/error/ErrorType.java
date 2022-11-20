package com.okayjava.rest.api.student.error;

public class ErrorType {

	private String time;
	private String status;
	private String message;
	
	
	public ErrorType() {
	}
	public ErrorType(String time, String status, String message) {
		super();
		this.time = time;
		this.status = status;
		this.message = message;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
