<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<style type="text/css">
/* 제품검색 조건 체크박스 */
/* .div_itemGroupTitle {width:100%; height:45px; background-color: #606c79;} */
.div_itemGroupTitle {width:100%; height:20px; text-align: right;}
.div_itemGroupTitle a {cursor:pointer; color:#0100FF; font-size:13px;}
.div_itemGroupTitle a:hover{text-decoration:underline; font-weight: bold;}
.div_itemGroupTitle td{color:#fff; text-align: center; vertical-align: middle; font-size: 18px;}
.div_itemGroupTitle .btn_plus{background-color:#808c98; z-index:5; width:42px; height:42px; color:#fff; font-weight:bold; font-size:25px;}
.div_itemGroupTitle .btn_minus{background-color:#808c98; z-index:5; width:42px; height:42px; color:#fff; font-weight:bold; font-size:25px;}

/* .div_itemGroup {width:1060px; height:450px; display: none;} */
.div_itemGroup {width:auto; height:450px; display: none; overflow: hidden;}
.div_daebun {width:37%; height:445px; float:left; background-color:#f1f1f1; overflow:auto;}
.div_daebun ul {width:100%; height:auto; overflow:hidden;}
.div_daebun ul li {font-weight: bold; height:30px; cursor:pointer; vertical-align: middle; text-align:left; padding-left:10px; padding-top:10px; float:left;}
.div_daebun label{width:75px !important; height:100%; float:left;}

.div_jungbun {width:63%; height:445px; float:left; overflow: auto; background-color:#f1f1f1;}

.div_jungbun_sub {width:auto; height:auto; overflow:hidden; background-color:#fff; margin-top:5px; margin-bottom:5px; margin-right:5px;}
.div_jungbun_sub span {float:left; width:100%; height:25px; font-size: 15px; font-weight:bold; color:#fff; text-align:center; background-color:#808c98;}
.div_jungbun_sub ul {float:left; width:100%; height:auto%;}
.div_jungbun_sub ul li {height:30px; cursor:pointer; vertical-align: middle; text-align:left; padding-left:20px; padding-top:7px; float:left;}
.div_jungbun_sub label{width:150px; height:100%; float:left;}

.div_itemGroupTitle table{height:100%;}
.div_itemGroupTitle td{text-align: center; font-weight: bold; vertical-align:middle;}
</style>

<script type="text/javascript">
$(document).ready(function() {
	//상단 검색확장 체크
	if(sessionStorage.getItem("search_toggle") == "show") {
// 		$("#btn_displayCheck").removeClass();
// 		$("#btn_displayCheck").addClass("btn_minus");
// 		$("#btn_displayCheck").val("-");
// 		$("div[class='div_itemGroup']").show();

		$("#a_displayCheck").removeClass();
		$("#a_displayCheck").addClass("minus");
		$("#a_displayCheck").html("제품분류 접기");
		$("div[class='div_itemGroup']").show();
		
	} else {
// 		$("#btn_displayCheck").removeClass();
// 		$("#btn_displayCheck").addClass("btn_plus");
// 		$("#btn_displayCheck").val("+");
	}
	
	//확장버튼 클릭시 show/hide
// 	$("#btn_displayCheck").on("click", function(event) {
// 		if($(event.target).attr("class") == "btn_plus") {
// 			$(event.target).removeClass();
// 			$(event.target).addClass("btn_minus");
// 			$(event.target).val("-");
// 			$("div[class='div_itemGroup']").slideDown();
// 			sessionStorage.setItem("search_toggle", "show");
			
// 		} else {
// 			$(event.target).removeClass();
// 			$(event.target).addClass("btn_plus");
// 			$(event.target).val("+");
// 			$("div[class='div_itemGroup']").slideUp();
// 			sessionStorage.setItem("search_toggle", "hide");
// 		}
// 	});
	
	//확장버튼 클릭시 show/hide
	$("#a_displayCheck").on("click", function(event) {
		if($(event.target).attr("class") == "plus") {
			$(event.target).removeClass();
			$(event.target).addClass("minus");
			$(event.target).html("제품분류 접기");
			$("div[class='div_itemGroup']").slideDown();
			sessionStorage.setItem("search_toggle", "show");
			
		} else {
			$(event.target).removeClass();
			$(event.target).addClass("plus");
			$(event.target).html("제품분류 펼쳐보기");
			$("div[class='div_itemGroup']").slideUp();
			sessionStorage.setItem("search_toggle", "hide");
		}
	});
	
	//넘어온 대분류와 중분류값 검사
	if("${searchVO.search_daebun}".length > 0) {
		var search_daebun = "${searchVO.search_daebun}".split(","); 
		var search_jungbun = "${searchVO.search_jungbun}".split(",");
		
		for(var i=0; i < search_daebun.length; i++) {
			$("#chkBox_"+search_daebun[i]).prop("checked", true);
			$("#div_jungbun_" + search_daebun[i]).show();
		}
		
		//넘어온 중분류값이 있는지 검사
		if(search_jungbun.length > 0) {
			for(var i=0; i < search_jungbun.length; i++) {
// 				console.log(search_daebun + " // " + search_jungbun[i].substring(0, 1) + " == " + search_daebun.indexOf(search_jungbun[i].substring(0, 1)));
				if(search_daebun.indexOf(search_jungbun[i].substring(0, 1)) >= 0) {
					$("#chkBox_"+search_jungbun[i]).prop("checked", true);
				}
			}
		}
		
		//중분류 전체 체크박스 확인
		for(var i=0; i < search_daebun.length; i++) {
// 			alert($("#div_jungbun_" + search_daebun[i]).find("input[name='search_jungbun']:not(:checked)").length);
			if($("#div_jungbun_" + search_daebun[i]).find("input[name='search_jungbun']:not(:checked)").length == 0) {
				$("#div_jungbun_" + search_daebun[i]).find("input[name='chkAll_jungbun']").prop("checked", true);
			}
		}
		
		if($("input[name='search_daebun']:not(:checked)").length == 0) {
			$("#chkAll_daebun").prop("checked", true);
		}
	}
	
	
	//체크되어 있는 대분류의 중분류를 show 한 상태로 화면 불러옴
// 	var li_daebun = $("input[name='search_daebun']:checked");
// 	for(var i=0; i < li_daebun.length; i ++) {
// 		$("#div_jungbun_" + $(li_daebun[i]).val()).show();
// // 		$("#div_jungbun_" + $(li_daebun[i]).val()).find("input[type='checkbox']").prop("checked", true);
// 	}
	
	//중분류 클릭시 전체 선택 상태가 되면 All체크박스를 선택, 혹은 선택 해제
	$("input[name='search_jungbun']").on("change", function(event) {
		var li_chkBox = $(event.target).parent().parent().parent().find("input[name='search_jungbun']:not(:checked)");
		
		if(li_chkBox.length == 0) {
			$(event.target).parent().parent().parent().find("input[name='chkAll_jungbun']").prop("checked", true);
		} else {
			$(event.target).parent().parent().parent().find("input[name='chkAll_jungbun']").prop("checked", false);
		}
	});
	
	//대분류 선택시 해당되는 중분류를 표시하거나 숨김
	$("input[name='search_daebun']").on("change", function(event) {
		selectJungbun(event.target);
		//대분류가 모두 선택이 되면 전체 체크박스 선택
		if($("input[name='search_daebun']:not(:checked)").length == 0) {
			$("input[name='chkAll_daebun']").prop("checked", true);
		} else {
			$("input[name='chkAll_daebun']").prop("checked", false);
		}
	});

	//대분류 전체 선택시 해당되는 중분류를 표시하거나 숨김
	$("input[name='chkAll_daebun']").on("change", function(event) {
		if($(event.target).prop("checked") == true) {
			checkAllDaebun(event.target);
			$("div[name='div_jungbun']").show();
			$(event.target).parent().parent().parent().parent().find("input[type='checkbox']").prop("checked", true);
		} else {
			checkAllDaebun(event.target);
			$("div[name='div_jungbun']").hide();
			$(event.target).parent().parent().parent().parent().find("input[type='checkbox']").prop("checked", false);
		}
	});
	
});

//중분 전체 체크박스
function checkAllDaebun(obj) {
// 	alert($(obj).parent().parent().html());
	if($(obj).prop("checked") == true) {
		$(obj).parent().parent().parent().find("input[name='search_daebun']").prop("checked", true);
	} else {
		$(obj).parent().parent().parent().find("input[name='search_daebun']").prop("checked", false);
	}
}

//중분 전체 체크박스
function checkAllJungbun(obj) {
	if($(obj).prop("checked") == true) {
		$(obj).parent().parent().parent().find("input[name='search_jungbun']").prop("checked", true);
	} else {
		$(obj).parent().parent().parent().find("input[name='search_jungbun']").prop("checked", false);
	}
}

//대분 체크박스 선택하여 중분 표시
function selectJungbun(obj) {
	var selectDaebun = $(obj).val();
	
	if($(obj).prop("checked") == true) {
		$("#div_jungbun_" + $(obj).val()).show();
		$("#div_jungbun_" + $(obj).val()).find("input[type='checkbox']").prop("checked", true);
	} else {
		$("#div_jungbun_" + $(obj).val()).hide();
		$("#div_jungbun_" + $(obj).val()).find("input[type='checkbox']").prop("checked", false);
	}
	
}

</script>
<div class="div_itemGroupTitle">
<!-- 	<table> -->
<!-- 		<colgroup> -->
<!-- 			<col style="width:424px;"/> -->
<!-- 			<col style="width:590px;"/> -->
<!-- 			<col style="width:;"/> -->
<!-- 		</colgroup> -->
<!-- 		<tr> -->
<!-- 			<td>대분류</td> -->
<!-- 			<td>중분류</td> -->
<!-- 			<td><input class="btn_plus" id="btn_displayCheck" name="btn_displayCheck" type="button" value="+"></td> -->
<!-- 		</tr> -->
<!-- 	</table> -->
	<a class="plus" id="a_displayCheck" onclick="">제품분류 펼쳐보기</a>
</div>
<div class="div_itemGroup" id="div_itemGroup">
	<!-- 대분 체크박스 -->
	<div class="div_daebun" >
		<ul>
			<li>
				<input class="search_checkbox" id="chkAll_daebun" name="chkAll_daebun" type="checkbox" onclick="checkAllDaebun(this);"/>
				<label class="search_label" for="chkAll_daebun" style="color: #FF0000;">전체</label>
			</li>
		</ul>
		<ul>
			<c:if test="${fn:length(search_daebun) > 0}">
				<c:forEach items="${search_daebun}" var="row" varStatus="status">
					<li>
						<input class="search_checkbox" id="chkBox_${row.minor_cd}" name="search_daebun" type="checkbox" onclick="" value="${row.minor_cd}"/>
						<label class="search_label" for="chkBox_${row.minor_cd}" style="">${status.count}. ${row.cd_nm}</label>
					</li>
				</c:forEach>
			</c:if>
		</ul>
	</div>
	
	<!-- 중분 체크박스 -->
	<div class="div_jungbun">
		<c:if test="${fn:length(search_daebun) > 0}">
			<c:forEach items="${search_daebun}" var="dRow" varStatus="dStatus">
				<div class="div_jungbun_sub" id="div_jungbun_${dRow.minor_cd}" name="div_jungbun" style="display:none;">
					<span>${dStatus.count}. ${dRow.cd_nm}</span>
					<ul>
						<li>
							<input class="search_checkbox" id="chkAll_${dRow.minor_cd}" name="chkAll_jungbun" type="checkbox" onclick="checkAllJungbun(this);"/>
							<label class="search_label" for="chkAll_${dRow.minor_cd}"  style="color: #FF0000;">전체</label>
						</li>
					</ul>
					<ul>
						<c:if test="${fn:length(search_jungbun) > 0}">
							<c:forEach items="${search_jungbun}" var="jRow" varStatus="jStatus">
								<c:if test="${dRow.minor_cd == jRow.daebun}">
									<li>
										<input class="search_checkbox" id="chkBox_${dRow.minor_cd}${jRow.jungbun}" 
											name="search_jungbun" type="checkbox" value="${dRow.minor_cd}${jRow.jungbun}"/>
										<label class="search_label" for="chkBox_${dRow.minor_cd}${jRow.jungbun}" style="">${jRow.jungbun_m}</label>
									</li>
								</c:if>
							</c:forEach>
						</c:if>
					</ul>
				</div>
			</c:forEach>
		</c:if>
	</div>
</div>