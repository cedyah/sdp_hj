package com.jebi.sdp.model;

public class FavoriteGroupVO extends CustomerVO {
	public FavoriteGroupVO() throws Exception {
		super();
	}
	
	String group_cd;
	String group_seq;
	String group_cdnm;
	
	String select_group_cd;		//선택된 그룹 코드 저장
	
	public String getSelect_group_cd() {
		return select_group_cd == null ? "" : select_group_cd;
	}
	public void setSelect_group_cd(String select_group_cd) {
		this.select_group_cd = select_group_cd == null ? "" : select_group_cd;
	}
	public String getGroup_cd() {
		return group_cd == null ? "" : group_cd;
	}
	public void setGroup_cd(String group_cd) {
		this.group_cd = group_cd == null ? "" : group_cd;
	}
	public String getGroup_seq() {
		return group_seq == null ? "" : group_seq;
	}
	public void setGroup_seq(String group_seq) {
		this.group_seq = group_seq == null ? "" : group_seq;
	}
	public String getGroup_cdnm() {
		return group_cdnm == null ? "" : group_cdnm;
	}
	public void setGroup_cdnm(String group_cdnm) {
		this.group_cdnm = group_cdnm == null ? "" : group_cdnm;
	}
	
	
}
