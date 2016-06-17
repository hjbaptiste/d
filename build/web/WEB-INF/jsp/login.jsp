<%@page import="com.jigy.api.Helpful"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String loginCookie = (String) Helpful.getObjFromSession(request, "loginCookie");
%>
<!DOCTYPE HTML>
<head>
    <meta name="description" content="Login to your Ideal Business account to manage events and promotions." />
    <meta name="keywords" content="event, login, log in, sign in, event management, promotion management, create promotions, event planning, event marketing, events planning, events, events marketing, events management, eventmanagement event manager, promoter, promotions, promotions application, promotions app, event management app, event planning app, data collection, email collection, atlanta, ideal logic, ideal logic group"/>
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

    <title>Log In</title>
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
        <link href="styles/animate.css"         rel="stylesheet" type="text/css"-->

        <!-- script type="text/javascript" src="scripts/jquery.js"></script>
        <script type="text/javascript" src="scripts/jqueryui.js"></script>
        <script type="text/javascript" src="scripts/framework-plugins.js"></script-->
        <script type="text/javascript">
            function login() {
            var params = {userName: getEIV('userName'), password: getEIV('password'), loginCookie: getEIV('loginCookie')};
                    var url = "<%= Helpful.getProperty(request, "jdbc.properties", "context")%>/login.html";
                    /* create the callback function that will be called after submitting login info */
                    var callback = function(jsonReturnObj) {
                    if (jsonReturnObj.isError) {
                    getEI('error').innerHTML = '<div class="alert alert-danger"><button onclick="insertHtml(\'error\',\'&nbsp;\')" type="button" class="close" data-dismiss="alert" aria-hidden="true">&nbsp;×</button>'
                            + '<i class="fa fa-times-circle sign"></i><strong> ' + jsonReturnObj.errorMessage + '</div>';
                            getEI('error').style.display = '';
                            jQuery("body").removeClass("loading");
                    } else {
                    window.location.href = jsonReturnObj.successMessage;
                    }
                    };
                    // clear out all errors
                    insertHtml('error', '&nbsp;');
                    jQuery("body").addClass("loading");
                    send(url, params, callback);
            }

            var isLoginDisplayed = true;
                    function displayForgotCredentials() {
                    displayElement('sendBtn');
                            displayElement('loginLink');
                            displayElement('forgotCredentialInstructions');
                            hideElement('passwordDiv');
                            hideElement('loginBtn');
                            hideElement('forgotCredentials');
                            insertHtml('error', '');
                            isLoginDisplayed = false;
                    }


            function displayLogin() {
            displayElement('passwordDiv');
                    displayElement('loginBtn');
                    displayElement('forgotCredentials');
                    hideElement('sendBtn');
                    hideElement('loginLink');
                    hideElement('forgotCredentialInstructions');
                    insertHtml('error', '');
                    isLoginDisplayed = true;
            }

            function getPasswordRetrieval(){
            // clear out all errors
            insertHtml('error', '&nbsp;');
                    retrievePassword('<%= Helpful.getProperty(request, "jdbc.properties", "context")%>/retrieveLostPassword.html');
            }

            function checkForEnterKey(){
            if (!isLoginDisplayed){
            isEnterKeyPressed(event, getPasswordRetrieval);
            }
            }
    </script>
    <style>
        .modal {
            display:    none;
            position:   fixed;
            z-index:    1000;
            top:        0;
            left:       0;
            height:     100%;
            width:      100%;
            background: rgba( 255, 255, 255, .8 ) 
                url('img/loading.gif') 
                50% 50% 
                no-repeat;
        }

        /* When the body has the loading class, we turn
           the scrollbar off with overflow:hidden */
        body.loading {
            overflow: hidden;   
        }

        /* Anytime the body has the loading class, our
           modal element will be visible */
        body.loading .modal {
            display: block;
        }
    </style>
</head>

<body class="left-sidebar">

    <!-- start header -->
    <jsp:include page="header.jsp"></jsp:include>    
        <!-- end header -->



        <div id="content" class="snap-content">
            <div class="content">
                <div class="header-clear"></div>
                <!--Page content goes here, fixed elements go above the all elements class-->        

                <div class="decoration"></div>

                <div class="page-login full-bottom">
                    <img id="pageIcon" src="img/welcome.jpg" style="margin-left: auto; margin-right: auto" />
                    <div id="error" style="margin-top: 20px">&nbsp;</div>
                    <form id="loginForm" action="displayEventPromotions.html" method="POST">    
                        <div id="forgotCredentialInstructions" style="display:none; text-align: center"><b>Enter the email address for your account</b></div><br>
                        <div class="login-input">
                            <i class="fa fa-user"></i>
                            <input id="userName" name="userName" type="text" onkeyup="checkForEnterKey()" placeholder="E-mail" value="" maxlength="45">
                        </div>
                        <div id="passwordDiv" class="login-password">
                            <i class="fa fa-lock"></i>
                            <input id="password" name="password" onkeyup="isEnterKeyPressed(event, login)" type="password" placeholder="Password" value="" maxlength="45" >
                        </div>
                        <a id="forgotCredentials" href="javascript:displayForgotCredentials()" class="login-forgot" style="color: #5893DD" onmousedown="this.style.color = purple" onmouseup="this.style.color = #5893DD"><i class="fa fa-unlock"></i>Forgot Credentials</a>
                        <a id="loginLink" href="javascript:displayLogin()" style="display:none; color: #5893DD" onmousedown="this.style.color = purple" onmouseup="this.style.color = #5893DD" class="login-forgot"><i class="fa fa-lock"></i>Go Back to Login</a>
                        <a id="createAccount" href="<%= Helpful.getProperty(request, "jdbc.properties", "context")%>/displaySignUp.html" class="login-create" onmousedown="this.style.color = purple" onmouseup="this.style.color = #5893DD" style="color: #5893DD; display: none">Create Account<i class="fa fa-user"></i></a>
                    <input id="loginCookie" name="loginCookie" type="hidden" value="<%= loginCookie%>"
                </form>
                <div class="clear"></div>
                <a id="loginBtn" href="javascript:login()" class="login-button button button-xl button-green button-fullscreen full-bottom">Login</a>
                <a id="sendBtn" href="javascript:retrievePassword('retrieveLostPassword.html')" style="display:none" class="login-button button button-xl button-green button-fullscreen full-bottom">Submit</a>
            </div>

            <input id="activeElem" type="hidden" value="loginMenuItem"></div>
        <div class="modal"></div>
        <!-- end body -->


        <script type="text/javascript">
                    Waves.attach('.login-button', ['waves-float']);
        </script>
        <!-- start footer -->
        <jsp:include page="footer.jsp"></jsp:include>    
        <!-- end footer -->

