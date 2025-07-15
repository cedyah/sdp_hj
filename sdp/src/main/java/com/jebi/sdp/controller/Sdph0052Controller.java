package com.jebi.sdp.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jettison.json.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.JsonArray;
import com.jebi.sdp.common.*;
import com.jebi.sdp.dao.*;
import com.jebi.sdp.model.*;
import com.sun.mail.imap.protocol.Item;

@Controller
public class Sdph0052Controller extends CommonUtil {
	private static final Logger logger = LoggerFactory.getLogger(Sdph0052Controller.class);

	@Autowired
	private CmmnDao dao;
 
	@RequestMapping(value = "sdph005201l.do")		//샘플진도조회
	public String detailSampleResultList(@ModelAttribute("SampleRequestItemStatVO")SampleRequestItemStatVO sampleRequestItemStatVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {

		logger.debug("param: {}", "detailSampleResultList");
		
		// 초기 날짜 값 세팅 (예: 오늘 날짜 기준 최근 7일)
	    if (sampleRequestItemStatVO.getSearchDate_from() == null || sampleRequestItemStatVO.getSearchDate_to() == null) {
			logger.debug("param: {}", "LocalDate.now()");
	        LocalDate today = LocalDate.now();
	        sampleRequestItemStatVO.setSearchDate_to(today.toString()); // 예: 2025-06-27
			logger.debug("param: {}", "setSearchDate_to(today.toString())");
	        sampleRequestItemStatVO.setSearchDate_from(today.minusDays(7).toString()); // 예: 2025-06-20
			logger.debug("param: {}", "setSearchDate_from(today.toString())");
	    }
		
		logger.debug("param: {}", "map");
		//header 정보
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ARG_CUST_CD", 		sampleRequestItemStatVO.getCust_num());
		map.put("ARG_FRDT", getExpDateString(sampleRequestItemStatVO.getSearchDate_from()));
		map.put("ARG_TODT", getExpDateString(sampleRequestItemStatVO.getSearchDate_to()));
		map.put("ARG_GUBUN",sampleRequestItemStatVO.getSearchRadio_01());
		map.put("OUT_PARAM", null);

		

		//출하정보 목록 프로시저
		dao.update("sdph0052.procedure_selectSampleResultList", map);
		Object result = map.get("OUT_PARAM");
		if(result==null){
			result = new ArrayList<Map<String,Object>>();
		}
		model.addAttribute("sampleRequestItemStat", result);
		
		return "sdph0052/sdph005201l";
		 
	}


	@RequestMapping(value = "sdph005201d.do")		//샘플의뢰 상세 조회
	public String sdph005201d(@ModelAttribute("sampleRequestVO")SampleRequestVO sampleRequestVO,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		HashMap<String, Object> map;
		
		
		//샘플의뢰 헤더 불러오기
		map = new HashMap<String, Object>();
		//map.put("ARG_BIZ_AREA_CD", sampleRequestVO.getWorkplace());
		map.put("ARG_CUST_CD", sampleRequestVO.getCust_num());
		map.put("ARG_DT", getExpDateString(sampleRequestVO.getIlja()));
		map.put("ARG_NO", sampleRequestVO.getJeonpyo_no());
		map.put("ARG_GUBUN", sampleRequestVO.getGubun());
		map.put("OUT_PARAM", null);
		dao.select("sdph0052.procedure_selectSampleResult", map);
		SampleRequestVO sampleRequest = ((List<SampleRequestVO>) map.get("OUT_PARAM")).get(0);
		model.addAttribute("sampleResult", sampleRequest);
		
		
		//샘플의뢰 서브 불러오기
		map = new HashMap<String, Object>();
		//map.put("ARG_BIZ_AREA_CD", prVO.getWorkplace());
		map.put("ARG_CUST_CD", sampleRequestVO.getCust_num());
		map.put("ARG_ORD_DT", getExpDateString(sampleRequestVO.getIlja()));
		map.put("ARG_ORD_NO", sampleRequestVO.getJeonpyo_no());
		map.put("ARG_GUBUN","INTERNET");
	    logger.debug("param: {}", "AAAAA");
		logger.debug("ilja param: {}", sampleRequestVO.getIlja());
		logger.debug("jeonpyo_no param: {}", sampleRequestVO.getJeonpyo_no());
		map.put("OUT_PARAM", null);
		dao.select("sdph0052.procedure_selectSampleResultItem", map);
		List<SampleRequestItemVO> sampleRequestItemList = (List<SampleRequestItemVO>) map.get("OUT_PARAM");
		model.addAttribute("sampleResultItemList", sampleRequestItemList);
		
		return "sdph0052/sdph005201d";
	}
	
	
	@RequestMapping(value = "sdph005201u.{flag}.do")		//샐플의뢰 작성 & 수정 화면 호출
	public String sdph005201u(@ModelAttribute("sampleRequestVO") SampleRequestVO sampleRequestVO, @PathVariable("flag")String flag,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		HashMap<String, Object> map;
		model.addAttribute("flag", flag);
		
		//사업장(본사,대구, 신제품:0031)
		sampleRequestVO.setSaeobjang(sampleRequestVO.getWorkplace());
	    System.out.println(">>> [sampleRequestVO.setSaeobjang1] " + sampleRequestVO.getSaeobjang());
	    map = new HashMap<String, Object>();
		map.put("ARG_MAJOR_CD", "0031");
		dao.update("common.procedure_selectCode", map);
		model.addAttribute("code0031", map.get("OUT_PARAM"));
	    System.out.println(">>> [sampleRequestVO.setSaeobjang2] " + sampleRequestVO.getSaeobjang());
        
		//견본구분(기존품, 신제품:4007)
		map = new HashMap<String, Object>();
		map.put("ARG_MAJOR_CD", "4007");
		dao.update("common.procedure_selectCode", map);
		model.addAttribute("code4007", map.get("OUT_PARAM"));

		
		
		//포장단위(LT,KG,EA:0102)
		map = new HashMap<String, Object>();
		map.put("ARG_MAJOR_CD", "0102");
		dao.update("common.procedure_selectCode", map);
		model.addAttribute("po_danwi_b", map.get("OUT_PARAM"));

		
		//품목분류(핸드폰/일반품 4901)
		map = new HashMap<String, Object>();
		map.put("ARG_MAJOR_CD", "4901");
		dao.update("common.procedure_selectCode", map);
		model.addAttribute("code4901", map.get("OUT_PARAM"));

		
		//핸드폰분류(삼성,LG,기타:4902)
		map = new HashMap<String, Object>();
		map.put("ARG_MAJOR_CD", "4902");
		dao.update("common.procedure_selectCode", map);
		model.addAttribute("code4902", map.get("OUT_PARAM"));

		//결과등록시한(7일이네:4905)
		map = new HashMap<String, Object>();
		map.put("ARG_MAJOR_CD", "4905");
		dao.update("common.procedure_selectCode", map);
		model.addAttribute("code4905", map.get("OUT_PARAM"));

		//수신부서
		map = new HashMap<String, Object>();
		map.put("ARG_COMP_CD", "HJCHEM");
		map.put("ARG_GUBUN", "1");
		dao.update("common.procedure_selectBaseDddwS1", map);
		model.addAttribute("codeSusinBuseo", map.get("OUT_PARAM"));

		//수신자
		map = new HashMap<String, Object>();
		map.put("ARG_COMP_CD", "HJCHEM");
		map.put("ARG_GUBUN", "2");
		try{
		dao.update("common.procedure_selectBaseDddwS1", map);
		}
		catch(Exception e){
			logger.error("수신자 코드 조회 실패",e);
		}
		model.addAttribute("codeSusinja", map.get("OUT_PARAM"));
		//List<CommonCodeVO> codeSusinja = commonCodeService.getCodeList("SUSINJA");
		//model.addAttribute("codeSusinja", codeSusinja);
		
		logger.debug("codeSusinja size: " + map.size());
		
		
		
		//영업담당자
		map = new HashMap<String, Object>();
		map.put("ARG_COMP_CD", "HJCHEM");
		map.put("ARG_GUBUN", "2");
		dao.update("common.procedure_selectBaseDddwS1", map);
		model.addAttribute("codeBalsinja", map.get("OUT_PARAM"));

		//입회자
		map = new HashMap<String, Object>();
		map.put("ARG_COMP_CD", "HJCHEM");
		map.put("ARG_GUBUN", "2");
		dao.update("common.procedure_selectBaseDddwS1", map);
		model.addAttribute("codeIbhoija", map.get("OUT_PARAM"));
		
		//유무상구분(유상,무상:4030)
		map = new HashMap<String, Object>();
		map.put("ARG_MAJOR_CD", "4030");
		dao.update("common.procedure_selectCode", map);
		model.addAttribute("code4030", map.get("OUT_PARAM"));

		if(flag != null && flag.equals("update")) {
			//샘플의뢰 헤더 불러오기
			map = new HashMap<String, Object>();
			map.put("ARG_BIZ_AREA_CD", sampleRequestVO.getWorkplace());
			map.put("ARG_CUST_CD", sampleRequestVO.getCust_num());
			map.put("ARG_REQ_DT", getExpDateString(sampleRequestVO.getIlja()));
			map.put("ARG_REQ_NO", sampleRequestVO.getJeonpyo_no());
			map.put("OUT_PARAM", null);
			dao.select("sdph0052.procedure_selectSampleRequest", map);
			SampleRequestVO sampleRequest = ((List<SampleRequestVO>) map.get("OUT_PARAM")).get(0);
			model.addAttribute("sampleRequest", sampleRequest);
			
			
			//제조의뢰 서브 불러오기
			map = new HashMap<String, Object>();
			map.put("ARG_BIZ_AREA_CD", sampleRequestVO.getWorkplace());
			map.put("ARG_CUST_CD", sampleRequestVO.getCust_num());
			map.put("ARG_REQ_DT", getExpDateString(sampleRequestVO.getIlja()));
			map.put("ARG_REQ_NO", sampleRequestVO.getJeonpyo_no());
			map.put("OUT_PARAM", null);
			dao.select("sdph0052.procedure_selectSampleRequestItem", map);
			List<SampleRequestItemVO> sampleRequestItemList = (List<SampleRequestItemVO>) map.get("OUT_PARAM");
			model.addAttribute("sampleRequestItemList", sampleRequestItemList);
		}
				
		return "sdph0052/sdph005201u";
	}
	
	@RequestMapping(value = "sdph005201u_insert.do")		//샘플출고요청 작성(db insert)
	public String sdph005201u_insert(@ModelAttribute("sampleRequestVO")SampleRequestVO sampleRequestVO, RedirectAttributes redirectAttr,
			@RequestParam(value="pageCheck", required=false) String pageCheck,
			@RequestParam(value="jsonList", required=false) JSONArray jsonList,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
	/*@RequestMapping(value = "sdph005201u_insert.do")		//샘플출고요청 작성 (db insert)
	public String sdph005201u_insert(@ModelAttribute("sampleRequestVO") SampleRequestVO sampleRequestVO,@ModelAttribute("sampleRequestItemVO") SampleRequestItemVO sampleRequestItemVO,
			@RequestParam(value="jsonList")JSONArray jsonList, RedirectAttributes redirectAttr,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {*/
		    System.out.println(">>> [sdph005201u_insert] ");

		    
		try {
			HashMap<String, Object> map;
			
			//작성일 현재일자 셋팅
			Date dt = new Date();
			SimpleDateFormat smt = new SimpleDateFormat("yyyy.MM.dd");
			sampleRequestVO.setIlja(smt.format(dt));
			
			CustomerVO customerVO = new CustomerVO();
			sampleRequestVO.setSil_geolaecheo(customerVO.getCust_num());
			//transaction 시작
			dao.startTransaction();
			dao.startBatch();
			
			//전표번호 가져오기
			map = new HashMap<String, Object>();
			map.put("ARG_BIZ_AREA_CD", sampleRequestVO.getWorkplace());
			sampleRequestVO.setSaeobjang(sampleRequestVO.getWorkplace());
			map.put("ARG_SLIP_TYPE", "90");		//WEB 출고
			map.put("ARG_DT", getExpDateString(sampleRequestVO.getIlja()));
			map.put("OUT_PARAM", null);
			
			dao.select("sdph0052.procedure_selectJeonpyoNo", map);
			List<SampleRequestVO> list = (List<SampleRequestVO>) map.get("OUT_PARAM");
			sampleRequestVO.setJeonpyo_no(((SampleRequestVO) list.get(0)).getJeonpyo_no());

		    System.out.println(">>> list.get(0)).getJeonpyo_no proc: " + ((SampleRequestVO) list.get(0)).getJeonpyo_no());
		    System.out.println(">>> [sampleRequestVO.getWorkplace] received: " + sampleRequestVO.getWorkplace());
		    System.out.println(">>> [sampleRequestVO.getIlja]: " + getExpDateString(sampleRequestVO.getIlja()));
		    System.out.println(">>> [sampleRequestVO.getJeonpyo_no]: " + ((SampleRequestVO) list.get(0)).getJeonpyo_no().toString());
		    System.out.println(">>> [sampleRequestVO.getJeonpyo_no] is null? " + (sampleRequestVO.getJeonpyo_no() == null));
		    System.out.println(">>> [sampleRequestVO.getJeonpyo_no] is empty? " + ("".equals(sampleRequestVO.getJeonpyo_no())));
		    System.out.println(">>> OUT_PARAM from jeonpyoNo proc: " + map.get("OUT_PARAM"));
  

			
			//header 입력
			map = new HashMap<String, Object>();
			map.put("ARG_FLAG", "INSERT");
      map.put("ARG_SAEOBJANG"                  , sampleRequestVO.getSaeobjang         ());
      map.put("ARG_ILJA"                       , getExpDateString(sampleRequestVO.getIlja()));
      map.put("ARG_JEONPYO_NO"                 , sampleRequestVO.getJeonpyo_no        ());
      map.put("ARG_SIL_GEOLAECHEO"             , sampleRequestVO.getSil_geolaecheo    ());
      map.put("ARG_GYEONBON_GUBUN"             , sampleRequestVO.getGyeonbon_gubun    ());
      map.put("ARG_PUMMOG_BUNRYU"              , sampleRequestVO.getPummog_bunryu     ());
      map.put("ARG_HP_BUNRYU"                  , sampleRequestVO.getHp_bunryu         ());
      map.put("ARG_GEOLAECHEO_CODE1"           , sampleRequestVO.getGeolaecheo_code  ());
      map.put("ARG_SANGHO1"                    , sampleRequestVO.getSangho           ());

      map.put("ARG_GEOLAECHEO_CODE2"           , sampleRequestVO.getGeolaecheo_code_2  ());
      map.put("ARG_SANGHO2"                    , sampleRequestVO.getSangho_2           ());
      map.put("ARG_GOGAEG_MYEONG"              , sampleRequestVO.getGogaeg_myeong     ());
      map.put("ARG_BALSINJA"                   , sampleRequestVO.getBalsinja          ());
      map.put("ARG_SUSIN_BUSEO"                , sampleRequestVO.getSusin_buseo       ());
      map.put("ARG_SUSINJA"                    , sampleRequestVO.getSusinja           ());
      map.put("ARG_IBHOIJA"                    , sampleRequestVO.getIbhoija           ());
      map.put("ARG_NABPUM_ILJA"                , getExpDateString(sampleRequestVO.getNabpum_ilja()));
      map.put("ARG_YESANG_GEUMAEG"             , sampleRequestVO.getYesang_geumaeg    ());
      map.put("ARG_SAYONG_GEUMAEG"             , sampleRequestVO.getSayong_geumaeg    ());

      map.put("ARG_HIMANG_GAGYEOG"             , sampleRequestVO.getHimang_gagyeog    ());
      map.put("ARG_EX_GEOLAECHEO"              , sampleRequestVO.getEx_geolaecheo     ());
      map.put("ARG_EX_GYEONBON_YN"             , sampleRequestVO.getEx_gyeonbon_yn    ());
      map.put("ARG_DOJANG_BANGBEOB"            , sampleRequestVO.getDojang_bangbeob   ());
      map.put("ARG_DOJANG_GONGJEONG"           , sampleRequestVO.getDojang_gongjeong  ());
      map.put("ARG_GEONJO_BANGBEOB"            , sampleRequestVO.getGeonjo_bangbeob   ());
      map.put("ARG_DORYO_TYPE"                 , sampleRequestVO.getDoryo_type        ());
      map.put("ARG_SOJAE_JONGLYU"              , sampleRequestVO.getSojae_jonglyu     ());
      map.put("ARG_GITA_YOGU6"                 , sampleRequestVO.getGita_yogu6        ());
      map.put("ARG_GITA_YOGU3"                 , sampleRequestVO.getGita_yogu3        ());

      map.put("ARG_BIGO1"                      , sampleRequestVO.getBigo_1             ());
      map.put("ARG_BIGO2"                      , sampleRequestVO.getBigo_2             ());
      map.put("ARG_BIGO3"                      , sampleRequestVO.getBigo_3             ()); 
      map.put("ARG_GYEOLGWA_GIHAN"             , sampleRequestVO.getGyeolgwa_gihan    ());			
      map.put("OUT_PARAM"                      ,  ""                                    );
			
			dao.select("sdph0052.procedure_updateSampleRequest", map);

			System.out.println("Pre M Transaction");

			if(!map.get("OUT_PARAM").equals("OK")) {
				System.out.println("M Transaction NOT OK");
				//결과가 에러 발생하면 트랜잭션을 닫고 에러페이지로 이동
				System.out.println((String) map.get("OUT_PARAM"));
				dao.endTransaction();
				return "templates/error";
		
			}
			else
			{
				System.out.println("M Transaction OK");

			}
			System.out.println("M Transaction After");
			System.out.println(jsonList);
			System.out.println(">>> jsonList.length = " + jsonList.length());
			//sub 입력
			if(jsonList.length() > 0) {
				JSONObject obj = new JSONObject();

				for(int i=0; i < jsonList.length(); i++) {
					obj = (JSONObject) jsonList.get(i);
					System.out.println(">>> item JSON[" + i + "] = " + obj.toString());
					System.out.println(">>> item sampleRequestVO.getCust_num[" + i + "] = " + sampleRequestVO.getCust_num());
					System.out.println(">>> item sampleRequestVO.getGyeonbon_gubun[" + i + "] = " + sampleRequestVO.getGyeonbon_gubun());
					System.out.println(">>> item sampleRequestVO.getPummog_bunryu[" + i + "] = " + sampleRequestVO.getPummog_bunryu());

					map = new HashMap<String, Object>();
                    map.put("ARG_FLAG"                           ,    "INSERT"                                     );    
                    map.put("ARG_BIZ_AREA_CD"                    ,    sampleRequestVO.getSaeobjang            ());   
                    map.put("ARG_ORD_DT"                         ,    getExpDateString(sampleRequestVO.getIlja()));   
                    map.put("ARG_ORD_NO"                         ,    sampleRequestVO.getJeonpyo_no            ());   
                    map.put("ARG_SEQ"                            ,    Integer.toString(i + 1)                        );   
                    map.put("ARG_SEQ_1"                          ,    Integer.toString(i + 1)                      );     
                    map.put("ARG_SIL_GEOLAECHEO"                 ,    sampleRequestVO.getCust_num());   
                    map.put("ARG_GYEONBON_GUBUN"                 ,    sampleRequestVO.getGyeonbon_gubun        ());   
                    map.put("ARG_PUMMOG_BUNRYU"                  ,    sampleRequestVO.getPummog_bunryu         ());   
                    map.put("ARG_GEOLAECHEO_CODE1"               ,    sampleRequestVO.getGeolaecheo_code      ());   
                    
                    map.put("ARG_SANGHO1"                        ,    sampleRequestVO.getSangho               ());
                    map.put("ARG_GEOLAECHEO_CODE2"               ,    sampleRequestVO.getGeolaecheo_code_2      ());
                    map.put("ARG_SANGHO2"                        ,    sampleRequestVO.getSangho_2               ());
                    map.put("ARG_PUMMOG_CODE"                    ,    obj.getString("item"));
                    map.put("ARG_PUMMYEONG"                      ,    obj.getString("description"));
                    map.put("ARG_PO_DANWI_A"                     ,    obj.getString("qty_allocjob"));
                    map.put("ARG_PO_DANWI_B"                     ,    obj.getString("u_m"));
                    map.put("ARG_PO_SU"                          ,    obj.getString("qty_input1"));
                    map.put("ARG_PRICE_YN"                       ,    "");//obj.getString("price_yn"));   
                    map.put("ARG_DOPYEON_YN"                     ,    "");//obj.getString("dopyeon_yn"));
                    
                    map.put("ARG_MODEL_CODE"                     ,    "");//obj.getString("model_code"));
                    map.put("ARG_BALHAENGIL"                     ,    "");//getExpDateString(obj.getString("balhaengil")));
                    map.put("ARG_BALHAENG_BUSEO"                 ,    "");//obj.getString("balhaeng_buseo"));
                    map.put("ARG_BALHAENGJA"                     ,    "");//obj.getString("balhaengja"));
                    map.put("OUT_PARAM"                          ,    ""                                             );   


					System.out.println(">>> Before dao select!!!" );

					dao.select("sdph0052.procedure_updateSampleRequestItem", map);

					System.out.println(">>> After dao select!!!" );
					
					if(!map.get("OUT_PARAM").equals("OK")) {
						//결과가 에러 발생하면 트랜잭션을 닫고 에러페이지로 이동
						System.out.println(">>> D Transaction Not OK!!!" );
						System.out.println((String) map.get("OUT_PARAM"));
						dao.endTransaction();
						return "templates/error";
					}
					else
					{
						System.out.println(">>> D Transaction OK!!!" );
					}
					System.out.println(">>> D Transaction After!!!" );
					
				}
			}
			
			dao.executeBatch();
			dao.commit();
			System.out.println(">>> Transaction Commit!!!" );
			
			redirectAttr.addFlashAttribute("sampleRequestVO", sampleRequestVO);

			System.out.println(">>> Before reutn redirect:sdph005201d!!!" );
			return "redirect:/sdph005201d.do";

		} catch (Exception e) {
			System.out.println(">>> Catch Exceptiom e!!!" );
			e.printStackTrace(); // 전체 스택 트레이스 콘솔에 출력
		    System.out.println(">>> Exception Message: " + e.getMessage()); // 에러 메시지만 출력
			return "templates/error";
			
		} finally {
			System.out.println(">>> Before EndTransaction!!!" );
			dao.endTransaction();
			System.out.println(">>> After EndTransaction!!!" );
		}
		
	}
	@RequestMapping(value = "sdph005201u_update.do")		//샘플출고요청 수정 DB처리
	public String sdph005201u_update(@ModelAttribute(value = "sampleRequestVO")SampleRequestVO sampleRequestVO, @ModelAttribute("sampleRequestItemVO")SampleRequestItemVO sampleRequestItemVO,  
			@RequestParam(value="jsonList", required=false) JSONArray jsonList,
			RedirectAttributes redirectAttr, HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		HashMap<String, Object> map;
		
		try {
			//transaction 시작
			dao.startTransaction();
			dao.startBatch();
			
			//header 입력
			map = new HashMap<String, Object>();
			map.put("ARG_FLAG", "update");
      map.put("ARG_SAEOBJANG"                  , sampleRequestVO.getSaeobjang         ());      
      map.put("ARG_ILJA"                       , sampleRequestVO.getIlja              ());      
      map.put("ARG_JEONPYO_NO"                 , sampleRequestVO.getJeonpyo_no        ());      
      map.put("ARG_SIL_GEOLAECHEO"             , sampleRequestVO.getSil_geolaecheo    ());      
      map.put("ARG_GYEONBON_GUBUN"             , sampleRequestVO.getGyeonbon_gubun    ());      
      map.put("ARG_PUMMOG_BUNRYU"              , sampleRequestVO.getPummog_bunryu     ());      
      map.put("ARG_HP_BUNRYU"                  , sampleRequestVO.getHp_bunryu         ());      
      map.put("ARG_GEOLAECHEO_CODE1"           , sampleRequestVO.getGeolaecheo_code  ());      
      map.put("ARG_SANGHO1"                    , sampleRequestVO.getSangho           ());      
                                                                                                
      map.put("ARG_GEOLAECHEO_CODE2"           , sampleRequestVO.getGeolaecheo_code_2  ());      
      map.put("ARG_SANGHO2"                    , sampleRequestVO.getSangho_2           ());      
      map.put("ARG_GOGAEG_MYEONG"              , sampleRequestVO.getGogaeg_myeong     ());      
      map.put("ARG_BALSINJA"                   , sampleRequestVO.getBalsinja          ());      
      map.put("ARG_SUSIN_BUSEO"                , sampleRequestVO.getSusin_buseo       ());      
      map.put("ARG_SUSINJA"                    , sampleRequestVO.getSusinja           ());      
      map.put("ARG_IBHOIJA"                    , sampleRequestVO.getIbhoija           ());      
      map.put("ARG_NABPUM_ILJA"                , sampleRequestVO.getNabpum_ilja       ());      
      map.put("ARG_YESANG_GEUMAEG"             , sampleRequestVO.getYesang_geumaeg    ());      
      map.put("ARG_SAYONG_GEUMAEG"             , sampleRequestVO.getSayong_geumaeg    ());      
                                                                                                
      map.put("ARG_HIMANG_GAGYEOG"             , sampleRequestVO.getHimang_gagyeog    ());      
      map.put("ARG_EX_GEOLAECHEO"              , sampleRequestVO.getEx_geolaecheo     ());      
      map.put("ARG_EX_GYEONBON_YN"             , sampleRequestVO.getEx_gyeonbon_yn    ());      
      map.put("ARG_DOJANG_BANGBEOB"            , sampleRequestVO.getDojang_bangbeob   ());      
      map.put("ARG_DOJANG_GONGJEONG"           , sampleRequestVO.getDojang_gongjeong  ());      
      map.put("ARG_GEONJO_BANGBEOB"            , sampleRequestVO.getGeonjo_bangbeob   ());      
      map.put("ARG_DORYO_TYPE"                 , sampleRequestVO.getDoryo_type        ());      
      map.put("ARG_SOJAE_JONGLYU"              , sampleRequestVO.getSojae_jonglyu     ());      
      map.put("ARG_GITA_YOGU6"                 , sampleRequestVO.getGita_yogu6        ());      
      map.put("ARG_GITA_YOGU3"                 , sampleRequestVO.getGita_yogu3        ());      
                                                                                                
      map.put("ARG_BIGO1"                      , sampleRequestVO.getBigo_1             ());      
      map.put("ARG_BIGO2"                      , sampleRequestVO.getBigo_2             ());      
      map.put("ARG_BIGO3"                      , sampleRequestVO.getBigo_3             ());      
      map.put("ARG_GYEOLGWA_GIHAN"             , sampleRequestVO.getGyeolgwa_gihan    ());			
			map.put("OUT_PARAM", "");
			
			dao.select("sdph0052.procedure_updateSampleRequest", map);

			System.out.println("AAA111");

			if(!map.get("OUT_PARAM").equals("OK")) {
				System.out.println("AAA222");
				//결과가 에러 발생하면 트랜잭션을 닫고 에러페이지로 이동
				System.out.println((String) map.get("OUT_PARAM"));
				dao.endTransaction();
				return "templates/error";
			}
			System.out.println("AAA333");

			//sub 입력
			if(jsonList.length() > 0) {
				System.out.println("AAA444");
				//기존 sub 품목 전체 삭제
				map = new HashMap<String, Object>();
				map.put("ARG_FLAG", "delete");
				map.put("ARG_BIZ_AREA_CD", sampleRequestVO.getWorkplace());
				map.put("ARG_REQ_DT", getExpDateString(sampleRequestVO.getIlja()));
				map.put("ARG_REQ_NO", sampleRequestVO.getJeonpyo_no());
				map.put("ARG_CUST_CD", sampleRequestVO.getCust_num());
				map.put("OUT_PARAM", "");
				dao.select("sdph0052.procedure_updateSampleRequestItem", map);
				
				if(!map.get("OUT_PARAM").equals("OK")) {
					//결과가 에러 발생하면 트랜잭션을 닫고 에러페이지로 이동
					System.out.println((String) map.get("OUT_PARAM"));
					dao.endTransaction();
					return "templates/error";
				}
				
				
				JSONObject obj = new JSONObject();

				for(int i=0; i < jsonList.length(); i++) {
					obj = (JSONObject) jsonList.get(i);

					map = new HashMap<String, Object>();
					map.put("ARG_FLAG", "insert");
					   map.put("ARG_BIZ_AREA_CD"                    ,    sampleRequestItemVO.getSaeobjang            ());   
				          map.put("ARG_ORD_DT"                         ,    sampleRequestItemVO.getIlja                 ());   
				          map.put("ARG_ORD_NO"                         ,    sampleRequestItemVO.getJeonpyo_no            ());   
				          map.put("ARG_SEQ"                            ,    Integer.toString(i + 1)                        );   
				          map.put("ARG_SEQ_1"                          ,    Integer.toString(i + 1)                      );     
				          map.put("ARG_SIL_GEOLAECHEO"                 ,    sampleRequestItemVO.getSil_geolaecheo        ());   
				          map.put("ARG_GYEONBON_GUBUN"                 ,    sampleRequestItemVO.getGyeonbon_gubun        ());   
				          map.put("ARG_PUMMOG_BUNRYU"                  ,    sampleRequestItemVO.getPummog_bunryu         ());   
				          map.put("ARG_GEOLAECHEO_CODE1"               ,    sampleRequestItemVO.getGeolaecheo_code      ());   

				          map.put("ARG_SANGHO1"                        ,    sampleRequestItemVO.getSangho               ());   
				          map.put("ARG_GEOLAECHEO_CODE2"               ,    sampleRequestItemVO.getGeolaecheo_code_2      ());   
				          map.put("ARG_SANGHO2"                        ,    sampleRequestItemVO.getSangho_2               ());   
				          map.put("ARG_PUMMOG_CODE"                    ,    sampleRequestItemVO.getPummog_code           ());   
				          map.put("ARG_PUMMYEONG"                      ,    sampleRequestItemVO.getPummyeong             ());   
				          map.put("ARG_PO_DANWI_A"                     ,    sampleRequestItemVO.getPo_danwi_a            ());   
				          map.put("ARG_PO_DANWI_B"                     ,    sampleRequestItemVO.getPo_danwi_b            ());   
				          map.put("ARG_PO_SU"                          ,    sampleRequestItemVO.getPo_su                 ());   
				          map.put("ARG_PRICE_YN"                       ,    sampleRequestItemVO.getPrice_yn              ());   
				          map.put("ARG_DOPYEON_YN"                     ,    sampleRequestItemVO.getDopyeon_yn            ());   

				          map.put("ARG_MODEL_CODE"                     ,    sampleRequestItemVO.getModel_code            ());   
				          map.put("ARG_BALHAENGIL"                     ,    ""                                              );   
				          map.put("ARG_BALHAENG_BUSEO"                 ,    ""                                             );   
				          map.put("ARG_BALHAENGJA"                     ,    ""                                             );   
				          map.put("OUT_PARAM"                          ,    ""                                             );   
 


					dao.select("sdph0052.procedure_updateSampleRequestItem", map);
					
					if(!map.get("OUT_PARAM").equals("OK")) {
						//결과가 에러 발생하면 트랜잭션을 닫고 에러페이지로 이동
						System.out.println((String) map.get("OUT_PARAM"));
						dao.endTransaction();
						return "templates/error";
					}
					
				}
			}
			else
			{
				System.out.println("BBB444");
			}
			System.out.println("BBB555");
			
			dao.executeBatch();
			dao.commit();
			
			redirectAttr.addFlashAttribute("sampleRequestVO", sampleRequestVO);
			return "redirect:/sdph005201d.do";
			
		} catch(Exception e) {
			return "templates/error";
			
		} finally {
			dao.endTransaction();
		}
	}

	@RequestMapping(value = "sdph005201d_delete.do")		//샘플출고요청 삭제
	public String sdph005201d_delete(@ModelAttribute("sampleRequestVO")SampleRequestVO sampleRequestVO, RedirectAttributes redirectAttr,
			HttpServletRequest request, ModelMap model, Locale locale) throws Exception {
		HashMap<String, Object> map;
		
		//header 입력
		map = new HashMap<String, Object>();
		map.put("ARG_FLAG", "delete");
		map.put("ARG_BIZ_AREA_CD", sampleRequestVO.getWorkplace());
		map.put("ARG_ORD_DT", getExpDateString(sampleRequestVO.getIlja()));
		map.put("ARG_ORD_NO", sampleRequestVO.getJeonpyo_no());
		map.put("ARG_CUST_CD", sampleRequestVO.getCust_num());
		map.put("OUT_PARAM", "");
		
		dao.select("sdph0052.procedure_updateSampleRequest", map);
		
		if(!map.get("OUT_PARAM").equals("OK")) {
			//결과가 에러 발생하면 트랜잭션을 닫고 에러페이지로 이동
			System.out.println((String) map.get("OUT_PARAM"));
			dao.endTransaction();
			return "templates/error";
		}
		
		redirectAttr.addFlashAttribute("sampleRequestVO", sampleRequestVO);
		
		return "redirect:/sdph005201l.do";
	}
	
	
	
	
	
	
}

