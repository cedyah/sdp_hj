<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html style="height:100%">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<jsp:directive.include file="/WEB-INF/views/common/oz_include.jsp" />

<!-- If you want to run the HTML5SVG viewer please change the OZJSViewer.js to OZJSSVGViewer.js.
<script type="text/javascript" src="http://121.138.159.134:8080/oz70/ozhviewer/OZJSSVGViewer.js" charset="utf-8"></script>
-->

</head>
<body style="width:100%;height:100%">
<div id="OZViewer" style="width:98%;height:98%">
</div>
<script type="text/javascript" >
	var url = "${map.url}";
	alert("좌측 상단에 디스크 모양의 저장 버튼을 누르시면 엑셀파일로 저장이 가능 합니다.");
	function SetOZParamters_OZViewer(){
		var oz;
		
		oz = document.getElementById("OZViewer");
		oz.sendToActionScript("connection.servlet", url + "/oz70/server");
		oz.sendToActionScript("connection.reportname", "/sdp/sdpe001001d_eng.ozr");
		
		//사용자변수 지정
		oz.sendToActionScript("odi.odinames", "sdpe001001d");
		oz.sendToActionScript("odi.sdpe001001d.pcount", "4");
		oz.sendToActionScript("odi.sdpe001001d.args1", "ARG_CUST_NM=${map.ARG_CUST_NM}");
		oz.sendToActionScript("odi.sdpe001001d.args2", "ARG_BIZ_AREA_CD=${map.ARG_BIZ_AREA_CD}");
		oz.sendToActionScript("odi.sdpe001001d.args3", "ARG_ITEM_CD=${map.ARG_ITEM_CD}");
		oz.sendToActionScript("odi.sdpe001001d.args4", "ARG_LOT_NO=${map.ARG_LOT_NO}");
		
		return true;
	}
	start_ozjs("OZViewer", url + "/oz70/ozhviewer/");
</script>
</body>
</html>
