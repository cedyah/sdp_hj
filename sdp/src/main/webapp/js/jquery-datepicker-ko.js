// 2015.06.05 한글판 달력
jQuery(function($){
	$.datepicker.regional['ko'] = {
		closeText: "닫기", // Display text for close link
		prevText: "◀", // Display text for previous month link
		nextText: "▶", // Display text for next month link
		currentText: "오늘", // Display text for current month link
		monthNames: ["1월","2월","3월","4월","5월","6월",
			"7월","8월","9월","10월","11월","12월"], // Names of months for drop-down and formatting
		monthNamesShort: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"], // For formatting
		dayNames: ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"], // For formatting
		dayNamesShort: ["일", "월", "화", "수", "목", "금", "토"], // For formatting
		dayNamesMin: ["일","월","화","수","목","금","토"], // Column headings for days starting at Sunday
		weekHeader: "Wk", // Column header for week of the year
		dateFormat: "yy-mm-dd", // See format options on parseDate
		firstDay: 0, // The first day of the week, Sun = 0, Mon = 1, ...
		isRTL: false, // True if right-to-left language, false if left-to-right
		showMonthAfterYear: true, // True if the year select precedes month, false for month then year
		yearSuffix: "년", // Additional text to append to the year in the month headers
		changeMonth: true, // True if month can be selected directly, false if only prev/next
		changeYear: true,
		showButtonPanel: true,
		showOtherMonths: true,
		beforeShowDay : function(day){
			var result;
			
			var solHoliday = solHolidays[$.datepicker.formatDate("mmdd", day)];
			var lunaHoliday = lunaCalc($.datepicker.formatDate("yy", day), $.datepicker.formatDate("mm", day), $.datepicker.formatDate("dd", day));
			var thisYear = $.datepicker.formatDate("yy", day);
			
			// 양력 휴일.
			if(solHoliday){
				if(solHoliday.year == ""){
					result = [true, "holiday", solHoliday.title];
				}
			}
			
			// 음력 휴일.
			if(lunaHoliday){
				result = [true, "holiday", lunaHoliday];
			}
			// 음력 휴일 추가해야함.
			
			if(!result){
				switch(day.getDay()){
				case 0 :
					result = [true, "sunday"];
					break;
				case 6 :
					result = [true, "saturday"];
					break;
				default :
					result = [true, ""];
					break;
				}
			}
			return result;
		}
	};
	$.datepicker.setDefaults($.datepicker.regional['ko']);
});

// 양력 휴일
var solHolidays = {
		"0101":{type:0, title:"신정", year:""},
		"0301":{type:0, title:"삼일절", year:""},
		"0505":{type:0, title:"어린이날", year:""},
		"0606":{type:0, title:"현충일", year:""},
		"0815":{type:0, title:"광복절", year:""},
		"1003":{type:0, title:"개천절", year:""},
		"1009":{type:0, title:"한글날", year:""},
		"1225":{type:0, title:"크리스마스", year:""}
};

// 음력 휴일.
var lunaHolidays = {
		"1230":{type:0, title:"설날", year:""},
		"0101":{type:0, title:"설날", year:""},
		"0102":{type:0, title:"설날", year:""},
		"0408":{type:0, title:"석가탄신일", year:""},
		"0814":{type:0, title:"추석", year:""},
		"0815":{type:0, title:"추석", year:""},
		"0816":{type:0, title:"추석", year:""}
};

