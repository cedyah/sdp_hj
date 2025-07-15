package com.jebi.sdp.model;

public class ContrastSubVO extends CustomerVO{

	public ContrastSubVO() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	//프로시저 파라미터
	String arg_pummog;
	//목록
	String pummyeong;
	String pa_danwi;
	String po_danwi;
	String aeg1_myeong;
	String aeg1_danwi;
	String aeg2_myeong;
	String aeg2_danwi;
	String sinna;

	public String getarg_pummog() {
		return arg_pummog == null ? "" : arg_pummog;
	}
	public void setarg_pummog(String arg_pummog) {
		this.arg_pummog = arg_pummog == null ? "" : arg_pummog;
	}
	public String getPummyeong() {
		return pummyeong == null ? "" : pummyeong;
	}
	public void setPummyeong(String pummyeong) {
		this.pummyeong = pummyeong == null ? "" : pummyeong;
	}
	public String getpa_danwi() {
		return pa_danwi == null ? "" : pa_danwi;
	}
	public void setpa_danwi(String pa_danwi) {
		this.pa_danwi = pa_danwi == null ? "" : pa_danwi;
	}
	public String getpo_danwi() {
		return po_danwi == null ? "" : po_danwi; 
	}
	public void setpo_danwi(String po_danwi) {
		this.po_danwi = po_danwi == null ? "" : po_danwi;
	}
	public String getaeg1_myeong() {
		return aeg1_myeong == null ? "" : aeg1_myeong;
	}
	public void setaeg1_myeong(String aeg1_myeong) {
		this.aeg1_myeong = aeg1_myeong == null ? "" : aeg1_myeong;
	}
	public String getaeg1_danwi() {
		return aeg1_danwi == null ? "" : aeg1_danwi;
	}
	public void setaeg1_danwi(String aeg1_danwi) {
		this.aeg1_danwi = aeg1_danwi == null ? "" : aeg1_danwi;
	}
	public String getaeg2_myeong() {
		return aeg2_myeong == null ? "" : aeg2_myeong;
	}
	public void setaeg2_myeong(String aeg2_myeong) {
		this.aeg2_myeong = aeg2_myeong == null ? "" : aeg2_myeong;
	}
	public String getaeg2_danwi() {
		return aeg2_danwi == null ? "" : aeg2_danwi;
	}
	public void setaeg2_danwi(String aeg2_danwi) {
		this.aeg2_danwi = aeg2_danwi == null ? "" : aeg2_danwi;
	}
	public String getsinna() {
		return sinna == null ? "" : sinna;
	}
	public void setsinna(String sinna) {
		this.sinna = sinna == null ? "" : sinna;
	}
}
