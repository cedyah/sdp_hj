package com.jebi.sdp.model;

public class CodeVO {
	String modul_cd;
	String major_cd;
	String minor_cd;
	String cd_nm;
	String use_yn;
	String description;
	String rel_cd1;
	String rel_cd2;
	String rel_cd3;
	String rel_cd4;
	String rel_cd5;
	String seq_no;
	String createdBy;
	String updatedBy;
	String createDate;
	String recordDate;
	String rowpointer;
	String noteexistsflag;
	String inworkflow;
	
	//bizpower쪽에서 사용하는 필드명
	String code;
	String name;
	String daebun;
	String jungbun;
	String jungbun_m;
	
	
	public String getDaebun() {
		return daebun == null ? "" : daebun;
	}
	public void setDaebun(String daebun) {
		this.daebun = daebun == null ? "" : daebun;
	}
	public String getJungbun() {
		return jungbun == null ? "" : jungbun;
	}
	public void setJungbun(String jungbun) {
		this.jungbun = jungbun == null ? "" : jungbun;
	}
	public String getJungbun_m() {
		return jungbun_m == null ? "" : jungbun_m;
	}
	public void setJungbun_m(String jungbun_m) {
		this.jungbun_m = jungbun_m == null ? "" : jungbun_m;
	}
	public String getCode() {
		return code == null ? "" : code;
	}
	public void setCode(String code) {
		this.code = code == null ? "" : code;
	}
	public String getName() {
		return name == null ? "" : name;
	}
	public void setName(String name) {
		this.name = name == null ? "" : name;
	}
	public String getModul_cd() {
		return modul_cd == null ? "" : modul_cd;
	}
	public void setModul_cd(String modul_cd) {
		this.modul_cd = modul_cd == null ? "" : modul_cd;
	}
	public String getMajor_cd() {
		return major_cd == null ? "" : major_cd;
	}
	public void setMajor_cd(String major_cd) {
		this.major_cd = major_cd == null ? "" : major_cd;
	}
	public String getMinor_cd() {
		return minor_cd == null ? "" : minor_cd;
	}
	public void setMinor_cd(String minor_cd) {
		this.minor_cd = minor_cd == null ? "" : minor_cd;
	}
	public String getCd_nm() {
		return cd_nm == null ? "" : cd_nm;
	}
	public void setCd_nm(String cd_nm) {
		this.cd_nm = cd_nm == null ? "" : cd_nm;
	}
	public String getUse_yn() {
		return use_yn == null ? "" : use_yn;
	}
	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn == null ? "" : use_yn;
	}
	public String getDescription() {
		return description == null ? "" : description;
	}
	public void setDescription(String description) {
		this.description = description == null ? "" : description;
	}
	public String getRel_cd1() {
		return rel_cd1 == null ? "" : rel_cd1;
	}
	public void setRel_cd1(String rel_cd1) {
		this.rel_cd1 = rel_cd1 == null ? "" : rel_cd1;
	}
	public String getRel_cd2() {
		return rel_cd2 == null ? "" : rel_cd2;
	}
	public void setRel_cd2(String rel_cd2) {
		this.rel_cd2 = rel_cd2 == null ? "" : rel_cd2;
	}
	public String getRel_cd3() {
		return rel_cd3 == null ? "" : rel_cd3;
	}
	public void setRel_cd3(String rel_cd3) {
		this.rel_cd3 = rel_cd3 == null ? "" : rel_cd3;
	}
	public String getRel_cd4() {
		return rel_cd4 == null ? "" : rel_cd4;
	}
	public void setRel_cd4(String rel_cd4) {
		this.rel_cd4 = rel_cd4 == null ? "" : rel_cd4;
	}
	public String getRel_cd5() {
		return rel_cd5 == null ? "" : rel_cd5;
	}
	public void setRel_cd5(String rel_cd5) {
		this.rel_cd5 = rel_cd5 == null ? "" : rel_cd5;
	}
	public String getSeq_no() {
		return seq_no == null ? "" : seq_no;
	}
	public void setSeq_no(String seq_no) {
		this.seq_no = seq_no == null ? "" : seq_no;
	}
	public String getCreatedBy() {
		return createdBy == null ? "" : createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy == null ? "" : createdBy;
	}
	public String getUpdatedBy() {
		return updatedBy == null ? "" : updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy == null ? "" : updatedBy;
	}
	public String getCreateDate() {
		return createDate == null ? "" : createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate == null ? "" : createDate;
	}
	public String getRecordDate() {
		return recordDate == null ? "" : recordDate;
	}
	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate == null ? "" : recordDate;
	}
	public String getRowpointer() {
		return rowpointer == null ? "" : rowpointer;
	}
	public void setRowpointer(String rowpointer) {
		this.rowpointer = rowpointer == null ? "" : rowpointer;
	}
	public String getNoteexistsflag() {
		return noteexistsflag == null ? "" : noteexistsflag;
	}
	public void setNoteexistsflag(String noteexistsflag) {
		this.noteexistsflag = noteexistsflag == null ? "" : noteexistsflag;
	}
	public String getInworkflow() {
		return inworkflow == null ? "" : inworkflow;
	}
	public void setInworkflow(String inworkflow) {
		this.inworkflow = inworkflow == null ? "" : inworkflow;
	}
}
