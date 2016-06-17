<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<head>
    <meta charset="utf-8">
    <title>Site Title</title>
    <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="js/vendor/jquery-1.10.1.min.js"></script>
    <script type="text/javascript" src="js/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/bootbox.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {

            bootbox.dialog({
  title: "That html",
  message: '<img src="images/bootstrap_logo.png" width="100px"/><br/> You can also use <b>html</b>'
});
        });


    </script>

</head>
<body>
    <input type="button" value="Click Me" id="myBtn" style="position: absolute; top: 100px; left: 100px"/>
    

</body>






























