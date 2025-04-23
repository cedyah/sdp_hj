package com.jebi.sdp.controller;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.Session;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jebi.sdp.common.CommonUtil;
import com.jebi.sdp.dao.CmmnDao;
import com.jebi.sdp.model.*;
import com.jebi.sdp.service.*;

@Controller
public class Sdpa0050Controller extends CommonUtil {
	private static final Logger logger = LoggerFactory.getLogger(Sdpa0050Controller.class);

	@Autowired
	private CmmnDao dao;
	
	@RequestMapping(value = "sdpa005001l.do")		//견본요청서 목록 조회
	public String sdpa005001l(@ModelAttribute("smplReqVO")SmplRequestVO smplReqVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ARG_BIZ_AREA_CD", smplReqVO.getWorkplace());
		map.put("ARG_CUST_CD", smplReqVO.getCust_num());
		map.put("ARG_FRDT", getExpDateString(smplReqVO.getSearchDate_from()));
		map.put("ARG_TODT", getExpDateString(smplReqVO.getSearchDate_to()));
		map.put("OUT_PARAM", null);
		
		dao.update("sdpa0050.procedure_selectSampleRequest",map);
		
		model.addAttribute("smplRequestList", map.get("OUT_PARAM"));
		return "sdpa0050/sdpa005001l";
	}
	
	@RequestMapping(value="sdpa005001d.do")		//견본요청서 상세 조회
	public String sdpa005001d(@ModelAttribute("smplReqVO")SmplRequestVO smplReqVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		Map<String, Object> map;
		
		//견본요청 상세정보
		map = new HashMap<String, Object>();
		map.put("ARG_BIZ_AREA_CD", 	smplReqVO.getWorkplace());
		map.put("ARG_CUST_CD", 		smplReqVO.getCust_num());
		map.put("ARG_DT", 			getExpDateString(smplReqVO.getIlja()));
		map.put("ARG_NO", 			getExpDateString(smplReqVO.getJeonpyo_no()));
		map.put("OUT_PARAM", 		null);
		dao.update("sdpa0050.procedure_selectSampleRequestDetail", map);

		List<SmplRequestVO> list = (List<SmplRequestVO>) map.get("OUT_PARAM");
		
		if(list.size() > 0){
			model.addAttribute("smplRequest", list.get(0));
		}
		
		return "sdpa0050/sdpa005001d";
	}
	
