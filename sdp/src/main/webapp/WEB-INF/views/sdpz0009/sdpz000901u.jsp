<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 로그인  -->
<!DOCTYPE html>
<html>
<head>
<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />
<script type="text/javascript">
	$(document).ready(function() {
		//쿠키가 저장되어 있는지 검사
		cookieCheck();
		
		//ID에 스페이스 입력 방지
		$("#cust_num").on("keyup", function(event) {
			$(event.target).val($(event.target).val().replace(/ /gi, ""));
		});
		
		$("#password").on("keyup", function(event) {
			if(event.keyCode == "13") {
				login();
			}
		});
	});
	
	
	//비밀번호 찾기 다이얼로그
	function dialog_findPw() {
		$("#find_phone").val("");
		
		//로그인창에 아이디가 입력 되어 있으면 가지고 감
		if($("#cust_num").val().length > 0) {
			$("#find_cust_num").val($("#cust_num").val());
		}
		
		$("#dialog_findPw").dialog({
			title : "비밀번호 찾기"
			,resizable : false
			,height : "auto"
			,width : "auto"
			,modal : true
		});
		
		if($("#cust_num").val().length > 0) {
			$("#find_phone").focus();
		}
	}
	
	//로그인 시도
	function login() {
		//id 체크
		if($("#cust_num").val().length < 6) {
			//c_alert("ID는 6자리 이상입니다.");
			//return;
		}
		
		//id에 띄어쓰기 제거
		$("#cust_num").val($("#cust_num").val().replace(/ /gi, ""));
		
		$.ajaxSettings.traditional = true;
		jQuery.ajax({
			type : "POST",
			url : "ajaxLogin.do",
			data : {
				cust_num : $("input[name='cust_num']").val()
				, password : $("input[name='password']").val()
			},
			datatype : "JSON",
			success : function(data) {
				if(data.login_result == "0") {
					c_alert("입력하신 회원 ID가 존재 하지 않습니다.<br>ID를 확인해 주십시오.");
					$("#password").val("");
					
				} else if(data.login_result == "2"){
					c_alert("입력하신 정보와 일치하는 계정이 없습니다.<br>아이디와 비밀번호를 확인해 주십시오.");
					$("#password").val("");
					
				} else {
					//아이디 저장 체크박스 확인
					if ($("#chkBox_saveId").prop("checked")) {
						setCookie("cust_num", $("#cust_num").val(), 7);		//cust_num 7일간 저장
					} else {
						setCookie("cust_num", "", 0);		//cust_num 쿠키 삭제
					}
					
					c_href("main.do");
				}
			},
			error : function(xhr, status, error) {
				alert("error");	
			}
		});
	}
	
	//쿠키 저장
	function setCookie(name, value, dt) {
		var today = new Date();
		today.setDate(today.getDate() + dt);
		document.cookie = name + "=" + escape(value) + "; path=/; expires=" + today.toGMTString() + ";";
	}
	
	//저장된 쿠키가 있는지 검사
	function cookieCheck() {
		// userid 쿠키에서 id 값을 가져온다.
		var cook = document.cookie + ";";
		var idx = cook.indexOf("cust_num", 0);
		var val = "";

		if (idx != -1) {
			cook = cook.substring(idx, cook.length);
			begin = cook.indexOf("=", 0) + 1;
			end = cook.indexOf(";", begin);
			val = unescape(cook.substring(begin, end));
		}

		// 가져온 쿠키값이 있으면
		if (val != "") {
			document.getElementById("cust_num").value = val;
			document.getElementById("chkBox_saveId").checked = true;
		}
	}
	
	//비밀번호 찾기
	function findPassword() {
// 		c_alert("개발중!");
		$.ajaxSettings.traditional = true;
		jQuery.ajax({
			type : "POST",
			url : "ajaxFindPassword.do",
			data : {
				cust_num : String($("input[name='find_cust_num']").val()).trim()
				, hp_no : String($("input[name='find_phone']").val()).trim()
			},
			datatype : "JSON",
			success : function(data) {
				if(data.findpw_result == "fail") {
					c_alert("입력하신 정보와 일치하는 계정이 없습니다.<br>아이디와 전화번호를 확인해 주십시오.");
				} else {
					closeDialog("dialog_findPw");
					c_alert("초기화된 비밀번호가 문자메시지로 발송되었습니다.<br>등록된 대표자 휴대전화의 문자 메시지를 확인해주십시오.");
					
					$("input[name='cust_num']").val($("input[name='find_cust_num']").val());
					$("input[name='find_cust_num']").val("");
					$("input[name='find_phone']").val("");
				}
			},
			error : function(xhr, status, error) {
				alert("error");
			}
		});
	}
</script>
<title>한진화학 주문관리 시스템</title>
</head>

<body class="wrap_loginbg">
<form id="frm" name="frm">
	<div class="login_wrap" style="background: #0444aa;">
		<div class="login_logo">
			<h1 class="logo"></h1>
			<p style="color:#fff; font-weight: bold;">한진화학 주문관리 시스템</p>
		</div>
		<div class="login_box_wrap">
<!-- 			<select> -->
<!-- 				<option value="">고객</option> -->
<!-- 				<option value="">관리자</option> -->
<!-- 			</select> -->
			<input type="text" class="loginbox" placeholder="고객ID" id="cust_num" name="cust_num" maxlength="14" value="" style="background-color: #fff;"/>
			<input type="password" class="loginpw" placeholder="비밀번호" id="password" name="password" maxlength="14" value="" style="background-color: #fff;">
			<input type="button" value="로그인" class="loginbtn" onclick="login();" style="background-color:#00a6ff "/>
			<div style="width:360px; height:20px;">
				<input class="search_checkbox" id="chkBox_saveId" name="chkBox_saveId" type="checkbox" value=""/>
				<label class="search_label" for="chkBox_saveId" style="float:left; margin-left:26px !important; padding:0px!important;margin-right:0px !important;">아이디 저장</label>
				<a style="" href="javascript:dialog_findPw();">비밀번호 찾기</a>
			</div>
		</div>				
		<p class="lostpw" style="text-align: right;">
			<a>업무문의  ☎ 070-7096-3079<br>전산문의  ☎ 070-7096-3566</a>
		</p>
	</div>
	
	
	<jsp:directive.include file="/WEB-INF/views/templates/dialog_sdpz000901u.jsp" />
	<jsp:directive.include file="/WEB-INF/views/templates/dialog_common.jsp" />
</form>
</body>
</html>