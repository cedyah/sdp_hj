package com.jebi.sdp.model;

public class TestReportItemVO extends CustomerVO {
	public TestReportItemVO() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	String req_dt;		//요청일자
	String req_no;		//요청번호
	String seq;         //순번
	String item_cd;     //제품코드
	String pummyeong;   //품명
	String lot_no;      //lot번호
	String su;          //수
	String prog_stat;   //상태코드
	String prog_dt;     //상태 갱신일자
	String emp_no;      //담당자 사번
	String dept;        //부서
	String print_yn;	//출력구분
	
	public String getPrint_yn() {
		return print_yn == null ? "" : print_yn;
	}
	public void setPrint_yn(String print_yn) {
		this.print_yn = print_yn == null ? "" : print_yn;
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
	public String getLot_no() {
		return lot_no == null ? "" : lot_no;
	}
	public void setLot_no(String lot_no) {
		this.lot_no = lot_no == null ? "" : lot_no;
	}
	public String getSu() {
		return su == null ? "" : su;
	}
	public void setSu(String su) {
		this.su = su == null ? "" : su;
	}
	public String getProg_stat() {
		return prog_stat == null ? "" : prog_stat;
	}
	public void setProg_stat(String prog_stat) {
		this.prog_stat = prog_stat == null ? "" : prog_stat;
	}
	public String getProg_dt() {
		return prog_dt == null ? "" : prog_dt;
	}
	public void setProg_dt(String prog_dt) {
		this.prog_dt = prog_dt == null ? "" : prog_dt;
	}
	public String getEmp_no() {
		return emp_no == null ? "" : emp_no;
	}
	public void setEmp_no(String emp_no) {
		this.emp_no = emp_no == null ? "" : emp_no;
	}
	public String getDept() {
		return dept == null ? "" : dept;
	}
	public void setDept(String dept) {
		this.dept = dept == null ? "" : dept;
	}
	
}
