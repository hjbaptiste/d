function openContextMenu(e, childIndex) {
    e.preventDefault;
    buildContextMenu(childIndex);
    slideBottom.open();
}


function buildContextMenu(childIndex){
    if (currentNode) {        
        var selectedNode;
        var shareOrInfoHtml;
        if (childIndex.endsWith('_file')) {
            selectedNode = currentNode.fileChildren[childIndex.replace('_file', '')];
            var shareFileInsert = "javascript: shareFile('" + selectedNode.path + "')";
            var shareableLinkInsert = "javascript: getShareableLink('" + selectedNode.path + "')";
            shareOrInfoHtml = '<a id="contextMenuShareFile" href="' + shareFileInsert +'" class="menuItem"><div class="col-xs-2">'
                + '<i class="fa fa-user-plus" style="text-align: left; line-height: 40px; color: rgba(0,0,0,.6)"></i></div>'
                + '<div class="col-xs-10" style="text-align: left; line-height: 40px; color: rgba(0,0,0,.6)">Share File</div></a>'
                + '<a id="contextMenuShareableLink" href="' + shareableLinkInsert + '" class="menuItem">'
                + '<div class="col-xs-2"><i class="fa fa-link" style="text-align: left; line-height: 40px; color: rgba(0,0,0,.6)"></i>'
                + '</div><div class="col-xs-10" style="text-align: left; line-height: 40px; color: rgba(0,0,0,.6)">Get Shareable Link</div></a>';
        } else {
            selectedNode = currentNode.directoryChildren[childIndex.replace('_dir', '')];
            shareOrInfoHtml = '<div class="col-xs-4" style="padding-left: 25px; padding-right: 0px; margin-right: 0px; text-align: left; font-weight: 500; line-height: 40px; color: rgba(0,0,0,.6)">'
                + 'Date Created:</div><div class="col-xs-8" style="padding-left: 0px; margin-left: 0px; text-align: left; line-height: 40px; color: rgba(0,0,0,.6)">'
                + selectedNode.dtCreated + '</div><div class="col-xs-4" style="padding-left: 25px; padding-right: 0px; margin-right: 0px; text-align: left; font-weight: 500; line-height: 40px; color: rgba(0,0,0,.6)">'
                + 'Date Modified:</div><div class="col-xs-8" style="padding-left: 0px; margin-left: 0; text-align: left; line-height: 40px; color: rgba(0,0,0,.6)">'
                + selectedNode.lastModified + '</div>';            
        }
        
        insertHtml('contextMenuShareOrInfo', shareOrInfoHtml);
        var moveInsert = "javascript: move('" + selectedNode.path + "')";
        var renameInsert = "javascript: openRenameModal('" + selectedNode.path + "', '" + childIndex + "', '" + selectedNode.name + "')";
        var deleteFileInsert = "javascript: deleteFile('" + selectedNode.path + "', '" + childIndex + "')";
        insertHtml('contextMenuFolderName', selectedNode.name);
        $("#contextMenuMove").attr("href", moveInsert);
        $("#contextMenuRename").attr("href", renameInsert);
        $("#contextMenuDelete").attr("href", deleteFileInsert);
    }
}


function shareFile(path){
    var url = 'shareFile.html';
    var params = {path: path};
    var callback = function(jsonReturnObj){
        if(jsonReturnObj.isError){
            // do something
        } else {
            getEI('c-menu__closeBtn').click();
            var shareableLink = jsonReturnObj.successMessage;
            var href = "mailto:email@address.com?subject=Check out this link!&body=Here's the link: " + shareableLink;
            $("#hdnShareLink").attr("href", href); 
            getEI('hdnShareLink').click();
        }
    };
    
    send(url, params, callback);
}

function getShareableLink(path){
    var url = 'shareFile.html';
    var params = {path: path};
    var callback = function(jsonReturnObj){
        if(jsonReturnObj.isError){
            // do something
        } else {
            var shareableLink = jsonReturnObj.successMessage;
            getEI('c-menu__closeBtn').click();
            insertHtml('copyShareLink', shareableLink);
            getEI('displayCopyShareLinkBtn').click();
            highlight(getEI('copyShareLink'));
        }
    };
    
    send(url, params, callback);
}

var fileToMove;
var folderToMoveTo;
function move(path){
    fileToMove = path;
    alert('moving file: ' + path);
}

function openRenameModal(path, childIndex, name){
    var renameField = getEI('renameField');
    getEI('c-menu__closeBtn').click();
    getEI('renameModalButton').click();
    getEI("renameSubmitBtn" ).setAttribute('onclick','rename(\'' + path + '\',\'' + childIndex + '\',\'' + name + '\')');
    renameField.value = name; 
    highlight(renameField);
}


function clickRenameSubmit(){
    getEI("renameSubmitBtn" ).click();
}


function highlight(elem){
    setTimeout(function(){elem.select(); elem.focus();}, 400);
}

function rename(path, childIndex, name){
    var newName = getEIV('renameField');
    var oldPath;
    var node;
    if(newName != name){        
        if (childIndex.endsWith('_dir')) {
            var index = childIndex.replace('_dir', '');
            node = currentNode.directoryChildren[index];
            oldPath = node.path;
            node.name = newName;
            node.path = replaceNodeName(path, name, newName);
            currentNode.directoryChildren[index] = node;
        } else {
            var index = childIndex.replace('_file', '');
            node = currentNode.fileChildren[index];
            oldPath = node.path;
            node.name = newName;
            node.path = replaceNodeName(path, name, newName);
            currentNode.fileChildren[index] = node;
        }
        
        var url = 'renameFile.html';
        var params = {name: name, newName: newName, path: oldPath, newPath: node.path};
        sendNoCallback(url, params);
        displayNoSwitch();
        bubbleChangesToTop();
    }
}


function replaceNodeName(path, name, newName){
    var n = path.lastIndexOf(name);
    if (n >= 0 && n + name.length >= path.length) {
        return path.substring(0, n) + newName;
    }
}

function deleteFile(path, childIndex){
    getEI('c-menu__closeBtn').click();
    if (childIndex.endsWith('_dir')) {
        var index = childIndex.replace('_dir', '');
        currentNode.directoryChildren.splice(index, 1);
    } else {
        var index = childIndex.replace('_file', '');
        currentNode.fileChildren.splice(index, 1);
    }

    var url = 'deleteFile.html';
    var params = {path: path};
    sendNoCallback(url, params);
    displayNoSwitch();
    bubbleChangesToTop();
}



/*
 * This function is called after changes have been made to the current node
 */
function bubbleChangesToTop(){
    var parentQueueContainer = [];
    var currentLevel = currentNode;
    
    for(var i = 0; i < parentQueue.length; i++){
        var parent = parentQueue.pop();
        var directoryChildren = parent.directoryChildren;
        var fileChildren = parent.fileChildren;
        var fileIndex = -1;
        var dirIndex = -1;
        
        if(currentLevel.isDirectory){
            for(var j = 0; j < directoryChildren.length; j++){
                var dirChild = directoryChildren[j];
                if(dirChild.path == currentLevel.path){
                    dirIndex = j;
                    parent.directoryChildren[dirIndex] = currentLevel;
                    parentQueueContainer.push(parent);
                    break;
                }
            }
        }
        
        currentLevel = parent;
    }
    
    for(var m = 0; m < parentQueueContainer.length; i++){
        parentQueue.push(parentQueueContainer.shift());
    }
}