	@RequestMapping(value = "sdpa005001u.{flag}.do")		//견본요청서 등록&수정화면 호출
	public String sdpa005001u(@ModelAttribute("smplReqVO")SmplRequestVO smplReqVO,
			@PathVariable String flag, HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		Map<String, Object> map;
		
		model.addAttribute("flag", flag);		//flag 값을 다음 화면으로 넘김
		
		//견본구분 코드목록
		map = new HashMap<String, Object>();
		map.put("ARG_MAJOR_CD", "054");
		map.put("ARG_COMP_CD", null);		//erp 다른 시스템에서 사용하는 필드. null처리 해도 상관 없음
		map.put("OUT_PARAM", null);
		dao.update("common.procedure_selectCode", map);
		model.addAttribute("code01", map.get("OUT_PARAM"));
		
		//도장소재 코드목록
		map = new HashMap<String, Object>();
		map.put("ARG_MAJOR_CD", "110");
		map.put("ARG_COMP_CD", null);		//erp 다른 시스템에서 사용하는 필드. null처리 해도 상관 없음
		map.put("OUT_PARAM", null);
		dao.update("common.procedure_selectCode", map);
		model.addAttribute("code02", map.get("OUT_PARAM"));
		
		//표준시편 코드목록
		map = new HashMap<String, Object>();
		map.put("ARG_MAJOR_CD", "353");
		map.put("ARG_COP_CD", null);		//erp 다른 시스템에서 사용하는 필드. null처리 해도 상관 없음
		map.put("OUT_PARAM", null);
		dao.update("common.procedure_selectCode", map);
		model.addAttribute("code03", map.get("OUT_PARAM"));

		//광택 코드목록
		map = new HashMap<String, Object>();
		map.put("ARG_MAJOR_CD", "355");
		map.put("ARG_COMP_CD", null);		//erp 다른 시스템에서 사용하는 필드. null처리 해도 상관 없음
		map.put("OUT_PARAM", null);
		dao.update("common.procedure_selectCode", map);
		model.addAttribute("code04", map.get("OUT_PARAM"));
		
		//도료종류 코드목록
		map = new HashMap<String, Object>();
		map.put("ARG_MAJOR_CD", "356");
		map.put("ARG_COMP_CD", null);		//erp 다른 시스템에서 사용하는 필드. null처리 해도 상관 없음
		map.put("OUT_PARAM", null);
		dao.update("common.procedure_selectCode", map);
		model.addAttribute("code05", map.get("OUT_PARAM"));
		
		//도장방법 코드목록
		map = new HashMap<String, Object>();
		map.put("ARG_MAJOR_CD", "056");
		map.put("ARG_COMP_CD", null);		//erp 다른 시스템에서 사용하는 필드. null처리 해도 상관 없음
		map.put("OUT_PARAM", null);
		dao.update("common.procedure_selectCode", map);
		model.addAttribute("code06", map.get("OUT_PARAM"));
		
		//건조조건 코드목록
		map = new HashMap<String, Object>();
		map.put("ARG_MAJOR_CD", "114");
		map.put("ARG_COMP_CD", null);		//erp 다른 시스템에서 사용하는 필드. null처리 해도 상관 없음
		map.put("OUT_PARAM", null);
		dao.update("common.procedure_selectCode", map);
		model.addAttribute("code07", map.get("OUT_PARAM"));

		//전처리조건 코드목록
		map = new HashMap<String, Object>();
		map.put("ARG_MAJOR_CD", "111");
		map.put("ARG_COMP_CD", null);		//erp 다른 시스템에서 사용하는 필드. null처리 해도 상관 없음
		map.put("OUT_PARAM", null);
		dao.update("common.procedure_selectCode", map);
		model.addAttribute("code08", map.get("OUT_PARAM"));
		
		//단위 코드목록
		map = new HashMap<String, Object>();
		map.put("ARG_MAJOR_CD", "104");
		map.put("ARG_COMP_CD", null);		//erp 다른 시스템에서 사용하는 필드. null처리 해도 상관 없음
		map.put("OUT_PARAM", null);
		dao.update("common.procedure_selectCode", map);
		model.addAttribute("code09", map.get("OUT_PARAM"));
		
		//배달구분 코드목록
		map = new HashMap<String, Object>();
		map.put("ARG_MAJOR_CD", "326");
		map.put("ARG_COMP_CD", null);		//erp 다른 시스템에서 사용하는 필드. null처리 해도 상관 없음
		map.put("OUT_PARAM", null);
		dao.update("common.procedure_selectCode", map);
		model.addAttribute("code10", map.get("OUT_PARAM"));
		
		if("insert".equals(flag)) {				//신규등록화면
			
		} else if("update".equals(flag)) {		//기존 시험요청서 수정화면
			map = new HashMap<String, Object>();
			map.put("ARG_BIZ_AREA_CD", 	smplReqVO.getWorkplace());
			map.put("ARG_CUST_CD", 		smplReqVO.getCust_num());
			map.put("ARG_DT", 			getExpDateString(smplReqVO.getIlja()));
			map.put("ARG_NO", 			getExpDateString(smplReqVO.getJeonpyo_no()));
			map.put("OUT_PARAM", 		null);
			
			dao.update("sdpa0050.procedure_selectSampleRequestDetail", map);
			@SuppressWarnings("unchecked")
			List<SmplRequestVO> list = (List<SmplRequestVO>) map.get("OUT_PARAM");
			if(list.size() > 0){
				model.addAttribute("smplRequest", list.get(0));
			}
		}
		
		return "sdpa0050/sdpa005001u";
	}
	
