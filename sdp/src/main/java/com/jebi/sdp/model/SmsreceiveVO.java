package com.jebi.sdp.model;

public class SmsreceiveVO extends CustomerVO {
	public SmsreceiveVO() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	String sms_num;
	String receive_time;
	String receive_contents;
	String phone;
	String receiver;
	String reply_phone;
	String reply_man;
	String sms_state;
	String sms_result;
	
	//프로시저 조건
	String arg_cust_cd;
	String arg_frdt;
	String arg_todt;
	String out_param;
	
	public String getSms_num() {
		return sms_num;
	}
	public String getArg_cust_cd() {
		return arg_cust_cd;
	}
	public void setArg_cust_cd(String arg_cust_cd) {
		this.arg_cust_cd = arg_cust_cd;
	}
	public String getArg_frdt() {
		return arg_frdt;
	}
	public void setArg_frdt(String arg_frdt) {
		this.arg_frdt = arg_frdt;
	}
	public String getArg_todt() {
		return arg_todt;
	}
	public void setArg_todt(String arg_todt) {
		this.arg_todt = arg_todt;
	}
	public String getOut_param() {
		return out_param;
	}
	public void setOut_param(String out_param) {
		this.out_param = out_param;
	}
	public void setSms_num(String sms_num) {
		this.sms_num = sms_num;
	}
	public String getReceive_time() {
		return receive_time;
	}
	public void setReceive_time(String receive_time) {
		this.receive_time = receive_time;
	}
	public String getReceive_contents() {
		return receive_contents;
	}
	public void setReceive_contents(String receive_contents) {
		this.receive_contents = receive_contents;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getReply_phone() {
		return reply_phone;
	}
	public void setReply_phone(String reply_phone) {
		this.reply_phone = reply_phone;
	}
	public String getReply_man() {
		return reply_man;
	}
	public void setReply_man(String reply_man) {
		this.reply_man = reply_man;
	}
	public String getSms_state() {
		return sms_state;
	}
	public void setSms_state(String sms_state) {
		this.sms_state = sms_state;
	}
	public String getSms_result() {
		return sms_result;
	}
	public void setSms_result(String sms_result) {
		this.sms_result = sms_result;
	}
	

}
