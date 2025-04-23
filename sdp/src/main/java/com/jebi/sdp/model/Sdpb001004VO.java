package com.jebi.sdp.model;

public class Sdpb001004VO extends CustomerVO {
	
	public Sdpb001004VO() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	String youbyul;
	String jepum;
	String pummyeong;
	String panmaerayng;
	
	public String getYoubyul() {
		return youbyul == null ? "" : youbyul;
	}
	public void setYoubyul(String youbyul) {
		this.youbyul = youbyul == null ? "" : youbyul;
	}
	public String getJepum() {
		return jepum == null ? "" : jepum;
	}
	public void setJepum(String jepum) {
		this.jepum = jepum == null ? "" : jepum;
	}
	public String getPummyeong() {
		return pummyeong == null ? "" : pummyeong;
	}
	public void setPummyeong(String pummyeong) {
		this.pummyeong = pummyeong == null ? "" : pummyeong;
	}
	public String getPanmaerayng() {
		return panmaerayng == null ? "" : panmaerayng;
	}
	public void setPanmaerayng(String panmaerayng) {
		this.panmaerayng = panmaerayng == null ? "" : panmaerayng;
	}
	
}
