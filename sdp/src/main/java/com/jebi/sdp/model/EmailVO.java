package com.jebi.sdp.model;

import java.util.*;

public class EmailVO {
	String from;
	String to;
	List li_to = new ArrayList<String>();
	String subject;
	String contents;
	
	public String getFrom() {
		return from == null ? "" : from;
	}
	public void setFrom(String from) {
		this.from = from == null ? "" : from;
	}
	public String getTo() {
		return to == null ? "" : to;
	}
	public void setTo(String to) {
		this.to = to == null ? "" : to;
	}
	public List getLi_to() {
		return li_to;
	}
	public void setLi_to(List li_to) {
		this.li_to = li_to;
	}
	public String getSubject() {
		return subject == null ? "" : subject;
	}
	public void setSubject(String subject) {
		this.subject = subject == null ? "" : subject;
	}
	public String getContents() {
		return contents == null ? "" : contents;
	}
	public void setContents(String contents) {
		this.contents = contents == null ? "" : contents;
	}
}
