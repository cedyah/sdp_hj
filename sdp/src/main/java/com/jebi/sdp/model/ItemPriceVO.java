package com.jebi.sdp.model;

public class ItemPriceVO extends ItemVO {

	public ItemPriceVO() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	String rnum;				//순번
	String jepum_code;			//제품코드
	String pummyeong;           //품명
	String panmae_danwi_a;      //판매단위a
	String panmae_danwi_b;      //판매단위b
	String panmaega;            //판매가격
	String gongjangdoga;		//공장도가
	String maeibga;				//매입가
	String endil;				//종료일
	
	public String getGongjangdoga() {
		return gongjangdoga == null ? "" : gongjangdoga;
	}
	public void setGongjangdoga(String gongjangdoga) {
		this.gongjangdoga = gongjangdoga == null ? "" : gongjangdoga;
	}
	public String getMaeibga() {
		return maeibga == null ? "" : maeibga;
	}
	public void setMaeibga(String maeibga) {
		this.maeibga = maeibga == null ? "" : maeibga;
	}
	public String getEndil() {
		return endil == null ? "" : endil;
	}
	public void setEndil(String endil) {
		this.endil = endil == null ? "" : endil;
	}
	public String getRnum() {
		return rnum == null ? "" : rnum;
	}
	public void setRnum(String rnum) {
		this.rnum = rnum == null ? "" : rnum;
	}
	public String getJepum_code() {
		return jepum_code == null ? "" : jepum_code;
	}
	public void setJepum_code(String jepum_code) {
		this.jepum_code = jepum_code == null ? "" : jepum_code;
	}
	public String getPummyeong() {
		return pummyeong == null ? "" : pummyeong;
	}
	public void setPummyeong(String pummyeong) {
		this.pummyeong = pummyeong == null ? "" : pummyeong;
	}
	public String getPanmae_danwi_a() {
		return panmae_danwi_a == null ? "" : panmae_danwi_a;
	}
	public void setPanmae_danwi_a(String panmae_danwi_a) {
		this.panmae_danwi_a = panmae_danwi_a == null ? "" : panmae_danwi_a;
	}
	public String getPanmae_danwi_b() {
		return panmae_danwi_b == null ? "" : panmae_danwi_b;
	}
	public void setPanmae_danwi_b(String panmae_danwi_b) {
		this.panmae_danwi_b = panmae_danwi_b == null ? "" : panmae_danwi_b;
	}
	public String getPanmaega() {
		return panmaega == null ? "" : panmaega;
	}
	public void setPanmaega(String panmaega) {
		this.panmaega = panmaega == null ? "" : panmaega;
	}
}
