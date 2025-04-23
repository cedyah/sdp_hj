
  window.fbAsyncInit = function() {
    FB.init({
      appId      : '990045731028219',
      xfbml      : true,
      version    : 'v2.3'
    });
  };

/*(function(d, s, id){
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) {return;}
    js = d.createElement(s); js.id = id;
    js.src = "//connect.facebook.net/en_US/sdk.js";
    fjs.parentNode.insertBefore(js, fjs);
  }(document, 'script', 'facebook-jssdk'));*/
 

(function(d){
 var js, id = 'facebook-jssdk', ref = d.getElementsByTagName('script')[0];
 if (d.getElementById(id)) {return;}  
 js = d.createElement('script'); js.id = id; js.async = true;  
 js.src = "//connect.facebook.net/en_US/all.js";  
 ref.parentNode.insertBefore(js, ref);  
}(document));  

 

function facebooklogin() { 
 FB.login(function(response) {
   if (response.status === 'connected') {
	   getMyProfile();
   } else if (response.status === 'not_authorized') {
     // 페이스북에는 로그인 되어있으나, 앱에는 로그인 되어있지 않다.
   } else {
     // 페이스북에 로그인이 되어있지 않아서, 앱에 로그인 되어있는지 불명확하다.
   }
 } , {scope: "user_about_me,email,user_birthday"} ); //나는 유저의 아이디(이메일)과 생일 정보를 얻어오고 싶다.
 
}  

 

function getMyProfile(){
	 var myName= "";
	 var myEmail = "";
	 var myId = "";
	 var myImg = "";
	 var myInfo = "";
	
	 alert('1');
 FB.api('/me',function(user){
	myName= user.name ;
	myEmail = user.email;
	myId = user.id;
  });
 
 alert('2');
 FB.api('/me/picture?type=large',function(data){
	 myImg = data.data.url;
 });
 alert(myImg);
 if(myEmail != ""){
	 myInfo = 'myName:'+myName+',myEmail:'+myEmail+',myId:'+myId+',myImg:'+myImg;
	 $("#Facebook_res").val(myInfo);
	 $("#frm").attr({"action" : "callback3.do", "method" : "post"}).submit();
	 //정보를 post로 보내고 submit처리
 }
 
}




//This is called with the results from from FB.getLoginStatus().
function statusChangeCallback(response) {
  console.log('statusChangeCallback');
  console.log(response);
  // The response object is returned with a status field that lets the
  // app know the current login status of the person.
  // Full docs on the response object can be found in the documentation
  // for FB.getLoginStatus().
  if (response.status === 'connected') {
    // Logged into your app and Facebook.
    testAPI();
  } else if (response.status === 'not_authorized') {
    // The person is logged into Facebook, but not your app.
    document.getElementById('status').innerHTML = 'Please log ' +
      'into this app.';
  } else {
    // The person is not logged into Facebook, so we're not sure if
    // they are logged into this app or not.
    document.getElementById('status').innerHTML = 'Please log ' +
      'into Facebook.';
  }
}

// This function is called when someone finishes with the Login
// Button.  See the onlogin handler attached to it in the sample
// code below.
function checkLoginState() {
  FB.getLoginStatus(function(response) {
    statusChangeCallback(response);
  });
}

/*window.fbAsyncInit = function() {
FB.init({
  appId      : '{your-app-id}',
  cookie     : true,  // enable cookies to allow the server to access 
                      // the session
  xfbml      : true,  // parse social plugins on this page
  version    : 'v2.2' // use version 2.2
});*/

// Now that we've initialized the JavaScript SDK, we call 
// FB.getLoginStatus().  This function gets the state of the
// person visiting this page and can return one of three states to
// the callback you provide.  They can be:
//
// 1. Logged into your app ('connected')
// 2. Logged into Facebook, but not your app ('not_authorized')
// 3. Not logged into Facebook and can't tell if they are logged into
//    your app or not.
//
// These three cases are handled in the callback function.

FB.getLoginStatus(function(response) {
  statusChangeCallback(response);
});

// Load the SDK asynchronously
/*(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/en_US/sdk.js";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));*/

(function(d, s, id) {
	  var js, fjs = d.getElementsByTagName(s)[0];
	  if (d.getElementById(id)) return;
	  js = d.createElement(s); js.id = id;
	  js.src = "//connect.facebook.net/ko_KR/sdk.js#xfbml=1&version=v2.3&appId=990045731028219";
	  fjs.parentNode.insertBefore(js, fjs);
	}(document, 'script', 'facebook-jssdk'));

// Here we run a very simple test of the Graph API after login is
// successful.  See statusChangeCallback() for when this call is made.
function testAPI() {
	alert('testAPI');
  console.log('Welcome!  Fetching your information.... ');
  FB.api('/me', function(response) {
    console.log('Successful login for: ' + response.name);
    document.getElementById('status').innerHTML =
      'Thanks for logging in, ' + response.name + '!';
  });
}


