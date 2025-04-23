package com.jebi.sdp.model;

public class PurchaseVO extends CustomerVO{

	public PurchaseVO() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	String sale_dt;
	String sale_no;
	String seq;
	String item_cd;
	String item_nm;
	String ut_a;
	String ut_b;
	String qty;
	String up;
	String amt;
	String dely_place;
	
	public String getSale_dt() {
		return sale_dt == null ? "" : sale_dt;
	}
	public void setSale_dt(String sale_dt) {
		this.sale_dt = sale_dt == null ? "" : sale_dt;
	}
	public String getSale_no() {
		return sale_no == null ? "" : sale_no;
	}
	public void setSale_no(String sale_no) {
		this.sale_no = sale_no == null ? "" : sale_no;
	}
	public String getSeq() {
		return seq == null ? "" : seq;
	}
	public void setSeq(String seq) {
		this.seq = seq == null ? "" : seq;
	}
	public String getItem_cd() {
		return item_cd == null ? "" : item_cd;
	}
	public void setItem_cd(String item_cd) {
		this.item_cd = item_cd == null ? "" : item_cd;
	}
	public String getItem_nm() {
		return item_nm == null ? "" : item_nm;
	}
	public void setItem_nm(String item_nm) {
		this.item_nm = item_nm == null ? "" : item_nm;
	}
	public String getUt_a() {
		return ut_a == null ? "" : ut_a;
	}
	public void setUt_a(String ut_a) {
		this.ut_a = ut_a == null ? "" : ut_a;
	}
	public String getUt_b() {
		return ut_b == null ? "" : ut_b;
	}
	public void setUt_b(String ut_b) {
		this.ut_b = ut_b == null ? "" : ut_b;
	}
	public String getQty() {
		return qty == null ? "" : qty;
	}
	public void setQty(String qty) {
		this.qty = qty == null ? "" : qty;
	}
	public String getUp() {
		return up == null ? "" : up;
	}
	public void setUp(String up) {
		this.up = up == null ? "" : up;
	}
	public String getAmt() {
		return amt == null ? "" : amt;
	}
	public void setAmt(String amt) {
		this.amt = amt == null ? "" : amt;
	}
	public String getDely_place() {
		return dely_place == null ? "" : dely_place;
	}
	public void setDely_place(String dely_place) {
		this.dely_place = dely_place == null ? "" : dely_place;
	}
}
