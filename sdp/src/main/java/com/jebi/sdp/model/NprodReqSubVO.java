package com.jebi.sdp.model;

public class NprodReqSubVO extends CustomerVO {
	public NprodReqSubVO() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	String saeobjang;			//사업장
	String ilja;                //작성일
	String jeonpyo_no;          //전표번호
	String sunbeon;             //순번
	String geolaecheo_code;     //거래처코드
	String pummyeong;           //품명
	String pojang_danwi_a;      //단위1
	String pojang_danwi_b;      //단위2
	String pojang_sulyang;      //제조요청수량
	String bigo;                //비고
	String jindo;               //진도
	String jindo_nm;            //진도 명칭
	String jejo_saeobjang;      //제조사업장
	String jejo_ilja;           //제조일자
	String jejo_jeonpyo_no;     //제조 전표번호
	String jejo_sunbeon;        //제조순번
	String gyeon_saeobjang;     //견본 사업장
	String gyeon_saeobjang_nm;  //견본 사업장 명칭
	String gyeon_ilja;          //견본 작성일
	String gyeon_jeonpyo_no;    //견본 전표번호
	
	public String getGyeon_saeobjang_nm() {
		return gyeon_saeobjang_nm == null ? "" : gyeon_saeobjang_nm;
	}
	public void setGyeon_saeobjang_nm(String gyeon_saeobjang_nm) {
		this.gyeon_saeobjang_nm = gyeon_saeobjang_nm == null ? "" : gyeon_saeobjang_nm;
	}
	public String getJindo_nm() {
		return jindo_nm == null ? "" : jindo_nm;
	}
	public void setJindo_nm(String jindo_nm) {
		this.jindo_nm = jindo_nm == null ? "" : jindo_nm;
	}
	public String getSaeobjang() {
		return saeobjang == null ? "" : saeobjang;
	}
	public void setSaeobjang(String saeobjang) {
		this.saeobjang = saeobjang == null ? "" : saeobjang;
	}
	public String getIlja() {
		return ilja == null ? "" : ilja;
	}
	public void setIlja(String ilja) {
		this.ilja = ilja == null ? "" : ilja;
	}
	public String getJeonpyo_no() {
		return jeonpyo_no == null ? "" : jeonpyo_no;
	}
	public void setJeonpyo_no(String jeonpyo_no) {
		this.jeonpyo_no = jeonpyo_no == null ? "" : jeonpyo_no;
	}
	public String getSunbeon() {
		return sunbeon == null ? "" : sunbeon;
	}
	public void setSunbeon(String sunbeon) {
		this.sunbeon = sunbeon == null ? "" : sunbeon;
	}
	public String getGeolaecheo_code() {
		return geolaecheo_code == null ? "" : geolaecheo_code;
	}
	public void setGeolaecheo_code(String geolaecheo_code) {
		this.geolaecheo_code = geolaecheo_code == null ? "" : geolaecheo_code;
	}
	public String getPummyeong() {
		return pummyeong == null ? "" : pummyeong;
	}
	public void setPummyeong(String pummyeong) {
		this.pummyeong = pummyeong == null ? "" : pummyeong;
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
	public String getPojang_sulyang() {
		return pojang_sulyang == null ? "" : pojang_sulyang;
	}
	public void setPojang_sulyang(String pojang_sulyang) {
		this.pojang_sulyang = pojang_sulyang == null ? "" : pojang_sulyang;
	}
	public String getBigo() {
		return bigo == null ? "" : bigo;
	}
	public void setBigo(String bigo) {
		this.bigo = bigo == null ? "" : bigo;
	}
	public String getJindo() {
		return jindo == null ? "" : jindo;
	}
	public void setJindo(String jindo) {
		this.jindo = jindo == null ? "" : jindo;
	}
	public String getJejo_saeobjang() {
		return jejo_saeobjang == null ? "" : jejo_saeobjang;
	}
	public void setJejo_saeobjang(String jejo_saeobjang) {
		this.jejo_saeobjang = jejo_saeobjang == null ? "" : jejo_saeobjang;
	}
	public String getJejo_ilja() {
		return jejo_ilja == null ? "" : jejo_ilja;
	}
	public void setJejo_ilja(String jejo_ilja) {
		this.jejo_ilja = jejo_ilja == null ? "" : jejo_ilja;
	}
	public String getJejo_jeonpyo_no() {
		return jejo_jeonpyo_no == null ? "" : jejo_jeonpyo_no;
	}
	public void setJejo_jeonpyo_no(String jejo_jeonpyo_no) {
		this.jejo_jeonpyo_no = jejo_jeonpyo_no == null ? "" : jejo_jeonpyo_no;
	}
	public String getJejo_sunbeon() {
		return jejo_sunbeon == null ? "" : jejo_sunbeon;
	}
	public void setJejo_sunbeon(String jejo_sunbeon) {
		this.jejo_sunbeon = jejo_sunbeon == null ? "" : jejo_sunbeon;
	}
	public String getGyeon_saeobjang() {
		return gyeon_saeobjang == null ? "" : gyeon_saeobjang;
	}
	public void setGyeon_saeobjang(String gyeon_saeobjang) {
		this.gyeon_saeobjang = gyeon_saeobjang == null ? "" : gyeon_saeobjang;
	}
	public String getGyeon_ilja() {
		return gyeon_ilja == null ? "" : gyeon_ilja;
	}
	public void setGyeon_ilja(String gyeon_ilja) {
		this.gyeon_ilja = gyeon_ilja == null ? "" : gyeon_ilja;
	}
	public String getGyeon_jeonpyo_no() {
		return gyeon_jeonpyo_no == null ? "" : gyeon_jeonpyo_no;
	}
	public void setGyeon_jeonpyo_no(String gyeon_jeonpyo_no) {
		this.gyeon_jeonpyo_no = gyeon_jeonpyo_no == null ? "" : gyeon_jeonpyo_no;
	}
	
}
