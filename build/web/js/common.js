/*
 * This function makes an ajax call to the server
 * @param {type} url the url to call
 * @param {type} params the parameters to send to the server
 * @param {type} callback the callback function to evoke when the ajax call returns
 */
function send(url, params, callback) {
    $.ajax({
        type: "POST",
        url: url,
        data: params
    }).done(function(data) {
        callback(data);
    });
}


/*
 * This function makes an ajax call to the server and does not 
 * take a callback function
 * @param {type} url the url to call
 * @param {type} params the parameters to send to the server
 * @param {type} callback the callback function to evoke when the ajax call returns
 */
function sendNoCallback(url, params) {
    $.ajax({
        type: "POST",
        url: url,
        data: params
    });
}


/**
 * This function gets the element represented by
 * the passed in id
 * @param {type} id the id of the element to retrieve
 * @returns {@exp;@call;getEI@pro;value}
 */
function getEI(id) {
    return document.getElementById(id);
}


/**
 * This function gets the value of the element represented by
 * the passed in id
 * @param {type} id the id of the element to retrieve the value of
 * @returns {@exp;@call;getEI@pro;value}
 */
function getEIV(id) {
    return getEI(id).value;
}


/**
 * This funciton sets the passed in html as the innerHTML of the 
 * element represented by the passed in id
 * @param {type} id the id of the element to insert the html into
 * @param {type} html the html to insert
 * @returns {undefined}
 */
function insertHtml(id, html){
    getEI(id).innerHTML = html;
}


/**
 * This function turns the display of an element on
 * @param {type} id the id of the element to hide
 * @returns {undefined}
 */
function displayElement(id){
    if(getEI(id)){
        getEI(id).style.display = '';
    }
}


/**
 * This function turns the display of an element off
 * @param {type} id the id of the element to hide
 * @returns {undefined}
 */
function hideElement(id){
    if(getEI(id)){
        getEI(id).style.display = 'none';
    }
}


/**
 * This method appends an html string to the end of a container
 * @param {type} id the id of the element to append to
 * @param {type} html the html to append
 * @returns {undefined}
 */
function appendHtml(id, html){
    if(getEI(id)){
        var newHtml = getEI(id).innerHTML + html;
        insertHtml(id, newHtml);
    }
}


/**
 * this function is called when a user submits a form.
 * It grabs all of the information from the form
 * @param {type} formId the id of the form to submit
 * @param {type} url the url to submit to
 * @param {type} callback
 * @param {type} async true if the request should be asynchronous, false otherwise
 * @returns {undefined}
 */
function wrapParams(formId, url, callback, async){        
    var paramString = new String();
    var form = document.getElementById(formId);
    var inputs = form.getElementsByTagName('input');
    var txts = form.getElementsByTagName('TEXTAREA') 

    // add all form parameters to the paramString
    for(var i = 0; i < inputs.length; i++){
        var elem = inputs[i];
        if(elem.type != "submit" && elem.type != "button"){
            paramString = paramString + elem.name + '=' + encodeURIComponent(elem.value) + '&';
        }
    }
    
    
    // add all form text areas to the paramString
    for(var i = 0; i < txts.length; i++){
        var elem = txts[i];
        if(elem.type != "submit" && elem.type != "button"){
            paramString = paramString + elem.name + '=' + encodeURIComponent(elem.value) + '&';
        }
    }
	

    // add params to url
    url = url + '?' + paramString.substring(0, paramString.length - 1);

    // submit form
    $.ajax({
        type: "POST",
        async: async,
        url: url
    }).done(function(msg){
        callback(msg);
    });
}


 /**
 * This functions toggles the lost password input
 */
function lostPassword(){
    
    var lostPasswordHtml = '<div id="retrievePasswordError" style="height:40px; color: green"><br>&nbsp;&nbsp;To retrieve your password, enter your email and click send</div><br>'
    + '<div><input type="email" placeholder="Enter Your Email" id="forgotPasswordEmail" '
    + 'name="forgotPasswordEmail" maxlength="45">'
    + '<button type="button" onclick="retrievePassword(\'retrieveLostPassword.html\')">Send</button>'
    + '</div><div style="height:40px"></div>';
    
    insertHtml('forgotUserName', lostPasswordHtml);
    getEI('forgotUserName').style.backgroundColor = '#e0ecfa';
    getEI('forgotPasswordEmail').focus(); 
}


/**
 * This function helps a user to retrieve their password credentials
 * @param {type} url the url to submit to
 */
function retrievePassword(url){
    var email = document.getElementById('userName').value;
    var html = '';
    if(email){
        var url = url + '?email=' + encodeURIComponent(email);
        $.ajax({
        type: "POST",
        dataType: "text",
        url: url,
        async: true
    }).done(function(msg){
        var msgStr = new String(msg);
        var response = msgStr.split(",");
        if('ERROR' == response[0]){
            html = '<div class="alert alert-danger"><button onclick="insertHtml(\'error\',\'&nbsp;\')" type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>'
                        + '<strong>' + response[1] + '</div>';          
        } else if('SUCCESS' == response[0]){
            html = '<div class="alert alert-success"><button onclick="insertHtml(\'error\',\'&nbsp;\')" type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>'
                        + '<i class="fa fa-times-circle sign"></i><strong>&nbsp;&nbsp;A password recovery email has been sent to the email address on file.</div>';
        }
        insertHtml('error', html);
    });
  } else {
        html = '<div class="alert alert-danger"><button onclick="insertHtml(\'error\',\'&nbsp;\')" type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>'
                        + '<strong>Please Enter an Email Address</div>';    
        insertHtml('error', html);
  }
}



  /**
   * This function sets the maxlength of all text areas on the page 
   * @returns {undefined}   
   */
