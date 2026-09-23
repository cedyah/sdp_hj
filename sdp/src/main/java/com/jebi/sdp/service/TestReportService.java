package com.jebi.sdp.service;

import static com.jebi.sdp.common.FormatUtil.getExpDateString;

import java.util.HashMap;
import java.util.List;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jebi.sdp.dao.CmmnDao;
import com.jebi.sdp.model.TestReportItemVO;
import com.jebi.sdp.model.TestReportVO;

/**
 * 시험성적서(sdpe0010) 화면의 저장 프로시저 호출.
 *
 * 저장 계열 메서드는 프로시저의 OUT_PARAM 값을 그대로 돌려준다. 정상이면 "OK" 이고,
 * 그 밖의 값은 오류 메시지다. 호출하는 쪽에서 판단한다.
 * 트랜잭션 경계도 호출하는 쪽(컨트롤러)이 기존과 같이 직접 관리한다.
 */
@Component
public class TestReportService {

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

	public Object selectTestReportList(TestReportVO vo) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ARG_CUST_CD", vo.getCust_num());
		map.put("ARG_FRDT", getExpDateString(vo.getSearchDate_from()));
		map.put("ARG_TODT", getExpDateString(vo.getSearchDate_to()));
		map.put("ARG_SUBMIT_NM", vo.getSearchText());
		map.put("OUT_PARAM", null);

		dao.update("sdpe0010.procedure_selectTestReportList", map);
		return map.get("OUT_PARAM");
	}

	@SuppressWarnings("unchecked")
	public TestReportVO selectTestReportHeader(TestReportVO vo) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ARG_CUST_CD", vo.getCust_num());
		map.put("ARG_REQ_DT", getExpDateString(vo.getReq_dt()));
		map.put("ARG_REQ_NO", vo.getReq_no());
		map.put("OUT_PARAM", null);

		dao.update("sdpe0010.procedure_selectTestReportHeader", map);
		return ((List<TestReportVO>) map.get("OUT_PARAM")).get(0);
	}

	@SuppressWarnings("unchecked")
	public List<TestReportItemVO> selectTestReportSubList(TestReportVO vo) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ARG_REQ_DT", getExpDateString(vo.getReq_dt()));
		map.put("ARG_REQ_NO", vo.getReq_no());
		map.put("OUT_PARAM", null);

		dao.update("sdpe0010.procedure_selectTestReportSub", map);
		return (List<TestReportItemVO>) map.get("OUT_PARAM");
	}

	/** 새 요청번호 */
	@SuppressWarnings("unchecked")
	public String selectReqNo(TestReportVO vo) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ARG_TYPE", "TEST_SHEET");
		map.put("ARG_DT", getExpDateString(vo.getReq_dt()));
		map.put("OUT_PARAM", "");

		dao.select("sdpe0010.procedure_selectReqNo", map);
		List<TestReportVO> list = (List<TestReportVO>) map.get("OUT_PARAM");
		return list.get(0).getReq_no();
	}

	/** 품목·로트번호로 시험성적서 담당자 메일 주소 목록을 가져온다. */
	@SuppressWarnings("unchecked")
	public List<TestReportVO> selectRcptTo(String itemCd, String lotNo) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ARG_ITEM_CD", itemCd);
		map.put("ARG_LOT_NO", lotNo);
		map.put("OUT_PARAM", null);

		dao.update("sdpe0010.procedure_selectRcptTo", map);
		return (List<TestReportVO>) map.get("OUT_PARAM");
	}

	// ---- 저장 ----

	/** flag 는 "insert" 또는 "update". 두 경우의 파라미터가 같다. */
	public String saveHeader(TestReportVO vo, String flag) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ARG_FLAG", flag);
		map.put("ARG_REQ_DT", getExpDateString(vo.getReq_dt()));
		map.put("ARG_REQ_NO", vo.getReq_no());
		map.put("ARG_CUST_CD", vo.getCust_num());
		map.put("ARG_SUBMIT_NM_K", vo.getSubmit_nm_k());
		map.put("ARG_SUBMIT_NM_E", vo.getSubmit_nm_e());
		map.put("ARG_SUBMIT_DT", getExpDateString(vo.getSubmit_dt()));
		map.put("ARG_LANG", vo.getLang());
		map.put("ARG_RMK", vo.getRmk());
		map.put("OUT_PARAM", "");

		dao.update("sdpe0010.procedure_updateTestReportHeader", map);
		return (String) map.get("OUT_PARAM");
	}

	/** 요청 취소(삭제 표시). 작성·수정 때와 달리 제출처 등은 넘기지 않는다(기존 동작). */
	public String deleteHeader(TestReportVO vo) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ARG_FLAG", "delete");
		map.put("ARG_REQ_DT", getExpDateString(vo.getReq_dt()));
		map.put("ARG_REQ_NO", vo.getReq_no());
		map.put("ARG_CUST_CD", vo.getCust_num());
		map.put("OUT_PARAM", "");

		dao.update("sdpe0010.procedure_updateTestReportHeader", map);
		return (String) map.get("OUT_PARAM");
	}

	/** 서브 품목 입력. seq 는 기존 코드대로 0부터 시작한다. */
	public String insertSub(TestReportVO vo, int seq, JSONObject item) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ARG_FLAG", "insert");
		map.put("ARG_REQ_DT", getExpDateString(vo.getReq_dt()));
		map.put("ARG_REQ_NO", vo.getReq_no());
		map.put("ARG_SEQ", Integer.toString(seq));
		map.put("ARG_ITEM_CD", item.getString("item"));
		map.put("ARG_LOT_NO", item.getString("lot_no"));
		map.put("ARG_SU", item.getString("su"));
		map.put("OUT_PARAM", "");

		dao.update("sdpe0010.procedure_updateTestReportSub", map);
		return (String) map.get("OUT_PARAM");
	}

	/** 수정 시 기존 서브 품목 전체 삭제 */
	public String deleteSubAll(TestReportVO vo) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ARG_FLAG", "delete");
		map.put("ARG_REQ_DT", getExpDateString(vo.getReq_dt()));
		map.put("ARG_REQ_NO", vo.getReq_no());
		map.put("OUT_PARAM", "");

		dao.update("sdpe0010.procedure_updateTestReportSub", map);
		return (String) map.get("OUT_PARAM");
	}
}
