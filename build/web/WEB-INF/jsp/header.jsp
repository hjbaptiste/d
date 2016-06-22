<%@page import="com.homeclouddrive.domain.Node"%>
<%@page import="com.homeclouddrive.domain.User"%>
<%@page import="com.homeclouddrive.domain.Role"%>
<%@page import="com.jigy.api.Helpful"%>
<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% 
String path = request.getRequestURL().toString();
boolean isMove = path == null ? false : path.endsWith("move.jsp");
%>

<!-- start wave libraries -->
<jsp:include page="header_css_waves.jsp"></jsp:include>
<jsp:include page="header_js_waves.jsp"></jsp:include>
<!-- end wave libraries -->

<!-- analytics code -->
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-70752931-1', 'auto');
  ga('send', 'pageview');

</script>
<!-- end analytics code -->

<style>
    #poweredBy {
        color: #5893DD;
    }
    @media only screen 
          and (max-device-width: 325px) {
              #copyrightText {
                  font-size: 12px;
              }
              #error {
                  font-size: 12px;
              }
              #pageIcon {
                  height: 100px;
                  width: 100px;
              }
              .footer-icons{width:80%;margin-left:auto;margin-right:auto;text-align: center;margin-bottom:30px}
              .footer-icons a,.footer-icons a i{width:33px;height:33px;line-height:33px}
              .footer-icons a{margin-left:3px;margin-right:3px}
              .footer-icons a i{font-size:12px}
              .footer p{margin-bottom:15px}
          
    }
    
    @media only screen 
          and (min-device-width: 326px)
          and (max-device-width: 364px) {
              #copyrightText {
                  font-size: 12px;
              }
              #error {
                  font-size: 12px;
              }
              #pageIcon {
                  height: 120px;
                  width: 120px;
              }
              .footer-icons{width:80%;margin-left:auto;margin-right:auto;text-align: center;margin-bottom:30px}
              .footer-icons a,.footer-icons a i{width:36px;height:36px;line-height:36px}
              .footer-icons a{margin-left:5px;margin-right:5px}
              .footer-icons a i{font-size:12px}
              .footer p{margin-bottom:15px}
          
    }
    
    @media only screen 
          and (min-device-width: 365px) {
              #copyrightText {
                  font-size: 14px;
              }
              #error {
                  font-size: 12px;
              }
              #pageIcon {
                  height: 120px;
                  width: 120px;
              }
              .footer-icons{width:100%;margin-left:auto;margin-right:auto;text-align: center;margin-bottom:30px}
              .footer-icons a,.footer-icons a i{width:40px;height:40px;line-height:40px}
              .footer-icons a{margin-left:5px;margin-right:5px}
              .footer-icons a i{font-size:12px}
              .footer p{margin-bottom:15px}
          
    }
    <% if (Helpful.isUserLoggedIn(request)) { %>
        .fa-user {
            color: #3587BD;   
        }
    <% } else { %>
        .fa-user {
            color: black;   
        }
    <% } %>
</style>
<script type='text/javascript'>
    function setActive(){
        var activeElem = getEI(getEIV('activeElem'));
        
        if(activeElem){
            activeElem.className = activeElem.className + ' menu-item-active';
        }
    }
    
    
    /*
     * This method checks to see if the user pressed the enter key
     * and if so it logs the user in 
     */
    function isEnterKeyPressed(e, callback) {
        var code = (e.keyCode ? e.keyCode : e.which);
        if (code == 13) { //Enter keycode
            callback();
        }
    }
    
    function hideMenu(){
        $('#header-fixed').hide( 'slide', { direction: 'up'  }, 500 );
    }
    
    function showMenu(){
        $('#header-fixed').show( 'slide', { direction: 'up'  }, 500 );
    }
    
    function setHtmlBySize(smallHtml, mediumHtml, largeHtml){
        var width = screen.width;
        if(width <= 325){
            write(smallHtml);
        } else if(width > 325 && width <= 350){
            write(mediumHtml);
        } else if(width > 350){
            write(largeHtml);
        }
    }


    function write(html){
        document.write(html);
    }
</script>
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-66627791-1', 'auto');
  ga('send', 'pageview');