	@RequestMapping(value = "sdpa005001u_insert.do")		//견본요청서 신규 작성 (db insert)
	public String sdpe002001u_insert(@ModelAttribute("smplReqVO")SmplRequestVO smplReqVO,
			RedirectAttributes redirectAttr, HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		Map<String, Object> map;
		
		//전표번호 가져오기
		map = new HashMap<String, Object>();
		map.put("ARG_BIZ_AREA_CD", smplReqVO.getWorkplace());
		map.put("ARG_SLIP_TYPE", "90");
		map.put("ARG_DT", getExpDateString(smplReqVO.getIlja()));
		map.put("OUT_PARAM", null);
		
		dao.update("sdpa0050.procedure_selectJeonpyoNo", map);
		List<SmplRequestVO> list = (List<SmplRequestVO>)map.get("OUT_PARAM");
		smplReqVO.setJeonpyo_no(((SmplRequestVO) list.get(0)).getJeonpyo_no());
		
		//insert를 위해 map에 값 넣기
		map = new HashMap<String, Object>();
		map.put("ARG_FLAG", "insert");
		map.put("ARG_SAEOBJANG", smplReqVO.getWorkplace());
		map.put("ARG_ILJA",  getExpDateString(smplReqVO.getIlja()));
		map.put("ARG_GYEJEONG_GUBUN", "3");		//계정구분 : 3으로 고정
		map.put("ARG_JEONPYO_NO", smplReqVO.getJeonpyo_no());
		map.put("ARG_GEOLAECHEO_CODE", smplReqVO.getCust_num());
		map.put("ARG_HIMANG_WANRYOIL", getExpDateString(smplReqVO.getHimang_wanryoil()));
		map.put("ARG_JECHULCHEO", smplReqVO.getJechulcheo());
		map.put("ARG_GOGAEG_MYEONG", smplReqVO.getGogaeg_myeong());
		map.put("ARG_TEL_NO", smplReqVO.getTel_no());
		map.put("ARG_GYEONBON_GUBUN", smplReqVO.getGyeonbon_gubun());
		map.put("ARG_JEPUM_CODE", smplReqVO.getJepum_code());
		map.put("ARG_PUMMYEONG", smplReqVO.getPummyeong());
		map.put("ARG_WANJEPUM_MYEONG", smplReqVO.getWanjepum_myeong());
		map.put("ARG_DOJANG_SOJI", smplReqVO.getDojang_soji());
		map.put("ARG_SEOLCHI_JANGSO", smplReqVO.getSeolchi_jangso());
		map.put("ARG_GYESOK_YN", "1");	//계속여부 : 현재 구시스템에만 있음. 현시스템에 없으며 변수만 넘김
		map.put("ARG_SAYONG_MM", smplReqVO.getSayong_mm());
		map.put("ARG_POJANG_DANWI_A", smplReqVO.getPojang_danwi_a());
		map.put("ARG_POJANG_DANWI_B", smplReqVO.getPojang_danwi_b());
		map.put("ARG_SULYANG", smplReqVO.getSulyang());
		map.put("ARG_SIPYEONMAESU", smplReqVO.getSipyeonmaesu());
		map.put("ARG_KYEONGJAENGSA", smplReqVO.getKyeongjaengsa());
		map.put("ARG_HISEOGJE", smplReqVO.getHiseogje());
		map.put("ARG_JUNGGEUMSOK_YN", smplReqVO.getJunggeumsok_yn());
		map.put("ARG_TEUGKISAHANG_3", smplReqVO.getTeugkisahang_3());
		map.put("ARG_PYOJUN_SIPYEON", smplReqVO.getPyojun_sipyeon());
		map.put("ARG_TEUGKISAHANG_2", smplReqVO.getTeugkisahang_2());
		map.put("ARG_KWANGTAEG", smplReqVO.getKwangtaeg());
		map.put("ARG_GLOSS_A", smplReqVO.getGloss_a());
		map.put("ARG_GLOSS_B", smplReqVO.getGloss_b());
		map.put("ARG_DORYO_JONGLYU", smplReqVO.getDoryo_jonglyu());
		map.put("ARG_DORYO_JONGLYU_M", smplReqVO.getDoryo_jonglyu_m());
		map.put("ARG_DOJANG_BANGBEOB", smplReqVO.getDojang_bangbeob());
		map.put("ARG_JEONCHEOLI_BANGBEOB", smplReqVO.getJeoncheoli_bangbeob());
		map.put("ARG_GEONJO_BANGBEOB", smplReqVO.getGeonjo_bangbeob());
		map.put("ARG_HADO_DOLYO", smplReqVO.getHado_dolyo());
		map.put("ARG_TEUGKISAHANG_1", smplReqVO.getTeugkisahang_1());
		map.put("ARG_BAEDAL_GUBUN", smplReqVO.getBaedal_gubun());
		map.put("ARG_INSUJA", smplReqVO.getInsuja());
		map.put("ARG_INSU_TEL", smplReqVO.getInsu_tel());
		map.put("ARG_INSU_JUSO", smplReqVO.getInsu_juso());
		map.put("ARG_ZIP", smplReqVO.getZip());
		map.put("ARG_ADDR1", smplReqVO.getAddr1());
		map.put("ARG_ADDR2", smplReqVO.getAddr2());
		map.put("ARG_SOLID", smplReqVO.getSolid());
		map.put("OUT_PARAM", "");

		dao.update("sdpa0050.procedure_updateSampleRequest", map);
		
		String url = "";
		String result = map.get("OUT_PARAM").toString();
		
		model.addAttribute("smplReqVO", smplReqVO);
		
		if(result.equals("OK")) {
			redirectAttr.addFlashAttribute("smplReqVO", smplReqVO);
			url = "redirect:/sdpa005001d.do";
		} else {
			url = "redirect:/main.do";
		}
		
		return url;
	}
	
