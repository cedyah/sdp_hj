package com.jebi.sdp.model;

public class BasketItemVO extends ItemVO{
	public BasketItemVO() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	String gubun;
	String cust_po;
	String price;
	String qty_basket;
	String capacity;
	
	public String getGubun() {
		return gubun == null ? "" : gubun;
	}
	public void setGubun(String gubun) {
		this.gubun = gubun == null ? "" : gubun;
	}
	public String getCust_po() {
		return cust_po == null ? "" : cust_po;
	}
	public void setCust_po(String cust_po) {
		this.cust_po = cust_po == null ? "" : cust_po;
	}
	public String getPrice() {
		return price == null ? "" : price;
	}
	public void setPrice(String price) {
		this.price = price == null ? "" : price;
	}
	public String getQty_basket() {
		return qty_basket == null ? "" : qty_basket;
	}
	public void setQty_basket(String qty_basket) {
		this.qty_basket = qty_basket == null ? "" : qty_basket;
	}
	public String getCapacity() {
		return capacity == null ? "" : capacity;
	}
	public void setCapacity(String capacity) {
		this.capacity = capacity == null ? "" : capacity;
	}

}
