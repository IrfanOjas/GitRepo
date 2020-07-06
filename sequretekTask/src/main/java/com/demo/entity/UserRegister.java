package com.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user")
public class UserRegister {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String number;
	private String email;
	private String otp;
	private String whatsApp;
	private String options;
	private String skype;
	private String line;
	private String hike;
	
	public String getSkype() {
		return skype;
	}
	public void setSkype(String skype) {
		this.skype = skype;
	}
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	public String getHike() {
		return hike;
	}
	public void setHike(String hike) {
		this.hike = hike;
	}
	public String getOptions() {
		return options;
	}
	public void setOptions(String option) {
		this.options = option;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	
	public String getWhatsApp() {
		return whatsApp;
	}
	public void setWhatsApp(String whatsApp) {
		this.whatsApp = whatsApp;
	}
	
    public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
}