</script>
<!--div id="preloader">
    <div id="status">
        <div class="preloader-logo"></div>
        <h3 class="center-text">Welcome</h3>
        <p class="center-text smaller-text">
            We're loading the content, give us a second. This won't take long!
        </p>
    </div>
</div-->

<div class="gallery-fix"></div> <!-- Important for all pages that have galleries or portfolios -->
<!-- begin top bar -->
<% if(!isMove){ %>
<div id="header-fixed" class="header-style-1">
    <a class="header-1 open-left-sidebar" href="#"><i class="fa fa-navicon fa-lg flat-icon"></i></a>
    <a class="header-3" href="javascript: changeToSearchHeader()" style="position: absolute; top: 6px; right: 95px"><i id="search" class="fa fa-search fa-lg flat-icon"></i></a>
    <a class="header-4" href="javascript: display(); changeOrderIcon();" style="position: absolute; top: 7px; right: 55px"><i id="listOrder" class="fa fa-list fa-lg flat-icon reorder"></i></a>
    <h4 class="header-logo" style="margin-left: -120px; margin-top: 20px; color: rgba(0,0,0,.6)">Home Cloud Drive</h4>
        <% if (Helpful.isUserLoggedIn(request)) { %>
    <a class="header-2" href="signOut.html"><i class="fa fa-user fa-lg flat-icon"></i></a>
        <% } else { %>
    <a class="header-2" href="<%= Helpful.getProperty(request, "jdbc.properties", "context") %>/displayLogin.html"><i class="fa fa-user fa-lg flat-icon"></i></a>
        <% } %>
</div>
<div id="header-fixed_dir" class="header-style-1" style="display: none; border-top: none; top: 0px;">
    <a class="header-1" href="javascript: goUp()"><i class="fa fa-arrow-circle-left fa-2x flat-icon"></i></a>
    <a class="header-3" href="javascript: changeToSearchHeader()" style="position: absolute; top: 7px; right: 95px"><i id="search" class="fa fa-search fa-lg flat-icon"></i></a>
    <a class="header-4" href="javascript: display(); changeOrderIcon();" style="position: absolute; top: 8px; right: 55px"><i id="listOrder" class="fa fa-list fa-lg flat-icon reorder"></i></a>
    <h4 id="currentFolder" class="header-logo" style="margin-left: -120px; margin-top: 20px; color: rgba(0,0,0,.6)">Home Cloud Drive</h4>
        <% if (Helpful.isUserLoggedIn(request)) { %>
    <a class="header-2" href="signOut.html"><i class="fa fa-user fa-lg flat-icon" style="position: relative; top: 1px"></i></a>
        <% } else { %>
    <a class="header-2" href="<%= Helpful.getProperty(request, "jdbc.properties", "context") %>/displayLogin.html"><i class="fa fa-user fa-lg flat-icon" style="position: relative; top: 1px"></i></a>
        <% } %>
</div>
<div id="header-fixed_search" class="header-style-1" style="display: none; border-top: none; top: 0px;">
    <a class="header-1" href="javascript: goUp()"><i class="fa fa-arrow-circle-left fa-2x flat-icon"></i></a>
    <div id="searchBar" class="header-logo" style="margin-left: -120px; margin-top: 20px;">
        <input id="searchInput" type="text" maxlength="32" width="40" onkeyup="isEnterKeyPressed(event, search)" style="border: none; font-size: 16px;" />
    </div>
</div>
<% } else { %>
<div class="col-xs-12" style="height: 80px; padding-bottom:0px; margin-bottom: 0px; border-bottom: solid 1px rgba(0,0,0,.1)">
    <div class="col-xs-2" style="position: relative; top: 20px">
        <a id="goUpMoveBtn" class="header-1" href="javascript: goUpMove()"><i class="fa fa-arrow-circle-left fa-2x" style="text-align: left; line-height: 40px; color: rgba(0,0,0,.6)"></i></a>
    </div>
    <div class="col-xs-8" style="text-align: left; line-height: 40px; color: rgba(0,0,0,.6)">
        <div class="col-xs-12" style="position: relative; top: 8px; padding:0px; margin: 0px;">
            Select destination folder
        </div>
        <div id="moveHeaderName" class="col-xs-12" style="position: relative; top: -8px; padding:0px; margin: 0px; font-weight: 800; font-size: 20px">
            
        </div>
    </div>
    <div class="col-xs-2" style="position: relative; top: 20px">
        <i class="fa fa-folder fa-2x" style="text-align: left; line-height: 40px; color: rgba(0,0,0,.6)"></i>
    </div>
</div>
<div class="col-xs-12" style="padding-bottom:0px; margin-bottom: 0px; height: 35px; line-height: 35px; border-bottom: solid 1px gray">
    Folders
</div>

<% } %>
<!-- end top bar -->


