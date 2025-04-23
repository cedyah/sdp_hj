package com.jebi.sdp.model;

public class TestReportVO extends CustomerVO {
	public TestReportVO() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	String req_dt;			//요청일자
	String req_no;          //요청번호
	String submit_nm_k;     //제출처명_한글
	String submit_nm_e;     //제출처명_영문
	String submit_dt;       //제출일자
	String lang;            //언어
	String rmk;             //비고
	String inst_dt;         //작성일시
	
	String item_cd;     //제품코드
	String lot_no;      //lot번호
	
	
	public String getItem_cd() {
		return item_cd == null ? "" : item_cd;
	}
	public void setItem_cd(String item_cd) {
		this.item_cd = item_cd == null ? "" : item_cd;
	}
	public String getLot_no() {
		return lot_no == null ? "" : lot_no;
	}
	public void setLot_no(String lot_no) {
		this.lot_no = lot_no == null ? "" : lot_no;
	}
	public String getReq_dt() {
		return req_dt == null ? "" : req_dt;
	}
	public void setReq_dt(String req_dt) {
		this.req_dt = req_dt == null ? "" : req_dt;
	}
	public String getReq_no() {
		return req_no == null ? "" : req_no;
	}
	public void setReq_no(String req_no) {
		this.req_no = req_no == null ? "" : req_no;
	}
	public String getSubmit_nm_k() {
		return submit_nm_k == null ? "" : submit_nm_k;
	}
	public void setSubmit_nm_k(String submit_nm_k) {
		this.submit_nm_k = submit_nm_k == null ? "" : submit_nm_k;
	}
	public String getSubmit_nm_e() {
		return submit_nm_e == null ? "" : submit_nm_e;
	}
	public void setSubmit_nm_e(String submit_nm_e) {
		this.submit_nm_e = submit_nm_e == null ? "" : submit_nm_e;
	}
	public String getSubmit_dt() {
		return submit_dt == null ? "" : submit_dt;
	}
	public void setSubmit_dt(String submit_dt) {
		this.submit_dt = submit_dt == null ? "" : submit_dt;
	}
	public String getLang() {
		return lang == null ? "" : lang;
	}
	public void setLang(String lang) {
		this.lang = lang == null ? "" : lang;
	}
	public String getRmk() {
		return rmk == null ? "" : rmk;
	}
	public void setRmk(String rmk) {
		this.rmk = rmk == null ? "" : rmk;
	}
	public String getInst_dt() {
		return inst_dt == null ? "" : inst_dt;
	}
	public void setInst_dt(String inst_dt) {
		this.inst_dt = inst_dt == null ? "" : inst_dt;
	}
}
