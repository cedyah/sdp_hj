package com.jebi.sdp.model;

public class CoItemVO extends ItemVO{

	public CoItemVO() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	//COITEM_MST 필드
	String ord_num;
	String ord_line;
	String ord_date;
	String release_code;
	String ship_code;
	String transport_code;
	String qty_invoiced;
	String qty_ordered;
	String qty_shipped;
	String qty_request;				//제조의뢰 수량
	String unstoring_yn;
	String packed_item;
	String co_num;
	String co_line;
	String co_release;
	String co_date;
	String co_site;
	String inv_num;
	String inv_line;
	String inv_date;
	String inv_site;
//	String bigo;
//	String stat;
	String internet_yn;
	String keep_qty;
	String qty_keep;
	String related_item;		//주재 품목 코드
	
	//bizpower 필드
	String sunbeon;
	String jepum_code;
	String pummyeong;
	String disp_yn;
	String panmae_danwi_a;
	String panmae_danwi_b;
	String pojang_danwi_a;
	String pojang_danwi_b;
	String panmae_sulyang;
	String bo_sulyang;
	String bo_sulyang_c;
	String bigo;
	String stat;
	String panmae_jeonpyo_no;
	String lot_no;
	String ibgo_ilja;
	String saeobjang;
	String restriction;		//제한품 여부
	String panmae_danwi;
	
