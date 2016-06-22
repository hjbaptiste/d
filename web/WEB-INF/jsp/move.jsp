<%@page import="com.homeclouddrive.domain.User"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="com.homeclouddrive.domain.Node"%>
<%@page import="com.homeclouddrive.controller.BaseController"%>
<%@page import="com.jigy.api.Helpful"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<head>
    <meta name="description" content="View and add authorized devices to your account to control access to qr code scanning within your business." />
    <meta name="keywords" content="scan, scan device, scan devices, list of scan devices, list of scanners, authorized scanners, add scanner, add authorized scanner, add scanner device, view scanners, view scan devices, view scanner devices, view authorized devices, ideal business, ideal logic group"/>
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

    <title>Home Cloud Drive</title>

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
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    <!-- end includes for speed optimization -->


    <!--link href="styles/style.css"           rel="stylesheet" type="text/css">
    <link href="styles/framework.css"       rel="stylesheet" type="text/css">
    <link href="styles/font-awesome.css"    rel="stylesheet" type="text/css">
    <link href="styles/animate.css"         rel="stylesheet" type="text/css">
    
    <script type="text/javascript" src="scripts/jquery.js"></script>
    <script type="text/javascript" src="scripts/jqueryui.js"></script>
    <script type="text/javascript" src="scripts/framework-plugins.js"></script>
    <script type="text/javascript" src="scripts/custom.js"></script>
    <script type="text/javascript" src="js/common.js"></script-->
    <script type="text/javascript">    
        
        $(document).ready(function() { 
            getDirs();
        });
        
        // dirs vars
        var parentQueue = [];
        var currentNode;

        
        // move vars
        var moveParentQueue = [];
        var moveCurrentNode;
        var parentPath;


        
        function getDirs(){
            var params = {};
            var url = 'getNodeMover.html';
            var callback = function(nodeMoverStr) {
                if(nodeMoverStr != null){
                    var nodeMover = $.parseJSON(nodeMoverStr);
                    // dirs vars
                    parentQueue = nodeMover.parentQueue;
                    currentNode = nodeMover.currentNode;


                    // move vars
                    moveParentQueue = nodeMover.parentQueue;
                    moveCurrentNode = nodeMover.currentNode;
                    var parent = parentQueue.pop();
                    parentPath = parent.path;
                    parentQueue.push(parent);


                    displayFilesMove();                        
                }
            };
            
            send(url, params, callback);
        }        
    </script>
    <style>        
