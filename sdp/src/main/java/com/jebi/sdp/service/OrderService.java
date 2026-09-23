package com.jebi.sdp.service;

import static com.jebi.sdp.common.FormatUtil.getExpDateString;

import java.util.HashMap;
import java.util.List;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jebi.sdp.dao.CmmnDao;
import com.jebi.sdp.model.BasketItemVO;
import com.jebi.sdp.model.CoItemVO;
import com.jebi.sdp.model.CoVO;
import com.jebi.sdp.model.CustomerVO;

/**
 * 주문서(sdpa0020) 화면의 저장 프로시저 호출.
 *
 * 저장 계열 메서드는 프로시저의 OUT_PARAM 값을 그대로 돌려준다. 정상이면 "OK" 이고,
 * 그 밖의 값은 오류 메시지다. 호출하는 쪽에서 판단한다.
 * 트랜잭션 경계도 호출하는 쪽(컨트롤러)이 기존과 같이 직접 관리한다.
 */
@Component
public class OrderService {

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

	// ---- 조회 ----

	public Object selectOrderList(CoVO vo) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ARG_CUST_CD", vo.getCust_num());
		map.put("ARG_FRDT", getExpDateString(vo.getSearchDate_from()));
		map.put("ARG_TODT", getExpDateString(vo.getSearchDate_to()));
		// 출하완료 포함이면 Y, 미포함 N
		map.put("ARG_INCLUDE_YN", vo.getSearchCheckBox_01().equals("on") ? "Y" : "N");
		map.put("OUT_PARAM", null);

