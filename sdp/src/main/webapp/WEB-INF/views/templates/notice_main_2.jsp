<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />
<!DOCTYPE html>
<html>
<head>
<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />
<style type="text/css">
   body {width:auto; height:auto;}
   .notice_wrap {width:500px;height:570px;;margin:10px;background:#e2eaf3;}
   .notice_title{width:500px;height:100px; vertical-align: middle; margin:10px;}
   .notice_title h3 {width:480px;height:90px; font-size:20px; font-weight:bold; color:#333; overflow: hidden;}
   
   .notice_contents{width:100%;height:auto; overflow:hidden; }
   .notice_contents p {font-size:14px; width:470px;height:420px; margin:10px; padding:5px; overflow:hidden; background-color: #ffffff;}
   
   .notice_footer{width:100%;height:auto; overflow:hidden; vertical-align:middle; padding-top:20px;}
   .notice_footer a{cursor:pointer; width:auto;font-size:16px;}
   .notice_footer a:hover{font-weight:bold; text-decoration:underline; }
   .notice_footer .a_noShowClose{float:left; margin:10px;}
   .notice_footer .input_onlyClose{float:right; margin:10px;}
</style>
<script type="text/javascript">
   function noticePopClose(flag) {
      //Y로 들어올 경우 공지 팝업이 7일간 열리지 않음(단 브라우저 캐쉬정보를 삭제하거나 다른 브라우저로 접속시에 뜰수도 있음)
      if(flag == "Y") {
         setCookie("main_noticeCheck2", "Y", "7");
      }
      self.close();
   }
   
   //쿠키 저장
   function setCookie(name, value, dt) {
      var today = new Date();
      today.setDate(today.getDate() + dt);
      document.cookie = name + "=" + escape(value) + "; path=/; expires=" + today.toGMTString() + ";";
   }
</script>
<title>공지사항</title>
</head>
<body>
	<div class="notice_wrap">
<!-- 		<div class="notice_title"> -->
<!-- 		</div> -->
		
		<div class="notice_contents">
			<img src="notice/notice2.gif">
		</div>
		
		<div class="notice_footer">
			<a class="a_noShowClose" href="javascript:noticePopClose('Y');" style="">앞으로 이 알림을 7일동안 차단합니다</a>
			<input class="input_onlyClose order_addlist" type="button" onclick="javascript:noticePopClose('N');" value="닫기"/>
		</div>
	</div>
</body>
</html>