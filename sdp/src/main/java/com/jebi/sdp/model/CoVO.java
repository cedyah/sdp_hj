package com.jebi.sdp.model;

public class CoVO extends CustomerVO {
	public CoVO() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	String ord_num;					//주문번호
	String co_num;                  //전표번호(수주번호)
	String ord_date;                //주문일자
	String release_code;            //출고구분
	String ship_code;               //배달구분
	String transport_code;          //화물택배
	String request_date;            //배달요청일
	String slsman;                  //영업담당자
	String man;                  	//인수자
//	String zip;                     //우편번호
//	String addr1;                   //주소
//	String addr2;                   //상세주소
	String phone;                   //전화번호
//	String bigo;                    //비고
	String stat;                    //상태(진도)
	String internet_YN;             //인터넷구분
	String del_YN;                  //삭제구분
	String order_type;              //삭제구분
	
	String ship_code_nm;               //배달구분 코드명
	String transport_code_nm;          //화물택배 코드명
	
	//bizpower 필드
	String saeobjang;           //사업장
	String ilja;               	//작성일
	String jeonpyo_no;          //전표번호
	String geolaecheo_code;		//거래처코드
	String chulgo_gubun;        //출고구분
	String baedal_gubun;        //배달구분
	String yocheongil;          //배달요청일
	String bigo;               	//비고
	String panmae_saeobjang;    //판매사업장
	String panmae_ilja;         //판매일자
	String panmae_jeonpyo_no;   //판매전표번호
	String chulha_saeobjang;    //출하사업장
	String chulha_ilja;         //출하일자
	String chulha_jeonpyo_no;   //출하전표번호
	String yejeongil;           //예정일
	String jindo_code;          //진도코드
	String internet;            //인터넷여부
	String baedal_jangso;       //배달장소
	String insuja;              //인수자
	String tel_no;              //인수자 전화번호
	String deunglogil;          //등록일
	String taksong_point_yn;    //탁송포인트사용여부
	String zip;               	//우편번호
	String addr1;               //지번주소
	String addr2;               //상세주소
	
	String baedal_gubun_nm;     //배달구분명
	String jindo_code_nm;       //진도명

	String yocheong_hh;         //납품요청시간
	String yocheong_mm;         //납품요청분
    String panmae_gubun;        //판매구분
    String hwapye_code;         //화폐코드
	
	
	
