package com.jebi.sdp.model;

public class CreditSubVO extends CustomerVO{

	public CreditSubVO() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	//프로시저 파라미터
	String arg_cust_cd;
	String arg_yyyymm;
	String arg_type;
	   
	//type1
	String ord_dt;
	String sale_no;
	String seq;
	String item_cd;
	String pummyeong;
	String sale_inv_unit;
	String sale_inv_qty;
	String price;
	String amt;
	String vat;
	String tot_amt;
	
	//type2
	String ilja;
	String jeogyo;
	String geumaeg;
	
	//type3
	String month;
//	String pummyeong;	//type1과 중복되서 삭제
	String amt_etc;
	String vat_etc;
	String colet_amt_etc;
	String jango_etc;
	
	public String getArg_cust_cd() {
		return arg_cust_cd == null ? "" : arg_cust_cd;
	}
	public void setArg_cust_cd(String arg_cust_cd) {
		this.arg_cust_cd = arg_cust_cd == null ? "" : arg_cust_cd;
	}
	public String getArg_yyyymm() {
		return arg_yyyymm == null ? "" : arg_yyyymm;
	}
	public void setArg_yyyymm(String arg_yyyymm) {
		this.arg_yyyymm = arg_yyyymm == null ? "" : arg_yyyymm;
	}
	public String getArg_type() {
		return arg_type == null ? "" : arg_type;
	}
	public void setArg_type(String arg_type) {
		this.arg_type = arg_type == null ? "" : arg_type;
	}
	public String getOrd_dt() {
		return ord_dt == null ? "" : ord_dt;
	}
	public void setOrd_dt(String ord_dt) {
		this.ord_dt = ord_dt == null ? "" : ord_dt;
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
	public String getPummyeong() {
		return pummyeong == null ? "" : pummyeong;
	}
	public void setPummyeong(String pummyeong) {
		this.pummyeong = pummyeong == null ? "" : pummyeong;
	}
	public String getSale_inv_unit() {
		return sale_inv_unit == null ? "" : sale_inv_unit;
	}
	public void setSale_inv_unit(String sale_inv_unit) {
		this.sale_inv_unit = sale_inv_unit == null ? "" : sale_inv_unit;
	}
	public String getSale_inv_qty() {
		return sale_inv_qty == null ? "" : sale_inv_qty;
	}
	public void setSale_inv_qty(String sale_inv_qty) {
		this.sale_inv_qty = sale_inv_qty == null ? "" : sale_inv_qty;
	}
	public String getPrice() {
		return price == null ? "" : price;
	}
	public void setPrice(String price) {
		this.price = price == null ? "" : price;
	}
	public String getAmt() {
		return amt == null ? "" : amt;
	}
	public void setAmt(String amt) {
		this.amt = amt == null ? "" : amt;
	}
	public String getVat() {
		return vat == null ? "" : vat;
	}
	public void setVat(String vat) {
		this.vat = vat == null ? "" : vat;
	}
	public String getTot_amt() {
		return tot_amt == null ? "" : tot_amt;
	}
	public void setTot_amt(String tot_amt) {
		this.tot_amt = tot_amt == null ? "" : tot_amt;
	}
	public String getIlja() {
		return ilja == null ? "" : ilja;
	}
	public void setIlja(String ilja) {
		this.ilja = ilja == null ? "" : ilja;
	}
	public String getJeogyo() {
		return jeogyo == null ? "" : jeogyo;
	}
	public void setJeogyo(String jeogyo) {
		this.jeogyo = jeogyo == null ? "" : jeogyo;
	}
	public String getGeumaeg() {
		return geumaeg == null ? "" : geumaeg;
	}
	public void setGeumaeg(String geumaeg) {
		this.geumaeg = geumaeg == null ? "" : geumaeg;
	}
	public String getMonth() {
		return month == null ? "" : month;
	}
	public void setMonth(String month) {
		this.month = month == null ? "" : month;
	}
	public String getAmt_etc() {
		return amt_etc == null ? "" : amt_etc;
	}
	public void setAmt_etc(String amt_etc) {
		this.amt_etc = amt_etc == null ? "" : amt_etc;
	}
	public String getVat_etc() {
		return vat_etc == null ? "" : vat_etc;
	}
	public void setVat_etc(String vat_etc) {
		this.vat_etc = vat_etc == null ? "" : vat_etc;
	}
	public String getColet_amt_etc() {
		return colet_amt_etc == null ? "" : colet_amt_etc;
	}
	public void setColet_amt_etc(String colet_amt_etc) {
		this.colet_amt_etc = colet_amt_etc == null ? "" : colet_amt_etc;
	}
	public String getJango_etc() {
		return jango_etc == null ? "" : jango_etc;
	}
	public void setJango_etc(String jango_etc) {
		this.jango_etc = jango_etc == null ? "" : jango_etc;
	}
}
