<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="./js/jquery-1.11.2.js"></script>
<script type="text/javascript">
	$(window).load(function(){
		var strMessage = "<c:out value='${message}' escapeXml='false' />";
		var strScript = "${script}";	// jstl 로 처리하면 jstl 자체적으로 특수문자를 처리해서 스크립트 사용이 힘듬.
		var strType = "<c:out value='${type}' />";
		var strError = "<c:out value='${error}' />";
		var fncScript = function(strType){
			if(strType == "self"){
				self.close();
			}else if(strType == "parent"){
				parent.close();
			}
		};
		
		if(strMessage.length > 0){
			alert(strMessage);
		}
		if(strError.length > 0){
			alert(strError);
			history.back(-1);
		}
		if(strScript.length > 0){
			//out.print(script);
			//alert(strScript);
			eval(strScript);
		}
		if(strType.length > 0){
			fncScript(strType);
		}
	});
</script>
<title></title>
</head>
<body>
</body>
</html>