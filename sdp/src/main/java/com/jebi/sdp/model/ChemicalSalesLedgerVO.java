package com.jebi.sdp.model;

public class ChemicalSalesLedgerVO extends CustomerVO {

	public ChemicalSalesLedgerVO() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	String pummyeong;		//품명
	String cas_myeong;      //물질명
	String baehab_10;       //함유량
	String hwansan_su;      //취급량
	String yongdo;          //주요용도
	String cas_no;          //고유번호
	String jepum_code;      //제품코드
	
	public String getPummyeong() {
		return pummyeong == null ? "" : pummyeong;
	}
	public void setPummyeong(String pummyeong) {
		this.pummyeong = pummyeong == null ? "" : pummyeong;
	}
	public String getCas_myeong() {
		return cas_myeong == null ? "" : cas_myeong;
	}
	public void setCas_myeong(String cas_myeong) {
		this.cas_myeong = cas_myeong == null ? "" : cas_myeong;
	}
	public String getBaehab_10() {
		return baehab_10 == null ? "" : baehab_10;
	}
	public void setBaehab_10(String baehab_10) {
		this.baehab_10 = baehab_10 == null ? "" : baehab_10;
	}
	public String getHwansan_su() {
		return hwansan_su == null ? "" : hwansan_su;
	}
	public void setHwansan_su(String hwansan_su) {
		this.hwansan_su = hwansan_su == null ? "" : hwansan_su;
	}
	public String getYongdo() {
		return yongdo == null ? "" : yongdo;
	}
	public void setYongdo(String yongdo) {
		this.yongdo = yongdo == null ? "" : yongdo;
	}
	public String getCas_no() {
		return cas_no == null ? "" : cas_no;
	}
	public void setCas_no(String cas_no) {
		this.cas_no = cas_no == null ? "" : cas_no;
	}
	public String getJepum_code() {
		return jepum_code == null ? "" : jepum_code;
	}
	public void setJepum_code(String jepum_code) {
		this.jepum_code = jepum_code == null ? "" : jepum_code;
	}
}