.inactivate {
   pointer-events: none;
   cursor: default;
   color: #999;
}
.activate {
    color: #0000EE;
}
        .folder-btn, .folder-btn:link, .folder-btn:visited {
  color:#FFFFFF;
  background-color:#73AD21;
  background-color:#4CAF50;  
  box-shadow: 0 1px 3px rgba(0,0,0,0.12), 0 1px 2px rgba(0,0,0,0.24);
  -webkit-user-select: none;
  .folder-btn,.folder-btn-block{border:none;display:inline-block;outline:0;padding:6px 16px;vertical-align:middle;overflow:hidden;text-decoration:none!important;color:#fff;background-color:#000;text-align:center;cursor:pointer;white-space:nowrap}
}
.folder-btn:hover, .folder-btn:active {
  background-color:#ffffff;
  color:#73AD21;
  color:#4CAF50;  
  box-shadow:0 8px 16px 0 rgba(0,0,0,0.2),0 6px 20px 0 rgba(0,0,0,0.19);
}
a.folder-btn{
  margin:10px 5px 0 0;
  width: 100%;
  height: 42px;
  font-size: 14px;
  line-height: 42px;
}

    .file-btn, .file-btn:link, .file-btn:visited {
  color:#FFFFFF;
  background-color: #fec00b;
  background-color: #fec00b;  
  box-shadow: 0 1px 3px rgba(0,0,0,0.12), 0 1px 2px rgba(0,0,0,0.24);
  -webkit-user-select: none;
  .file-btn,.file-btn-block{border:none;display:inline-block;outline:0;padding:6px 16px;vertical-align:middle;overflow:hidden;text-decoration:none!important;color:#fff;background-color:#000;text-align:center;cursor:pointer;white-space:nowrap}
}
.file-btn:hover, .file-btn:active {
  background-color:#ffffff;
  color:#73AD21;
  color:#4CAF50;  
  box-shadow:0 8px 16px 0 rgba(0,0,0,0.2),0 6px 20px 0 rgba(0,0,0,0.19);
}
a.file-btn{
  margin:10px 5px 0 0;
  width: 100%;
  height: 42px;
  font-size: 14px;
  line-height: 42px;
}

.modal2 {
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
        body.loading .modal2 {
            display: block;
        }
        
        
        @media only screen 
          and (min-device-width: 320px) 
          and (max-device-width: 350px) {
            .DIV_1 {
                color: rgb(51, 51, 51);
                height: 30px;
                position: relative;
                width: 100%;
                perspective-origin: 162.828125px 15px;
                transform-origin: 162.828125px 15px;
                border: 0px none rgb(51, 51, 51);
                font: normal normal normal normal 14.3999996185303px/20.8799991607666px 'Helvetica Neue', HelveticaNeue, Helvetica, Arial, sans-serif;
                margin: 0px 0px 14.3999996185303px;
                outline: rgb(51, 51, 51) none 0px;
            }
              .SPAN_2 {
                    box-sizing: border-box;
                    cursor: pointer;
                    display: block;
                    float: left;
                    height: 120%;
                    padding-left: 100px;
                    text-align: center;
                    text-shadow: rgba(255, 255, 255, 0.901961) 1px 2px 0px;
                    width: 24%;
                    perspective-origin: 24.421875px 15px;
                    transform-origin: 24.421875px 15px;
                    background: rgba(0, 0, 0, 0) linear-gradient(rgb(255, 255, 255) 0%, rgb(220, 220, 220) 100%) repeat scroll 0% 0% / auto padding-box border-box;
                    border: 1px solid rgb(202, 202, 202);
                    border-radius: 4px 0 0 4px;
                    font: normal normal normal normal .8em/20.8799991607666px 'Helvetica Neue', HelveticaNeue, Helvetica, Arial, sans-serif;
                    padding: 7px 0px 0px;
                }

              .urlInput {
                    box-shadow: rgb(51, 51, 51) 0px 0px 3px 0px inset;
                    height: 120%;
                    vertical-align: middle;
                    width: 66%;
                    perspective-origin: 136.78125px 15px;
                    transform-origin: 136.78125px 15px;
                    background: rgb(249, 249, 249) none repeat scroll 0% 0% / auto padding-box border-box;
                    border: 0px none rgb(0, 0, 0);
                    border-radius: 0 4px 4px 0;
                    font: normal normal normal normal .8em/normal 'Source Code Pro', Consolas, Monaco, 'Bitstream Vera Sans Mono', 'Courier New', Courier, monospace;
                    padding: 10px 9px;
                }
                
                
                #addBtn {
                    color: rgb(255, 255, 255);
                    cursor: pointer;
                    height: 10%;
                    vertical-align: middle;
                    white-space: nowrap;
                    width: 50%;
                    z-index: 0;
                    perspective-origin: 79.34375px 22.5px;
                    transform: matrix(1, 0, 0, 1, 0, 0);
                    transform-origin: 79.34375px 22.5px;
                    background: rgb(217, 83, 79) none repeat scroll 0% 0% / auto padding-box border-box;
                    border: 1px solid rgb(212, 63, 58);
                    border-radius: 6px 6px 6px 6px;
                    font: normal normal normal normal 1em 'Helvetica Neue', Helvetica, Arial, sans-serif;
                    outline: rgb(255, 255, 255) none 0px;
                    padding: 10px 16px;
                }
                
                .removeIcon {
                    width: 15px;
                    height: 15px;
                    margin: 0;
                    padding: 0px;
                    display: inline-block; 
                    margin-left: 5px;
                }
        }
        @media only screen 
          and (min-device-width: 351px) 
          and (max-device-width: 411px) {
            .DIV_1 {
                color: rgb(51, 51, 51);
                height: 30px;
                position: relative;
                width: 100%;
                perspective-origin: 162.828125px 15px;
                transform-origin: 162.828125px 15px;
                border: 0px none rgb(51, 51, 51);
                font: normal normal normal normal 14.3999996185303px/20.8799991607666px 'Helvetica Neue', HelveticaNeue, Helvetica, Arial, sans-serif;
                margin: 0px 0px 14.3999996185303px;
                outline: rgb(51, 51, 51) none 0px;
            }
            .SPAN_2 {
                box-sizing: border-box;
                cursor: pointer;
                display: block;
                float: left;
                height: 120%;
                padding-left: 100px;
                text-align: center;
                text-shadow: rgba(255, 255, 255, 0.901961) 1px 2px 0px;
                width: 24%;
                perspective-origin: 24.421875px 15px;
                transform-origin: 24.421875px 15px;
                background: rgba(0, 0, 0, 0) linear-gradient(rgb(255, 255, 255) 0%, rgb(220, 220, 220) 100%) repeat scroll 0% 0% / auto padding-box border-box;
                border: 1px solid rgb(202, 202, 202);
                border-radius: 4px 0 0 4px;
                font: normal normal normal normal .8em/20.8799991607666px 'Helvetica Neue', HelveticaNeue, Helvetica, Arial, sans-serif;
                padding: 7px 0px 0px;
            }

              .urlInput {
                    box-shadow: rgb(51, 51, 51) 0px 0px 3px 0px inset;
                    height: 120%;
                    vertical-align: middle;
                    width: 67%;
                    perspective-origin: 136.78125px 15px;
                    transform-origin: 136.78125px 15px;
                    background: rgb(249, 249, 249) none repeat scroll 0% 0% / auto padding-box border-box;
                    border: 0px none rgb(0, 0, 0);
                    border-radius: 0 4px 4px 0;
                    font: normal normal normal normal .8em/normal 'Source Code Pro', Consolas, Monaco, 'Bitstream Vera Sans Mono', 'Courier New', Courier, monospace;
                    padding: 10px 9px;
                }
                
                
                #addBtn {
                    color: rgb(255, 255, 255);
                    cursor: pointer;
                    height: 10%;
                    vertical-align: middle;
                    white-space: nowrap;
                    width: 120px;
                    z-index: 0;
                    perspective-origin: 79.34375px 22.5px;
                    transform: matrix(1, 0, 0, 1, 0, 0);
                    transform-origin: 79.34375px 22.5px;
                    background: rgb(217, 83, 79) none repeat scroll 0% 0% / auto padding-box border-box;
                    border: 1px solid rgb(212, 63, 58);
                    border-radius: 6px 6px 6px 6px;
                    font: normal normal normal normal 1em 'Helvetica Neue', Helvetica, Arial, sans-serif;
                    outline: rgb(255, 255, 255) none 0px;
                    padding: 10px 16px;
                }
                
                .removeIcon {
                    width: 15px;
                    height: 15px;
                    margin: 0;
                    padding: 0px;
                    display: inline-block; 
                    margin-left: 5px;
                }
        }
        
        
        @media only screen 
          and (min-device-width: 412px) 
          and (max-device-width: 599px) {
            .DIV_1 {
                color: rgb(51, 51, 51);
                height: 30px;
                position: relative;
                width: 100%;
                perspective-origin: 162.828125px 15px;
                transform-origin: 162.828125px 15px;
                border: 0px none rgb(51, 51, 51);
                font: normal normal normal normal 14.3999996185303px/20.8799991607666px 'Helvetica Neue', HelveticaNeue, Helvetica, Arial, sans-serif;
                margin: 0px 0px 14.3999996185303px;
                outline: rgb(51, 51, 51) none 0px;
            }
            .SPAN_2 {
                box-sizing: border-box;
                cursor: pointer;
                display: block;
                float: left;
                height: 166%;
                padding-left: 100px;
                text-align: center;
                text-shadow: rgba(255, 255, 255, 0.901961) 1px 2px 0px;
                width: 28%;
                perspective-origin: 24.421875px 15px;
                transform-origin: 24.421875px 15px;
                background: rgba(0, 0, 0, 0) linear-gradient(rgb(255, 255, 255) 0%, rgb(220, 220, 220) 100%) repeat scroll 0% 0% / auto padding-box border-box;
                border: 1px solid rgb(202, 202, 202);
                border-radius: 4px 0 0 4px;
                font: normal normal normal normal 1.1em/20.8799991607666px 'Helvetica Neue', HelveticaNeue, Helvetica, Arial, sans-serif;
                padding: 14px 0px 0px;
            }

                
          .urlInput {
                box-shadow: rgb(51, 51, 51) 0px 0px 3px 0px inset;
                height: 166%;
                vertical-align: middle;
                width: 63%;
                perspective-origin: 136.78125px 15px;
                transform-origin: 136.78125px 15px;
                background: rgb(249, 249, 249) none repeat scroll 0% 0% / auto padding-box border-box;
                border: 0px none rgb(0, 0, 0);
                border-radius: 0 4px 4px 0;
                font: normal normal normal normal 1em/normal 'Source Code Pro', Consolas, Monaco, 'Bitstream Vera Sans Mono', 'Courier New', Courier, monospace;
                padding: 10px 9px;
            }

            #addBtn {
                color: rgb(255, 255, 255);
                cursor: pointer;
                height: 15%;
                vertical-align: middle;
                white-space: nowrap;
                width: 50%;
                z-index: 0;
                perspective-origin: 79.34375px 22.5px;
                transform: matrix(1, 0, 0, 1, 0, 0);
                transform-origin: 79.34375px 22.5px;
                background: rgb(217, 83, 79) none repeat scroll 0% 0% / auto padding-box border-box;
                border: 1px solid rgb(212, 63, 58);
                border-radius: 6px 6px 6px 6px;
                font: normal normal normal normal 1.4em 'Helvetica Neue', Helvetica, Arial, sans-serif;
                outline: rgb(255, 255, 255) none 0px;
                padding: 10px 16px;
            }

            .removeIcon {
                width: 20px;
                height: 20px;
                margin: 0;
                padding: 0px;
                display: inline-block; 
                margin-left: 5px;
            }
        }
        
        @media only screen 
          and (min-device-width: 600px) {
              .DIV_1 {
                color: rgb(51, 51, 51);
                height: 30px;
                position: relative;
                width: 600px;
                perspective-origin: 162.828125px 15px;
                transform-origin: 162.828125px 15px;
                border: 0px none rgb(51, 51, 51);
                font: normal normal normal normal 14.3999996185303px/20.8799991607666px 'Helvetica Neue', HelveticaNeue, Helvetica, Arial, sans-serif;
                margin-left: auto;
                margin-right: auto;
                outline: rgb(51, 51, 51) none 0px;
            }
            .SPAN_2 {
                box-sizing: border-box;
                cursor: pointer;
                display: block;
                float: left;
                height: 166%;
                padding-left: 100px;
                text-align: center;
                text-shadow: rgba(255, 255, 255, 0.901961) 1px 2px 0px;
                width: 28%;
                perspective-origin: 24.421875px 15px;
                transform-origin: 24.421875px 15px;
                background: rgba(0, 0, 0, 0) linear-gradient(rgb(255, 255, 255) 0%, rgb(220, 220, 220) 100%) repeat scroll 0% 0% / auto padding-box border-box;
                border: 1px solid rgb(202, 202, 202);
                border-radius: 4px 0 0 4px;
                font: normal normal normal normal 1.1em/20.8799991607666px 'Helvetica Neue', HelveticaNeue, Helvetica, Arial, sans-serif;
                padding: 14px 0px 0px;
            }


          .urlInput {
                box-shadow: rgb(51, 51, 51) 0px 0px 3px 0px inset;
                height: 166%;
                vertical-align: middle;
                width: 60%;
                perspective-origin: 136.78125px 15px;
                transform-origin: 136.78125px 15px;
                background: rgb(249, 249, 249) none repeat scroll 0% 0% / auto padding-box border-box;
                border: 0px none rgb(0, 0, 0);
                border-radius: 0 4px 4px 0;
                font: normal normal normal normal 1em/normal 'Source Code Pro', Consolas, Monaco, 'Bitstream Vera Sans Mono', 'Courier New', Courier, monospace;
                padding: 10px 9px;
            }

            #addBtn {
                color: rgb(255, 255, 255);
                cursor: pointer;
                height: 15%;
                vertical-align: middle;
                white-space: nowrap;
                width: 150px;
                z-index: 0;
                perspective-origin: 79.34375px 22.5px;
                transform: matrix(1, 0, 0, 1, 0, 0);
                transform-origin: 79.34375px 22.5px;
                background: rgb(217, 83, 79) none repeat scroll 0% 0% / auto padding-box border-box;
                border: 1px solid rgb(212, 63, 58);
                border-radius: 6px 6px 6px 6px;
                font: normal normal normal normal 1.4em 'Helvetica Neue', Helvetica, Arial, sans-serif;
                outline: rgb(255, 255, 255) none 0px;
                padding: 10px 16px;
            }

            .removeIcon {
                width: 20px;
                height: 20px;
                margin: 0;
                padding: 0px;
                display: inline-block; 
                margin-left: 5px;
            }
        }
        

        

        #deviceDeletedInfo {
            box-sizing: border-box;
            color: rgb(49, 112, 143);
            height: 54px;
            width: 50%;
            margin: 0 auto;
            position: relative;
            top: -100px;
            perspective-origin: 360px 54px;
            transform-origin: 360px 54px;
            background: rgb(217, 237, 247) none repeat scroll 0% 0% / auto padding-box border-box;
            border: 1px solid rgb(188, 232, 241);
            border-radius: 4px 4px 4px 4px;
            font: normal normal normal normal 14px / 14px 'Helvetica Neue', Helvetica, Arial, sans-serif;
            outline: rgb(49, 112, 143) none 0px;
            padding: 15px;
            text-align: center;
        }
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        /**
 * Menu overview.
 */
