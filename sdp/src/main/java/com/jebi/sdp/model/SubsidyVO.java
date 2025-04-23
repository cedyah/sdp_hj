package com.jebi.sdp.model;

public class SubsidyVO extends CustomerVO {

	public SubsidyVO() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	String pay_yyyymm;
	String div_amt1;
	String div_amt2;
	String div_amt3;
	String w_hab;
	String sale_yyyymm;
	
	public String getPay_yyyymm() {
		return pay_yyyymm == null ? "" : pay_yyyymm;
	}
	public void setPay_yyyymm(String pay_yyyymm) {
		this.pay_yyyymm = pay_yyyymm == null ? "" : pay_yyyymm;
	}
	public String getDiv_amt1() {
		return div_amt1 == null ? "" : div_amt1;
	}
	public void setDiv_amt1(String div_amt1) {
		this.div_amt1 = div_amt1 == null ? "" : div_amt1;
	}
	public String getDiv_amt2() {
		return div_amt2 == null ? "" : div_amt2;
	}
	public void setDiv_amt2(String div_amt2) {
		this.div_amt2 = div_amt2 == null ? "" : div_amt2;
	}
	public String getDiv_amt3() {
		return div_amt3 == null ? "" : div_amt3;
	}
	public void setDiv_amt3(String div_amt3) {
		this.div_amt3 = div_amt3 == null ? "" : div_amt3;
	}
	public String getW_hab() {
		return w_hab == null ? "" : w_hab;
	}
	public void setW_hab(String w_hab) {
		this.w_hab = w_hab == null ? "" : w_hab;
	}
	public String getSale_yyyymm() {
		return sale_yyyymm == null ? "" : sale_yyyymm;
	}
	public void setSale_yyyymm(String sale_yyyymm) {
		this.sale_yyyymm = sale_yyyymm == null ? "" : sale_yyyymm;
	}
	
}