	public String getSaeobjang() {
		return saeobjang == null ? "" : saeobjang;
	}
	public void setSaeobjang(String saeobjang) {
		this.saeobjang = saeobjang == null ? "" : saeobjang;
	}
	public String getGeolaecheo_code() {
		return geolaecheo_code == null ? "" : geolaecheo_code;
	}
	public void setGeolaecheo_code(String geolaecheo_code) {
		this.geolaecheo_code = geolaecheo_code == null ? "" : geolaecheo_code;
	}
	public String getChulgo_gubun() {
		return chulgo_gubun == null ? "" : chulgo_gubun;
	}
	public void setChulgo_gubun(String chulgo_gubun) {
		this.chulgo_gubun = chulgo_gubun == null ? "" : chulgo_gubun;
	}
	public String getPanmae_saeobjang() {
		return panmae_saeobjang == null ? "" : panmae_saeobjang;
	}
	public void setPanmae_saeobjang(String panmae_saeobjang) {
		this.panmae_saeobjang = panmae_saeobjang == null ? "" : panmae_saeobjang;
	}
	public String getChulha_saeobjang() {
		return chulha_saeobjang == null ? "" : chulha_saeobjang;
	}
	public void setChulha_saeobjang(String chulha_saeobjang) {
		this.chulha_saeobjang = chulha_saeobjang == null ? "" : chulha_saeobjang;
	}
	public String getChulha_ilja() {
		return chulha_ilja == null ? "" : chulha_ilja;
	}
	public void setChulha_ilja(String chulha_ilja) {
		this.chulha_ilja = chulha_ilja == null ? "" : chulha_ilja;
	}
	public String getChulha_jeonpyo_no() {
		return chulha_jeonpyo_no == null ? "" : chulha_jeonpyo_no;
	}
	public void setChulha_jeonpyo_no(String chulha_jeonpyo_no) {
		this.chulha_jeonpyo_no = chulha_jeonpyo_no == null ? "" : chulha_jeonpyo_no;
	}
	public String getYejeongil() {
		return yejeongil == null ? "" : yejeongil;
	}
	public void setYejeongil(String yejeongil) {
		this.yejeongil = yejeongil == null ? "" : yejeongil;
	}
	public String getInternet() {
		return internet == null ? "" : internet;
	}
	public void setInternet(String internet) {
		this.internet = internet == null ? "" : internet;
	}
	public String getDeunglogil() {
		return deunglogil == null ? "" : deunglogil;
	}
	public void setDeunglogil(String deunglogil) {
		this.deunglogil = deunglogil == null ? "" : deunglogil;
	}
	public String getTaksong_point_yn() {
		return taksong_point_yn == null ? "" : taksong_point_yn;
	}
	public void setTaksong_point_yn(String taksong_point_yn) {
		this.taksong_point_yn = taksong_point_yn == null ? "" : taksong_point_yn;
	}
	public String getJindo_code_nm() {
		return jindo_code_nm == null ? "" : jindo_code_nm;
	}
	public void setJindo_code_nm(String jindo_code_nm) {
		this.jindo_code_nm = jindo_code_nm == null ? "" : jindo_code_nm;
	}
	public String getJindo_code() {
		return jindo_code == null ? "" : jindo_code;
	}
	public void setJindo_code(String jindo_code) {
		this.jindo_code = jindo_code == null ? "" : jindo_code;
	}
	public String getBaedal_gubun_nm() {
		return baedal_gubun_nm == null ? "" : baedal_gubun_nm;
	}
	public void setBaedal_gubun_nm(String baedal_gubun_nm) {
		this.baedal_gubun_nm = baedal_gubun_nm == null ? "" : baedal_gubun_nm;
	}
	public String getIlja() {
		return ilja == null ? "" : ilja;
	}
	public void setIlja(String ilja) {
		this.ilja = ilja == null ? "" : ilja;
	}
	public String getJeonpyo_no() {
		return jeonpyo_no == null ? "" : jeonpyo_no;
	}
	public void setJeonpyo_no(String jeonpyo_no) {
		this.jeonpyo_no = jeonpyo_no == null ? "" : jeonpyo_no;
	}
	public String getBaedal_gubun() {
		return baedal_gubun == null ? "" : baedal_gubun;
	}
	public void setBaedal_gubun(String baedal_gubun) {
		this.baedal_gubun = baedal_gubun == null ? "" : baedal_gubun;
	}
	public String getYocheongil() {
		return yocheongil == null ? "" : yocheongil;
	}
	public void setYocheongil(String yocheongil) {
		this.yocheongil = yocheongil == null ? "" : yocheongil;
	}
	public String getBaedal_jangso() {
		return baedal_jangso == null ? "" : baedal_jangso;
	}
	public void setBaedal_jangso(String baedal_jangso) {
		this.baedal_jangso = baedal_jangso == null ? "" : baedal_jangso;
	}
	public String getInsuja() {
		return insuja == null ? "" : insuja;
	}
	public void setInsuja(String insuja) {
		this.insuja = insuja == null ? "" : insuja;
	}
	public String getTel_no() {
		return tel_no == null ? "" : tel_no;
	}
	public void setTel_no(String tel_no) {
		this.tel_no = tel_no == null ? "" : tel_no;
	}
	public String getPanmae_ilja() {
		return panmae_ilja == null ? "" : panmae_ilja;
	}
	public void setPanmae_ilja(String panmae_ilja) {
		this.panmae_ilja = panmae_ilja == null ? "" : panmae_ilja;
	}
	public String getPanmae_jeonpyo_no() {
		return panmae_jeonpyo_no == null ? "" : panmae_jeonpyo_no;
	}
	public void setPanmae_jeonpyo_no(String panmae_jeonpyo_no) {
		this.panmae_jeonpyo_no = panmae_jeonpyo_no == null ? "" : panmae_jeonpyo_no;
	}
	public String getOrder_type() {
		return order_type == null ? "" : order_type;
	}
	public void setOrder_type(String order_type) {
		this.order_type = order_type == null ? "" : order_type;
	}
	public String getMan() {
		return man == null ? "" : man;
	}
	public void setMan(String man) {
		this.man = man == null ? "" : man;
	}
	public String getShip_code_nm() {
		return ship_code_nm == null ? "" : ship_code_nm;
	}
	public void setShip_code_nm(String ship_code_nm) {
		this.ship_code_nm = ship_code_nm == null ? "" : ship_code_nm;
	}
	public String getTransport_code_nm() {
		return transport_code_nm == null ? "" : transport_code_nm;
	}
	public void setTransport_code_nm(String transport_code_nm) {
		this.transport_code_nm = transport_code_nm == null ? "" : transport_code_nm;
	}
	public String getOrd_num() {
		return ord_num == null ? "" : ord_num;
	}
	public void setOrd_num(String ord_num) {
		this.ord_num = ord_num == null ? "" : ord_num;
	}
	public String getCo_num() {
		return co_num == null ? "" : co_num;
	}
	public void setCo_num(String co_num) {
		this.co_num = co_num == null ? "" : co_num;
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
	public String getRequest_date() {
		return request_date == null ? "" : request_date;
	}
	public void setRequest_date(String request_date) {
		this.request_date = request_date == null ? "" : request_date;
	}
	public String getSlsman() {
		return slsman == null ? "" : slsman;
	}
	public void setSlsman(String slsman) {
		this.slsman = slsman == null ? "" : slsman;
	}
	public String getZip() {
		return zip == null ? "" : zip;
	}
	public void setZip(String zip) {
		this.zip = zip == null ? "" : zip;
	}
	public String getAddr1() {
		return addr1 == null ? "" : addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1 == null ? "" : addr1;
	}
	public String getAddr2() {
		return addr2 == null ? "" : addr2;
	}
	public void setAddr2(String addr2) {
		this.addr2 = addr2 == null ? "" : addr2;
	}
	public String getPhone() {
		return phone == null ? "" : phone;
	}
	public void setPhone(String phone) {
		this.phone = phone == null ? "" : phone;
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
	public String getInternet_YN() {
		return internet_YN == null ? "" : internet_YN;
	}
	public void setInternet_YN(String internet_YN) {
		this.internet_YN = internet_YN == null ? "" : internet_YN;
	}
	public String getDel_YN() {
		return del_YN == null ? "" : del_YN;
	}
	public void setDel_YN(String del_YN) {
		this.del_YN = del_YN == null ? "" : del_YN;
	}
	public String getYocheong_hh() {
		return yocheong_hh;
	}
	public void setYocheong_hh(String yocheong_hh) {
		this.yocheong_hh = yocheong_hh;
	}
	public String getYocheong_mm() {
		return yocheong_mm;
	}
	public void setYocheong_mm(String yocheong_mm) {
		this.yocheong_mm = yocheong_mm;
	}
	public String getPanmae_gubun() {
		return panmae_gubun;
	}
	public void setPanmae_gubun(String panmae_gubun) {
		this.panmae_gubun = panmae_gubun;
	}
	public String getHwapye_code() {
		return hwapye_code;
	}
	public void setHwapye_code(String hwapye_code) {
		this.hwapye_code = hwapye_code;
	}
}
