package com.android.firewall.model;
/**
 * PBR:phone block record
 * @author fengshu
 *
 */
public class PBRInfo {
	private String name;
	private String phone_num;
	private String block_time;
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
	public String getBlock_time() {
		return block_time;
	}
	public void setBlock_time(String block_time) {
		this.block_time = block_time;
	}
	
	

}
