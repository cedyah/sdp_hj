<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />
<title>한진화학 주문관리 시스템</title>
</head>
<body>	
	<!--wrap end-->
	<div class="wrap">
			<jsp:directive.include file="/WEB-INF/views/templates/header.jsp" />			
				<div class="sub_wrap_area">
					<div class="sub_wrap">
						<div class="sub_contents">					
<!-- 							<div class="local_nav_wrap"> -->
<!-- 						 		<h3 class="sub_tit">공지사항</h3>		       				 -->
<!-- 				       				<div class="local_nav"> -->
<!-- 				                         <ul> -->
<!-- 					                           <li class="home">홈</li> -->
<!-- 					                           <li>공지사항</li> -->
<!-- 				                         </ul> -->
<!-- 				                   </div> -->
<!-- 				                   local_nav		                    -->
<!-- 		       				</div> -->
		       				<!--local_nav_wrap-->	
		         			<div class="sub_cont">		
								<p class="errorimg_area"></p>
								<p class="error_txt">요청하신 부분에 대하여 404 에러가 발생하였습니다.<br />자세한 사항은 IT 팀에 문의 바랍니다.</p>
								<p class="error_cs">담당자 02-1234-1234</p>
		         			</div>
		         			<!--// sub_cont-->							
						</div>
						<!--// sub_contents-->
					</div>
					<!--// sub_wrap-->
				</div>
				<!--// sub_wrap_area-->
			<jsp:directive.include file="/WEB-INF/views/templates/footer.jsp" />
		</div>
		<!--wrap end-->
</body>
</html>