package com.android.firewall.model;

public class BlacklistInfo {
	private int id;
	private String name;
	private String phone_num;
	private int phone_mode;
	private int msg_mode;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone_num() {
		return phone_num;
	}
	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}
	public int getPhone_mode() {
		return phone_mode;
	}
	public void setPhone_mode(int phone_mode) {
		this.phone_mode = phone_mode;
	}
	public long getMsg_mode() {
		return msg_mode;
	}
	public void setMsg_mode(int msg_mode) {
		this.msg_mode = msg_mode;
	}
	

}
