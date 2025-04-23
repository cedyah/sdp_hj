package com.jebi.sdp.model;

public class NoticeVO extends CustomerVO {
	public NoticeVO() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	String notice_num;		//공지번호
	String notice_from;		//기간 form
	String notice_to;		//기간 to
	String notice_type;		//구분(A:공지사항, P:신제품, G:상품권, L:대리점 소식)
	String title;			//제목
	String contents;		//내용
	String uploadfile;		//첨부파일(경로)
	String link;			//링크(URL)
	String image;			//이미지(경로)
	String internet_yn;		//인터넷구분
	String view_cnt;		//조회수
	
	String top;				//조회 건수
	
	public String getTop() {
		return top == null ? "1" : top;
	}
	public void setTop(String top) {
		this.top = top == null ? "1" : top;
	}
	public String getView_cnt() {
		return view_cnt == null ? "" : view_cnt;
	}
	public void setView_cnt(String view_cnt) {
		this.view_cnt = view_cnt == null ? "" : view_cnt;
	}
	public String getNotice_num() {
		return notice_num == null ? "" : notice_num;
	}
	public void setNotice_num(String notice_num) {
		this.notice_num = notice_num == null ? "" : notice_num;
	}
	public String getNotice_from() {
		return notice_from == null ? "" : notice_from;
	}
	public void setNotice_from(String notice_from) {
		this.notice_from = notice_from == null ? "" : notice_from;
	}
	public String getNotice_to() {
		return notice_to == null ? "" : notice_to;
	}
	public void setNotice_to(String notice_to) {
		this.notice_to = notice_to == null ? "" : notice_to;
	}
	public String getNotice_type() {
		return notice_type == null ? "" : notice_type;
	}
	public void setNotice_type(String notice_type) {
		this.notice_type = notice_type == null ? "" : notice_type;
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
	public String getUploadfile() {
		return uploadfile == null ? "" : uploadfile;
	}
	public void setUploadfile(String uploadfile) {
		this.uploadfile = uploadfile == null ? "" : uploadfile;
	}
	public String getLink() {
		return link == null ? "" : link;
	}
	public void setLink(String link) {
		this.link = link == null ? "" : link;
	}
	public String getImage() {
		return image == null ? "" : image;
	}
	public void setImage(String image) {
		this.image = image == null ? "" : image;
	}
	public String getInternet_yn() {
		return internet_yn == null ? "" : internet_yn;
	}
	public void setInternet_yn(String internet_yn) {
		this.internet_yn = internet_yn == null ? "" : internet_yn;
	}
}
