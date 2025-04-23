<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 사은품 신청 상세 -->
<!DOCTYPE html>
<html>

<head>
<jsp:directive.include file="/WEB-INF/views/common/common_include.jsp" />
<title>한진화학 주문관리 시스템</title>
</head>

<body>
<form id="frm" name="frm">
	<div class="wrap">
		<jsp:directive.include file="/WEB-INF/views/templates/header.jsp" />
		<div class="sub_wrap_area">
			<div class="sub_wrap">
				<div class="sub_contents">
					<div class="local_nav_wrap">
						<h3 class="sub_tit">사은품 신청</h3>
						<div class="local_nav">
							<ul>
								<li class="home">홈</li>
								<li>마이페이지</li>
								<li>사은품 신청</li>
							</ul>
						</div>
						<!--local_nav-->
					</div>
					<!--local_nav_wrap-->
					<div class="sub_cont">
						<div class="point_wrap">
							<div class="point_bg">
								<div class="order_point">
									주문 포인트
									<p class="point_price"><a href="#">201,000 P</a></p>
								</div>
								<div class="order_point">
									장려금
									<p class="point_price"><a href="#">851,000 원</a></p>
								</div>
								<div class="order_point" style="border:0;">
									화물탁송포인트
									<p class="point_price"><a href="#">140,000 P</a></p>
								</div>
							</div>
						</div>

						<div class="order_subtit">사은품 신청</div>
						<div class="tbl_wrap">
							<table class="tbl_basic" summary="사은품 신청 보기">
								<caption>사은품 신청</caption>
								<colgroup>
									<col style="width:10%" />
									<col style="width:23%" />
									<col style="width:10%" />
									<col style="width:23%">
									<col style="width:10%" />
									<col style="width:23%">
								</colgroup>
								<tbody>
									<tr class="first">
										<th scope="row">신청일자</th>
										<td>2017.01.01</td>
										<th scope="row">신청가능금액</th>
										<td>201,000 원</td>
										<th scope="row">사은품종류</th>
										<td class="last">선택1</td>
									</tr>
									<tr>
										<th scope="row">신청수량</th>
										<td>12 개</td>
										<th scope="row">신청금액</th>
										<td>120,000 원</td>
										<th scope="row">잔여금액</th>
										<td class="last">101,000 원</td>
									</tr>
									<tr>
										<th scope="row">지급확정일</th>
										<td>2017.01.01</td>
										<th scope="row">지급일</th>
										<td class="last" colspan="3">2017.01.11</td>
									</tr>
									<tr>
										<td colspan="6" class="tblin_comment">
											<p>1. 공지된 기간 중에 1회만 신청할 수 있습니다.</p>
											<p>2. 10만원이상 10만원 단위로 신청할 수 있습니다.</p>
											<p>3. 잔여금액은 다음에 사용할 수 있습니다.</p>
											<p>4. 지급이 확정되면 수정이나 삭제할 수 없습니다. </p>
										</td>
									</tr>

								</tbody>
							</table>
						</div>

						<div class="bottom_btn_wrap">
							<div class="right_btn_area">
								<input class="btn_save" type="button" id="" value="저장" onclick="" />
								<input class="btn_cancel" type="button" id="" value="취소" onclick="" />
								<input class="btn_modify" type="button" id="" value="수정" onclick="" />
								<input class="btn_ord_cancel" type="button" id="" value="신청 취소" onclick="" />
							</div>
						</div>
						<!--right_btn_area-->

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
</form>
</body>

</html>