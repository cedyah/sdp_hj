package com.jebi.sdp.model;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.*;

import org.bouncycastle.crypto.tls.ServerOnlyTlsAuthentication;
import org.springframework.web.context.request.*;

public class CustomerVO {
	public CustomerVO() throws Exception {
		HttpServletRequest request =((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession(true);
		
		CustomerVO vo = (CustomerVO) session.getAttribute("user");
		if(vo != null) {
			setCust_num(vo.getCust_num());
			setCust_nm(vo.getCust_nm());
			setSet_item_group(vo.getSet_item_group());
			setSet_alarm(vo.getSet_alarm());
			setAuth(vo.getAuth());
			setCust_type(vo.getCust_type());
			setWorkplace(vo.getWorkplace());
			setEmail(vo.getEmail());
			setHp_no(vo.getHp_no());
		}
		
		//검색조건이 없을시 기본 3일로 셋팅
		Date dt = new Date();
		SimpleDateFormat smt = new SimpleDateFormat("yyyy.MM.dd");

		setSearchDate_to(smt.format(dt));
		
		Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        cal.add(Calendar.DATE, -3);		//3일 전
//        cal.add(Calendar.MONTH, -1);		//1달 전

		setSearchDate_from(smt.format(cal.getTime()));
		
		//검색조건에 활용하기 위한 현재 시간 셋팅
		setSearchCurrent_date(new Date());
		
	}
	
	String cust_num;
	String cust_nm;
	String cust_seq;
	String site_ref;
	String name;
	String contact1;
	String contact2;
	String contact3;
	String phone1;
	String phone2;
	String phone3;
	String auth;
	String workplace;
	String email;
	String hp_no;
	
	//포인트 관련
	String cust_point;
	String cust_subsidy;
	String cust_consignment_point;
	
	//인터넷 주문 시스템에서 사용하는 정보
	String cust_type;
	String password;
	String set_item_group;
	String set_alarm;
	
	String del_yn;
	
	String createdBy;
	String createDate;
	String recordDate;
	String updatedBy;
	
	String pageCheck = "N";
	
	//검색관련
	String searchDate_from;				//검색조건 날짜 from
	String searchDate_to;				//검색조건 날짜 to
	String searchDiv;					//검색조건 구분
	String searchText;					//검색조건 텍스트
	String searchYear;					//검색조건 연도
	String searchCheckBox_01;			//검색조건 체크박스01
	String searchRadio_01;			//검색조건 체크박스01
	String searchSelect_01;			//검색조건 select박스01
	String searchInput_01;			//검색 input-1
	String searchInput_02;          //검색 input-2
	Date searchCurrent_date;
	
	String search_daebun;			//검색조건 품목 대분류
	String search_jungbun;			//검색조건 품목 중분류
	
	String search_pgmOption;				//제품검색에서 주문품,보관품 등 조회 조건
	
	//페이징 관련 변수
	String page_current;				//현재 페이지
	String page_totalCnt;				//전체 데이터 갯수
	String page_countPer = "50";		//한 화면에 표시될 갯수
	
	//재고수량 표시를 위한 리스트 변수
	List li_qty_site = new ArrayList();
	String qty_site;
	
	public String getSearch_pgmOption() {
		return search_pgmOption == null ? "" : search_pgmOption;
	}
	public void setSearch_pgmOption(String search_pgmOption) {
		this.search_pgmOption = search_pgmOption == null ? "" : search_pgmOption;
	}
	public String getSearch_daebun() {
		return search_daebun == null ? "" : search_daebun;
	}
	public void setSearch_daebun(String search_daebun) {
		this.search_daebun = search_daebun == null ? "" : search_daebun;
	}
	public String getSearch_jungbun() {
		return search_jungbun == null ? "" : search_jungbun;
	}
	public void setSearch_jungbun(String search_jungbun) {
		this.search_jungbun = search_jungbun == null ? "" : search_jungbun;
	}
	public String getSearchSelect_01() {
		return searchSelect_01 == null ? "" : searchSelect_01;
	}
	public void setSearchSelect_01(String searchSelect_01) {
		this.searchSelect_01 = searchSelect_01 == null ? "" : searchSelect_01;
	}
	public String getSearchInput_01() {
		return searchInput_01 == null ? "" : searchInput_01;
	}
	public void setSearchInput_01(String searchInput_01) {
		this.searchInput_01 = searchInput_01 == null ? "" : searchInput_01;
	}
	public String getSearchInput_02() {
		return searchInput_02 == null ? "" : searchInput_02;
	}
	public void setSearchInput_02(String searchInput_02) {
		this.searchInput_02 = searchInput_02 == null ? "" : searchInput_02;
	}
	public String getHp_no() {
		return hp_no == null ? "" : hp_no;
	}
	public void setHp_no(String hp_no) {
		this.hp_no = hp_no == null ? "" : hp_no;
	}
	public String getEmail() {
		return email == null ? "" : email;
	}
	public void setEmail(String email) {
		this.email = email == null ? "" : email;
	}
	public String getSearchRadio_01() {
		return searchRadio_01 == null ? "" : searchRadio_01;
	}
	public void setSearchRadio_01(String searchRadio_01) {
		this.searchRadio_01 = searchRadio_01 == null ? "" : searchRadio_01;
	}
	public String getCust_nm() {
		return cust_nm == null ? "" : cust_nm;
	}
	public void setCust_nm(String cust_nm) {
		this.cust_nm = cust_nm == null ? "" : cust_nm;
	}
	public Date getSearchCurrent_date() {
		return searchCurrent_date;
	}
	public void setSearchCurrent_date(Date searchCurrent_date) {
		this.searchCurrent_date = searchCurrent_date;
	}
	public String getCust_point() {
		return cust_point == null ? "" : cust_point;
	}
	public void setCust_point(String cust_point) {
		this.cust_point = cust_point == null ? "" : cust_point;
	}
	public String getCust_subsidy() {
		return cust_subsidy == null ? "" : cust_subsidy;
	}
	public void setCust_subsidy(String cust_subsidy) {
		this.cust_subsidy = cust_subsidy == null ? "" : cust_subsidy;
	}
	public String getCust_consignment_point() {
		return cust_consignment_point == null ? "" : cust_consignment_point;
	}
	public void setCust_consignment_point(String cust_consignment_point) {
		this.cust_consignment_point = cust_consignment_point == null ? "" : cust_consignment_point;
	}
	public String getPageCheck() {
		return pageCheck == null ? "" : pageCheck;
	}
	public void setPageCheck(String pageCheck) {
		this.pageCheck = pageCheck == null ? "" : pageCheck;
	}
	public String getSearchCheckBox_01() {
		return searchCheckBox_01 == null ? "" : searchCheckBox_01;
	}
	public void setSearchCheckBox_01(String searchCheckBox_01) {
		this.searchCheckBox_01 = searchCheckBox_01 == null ? "" : searchCheckBox_01;
	}
	public String getSearchYear() {
		return searchYear == null ? "" : searchYear;
	}
	public void setSearchYear(String searchYear) {
		this.searchYear = searchYear == null ? "" : searchYear;
	}
	public String getWorkplace() {
		return workplace == null ? "" : workplace;
	}
	public void setWorkplace(String workplace) {
		this.workplace = workplace == null ? "" : workplace;
	}
	public String getAuth() {
		return auth == null ? "" : auth;
	}
	public void setAuth(String auth) {
		this.auth = auth == null ? "" : auth;
	}
	public String getSet_alarm() {
		return set_alarm == null ? "" : set_alarm;
	}
	public void setSet_alarm(String set_alarm) {
		this.set_alarm = set_alarm == null ? "" : set_alarm;
	}
	public List getLi_qty_site() {
		return li_qty_site;
	}
	public void setLi_qty_site(List li_qty_site) {
		this.li_qty_site = li_qty_site;
	}
	public String getQty_site() {
		return qty_site == null ? "" : qty_site;
	}
	public void setQty_site(String qty_site) {
		this.qty_site = qty_site == null ? "" : qty_site;
	}
	public String getDel_yn() {
		return del_yn == null ? "" : del_yn;
	}
	public void setDel_yn(String del_yn) {
		this.del_yn = del_yn == null ? "" : del_yn;
	}
	public String getSet_item_group() {
		return set_item_group == null ? "" : set_item_group;
	}
	public void setSet_item_group(String set_item_group) {
		this.set_item_group = set_item_group == null ? "" : set_item_group;
	}
	public String getPage_countPer() {
		return page_countPer == null ? "" : page_countPer;
	}
	public void setPage_countPer(String page_countPer) {
		this.page_countPer = page_countPer == null ? "" : page_countPer;
	}
	public String getPage_current() {
		return page_current == null ? "1" : page_current;
	}
	public void setPage_current(String page_current) {
		this.page_current = page_current == null ? "1" : page_current;
	}
	public String getPage_totalCnt() {
		return page_totalCnt == null ? "" : page_totalCnt;
	}
	public void setPage_totalCnt(String page_totalCnt) {
		this.page_totalCnt = page_totalCnt == null ? "" : page_totalCnt;
	}
	
	
	public String getSearchDate_from() {
		return searchDate_from == null ? "" : searchDate_from;
	}
	public void setSearchDate_from(String searchDate_from) {
		this.searchDate_from = searchDate_from == null ? "" : searchDate_from;
	}
	public String getSearchDate_to() {
		return searchDate_to == null ? "" : searchDate_to;
	}
	public void setSearchDate_to(String searchDate_to) {
		this.searchDate_to = searchDate_to == null ? "" : searchDate_to;
	}
	public String getSearchDiv() {
		return searchDiv == null ? "" : searchDiv;
	}
	public void setSearchDiv(String searchDiv) {
		this.searchDiv = searchDiv == null ? "" : searchDiv;
	}
	public String getSearchText() {
		return searchText == null ? "" : searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText == null ? "" : searchText;
	}
	public String getPassword() {
		return password == null ? "" : password;
	}
	public void setPassword(String password) {
		this.password = password == null ? "" : password;
	}
	
	
	public String getCust_num() {
		return cust_num == null ? "" : cust_num;
	}

	public void setCust_num(String cust_num) {
		this.cust_num = cust_num == null ? "" : cust_num;
	}

	public String getCust_seq() {
		return cust_seq == null ? "" : cust_seq;
	}

	public void setCust_seq(String cust_seq) {
		this.cust_seq = cust_seq == null ? "" : cust_seq;
	}

	public String getSite_ref() {
		return site_ref == null ? "" : site_ref;
	}

	public void setSite_ref(String site_ref) {
		this.site_ref = site_ref == null ? "" : site_ref;
	}

	public String getName() {
		return name == null ? "" : name;
	}

	public void setName(String name) {
		this.name = name == null ? "" : name;
	}

	public String getContact1() {
		return contact1 == null ? "" : contact1;
	}

	public void setContact1(String contact1) {
		this.contact1 = contact1 == null ? "" : contact1;
	}

	public String getContact2() {
		return contact2 == null ? "" : contact2;
	}

	public void setContact2(String contact2) {
		this.contact2 = contact2 == null ? "" : contact2;
	}

	public String getContact3() {
		return contact3 == null ? "" : contact3;
	}

	public void setContact3(String contact3) {
		this.contact3 = contact3 == null ? "" : contact3;
	}

	public String getPhone1() {
		return phone1 == null ? "" : phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1 == null ? "" : phone1;
	}

	public String getPhone2() {
		return phone2 == null ? "" : phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2 == null ? "" : phone2;
	}

	public String getPhone3() {
		return phone3 == null ? "" : phone3;
	}

	public void setPhone3(String phone3) {
		this.phone3 = phone3 == null ? "" : phone3;
	}

	public String getCust_type() {
		return cust_type == null ? "" : cust_type;
	}

	public void setCust_type(String cust_type) {
		this.cust_type = cust_type == null ? "" : cust_type;
	}

	public String getCreatedBy() {
		return createdBy == null ? "" : createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy == null ? "" : createdBy;
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

	public String getUpdatedBy() {
		return updatedBy == null ? "" : updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy == null ? "" : updatedBy;
	}
}


