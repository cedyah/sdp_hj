package com.jebi.sdp.model;

public class LogVO {
	String no;			//년월일시분초 밀리초(YYYYMMDD_HH24MISSFF2)
	String type;		//타입 login/searching
	String title;		//제목
	String contents;	//로그내용
	String cust_num;	//사용자 아이디
	String cust_nm;		//사용자명
	String auth;		//사용자 권한
	String createdate;
	String createdby;
	String recorddate;
	String updatedby;
	
	public String getNo() {
		return no == null ? "" : no;
	}
	public void setNo(String no) {
		this.no = no == null ? "" : no;
	}
	public String getType() {
		return type == null ? "" : type;
	}
	public void setType(String type) {
		this.type = type == null ? "" : type;
	}
	public String getTitle() {
		return title == null ? "" : title;
	}
	public void setTitle(String title) {
		this.title = title == null ? "" : title;
	}
	public String getContents() {
		return contents == null ? "" : contents;
	}
	public void setContents(String contents) {
		this.contents = contents == null ? "" : contents;
	}
	public String getCust_num() {
		return cust_num == null ? "" : cust_num;
	}
	public void setCust_num(String cust_num) {
		this.cust_num = cust_num == null ? "" : cust_num;
	}
	public String getCust_nm() {
		return cust_nm == null ? "" : cust_nm;
	}
	public void setCust_nm(String cust_nm) {
		this.cust_nm = cust_nm == null ? "" : cust_nm;
	}
	public String getAuth() {
		return auth == null ? "" : auth;
	}
	public void setAuth(String auth) {
		this.auth = auth == null ? "" : auth;
	}
	public String getCreatedate() {
		return createdate == null ? "" : createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate == null ? "" : createdate;
	}
	public String getCreatedby() {
		return createdby == null ? "" : createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby == null ? "" : createdby;
	}
	public String getRecorddate() {
		return recorddate == null ? "" : recorddate;
	}
	public void setRecorddate(String recorddate) {
		this.recorddate = recorddate == null ? "" : recorddate;
	}
	public String getUpdatedby() {
		return updatedby == null ? "" : updatedby;
	}
	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby == null ? "" : updatedby;
	}
}
