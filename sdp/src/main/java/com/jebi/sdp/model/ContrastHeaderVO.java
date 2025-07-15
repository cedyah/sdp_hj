package com.jebi.sdp.model;

public class ContrastHeaderVO extends CustomerVO {

	public ContrastHeaderVO() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	String no;
	String pummog;
	String pummyeong;
	String aeg;
	String biyul;
	
	public String getno() {
		return no == null ? "" : no;
	}
	public void setno(String no) {
		this.no = no == null ? "" : no;
	}
	public String getpummog() {
		return pummog == null ? "" : pummog;
	}
	public void setpummog(String pummog) {
		this.pummog = pummog == null ? "" : pummog;
	}
	public String getpummyeong() {
		return pummyeong == null ? "" : pummyeong;
	}
	public void setpummyeong(String pummyeong) {
		this.pummyeong = pummyeong == null ? "" : pummyeong;
	}
	public String getaeg() {
		return aeg == null ? "" : aeg;
	}
	public void setaeg(String aeg) {
		this.aeg = aeg == null ? "" : aeg;
	}
	public String getbiyul() {
		return biyul == null ? "" : biyul;
	}
	public void setbiyul(String biyul) {
		this.biyul = biyul == null ? "" : biyul;
	}
}
