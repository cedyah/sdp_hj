package com.jebi.sdp.model;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;

public class ItemVO extends CustomerVO {
	public ItemVO() throws Exception {
		super();
	}
	String item;				//제품코드
	String item_type;			//제한품 구분(제한, 비제한)
	String sales_type;			//판매구분(시판품,주문품)
	String product_code;		//제품군
	String description;			//제품명
	String qty_allocjob;		//최소판매수량
	String u_m;					//판매단위
	String keep_on_hand;		//보관품 재고수량
	String uf_inventory_flag;	//상시품 주문품 구분 (상시,주문)
	List uf_salegroup1;		//제품 그룹 코드1
	List uf_salegroup2;		//제품 그룹 코드2
	List uf_salegroup3;		//제품 그룹 코드3
	
	String qty_on_hand;			//본사 재고수량
	String qty_on_hand01;		//본사 재고수량
	String qty_on_hand02;		//본사 재고수량
	String qty_on_hand03;		//본사 재고수량
	String qty_on_hand04;		//본사 재고수량
	String qty_on_hand05;		//본사 재고수량
	
	String qty_input;			//화면에서 입력된 수량
	List paramList;				//아이템 삭제, 이동 등에 활용할 코드들을 배열로 보관
	String fav_check;			//화면에서 사용하는 변수. 즐겨찾기 유무를 표시
	
	String level;				//주재와 부재를 구분하기 위한 레벨
	String related_item;		//주재의 code
	
	String pummog_gubun;		//상시품,주문품 구분
	
	public String getPummog_gubun() {
		return pummog_gubun == null ? "" : pummog_gubun;
	}
	public void setPummog_gubun(String pummog_gubun) {
		this.pummog_gubun = pummog_gubun == null ? "" : pummog_gubun;
	}
	public String getSales_type() {
		return sales_type == null ? "" : sales_type;
	}
	public void setSales_type(String sales_type) {
		this.sales_type = sales_type == null ? "" : sales_type;
	}
	public String getItem_type() {
		return item_type == null ? "" : item_type;
	}
	public void setItem_type(String item_type) {
		this.item_type = item_type == null ? "" : item_type;
	}
	public List getUf_salegroup1() {
		return uf_salegroup1;
	}
	public void setUf_salegroup1(List uf_salegroup1) {
		this.uf_salegroup1 = uf_salegroup1;
	}
	public List getUf_salegroup2() {
		return uf_salegroup2;
	}
	public void setUf_salegroup2(List uf_salegroup2) {
		this.uf_salegroup2 = uf_salegroup2;
	}
	public List getUf_salegroup3() {
		return uf_salegroup3;
	}
	public void setUf_salegroup3(List uf_salegroup3) {
		this.uf_salegroup3 = uf_salegroup3;
	}
	public String getQty_on_hand04() {
		return qty_on_hand04 == null ? "" : qty_on_hand04;
	}
	public void setQty_on_hand04(String qty_on_hand04) {
		this.qty_on_hand04 = qty_on_hand04 == null ? "" : qty_on_hand04;
	}
	public String getQty_on_hand05() {
		return qty_on_hand05 == null ? "" : qty_on_hand05;
	}
	public void setQty_on_hand05(String qty_on_hand05) {
		this.qty_on_hand05 = qty_on_hand05 == null ? "" : qty_on_hand05;
	}
	public String getQty_on_hand() {
		return qty_on_hand == null ? "" : qty_on_hand;
	}
	public void setQty_on_hand(String qty_on_hand) {
		this.qty_on_hand = qty_on_hand == null ? "" : qty_on_hand;
	}
	public String getQty_on_hand01() {
		return qty_on_hand01 == null ? "" : qty_on_hand01;
	}
	public void setQty_on_hand01(String qty_on_hand01) {
		this.qty_on_hand01 = qty_on_hand01 == null ? "" : qty_on_hand01;
	}
	public String getQty_on_hand02() {
		return qty_on_hand02 == null ? "" : qty_on_hand02;
	}
	public void setQty_on_hand02(String qty_on_hand02) {
		this.qty_on_hand02 = qty_on_hand02 == null ? "" : qty_on_hand02;
	}
	public String getQty_on_hand03() {
		return qty_on_hand03 == null ? "" : qty_on_hand03;
	}
	public void setQty_on_hand03(String qty_on_hand03) {
		this.qty_on_hand03 = qty_on_hand03 == null ? "" : qty_on_hand03;
	}
	public String getLevel() {
		return level == null ? "" : level;
	}
	public void setLevel(String level) {
		this.level = level == null ? "" : level;
	}
	public String getRelated_item() {
		return related_item == null ? "" : related_item;
	}
	public void setRelated_item(String related_item) {
		this.related_item = related_item == null ? "" : related_item;
	}
	public String getUf_inventory_flag() {
		return uf_inventory_flag == null ? "" : uf_inventory_flag;
	}
	public void setUf_inventory_flag(String uf_inventory_flag) {
		this.uf_inventory_flag = uf_inventory_flag == null ? "" : uf_inventory_flag;
	}
	public String getFav_check() {
		return fav_check == null ? "" : fav_check;
	}
	public void setFav_check(String fav_check) {
		this.fav_check = fav_check == null ? "" : fav_check;
	}
	public String getKeep_on_hand() {
		return keep_on_hand == null ? "" : keep_on_hand;
	}
	public void setKeep_on_hand(String keep_on_hand) {
		this.keep_on_hand = keep_on_hand == null ? "" : keep_on_hand;
	}
	public List getParamList() {
		return paramList == null ? new ArrayList() : paramList;
	}
	public void setParamList(List paramList) {
		this.paramList = paramList == null ? new ArrayList() : paramList;
	}
	public String getQty_input() {
		return qty_input == null ? "" : qty_input;
	}
	public void setQty_input(String qty_input) {
		this.qty_input = qty_input == null ? "" : qty_input;
	}
	public String getItem() {
		return item == null ? "" : item;
	}
	public void setItem(String item) {
		this.item = item == null ? "" : item;
	}
	public String getProduct_code() {
		return product_code == null ? "" : product_code;
	}
	public void setProduct_code(String product_code) {
		this.product_code = product_code == null ? "" : product_code;
	}
	public String getDescription() {
		return description == null ? "" : description;
	}
	public void setDescription(String description) {
		this.description = description == null ? "" : description;
	}
	public String getQty_allocjob() {
		return qty_allocjob == null ? "" : qty_allocjob;
	}
	public void setQty_allocjob(String qty_allocjob) {
		this.qty_allocjob = qty_allocjob == null ? "" : qty_allocjob;
	}
	public String getU_m() {
		return u_m == null ? "" : u_m;
	}
	public void setU_m(String u_m) {
		this.u_m = u_m == null ? "" : u_m;
	}
}