<!-- begin left slideout menu -->
<div class="all-elements">
    <div class="snap-drawers">
        <div class="snap-drawer snap-drawer-left">        
            <div class="sidebar-header-left">
                <a href="#"><img src="img/logo.png" alt="img"></a>
                <a class="close-sidebar" href="#"><i class="fa fa-times"></i></a>
            </div>      

            <p class="sidebar-divider">Navigation</p>

            <% if(!Helpful.isUserLoggedIn(request)) { %>
            <div class="sidebar-menu">
                <a id="homeMenuItem" class="menu-item" href="index.html">
                    <i class="fa fa-home"></i>
                    <em>Home</em>
                    <i class="fa fa-circle"></i>
                </a>  
                <a id="howItWorksMenuItem" class="menu-item" href="displayHowItWorks.html">
                    <i class="fa fa-gears"></i>
                    <em>How It Works</em>
                    <i class="fa fa-circle"></i>
                </a>
                <a id="faqMenuItem" class="menu-item" href="displayFaq.html">
                    <i class="fa fa-question"></i>
                    <em>FAQ's</em>
                    <i class="fa fa-circle"></i>
                </a>
                <a id="loginMenuItem" class="menu-item" href="<%= Helpful.getProperty(request, "jdbc.properties", "context") %>/displayLogin.html">
                    <i class="fa fa-sign-in"></i>
                    <em>Log In</em>
                    <i class="fa fa-circle"></i>
                </a>
            </div>
            <% } else if(Role.Standard.equals(((User) Helpful.getUser(request)).getRole())) { %>
            <div class="sidebar-menu">
                <a id="homeMenuItem" class="menu-item" href="index.html">
                    <i class="fa fa-home"></i>
                    <em>Home</em>
                    <i class="fa fa-circle"></i>
                </a>  
                <a id="dashboardMenuItem" class="menu-item" href="displayDashboard.html">
                    <i class="fa fa-bar-chart"></i>
                    <em>My Control Center</em>
                    <i class="fa fa-circle"></i>
                </a>
                <a id="promotionalLinkMenuItem" class="menu-item" href="displayPromoterPromotions.html">
                    <i class="fa fa-link"></i>
                    <em>View Promotions</em>
                    <i class="fa fa-circle"></i>
                </a>  
                <a id="updatePaypalInfoMenuItem" class="menu-item" href="displayPaypalInfo.html">
                    <i class="fa fa-dollar"></i>
                    <em>Payout Info</em>
                    <i class="fa fa-circle"></i>
                </a>
                <a id="howItWorksMenuItem" class="menu-item" href="displayHowItWorks.html">
                    <i class="fa fa-question"></i>
                    <em>How It Works</em>
                    <i class="fa fa-circle"></i>
                </a>
                <a id="faqMenuItem" class="menu-item" href="displayFaq.html">
                    <i class="fa fa-question"></i>
                    <em>FAQ's</em>
                    <i class="fa fa-circle"></i>
                </a>
                <a id="changePasswordMenuItem" class="menu-item" href="displayResetPassword.html">
                    <i class="fa fa-exchange"></i>
                    <em>Change Password</em>
                    <i class="fa fa-circle"></i>
                </a>
                <a class="menu-item" href="signOut.html">
                    <i class="fa fa-sign-out"></i>
                    <em>Sign Out</em>
                    <i class="fa fa-circle"></i>
                </a>  
            </div>
            <% } else if(Role.Admin.equals(((User) Helpful.getUser(request)).getRole())) { %>
            <div class="sidebar-menu">
                <a id="homeMenuItem" class="menu-item" href="index.html">
                    <i class="fa fa-home"></i>
                    <em>Home</em>
                    <i class="fa fa-circle"></i>
                </a>    
                <a id="howItWorksMenuItem" class="menu-item" href="displayHowItWorks.html">
                    <i class="fa fa-question"></i>
                    <em>How It Works</em>
                    <i class="fa fa-circle"></i>
                </a>
                <a id="faqMenuItem" class="menu-item" href="displayFaq.html">
                    <i class="fa fa-question"></i>
                    <em>FAQ's</em>
                    <i class="fa fa-circle"></i>
                </a>
                <a id="businessListMenuItem" class="menu-item" href="displayBusinessList.html">
                    <i class="fa fa-link"></i>
                    <em>Business List</em>
                    <i class="fa fa-circle"></i>
                </a>
                <a id="businessPaymentsMenuItem" class="menu-item" href="displayBusinessPayments.html">
                    <i class="fa fa-link"></i>
                    <em>Business Payments</em>
                    <i class="fa fa-circle"></i>
                </a>
                <a id="promoterPayoutsMenuItem" class="menu-item" href="displayPromoterPayouts.html">
                    <i class="fa fa-link"></i>
                    <em>Promoter Payouts</em>
                    <i class="fa fa-circle"></i>
                </a>
                <a id="signUpMenuItem" class="menu-item" href="<%= Helpful.getProperty(request, "jdbc.properties", "context") %>/displaySignUp.html">
                    <i class="fa fa-plus"></i>
                    <em>Sign Up Business</em>
                    <i class="fa fa-circle"></i>
                </a>
                <a id="changePasswordMenuItem" class="menu-item" href="displayResetPassword.html">
                    <i class="fa fa-exchange"></i>
                    <em>Change Password</em>
                    <i class="fa fa-circle"></i>
                </a>
                <a class="menu-item" href="signOut.html">
                    <i class="fa fa-sign-out"></i>
                    <em>Sign Out</em>
                    <i class="fa fa-circle"></i>
                </a>  
            </div>
            <% } %>

            <p class="sidebar-divider">Let's get social</p>

            <div class="sidebar-menu">
                <a target="_blank" class="menu-item" href="https://instagram.com/ideallogicgroup">
                    <i class="fa fa-instagram"></i>
                    <em>Instagram</em>
                </a> 
                <a target="_blank" class="menu-item" href="https://www.facebook.com/people/Ideal-Logic/100010075840873">
                    <i class="fa fa-facebook"></i>
                    <em>Facebook</em>
                </a>                   
                <a target="_blank" class="menu-item" href="https://twitter.com/ideallogicgroup">
                    <i class="fa fa-twitter"></i>
                    <em>Twitter</em>
                </a>                 
                <a target="_blank" class="menu-item" href="https://plus.google.com/u/0/109908195725174676153/posts">
                    <i class="fa fa-google-plus"></i>
                    <em>Google Plus</em>
                </a>                    
                <a target="_blank" class="menu-item" href="https://www.youtube.com/channel/UCbo4n6rlMUYhv_5gW-Ukwng">
                    <i class="fa fa-youtube-play"></i>
                    <em>YouTube</em>
                </a>    
            </div>

            <p class="sidebar-divider">Contact Us</p>

            <div class="sidebar-menu">                 
                <a class="menu-item" href="mailto:support@ideallogicgroup.com?subject=Ideal Business Contact">
                    <i class="fa fa-envelope-o"></i>
                    <em>E-Mail Us</em>
                </a>     
            </div>
            
            <p class="sidebar-divider">Just a Bit of Legal Stuff</p>

            <div class="sidebar-menu">                     
                <a id="termsOfUseMenuItem" class="menu-item" href="displayTermsOfUse.html">
                    <i class="fa fa-legal"></i>
                    <em>Terms of Use</em>
                    <i class="fa fa-circle"></i>
                </a>
                <a id="privacyPolicyMenuItem" class="menu-item" href="displayPrivacyPolicy.html">
                    <i class="fa fa-eye"></i>
                    <em>Privacy Policy</em>
                    <i class="fa fa-circle"></i>
                </a>
                <a id="promoterAgreementMenuItem" class="menu-item" href="displayPromoterAgreement.html">
                    <i class="fa fa-exchange"></i>
                    <em>Promoter Agreement</em>
                    <i class="fa fa-circle"></i>
                </a>
            </div>

            <p class="sidebar-footer">
                Copyright 2015. All rights reserved
            </p>
        </div>
        <!-- end left slideout menu -->