function setMaxLengthOnTxtAreas() { 
  var txts = document.getElementsByTagName('TEXTAREA'); 

  for(var i = 0, l = txts.length; i < l; i++) {
    if(/^[0-9]+$/.test(txts[i].getAttribute("maxlength"))) { 
          var func = function() { 
                var len = parseInt(this.getAttribute("maxlength"), 10); 

                if(this.value.length > len) { 
                  alert('Maximum length exceeded: ' + len); 
                  this.value = this.value.substr(0, len); 
                  return false; 
                } 
          };

      txts[i].onkeyup = func;
      txts[i].onblur = func;
    } 
  } 
}
  
  
  
/**
 * This function clears all inputs on the passed in form
 * @param {type} formId the id of the form to submit
 * @returns {undefined}
 */
function clearForm(formId){        
    var form = document.getElementById(formId);
    var inputs = form.getElementsByTagName('input');
    var txts = form.getElementsByTagName('TEXTAREA') 

    // add all form parameters to the paramString
    for(var i = 0; i < inputs.length; i++){
        var elem = inputs[i];
        if(elem.type != "submit" && elem.type != "button"){
            elem.value = '';
        }
    }
    
    
    // add all form text areas to the paramString
    for(var i = 0; i < txts.length; i++){
        var elem = txts[i];
        if(elem.type != "submit" && elem.type != "button"){
            elem.value = '';
        }
    }
}



/**
 * This function bolds the passed-in text
 * @param {type} str the text to make bold
 * @returns {String}
 */
function embolden(str){
    if(str){
        return '<b>' + str + '</b>';
    }
        return '';
}



/**
 * This function logs a user in
 */
function login(){
    var params = {userName : getEIV('userName'), password : getEIV('password')};   
    var url = "https://events.ideallogicgroup.com/login.html";

    /* create the callback function that will be called after submitting login info */
    var callback = function(data){
        var msgString = new String(data);
        var response = msgString.split(",");
        if(response[0] == 'error'){
            getEI('error').innerHTML = '<div class="alert alert-danger"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>'
            + '<i class="fa fa-times-circle sign"></i><strong>' + response[1] + '</div>';
            getEI('error').style.display = '';
        } else {
            getEI('error').style.color='green';
            insertHtml('error', response[1]);
            // document.getElementById('loginForm').submit();
        }                     
    };

    /* get the categories from the server */
    send(url, params, callback);
}


function changePassword(){
    var params = {password : getEIV('password'), confirmPassword : getEIV('confirmPassword')};   
    var url = "https://events.ideallogicgroup.com/changePassword.html";

    /* create the callback function that will be called after submitting login info */
    var callback = function(data){
        var msgString = new String(data);
        var response = msgString.split(",");
        if(response[0] == 'ERROR'){
            getEI('error').innerHTML = '<div class="alert alert-danger"><button onclick="insertHtml(\'error\',\'&nbsp;\')" type="button" class="close" data-dismiss="alert" aria-hidden="true">&nbsp;×</button>'
            + '<i class="fa fa-times-circle sign"></i><strong> ' + response[1] + '</div>';
        } else {
            getEI('error').innerHTML = '<div class="alert alert-success"><button onclick="insertHtml(\'error\',\'&nbsp;\')" type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>'
                        + '<i class="fa fa-times-circle sign"></i><strong> ' + response[1] + '</div>';
            displayElement('loginLink');
        }                     
        displayElement('error');
    };

    /* get the categories from the server */
    send(url, params, callback);
}


/**
 * This function submits the registration info to the server
 * @param {type} formId the id of the form to submit
 * @param {type} url the url to submit to
 */
function register(formId, url){
    
    /* create the callback function that will be called after submitting registration info */
    var callback = function(data){
        var msgString = new String(data);
        var response = msgString.split(",");
        
        if(response[0] == 'ERROR'){
            insertHtml('registerError', '<div class="alert alert-danger"><button onclick="insertHtml(\'registerError\',\'&nbsp;\')" type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>'
                        + '<i class="fa fa-times-circle sign"></i><strong>' + response[1] + '</div>');
        } else {
            insertHtml('registerError', '<div class="alert alert-success"><button onclick="insertHtml(\'registerError\',\'&nbsp;\')" type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>'
                        + '<i class="fa fa-times-circle sign"></i><strong>' + response[1] + '</div>');
        }
        
        $("body").removeClass("loading");
        
    };   
    
    $("body").addClass("loading");
    wrapParams(formId, url, callback, true);
}



/**
 * This function sends the contact details from the
 * contact form to the server and handles the display
 * of success and error messages
 * @returns {undefined}
 */
function sendContact(){     
    var url = 'saveContact.html';
    var callback = function(data){
        var msgStr = new String(data);
        var response = msgStr.split(",");

        if(response[0] == 'ERROR'){
           getEI('error').style.color='red';
        } else {
            getEI('error').style.color='green';
        }         

        var serverHtml = '<b>' + response[1] + '</b>';
        insertHtml('error', serverHtml);
        clearForm('contactForm');
    };

    wrapParams('contactForm', url, callback, true);
}