	@RequestMapping(value = "sdpa005001u_update.do")		//견본요청서 신규 수정 (db update)
	public String sdpa005001u_update(@ModelAttribute("smplReqVO")SmplRequestVO smplReqVO,
			RedirectAttributes redirectAttr, HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		Map<String, Object> map;
		
		map = new HashMap<String, Object>();
		map.put("ARG_FLAG", "update");
		map.put("ARG_SAEOBJANG", smplReqVO.getWorkplace());
		map.put("ARG_ILJA",  getExpDateString(smplReqVO.getIlja()));
		map.put("ARG_GYEJEONG_GUBUN", "3");		//계정구분 : 3으로 고정
		map.put("ARG_JEONPYO_NO", smplReqVO.getJeonpyo_no());
		map.put("ARG_GEOLAECHEO_CODE", smplReqVO.getCust_num());
		map.put("ARG_HIMANG_WANRYOIL", getExpDateString(smplReqVO.getHimang_wanryoil()));
		map.put("ARG_JECHULCHEO", smplReqVO.getJechulcheo());
		map.put("ARG_GOGAEG_MYEONG", smplReqVO.getGogaeg_myeong());
		map.put("ARG_TEL_NO", smplReqVO.getTel_no());
		map.put("ARG_GYEONBON_GUBUN", smplReqVO.getGyeonbon_gubun());
		map.put("ARG_JEPUM_CODE", smplReqVO.getJepum_code());
		map.put("ARG_PUMMYEONG", smplReqVO.getPummyeong());
		map.put("ARG_WANJEPUM_MYEONG", smplReqVO.getWanjepum_myeong());
		map.put("ARG_DOJANG_SOJI", smplReqVO.getDojang_soji());
		map.put("ARG_SEOLCHI_JANGSO", smplReqVO.getSeolchi_jangso());
		map.put("ARG_GYESOK_YN", "1");	//계속여부 : 현재 구시스템에만 있음. 현시스템에 없으며 변수만 넘김
		map.put("ARG_SAYONG_MM", smplReqVO.getSayong_mm());
		map.put("ARG_POJANG_DANWI_A", smplReqVO.getPojang_danwi_a());
		map.put("ARG_POJANG_DANWI_B", smplReqVO.getPojang_danwi_b());
		map.put("ARG_SULYANG", smplReqVO.getSulyang());
		map.put("ARG_SIPYEONMAESU", smplReqVO.getSipyeonmaesu());
		map.put("ARG_KYEONGJAENGSA", smplReqVO.getKyeongjaengsa());
		map.put("ARG_HISEOGJE", smplReqVO.getHiseogje());
		map.put("ARG_JUNGGEUMSOK_YN", smplReqVO.getJunggeumsok_yn());
		map.put("ARG_TEUGKISAHANG_3", smplReqVO.getTeugkisahang_3());
		map.put("ARG_PYOJUN_SIPYEON", smplReqVO.getPyojun_sipyeon());
		map.put("ARG_TEUGKISAHANG_2", smplReqVO.getTeugkisahang_2());
		map.put("ARG_KWANGTAEG", smplReqVO.getKwangtaeg());
		map.put("ARG_GLOSS_A", smplReqVO.getGloss_a());
		map.put("ARG_GLOSS_B", smplReqVO.getGloss_b());
		map.put("ARG_DORYO_JONGLYU", smplReqVO.getDoryo_jonglyu());
		map.put("ARG_DORYO_JONGLYU_M", smplReqVO.getDoryo_jonglyu_m());
		map.put("ARG_DOJANG_BANGBEOB", smplReqVO.getDojang_bangbeob());
		map.put("ARG_JEONCHEOLI_BANGBEOB", smplReqVO.getJeoncheoli_bangbeob());
		map.put("ARG_GEONJO_BANGBEOB", smplReqVO.getGeonjo_bangbeob());
		map.put("ARG_HADO_DOLYO", smplReqVO.getHado_dolyo());
		map.put("ARG_TEUGKISAHANG_1", smplReqVO.getTeugkisahang_1());
		map.put("ARG_BAEDAL_GUBUN", smplReqVO.getBaedal_gubun());
		map.put("ARG_INSUJA", smplReqVO.getInsuja());
		map.put("ARG_INSU_TEL", smplReqVO.getInsu_tel());
		map.put("ARG_INSU_JUSO", smplReqVO.getInsu_juso());
		map.put("ARG_ZIP", smplReqVO.getZip());
		map.put("ARG_ADDR1", smplReqVO.getAddr1());
		map.put("ARG_ADDR2", smplReqVO.getAddr2());
		map.put("ARG_SOLID", smplReqVO.getSolid());
		map.put("OUT_PARAM", "");
		
		dao.update("sdpa0050.procedure_updateSampleRequest", map);
		
		String url = "";
		String result = map.get("OUT_PARAM").toString();
		
		if(result.equals("OK")) {
			redirectAttr.addFlashAttribute("smplReqVO", smplReqVO);
			url = "redirect:/sdpa005001d.do";
		} else {
			url = "redirect:/main.do";
		}
		
		return "redirect:/sdpa005001d.do";
	}
	
