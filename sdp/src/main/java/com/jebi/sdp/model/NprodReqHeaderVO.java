package com.jebi.sdp.model;

public class NprodReqHeaderVO extends CustomerVO {
	public NprodReqHeaderVO() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	String ilja;				//작성일
	String jeonpyo_no;			//전표번호
	String euiloiil;            //완료요청일
	String baedal_gubun;        //배달구분
	String baedal_gubun_nm;     //배달구분명
	String baedal_jangso;       //배달장소
	String insuja;              //인수자
	String tel_no;              //전화번호
	String bigo;                //비고
	String zip;                 //우편번호
	String addr1;               //주소_지번
	String addr2;               //주소_상세주소
	
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
	public String getEuiloiil() {
		return euiloiil == null ? "" : euiloiil;
	}
	public void setEuiloiil(String euiloiil) {
		this.euiloiil = euiloiil == null ? "" : euiloiil;
	}
	public String getBaedal_gubun() {
		return baedal_gubun == null ? "" : baedal_gubun;
	}
	public void setBaedal_gubun(String baedal_gubun) {
		this.baedal_gubun = baedal_gubun == null ? "" : baedal_gubun;
	}
	public String getBaedal_gubun_nm() {
		return baedal_gubun_nm == null ? "" : baedal_gubun_nm;
	}
	public void setBaedal_gubun_nm(String baedal_gubun_nm) {
		this.baedal_gubun_nm = baedal_gubun_nm == null ? "" : baedal_gubun_nm;
	}
	public String getBaedal_jangso() {
		return baedal_jangso == null ? "" : baedal_jangso;
	}
	public void setBaedal_jangso(String baedal_jangso) {
		this.baedal_jangso = baedal_jangso == null ? "" : baedal_jangso;
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
	public String getBigo() {
		return bigo == null ? "" : bigo;
	}
	public void setBigo(String bigo) {
		this.bigo = bigo == null ? "" : bigo;
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
}
