var currentFolder = '';
var parentFolder = '';


function showFolders() {
    var url = 'getFolders.htm';
    var callback = function(data) {
        var parent = data['parentFolder'];
        var results = data['folderList'];
        currentFolder = parent[1][1];
        parentFolder = parent[0][1];        
        if (results != null) {
            addResults(results, true);
            getEI('selectedFolder').value = currentFolder;
        }
    }
    sendFileBrowser(url, '', callback);
}



function diveInto(folderPath) {
    parentFolder = currentFolder;
    currentFolder = folderPath;
    var url = 'diveIntoFolder.htm';
    var params = {folderPath: folderPath};
    var callback = function(data) {
        var results = data;
        if (results != null) {
            insertHtml('dialog', '');
            getEI('selectedFolder').value = currentFolder;
            addResults(results, false);
        }
    }
    sendFileBrowser(url, params, callback);
}



function goBack() {
    if (currentFolder != '') {
        var url = 'goBack.htm';
        var params = {folderPath: currentFolder};
        var callback = function(data) {
            var parent = data['parentFolder'];
            var results = data['folderList'];
            currentFolder = parentFolder;
            parentFolder = parent[0][1];
            getEI('selectedFolder').value = currentFolder;
            if (results != null) {
                insertHtml('dialog', '');
                addResults(results, false);
            }
        }
        sendFileBrowser(url, params, callback);
    }
}


function close() {
    var uiDialog = document.getElementsByClassName('ui-dialog')[0];
    var dialogParent = uiDialog.parentNode;
    dialogParent.removeChild(uiDialog);
    
    // create new html node and add it to the body
    var nodeHtml = '<div id="dialog" title="Browse For Folder" style="margin:0 auto; display: none"></div>';
    var div = document.createElement('div');
    div.innerHTML = nodeHtml;
    var i = div.firstChild;
    document.getElementsByTagName('body')[0].appendChild(i);
}




function addResults(results, init) {
    var toInsert = '<table style="width:460px; padding:0px; margin:0px; border: none"><tr style="width:480px; border:none">';
    var html = '<td style="border:none; width:153px"><table style="padding:0px; margin:0px; border: none"><tr><td style="border:none"><img id=":folderPath" title=":folderName" onclick="getEI(\'selectedFolder\').value=this.id" ondblclick="diveInto(this.id)" src="images/folderIcon.jpg" style="cursor: pointer" width="85" height="63" /></td></tr><tr><td style="text-align:center; padding:0px">:folderName</td></tr></table></td>';
    displayElement('dialog');
    if (init) {
        $("#dialog").dialog({width: 500, height: 400});
        var elems = document.getElementsByTagName('button');
        if (elems && elems.length > 0) {
            for (var k = 0; k < elems.length; k++) {
                var className = elems[k].className;
                if (className.indexOf('ui-button') != -1) {
                    var cancel = elems[k];
                    cancel.innerHTML = '<span class="ui-button-icon-primary ui-icon ui-icon-closethick" onclick="cancel()"></span><span class="ui-button-text">close</span>';
                    break;
                }
            }
        }
    }

    for (var i = 0; i < results.length; i++) {
        toInsert = toInsert + html.replace(":folderPath", results[i][1]).replace(":folderName", results[i][0]).replace(":folderName", results[i][0].substring(0, results[i][0].length < 15 ? results[i][0].length : 15) + (results[i][0].length < 15 ? '' : '...'));
        if ((i + 1) % 3 == 0) {
            toInsert = toInsert + '</tr>';
            if (i < results.length) {
                toInsert = toInsert + '<table style="width:460px; padding:0px; margin:0px; border: none"><tr style="width:480px">';
            }
        }
    }

    if (init) {
        var parent = getEI('dialog').parentNode;
        parent.style.position = 'relative';
        parent.id = 'dialogParent';
        parent.innerHTML = parent.innerHTML + '<div style="width:485px; background-color: #CCCCCC; height: 80px;  position: absolute; border: solid 1px rgb(170, 170, 170); bottom:0px; right:18px; padding:0px; margin:0px;"><img src="images/backBtn.png" onclick="goBack()" class="glow" style="position: absolute; cursor:pointer; bottom:20px; left:10px;" width="40" height="40" /><input id="selectedFolder" class="text glow requiredField" style="border-color: solid thin #545454; width:300px; height: 30px; position: absolute; bottom:23px; left:60px; padding:0px; margin:0px;" /><input type="button" value="OK" style="height: 33px; position: absolute; cursor: pointer; bottom:23px; right:80px;" onclick="selectFolder()" /><input type="button" value="CANCEL" style="height: 33px; position: absolute; cursor: pointer; bottom:23px; right:10px;" onclick="cancel()" />';
    }

    toInsert = toInsert + '<tr></table><br><br><br><br>';
    insertHtml('dialog', toInsert);
    insertHtml('ui-id-1', 'Browse For Folder&nbsp;&nbsp;&nbsp;<font style="font-weight:100">' + (currentFolder.length < 80 ? currentFolder : currentFolder.substring(0, 80) + '...') + '</font>');
}


function selectFolder() {
    var folder = getEIV('selectedFolder');
    close();
    getEI('htmlFileLocation').value = folder;
}


function cancel() {
    close();
}

/*
 * This function makes an ajax call to the server
 * @param {type} url the url to call
 * @param {type} params the parameters to send to the server
 * @param {type} callback the callback function to evoke when the ajax call returns
 */
function sendFileBrowser(url, params, callback) {
    jQuery.ajax({
        dataType: 'json',
        type: "POST",
        url: url,
        data: params
    }).done(function(data) {
        callback(data);
    });
}