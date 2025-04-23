package com.jebi.sdp.model;

public class SmplRequestVO extends CustomerVO {
	public SmplRequestVO() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	
	private String ilja; 				// 의뢰일자
	private String jeonpyo_no; 					// 전표번호
	private String pummyeong; 			// 품명
	private String jechulcheo; 			// 제출처
	private String himang_wanryoil; 	// 희망완료일
	private String cheoliil; 			// 처리일
	private String gogaeg_myeong;      	// 고객명
	private String tel_no;             	// 전화번호
	private String gyeonbon_gubun;     	// 견본구분
	private String jepum_code;			// 제품코드         
	private String wanjepum_myeong;    	// 완제품명
	private String dojang_soji;        	// 도장소지
	private String seolchi_jangso;     	// 설치장소
	private String gyesok_yn;          	// 계속사용여부
	private String sayong_mm;			// 월사용예상량          
	private String pojang_danwi_a;     	// 포장단위 A
	private String pojang_danwi_b;     	// 포장단위 B
	private String sulyang;            	// 수량
	private String sipyeonmaesu;       	// 시편매수
	private String kyeongjaengsa;      	// 경쟁사
	private String hiseogje;           	// 희석제
	private String junggeumsok_yn;     	// 중금속여부
	private String teugkisahang_3;     	// 특기사항 3
	private String pyojun_sipyeon;     	// 표준시편
	private String teugkisahang_2;     	// 특기사항 2
	private String kwangtaeg;          	// 광택
	private String gloss_a;            	// gross A
	private String gloss_b;            	// gross B
	private String doryo_jonglyu;      	// 도료종류
	private String doryo_jonglyu_m;    	// 도료종류명
	private String dojang_bangbeob;    	// 도장방법
	private String jeoncheoli_bangbeob;	// 전처리방법
	private String geonjo_bangbeob;    	// 건조방법
	private String hado_dolyo;         	// 하도도료
	private String teugkisahang_1;     	// 특기사항 1
	private String baedal_gubun;       	// 배달구분
	private String insuja;             	// 인수자
	private String insu_tel;           	// 인수자 전화번호
	private String insu_juso;          	// 인수자 주소(전체)
	private String zip;					// 우편번호
	private String addr1;				//지번주소
	private String addr2;				//상세주소
	private String saeobjang;			//사업장
	private String solid;				//솔리드타입 3:솔리드 4:메탈릭
	
	//코드 한글 출력용 필드 추가
	private String saeobjang_nm;			//사업장
	private String gyeonbon_gubun_nm;		//견본 구분
	private String dojang_soji_nm;			//도장소재
	private String pyojun_sipyeon_nm;		//표준시편
	private String kwangtaeg_nm;			//광택
	private String doryo_jonglyu_nm;		//도료 종류
	private String dojang_bangbeob_nm;		//도장방법
	private String geonjo_bangbeob_nm;		//건조방법
	private String jeoncheoli_bangbeob_nm;	//전처리방법
	private String pojang_danwi_b_nm;		//포장단위
	private String baedal_gubun_nm;			//배달구분
	private String seolchi_jangso_nm;		//설치장소구분
	private String hiseogje_nm;				//희석제유부
	private String junggeumsok_yn_nm;		//중금속 관리여부
	private String solid_nm;				//솔리드 타입 한글
	
