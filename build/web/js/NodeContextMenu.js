function openContextMenu(e, childIndex) {
    e.preventDefault;
    
    // replace context menu html if it has been removed by add button
    if(saveMenuHtml && saveMenuHtml.length > 0){
        insertHtml('c-menu--slide-bottom', saveMenuHtml);
    }
    
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
            slideBottom.close();
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
            slideBottom.close();
            insertHtml('copyShareLink', shareableLink);
            getEI('displayCopyShareLinkBtn').click();
            highlight(getEI('copyShareLink'));
        }
    };
    
    send(url, params, callback);
}


function move(path){
    var url = 'selectMoveDir.html';
    var nodeMover = {parentQueue: parentQueue, currentNode: currentNode, flatList: flatList};
    var params = {path: path, nodeMover: JSON.stringify(nodeMover)};
    var callback = function(){
        window.location.href = 'displayMove.html';
    };
    
    send(url, params, callback);
 
    
}

function openRenameModal(path, childIndex, name){
    var renameField = getEI('renameField');
    slideBottom.close();
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
    var validationHtml = '';
    var hasInvalidChars = newName.indexOf("\\") == -1;
    hasInvalidChars = hasInvalidChars && newName.indexOf("/") == -1;
    hasInvalidChars = hasInvalidChars && newName.indexOf("+") == -1;
    hasInvalidChars = hasInvalidChars && newName.indexOf(":") == -1;
    hasInvalidChars = hasInvalidChars && newName.indexOf("*") == -1;
    hasInvalidChars = hasInvalidChars && newName.indexOf("?") == -1;
    hasInvalidChars = hasInvalidChars && newName.indexOf("<") == -1;
    hasInvalidChars = hasInvalidChars && newName.indexOf(">") == -1;
    hasInvalidChars = hasInvalidChars && newName.indexOf("|") == -1;
     
    if(newName == name){
        validationHtml = '<div style="text-align: center; position: relative; top: 15px" class="alert alert-danger"><strong>Error: </strong> Please enter a new file name. </div>';
        displayMessageToScreen(validationHtml);
    } else if(!newName || newName.length == 0){
        validationHtml = '<div style="text-align: center; position: relative; top: 15px" class="alert alert-danger"><strong>Error: </strong> Please enter a valid file name. </div>';
        displayMessageToScreen(validationHtml);
    } else if (!hasInvalidChars){
        validationHtml = '<div style="text-align: center; position: relative; top: 15px" class="alert alert-danger"><strong>Error: </strong> Illegal characters in folder name (\, /, +, :, *, ?, <, >, |). </div>';
        displayMessageToScreen(validationHtml);
    } else {        
        if (childIndex.endsWith('_dir')) {      
            if(!isDuplicateName(newName, currentNode.directoryChildren)){
                var index = childIndex.replace('_dir', '');
                node = currentNode.directoryChildren[index];
                oldPath = node.path;
                node.name = newName;
                node.path = replaceNodeName(path, name, newName);
                currentNode.directoryChildren[index] = node;
            } else {
                validationHtml = '<div style="text-align: center; position: relative; top: 15px" class="alert alert-danger"><strong>Error: </strong> There is already a folder with this name. </div>';
                displayMessageToScreen(validationHtml);
            }            
        } else {
            if(!isDuplicateName(newName, currentNode.directoryChildren)){
                var index = childIndex.replace('_file', '');
                node = currentNode.fileChildren[index];
                oldPath = node.path;
                node.name = newName;
                node.path = replaceNodeName(path, name, newName);
                currentNode.fileChildren[index] = node;
            } else {
                validationHtml = '<div style="text-align: center; position: relative; top: 15px" class="alert alert-danger"><strong>Error: </strong> There is already a folder with this name. </div>';
                displayMessageToScreen(validationHtml);
            }            
        }
        
        var url = 'renameFile.html';
        var params = {name: name, newName: newName, path: oldPath, newPath: node.path};
        sendNoCallback(url, params);
        displayNoSwitch();
        bubbleChangesToTop();
    }
}


function isDuplicateName(name, children){
    if(children.length == 0){
        return false;
    }
    
    if(name && children){
        for(var i = 0; i < children.length; i++){
            var node = children[i];
            if(name == node.name){
                return true;
            }
        }
    }
    
    return false;
}


function replaceNodeName(path, name, newName){
    var n = path.lastIndexOf(name);
    if (n >= 0 && n + name.length >= path.length) {
        return path.substring(0, n) + newName;
    }
}

