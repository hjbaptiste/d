<%@page import="com.jigy.api.Helpful"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<head>
    <meta name="description" content="Change/Update your Ideal Logic password." />
    <meta name="keywords" content="event, event management, event planning, change password, modify password, update password, change, update, modify, event marketing, events planning, events, events marketing, events management, eventmanagement event manager, promoter, promotions, promotions application, promotions app, event management app, event planning app, data collection, email collection, atlanta, ideal logic, ideal logic group"/>
    <meta name="author" content="www.getjigy.com" />
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

    <title>Change Password</title>

    <!-- start includes for speed optimization -->
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
        <!-- end includes for speed optimization -->

        <!--link href="styles/style.css"           rel="stylesheet" type="text/css">
        <link href="styles/framework.css"       rel="stylesheet" type="text/css">
        <link href="styles/font-awesome.css"    rel="stylesheet" type="text/css">
        <link href="styles/animate.css"         rel="stylesheet" type="text/css">
        <link href="css/bootstrap.min.css"      rel="stylesheet" type="text/css">
        
        <script type="text/javascript" src="scripts/jquery.js"></script>
        <script type="text/javascript" src="scripts/jqueryui.js"></script>
        <script type="text/javascript" src="scripts/framework-plugins.js"></script>
        <script type="text/javascript" src="scripts/custom.js"></script>
        <script src="js/common.js"></script-->
        <script type="text/javascript">
            function login() {
                var params = {userName: getEIV('userName'), password: getEIV('password')};
                var url = "<%= Helpful.getProperty(request, "jdbc.properties", "context")%>/login.html";

                /* create the callback function that will be called after submitting login info */
                var callback = function(data) {
                    var msgString = new String(data);
                    var response = msgString.split(",");
                    if (response[0] == 'error') {
                        getEI('error').innerHTML = '<div class="alert alert-danger"><button onclick="insertHtml(\'error\',\'&nbsp;\')" type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>'
                                + '<i class="fa fa-times-circle sign"></i><strong>' + response[1] + '</div>';
                        getEI('error').style.display = '';
                    } else {
                        getEI('error').innerHTML = '<div class="alert alert-success"><button onclick="insertHtml(\'error\',\'&nbsp;\')" type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>'
                                + '<i class="fa fa-times-circle sign"></i><strong>Login Successful</div>';
                        getEI('error').style.display = '';
                        // document.getElementById('loginForm').submit();
                    }
                };

                /* get the categories from the server */
                send(url, params, callback);
            }


            function displayForgotCredentials() {
                displayElement('sendBtn');
                displayElement('loginLink');
                displayElement('forgotCredentialInstructions');
                hideElement('passwordDiv');
                hideElement('loginBtn');
                hideElement('forgotCredentials');
            }


            function displayLogin() {
                displayElement('passwordDiv');
                displayElement('loginBtn');
                displayElement('forgotCredentials');
                hideElement('sendBtn');
                hideElement('loginLink');
                hideElement('forgotCredentialInstructions');
            }
    </script>
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
                    <img id="pageIcon" src="img/changePassword2.png" style="margin-left: auto; margin-right: auto" />
                    <br>
                    <div id="error">&nbsp;</div>
                    <form id="loginForm" action="displayFirstPage.html" method="POST">    
                        <div id="passwordDiv" class="login-password">
                            <i class="fa fa-lock"></i>
                            <input id="password" name="password" type="password" placeholder="New Password" value="" maxlength="45" >
                        </div>
                        <div id="confirmPasswordDiv" class="login-password">
                            <i class="fa fa-lock"></i>
                            <input id="confirmPassword" name="confirmPassword" type="password" onkeyup="isEnterKeyPressed(event, changePassword)" placeholder="Confirm New Password" value="" maxlength="45" >
                        </div>
                        <a id="loginLink" href="<%= Helpful.getProperty(request, "jdbc.properties", "context")%>/displayLogin.html" style="display:none" class="login-forgot"><i class="fa fa-lock"></i>Go to Login Page</a>
                </form>
                <div class="clear"></div>
                <a id="updatePasswordBtn" href="javascript:changePassword()" class="login-button button button-xl button-green button-fullscreen full-bottom">Update Password</a>
                <div class="decoration"></div>
                <div class="clear"></div>
            </div>


            <input id="activeElem" type="hidden" value="changePasswordMenuItem"></div>
        <!-- end body -->
        
        
        <script>
            Waves.attach('#updatePasswordBtn', ['waves-float']);
        </script>
        <!-- start footer -->
        <jsp:include page="footer.jsp"></jsp:include>    
        <!-- end footer -->