	public String getSolid() {
		return solid == null ? "" : solid;
	}
	public void setSolid(String solid) {
		this.solid = solid == null ? "" : solid;
	}
	public String getSolid_nm() {
		return solid_nm == null ? "" : solid_nm;
	}
	public void setSolid_nm(String solid_nm) {
		this.solid_nm = solid_nm == null ? "" : solid_nm;
	}
	public String getSaeobjang_nm() {
		return saeobjang_nm == null ? "" : saeobjang_nm;
	}
	public void setSaeobjang_nm(String saeobjang_nm) {
		this.saeobjang_nm = saeobjang_nm == null ? "" : saeobjang_nm;
	}
	public String getSaeobjang() {
		return saeobjang == null ? "" : saeobjang;
	}
	public void setSaeobjang(String saeobjang) {
		this.saeobjang = saeobjang == null ? "" : saeobjang;
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
	public String getSeolchi_jangso_nm() {
		return seolchi_jangso_nm == null ? "" : seolchi_jangso_nm;
	}
	public void setSeolchi_jangso_nm(String seolchi_jangso_nm) {
		this.seolchi_jangso_nm = seolchi_jangso_nm == null ? "" : seolchi_jangso_nm;
	}
	public String getHiseogje_nm() {
		return hiseogje_nm == null ? "" : hiseogje_nm;
	}
	public void setHiseogje_nm(String hiseogje_nm) {
		this.hiseogje_nm = hiseogje_nm == null ? "" : hiseogje_nm;
	}
	public String getJunggeumsok_yn_nm() {
		return junggeumsok_yn_nm == null ? "" : junggeumsok_yn_nm;
	}
	public void setJunggeumsok_yn_nm(String junggeumsok_yn_nm) {
		this.junggeumsok_yn_nm = junggeumsok_yn_nm == null ? "" : junggeumsok_yn_nm;
	}
	public String getGyeonbon_gubun_nm() {
		return gyeonbon_gubun_nm == null ? "" : gyeonbon_gubun_nm;
	}
	public void setGyeonbon_gubun_nm(String gyeonbon_gubun_nm) {
		this.gyeonbon_gubun_nm = gyeonbon_gubun_nm == null ? "" : gyeonbon_gubun_nm;
	}
	public String getDojang_soji_nm() {
		return dojang_soji_nm == null ? "" : dojang_soji_nm;
	}
	public void setDojang_soji_nm(String dojang_soji_nm) {
		this.dojang_soji_nm = dojang_soji_nm == null ? "" : dojang_soji_nm;
	}
	public String getPyojun_sipyeon_nm() {
		return pyojun_sipyeon_nm == null ? "" : pyojun_sipyeon_nm;
	}
	public void setPyojun_sipyeon_nm(String pyojun_sipyeon_nm) {
		this.pyojun_sipyeon_nm = pyojun_sipyeon_nm == null ? "" : pyojun_sipyeon_nm;
	}
	public String getKwangtaeg_nm() {
		return kwangtaeg_nm == null ? "" : kwangtaeg_nm;
	}
	public void setKwangtaeg_nm(String kwangtaeg_nm) {
		this.kwangtaeg_nm = kwangtaeg_nm == null ? "" : kwangtaeg_nm;
	}
	public String getDoryo_jonglyu_nm() {
		return doryo_jonglyu_nm == null ? "" : doryo_jonglyu_nm;
	}
	public void setDoryo_jonglyu_nm(String doryo_jonglyu_nm) {
		this.doryo_jonglyu_nm = doryo_jonglyu_nm == null ? "" : doryo_jonglyu_nm;
	}
	public String getDojang_bangbeob_nm() {
		return dojang_bangbeob_nm == null ? "" : dojang_bangbeob_nm;
	}
	public void setDojang_bangbeob_nm(String dojang_bangbeob_nm) {
		this.dojang_bangbeob_nm = dojang_bangbeob_nm == null ? "" : dojang_bangbeob_nm;
	}
	public String getGeonjo_bangbeob_nm() {
		return geonjo_bangbeob_nm == null ? "" : geonjo_bangbeob_nm;
	}
	public void setGeonjo_bangbeob_nm(String geonjo_bangbeob_nm) {
		this.geonjo_bangbeob_nm = geonjo_bangbeob_nm == null ? "" : geonjo_bangbeob_nm;
	}
	public String getJeoncheoli_bangbeob_nm() {
		return jeoncheoli_bangbeob_nm == null ? "" : jeoncheoli_bangbeob_nm;
	}
	public void setJeoncheoli_bangbeob_nm(String jeoncheoli_bangbeob_nm) {
		this.jeoncheoli_bangbeob_nm = jeoncheoli_bangbeob_nm == null ? "" : jeoncheoli_bangbeob_nm;
	}
	public String getPojang_danwi_b_nm() {
		return pojang_danwi_b_nm == null ? "" : pojang_danwi_b_nm;
	}
	public void setPojang_danwi_b_nm(String pojang_danwi_b_nm) {
		this.pojang_danwi_b_nm = pojang_danwi_b_nm == null ? "" : pojang_danwi_b_nm;
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
	public String getPummyeong() {
		return pummyeong == null ? "" : pummyeong;
	}
	public void setPummyeong(String pummyeong) {
		this.pummyeong = pummyeong == null ? "" : pummyeong;
	}
	public String getJechulcheo() {
		return jechulcheo == null ? "" : jechulcheo;
	}
	public void setJechulcheo(String jechulcheo) {
		this.jechulcheo = jechulcheo == null ? "" : jechulcheo;
	}
	public String getHimang_wanryoil() {
		return himang_wanryoil == null ? "" : himang_wanryoil;
	}
	public void setHimang_wanryoil(String himang_wanryoil) {
		this.himang_wanryoil = himang_wanryoil == null ? "" : himang_wanryoil;
	}
	public String getCheoliil() {
		return cheoliil == null ? "" : cheoliil;
	}
	public void setCheoliil(String cheoliil) {
		this.cheoliil = cheoliil == null ? "" : cheoliil;
	}
	public String getGogaeg_myeong() {
		return gogaeg_myeong == null ? "" : gogaeg_myeong;
	}
	public void setGogaeg_myeong(String gogaeg_myeong) {
		this.gogaeg_myeong = gogaeg_myeong == null ? "" : gogaeg_myeong;
	}
	public String getTel_no() {
		return tel_no == null ? "" : tel_no;
	}
	public void setTel_no(String tel_no) {
		this.tel_no = tel_no == null ? "" : tel_no;
	}
	public String getGyeonbon_gubun() {
		return gyeonbon_gubun == null ? "" : gyeonbon_gubun;
	}
	public void setGyeonbon_gubun(String gyeonbon_gubun) {
		this.gyeonbon_gubun = gyeonbon_gubun == null ? "" : gyeonbon_gubun;
	}
	public String getJepum_code() {
		return jepum_code == null ? "" : jepum_code;
	}
	public void setJepum_code(String jepum_code) {
		this.jepum_code = jepum_code == null ? "" : jepum_code;
	}
	public String getWanjepum_myeong() {
		return wanjepum_myeong == null ? "" : wanjepum_myeong;
	}
	public void setWanjepum_myeong(String wanjepum_myeong) {
		this.wanjepum_myeong = wanjepum_myeong == null ? "" : wanjepum_myeong;
	}
	public String getDojang_soji() {
		return dojang_soji == null ? "" : dojang_soji;
	}
	public void setDojang_soji(String dojang_soji) {
		this.dojang_soji = dojang_soji == null ? "" : dojang_soji;
	}
	public String getSeolchi_jangso() {
		return seolchi_jangso == null ? "" : seolchi_jangso;
	}
	public void setSeolchi_jangso(String seolchi_jangso) {
		this.seolchi_jangso = seolchi_jangso == null ? "" : seolchi_jangso;
	}
	public String getGyesok_yn() {
		return gyesok_yn == null ? "" : gyesok_yn;
	}
	public void setGyesok_yn(String gyesok_yn) {
		this.gyesok_yn = gyesok_yn == null ? "" : gyesok_yn;
	}
	public String getSayong_mm() {
		return sayong_mm == null ? "" : sayong_mm;
	}
	public void setSayong_mm(String sayong_mm) {
		this.sayong_mm = sayong_mm == null ? "" : sayong_mm;
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
	public String getSulyang() {
		return sulyang == null ? "" : sulyang;
	}
	public void setSulyang(String sulyang) {
		this.sulyang = sulyang == null ? "" : sulyang;
	}
	public String getSipyeonmaesu() {
		return sipyeonmaesu == null ? "" : sipyeonmaesu;
	}
	public void setSipyeonmaesu(String sipyeonmaesu) {
		this.sipyeonmaesu = sipyeonmaesu == null ? "" : sipyeonmaesu;
	}
	public String getKyeongjaengsa() {
		return kyeongjaengsa == null ? "" : kyeongjaengsa;
	}
	public void setKyeongjaengsa(String kyeongjaengsa) {
		this.kyeongjaengsa = kyeongjaengsa == null ? "" : kyeongjaengsa;
	}
	public String getHiseogje() {
		return hiseogje == null ? "" : hiseogje;
	}
	public void setHiseogje(String hiseogje) {
		this.hiseogje = hiseogje == null ? "" : hiseogje;
	}
	public String getJunggeumsok_yn() {
		return junggeumsok_yn == null ? "" : junggeumsok_yn;
	}
	public void setJunggeumsok_yn(String junggeumsok_yn) {
		this.junggeumsok_yn = junggeumsok_yn == null ? "" : junggeumsok_yn;
	}
	public String getTeugkisahang_3() {
		return teugkisahang_3 == null ? "" : teugkisahang_3;
	}
	public void setTeugkisahang_3(String teugkisahang_3) {
		this.teugkisahang_3 = teugkisahang_3 == null ? "" : teugkisahang_3;
	}
	public String getPyojun_sipyeon() {
		return pyojun_sipyeon == null ? "" : pyojun_sipyeon;
	}
	public void setPyojun_sipyeon(String pyojun_sipyeon) {
		this.pyojun_sipyeon = pyojun_sipyeon == null ? "" : pyojun_sipyeon;
	}
	public String getTeugkisahang_2() {
		return teugkisahang_2 == null ? "" : teugkisahang_2;
	}
	public void setTeugkisahang_2(String teugkisahang_2) {
		this.teugkisahang_2 = teugkisahang_2 == null ? "" : teugkisahang_2;
	}
	public String getKwangtaeg() {
		return kwangtaeg == null ? "" : kwangtaeg;
	}
	public void setKwangtaeg(String kwangtaeg) {
		this.kwangtaeg = kwangtaeg == null ? "" : kwangtaeg;
	}
	public String getGloss_a() {
		return gloss_a == null ? "" : gloss_a;
	}
	public void setGloss_a(String gloss_a) {
		this.gloss_a = gloss_a == null ? "" : gloss_a;
	}
	public String getGloss_b() {
		return gloss_b == null ? "" : gloss_b;
	}
	public void setGloss_b(String gloss_b) {
		this.gloss_b = gloss_b == null ? "" : gloss_b;
	}
	public String getDoryo_jonglyu() {
		return doryo_jonglyu == null ? "" : doryo_jonglyu;
	}
	public void setDoryo_jonglyu(String doryo_jonglyu) {
		this.doryo_jonglyu = doryo_jonglyu == null ? "" : doryo_jonglyu;
	}
	public String getDoryo_jonglyu_m() {
		return doryo_jonglyu_m == null ? "" : doryo_jonglyu_m;
	}
	public void setDoryo_jonglyu_m(String doryo_jonglyu_m) {
		this.doryo_jonglyu_m = doryo_jonglyu_m == null ? "" : doryo_jonglyu_m;
	}
	public String getDojang_bangbeob() {
		return dojang_bangbeob == null ? "" : dojang_bangbeob;
	}
	public void setDojang_bangbeob(String dojang_bangbeob) {
		this.dojang_bangbeob = dojang_bangbeob == null ? "" : dojang_bangbeob;
	}
	public String getJeoncheoli_bangbeob() {
		return jeoncheoli_bangbeob == null ? "" : jeoncheoli_bangbeob;
	}
	public void setJeoncheoli_bangbeob(String jeoncheoli_bangbeob) {
		this.jeoncheoli_bangbeob = jeoncheoli_bangbeob == null ? "" : jeoncheoli_bangbeob;
	}
	public String getGeonjo_bangbeob() {
		return geonjo_bangbeob == null ? "" : geonjo_bangbeob;
	}
	public void setGeonjo_bangbeob(String geonjo_bangbeob) {
		this.geonjo_bangbeob = geonjo_bangbeob == null ? "" : geonjo_bangbeob;
	}
	public String getHado_dolyo() {
		return hado_dolyo == null ? "" : hado_dolyo;
	}
	public void setHado_dolyo(String hado_dolyo) {
		this.hado_dolyo = hado_dolyo == null ? "" : hado_dolyo;
	}
	public String getTeugkisahang_1() {
		return teugkisahang_1 == null ? "" : teugkisahang_1;
	}
	public void setTeugkisahang_1(String teugkisahang_1) {
		this.teugkisahang_1 = teugkisahang_1 == null ? "" : teugkisahang_1;
	}
	public String getBaedal_gubun() {
		return baedal_gubun == null ? "" : baedal_gubun;
	}
	public void setBaedal_gubun(String baedal_gubun) {
		this.baedal_gubun = baedal_gubun == null ? "" : baedal_gubun;
	}
	public String getInsuja() {
		return insuja == null ? "" : insuja;
	}
	public void setInsuja(String insuja) {
		this.insuja = insuja == null ? "" : insuja;
	}
	public String getInsu_tel() {
		return insu_tel == null ? "" : insu_tel;
	}
	public void setInsu_tel(String insu_tel) {
		this.insu_tel = insu_tel == null ? "" : insu_tel;
	}
	public String getInsu_juso() {
		return insu_juso == null ? "" : insu_juso;
	}
	public void setInsu_juso(String insu_juso) {
		this.insu_juso = insu_juso == null ? "" : insu_juso;
	}
}
