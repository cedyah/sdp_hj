package com.jebi.sdp.model;

public class ProdReqHeaderVO extends CustomerVO {

	public ProdReqHeaderVO() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	String saeobjang;			//사업장
	String ilja;                //일자
	String jeonpyo_no;          //전표번호
	String geolaecheo_code;     //거래처코드
	String euiloiil;            //의뢰일(완료요청일)
	String bigo;                //비고
	String jejo_saeobjang;      //제조사업장
	String jejo_ilja;           //제조일자
	String jejo_jeonpyo_no;     //제조 전표번호
	String wanlyo_yejeongil;    //완료 예정일
	String wanlyo_yn;           //완료여부
	String internet;            //인터넷
	String baedal_gubun;        //배달구분_코드
	String baedal_gubun_nm;     //배달구분_명칭
	String deunglogil;          //등록일
	String insuja;              //인수자
	String tel_no;              //전화번호
	String zip;                 //우편번호
	String addr1;               //주소_지번
	String addr2;               //주소_상세
	String baedal_jangso;       //배달장소
	String product_type;		//제조구분(신규, 일반)
	String jepum_code;			//품명(ex: ??외 몇건 )
	String pummyeong;			//품명(ex: ??외 몇건 )
	String gubun;			    //품명(ex: ??외 몇건 )
	
	public String getPummyeong() {
		return pummyeong == null ? "" : pummyeong;
	}
	public void setPummyeong(String pummyeong) {
		this.pummyeong = pummyeong == null ? "" : pummyeong;
	}
	public String getBaedal_gubun_nm() {
		return baedal_gubun_nm == null ? "" : baedal_gubun_nm;
	}
	public void setBaedal_gubun_nm(String baedal_gubun_nm) {
		this.baedal_gubun_nm = baedal_gubun_nm == null ? "" : baedal_gubun_nm;
	}
	public String getProduct_type() {
		return product_type == null ? "" : product_type;
	}
	public void setProduct_type(String product_type) {
		this.product_type = product_type == null ? "" : product_type;
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
	public String getGeolaecheo_code() {
		return geolaecheo_code == null ? "" : geolaecheo_code;
	}
	public void setGeolaecheo_code(String geolaecheo_code) {
		this.geolaecheo_code = geolaecheo_code == null ? "" : geolaecheo_code;
	}
	public String getEuiloiil() {
		return euiloiil == null ? "" : euiloiil;
	}
	public void setEuiloiil(String euiloiil) {
		this.euiloiil = euiloiil == null ? "" : euiloiil;
	}
	public String getBigo() {
		return bigo == null ? "" : bigo;
	}
	public void setBigo(String bigo) {
		this.bigo = bigo == null ? "" : bigo;
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
	public String getWanlyo_yejeongil() {
		return wanlyo_yejeongil == null ? "" : wanlyo_yejeongil;
	}
	public void setWanlyo_yejeongil(String wanlyo_yejeongil) {
		this.wanlyo_yejeongil = wanlyo_yejeongil == null ? "" : wanlyo_yejeongil;
	}
	public String getWanlyo_yn() {
		return wanlyo_yn == null ? "" : wanlyo_yn;
	}
	public void setWanlyo_yn(String wanlyo_yn) {
		this.wanlyo_yn = wanlyo_yn == null ? "" : wanlyo_yn;
	}
	public String getInternet() {
		return internet == null ? "" : internet;
	}
	public void setInternet(String internet) {
		this.internet = internet == null ? "" : internet;
	}
	public String getBaedal_gubun() {
		return baedal_gubun == null ? "" : baedal_gubun;
	}
	public void setBaedal_gubun(String baedal_gubun) {
		this.baedal_gubun = baedal_gubun == null ? "" : baedal_gubun;
	}
	public String getDeunglogil() {
		return deunglogil == null ? "" : deunglogil;
	}
	public void setDeunglogil(String deunglogil) {
		this.deunglogil = deunglogil == null ? "" : deunglogil;
	}
	public String getInsuja() {
		return insuja == null ? "" : insuja;
	}
	public void setInsuja(String insuja) {
		this.insuja = insuja == null ? "" : insuja;
	}
	public String getTel_no() {
		return tel_no == null ? "" : tel_no;
	}
	public void setTel_no(String tel_no) {
		this.tel_no = tel_no == null ? "" : tel_no;
	}
	public String getZip() {
		return zip == null ? "" : zip;
	}
	public void setZip(String zip) {
		this.zip = zip == null ? "" : zip;
	}
	public String getAddr1() {
		return addr1 == null ? "" : addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1 == null ? "" : addr1;
	}
	public String getAddr2() {
		return addr2 == null ? "" : addr2;
	}
	public void setAddr2(String addr2) {
		this.addr2 = addr2 == null ? "" : addr2;
	}
	public String getBaedal_jangso() {
		return baedal_jangso == null ? "" : baedal_jangso;
	}
	public void setBaedal_jangso(String baedal_jangso) {
		this.baedal_jangso = baedal_jangso == null ? "" : baedal_jangso;
	}
	public String getJepum_code() {
		return jepum_code;
	}
	public void setJepum_code(String jepum_code) {
		this.jepum_code = jepum_code;
	}
	public String getGubun() {
		return gubun;
	}
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
}
