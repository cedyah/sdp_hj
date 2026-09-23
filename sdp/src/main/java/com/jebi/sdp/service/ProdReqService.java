package com.jebi.sdp.service;

import static com.jebi.sdp.common.FormatUtil.getExpDateString;

import java.util.HashMap;
import java.util.List;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jebi.sdp.dao.CmmnDao;
import com.jebi.sdp.model.NprodReqHeaderVO;
import com.jebi.sdp.model.NprodReqSubVO;
import com.jebi.sdp.model.ProdReqHeaderVO;
import com.jebi.sdp.model.ProdReqSubVO;

/**
 * 제조의뢰(sdpa0040) 화면의 저장 프로시저 호출.
 *
 * 저장 계열 메서드는 프로시저의 OUT_PARAM 값을 그대로 돌려준다. 정상이면 "OK" 이고,
 * 그 밖의 값은 오류 메시지다. 호출하는 쪽에서 판단한다.
 * 트랜잭션 경계도 호출하는 쪽(컨트롤러)이 기존과 같이 직접 관리한다.
 */
@Component
public class ProdReqService {

	@Autowired
	private CmmnDao dao;

	// ---- 트랜잭션 ----

	public void startTransaction() throws Exception {
		dao.startTransaction();
	}

	public void startBatch() throws Exception {
		dao.startBatch();
	}

	public void executeBatch() throws Exception {
		dao.executeBatch();
	}

	public void commit() throws Exception {
		dao.commit();
	}

	public void endTransaction() throws Exception {
		dao.endTransaction();
	}

	// ---- 공통 ----

	/** 새 전표번호. slipType 은 제조의뢰 "W2", 신규제조의뢰 "03". */
	@SuppressWarnings("unchecked")
	public String selectJeonpyoNo(String workplace, String slipType, String ilja) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ARG_BIZ_AREA_CD", workplace);
		map.put("ARG_SLIP_TYPE", slipType);
		map.put("ARG_DT", getExpDateString(ilja));
		map.put("OUT_PARAM", null);

