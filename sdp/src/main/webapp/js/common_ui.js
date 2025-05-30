/*
 * XML 읽어오기
 */
function readingProperty() {
	var json;
	var text="";
	var url = document.URL.substring(document.URL.lastIndexOf("/") + 1, document.URL.length);
	
	$.getJSON("./property/guide.json", function(data) {
		json = data["guide_text"];
		
		//도움말을 추가 하고 싶은 url을 추가하고 guide.json 파일에 텍스트를 추가하면 자동으로 메뉴 우측에 도움말 아이콘을 생성함
//		var li_url = [
//			'sdpa001001l.standard.do'
//			, 'sdpa002001u.insert.do'
//			, 'sdpd001001l.do'
//			, 'sdpa002001l.do'
//		];
		
//		if(li_url.indexOf(url) >= 0) {
//			text = json[url];
//		}
		
		text = json[url];
		
		if(text != null && text != 'undefind' && text != '') {
			var add_html1 = '<img class="ico_question" alt="화면 도움말" src="img/ico_question.png" onclick="javascript:toggleGuideText(); return false;" title="클릭하시면 도움말이 표시됩니다">';
			
			var add_html2 = '';
			add_html2 += '<div class="guide_text" id="guide_text" name="guide_text" style="">';
			add_html2 += '<p>';
			add_html2 += text + '<br><input type="button" class="order_addlist" value="닫기" onclick="toggleGuideText();">';
			add_html2 += '</p>';
			add_html2 += '</div>';
			
			$(".sub_tit").append(add_html1)
			$(".local_nav_wrap").append(add_html2)
		}
	});
}

/*
 * 화면 도움말 보이기/숨기기
 */
var guideTextToggle = false;
function toggleGuideText() {
	if(!guideTextToggle) {
		$("div[name='guide_text']").slideDown();
		guideTextToggle = true;
	} else {
		$("div[name='guide_text']").slideUp();
		guideTextToggle = false;
	}
}

