package com.jebi.sdp.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jebi.sdp.dao.CmmnDao;

/**
 * 공통코드 조회 (common.procedure_selectCode).
 * 화면마다 코드그룹(ARG_MAJOR_CD)만 다르다. 예: 4020 배달구분, 4069 판매구분, 4900 화폐코드.
 */
@Component
public class CodeService {

	@Autowired
	private CmmnDao dao;

	public Object selectCodeList(String majorCd) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ARG_MAJOR_CD", majorCd);
		dao.update("common.procedure_selectCode", map);
		return map.get("OUT_PARAM");
	}
}
