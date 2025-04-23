package com.jebi.sdp.model;

public class ProdReqSubVO extends CustomerVO {

	public ProdReqSubVO() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	String jepum_code;			//제품코드
	String pojang_danwi_a;      //포장단위1
	String pojang_danwi_b;      //포장단위2
	String pummyeong;           //품명
	String pojang_sulyang;      //포장수량
	String wanlyo_yn;           //완료여부_코드
	String wanlyo_yn_nm;        //완료여부_명
	String jindo_date;          //진도갱신일
	String sunbeon;             //순번
	
	public String getJepum_code() {
		return jepum_code == null ? "" : jepum_code;
	}
	public void setJepum_code(String jepum_code) {
		this.jepum_code = jepum_code == null ? "" : jepum_code;
	}
	public String getPojang_danwi_a() {
		return pojang_danwi_a == null ? "" : pojang_danwi_a;
	}
	public void setPojang_danwi_a(String pojang_danwi_a) {
		this.pojang_danwi_a = pojang_danwi_a == null ? "" : pojang_danwi_a;
	}
	public String getPojang_danwi_b() {
		return pojang_danwi_b == null ? "" : pojang_danwi_b;
	}
	public void setPojang_danwi_b(String pojang_danwi_b) {
		this.pojang_danwi_b = pojang_danwi_b == null ? "" : pojang_danwi_b;
	}
	public String getPummyeong() {
		return pummyeong == null ? "" : pummyeong;
	}
	public void setPummyeong(String pummyeong) {
		this.pummyeong = pummyeong == null ? "" : pummyeong;
	}
	public String getPojang_sulyang() {
		return pojang_sulyang == null ? "" : pojang_sulyang;
	}
	public void setPojang_sulyang(String pojang_sulyang) {
		this.pojang_sulyang = pojang_sulyang == null ? "" : pojang_sulyang;
	}
	public String getWanlyo_yn() {
		return wanlyo_yn == null ? "" : wanlyo_yn;
	}
	public void setWanlyo_yn(String wanlyo_yn) {
		this.wanlyo_yn = wanlyo_yn == null ? "" : wanlyo_yn;
	}
	public String getWanlyo_yn_nm() {
		return wanlyo_yn_nm == null ? "" : wanlyo_yn_nm;
	}
	public void setWanlyo_yn_nm(String wanlyo_yn_nm) {
		this.wanlyo_yn_nm = wanlyo_yn_nm == null ? "" : wanlyo_yn_nm;
	}
	public String getJindo_date() {
		return jindo_date == null ? "" : jindo_date;
	}
	public void setJindo_date(String jindo_date) {
		this.jindo_date = jindo_date == null ? "" : jindo_date;
	}
	public String getSunbeon() {
		return sunbeon == null ? "" : sunbeon;
	}
	public void setSunbeon(String sunbeon) {
		this.sunbeon = sunbeon == null ? "" : sunbeon;
	}

}
