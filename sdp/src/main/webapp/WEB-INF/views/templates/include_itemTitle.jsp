<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:choose>
	<c:when test="${sessionScope.user['workplace'] == '1'}"><!-- 본사 -->
		<th class="tbl_row_tit" style="border-right:1px solid #606c79;">본사</th>
		<th class="tbl_row_tit" style="border-right:1px solid #606c79;">대구</th>
		<th class="tbl_row_tit" style="border-right:1px solid #606c79;">전체</th>
	</c:when>
	<c:when test="${sessionScope.user['workplace'] == '2'}"><!-- 부산 -->
		<th class="tbl_row_tit" style="border-right:1px solid #606c79;">본사</th>
		<th class="tbl_row_tit" style="border-right:1px solid #606c79;">대구</th>
		<th class="tbl_row_tit" style="border-right:1px solid #606c79;">전체</th>
	</c:when>
	<c:when test="${sessionScope.user['workplace'] == '3'}"><!-- 대구 -->
		<th class="tbl_row_tit" style="border-right:1px solid #606c79;">본사</th>
		<th class="tbl_row_tit" style="border-right:1px solid #606c79;">대구</th>
		<th class="tbl_row_tit" style="border-right:1px solid #606c79;">전체</th>
	</c:when>

	<c:otherwise><!-- 기타 -->
		<th class="tbl_row_tit" style="border-right:1px solid #606c79;">본사</th>
		<th class="tbl_row_tit" style="border-right:1px solid #606c79;">대구-</th>
		<th class="tbl_row_tit" style="border-right:1px solid #606c79;">전체</th>
	</c:otherwise>
</c:choose>
<!-- <c:if test="${flag != 'detail'}">
	<th class="tbl_row_tit">보관품</th>
</c:if> -->