//음력 데이터. (평달 - 작은달 (29일까지): 1, 큰달(30일까지) : 2) * 음력은 29, 30일만 있음.
// * 윤달이 있는 경우
//   - 평달이 작고 윤달도 작으면 : 3, 평달이 작고 윤달이 크면 : 4
//   - 평달이 크고 윤달이 작으면 : 5, 평달이 크고 윤달도 크면 : 6
// 기간 : 1900 ~ 2101
var lunarMonthTable = [
	[2, 1, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2],
	[1, 2, 1, 1, 2, 1, 2, 5, 2, 2, 1, 2],
	[1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 1],   /* 1901 */
	[2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2],
	[1, 2, 1, 2, 3, 2, 1, 1, 2, 2, 1, 2],
	[2, 2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 1],
	[2, 2, 1, 2, 2, 1, 1, 2, 1, 2, 1, 2],
	[1, 2, 2, 4, 1, 2, 1, 2, 1, 2, 1, 2],
	[1, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1],
	[2, 1, 1, 2, 2, 1, 2, 1, 2, 2, 1, 2],
	[1, 5, 1, 2, 1, 2, 1, 2, 2, 2, 1, 2],
	[1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 1],
	[2, 1, 2, 1, 1, 5, 1, 2, 2, 1, 2, 2],   /* 1911 */
	[2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 2],
	[2, 2, 1, 2, 1, 1, 2, 1, 1, 2, 1, 2],
	[2, 2, 1, 2, 5, 1, 2, 1, 2, 1, 1, 2],
	[2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2],
	[1, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1],
	[2, 3, 2, 1, 2, 2, 1, 2, 2, 1, 2, 1],
	[2, 1, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2],
	[1, 2, 1, 1, 2, 1, 5, 2, 1, 2, 2, 2],
	[1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 2, 2],
	[2, 1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2],   /* 1921 */
	[2, 1, 2, 2, 3, 2, 1, 1, 2, 1, 2, 2],
	[1, 2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2],
	[2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1, 1],
	[2, 1, 2, 5, 2, 1, 2, 2, 1, 2, 1, 2],
	[1, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 1],
	[2, 1, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2],
	[1, 5, 1, 2, 1, 1, 2, 2, 1, 2, 2, 2],
	[1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 2],
	[1, 2, 2, 1, 1, 5, 1, 2, 1, 2, 2, 1],
	[2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1],   /* 1931 */
	[2, 2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2],
	[1, 2, 2, 1, 6, 1, 2, 1, 2, 1, 1, 2],
	[1, 2, 1, 2, 2, 1, 2, 1, 2, 2, 1, 2],
	[1, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 1],
	[2, 1, 4, 1, 1, 2, 2, 1, 2, 2, 2, 1],
	[2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 2, 1],
	[2, 2, 1, 1, 2, 1, 4, 1, 2, 2, 1, 2],
	[2, 2, 1, 1, 2, 1, 1, 2, 1, 2, 1, 2],
	[2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1],
	[2, 2, 1, 2, 2, 4, 1, 1, 2, 1, 2, 1],   /* 1941 */
	[2, 1, 2, 2, 1, 2, 2, 1, 1, 2, 1, 2],
	[1, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1],
	[2, 1, 2, 4, 1, 2, 1, 2, 2, 1, 2, 2],
	[1, 1, 2, 1, 1, 2, 1, 2, 2, 2, 1, 2],
	[2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 1, 2],
	[2, 5, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2],
	[2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2],
	[2, 1, 2, 2, 1, 2, 3, 2, 1, 2, 1, 2],
	[1, 2, 2, 2, 1, 2, 1, 1, 2, 1, 2, 1],
	[2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2],   /* 1951 */
	[1, 2, 1, 2, 4, 1, 2, 2, 1, 2, 1, 2],
	[1, 2, 1, 1, 2, 2, 1, 2, 2, 1, 2, 2],
	[1, 1, 2, 1, 1, 2, 1, 2, 2, 1, 2, 2],
	[2, 1, 4, 1, 1, 2, 1, 2, 1, 2, 2, 2],
	[1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2],
	[2, 1, 2, 1, 2, 1, 1, 5, 2, 1, 2, 2],
	[1, 2, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2],
	[1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1],
	[2, 1, 2, 1, 2, 5, 2, 1, 2, 1, 2, 1],
	[2, 1, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2],   /* 1961 */
	[1, 2, 1, 1, 2, 1, 2, 2, 1, 2, 2, 1],
	[2, 1, 2, 3, 2, 1, 2, 1, 2, 2, 2, 1],
	[2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2],
	[1, 2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 2],
	[1, 2, 5, 2, 1, 1, 2, 1, 1, 2, 2, 1],
	[2, 2, 1, 2, 2, 1, 1, 2, 1, 2, 1, 2],
	[1, 2, 1, 2, 2, 1, 5, 2, 1, 2, 1, 2],
	[1, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1],
	[2, 1, 1, 2, 1, 2, 2, 1, 2, 2, 1, 2],
	[1, 2, 1, 1, 5, 2, 1, 2, 2, 2, 1, 2],   /* 1971 */
	[1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 1],
	[2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 2],
	[2, 2, 1, 5, 1, 2, 1, 1, 2, 2, 1, 2],
	[2, 2, 1, 2, 1, 1, 2, 1, 1, 2, 1, 2],
	[2, 2, 1, 2, 1, 2, 1, 5, 1, 2, 1, 2],
	[2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 1],
	[2, 1, 2, 2, 1, 2, 2, 1, 2, 1, 2, 1],
	[2, 1, 1, 2, 1, 6, 1, 2, 2, 1, 2, 1],
	[2, 1, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2],
	[1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 2, 2],   /* 1981 */
	[2, 1, 2, 3, 2, 1, 1, 2, 1, 2, 2, 2],
	[2, 1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2],
	[2, 1, 2, 2, 1, 1, 2, 1, 1, 5, 2, 2],
	[1, 2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2],
	[1, 2, 2, 1, 2, 2, 1, 2, 1, 2, 1, 1],
	[2, 1, 2, 1, 2, 5, 2, 2, 1, 2, 1, 2],
	[1, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 1],
	[2, 1, 1, 2, 1, 2, 1, 2, 1, 2, 2, 2],
	[1, 2, 1, 1, 5, 1, 2, 2, 1, 2, 2, 2],
	[1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 2],   /* 1991 */
	[1, 2, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2],
	[1, 2, 5, 2, 1, 2, 1, 1, 2, 1, 2, 1],
	[2, 2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2],
	[1, 2, 2, 1, 2, 1, 2, 5, 2, 1, 1, 2],
	[1, 2, 1, 2, 2, 1, 2, 1, 2, 2, 1, 1],
	[2, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 1],
	[2, 1, 1, 2, 3, 2, 2, 1, 2, 2, 2, 1],
	[2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 2, 1],
	[2, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 1],
	[2, 2, 1, 5, 2, 1, 1, 2, 1, 2, 1, 2],   /* 2001 */
	[2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1],
	[2, 2, 1, 2, 2, 1, 2, 1, 1, 2, 1, 2],
	[1, 5, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2],
	[1, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1],
	[2, 1, 2, 1, 2, 1, 5, 2, 2, 1, 2, 2],
	[1, 1, 2, 1, 1, 2, 1, 2, 2, 2, 1, 2],
	[2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 1, 2],
	[2, 2, 1, 1, 5, 1, 2, 1, 2, 1, 2, 2],
	[2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2],
	[2, 1, 2, 2, 1, 2, 1, 1, 2, 1, 2, 1],   /* 2011 */
	[2, 1, 2, 5, 2, 2, 1, 1, 2, 1, 2, 1],
	[2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2],
	[1, 2, 1, 2, 1, 2, 1, 2, 5, 2, 1, 2],
	[1, 2, 1, 1, 2, 1, 2, 2, 2, 1, 2, 1],
	[2, 1, 2, 1, 1, 2, 1, 2, 2, 1, 2, 2],
	[1, 2, 1, 2, 1, 4, 1, 2, 1, 2, 2, 2],
	[1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2],
	[2, 1, 2, 1, 2, 1, 1, 2, 1, 1, 2, 2],
	[2, 1, 2, 5, 2, 1, 1, 2, 1, 2, 1, 2],
	[1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1],   /* 2021 */
	[2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1, 2],
	[1, 5, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2],
	[1, 2, 1, 1, 2, 1, 2, 2, 1, 2, 2, 1],
	[2, 1, 2, 1, 1, 5, 2, 1, 2, 2, 2, 1],
	[2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2],
	[1, 2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 1],
	[2, 2, 2, 1, 5, 1, 2, 1, 1, 2, 2, 1],
	[2, 2, 1, 2, 2, 1, 1, 2, 1, 1, 2, 2],
	[1, 2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1],
	[2, 1, 5, 2, 1, 2, 2, 1, 2, 1, 2, 1],   /* 2031 */
	[2, 1, 1, 2, 1, 2, 2, 1, 2, 2, 1, 2],
	[1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 5, 2],
	[1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 1, 2],
	[2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 2],
	[2, 2, 1, 2, 1, 4, 1, 1, 2, 2, 1, 2],
	[2, 2, 1, 2, 1, 1, 2, 1, 1, 2, 1, 2],
	[2, 2, 1, 2, 1, 2, 1, 2, 1, 1, 2, 1],
	[2, 2, 1, 2, 5, 2, 1, 2, 1, 2, 1, 1],
	[2, 1, 2, 2, 1, 2, 1, 2, 2, 1, 2, 1],
	[2, 1, 1, 2, 1, 2, 2, 1, 2, 2, 1, 2],   /* 2041 */
	[1, 5, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2],
	[1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 2, 2],
	[2, 1, 2, 1, 1, 2, 3, 2, 1, 2, 2, 2],
	[2, 1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2],
	[2, 1, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2],
	[2, 1, 2, 2, 4, 1, 2, 1, 1, 2, 1, 2],
	[1, 2, 2, 1, 2, 2, 1, 2, 1, 1, 2, 1],
	[2, 1, 2, 1, 2, 2, 1, 2, 2, 1, 2, 1],
	[1, 2, 4, 1, 2, 1, 2, 2, 1, 2, 2, 1], 
	[2, 1, 1, 2, 1, 1, 2, 2, 1, 2, 2, 2],   /* 2051 */
	[1, 2, 1, 1, 2, 1, 1, 5, 2, 2, 2, 2],
	[1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 2],
	[1, 2, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2],
	[1, 2, 2, 1, 2, 4, 1, 1, 2, 1, 2, 1],
	[2, 2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2],
	[1, 2, 2, 1, 2, 1, 2, 2, 1, 1, 2, 1],
	[2, 1, 2, 4, 2, 1, 2, 1, 2, 2, 1, 1],
	[2, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 1],
	[2, 1, 1, 2, 1, 1, 2, 2, 1, 2, 2, 1],
	[2, 2, 3, 2, 1, 1, 2, 1, 2, 2, 2, 1],   /* 2061 */
	[2, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 1],
	[2, 2, 1, 2, 1, 2, 3, 2, 1, 2, 1, 2],
	[2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1],
	[2, 2, 1, 2, 2, 1, 2, 1, 1, 2, 1, 2],
	[1, 2, 1, 2, 5, 2, 1, 2, 1, 2, 1, 2],
	[1, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1],
	[2, 1, 2, 1, 1, 2, 2, 1, 2, 2, 1, 2],
	[1, 2, 1, 5, 1, 2, 1, 2, 2, 2, 1, 2],
	[2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 1, 2],
	[2, 1, 2, 1, 2, 1, 1, 5, 2, 1, 2, 2],   /* 2071 */
	[2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2],
	[2, 1, 2, 2, 1, 2, 1, 1, 2, 1, 2, 1],
	[2, 1, 2, 2, 1, 5, 2, 1, 2, 1, 2, 1],
	[2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1, 2],
	[1, 2, 1, 2, 1, 2, 1, 2, 2, 1, 2, 1],
	[2, 1, 2, 3, 2, 1, 2, 2, 2, 1, 2, 1],
	[2, 1, 2, 1, 1, 2, 1, 2, 2, 1, 2, 2],
	[1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2],
	[2, 1, 5, 2, 1, 1, 2, 1, 2, 1, 2, 2],
	[1, 2, 2, 1, 2, 1, 1, 2, 1, 1, 2, 2],   /* 2081 */
	[1, 2, 2, 2, 1, 2, 3, 2, 1, 1, 2, 2],
	[1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1],
	[2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1, 2],
	[1, 2, 1, 1, 6, 1, 2, 2, 1, 2, 1, 2],
	[1, 2, 1, 1, 2, 1, 2, 2, 1, 2, 2, 1],
	[2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2],
	[1, 2, 1, 5, 1, 2, 1, 1, 2, 2, 2, 1],
	[2, 2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 1],
	[2, 2, 2, 1, 2, 1, 1, 5, 1, 2, 2, 1],
	[2, 2, 1, 2, 1, 2, 1, 2, 1, 1, 2, 1],   /* 2091 */
	[2, 2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1],
	[1, 2, 2, 1, 2, 4, 2, 1, 2, 1, 2, 1],
	[2, 1, 1, 2, 1, 2, 2, 1, 2, 2, 1, 2],
	[1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 1],
	[2, 1, 2, 3, 2, 1, 1, 2, 2, 2, 1, 2],
	[2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 2],
	[2, 2, 1, 2, 1, 1, 2, 1, 1, 2, 1, 2],
	[2, 5, 2, 2, 1, 1, 2, 1, 1, 2, 1, 2],
	[2, 2, 1, 2, 1, 2, 1, 2, 1, 1, 2, 1],
	[2, 2, 1, 2, 2, 1, 5, 2, 1, 1, 2, 1]	/* 2101 */
];

