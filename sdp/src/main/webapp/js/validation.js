window.onload = browser_chk;

/* 브라우저별 head 에 script 추가 시작 */
function browser_chk() {
	var nv = navigator.appVersion;	// 브라우저 버젼 체크
	var nn = navigator.appName;		// 브라우저명 체크
	var nu = navigator.userAgent.toLowerCase();	// 브라우저 Agent 체크
	var dc = document.createElement("script");
	dc.type = "text/javascript";
	
	if(nu.indexOf("msie 7") > 0){	// 익스플로러 버전별로 다른 js 지정시 여기에 if 문 추가.
		dc.src = "/../js/common_ie.js";
	}else if(nu.indexOf("chrome") > 0){
		dc.src = "/../js/common_chrome.js";
	}else if(nu.indexOf("firefox") > 0){
		dc.src = "/../js/common_firefox.js";
	}
	
	document.getElementsByTagName("head")[0].appendChild(dc);
}
/* 브라우저별 head 에 script 추가 종료 */