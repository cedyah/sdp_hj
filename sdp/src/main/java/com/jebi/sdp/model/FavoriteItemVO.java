package com.jebi.sdp.model;

import java.util.*;

public class FavoriteItemVO extends ItemVO {
	public FavoriteItemVO() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	String group_cd;
	String seq;	
	
	String target_group_cd;		//관심그룹의 아이템 이동시 옮겨야할 대상그룹 저장용
	
	public String getTarget_group_cd() {
		return target_group_cd == null ? "" : target_group_cd;
	}
	public void setTarget_group_cd(String target_group_cd) {
		this.target_group_cd = target_group_cd == null ? "" : target_group_cd;
	}
	public String getGroup_cd() {
		return group_cd == null ? "" : group_cd;
	}
	public void setGroup_cd(String group_cd) {
		this.group_cd = group_cd == null ? "" : group_cd;
	}
	public String getSeq() {
		return seq == null ? "" : seq;
	}
	public void setSeq(String seq) {
		this.seq = seq == null ? "" : seq;
	}
}
