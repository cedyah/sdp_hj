package com.jebi.sdp.model;

public class ShipmentVO extends CustomerVO {
	public ShipmentVO() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	String ship_num;
	String cust_po;
	String zip;
	String addr1;
	String addr2;
	String phone;
	String man_num;
	String man;
	
	public String getShip_num() {
		return ship_num == null ? "" : ship_num;
	}
	public void setShip_num(String ship_num) {
		this.ship_num = ship_num == null ? "" : ship_num;
	}
	public String getCust_po() {
		return cust_po == null ? "" : cust_po;
	}
	public void setCust_po(String cust_po) {
		this.cust_po = cust_po == null ? "" : cust_po;
	}
	public String getZip() {
		return zip == null ? "" : zip;
	}
	public void setZip(String zip) {
		this.zip = zip == null ? "" : zip;
	}
	public String getAddr1() {
		return addr1 == null ? "" : addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1 == null ? "" : addr1;
	}
	public String getAddr2() {
		return addr2 == null ? "" : addr2;
	}
	public void setAddr2(String addr2) {
		this.addr2 = addr2 == null ? "" : addr2;
	}
	public String getPhone() {
		return phone == null ? "" : phone;
	}
	public void setPhone(String phone) {
		this.phone = phone == null ? "" : phone;
	}
	public String getMan_num() {
		return man_num == null ? "" : man_num;
	}
	public void setMan_num(String man_num) {
		this.man_num = man_num == null ? "" : man_num;
	}
	public String getMan() {
		return man == null ? "" : man;
	}
	public void setMan(String man) {
		this.man = man == null ? "" : man;
	}
}