	public String getRestriction() {
		return restriction == null ? "" : restriction;
	}
	public void setRestriction(String restriction) {
		this.restriction = restriction == null ? "" : restriction;
	}
	public String getSaeobjang() {
		return saeobjang == null ? "" : saeobjang;
	}
	public void setSaeobjang(String saeobjang) {
		this.saeobjang = saeobjang == null ? "" : saeobjang;
	}
	public String getPojang_danwi_a() {
		return pojang_danwi_a == null ? "" : pojang_danwi_a;
	}
	public void setPojang_danwi_a(String pojang_danwi_a) {
		this.pojang_danwi_a = pojang_danwi_a == null ? "" : pojang_danwi_a;
	}
	public String getPojang_danwi_b() {
		return pojang_danwi_b == null ? "" : pojang_danwi_b;
	}
	public void setPojang_danwi_b(String pojang_danwi_b) {
		this.pojang_danwi_b = pojang_danwi_b == null ? "" : pojang_danwi_b;
	}
	public String getLot_no() {
		return lot_no == null ? "" : lot_no;
	}
	public void setLot_no(String lot_no) {
		this.lot_no = lot_no == null ? "" : lot_no;
	}
	public String getIbgo_ilja() {
		return ibgo_ilja == null ? "" : ibgo_ilja;
	}
	public void setIbgo_ilja(String ibgo_ilja) {
		this.ibgo_ilja = ibgo_ilja == null ? "" : ibgo_ilja;
	}
	public String getPanmae_jeonpyo_no() {
		return panmae_jeonpyo_no == null ? "" : panmae_jeonpyo_no;
	}
	public void setPanmae_jeonpyo_no(String panmae_jeonpyo_no) {
		this.panmae_jeonpyo_no = panmae_jeonpyo_no == null ? "" : panmae_jeonpyo_no;
	}
	public String getBo_sulyang() {
		return bo_sulyang == null ? "" : bo_sulyang;
	}
	public void setBo_sulyang(String bo_sulyang) {
		this.bo_sulyang = bo_sulyang == null ? "" : bo_sulyang;
	}
	public String getBo_sulyang_c() {
		return bo_sulyang_c == null ? "" : bo_sulyang_c;
	}
	public void setBo_sulyang_c(String bo_sulyang_c) {
		this.bo_sulyang_c = bo_sulyang_c == null ? "" : bo_sulyang_c;
	}
	public String getSunbeon() {
		return sunbeon == null ? "" : sunbeon;
	}
	public void setSunbeon(String sunbeon) {
		this.sunbeon = sunbeon == null ? "" : sunbeon;
	}
	public String getJepum_code() {
		return jepum_code == null ? "" : jepum_code;
	}
	public void setJepum_code(String jepum_code) {
		this.jepum_code = jepum_code == null ? "" : jepum_code;
	}
	public String getPummyeong() {
		return pummyeong == null ? "" : pummyeong;
	}
	public void setPummyeong(String pummyeong) {
		this.pummyeong = pummyeong == null ? "" : pummyeong;
	}
	public String getDisp_yn() {
		return disp_yn == null ? "" : disp_yn;
	}
	public void setDisp_yn(String disp_yn) {
		this.disp_yn = disp_yn == null ? "" : disp_yn;
	}
	public String getPanmae_danwi_a() {
		return panmae_danwi_a == null ? "" : panmae_danwi_a;
	}
	public void setPanmae_danwi_a(String panmae_danwi_a) {
		this.panmae_danwi_a = panmae_danwi_a == null ? "" : panmae_danwi_a;
	}
	public String getPanmae_danwi_b() {
		return panmae_danwi_b == null ? "" : panmae_danwi_b;
	}
	public void setPanmae_danwi_b(String panmae_danwi_b) {
		this.panmae_danwi_b = panmae_danwi_b == null ? "" : panmae_danwi_b;
	}
	public String getPanmae_sulyang() {
		return panmae_sulyang == null ? "" : panmae_sulyang;
	}
	public void setPanmae_sulyang(String panmae_sulyang) {
		this.panmae_sulyang = panmae_sulyang == null ? "" : panmae_sulyang;
	}
	public String getRelated_item() {
		return related_item == null ? "" : related_item;
	}
	public void setRelated_item(String related_item) {
		this.related_item = related_item == null ? "" : related_item;
	}
	public String getQty_keep() {
		return qty_keep == null ? "" : qty_keep;
	}
	public void setQty_keep(String qty_keep) {
		this.qty_keep = qty_keep == null ? "" : qty_keep;
	}
	public String getQty_request() {
		return qty_request == null ? "" : qty_request;
	}
	public void setQty_request(String qty_request) {
		this.qty_request = qty_request == null ? "" : qty_request;
	}
	public String getKeep_qty() {
		return keep_qty == null ? "" : keep_qty;
	}
	public void setKeep_qty(String keep_qty) {
		this.keep_qty = keep_qty == null ? "" : keep_qty;
	}
	public String getOrd_num() {
		return ord_num == null ? "" : ord_num;
	}
	public void setOrd_num(String ord_num) {
		this.ord_num = ord_num == null ? "" : ord_num;
	}
	public String getOrd_line() {
		return ord_line == null ? "" : ord_line;
	}
	public void setOrd_line(String ord_line) {
		this.ord_line = ord_line == null ? "" : ord_line;
	}
	public String getOrd_date() {
		return ord_date == null ? "" : ord_date;
	}
	public void setOrd_date(String ord_date) {
		this.ord_date = ord_date == null ? "" : ord_date;
	}
	public String getRelease_code() {
		return release_code == null ? "" : release_code;
	}
	public void setRelease_code(String release_code) {
		this.release_code = release_code == null ? "" : release_code;
	}
	public String getShip_code() {
		return ship_code == null ? "" : ship_code;
	}
	public void setShip_code(String ship_code) {
		this.ship_code = ship_code == null ? "" : ship_code;
	}
	public String getTransport_code() {
		return transport_code == null ? "" : transport_code;
	}
	public void setTransport_code(String transport_code) {
		this.transport_code = transport_code == null ? "" : transport_code;
	}
	public String getQty_invoiced() {
		return qty_invoiced == null ? "" : qty_invoiced;
	}
	public void setQty_invoiced(String qty_invoiced) {
		this.qty_invoiced = qty_invoiced == null ? "" : qty_invoiced;
	}
	public String getQty_ordered() {
		return qty_ordered == null ? "" : qty_ordered;
	}
	public void setQty_ordered(String qty_ordered) {
		this.qty_ordered = qty_ordered == null ? "" : qty_ordered;
	}
	public String getQty_shipped() {
		return qty_shipped == null ? "" : qty_shipped;
	}
	public void setQty_shipped(String qty_shipped) {
		this.qty_shipped = qty_shipped == null ? "" : qty_shipped;
	}
	public String getUnstoring_yn() {
		return unstoring_yn == null ? "" : unstoring_yn;
	}
	public void setUnstoring_yn(String unstoring_yn) {
		this.unstoring_yn = unstoring_yn == null ? "" : unstoring_yn;
	}
	public String getPacked_item() {
		return packed_item == null ? "" : packed_item;
	}
	public void setPacked_item(String packed_item) {
		this.packed_item = packed_item == null ? "" : packed_item;
	}
	public String getCo_num() {
		return co_num == null ? "" : co_num;
	}
	public void setCo_num(String co_num) {
		this.co_num = co_num == null ? "" : co_num;
	}
	public String getCo_line() {
		return co_line == null ? "" : co_line;
	}
	public void setCo_line(String co_line) {
		this.co_line = co_line == null ? "" : co_line;
	}
	public String getCo_release() {
		return co_release == null ? "" : co_release;
	}
	public void setCo_release(String co_release) {
		this.co_release = co_release == null ? "" : co_release;
	}
	public String getCo_date() {
		return co_date == null ? "" : co_date;
	}
	public void setCo_date(String co_date) {
		this.co_date = co_date == null ? "" : co_date;
	}
	public String getCo_site() {
		return co_site == null ? "" : co_site;
	}
	public void setCo_site(String co_site) {
		this.co_site = co_site == null ? "" : co_site;
	}
	public String getInv_num() {
		return inv_num == null ? "" : inv_num;
	}
	public void setInv_num(String inv_num) {
		this.inv_num = inv_num == null ? "" : inv_num;
	}
	public String getInv_line() {
		return inv_line == null ? "" : inv_line;
	}
	public void setInv_line(String inv_line) {
		this.inv_line = inv_line == null ? "" : inv_line;
	}
	public String getInv_date() {
		return inv_date == null ? "" : inv_date;
	}
	public void setInv_date(String inv_date) {
		this.inv_date = inv_date == null ? "" : inv_date;
	}
	public String getInv_site() {
		return inv_site == null ? "" : inv_site;
	}
	public void setInv_site(String inv_site) {
		this.inv_site = inv_site == null ? "" : inv_site;
	}
	public String getBigo() {
		return bigo == null ? "" : bigo;
	}
	public void setBigo(String bigo) {
		this.bigo = bigo == null ? "" : bigo;
	}
	public String getStat() {
		return stat == null ? "" : stat;
	}
	public void setStat(String stat) {
		this.stat = stat == null ? "" : stat;
	}
	public String getInternet_yn() {
		return internet_yn == null ? "" : internet_yn;
	}
	public void setInternet_yn(String internet_yn) {
		this.internet_yn = internet_yn == null ? "" : internet_yn;
	}
	public String getPanmae_danwi() {
		return panmae_danwi;
	}
	public void setPanmae_danwi(String panmae_danwi) {
		this.panmae_danwi = panmae_danwi;
	}
}
