<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/views/common/taglib.jsp" />

<!-- 공지사항 목록  -->
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
		         				<div class="notice_search_wrap">
		         					<select style="width:70px;">
		         						<option>제목</option>
		         						<option>내용</option>
		         					</select>
		         					<input type="text"/>
		         					<input type="button" class="btn_notice_search" value="검색" />
		         				</div>       		
		         				<div class="notice_wrap_tit">
									<table class="table_notice" summary="공지사항">
										<caption>공지사항</caption>
										<colgroup>
											<col style="width: 60px;" />
											<col style="width: 100px;" />
											<col style="width: 650px;" />							
											<col style="width: 200px;" />																
											<col style="" />
													
										</colgroup>
										<thead>
											<tr>											
												<th scope="col">번호</th>
												<th scope="col">구분</th>																
												<th scope="col">제목</th>												
												<th scope="col">기간</th>												
												<th scope="col">조회</th>
											</tr>
										</thead>
									</table>
								</div>
				
								<div class="notice_wrap">
									<table class="table_notice" summary="공지사항">
										<caption>공지사항</caption>
										<colgroup>
											<col style="width: 60px;" /> <!-- 번호 -->
											<col style="width: 100px;" /> <!-- 구분 -->
											<col style="width: 650px;" /> <!-- 제목 -->													
											<col style="width: 200px" /> <!-- 기간 -->																															
											<col style="" /><!-- 조회 -->							
										</colgroup>
										<tbody>
												<tr>													
													<td>123</td>	
													<td>공지사항</td>									
													<td class="subject">공지사항입니다. 리스트 여기 뿌려집니다. 공지사항 공지사항 공지사항 첨부</td>																															
													<td>2017.01.01 ~ 2017.01.10</td>																						
													<td>210</td>	
												</tr>																												
												<tr>													
													<td>123</td>	
													<td>공지사항</td>									
													<td class="subject">공지사항입니다. 리스트 여기 뿌려집니다. 공지사항 공지사항 공지사항 첨부</td>																															
													<td>2017.01.01 ~ 2017.01.10</td>																						
													<td>210</td>	
												</tr>	
												<tr>													
													<td>123</td>	
													<td>공지사항</td>									
													<td class="subject">공지사항입니다. 리스트 여기 뿌려집니다. 공지사항 공지사항 공지사항 첨부</td>																															
													<td>2017.01.01 ~ 2017.01.10</td>																						
													<td>210</td>	
												</tr>	
												<tr>													
													<td>123</td>	
													<td>공지사항</td>									
													<td class="subject">공지사항입니다. 리스트 여기 뿌려집니다. 공지사항 공지사항 공지사항 첨부</td>																															
													<td>2017.01.01 ~ 2017.01.10</td>																						
													<td>210</td>	
												</tr>	
												<tr>													
													<td>123</td>	
													<td>공지사항</td>									
													<td class="subject">공지사항입니다. 리스트 여기 뿌려집니다. 공지사항 공지사항 공지사항 첨부</td>																															
													<td>2017.01.01 ~ 2017.01.10</td>																						
													<td>210</td>	
												</tr>	
												<tr>													
													<td>123</td>	
													<td>공지사항</td>									
													<td class="subject">공지사항입니다. 리스트 여기 뿌려집니다. 공지사항 공지사항 공지사항 첨부</td>																															
													<td>2017.01.01 ~ 2017.01.10</td>																						
													<td>210</td>	
												</tr>	
												<tr>													
													<td>123</td>	
													<td>공지사항</td>									
													<td class="subject">공지사항입니다. 리스트 여기 뿌려집니다. 공지사항 공지사항 공지사항 첨부</td>																															
													<td>2017.01.01 ~ 2017.01.10</td>																						
													<td>210</td>	
												</tr>	
												<tr>													
													<td>123</td>	
													<td>공지사항</td>									
													<td class="subject">공지사항입니다. 리스트 여기 뿌려집니다. 공지사항 공지사항 공지사항 첨부</td>																															
													<td>2017.01.01 ~ 2017.01.10</td>																						
													<td>210</td>	
												</tr>	
												<tr>													
													<td>123</td>	
													<td>공지사항</td>									
													<td class="subject">공지사항입니다. 리스트 여기 뿌려집니다. 공지사항 공지사항 공지사항 첨부</td>																															
													<td>2017.01.01 ~ 2017.01.10</td>																						
													<td>210</td>	
												</tr>	
												<tr>													
													<td>123</td>	
													<td>공지사항</td>									
													<td class="subject">공지사항입니다. 리스트 여기 뿌려집니다. 공지사항 공지사항 공지사항 첨부</td>																															
													<td>2017.01.01 ~ 2017.01.10</td>																						
													<td>210</td>	
												</tr>												
										</tbody>
									</table>	
								</div>
									
								<div class="pagingWrap">										
									<span class="btn">
										<a href="#prev" class="prev">이전 페이지로 이동</a>
									</span>										
										<span class="num">
											<a href="#" class="on">1</a>
												<a href="#">2</a>
												<a href="#">3</a>
												<a href="#">4</a>
												<a href="#">5</a>
												<a href="#">6</a>
												<a href="#">7</a>
												<a href="#">8</a>
												<a href="#">9</a>
												<a href="#">10</a>
										</span>												
									<span class="btn">
										<a href="#" class="next">다음 페이지로 이동</a>
									</span>	
								</div>
								<!-- // pagingWrap -->
								
								<!--여러개 등록시 활성화 div class="bottom_btn_wrap">					
									<div class="right_btn_area">
										<input class="btn_add" type="button" id="" value="품목 추가" onclick="" />
										<input class="btn_del2" type="button" id="" value="품목 삭제" onclick="" />
									</div>
								</div -->
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