		dao.update("sdpa0020.procedure_selectOrder", map);
		return map.get("OUT_PARAM");
	}

	/** 주문서 헤더. 없으면 빈 목록일 수 있으므로 목록 그대로 돌려준다. */
	@SuppressWarnings("unchecked")
	public List<CoVO> selectOrderHeader(CoVO vo) throws Exception {
		HashMap<String, Object> map = keyOfOrder(vo);
		map.put("ARG_GUBUN", "인터넷");

		dao.update("sdpa0020.procedure_selectOrderHeader", map);
		return (List<CoVO>) map.get("OUT_PARAM");
	}

	@SuppressWarnings("unchecked")
	public List<CoItemVO> selectOrderSubList(CoVO vo) throws Exception {
		HashMap<String, Object> map = keyOfOrder(vo);

		dao.update("sdpa0020.procedure_selectOrderSub", map);
		return (List<CoItemVO>) map.get("OUT_PARAM");
	}

	/** 주문서 조회 조건 (거래처, 주문일자, 전표번호) */
	private static HashMap<String, Object> keyOfOrder(CoVO vo) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ARG_CUST_CD", vo.getCust_num());
		map.put("ARG_ORD_DT", getExpDateString(vo.getIlja()));
		map.put("ARG_ORD_NO", vo.getJeonpyo_no());
		map.put("OUT_PARAM", null);
		return map;
	}

	/** 종합 포인트 정보 */
	public CustomerVO selectPointInfo(CoVO vo) throws Exception {
		return (CustomerVO) dao.select("sdpf0030.select_pointInfo", vo);
	}

	/** 새 전표번호 (주문서는 W1) */
	@SuppressWarnings("unchecked")
	public String selectJeonpyoNo(String workplace, String ilja) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ARG_BIZ_AREA_CD", workplace);
		map.put("ARG_SLIP_TYPE", "W1");
		map.put("ARG_DT", getExpDateString(ilja));
		map.put("OUT_PARAM", null);

		dao.select("sdpa0020.procedure_selectJeonpyoNo", map);
		List<CoVO> list = (List<CoVO>) map.get("OUT_PARAM");
		return list.get(0).getJeonpyo_no();
	}

	// ---- 헤더 저장 ----

	/**
	 * 작성 시 헤더 입력.
	 * 배송지는 주소 두 줄을 이어 붙이고, 수정·삭제 때와 달리 우편번호·주소 항목은 넘기지 않는다(기존 동작).
	 */
	public String insertOrderHeader(CoVO vo) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ARG_FLAG", "insert");
		map.put("ARG_BIZ_AREA_CD", vo.getWorkplace());
		map.put("ARG_ORD_DT", getExpDateString(vo.getIlja()));
		map.put("ARG_ORD_NO", vo.getJeonpyo_no());
		map.put("ARG_CUST_CD", vo.getCust_num());
		map.put("ARG_PANMAE_GUBUN", vo.getPanmae_gubun());
		map.put("ARG_HWAPYE_CODE", vo.getHwapye_code());
		map.put("ARG_CODE_1", "");
		map.put("ARG_CODE_2", "");
		map.put("ARG_DELY_DT", getExpDateString(vo.getYocheongil()));
		map.put("ARG_DELY_PLACE", vo.getAddr1() + ' ' + vo.getAddr2());
		map.put("ARG_RECVER", vo.getInsuja());
		map.put("ARG_TEL_NO", vo.getTel_no());
		map.put("ARG_RMK", vo.getBigo());
		map.put("OUT_PARAM", "");

		dao.select("sdpa0020.procedure_insertOrderHeader", map);
		return (String) map.get("OUT_PARAM");
	}

	/** 수정 시 헤더 입력. 배송지에 우편번호를 함께 적는다(기존 동작). */
	public String updateOrderHeader(CoVO vo) throws Exception {
		HashMap<String, Object> map = addressOfOrder(vo);
		map.put("ARG_FLAG", "update");
		map.put("ARG_DELY_PLACE", "(" + vo.getZip() + ")" + vo.getAddr1() + " " + vo.getAddr2());

		dao.select("sdpa0020.procedure_insertOrderHeader", map);
		return (String) map.get("OUT_PARAM");
	}

	/** 주문서 삭제. 배송지는 화면에서 넘어온 배달장소를 그대로 쓴다(기존 동작). */
	public String deleteOrderHeader(CoVO vo) throws Exception {
		HashMap<String, Object> map = addressOfOrder(vo);
		map.put("ARG_FLAG", "delete");
		map.put("ARG_DELY_PLACE", vo.getBaedal_jangso());

		dao.select("sdpa0020.procedure_insertOrderHeader", map);
		return (String) map.get("OUT_PARAM");
	}

	/** 수정·삭제가 함께 쓰는 헤더 항목 (ARG_FLAG 와 ARG_DELY_PLACE 는 호출한 쪽에서 채운다) */
	private static HashMap<String, Object> addressOfOrder(CoVO vo) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ARG_BIZ_AREA_CD", vo.getWorkplace());
		map.put("ARG_ORD_DT", getExpDateString(vo.getIlja()));
		map.put("ARG_ORD_NO", vo.getJeonpyo_no());
		map.put("ARG_CUST_CD", vo.getCust_num());
		map.put("ARG_PANMAE_GUBUN", vo.getPanmae_gubun());
		map.put("ARG_HWAPYE_CODE", vo.getHwapye_code());
		map.put("ARG_DELY_TYPE", vo.getBaedal_gubun());
		map.put("ARG_DELY_DT", getExpDateString(vo.getYocheongil()));
		map.put("ARG_RECVER", vo.getInsuja());
		map.put("ARG_TEL_NO", vo.getTel_no());
		map.put("ARG_RMK", vo.getBigo());
		map.put("ARG_TAKSONG_POINT_YN", vo.getTaksong_point_yn());
		map.put("ARG_ZIP", vo.getZip());
		map.put("ARG_ADDR1", vo.getAddr1());
		map.put("ARG_ADDR2", vo.getAddr2());
		map.put("OUT_PARAM", "");
		return map;
	}

	// ---- 서브 품목 저장 ----

	/** 작성 시 품목 입력. 수정 때와 달리 판매구분·출하구분·삼성여부를 함께 넘긴다(기존 동작). */
	public String insertOrderSub(CoVO vo, int seq, JSONObject item) throws Exception {
		HashMap<String, Object> map = subOfOrder(vo, seq, item);
		map.put("ARG_PANMAE_GUBUN", vo.getPanmae_gubun());
		map.put("ARG_GUBUN", "N");
		map.put("ARG_SAMSUNG_YN", "N");

		dao.select("sdpa0020.procedure_insertOrderSub", map);
		return (String) map.get("OUT_PARAM");
	}

	/** 수정 시 품목 입력. 작성 때와 달리 배달구분을 넘긴다(기존 동작). */
	public String insertOrderSubOnUpdate(CoVO vo, int seq, JSONObject item) throws Exception {
		HashMap<String, Object> map = subOfOrder(vo, seq, item);
		map.put("ARG_DELY_TYPE", vo.getBaedal_gubun());

		dao.select("sdpa0020.procedure_insertOrderSub", map);
		return (String) map.get("OUT_PARAM");
	}

	private static HashMap<String, Object> subOfOrder(CoVO vo, int seq, JSONObject item) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ARG_FLAG", "insert");
		map.put("ARG_BIZ_AREA_CD", vo.getWorkplace());
		map.put("ARG_ORD_DT", getExpDateString(vo.getIlja()));
		map.put("ARG_ORD_NO", vo.getJeonpyo_no());
		map.put("ARG_SEQ", Integer.toString(seq));
		map.put("ARG_CUST_CD", vo.getCust_num());
		map.put("ARG_ITEM_CD", item.getString("item"));
		map.put("ARG_SALE_UNIT_A", item.getString("qty_allocjob"));
		map.put("ARG_SALE_UNIT_B", item.getString("u_m"));
		map.put("ARG_QTY", item.getString("qty_input1"));
		map.put("ARG_RMK", item.getString("bigo"));
		map.put("ARG_NABPUM", "1");
		map.put("OUT_PARAM", "");
		return map;
	}

	/**
	 * 수정 시 기존 품목 전체 삭제.
	 * 기존 코드가 OUT_PARAM 을 미리 넣지 않아, 프로시저가 값을 채우지 않으면 null 이 돌아온다.
	 */
	public String deleteOrderSubAll(CoVO vo) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("ARG_FLAG", "delete");
		map.put("ARG_BIZ_AREA_CD", vo.getWorkplace());
		map.put("ARG_ORD_DT", getExpDateString(vo.getIlja()));
		map.put("ARG_ORD_NO", vo.getJeonpyo_no());
		map.put("ARG_SEQ", "0");
		map.put("ARG_CUST_CD", vo.getCust_num());

		dao.select("sdpa0020.procedure_insertOrderSub", map);
		return (String) map.get("OUT_PARAM");
	}

	// ---- 장바구니 ----

	/** 장바구니에서 주문서를 작성한 경우, 주문에 담긴 품목을 장바구니에서 지운다. */
	public void deleteBasketItems(List<String> itemKeys) throws Exception {
		BasketItemVO basketVO = new BasketItemVO();
		basketVO.setParamList(itemKeys);
		dao.delete("sdpf0020.delete_delBasket", basketVO);
	}
}
