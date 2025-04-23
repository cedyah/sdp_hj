<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 공지사항 등록  -->
<!DOCTYPE html>
<html>

<head>
<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />
<title>한진화학 주문관리 시스템</title>
<script type="text/javascript">
	$(document).ready(function() {
		//구분에 따라 기간 및 링크 활성화/비활성화
		$("#notice_type").on("change", function(event) {
			if($(event.target).val() == 'G'){
				$("#div_noticeDate").show();
				$("#notice_from").attr("disabled", false);
				$("#notice_to").attr("disabled", false);
				
				$("#tr_link").hide();
				$("#link").attr("disabled", true);
				
				$("#p_typeL").hide();
				$("#p_typeP").hide();

			} else if($(event.target).val() == 'L') {
				$("#div_noticeDate").hide();
				$("#notice_from").attr("disabled", true);
				$("#notice_to").attr("disabled", true);
				
				$("#tr_link").show();
				$("#link").attr("disabled", false);
				
				$("#p_typeL").show();
				$("#p_typeP").hide();
				
			} else {
				$("#div_noticeDate").hide();
				$("#notice_from").attr("disabled", true);
				$("#notice_to").attr("disabled", true);
				
				$("#tr_link").hide();
				$("#link").attr("disabled", true);
				
				$("#p_typeL").hide();
				$("#p_typeP").show();
			}
		});
	});
	
	//저장
	function confirm() {
		//상품권시 날짜체크
		if($("#notice_type").val() == 'G') {
			if($("#notice_from").val() == '' || $("#notice_to").val() == '') {
				c_alert("기간을 입력해주십시오");
				return;
			}
			
			if(returnNum($("#notice_to").val()) < returnNum($("#notice_from").val())) {
				c_alert("기간의 시작일은 종료일보다 작아야 합니다");
				$("#notice_to").val($("#notice_from").val());
				return;
			}
			
			if(!duplCheck()){
				c_alert("해당 월에는 이미 상품권 신청 기간이 등록되어 있습니다");
				return;
			}
		}
		
		var li_file = [];
		var file_nm = "";
		li_file = $("input[type='file']");
		for(var i=0; i < li_file.length; i++) {
			file_nm = $(li_file[i]).val();

			if(file_nm != null && file_nm != "") {
				if(!fileExtensionCheck($("#notice_type").val(), file_nm)) {
					return;
				}
			}
		}
		
		//데이터 검증
		if(!doFormValidate(document.frm)){
			return;
		}
		
		if("${flag}" == "insert") {
			c_submit("frm", "sdpy001001u_insert.do");
		} else {
			c_submit("frm", "sdpy001001u_update.do");
		}
		
	}
	
	//목록으로
	function returnList() {
		$("#notice_type").val("");
		c_submit("frm", "sdpy001001l.do");
	}

	
	//상세페이지로
	function returnDetail() {
		c_submit("frm", "sdpy001001d.do");
	}
	
	
	//상품권 입력시 기간 중복 체크
	function duplCheck() {
		var result;
		$.ajaxSettings.traditional = true;
		jQuery.ajax({
			type : "POST",
			url : "ajaxNoticeDuplCheck.do",
			data : {
				notice_from : $("#notice_from").val()
				, notice_num : $("#notice_num").val()
				, notice_type : "G"
			},
			datatype : "JSON",
			async : false,
			success : function(data) {
				if(data.result == "true"){
					result = true;
				} else {
					result = false;
				}
			},
			error : function(xhr, status, error) {
				alert("error");
			}
		});
		return result;
	}
</script>
</head>

<body>
<form id="frm" name="frm" enctype="multipart/form-data">
<input type="hidden" id="notice_num" name="notice_num" value="${noticeVO.notice_num}"/>

<!-- 검색조건 -->
<input type="hidden" id="searchDiv" name="searchDiv" value="${noticeVO.searchDiv}"/>
<input type="hidden" id="searchText" name="searchText" value="${noticeVO.searchText}"/>

