<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<sec:authentication property="principal" var="loginDTO"/>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="Dashboard">
  <meta name="keyword" content="Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">
  <title>Dashio - Bootstrap Admin Template</title>
  
  <!-- Favicons -->
  <link href="img/favicon.png" rel="icon">
  <link href="img/apple-touch-icon.png" rel="apple-touch-icon">

  <!-- Bootstrap core CSS -->
  <link href="lib/bootstrap/css/bootstrap.css" rel="stylesheet">
  
  <!--가져온 거 -->
  <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.0.0-alpha.6/css/bootstrap.min.css'>
  <link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css'>
  <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/octicons/4.4.0/font/octicons.min.css'>
  
  <!--external css-->
  <link href="lib/font-awesome/css/font-awesome.css" rel="stylesheet" />
  <!-- Custom styles for this template -->
  <link href="css/moon.css" rel="stylesheet">
  <link href="css/style.css" rel="stylesheet">
  <link href="css/style-responsive.css" rel="stylesheet">
  <!-- jQuery CDN-->
  <script
   src="https://code.jquery.com/jquery-1.9.0.js"
   integrity="sha256-TXsBwvYEO87oOjPQ9ifcb7wn3IrrW91dhj6EMEtRLvM="
   crossorigin="anonymous"></script>
  <!-- Web socket CDN -->
  <script src="http://cdn.sockjs.org/sockjs-0.3.4.js"></script>

  <!-- =======================================================
    Template Name: Dashio
    Template URL: https://templatemag.com/dashio-bootstrap-admin-template/
    Author: TemplateMag.com
    License: https://templatemag.com/license/
  ======================================================= -->
  <script>
 	 $(document).ready(function() {
		$(document).click(function(e){
			if(!$(e.target).is('#search2')) {
				if($(e.target).is('#buzzing')) buzzing();
				if($("#search2").val().trim().length == 0) {
					$("#search2").prop("rows", 1)
					$("#search2").val("");
					$("#appended").remove();
				}
			}
		});
		
		$("#search2").keyup(function(){
			console.log($("#search2").val().length);
			if($("#search2").val().trim().length > 0) $('#buzzing').attr("class", "btn btn-search-bar");
			else $('#buzzing').attr("class", "btn btn-search-bar disabled");
			$('#textCount').html($("#search2").val().length);
			if($("#search2").val().length > 140) {
				$('#buzzing').attr("class", "btn btn-search-bar disabled");
				$('#textCount').css("color", "red");
			} else {
				$('#textCount').css("color", "#31708f");
			}
		});				
		
		$("#search2").click(function(){
			$("#search2").prop("rows", 5);
			$("#appended").remove();
			$("#inputarea").append("<div id='appended' style='text-align:right'>"+
								       "<span id='textCount'>"+ $("#search2").val().length +"</span>/140&nbsp;&nbsp;<button id='buzzing' class='btn btn-search-bar disabled'>버징하기</button>"+
								   "</div>");
			if($("#search2").val().length > 140) $('#textCount').css("color", "red");
			else if($("#search2").val().trim().length > 0) {
				$('#buzzing').attr("class", "btn btn-search-bar");										
			}
		});
		
		$("#sendBtn").click(function() {
			if($("#search2").val().trim() == '') return;
			sendMessage();
			$('#search2').val('')
		});
	});
 	 
	//Web Socket js 
	//웹소켓을 지정한 url로 연결한다.
	let sock = new SockJS("<c:url value='/echo/echo'/>");
	sock.onmessage = onMessage;
	sock.onclose = onClose;
	
	function sendMessage() {
	   sock.send($("#search2").val());
	}

	// 서버로부터 메시지를 받았을 때
	function onMessage(msg) {
		$("#empty-buzzing").remove();
		var data = msg.data;
		boardNo = data.substring(data.lastIndexOf("$") + 1);
		data = data.substring(0, data.lastIndexOf("$"));
		data = data.replace(/\n/gi, "</br>");
		var html = '<div class="media">'+
				       '<a class="media-left" href="#fake">'+
				           '<img alt="" class="media-object img-rounded" src="http://placehold.it/64x64">'+
				       '</a>'+
				       '<div class="media-body">'+
				            '<h4 class="media-heading">${loginDTO.m_name} <a href="${loginDTO.username}">@${loginDTO.username}</a></h4><p>' + data + '</p>'+
				            '<ul class="nav nav-pills nav-pills-custom">'+
				                '<li><a href="#"><span class="glyphicon glyphicon-share-alt"></span></a></li>'+
				                '<li><a href="#"><span class="glyphicon glyphicon-retweet" id="board-rebuz">0</span></a></li>'+
				                '<li><a href="javascript:likes('+boardNo+')"><span class="glyphicon glyphicon-star" id="board-likes'+boardNo+'">0</span></a></li>'+
				                '<li><a href="#"><span class="glyphicon glyphicon-option-horizontal"></span></a></li>'+
				            '</ul>'+
				       '</div>'+
				  '</div>';
		$("#buz").prepend(html);
	}

	// 서버와 연결을 끊었을 때
	function onClose(evt) {
		$("#buz").append("연결 끊김");
	}

	function buzzing() {
		if($("#search2").val().trim() == '') return;
		sendMessage();
		$("#search2").val('');
	}

	function likes(boardNo){
		var request = $.ajax({url:"ajax/likes", method:"GET", data:{b_no:boardNo}, dataType:"html", 
		  success: function () {},
	      error: function (jqXHR) {
	        alert(jqXHR.status);
	        alert( jqXHR.statusText );
	        alert( jqXHR.responseText );
	        alert( jqXHR.readyState );
	      }});
		request.done(function(data){
			if(data == 'true') {
				$("#side-likes").html(parseInt($("#side-likes").html(), 10) + 1);
				$("#board-likes" + boardNo).html(parseInt($("#board-likes" + boardNo).html(), 10) + 1);
			} else {
				$("#side-likes").html(parseInt($("#side-likes").html(), 10) - 1);
				$("#board-likes" + boardNo).html(parseInt($("#board-likes" + boardNo).html(), 10) - 1);
			}
			
		});
	}
  </script>
