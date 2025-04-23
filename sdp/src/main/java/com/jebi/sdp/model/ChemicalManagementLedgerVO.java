package com.jebi.sdp.model;

public class ChemicalManagementLedgerVO extends CustomerVO {

	public ChemicalManagementLedgerVO() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	String pummyeong;		//품명
	String cas_myeong;      //물질명
	String baehab_10;       //함유량
	String hwansan_su;      //취급량
	String yongdo;          //주요용도
	String cas_no;          //고유번호(CAS_NO)
	String goche;           //상온,상압에서의 상태_고체인가?
	String aegche;          //상온,상압에서의 상태_액체인가?
	String giche;           //상온,상압에서의 상태_기체인가?
	
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
	public String getGoche() {
		return goche == null ? "" : goche;
	}
	public void setGoche(String goche) {
		this.goche = goche == null ? "" : goche;
	}
	public String getAegche() {
		return aegche == null ? "" : aegche;
	}
	public void setAegche(String aegche) {
		this.aegche = aegche == null ? "" : aegche;
	}
	public String getGiche() {
		return giche == null ? "" : giche;
	}
	public void setGiche(String giche) {
		this.giche = giche == null ? "" : giche;
	}
}
