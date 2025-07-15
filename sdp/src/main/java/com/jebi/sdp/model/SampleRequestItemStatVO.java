package com.jebi.sdp.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SampleRequestItemStatVO extends CustomerVO{

	public SampleRequestItemStatVO() throws Exception {
		super();
		// TODO Auto-generated constructor stub
		//검색조건이 없을시 기본 900개월 셋팅
		//		Date dt = new Date();
		//		SimpleDateFormat smt = new SimpleDateFormat("yyyy.MM.dd");

		//Calendar cal = Calendar.getInstance();
        //cal.setTime(dt);
//        cal.add(Calendar.DATE, -3);		//3일 전
        //cal.add(Calendar.MONTH, -900);		//1달 전

		//setSearchDate_from(smt.format(cal.getTime()));
		//setSearchDate_to(smt.format(cal.getTime()));
		System.out.println("searchDate_from = " + getSearchDate_from());
		System.out.println("searchDate_to = " + getSearchDate_to());
	}
	//BSET091_WEB
	String saeobjang          ;
    String prod_req_dt         ;
    String prod_req_no       ;
    String seq                ;
    String item_cd           ;
    String pummyeong         ;
    String po_danwi          ;
    String qty             ;
    String dely_date         ;
    String prgs_status       ;
    String rmk_1             ;
    String rmk_2             ;
    String rmk_3             ;
    String gubun             ;
    String gyeolgwa_1        ;
    
	public String getSaeobjang() {
		return saeobjang;
	}
	public void setSaeobjang(String saeobjang) {
		this.saeobjang = saeobjang;
	}
	public String getProd_req_dt() {
		return prod_req_dt;
	}
	public void setProd_req_dt(String prod_req_dt) {
		this.prod_req_dt = prod_req_dt;
	}
	public String getProd_req_no() {
		return prod_req_no;
	}
	public void setProd_req_no(String prod_req_no) {
		this.prod_req_no = prod_req_no;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getItem_cd() {
		return item_cd;
	}
	public void setItem_cd(String item_cd) {
		this.item_cd = item_cd;
	}
	public String getPummyeong() {
		return pummyeong;
	}
	public void setPummyeong(String pummyeong) {
		this.pummyeong = pummyeong;
	}
	public String getPo_danwi() {
		return po_danwi;
	}
	public void setPo_danwi(String po_danwi) {
		this.po_danwi = po_danwi;
	}
	public String getQty() {
		return qty;
	}
	public void setQty(String qty) {
		this.qty = qty;
	}
	public String getDely_date() {
		return dely_date;
	}
	public void setDely_date(String dely_date) {
		this.dely_date = dely_date;
	}
	public String getPrgs_status() {
		return prgs_status;
	}
	public void setPrgs_status(String prgs_status) {
		this.prgs_status = prgs_status;
	}
	public String getRmk_1() {
		return rmk_1;
	}
	public void setRmk_1(String rmk_1) {
		this.rmk_1 = rmk_1;
	}
	public String getRmk_2() {
		return rmk_2;
	}
	public void setRmk_2(String rmk_2) {
		this.rmk_2 = rmk_2;
	}
	public String getRmk_3() {
		return rmk_3;
	}
	public void setRmk_3(String rmk_3) {
		this.rmk_3 = rmk_3;
	}
	public String getGubun() {
		return gubun;
	}
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
	public String getGyeolgwa_1() {
		return gyeolgwa_1;
	}
	public void setGyeolgwa_1(String gyeolgwa_1) {
		this.gyeolgwa_1 = gyeolgwa_1;
	}
     
}
