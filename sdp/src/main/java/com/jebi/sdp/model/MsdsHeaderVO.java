package com.jebi.sdp.model;

public class MsdsHeaderVO extends CustomerVO {
	public MsdsHeaderVO() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	String req_dt;			//요청일자
	String req_no;			//전표번호
	String cust_cd;         //거래처코드
	String submit_nm;       //제출처명
	String submit_dt;       //제출일자
	String rmk;             //비고
	String inst_dt;         //생성일자
	
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
	public String getCust_cd() {
		return cust_cd == null ? "" : cust_cd;
	}
	public void setCust_cd(String cust_cd) {
		this.cust_cd = cust_cd == null ? "" : cust_cd;
	}
	public String getSubmit_nm() {
		return submit_nm == null ? "" : submit_nm;
	}
	public void setSubmit_nm(String submit_nm) {
		this.submit_nm = submit_nm == null ? "" : submit_nm;
	}
	public String getSubmit_dt() {
		return submit_dt == null ? "" : submit_dt;
	}
	public void setSubmit_dt(String submit_dt) {
		this.submit_dt = submit_dt == null ? "" : submit_dt;
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
