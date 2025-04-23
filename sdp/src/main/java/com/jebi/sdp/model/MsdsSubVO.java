package com.jebi.sdp.model;

public class MsdsSubVO extends CustomerVO {
	public MsdsSubVO() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	String seq;				//순번
	String item_cd;         //제품코드
	String pummyeong;       //품명
	String prog_dt;         //
	String buseomyeong ;    //부서명
	
	String msds_yn;			//출력 가능 여부
	String saeobjang;		//출력물을 위한 사업장
	
	public String getMsds_yn() {
		return msds_yn == null ? "" : msds_yn;
	}
	public void setMsds_yn(String msds_yn) {
		this.msds_yn = msds_yn == null ? "" : msds_yn;
	}
	public String getSaeobjang() {
		return saeobjang == null ? "" : saeobjang;
	}
	public void setSaeobjang(String saeobjang) {
		this.saeobjang = saeobjang == null ? "" : saeobjang;
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
	public String getProg_dt() {
		return prog_dt == null ? "" : prog_dt;
	}
	public void setProg_dt(String prog_dt) {
		this.prog_dt = prog_dt == null ? "" : prog_dt;
	}
	public String getBuseomyeong() {
		return buseomyeong == null ? "" : buseomyeong;
	}
	public void setBuseomyeong(String buseomyeong) {
		this.buseomyeong = buseomyeong == null ? "" : buseomyeong;
	}
	
}