$(document).ready(function() {
	//커서삭제--
	$(document).on("focus", "input[class='readonly']", function(event) {
		$(event.target).blur();
	});
	
	//도움말 셋팅--
	readingProperty();
	//--도움말 셋팅

	//네비게이션 홈 클릭--
	$(".home").on("click", function() {
		c_href("main.do");
	});
	//--네비게이션 홈 클릭
	
	//모바일 체크--
	var mobilecheck = function() {
		var check = false;
		(function(a, b) {
			if (/(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|mobile.+firefox|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows ce|xda|xiino/i
					.test(a)
					|| /1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i
							.test(a.substr(0, 4)))
				check = true
		})(navigator.userAgent || navigator.vendor || window.opera);
		return check;
	}

	if (mobilecheck()) {
		//location = "https://biz.jevisco.com/sdm";		//모바일로 접속시 이동 경로
	}
	//--모바일 체크
	
	//퀵메뉴 체크
	if(sessionStorage.getItem("quick_toggle") == "hide") {
		$("#toggle input").val("<");
		$("#quickmenu #menu").hide();
//		$(".quickmenu").width(0);
	}

	//상단 검색확장 체크
	if(sessionStorage.getItem("search_toggle") == "show") {
		$("#hiddenList").show();
		$("#opencheckBtn").removeClass();
		$("#opencheckBtn").addClass("search_more_off");
	}
	
	//부모 체크박스 컨트롤--체크박스 선택 안된것이 0 이면 부모 체크박스 체크
	if(($(".search_check input[name='uf_salegroup1']:not(:checked)")).length == 0) {
		$("input[name='parentChkBox_search']").prop("checked", true);
	}
	
	$(document).on("click", "input[name='uf_salegroup1']", function(event) {
		if(($("input[name='uf_salegroup1']:checked")).length < ($("input[name='uf_salegroup1']")).length) {
			$("input[name='parentChkBox_search']").prop("checked", false);
		} 
		if(($("input[name='uf_salegroup1']:checked")).length == ($("input[name='uf_salegroup1']")).length) {
			$("input[name='parentChkBox_search']").prop("checked", true);
		}
	});
	
	$("#table_item").on("click", "input[name='chkBox']", function(event) {
		if(($("#table_item input[name='chkBox']:checked")).length < ($("#table_item input[name='chkBox']")).length) {
			$("input[name='parentChkBox']").prop("checked", false);
		} 
		if(($("#table_item input[name='chkBox']:checked")).length == ($("#table_item input[name='chkBox']")).length) {
			$("input[name='parentChkBox']").prop("checked", true);
		}
	});
	//--부모 체크박스 컨트롤
	
	//날짜태그 체크--
	var format_date = /^[0-9, .]+$/;
	var format_number = /^[0-9]+$/;
	$("body").on("keyup change", ".datepicker, .datepicker_aftToday", function(event) {
		//숫자와 -만 입력가능하게 이외의 문자는 replace 로 바로 치환해 버림
		if($(event.target).val().length > 0) {
			if (!format_date.test($(event.target).val())) {
				$(event.target).val(($(event.target).val()).replace(/[^0-9,.]/gi, ""));
			}
		}
		
		if($(event.target).val().length > 8) {
			$(event.target).val(($(event.target).val()).substring(0, 10));
		}
		
		if($(event.target).val().length > 9) {
			//유효성 검사
			if(!validate_date($(event.target).val())) {
				c_alert("날짜 형식이 잘못되었습니다.");
				$(event.target).val("");
			}
			
			if($(event.target).attr("name") == "searchDate_from"
				|| $(event.target).attr("name") == "searchDate_to") {
				
				var from = Number($("input[name='searchDate_from']").val().replace(/./gi, ""));
				var to = Number($("input[name='searchDate_to']").val().replace(/./gi, ""));
				
				if(from > to && from != 0 && to != 0) {
					if($(event.target).attr("name") == "searchDate_from") {
						$("input[name='searchDate_to']").val($(event.target).val());
					} else {
						$("input[name='searchDate_from']").val($(event.target).val());
					}
				}
			}
		}
	});
	$("body").on("focusout", ".datepicker, .datepicker_aftToday", function(event) {
		if($(event.target).val().length < 10 && $(event.target).val().length > 0) {
			c_alert("날짜는 다음 형식으로 입력해주십시오.<br>예시 : 2017.01.01");
			$(event.target).val("");
		}
	});
	//--날짜태그 체크
	
	//전화번호 체크--
	$("body").on("keyup change", "input[name='phone'], input[class='phone'], input[name='cust_phone']", function(event) {
		if($(event.target).val().length > 0) {
			if (!format_number.test($(event.target).val())) {
				$(event.target).val(($(event.target).val()).replace(/[^0-9]/gi, ""));
			}
		}
		if($(event.target).val().length >= 11) {
			$(event.target).val(($(event.target).val()).substring(0, 11));
		}
	});
	//--전화번호 체크
	
	//수량필드 체크--
	$("body").on("keyup change", ".entry", function(event) {
		if($(event.target).val().length >= 4) {
			$(event.target).val(($(event.target).val()).substring(0, 4));
		}
		
		validate_number(event.target);
		
		if($(event.target).val() == "") {
			$(event.target).val("0");
		}
		
//		$(event.target).val(Number($(event.target).val()));
	});
	$("body").on("blur", ".entry", function(event) {
		if($(event.target).val().trim().length <= 0) {
			$(event.target).val("0");
		}
	});
	
	//소수
	$("body").on("keyup change", ".entry_f", function(event) {
		if($(event.target).val().length >= 5) {
			$(event.target).val(($(event.target).val()).substring(0, 5));
		}
		
		validate_float(event.target);
		
		if($(event.target).val() == "") {
			$(event.target).val("0");
		}
		
//		$(event.target).val(Number($(event.target).val()));
	});
	$("body").on("blur", ".entry_f", function(event) {
		if($(event.target).val().trim().length <= 0) {
			$(event.target).val("0");
		}
	});
	//--수량필드 체크
	
	//수량필드 클릭시 전체선택--
	$("body").on("click", ".entry", function(event) {
		$(event.target).select();
	});
	//--수량필드 클릭시 전체선택
	
	//달력 셋팅--
	$(".datepicker").datepicker({		//전체 날짜 선택
		changeMonth: true,
		changeYear: true,
		showMonthAfterYear: true,
		dateFormat: "yy.mm.dd",
		prevText: '이전 달',
		nextText: '다음 달',
		monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
		monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
		dayNames: ['일', '월', '화', '수', '목', '금', '토'],
		dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
		dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
		yearSuffix: '년'
	});
	
	//16시가 넘으면 배달요청일 최소 값이 다음날로 넘어감
	var minDt  = 0;
	var nowDt = new Date();
	var defaultDt;

	if(nowDt.getHours() >= 16) {
		minDt = 1;
	}
	$(".datepicker_aftToday").datepicker({		//오늘 이후
		minDate: minDt,
		setDate: nowDt,
		changeMonth: true,
		changeYear: true,
		showMonthAfterYear: true,
		dateFormat: "yy.mm.dd",
		prevText: '이전 달',
		nextText: '다음 달',
		monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
		monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
		dayNames: ['일', '월', '화', '수', '목', '금', '토'],
		dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
		dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
		yearSuffix: '년'
	});
	//--달력 셋팅
	
	//상단 검색 토글 활성화 펑션--
	$("#opencheckBtn").click(function() {
		if ($("#hiddenList").is(":visible")) {
			$("#hiddenList").slideUp();
			$("#opencheckBtn").removeClass();
			$("#opencheckBtn").addClass("search_more_on");
			sessionStorage.setItem("search_toggle", "hide");
		} else {
			$("#hiddenList").slideDown();
			$("#opencheckBtn").removeClass();
			$("#opencheckBtn").addClass("search_more_off");
			sessionStorage.setItem("search_toggle", "show");
		}
	});
	//--상단 검색 토글 활성화 펑션
	
	// family site
	var familyCloseH = $(".family_site").height();
	var familyOpenH = $(".family_site > ul").height()
			+ familyCloseH
			+ (parseInt($(".family_site > ul").css(
					"padding-top")) * 2);
	$(".family_site > a").on("click", function(e) {
		e.preventDefault();

		if ($(this).parent().hasClass("on")) {
			$(this).parent().removeClass("on");
			$(this).parent().stop().animate({
				height : familyCloseH + "px"
			}, 500);
		} else {
			$(this).parent().addClass("on");
			$(this).parent().stop().animate({
				height : familyOpenH + "px"
			}, 500);
		}
		;
	});
	
	// local menu
	$('.local_nav li').hover(function() {
		$('ul', this).stop().slideDown(200);
	}, function() {
		$('ul', this).stop().slideUp(200);
	});
	
	
	$(".wish_warp select").change(function() {
		$(".wish_warp label").html($(".wish_warp select option:selected").html());
	});

	// quick menu scroll
	$('.quickmenu .movetop').click(function(e){
		  e.preventDefault();
		  $('body, html').animate({ scrollTop: 0 }, 200);
		});
		$('.quickmenu .movedown').click(function(e){
		  e.preventDefault();
		  $('body, html').animate({ scrollTop: $(document).height() - $(window).height() }, 200);
		});
});

var siteMapSlickPlay = true;

$(document).ready(function(){
	$(window).on("resize load scroll", submenuFixed);

	//메뉴
	if ($(".gnb").length > 0) {

		$("body").on("mouseover focus",".gnb>li>a",function(e){
			$(".gnbbg").stop(true,true).slideDown(300);
			$(".gnb>li>.item").stop(true,true).slideDown(300);

			$(".gnb").find(".underline").each(function(){
				if (!$(this).hasClass("on")) {
					$(this).hide();
				};
			});

			$btn = $(this);
			if ($btn.parent().find(".underline").length > 0) {
				$btn.parent().find(".underline").each(function(){
					$(this).hasClass("on");
				});
				$btn.parent().find(".underline").show();
			};

			$(".btnSearchOpen").removeClass("on");
			$(".btnSearchOpen").text("검색창열기");
			$(".searchbox").stop(true,true).slideUp(200);
		});

		$("body").on("mouseleave",".gnb",function(e){			
			e.stopPropagation();
			$(".gnbbg").stop(true,true).slideUp(200);
			$(".gnb>li>.item").stop(true,true).slideUp(200);

			$(".gnb").find(".underline").each(function(){
				if (!$(this).hasClass("on")) {
					$(this).hide();
				};
			});
		});

		$("body").on("blur",".gnb>li:last>.item li:last>a",function(e){
			$(".gnbbg").stop(true,true).slideUp(200);
			$(".gnb>li>.item").stop(true,true).slideUp(200);
			$(".btnSearchOpen").focus();
		});
		$("body").on("focus",".header h1 a",function(e){
			$(".gnbbg").stop(true,true).slideUp(200);
			$(".gnb>li>.item").stop(true,true).slideUp(200);
		});
	}

	$(".btnTotalOpen").click(function(){
		try { mainBannerStop = true; } catch(e){}
		//모달창을 띄웠을때는 회사소개를 최초 포커싱하도록 한다.
		$("#mw_temp").show(0, function(){
			//$('.btnSiteMapClose').focus();
			$('.gnb01 > dt > a').focus();
		});

		try {

			if ($(".fg_con .sitemapSlide").length > 0 ) {
				$('.fg_con .sitemapSlide').slick({
					dots: false,
					autoplay:false,
					autoplaySpeed: 3000,
					speed:500,
					infinite:true,
					swipe:false,
					initialSlide:0,
					slidesToShow: 1
				});

				if ($(".fg_con .sitemapSlide").find(".item").length > 1) {
					$('.fg_con .sitemapSlide').slick('slickPlay');
				} else {
					$('.fg_con .sitemapSlide').slick('slickPause');
					$(".fg_con .pagePlay").addClass("on");
					siteMapSlickPlay = false;
				}
				//이미지가 1개이하인경우 버튼 숨김
				if ($(".fg_con .sitemapSlide").find(".item").length<=1) {
					$(".fg_con .sitemapSlidePager").hide();
				}

				$('.fg_con .sitemapSlide').on('afterChange', function(event, slick, currentSlide, nextSlide){
					$(".fg_con .pageItem").removeClass("on");
					$(".fg_con .pageItem:eq("+currentSlide+")").addClass("on");
				});
				$(".fg_con .pagePlay").bind("click",function(e){
					if (siteMapSlickPlay) {
						$('.fg_con .sitemapSlide').slick('slickPause');
						$(this).addClass("on");
						siteMapSlickPlay = false;
						$(".fg_con .pagePlay").html("재생");
						$(".fg_con .pagePlay").attr("title", "재생");
					} else {
						$('.fg_con .sitemapSlide').slick('slickPlay');
						$(this).removeClass("on");
						siteMapSlickPlay = true;
						$(".fg_con .pagePlay").html("정지");
						$(".fg_con .pagePlay").attr("title", "정지");
					}
				});
				$(".fg_con .pageItem").bind("click",function(e){
					$('.fg_con .sitemapSlide').slick('slickPause');
					$(".fg_con .pagePlay").addClass("on");

					siteMapSlickPlay = false;

					var inx = $(".pageItem").index( this );
					$('.fg_con .sitemapSlide').slick('slickGoTo', inx);
					$(".fg_con .pageItem").removeClass("on");
					$(".fg_con .pageItem:eq("+inx+")").addClass("on");
				});

				$(".fg_con .pageItem").focus(function() {
					$('.fg_con .sitemapSlide').slick('slickPause');
					$(".fg_con .pagePlay").addClass("on");
					siteMapSlickPlay = false;

					var inxf = $(".pageItem").index( this );
					$('.fg_con .sitemapSlide').slick('slickGoTo', inxf);
					$(".fg_con .pageItem").removeClass("on");
					$(".fg_con .pageItem:eq("+inxf+")").addClass("on");
				});
				$(".fg_con .pageItem:eq(0)").keydown(function(event){
					var v_keyCode = event.keyCode || event.which;
					if(v_keyCode == 9){
						if(event.shiftKey){
							// Shift + Tab 이벤트
						}else{
							// Tab 이벤트 - 닫기로 이동
							$(".fg_con .sitemapSlide .item a:eq(1)").focus();
							return false;
						}
					}
				});
				$(".fg_con .sitemapSlide .item a:eq(1)").keydown(function(event){
					var v_keyCode = event.keyCode || event.which;
					if(v_keyCode == 9){
						if(event.shiftKey){
							// Shift + Tab 이벤트
						}else{
							// Tab 이벤트 - 닫기로 이동
							$(".fg_con .pageItem:eq(1)").focus();
							return false;
						}
					}
				});
				$(".fg_con .pageItem:eq(1)").keydown(function(event){
					var v_keyCode = event.keyCode || event.which;
					if(v_keyCode == 9){
						if(event.shiftKey){
							// Shift + Tab 이벤트
						}else{
							// Tab 이벤트 - 닫기로 이동
							$(".fg_con .sitemapSlide .item a:eq(2)").focus();
							return false;
						}
					}
				});
				$(".fg_con .sitemapSlide .item a:eq(2)").keydown(function(event){
					var v_keyCode = event.keyCode || event.which;
					if(v_keyCode == 9){
						if(event.shiftKey){
							// Shift + Tab 이벤트
						}else{
							// Tab 이벤트 - 닫기로 이동
							$(".fg_con .pageItem:eq(2)").focus();
							return false;
						}
					}
				});
				$(".fg_con .pageItem:eq(2)").keydown(function(event){
					var v_keyCode = event.keyCode || event.which;
					if(v_keyCode == 9){
						if(event.shiftKey){
							// Shift + Tab 이벤트
						}else{
							// Tab 이벤트 - 닫기로 이동
							$(".fg_con .sitemapSlide .item a:eq(3)").focus();
							return false;
						}
					}
				});
				$(".fg_con .sitemapSlide .item a:eq(3)").keydown(function(event){
					var v_keyCode = event.keyCode || event.which;
					if(v_keyCode == 9){
						if(event.shiftKey){
							// Shift + Tab 이벤트
						}else{
							// Tab 이벤트 - 닫기로 이동
							$(".fg_con .pagePlay").focus();
							return false;
						}
					}
				});
			}
		}catch(e){
		}
		return false;
	});

	$(".btnSiteMapClose").click(function(){
		try { mainBannerStop = false; } catch(e){}

		$("#mw_temp").hide(0, function(){
			$(".btnTotalOpen").focus();
		});

		if ($(".fg_con .sitemapSlide").length > 0 ) {
			$('.fg_con .sitemapSlide').slick('unslick');

			$(".fg_con .pageItem").removeClass("on");
			$(".fg_con .pageItem:eq(0)").addClass("on");
			$(".fg_con .pagePlay").removeClass("on");
		}
		return false;
	});

	$("body").on("focus",".header .rela .gnb>li:last a",function(e){
		$(".btnSearchOpen").removeClass("on");
		$(".btnSearchOpen").text("검색창열기");
		$(".searchbox").stop(true,true).slideUp(200);
	});
	$("body").on("focus",".btnLang",function(e){
		$(".btnSearchOpen").removeClass("on");
		$(".searchbox").stop(true,true).slideUp(200);
	});
	$("body").on("focus",".btnSearchOpen",function(e){
		$(".subArea").stop().slideUp(0);
	});
});


// input file
$(document).ready(function() {
	var fileTarget = $('.filebox .upload-hidden');

	fileTarget.on('change', function() {
		if (window.FileReader) {
			var filename = $(this)[0].files[0].name;
		} else {
			var filename = $(this).val().split('/').pop().split('\\').pop();
		}

		$(this).siblings('.upload-name').val(filename);
	});
}); 


function submenuFixed(){
	var ck = $(window).scrollTop();
	if(ck > 70){
		$(".subTreeMenu").css({"position":"fixed", "left":"0", "top":"0", "z-index":"99"});
		
	}else{
		$(".subTreeMenu").css({"position":"static"});
	}
	
	if ($(".wrapper").height() < $(window).height()) {
		var contentHeight = $(window).height()-370;
		$(".subContent").css({"minHeight":contentHeight+"px"});
	}
	
	$("#mCSB_1").removeAttr("tabindex");
}

//퀵메뉴 토글 버튼
function toggleQuickMenu(obj) {
	if($(obj).val() == ">") {
		$(obj).val("<");
		$("#quickmenu #menu").animate({width:'toggle'},0);
        sessionStorage.setItem("quick_toggle", "hide");
	} else {
		$(obj).val(">");
		$("#quickmenu #menu").animate({width:'toggle'},0);
		sessionStorage.setItem("quick_toggle", "show");
	}
}