.c-menu {
  position: fixed;
  z-index: 200;
  background-color: #ffffff;
  transition: transform 0.3s;
}



/**
 * Body states.
 *
 * When a menu is active, we want to hide the overflows on the body to prevent
 * awkward document scrolling.
 */
body.has-active-menu {
  overflow: hidden;
}

/**
 * Mask component
 */

.c-mask {
  position: fixed;
  z-index: 100;
  top: 0;
  left: 0;
  overflow: hidden;
  width: 0;
  height: 0;
  background-color: #000;
  opacity: 0;
  transition: opacity 0.3s, width 0s 0.3s, height 0s 0.3s;
}

.c-mask.is-active {
  width: 100%;
  height: 100%;
  opacity: 0.7;
  transition: opacity 0.3s;
}

.c-menu--slide-top,
.c-menu--slide-bottom,
.c-menu--push-top,
.c-menu--push-bottom {
  vertical-align: middle;
  width: 100%;
  height: 375px;
  text-align: center;
  overflow-x: hidden;
}

.c-menu--slide-top .c-menu__items,
.c-menu--slide-bottom .c-menu__items,
.c-menu--push-top .c-menu__items,
.c-menu--push-bottom .c-menu__items {
  display: inline-block;
  text-align: center;
}

.c-menu--slide-top .c-menu__item,
.c-menu--slide-bottom .c-menu__item,
.c-menu--push-top .c-menu__item,
.c-menu--push-bottom .c-menu__item {
  display: inline-block;
  line-height: 60px;
}