	@RequestMapping(value = "sdpa005001d_delete.do")		//견본요청서 삭제 (db 삭제)
	public String sdpa005001d_delete(@ModelAttribute("smplReqVO")SmplRequestVO smplReqVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		Map<String, Object> map;
		
		map = new HashMap<String, Object>();
		map.put("ARG_FLAG", "delete");
		map.put("ARG_SAEOBJANG", smplReqVO.getWorkplace());
		map.put("ARG_ILJA",  getExpDateString(smplReqVO.getIlja()));
		map.put("ARG_JEONPYO_NO", smplReqVO.getJeonpyo_no());
		map.put("ARG_GEOLAECHEO_CODE", smplReqVO.getCust_num());
		
		dao.update("sdpa0050.procedure_updateSampleRequest", map);
		
		model.addAttribute("smplReqVO", smplReqVO);
		
		return "redirect:/sdpa005001l.do";
	}
	
	@RequestMapping(value = "sdpa005001p.do")		//견본요청서 팝업
	public String sdpa005001p(@ModelAttribute("smplReqVO")SmplRequestVO smplReqVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		
//		SimpleDateFormat smt = new SimpleDateFormat("yyyyMMdd");
//		smplReqVO.setSearchDate_to(smt.format(new Date()));//현재일자
//		
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(new Date());
//		cal.add(Calendar.YEAR, -1);
//		
//		smplReqVO.setSearchDate_from(smt.format(cal.getTime()));
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ARG_BIZ_AREA_CD", smplReqVO.getWorkplace());
		map.put("ARG_CUST_CD", smplReqVO.getCust_num());
		map.put("ARG_FRDT", getExpDateString(smplReqVO.getSearchDate_from()));
		map.put("ARG_TODT", getExpDateString(smplReqVO.getSearchDate_to()));
		map.put("OUT_PARAM", null);
		
		dao.update("sdpa0050.procedure_selectSampleRequest",map);
		
		model.addAttribute("smplRequestList", map.get("OUT_PARAM"));
		
		return "/sdpa0050/sdpa005001p";
	}
}