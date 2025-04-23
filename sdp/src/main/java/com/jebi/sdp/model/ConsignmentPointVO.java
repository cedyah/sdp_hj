package com.jebi.sdp.model;

public class ConsignmentPointVO extends CustomerVO {

	public ConsignmentPointVO() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	String yyyymm;
	String balsaeng_pt;
	String jigeub_pt;
	String w_hab;
	
	public String getYyyymm() {
		return yyyymm == null ? "" : yyyymm;
	}
	public void setYyyymm(String yyyymm) {
		this.yyyymm = yyyymm == null ? "" : yyyymm;
	}
	public String getBalsaeng_pt() {
		return balsaeng_pt == null ? "" : balsaeng_pt;
	}
	public void setBalsaeng_pt(String balsaeng_pt) {
		this.balsaeng_pt = balsaeng_pt == null ? "" : balsaeng_pt;
	}
	public String getJigeub_pt() {
		return jigeub_pt == null ? "" : jigeub_pt;
	}
	public void setJigeub_pt(String jigeub_pt) {
		this.jigeub_pt = jigeub_pt == null ? "" : jigeub_pt;
	}
	public String getW_hab() {
		return w_hab == null ? "" : w_hab;
	}
	public void setW_hab(String w_hab) {
		this.w_hab = w_hab == null ? "" : w_hab;
	}
	
}
