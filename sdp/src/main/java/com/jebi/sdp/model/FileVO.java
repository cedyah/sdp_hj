package com.jebi.sdp.model;

public class FileVO extends CustomerVO {
	public FileVO() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	String doc_num;			//문서번호
	String file_line;		//파일시퀀스
	String file_nm;			//변경된파일명
	String file_path = "/jevisco/file/";		//파일경로
	String view_cnt;		//파일조회수
	String internet_yn;		//인터넷사용구분
	String original_nm;		//실제파일명
	String file_size;		//파일크기
	
	public String getDoc_num() {
		return doc_num == null ? "" : doc_num;
	}
	public void setDoc_num(String doc_num) {
		this.doc_num = doc_num == null ? "" : doc_num;
	}
	public String getFile_line() {
		return file_line == null ? "" : file_line;
	}
	public void setFile_line(String file_line) {
		this.file_line = file_line == null ? "" : file_line;
	}
	public String getFile_nm() {
		return file_nm == null ? "" : file_nm;
	}
	public void setFile_nm(String file_nm) {
		this.file_nm = file_nm == null ? "" : file_nm;
	}
	public String getFile_path() {
		return file_path == null ? "" : file_path;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path == null ? "" : file_path;
	}
	public String getView_cnt() {
		return view_cnt == null ? "" : view_cnt;
	}
	public void setView_cnt(String view_cnt) {
		this.view_cnt = view_cnt == null ? "" : view_cnt;
	}
	public String getInternet_yn() {
		return internet_yn == null ? "" : internet_yn;
	}
	public void setInternet_yn(String internet_yn) {
		this.internet_yn = internet_yn == null ? "" : internet_yn;
	}
	public String getOriginal_nm() {
		return original_nm == null ? "" : original_nm;
	}
	public void setOriginal_nm(String original_nm) {
		this.original_nm = original_nm == null ? "" : original_nm;
	}
	public String getFile_size() {
		return file_size == null ? "" : file_size;
	}
	public void setFile_size(String file_size) {
		this.file_size = file_size == null ? "" : file_size;
	}
	public String getCreateDate() {
		return createDate == null ? "" : createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate == null ? "" : createDate;
	}
	public String getCreatedBy() {
		return createdBy == null ? "" : createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy == null ? "" : createdBy;
	}
	public String getRecordDate() {
		return recordDate == null ? "" : recordDate;
	}
	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate == null ? "" : recordDate;
	}
	public String getUpdatedBy() {
		return updatedBy == null ? "" : updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy == null ? "" : updatedBy;
	}
}