<!-- 페이징 변수 -->
<input type="hidden" id="page_current" name="page_current" value="${noticeVO.page_current}">
<input type="hidden" id="page_totalCnt" name="page_totalCnt" value="${noticeVO.page_totalCnt}">      
<input type="hidden" id="page_countPer" name="page_countPer" value="${noticeVO.page_countPer}">

	<div class="wrap">
		<jsp:directive.include file="/WEB-INF/views/templates/header.jsp" />
		<div class="sub_wrap_area">
			<div class="sub_wrap">
				<div class="sub_contents">
					<div class="local_nav_wrap">
						<h3 class="sub_tit">공지사항</h3>
						<div class="local_nav">
							<ul>
								<li class="home">홈</li>
								<li>공지사항</li>
							</ul>
						</div>
						<!--local_nav-->
					</div>
					<!--local_nav_wrap-->
					<div class="sub_cont">
						<div class="tbl_wrap">
							<table class="tbl_basic" summary="공지사항 등록 입력폼">
								<caption>공지사항 등록</caption>
								<colgroup>
									<col style="width:15%" />
									<col style="width:35%" />
									<col style="width:15%" />
									<col style="width:35%" />
								</colgroup>
								<tbody>
									<tr class="first">
										<th scope="row">구분</th>
										<td class="last">
											<select style="width:100px;" id="notice_type" name="notice_type"
												<c:if test="${flag == 'update'}">disabled="disabled"</c:if>>
												<option value="A" <c:if test="${fn:trim(notice.notice_type) == 'A'}">selected</c:if>>공지사항</option>
												<option value="P" <c:if test="${fn:trim(notice.notice_type) == 'P'}">selected</c:if>>신제품</option>
												<option value="G" <c:if test="${fn:trim(notice.notice_type) == 'G'}">selected</c:if>>상품권</option>
												<option value="L" <c:if test="${fn:trim(notice.notice_type) == 'L'}">selected</c:if>>대리점소식</option>
											</select>
											<c:if test="${flag == 'update'}">&nbsp;(구분은 수정이 불가 합니다)</c:if>
										</td>
										<th scope="row">기간</th>
										<td class="last">
											<div id="div_noticeDate" <c:if test="${fn:trim(notice.notice_type) != 'G'}">style="display: none;"</c:if>>
												<input type="text" class="ico_cal datepicker readonly" id="notice_from" name="notice_from" value="${notice.notice_from}"
													readonly/>
												 ~ <input type="text" class="ico_cal datepicker readonly"  id="notice_to" name="notice_to" value="${notice.notice_to}"
												 	readonly/>
											</div>
										</td>
									</tr>
									<tr>
										<th scope="row">제목</th>
										<td class="last" colspan="3">
											<input type="text" style="width:100%;" id="title" name="title" title="제목" value="${notice.title}" 
												maxlength="50" req/>
										</td>
									</tr>
									<tr>
										<th scope="row">내용</th>
										<td class="last" colspan="3">
											<textarea class="txtarea_input" id="contents" name="contents" maxlength="4000">${notice.contents}</textarea>
										</td>
									</tr>
									<tr>
										<th scope="row">파일첨부</th>
										<td class="last" colspan="3">
											<c:choose>
												<c:when test="${flag == 'update' and fn:length(fileList) > 0}">
													<c:forEach items="${fileList}" var="row" varStatus="status">
														<div class="filebox bs3-primary">
															<input class="upload-name" value="${row.original_nm}" disabled="disabled">
															<label for="old_${row.file_nm}">파일찾기</label>
															<p id="p_typeP" <c:if test="${fn:trim(notice.notice_type) != 'P'}">style="display:none;"</c:if>
																>신제품 소식 이미지 파일은 크기 160px * 185px로 업로드해주십시오.</p>
															<p id="p_typeL" <c:if test="${fn:trim(notice.notice_type) != 'L'}">style="display:none;"</c:if>
																>대리점 소식 이미지 파일은 크기 210px * 180px로 업로드해주십시오.</p>
															<input type="file" class="upload-hidden" id="old_${row.file_nm}" name="old_${row.file_nm}">
														</div>
													</c:forEach>
												</c:when>
												<c:otherwise>
													<div class="filebox bs3-primary">
														<input class="upload-name" value="파일을 추가해주십시오" disabled="disabled">
														<label for="new_1">파일찾기</label>
														<input type="file" class="upload-hidden" id="new_1" name="new_1">
														<p id="p_typeP" <c:if test="${fn:trim(notice.notice_type) != 'P'}">style="display:none;"</c:if>
															>신제품 소식 이미지 파일은 크기 160px * 185px로 업로드해주십시오.</p>
														<p id="p_typeL" <c:if test="${fn:trim(notice.notice_type) != 'L'}">style="display:none;"</c:if>
															>대리점 소식 이미지 파일은 크기 210px * 180px로 업로드해주십시오.</p>
													</div>
												</c:otherwise>
											</c:choose>
										</td>
									</tr>
									
									<tr id="tr_link" <c:if test="${fn:trim(notice.notice_type) != 'L'}">style="display:none;"</c:if>>
										<th scope="row">Link</th>
										<td class="last" colspan="3">
											<input type="text" style="width:100%;" id="link" name="link" value="${notice.link}" maxlength="200"/>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
						<div class="btn_center_wrap">
							<c:choose>
								<c:when test="${flag == 'insert'}">
									<input type="button" class="btn_big_order" value="저장" onclick="confirm();">
									<input type="button" class="btn_big_cancel" value="취소" onclick="returnList();">
								</c:when>
								<c:otherwise>
									<input type="button" class="btn_big_order" value="수정" onclick="confirm();">
									<input type="button" class="btn_big_cancel" value="취소" onclick="returnDetail();">
								</c:otherwise>
							</c:choose>
						</div>
					</div>
					<!--sub_cont-->

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