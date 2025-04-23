package com.jebi.sdp.model;

public class ProductionProgressVO extends CustomerVO {
	
	public ProductionProgressVO() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	String ilja; 				// 의뢰일자
	String jeonpyo_no; 			// 전표번호
	String sunbeon; 			// 순번
	String jepum_code; 			// 제품코드
	String pummyeong; 			// 품명
	String pojang_danwi; 		// 포장단위
	String pojang_sulyang; 		// 포장수량
	String ibgo_sulyang; 		// 입고수량
	String euiloiil; 			// 의뢰일자(확인필요)
	String wanlyo_yejeongil; 	// 완료예정일
	String lot_no; 				// 창고번호
	String wanlyo_yn; 			// 완료여부, 진도상황
	String jindo_date; 			// 진도일자
	String bigo;				// 비고
	
	public String getIlja() {
		return ilja == null ? "" : ilja;
	}
	public void setIlja(String ilja) {
		this.ilja = ilja;
	}
	public String getJeonpyo_no() {
		return jeonpyo_no == null ? "" : jeonpyo_no;
	}
	public void setJeonpyo_no(String jeonpyo_no) {
		this.jeonpyo_no = jeonpyo_no;
	}
	public String getSunbeon() {
		return sunbeon == null ? "" : sunbeon;
	}
	public void setSunbeon(String sunbeon) {
		this.sunbeon = sunbeon;
	}
	public String getJepum_code() {
		return jepum_code == null ? "" : jepum_code;
	}
	public void setJepum_code(String jepum_code) {
		this.jepum_code = jepum_code;
	}
	public String getPummyeong() {
		return pummyeong == null ? "" : pummyeong;
	}
	public void setPummyeong(String pummyeong) {
		this.pummyeong = pummyeong;
	}
	public String getPojang_danwi() {
		return pojang_danwi == null ? "" : pojang_danwi;
	}
	public void setPojang_danwi(String pojang_danwi) {
		this.pojang_danwi = pojang_danwi;
	}
	public String getPojang_sulyang() {
		return pojang_sulyang == null ? "" : pojang_sulyang;
	}
	public void setPojang_sulyang(String pojang_sulyang) {
		this.pojang_sulyang = pojang_sulyang;
	}
	public String getIbgo_sulyang() {
		return ibgo_sulyang == null ? "" : ibgo_sulyang;
	}
	public void setIbgo_sulyang(String ibgo_sulyang) {
		this.ibgo_sulyang = ibgo_sulyang;
	}
	public String getEuiloiil() {
		return euiloiil == null ? "" : euiloiil;
	}
	public void setEuiloiil(String euiloiil) {
		this.euiloiil = euiloiil;
	}
	public String getWanlyo_yejeongil() {
		return wanlyo_yejeongil == null ? "" : wanlyo_yejeongil;
	}
	public void setWanlyo_yejeongil(String wanlyo_yejeongil) {
		this.wanlyo_yejeongil = wanlyo_yejeongil;
	}
	public String getLot_no() {
		return lot_no == null ? "" : lot_no;
	}
	public void setLot_no(String lot_no) {
		this.lot_no = lot_no;
	}
	public String getWanlyo_yn() {
		return wanlyo_yn == null ? "" : wanlyo_yn;
	}
	public void setWanlyo_yn(String wanlyo_yn) {
		this.wanlyo_yn = wanlyo_yn;
	}
	public String getJindo_date() {
		return jindo_date == null ? "" : jindo_date;
	}
	public void setJindo_date(String jindo_date) {
		this.jindo_date = jindo_date;
	}
	public String getBigo() {
		return bigo == null ? "" : bigo;
	}
	public void setBigo(String bigo) {
		this.bigo = bigo;
	}
}
