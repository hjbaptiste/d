<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% 
String message = (String) request.getAttribute("message");
%>
<!DOCTYPE HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0 minimal-ui"/>
<meta name="apple-mobile-web-app-capable" content="yes"/>
<meta name="apple-mobile-web-app-status-bar-style" content="black">


<link rel="icon" type="image/png" href="img/favicon.png">
<link rel="apple-touch-icon" sizes="196x196" href="img/favicon.png">
<link rel="apple-touch-icon" sizes="180x180" href="img/favicon.png">
<link rel="apple-touch-icon" sizes="152x152" href="img/favicon.png">
<link rel="apple-touch-icon" sizes="144x144" href="img/favicon.png">
<link rel="apple-touch-icon" sizes="120x120" href="img/favicon.png">
<link rel="apple-touch-icon" sizes="114x114" href="img/favicon.png">
<link rel="apple-touch-icon" sizes="76x76" href="img/favicon.png">
<link rel="apple-touch-icon" sizes="72x72" href="img/favicon.png">
<link rel="apple-touch-icon" sizes="60x60" href="img/favicon.png">
<link rel="apple-touch-icon" sizes="57x57" href="img/favicon.png">  
<link rel="icon" type="image/png" href="img/favicon.png" sizes="96x96">
<link rel="icon" type="image/png" href="img/favicon.png" sizes="32x32">
<link rel="icon" type="image/png" href="img/favicon.png" sizes="16x16">
<link rel="shortcut icon" href="img/favicon.png" type="image/png" />
    
<title>Ideal Business</title>

<jsp:include page="header_css_fonts.jsp"></jsp:include>
<jsp:include page="header_css.jsp"></jsp:include>
<jsp:include page="header_css1.jsp"></jsp:include>
<jsp:include page="header_css1_1.jsp"></jsp:include>
<jsp:include page="header_css2.jsp"></jsp:include>
<jsp:include page="header_css_bootstrap1.jsp"></jsp:include>
<jsp:include page="header_css_bootstrap2.jsp"></jsp:include>


<jsp:include page="header_js.jsp"></jsp:include>
<jsp:include page="header_js_1.jsp"></jsp:include>
<jsp:include page="header_js_2.jsp"></jsp:include>
<jsp:include page="header_js_framework.jsp"></jsp:include>

<!--link href="styles/style.css"           rel="stylesheet" type="text/css">
<link href="styles/framework.css"       rel="stylesheet" type="text/css">
<link href="styles/font-awesome.css"    rel="stylesheet" type="text/css">
<link href="styles/animate.css"         rel="stylesheet" type="text/css">
<link href="css/bootstrap.min.css"         rel="stylesheet" type="text/css">

<script type="text/javascript" src="scripts/jquery.js"></script>
<script type="text/javascript" src="scripts/jqueryui.js"></script>
<script type="text/javascript" src="scripts/framework-plugins.js"></script>
<script type="text/javascript" src="scripts/custom.js"></script>
<script type="text/javascript" src="js/common.js"></script-->
</head>

<body class="left-sidebar">

<!-- start header -->
<jsp:include page="header.jsp"></jsp:include>    
<!-- end header -->
        
        
        <!-- start body -->
        <div id="content" class="snap-content">
            <div class="content">
            <div class="header-clear"></div>
            <!--Page content goes here, fixed elements go above the all elements class-->        
                
                <div class="decoration"></div>
                
                <div class="page-login full-bottom">
                    <div id="messageContainer" style="font-size: 200%; font-weight: 500">
                        <%= message %>
                    </div>
                </div>
                <!-- end body -->
                
                
                <!-- start footer -->
                <jsp:include page="footer.jsp"></jsp:include>    
                <!-- end footer -->