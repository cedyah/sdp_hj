package com.jebi.sdp.service;

import static com.jebi.sdp.common.FormatUtil.getExpDateString;

import java.util.HashMap;
import java.util.List;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jebi.sdp.dao.CmmnDao;
import com.jebi.sdp.model.MsdsHeaderVO;
import com.jebi.sdp.model.MsdsSubVO;

/**
 * MSDS(sdpe0020) 화면의 저장 프로시저 호출.
 *
 * 저장 계열 메서드는 프로시저의 OUT_PARAM 값을 그대로 돌려준다. 정상이면 "OK" 이고,
 * 그 밖의 값은 오류 메시지다. 호출하는 쪽에서 판단한다.
 * 트랜잭션 경계도 호출하는 쪽(컨트롤러)이 기존과 같이 직접 관리한다.
 */
@Component
public class MsdsService {

	@Autowired
	private CmmnDao dao;

	// ---- 트랜잭션 ----

	public void startTransaction() throws Exception {
		dao.startTransaction();
	}

	public void commit() throws Exception {
		dao.commit();
	}

	public void endTransaction() throws Exception {
		dao.endTransaction();
	}

	// ---- 조회 ----

	public Object selectMsdsList(MsdsHeaderVO vo) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ARG_CUST_CD", vo.getCust_num());
		map.put("ARG_FRDT", getExpDateString(vo.getSearchDate_from()));
		map.put("ARG_TODT", getExpDateString(vo.getSearchDate_to()));
		map.put("ARG_SUBMIT_NM", vo.getSearchText());
		map.put("OUT_PARAM", null);

		dao.update("sdpe0020.procedure_selectMsdsList", map);
		return map.get("OUT_PARAM");
	}

	@SuppressWarnings("unchecked")
	public MsdsHeaderVO selectMsdsHeader(MsdsHeaderVO vo) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ARG_CUST_CD", vo.getCust_num());
		map.put("ARG_REQ_DT", getExpDateString(vo.getReq_dt()));
		map.put("ARG_REQ_NO", vo.getReq_no());
		map.put("OUT_PARAM", null);

		dao.update("sdpe0020.procedure_selectMsdsHeader", map);
		return ((List<MsdsHeaderVO>) map.get("OUT_PARAM")).get(0);
	}

	@SuppressWarnings("unchecked")
	public List<MsdsSubVO> selectMsdsSubList(MsdsHeaderVO vo) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ARG_REQ_DT", getExpDateString(vo.getReq_dt()));
		map.put("ARG_REQ_NO", vo.getReq_no());
		map.put("OUT_PARAM", null);

		dao.update("sdpe0020.procedure_selectMsdsSub", map);
		return (List<MsdsSubVO>) map.get("OUT_PARAM");
	}

	/** 새 요청번호 */
	@SuppressWarnings("unchecked")
	public String selectReqNo(MsdsHeaderVO vo) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ARG_TYPE", "MSDS");
		map.put("ARG_DT", getExpDateString(vo.getReq_dt()));
		map.put("OUT_PARAM", null);

		dao.select("sdpe0020.procedure_selectJeonpyoNo", map);
		List<MsdsHeaderVO> list = (List<MsdsHeaderVO>) map.get("OUT_PARAM");
		return list.get(0).getReq_no();
	}

	/** 품목 담당자 메일 주소 목록 */
	@SuppressWarnings("unchecked")
	public List<MsdsSubVO> selectEmail(String itemCd) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ARG_ITEM_CD", itemCd);
		map.put("OUT_PARAM", null);

		dao.select("sdpe0020.procedure_selectEmail", map);
		return (List<MsdsSubVO>) map.get("OUT_PARAM");
	}

	// ---- 저장 ----

	/** flag 는 "insert", "update", "delete". 세 경우의 파라미터가 같다. */
	public String saveHeader(MsdsHeaderVO vo, String flag) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ARG_FLAG", flag);
		map.put("ARG_REQ_DT", getExpDateString(vo.getReq_dt()));
		map.put("ARG_REQ_NO", vo.getReq_no());
		map.put("ARG_CUST_CD", vo.getCust_num());
		map.put("ARG_SUBMIT_NM", vo.getSubmit_nm());
		map.put("ARG_SUBMIT_DT", getExpDateString(vo.getSubmit_dt()));
		map.put("ARG_RMK", vo.getRmk());
		map.put("OUT_PARAM", "");

		dao.select("sdpe0020.procedure_updateMsdsHeader", map);
		return (String) map.get("OUT_PARAM");
	}

	/** 서브 품목 입력. seq 는 기존 코드대로 0부터 시작한다. */
	public String insertSub(MsdsHeaderVO vo, int seq, JSONObject item) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ARG_FLAG", "insert");
		map.put("ARG_REQ_DT", getExpDateString(vo.getReq_dt()));
		map.put("ARG_REQ_NO", vo.getReq_no());
		map.put("ARG_SEQ", Integer.toString(seq));
		map.put("ARG_ITEM_CD", item.getString("item"));
		map.put("ARG_DEPT_CD", "");
		map.put("ARG_EMP_NO", "");
		map.put("OUT_PARAM", "");

		dao.select("sdpe0020.procedure_updateMsdsSub", map);
		return (String) map.get("OUT_PARAM");
	}

	/**
	 * 수정 시 기존 서브 품목 전체 삭제.
	 * 기존 코드가 OUT_PARAM 을 미리 넣지 않아, 프로시저가 값을 채우지 않으면 null 이 돌아온다.
	 */
	public String deleteSubAll(MsdsHeaderVO vo) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ARG_FLAG", "delete");
		map.put("ARG_REQ_DT", getExpDateString(vo.getReq_dt()));
		map.put("ARG_REQ_NO", vo.getReq_no());

		dao.select("sdpe0020.procedure_updateMsdsSub", map);
		return (String) map.get("OUT_PARAM");
	}
}