function deleteFile(path, childIndex){
    slideBottom.close();
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



function uploadFile(id) {
    var file = getEI(id).files[0];
    name = file.name;
    size = file.size;
    type = file.type;

    if (file.name.length < 1 || file.size == 0) {
        var validationHtml = '<div style="text-align: center; position: relative; top: 15px" class="alert alert-danger"><strong>Error: </strong> Please select a valid file. </div>';
        displayMessageToScreen(validationHtml);
    } else {
        jQuery("body").addClass("loading");
        slideBottom.close();
        var formData = new FormData();
        formData.append("userfile", file);
        formData.append("path", currentNode.path);

        var callback = function(node) {
            validationHtml = '<div style="text-align: center; position: relative; top: 15px" class="alert alert-danger"><strong>Oh no!</strong> Something went wrong :( </div>';
            if (node) {
                if (node.path.length == 0) {
                    validationHtml = '<div style="text-align: center; position: relative; top: 15px" class="alert alert-danger"><strong>Oh no!</strong> File upload failed.</div>';
                } else {
                    validationHtml = '<div style="text-align: center;" class="alert alert-success"><strong>Success!</strong> File uploaded successfully.</div>';
                    // add new node to current node and bubble up changes            
                    currentNode.fileChildren[currentNode.fileChildren.length] = node;
                    currentNode.numFiles++;
                    displayNoSwitch();
                    bubbleChangesToTop();
                }
            }
            
            // display message to screen            
            displayMessageToScreen(validationHtml);
        };

        $.ajax({
            url: 'uploadFile.html',
            data: formData,
            type: 'POST',
            contentType: false,
            processData: false,
            cache: false,
            dataType: 'json'
        }).done(function(jsonReturnObj) {
            callback(jsonReturnObj);
        });
    }
}



var saveMenuHtml;
var isAddMenu = false;
function openAddMenu(e){
    saveMenuHtml = getEI('c-menu--slide-bottom').innerHTML;
    insertHtml('c-menu--slide-bottom', getEI('addBtnHtml').innerHTML);
    e.preventDefault;
    isAddMenu = true;
    slideBottom.open();
}



function openNameFolderModal(){
    var nameFolderField = getEI('nameFolderField');
    slideBottom.close();
    getEI('nameFolderModalButton').click();
    nameFolderField.value = 'NEW FOLDER'; 
    highlight(nameFolderField);
}


function createFolder(){
    getEI('nameFolderCloseBtn').click();
    slideBottom.close();
    var newFolderName = getEIV('nameFolderField');
    var validationHtml = '';
    
    var hasInvalidChars = newFolderName.indexOf("\\") == -1;
    hasInvalidChars = hasInvalidChars && newFolderName.indexOf("/") == -1;
    hasInvalidChars = hasInvalidChars && newFolderName.indexOf("+") == -1;
    hasInvalidChars = hasInvalidChars && newFolderName.indexOf(":") == -1;
    hasInvalidChars = hasInvalidChars && newFolderName.indexOf("*") == -1;
    hasInvalidChars = hasInvalidChars && newFolderName.indexOf("?") == -1;
    hasInvalidChars = hasInvalidChars && newFolderName.indexOf("<") == -1;
    hasInvalidChars = hasInvalidChars && newFolderName.indexOf(">") == -1;
    hasInvalidChars = hasInvalidChars && newFolderName.indexOf("|") == -1;
                
    if(!newFolderName || newFolderName.length == 0){
        validationHtml = '<div style="text-align: center; position: relative; top: 15px" class="alert alert-danger"><strong>Error: </strong> Invalid folder name. </div>';
        displayMessageToScreen(validationHtml);
    } else if (!hasInvalidChars){
        validationHtml = '<div style="text-align: center; position: relative; top: 15px" class="alert alert-danger"><strong>Error: </strong> Illegal characters in folder name (\, /, +, :, *, ?, <, >, |). </div>';
        displayMessageToScreen(validationHtml);
    } else {
        if(!isDuplicateName(newFolderName, currentNode.directoryChildren)){
            var url = 'createNewFolder.html';
            var path = currentNode.path;
            var params = {path: path, newFolderName: newFolderName};
            var callback = function(node){
                if(!node || node.path.length == 0){
                    validationHtml = '<div style="text-align: center; position: relative; top: 15px" class="alert alert-danger"><strong>Oh no! </strong> We ran into an error creating the folder. </div>';
                    displayMessageToScreen(validationHtml);
                } else {
                    validationHtml = '<div style="text-align: center;" class="alert alert-success"><strong>Success!</strong> Folder created.</div>';
                    // add new node to current node and bubble up changes            
                    currentNode.directoryChildren[currentNode.directoryChildren.length] = node;
                    displayNoSwitch();
                    bubbleChangesToTop();
                }
                displayMessageToScreen(validationHtml);
            };

            send(url, params, callback);
        } else {
            validationHtml = '<div style="text-align: center; position: relative; top: 15px" class="alert alert-danger"><strong>Error: </strong> Folder already exist with that name. </div>';
            displayMessageToScreen(validationHtml);
        }        
    }
}
    
    
    

function closeAddMenu(){
    insertHtml('c-menu--slide-bottom', saveMenuHtml);
}



function displayMessageToScreen(html){
    insertHtml('error', html);
    jQuery("body").removeClass("loading");

    $('#error').fadeIn('slow', function() {
        $(this).delay(4000).fadeOut('slow');
    });
}