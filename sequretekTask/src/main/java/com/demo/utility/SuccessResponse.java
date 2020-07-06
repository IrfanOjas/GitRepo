package com.demo.utility;

public class SuccessResponse {

	private String message;
	private String statusCode;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	@Override
	public String toString() {
		return "SuccessResponse [message=" + message + ", statusCode=" + statusCode + "]";
	}

}