.c-menu--slide-top .c-menu__link,
.c-menu--slide-bottom .c-menu__link,
.c-menu--push-top .c-menu__link,
.c-menu--push-bottom .c-menu__link {
  display: block;
  padding: 0 4px;
  color: #fff;
}

.c-menu--slide-top .c-menu__close,
.c-menu--slide-bottom .c-menu__close,
.c-menu--push-top .c-menu__close,
.c-menu--push-bottom .c-menu__close {
  display: inline-block;
  margin-right: 12px;
  padding: 0 24px;
  height: 0px;
  width: 0px;
  line-height: 0px;
}

.c-menu--slide-bottom,
.c-menu--push-bottom {
  bottom: -260px;
  left: 0;
  transform: translateY(500px);
}

.c-menu--slide-bottom.is-active,
.c-menu--push-bottom.is-active {
  transform: translateY(-160px);
}

#copyShareLink {
    border: none;
    overflow: auto;
    outline: none;

    -webkit-box-shadow: none;
    -moz-box-shadow: none;
    box-shadow: none;
}
    </style>
</head>

<body class="left-sidebar">

    <!-- start header -->
    <jsp:include page="header.jsp"></jsp:include>    
        <!-- end header -->      

        <!-- start body -->
        <div id="content" class="snap-content">
            <div id="fill-content" class="fill-content">
                <br>
                <!--Page content goes here, fixed elements go above the all elements class-->
                <div id="error"></div>
                <div id="deviceDeletedInfo" style="display:none; z-index: 3000">
                    <strong id="STRONG_2"> Item Deleted </strong>
                </div>
                <div id="fileContainer" class="container">                    

                </div>
                <form id="folderToMoveToForm" method="post" action="moveFileToFolder.html">
                    <input id="folderToMoveTo" type="hidden" name="folderToMoveTo" value="" />
                </form>
            <input id="activeElem" type="hidden" value="devicesMenuItem">
            <div class="modal2"></div>
        </div>
        
        
        <!-- end body -->


        <script>
            Waves.attach('.folder-btn', '.fa-ellipsis-v', ['waves-float']);
            Waves.attach('.menuItem', ['waves-block']);
        </script>
        <!-- start footer -->
        <jsp:include page="footer.jsp"></jsp:include>    
        <!-- end footer -->  
        <div class="col-xs-12" style="position: fixed; bottom: 0px; height: 40px; background-color: rgb(230,230,230); padding-bottom:0px; margin-bottom: 0px; border-top: solid 1px rgba(0,0,0,.2)">
            <div class="col-xs-6"></div>
            <div class="col-xs-3"><a href="javascript:cancel()" style="text-align: right; color: #0000EE; font-size: 16px; font-weight: 500; line-height: 40px;">Cancel</a></div>
            <div class="col-xs-3"><a id="moveLink" class="inactivate" href="javascript:moveFileToFolder()" style="text-align: right; font-size: 16px; font-weight: 500; line-height: 40px;">Move</a></div>
        </div>