package com.jebi.sdp.model;

public class GiftVO extends CustomerVO{
	public GiftVO() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	String dt;
	String bal;
	String repay_type;
	String amt;
	String fix_dt;
	String paym_dt;
	
	public String getDt() {
		return dt == null ? "" : dt;
	}
	public void setDt(String dt) {
		this.dt = dt == null ? "" : dt;
	}
	public String getBal() {
		return bal == null ? "" : bal;
	}
	public void setBal(String bal) {
		this.bal = bal == null ? "" : bal;
	}
	public String getRepay_type() {
		return repay_type == null ? "" : repay_type;
	}
	public void setRepay_type(String repay_type) {
		this.repay_type = repay_type == null ? "" : repay_type;
	}
	public String getAmt() {
		return amt == null ? "" : amt;
	}
	public void setAmt(String amt) {
		this.amt = amt == null ? "" : amt;
	}
	public String getFix_dt() {
		return fix_dt == null ? "" : fix_dt;
	}
	public void setFix_dt(String fix_dt) {
		this.fix_dt = fix_dt == null ? "" : fix_dt;
	}
	public String getPaym_dt() {
		return paym_dt == null ? "" : paym_dt;
	}
	public void setPaym_dt(String paym_dt) {
		this.paym_dt = paym_dt == null ? "" : paym_dt;
	}
}
