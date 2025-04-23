package com.jebi.sdp.model;

public class CreditHeaderVO extends CustomerVO {

	public CreditHeaderVO() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	String month;
	String amt;
	String vat;
	String amt_vat;
	String colet_amt;
	String jango;
	String month_etc;
	String amt_etc;
	String vat_etc;
	String colet_amt_etc;
	String jango_etc;
	
	public String getMonth() {
		return month == null ? "" : month;
	}
	public void setMonth(String month) {
		this.month = month == null ? "" : month;
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
	public String getAmt_vat() {
		return amt_vat == null ? "" : amt_vat;
	}
	public void setAmt_vat(String amt_vat) {
		this.amt_vat = amt_vat == null ? "" : amt_vat;
	}
	public String getColet_amt() {
		return colet_amt == null ? "" : colet_amt;
	}
	public void setColet_amt(String colet_amt) {
		this.colet_amt = colet_amt == null ? "" : colet_amt;
	}
	public String getJango() {
		return jango == null ? "" : jango;
	}
	public void setJango(String jango) {
		this.jango = jango == null ? "" : jango;
	}
	public String getMonth_etc() {
		return month_etc == null ? "" : month_etc;
	}
	public void setMonth_etc(String month_etc) {
		this.month_etc = month_etc == null ? "" : month_etc;
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
