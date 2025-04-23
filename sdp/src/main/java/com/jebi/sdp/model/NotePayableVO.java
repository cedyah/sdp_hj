package com.jebi.sdp.model;

public class NotePayableVO extends CustomerVO{
	public NotePayableVO() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	String mangiil;		//만기일
	String eoeum_no;	//어음번호
	String geumaeg;		//금액
	String ibgeumil;	//입금일
	String balhaengja;	//발행인
	String semog;		//세목
	String gyeoljeil;	//결재일
	
	//프로시저 조건
	String arg_cust_cd;
	String arg_frdt;
	String arg_todt;
	String arg_bill_type;
	String out_param;
	
	public String getArg_cust_cd() {
		return arg_cust_cd == null ? "" : arg_cust_cd;
	}
	public void setArg_cust_cd(String arg_cust_cd) {
		this.arg_cust_cd = arg_cust_cd == null ? "" : arg_cust_cd;
	}
	public String getArg_frdt() {
		return arg_frdt == null ? "" : arg_frdt;
	}
	public void setArg_frdt(String arg_frdt) {
		this.arg_frdt = arg_frdt == null ? "" : arg_frdt;
	}
	public String getArg_todt() {
		return arg_todt == null ? "" : arg_todt;
	}
	public void setArg_todt(String arg_todt) {
		this.arg_todt = arg_todt == null ? "" : arg_todt;
	}
	public String getArg_bill_type() {
		return arg_bill_type == null ? "" : arg_bill_type;
	}
	public void setArg_bill_type(String arg_bill_type) {
		this.arg_bill_type = arg_bill_type == null ? "" : arg_bill_type;
	}
	public String getOut_param() {
		return out_param == null ? "" : out_param;
	}
	public void setOut_param(String out_param) {
		this.out_param = out_param == null ? "" : out_param;
	}
	
	
	
	public String getMangiil() {
		return mangiil == null ? "" : mangiil;
	}
	public void setMangiil(String mangiil) {
		this.mangiil = mangiil == null ? "" : mangiil;
	}
	public String getEoeum_no() {
		return eoeum_no == null ? "" : eoeum_no;
	}
	public void setEoeum_no(String eoeum_no) {
		this.eoeum_no = eoeum_no == null ? "" : eoeum_no;
	}
	public String getGeumaeg() {
		return geumaeg == null ? "" : geumaeg;
	}
	public void setGeumaeg(String geumaeg) {
		this.geumaeg = geumaeg == null ? "" : geumaeg;
	}
	public String getIbgeumil() {
		return ibgeumil == null ? "" : ibgeumil;
	}
	public void setIbgeumil(String ibgeumil) {
		this.ibgeumil = ibgeumil == null ? "" : ibgeumil;
	}
	public String getBalhaengja() {
		return balhaengja == null ? "" : balhaengja;
	}
	public void setBalhaengja(String balhaengja) {
		this.balhaengja = balhaengja == null ? "" : balhaengja;
	}
	public String getSemog() {
		return semog == null ? "" : semog;
	}
	public void setSemog(String semog) {
		this.semog = semog == null ? "" : semog;
	}
	public String getGyeoljeil() {
		return gyeoljeil == null ? "" : gyeoljeil;
	}
	public void setGyeoljeil(String gyeoljeil) {
		this.gyeoljeil = gyeoljeil == null ? "" : gyeoljeil;
	}
}
