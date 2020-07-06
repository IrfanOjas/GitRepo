package com.demo.entity;

public class WhatsAppEntity {
	private String toNumber;
	private String messageText;
	private String token;
	private String fromNumber;
	private String messagePrefix;
	private String apiUrl;
	private String messageTransfer;
	
	public String getToNumber() {
		return toNumber;
	}
	public void setToNumber(String toNumber) {
		this.toNumber = toNumber;
	}
	public String getMessageText() {
		return messageText;
	}
	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getFromNumber() {
		return fromNumber;
	}
	public void setFromNumber(String fromNumber) {
		this.fromNumber = fromNumber;
	}
	
	public String getMessagePrefix() {
		return messagePrefix;
	}
	public void setMessagePrefix(String messagePrefix) {
		this.messagePrefix = messagePrefix;
	}
	public String getApiUrl() {
		return apiUrl;
	}
	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}
	public String getMessageTransfer() {
		return messageTransfer;
	}
	public void setMessageTransfer(String messageTransfer) {
		this.messageTransfer = messageTransfer;
	}
	
	
}