</head>

<body>
  <div class='layer'>
  <span class='content'>
  <section id="container">
    <!-- **********************************************************************************************************************************************************
        TOP BAR CONTENT & NOTIFICATIONS
        *********************************************************************************************************************************************************** -->
    <!--header start-->
    <!-- Fixed top navbar -->
	<nav class="navbar navbar-toggleable-md fixed-top" id='board-header'>
	  	<button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
	        <span class="navbar-toggler-icon"></span>
	    </button>
	    
	<div id='top-logo'>
	<!--logo start-->
	    <a href="" class="logo"><b style="color:#404040">Buz<span style="color:rgb(251, 208, 67)">bee</span></b></a>
	<!--logo end-->
	</div>
	
	<!--  버즈비 로고 -->
	   	<a href=''><div id="logo"><img src="resources/img/fi.png"></div></a>
	<!--  버즈비 로고 -->
	
	  <div class="collapse navbar-collapse container" id='header-notice'>
	    <!-- Navbar navigation links -->
	    <ul class="navbar-nav mr-auto" id='menus'>
	      <li class="nav-item active">
	      <li class="nav-item">
	        <a class="nav-link" href="#"><i class="octicon octicon-bell"></i> Notifications</a>
	      </li>
	      <li class="nav-item">
	        <a class="nav-link" href="#"><i class="octicon octicon-inbox"></i> Messages</a>
	      </li>
	    </ul>
	    <!-- END: Navbar navigation links -->
	
	    <!-- 검색창 -->
	    <form class="navbar-form" role="search" action=''>
	      <div class="input-group">
	        <input type="text" class="form-control input-search" placeholder="Search Buzbee" name="srch-term" id="srch-term">
	        <div class="input-group-btn">
	          <button class="btn btn-default btn-search" type="submit"><i class="octicon octicon-search navbar-search-icon"></i></button>
	        </div>
	      </div>
	    </form>
	    <!-- END: 검색창 -->
	    
	    <!-- 프로필 사진 -->
	    <div class="dropdown navbar-user-dropdown">
	      <button class="btn btn-secondary dropdown-toggle btn-circle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"></button>
	      <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
	        <a class="dropdown-item" href="#">Action</a>
	        <a class="dropdown-item" href="#">Another action</a>
	        <a class="dropdown-item" href="#">Something else here</a>
	      </div>
	    </div>
	    <!-- END:프로필 사진  -->
	    <div id="ex1" class="modal">
		  <p>Thanks for clicking. That felt good.</p>
		  <a href="#" rel="modal:close">Close</a>
		</div>

	    
	    <!-- 버징 버튼 -->
	    <button class="btn btn-search-bar" id='buzzingBtn'>버징하기</button>
	  </div>
	</nav>
    
    <!--header end-->
    <!-- **********************************************************************************************************************************************************
        MAIN SIDEBAR MENU
        *********************************************************************************************************************************************************** -->
    <!--sidebar start-->
    
     <aside>
      <div id="sidebar" class="nav-collapse ">
        <!-- sidebar menu start-->
        <ul class="sidebar-menu" id="nav-accordion">
          <p class="centered"><a href="profile"><img src="resources/img/mb.png" class="img-circle" width='180'></a></p>
          <h5 class="centered" style="color:#404040; font-weight:bold">${loginDTO.m_name}</h5>
          <h5 class="centered" style="color:#b3b3b3">@${loginDTO.username}</h5>
          <li class="mt">
            <a href="index.html">
              <i class="fa fa-Buzzing"></i>
              <span style="color:#404040; font-weight:bold">버징</span>
              <span class="label label-theme pull-right mail-info">${etc.buzzes}</span>
              </a>
          </li>
          
          <li class="mt">
            <a href="index.html">
              <i class="fa fa-Buzzing"></i>
              <span style="color:#404040; font-weight:bold">팔로잉</span>
              <span class="label label-theme pull-right mail-info">${etc.following}</span>
              </a>
          </li>
          
           <li class="mt">
            <a href="index.html">
              <i class="fa fa-Buzzing"></i>
              <span style="color:#404040; font-weight:bold">팔로워</span>
              <span class="label label-theme pull-right mail-info">${etc.follower}</span>
              </a>
          </li>
          
           <li class="mt">
            <a href="index.html">
              <i class="fa fa-Buzzing"></i>
              <span style="color:#404040; font-weight:bold">좋아요</span>
              <span class="label label-theme pull-right mail-info" id='side-likes'>${etc.likes}</span>
              </a>
          </li>          


          <li class="mt">
            <a href="index.html">
              <i class="fa fa-Buzzing"></i>
              <span style="color:#404040; font-weight:bold">설정</span>
              </a>
          </li>                             

          <li class="mt">
            <a href="logout">
              <i class="fa fa-Buzzing"></i>
              <span style="color:#404040; font-weight:bold">로그아웃</span>
              </a>
          </li>
        <!-- sidebar menu end-->
      </div>
           </center>
    </aside>
    <!--sidebar end-->
    <!-- **********************************************************************************************************************************************************
        MAIN CONTENT
        *********************************************************************************************************************************************************** -->
    <!--ë ´ì ©-->

    <section id="main-content">
      <section class="wrapper site-min-height" id='section-body'>
      <div id='content-area'>
      <div class="col-lg-9" id='timeline'>
         <div class="panel panel-info" id='content-border'>
            <div class="panel-heading" id='input-form'>
               <a class="media-left" href="#fake">
                  <img alt="" class="media-object img-rounded" src="http://placehold.it/35x35">
               </a>
               <div class="media-body">
                  <div class="form-group has-feedback" id='inputarea'>
                     <label class="control-label sr-only" for="inputSuccess5">Hidden label</label>
                     <textarea class="form-control" id="search2" aria-describedby="search" placeholder='지금 무슨 일이 일어나고 있나요?' rows='1'></textarea>
                     <span class="glyphicon glyphicon-camera form-control-feedback" aria-hidden="true" ></span>
                     <!--<span id="search2" class="sr-only">(success)</span> -->
                  </div>
               </div>
            </div>
            <div class="panel-body" id='buz'>
            <c:if test='${empty buzzing}'>
               <div id='empty-buzzing' class='text-center'>아직 작성한 버징이 없습니다. 새로운 버징을 작성해주세요!</div>
            </c:if>
            <c:forEach items='${buzzing}' var='buzz'>
               <div class="media">
                  <a class="media-left" href="#fake">
                     <img alt="" class="media-object img-rounded" src="http://placehold.it/64x64">
                  </a>
                  <div class="media-body">
                     <h4 class="media-heading">${buzz.m_name} <a href='${buzz.m_id}'>@${buzz.m_id}</a></h4>
                     <p>${buzz.b_content}</p>
                     <ul class="nav nav-pills nav-pills-custom">
                        <li><a href="#"><span class="glyphicon glyphicon-share-alt"></span></a></li>
                        <li><a href="#"><span class="glyphicon glyphicon-retweet" id='board-rebuz'>${buzz.b_rebuz}</span></a></li>
                        <li><a href="javascript:likes(${buzz.b_no})"><span class="glyphicon glyphicon-star" id='board-likes${buzz.b_no}'>${buzz.b_like}</span></a></li>
                        <li><a href="#"><span class="glyphicon glyphicon-option-horizontal"></span></a></li>
                     </ul>
                  </div>
               </div>
            </c:forEach>
            </div>
         </div>
      </div>

      <div class="col-xs-3" id='side-nav'>
         <div class="panel panel-default panel-custom">
            <div class="panel-heading">
               <h3 class="panel-title">
                  Who to follow
                  <small><a href="#">Refresh</a> â   <a href="#">View all</a></small>
               </h3>
            </div>
            <div class="panel-body">
               <div class="media">
                  <div class="media-left">
                     <img src="http://placehold.it/32x32" alt="" class="media-object img-rounded">
                  </div>
                  <div class="media-body">
                     <h4 class="media-heading">Nome e cognome</h4>
                     <a href="#" class="btn btn-default btn-xs">
                        +
                        <span class="glyphicon glyphicon-user"></span>
                        Follow
                     </a>
                  </div>
               </div>
               <div class="media">
                  <div class="media-left">
                     <img src="http://placehold.it/32x32" alt="" class="media-object img-rounded">
                  </div>
                  <div class="media-body">
                     <h4 class="media-heading">Nome e cognome</h4>
                     <a href="#" class="btn btn-default btn-xs">
                        +
                        <span class="glyphicon glyphicon-user"></span>
                        Follow
                     </a>
                  </div>
               </div>
               <div class="media">
                  <div class="media-left">
                     <img src="http://placehold.it/32x32" alt="" class="media-object img-rounded">
                  </div>
                  <div class="media-body">
                     <h4 class="media-heading">Nome e cognome</h4>
                     <a href="#" class="btn btn-default btn-xs">
                        +
                        <span class="glyphicon glyphicon-user"></span>
                        Follow
                     </a>
                  </div>
               </div>
            </div>
            <div class="panel-footer">
               <a href="www.google.it">
                  <span class="glyphicon glyphicon-user"></span>
                  Find people you know
               </a>
            </div>
         </div>
         <div class="well well-sm">
            <ul class="list-inline">
               <li>© 2015 Twitter</li>
               <li><a href="#">About</a></li>
               <li><a href="#">Help</a></li>
               <li><a href="#">Terms</a></li>
               <li><a href="#">Privacy</a></li>
               <li><a href="#">Cookies</a></li>
               <li><a href="#">Ads info</a></li>
               <li><a href="#">Brand</a></li>
               <li><a href="#">Blog</a></li>
               <li><a href="#">Status</a></li>
               <li><a href="#">Apps</a></li>
               <li><a href="#">Jobs</a></li>
               <li><a href="#">Advertise</a></li>
               <li><a href="#">Businesses</a></li>
               <li><a href="#">Media</a></li>
               <li><a href="#">Developers</a></li>
            </ul>
         </div>
      </div>        
        </div>
      </section>
      <!-- /wrapper -->
    </section>
    <!-- /MAIN ë ´ì © -->
    <!--main content end-->
  </section>
  <span class="blank"></span>
  </span>
  </div>
  <!-- js placed at the end of the document so the pages load faster -->
  <script src="lib/jquery/jquery.min.js" ></script>
  <script src="lib/bootstrap/js/bootstrap.min.js"></script>
  <script src="lib/jquery-ui-1.9.2.custom.min.js" ></script>
  <script src="lib/jquery.ui.touch-punch.min.js"></script>
  <script class="include" type="text/javascript" src="lib/jquery.dcjqaccordion.2.7.js"></script>
  <script src="lib/jquery.scrollTo.min.js"></script>
  <script src="lib/jquery.nicescroll.js" type="text/javascript"></script>
  <!--common script for all pages-->
  <script src="lib/common-scripts.js"></script>
  <!--script for this page-->
</body>

</html>