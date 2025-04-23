package com.jebi.sdp.model;

public class PointVO extends CustomerVO {

	public PointVO() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	String dt;			//기준일자
	String up;			//추가된 포인트
	String down;		//사용된 포인트
	String bal;			//잔고
	String inst_dt;		//발생일자
	String reason;		//발생사유
	String gift_check;	//상품권 입력 가능 여부('':등록기간이 아니므로 입력 불가, 'insert':등록기간이며 신규 입력, 'update':기존에 데이터가 있어 수정화면으로 이동시킴)
	
	public String getGift_check() {
		return gift_check == null ? "" : gift_check;
	}
	public void setGift_check(String gift_check) {
		this.gift_check = gift_check == null ? "" : gift_check;
	}
	public String getDt() {
		return dt == null ? "" : dt;
	}
	public void setDt(String dt) {
		this.dt = dt == null ? "" : dt;
	}
	public String getUp() {
		return up == null ? "" : up;
	}
	public void setUp(String up) {
		this.up = up == null ? "" : up;
	}
	public String getDown() {
		return down == null ? "" : down;
	}
	public void setDown(String down) {
		this.down = down == null ? "" : down;
	}
	public String getBal() {
		return bal == null ? "" : bal;
	}
	public void setBal(String bal) {
		this.bal = bal == null ? "" : bal;
	}
	public String getInst_dt() {
		return inst_dt == null ? "" : inst_dt;
	}
	public void setInst_dt(String inst_dt) {
		this.inst_dt = inst_dt == null ? "" : inst_dt;
	}
	public String getReason() {
		return reason == null ? "" : reason;
	}
	public void setReason(String reason) {
		this.reason = reason == null ? "" : reason;
	}
}