var a = 0;	// 테스트용

function lunaCalc(year, month, day){
	var b = 0;	// 테스트용
	var solYear, solMonth, solDay;
	var lunYear, lunMonth, lunDay;
	var lunLeapMonth, lunMonthDay;
	var i, lunIndex;
	var chk;	// 해당날짜에 공휴일 유무 체크. true 이면 공휴일
	var solMonthDay = [31, 0, 31, 30, 31, 30, 31, 31, 30, 31, 30 ,31];
	
	/* range check */
	if(year < 1900 || year > 2101){
		alert("1900년부터 2101년까지만 지원합니다.");
		return;
	}
	
	/* 속도 개선을 위해 기준 일자를 여러개로 구분. */
	if(year >= 2080){
		/* 기준일자 양력 2080년 1월 1일(음력 2079년 12월 10일) */
		solYear = 2080;
		solMonth = 1;
		solDay = 1;
		lunYear = 2079;
		lunMonth = 12;
		lunDay = 10;
		lunLeapMonth = 0;
		
		solMonthDay[1] = 29;	/* 2080년 2월 28일 */
		lunMonthDay = 30;		/* 2079년 12월 */
	}else if (year >= 2060){
		/* 기준일자 양력 2060년 1월 1일 (음력 2059년 11월 28일) */
		solYear = 2060;
		solMonth = 1;
		solDay = 1;
		lunYear = 2059;
		lunMonth = 11;
		lunDay = 28;
		lunLeapMonth = 0;

		solMonthDay[1] = 29; /* 2060 년 2월 28일 */
		lunMonthDay = 30; /* 2059년 11월 */
	}else if (year >= 2040){
		/* 기준일자 양력 2040년 1월 1일 (음력 2039년 11월 17일) */
		solYear = 2040;
		solMonth = 1;
		solDay = 1;
		lunYear = 2039;
		lunMonth = 11;
		lunDay = 17;
		lunLeapMonth = 0;
		
		solMonthDay[1] = 29; /* 2040 년 2월 28일 */
		lunMonthDay = 29; /* 2039년 11월 */
	}else if (year >= 2020){
		/* 기준일자 양력 2020년 1월 1일 (음력 2019년 12월 7일) */
		solYear = 2020;
		solMonth = 1;
		solDay = 1;
		lunYear = 2019;
		lunMonth = 12;
		lunDay = 7;
		lunLeapMonth = 0;

		solMonthDay[1] = 29; /* 2020 년 2월 28일 */
		lunMonthDay = 30; /* 2019년 12월 */
	}else if (year >= 2000){
		/* 기준일자 양력 2000년 1월 1일 (음력 1999년 11월 25일) */
		solYear = 2000;
		solMonth = 1;
		solDay = 1;
		lunYear = 1999;
		lunMonth = 11;
		lunDay = 25;
		lunLeapMonth = 0;

		solMonthDay[1] = 29; /* 2000 년 2월 28일 */
		lunMonthDay = 30; /* 1999년 11월 */
	}else if (year >= 1980){
		/* 기준일자 양력 1980년 1월 1일 (음력 1979년 11월 14일) */
		solYear = 1980;
		solMonth = 1;
		solDay = 1;
		lunYear = 1979;
		lunMonth = 11;
		lunDay = 14;
		lunLeapMonth = 0;

		solMonthDay[1] = 29; /* 1980 년 2월 28일 */
		lunMonthDay = 30; /* 1979년 11월 */
	}else if (year >= 1960){
		/* 기준일자 양력 1960년 1월 1일 (음력 1959년 12월 3일) */
		solYear = 1960;
		solMonth = 1;
		solDay = 1;
		lunYear = 1959;
		lunMonth = 12;
		lunDay = 3;
		lunLeapMonth = 0;

		solMonthDay[1] = 29; /* 1960 년 2월 28일 */
		lunMonthDay = 29; /* 1959년 12월 */
	}else if (year >= 1940){
		/* 기준일자 양력 1940년 1월 1일 (음력 1939년 11월 22일) */
		solYear = 1940;
		solMonth = 1;
		solDay = 1;
		lunYear = 1939;
		lunMonth = 11;
		lunDay = 22;
		lunLeapMonth = 0;

		solMonthDay[1] = 29; /* 1940 년 2월 28일 */
		lunMonthDay = 29; /* 1939년 11월 */
	}else if (year >= 1920){
		/* 기준일자 양력 1920년 1월 1일 (음력 1919년 11월 11일) */
		solYear = 1920;
		solMonth = 1;
		solDay = 1;
		lunYear = 1919;
		lunMonth = 11;
		lunDay = 11;
		lunLeapMonth = 0;

		solMonthDay[1] = 29; /* 1920 년 2월 28일 */
		lunMonthDay = 30; /* 1919년 11월 */
	}else if (year >= 1900){
		/* 기준일자 양력 1900년 1월 1일 (음력 1899년 12월 1일) */
		solYear = 1900;
		solMonth = 1;
		solDay = 1;
		lunYear = 1899;
		lunMonth = 12;
		lunDay = 1;
		lunLeapMonth = 0;

		solMonthDay[1] = 28; /* 1900 년 2월 28일 */
		lunMonthDay = 30; /* 1899년 12월 */
	}
	
	lunIndex = lunYear - 1899;
	
	var lunaHoliday;
	
	while(true){
		chk = "";
		if(solDay == day && solMonth == month && solYear == year){
			lunaHoliday = lunaHolidays[$.datepicker.formatDate("mmdd", new Date(lunYear, lunMonth - 1, lunDay))]; 
			if(lunaHoliday){
				chk = lunaHoliday.title;
			}else{
				// 설날 전날 쉬는날 지정을 위해서. 음력의 12월은 29일이기도 30일이기도 하니까.
				if(lunMonth == 12 && lunDay == lunMonthDay){
					chk = "추석";
				}
			}
			break;
		}
		
		/* set monthDay of Feb 2월 마지막날짜 지정. */
		if (solYear % 400 == 0){
			solMonthDay[1] = 29;
		}else if (solYear % 100 == 0){
			solMonthDay[1] = 28;
		}else if (solYear % 4 == 0){
			solMonthDay[1] = 29;
		}else{
			solMonthDay[1] = 28;
		}
		
		if(solDay == solMonthDay[solMonth - 1]){
			if(solMonth == 12){
				solYear++;
				solMonth = 1;
				solDay = 1;
			}else{
				solMonth++;
				solDay = 1;
			}
		}else{
			solDay++;
		}

		if(lunarMonthTable[lunIndex][lunMonth - 1] == 1){
			lunMonthDay = 29;
		}else if(lunarMonthTable[lunIndex][lunMonth - 1] == 2){
			lunMonthDay = 30;
		}else{
			if(lunarMonthTable[lunIndex][lunMonth - 1] == 3){
				lunMonthDay = 29;
			}else if(lunarMonthTable[lunIndex][lunMonth - 1] == 4 && lunLeapMonth == 0){
				lunMonthDay = 29;
			}else if(lunarMonthTable[lunIndex][lunMonth - 1] == 4 && lunLeapMonth == 1){	// 윤달인경우
				lunMonthDay = 30;
			}else if(lunarMonthTable[lunIndex][lunMonth - 1] == 5 && lunLeapMonth == 0){
				lunMonthDay = 30;
			}else if(lunarMonthTable[lunIndex][lunMonth - 1] == 5 && lunLeapMonth == 1){	// 윤달인경우
				lunMonthDay = 29;
			}else if(lunarMonthTable[lunIndex][lunMonth - 1] == 6){
				lunMonthDay = 30;
			}
		}
		
		if(lunDay == lunMonthDay){
			if(lunarMonthTable[lunIndex][lunMonth - 1] > 2 && lunLeapMonth == 0){
				lunLeapMonth = 1;
				lunDay = 1;
			}else{
				lunLeapMonth = 0;
				if(lunMonth == 12){
					lunYear++;
					lunMonth = 1;
					lunDay = 1;
				}else{
					lunMonth++;
					lunDay = 1;
				}
				if(lunYear > 2101){
					alert("2101 년까지 지원합니다.");
					break;
				}
			}
		}else{
			lunDay++;
		}
		lunIndex = lunYear - 1899;
	}
	return chk;
}