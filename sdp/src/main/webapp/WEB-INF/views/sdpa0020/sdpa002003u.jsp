<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 견본요청서 등록 -->
<!DOCTYPE html>
<html>
<head>
<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />
<style type="text/css"></style>
<title>한진화학 주문관리 시스템</title>
</head>

<body>
	<form id="frm" name="frm">
		<div class="wrap">
			<jsp:directive.include file="/WEB-INF/views/templates/header.jsp" />

			<div class="sub_wrap_area">
	           <div class="sub_wrap">
					<div class="sub_contents">
				
							내용 
				
					</div>
					<!--sub_contents-->
				</div>
				<!--sub_wrap-->
			</div>
			<!--sub_wrap_area-->
			<jsp:directive.include file="/WEB-INF/views/templates/footer.jsp" />
		</div>
		<!--wrap end-->
	</form>
</body>
</html>