		dao.select("sdpa0040.procedure_selectJeonpyoNo", map);
		List<ProdReqHeaderVO> list = (List<ProdReqHeaderVO>) map.get("OUT_PARAM");
		return list.get(0).getJeonpyo_no();
	}

	// ---- 제조의뢰 ----

	public Object selectProdReqList(ProdReqHeaderVO vo) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ARG_BIZ_AREA_CD", vo.getWorkplace());
		map.put("ARG_CUST_CD", vo.getCust_num());
		map.put("ARG_FRDT", getExpDateString(vo.getSearchDate_from()));
		map.put("ARG_TODT", getExpDateString(vo.getSearchDate_to()));
		map.put("ARG_SEARCH_TYPE", vo.getSearchDiv().equals("") ? "1" : vo.getSearchDiv());
		map.put("OUT_PARAM", null);

		dao.select("sdpa0040.procedure_selectProdReqList", map);
		return map.get("OUT_PARAM");
	}

	@SuppressWarnings("unchecked")
	public ProdReqHeaderVO selectProdReqHeader(ProdReqHeaderVO vo) throws Exception {
		HashMap<String, Object> map = keyOfProdReq(vo);
		dao.select("sdpa0040.procedure_selectProdReqHeader", map);
		return ((List<ProdReqHeaderVO>) map.get("OUT_PARAM")).get(0);
	}

	@SuppressWarnings("unchecked")
	public List<ProdReqSubVO> selectProdReqSubList(ProdReqHeaderVO vo) throws Exception {
		HashMap<String, Object> map = keyOfProdReq(vo);
		dao.select("sdpa0040.procedure_selectProdReqSub", map);
		return (List<ProdReqSubVO>) map.get("OUT_PARAM");
	}

	/** 제조의뢰 헤더·서브 조회 조건 (거래처, 의뢰일자, 전표번호, 구분) */
	private static HashMap<String, Object> keyOfProdReq(ProdReqHeaderVO vo) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ARG_CUST_CD", vo.getCust_num());
		map.put("ARG_REQ_DT", getExpDateString(vo.getIlja()));
		map.put("ARG_REQ_NO", vo.getJeonpyo_no());
		map.put("ARG_GUBUN", vo.getProduct_type());
		map.put("OUT_PARAM", null);
		return map;
	}

	public String insertProdReqHeader(ProdReqHeaderVO vo) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ARG_FLAG", "insert");
		map.put("ARG_BIZ_AREA_CD", vo.getWorkplace());
		map.put("ARG_REQ_DT", getExpDateString(vo.getIlja()));
		map.put("ARG_REQ_NO", vo.getJeonpyo_no());
		map.put("ARG_CUST_CD", vo.getCust_num());
		map.put("ARG_DELY_DT", getExpDateString(vo.getEuiloiil()));
		map.put("ARG_PANMAE_GUBUN", vo.getPanmae_gubun());
		map.put("ARG_RMK", vo.getBigo());
		map.put("OUT_PARAM", "");

		dao.select("sdpa0040.procedure_updateProdReqHeader", map);
		return (String) map.get("OUT_PARAM");
	}

	/** 수정 시에는 작성 때와 달리 판매구분 대신 배달·수령인 정보를 넘긴다(기존 동작). */
	public String updateProdReqHeader(ProdReqHeaderVO vo) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ARG_FLAG", "update");
		map.put("ARG_BIZ_AREA_CD", vo.getWorkplace());
		map.put("ARG_REQ_DT", getExpDateString(vo.getIlja()));
		map.put("ARG_REQ_NO", vo.getJeonpyo_no());
		map.put("ARG_CUST_CD", vo.getCust_num());
		map.put("ARG_DELY_DT", getExpDateString(vo.getEuiloiil()));
		map.put("ARG_DELY_TYPE", vo.getBaedal_gubun());
		map.put("ARG_RMK", vo.getBigo());
		map.put("ARG_RECVER", vo.getInsuja());
		map.put("ARG_TEL_NO", vo.getTel_no());
		map.put("ARG_ZIP", vo.getZip());
		map.put("ARG_ADDR1", vo.getAddr1());
		map.put("ARG_ADDR2", vo.getAddr2());
		map.put("OUT_PARAM", "");

		dao.select("sdpa0040.procedure_updateProdReqHeader", map);
		return (String) map.get("OUT_PARAM");
	}

	public String deleteProdReqHeader(ProdReqHeaderVO vo) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ARG_FLAG", "delete");
		map.put("ARG_BIZ_AREA_CD", vo.getWorkplace());
		map.put("ARG_REQ_DT", getExpDateString(vo.getIlja()));
		map.put("ARG_REQ_NO", vo.getJeonpyo_no());
		map.put("ARG_CUST_CD", vo.getCust_num());
		map.put("OUT_PARAM", "");

		dao.select("sdpa0040.procedure_updateProdReqHeader", map);
		return (String) map.get("OUT_PARAM");
	}

	/** 작성 시 서브 품목 입력. 수정 때와 달리 판매구분을 함께 넘긴다(기존 동작). */
	public String insertProdReqSub(ProdReqHeaderVO vo, int seq, JSONObject item) throws Exception {
		HashMap<String, Object> map = subOfProdReq(vo, seq, item);
		map.put("ARG_PANMAE_GUBUN", vo.getPanmae_gubun());

		dao.select("sdpa0040.procedure_updateProdReqSub", map);
		return (String) map.get("OUT_PARAM");
	}

	/** 수정 시 서브 품목 입력 */
	public String insertProdReqSubOnUpdate(ProdReqHeaderVO vo, int seq, JSONObject item) throws Exception {
		HashMap<String, Object> map = subOfProdReq(vo, seq, item);

		dao.select("sdpa0040.procedure_updateProdReqSub", map);
		return (String) map.get("OUT_PARAM");
	}

	private static HashMap<String, Object> subOfProdReq(ProdReqHeaderVO vo, int seq, JSONObject item) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ARG_FLAG", "insert");
		map.put("ARG_BIZ_AREA_CD", vo.getWorkplace());
		map.put("ARG_REQ_DT", getExpDateString(vo.getIlja()));
		map.put("ARG_REQ_NO", vo.getJeonpyo_no());
		map.put("ARG_SEQ", Integer.toString(seq));
		map.put("ARG_CUST_CD", vo.getCust_num());
		map.put("ARG_ITEM_CD", item.getString("item"));
		map.put("ARG_UNIT_A", item.getString("qty_allocjob"));
		map.put("ARG_UNIT_B", item.getString("u_m"));
		map.put("ARG_QTY", item.getString("qty_input1"));
		map.put("OUT_PARAM", "");
		return map;
	}

	/** 수정 시 기존 서브 품목 전체 삭제 */
	public String deleteProdReqSubAll(ProdReqHeaderVO vo) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ARG_FLAG", "delete");
		map.put("ARG_BIZ_AREA_CD", vo.getWorkplace());
		map.put("ARG_REQ_DT", getExpDateString(vo.getIlja()));
		map.put("ARG_REQ_NO", vo.getJeonpyo_no());
		map.put("ARG_CUST_CD", vo.getCust_num());
		map.put("OUT_PARAM", "");

		dao.select("sdpa0040.procedure_updateProdReqSub", map);
		return (String) map.get("OUT_PARAM");
	}

	// ---- 신규제조의뢰 ----

	@SuppressWarnings("unchecked")
	public NprodReqHeaderVO selectNprodReqHeader(NprodReqHeaderVO vo) throws Exception {
		HashMap<String, Object> map = keyOfNprodReq(vo);
		dao.select("sdpa0040.procedure_selectNprodReqHeader", map);
		return ((List<NprodReqHeaderVO>) map.get("OUT_PARAM")).get(0);
	}

	/** 신규제조의뢰 서브는 단건이다. */
	@SuppressWarnings("unchecked")
	public NprodReqSubVO selectNprodReqSub(NprodReqHeaderVO vo) throws Exception {
		HashMap<String, Object> map = keyOfNprodReq(vo);
		dao.select("sdpa0040.procedure_selectNprodReqSub", map);
		return ((List<NprodReqSubVO>) map.get("OUT_PARAM")).get(0);
	}

	/** 신규제조의뢰 헤더·서브 조회 조건 (사업장, 거래처, 주문일자, 전표번호) */
	private static HashMap<String, Object> keyOfNprodReq(NprodReqHeaderVO vo) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ARG_BIZ_AREA_CD", vo.getWorkplace());
		map.put("ARG_CUST_CD", vo.getCust_num());
		map.put("ARG_ORD_DT", getExpDateString(vo.getIlja()));
		map.put("ARG_ORD_NO", vo.getJeonpyo_no());
		map.put("OUT_PARAM", null);
		return map;
	}

	/** flag 는 "insert" 또는 "update". 두 경우의 파라미터가 같다. */
	public String saveNprodReqHeader(NprodReqHeaderVO vo, String flag) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ARG_FLAG", flag);
		map.put("ARG_BIZ_AREA_CD", vo.getWorkplace());
		map.put("ARG_ORD_DT", getExpDateString(vo.getIlja()));
		map.put("ARG_ORD_NO", vo.getJeonpyo_no());
		map.put("ARG_CUST_CD", vo.getCust_num());
		map.put("ARG_DELY_DT", getExpDateString(vo.getEuiloiil()));
		map.put("ARG_DELY_TYPE", vo.getBaedal_gubun());
		map.put("ARG_DELY_PLACE", vo.getBaedal_jangso());
		map.put("ARG_RECVER", vo.getInsuja());
		map.put("ARG_TEL_NO", vo.getTel_no());
		map.put("ARG_RMK", vo.getBigo());
		map.put("ARG_ZIP", vo.getZip());
		map.put("ARG_ADDR1", vo.getAddr1());
		map.put("ARG_ADDR2", vo.getAddr2());
		map.put("OUT_PARAM", "");

		dao.select("sdpa0040.procedure_updateNprodReqHeader", map);
		return (String) map.get("OUT_PARAM");
	}

	/** flag 는 "insert" 또는 "update". 두 경우의 파라미터가 같다. */
	public String saveNprodReqSub(NprodReqSubVO vo, String flag) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ARG_FLAG", flag);
		map.put("ARG_BIZ_AREA_CD", vo.getWorkplace());
		map.put("ARG_ORD_DT", getExpDateString(vo.getIlja()));
		map.put("ARG_ORD_NO", vo.getJeonpyo_no());
		map.put("ARG_SEQ", "1");
		map.put("ARG_CUST_CD", vo.getCust_num());
		map.put("ARG_ITEM_NM", vo.getPummyeong());
		map.put("ARG_UNIT_A", vo.getPojang_danwi_a());
		map.put("ARG_UNIT_B", vo.getPojang_danwi_b());
		map.put("ARG_QTY", vo.getPojang_sulyang());
		map.put("ARG_RMK", vo.getBigo());
		map.put("ARG_GYEON_SAEOBJANG", vo.getGyeon_saeobjang());
		map.put("ARG_GYEON_ILJA", getExpDateString(vo.getGyeon_ilja()));
		map.put("ARG_GYEON_JEONPYO_NO", vo.getGyeon_jeonpyo_no());
		map.put("OUT_PARAM", "");

		dao.select("sdpa0040.procedure_updateNprodReqSub", map);
		return (String) map.get("OUT_PARAM");
	}

	/** 신규제조의뢰 삭제. 화면에서 ProdReqHeaderVO 로 넘어온다(기존 코드 그대로). */
	public String deleteNprodReqHeader(ProdReqHeaderVO vo) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ARG_FLAG", "delete");
		map.put("ARG_BIZ_AREA_CD", vo.getWorkplace());
		map.put("ARG_ORD_DT", getExpDateString(vo.getIlja()));
		map.put("ARG_ORD_NO", vo.getJeonpyo_no());
		map.put("ARG_CUST_CD", vo.getCust_num());
		map.put("OUT_PARAM", "");

		dao.select("sdpa0040.procedure_updateNprodReqHeader", map);
		return (String) map.get("OUT_PARAM");
	}
}
