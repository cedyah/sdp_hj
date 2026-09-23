package com.jebi.sdp.common;

import java.util.HashMap;

import com.jebi.sdp.model.SampleRequestVO;

/**
 * 샘플의뢰 헤더 저장 프로시저(sdph0050.procedure_updateSampleRequest)의 파라미터 구성.
 * Sdph0050Controller(샘플의뢰)와 Sdph0052Controller(샘플진도/결과)가 같은 파라미터를 쓴다.
 */
public class SampleRequestParams {

	/**
	 * 작성(INSERT) 시 파라미터. 날짜는 getExpDateString 으로 - . / 를 제거해서 넘긴다.
	 */
	public static HashMap<String, Object> forInsert(SampleRequestVO vo) {
		HashMap<String, Object> map = putCommon(new HashMap<String, Object>(), vo);
		map.put("ARG_FLAG", "INSERT");
		map.put("ARG_ILJA", FormatUtil.getExpDateString(vo.getIlja()));
		map.put("ARG_NABPUM_ILJA", FormatUtil.getExpDateString(vo.getNabpum_ilja()));
		map.put("OUT_PARAM", "");
		return map;
	}

	/**
	 * 수정(update) 시 파라미터.
	 * 작성 때와 달리 날짜를 화면 입력값 그대로 넘긴다(기존 동작 유지).
	 */
	public static HashMap<String, Object> forUpdate(SampleRequestVO vo) {
		HashMap<String, Object> map = putCommon(new HashMap<String, Object>(), vo);
		map.put("ARG_FLAG", "update");
		map.put("ARG_ILJA", vo.getIlja());
		map.put("ARG_NABPUM_ILJA", vo.getNabpum_ilja());
		map.put("OUT_PARAM", "");
		return map;
	}

	/** 작성·수정에서 값이 같은 파라미터 */
	private static HashMap<String, Object> putCommon(HashMap<String, Object> map, SampleRequestVO vo) {
		map.put("ARG_SAEOBJANG",        vo.getSaeobjang());
		map.put("ARG_JEONPYO_NO",       vo.getJeonpyo_no());
		map.put("ARG_SIL_GEOLAECHEO",   vo.getSil_geolaecheo());
		map.put("ARG_GYEONBON_GUBUN",   vo.getGyeonbon_gubun());
		map.put("ARG_PUMMOG_BUNRYU",    vo.getPummog_bunryu());
		map.put("ARG_HP_BUNRYU",        vo.getHp_bunryu());
		map.put("ARG_GEOLAECHEO_CODE1", vo.getGeolaecheo_code());
		map.put("ARG_SANGHO1",          vo.getSangho());
		map.put("ARG_GEOLAECHEO_CODE2", vo.getGeolaecheo_code_2());
		map.put("ARG_SANGHO2",          vo.getSangho_2());
		map.put("ARG_GOGAEG_MYEONG",    vo.getGogaeg_myeong());
		map.put("ARG_BALSINJA",         vo.getBalsinja());
		map.put("ARG_SUSIN_BUSEO",      vo.getSusin_buseo());
		map.put("ARG_SUSINJA",          vo.getSusinja());
		map.put("ARG_IBHOIJA",          vo.getIbhoija());
		map.put("ARG_YESANG_GEUMAEG",   vo.getYesang_geumaeg());
		map.put("ARG_SAYONG_GEUMAEG",   vo.getSayong_geumaeg());
		map.put("ARG_HIMANG_GAGYEOG",   vo.getHimang_gagyeog());
		map.put("ARG_EX_GEOLAECHEO",    vo.getEx_geolaecheo());
		map.put("ARG_EX_GYEONBON_YN",   vo.getEx_gyeonbon_yn());
		map.put("ARG_DOJANG_BANGBEOB",  vo.getDojang_bangbeob());
		map.put("ARG_DOJANG_GONGJEONG", vo.getDojang_gongjeong());
		map.put("ARG_GEONJO_BANGBEOB",  vo.getGeonjo_bangbeob());
		map.put("ARG_DORYO_TYPE",       vo.getDoryo_type());
		map.put("ARG_SOJAE_JONGLYU",    vo.getSojae_jonglyu());
		map.put("ARG_GITA_YOGU6",       vo.getGita_yogu6());
		map.put("ARG_GITA_YOGU3",       vo.getGita_yogu3());
		map.put("ARG_BIGO1",            vo.getBigo_1());
		map.put("ARG_BIGO2",            vo.getBigo_2());
		map.put("ARG_BIGO3",            vo.getBigo_3());
		map.put("ARG_GYEOLGWA_GIHAN",   vo.getGyeolgwa_gihan());
		return map;
	}
}
