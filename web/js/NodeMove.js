function displayFilesMove() {
    var directoryChildren = moveCurrentNode.directoryChildren;
    
    if(moveParentQueue.length == 0){
        insertHtml('moveHeaderName', 'Home Cloud Drive');
    } else {
        insertHtml('moveHeaderName', paraphrase(moveCurrentNode.name, 18, 8));
    }
    
    buildAndDisplayMove(directoryChildren);
}



function buildAndDisplayMove(directoryChildren) {
    var html = '';

    if (directoryChildren && directoryChildren.length > 0) {
        html = '<div class="row" style="padding:0px; position: relative; top: -25px;">';
        for (var i = 0; i < directoryChildren.length; i++) {
            var child = directoryChildren[i];
            var idAnchor = i + '_dir';
            html = html + '<div class="col-xs-12" style="height: 80px; padding-bottom:0px; margin-bottom: 0px; border-bottom: solid 1px rgba(0,0,0,.1)">'
                    + '<a href="javascript: goInMove(\'' + idAnchor + '\')" class="menuItem">'
                    + '<div class="col-xs-2"><i class="fa fa-folder fa-2x" style="position: relative; top: 20px; text-align: left; line-height: 40px; color: rgba(0,0,0,.6)"></i></div>'
                    + '<div class="col-xs-10" style="text-align: left; positition: relative; left: -15px; line-height: 40px; color: rgba(0,0,0,.6)">'
                    + '<div class="col-xs-12" style="text-align: left; position: relative; top: 8px; padding-bottom:0px; margin-bottom: 0px; font-weight: 600">'
                    + paraphrase(child.name, 35, 25) + '</div><div class="col-xs-12" style="text-align: left; position: relative; top: -8px; padding-bottom:0px; margin-bottom: 0px">'
                    + 'Modified: ' + child.lastModified + '</div></div></a></div>';
        }
                      
            
        html = html + '</div><br><br><br>';
    }

    if (html != null && html != '') {
        insertHtml('fileContainer', html);
    } else {
        html = '<div style="width: 100%; height: 300px;">&nbsp;</div>';
        insertHtml('fileContainer', html);
    }
}



function paraphrase(name, maxLength, cutOff) {
    if (name && name.length > maxLength) {
        return name.substring(0, cutOff) + '...' + name.substring(name.length - 8, name.length);
    }

    return name;
}


function cancelOrActivateMoveLink(){
    var parent = parentQueue.pop();
    
    if(parent.path == parentPath){        
        jQuery("#moveLink").addClass("inactivate");
    } else {
        jQuery("#moveLink").removeClass("inactivate");
        jQuery("#moveLink").addClass("activate");
    }
    
    parentQueue.push(parent);
}



function goUpMove() {
    if (moveParentQueue.length > 1) {
        moveCurrentNode = moveParentQueue.pop();
        var parentNode = moveParentQueue.pop();
        moveParentQueue.push(parentNode);
        displayFilesMove();
    } else {
        moveCurrentNode = moveParentQueue.pop();
        var parentNode = null;
        displayFilesMove();
        hideElement('goUpMoveBtn');
    }
    cancelOrActivateMoveLink();
}


function goInMove(childIndex) {
    if (moveCurrentNode) {
        var parentNode = moveCurrentNode;
        moveParentQueue.push(moveCurrentNode);

        if (childIndex.endsWith('_dir')) {
            moveCurrentNode = parentNode.directoryChildren[childIndex.replace('_dir', '')];
        } 
        
        displayFilesMove();
        displayElement('goUpMoveBtn');
    } 
    cancelOrActivateMoveLink();
}



function cancel(){
    var url = 'displayDirs.html';
    window.location.href = url;
}


function moveFileToFolder(){
    getEI('folderToMoveTo').value = moveCurrentNode.path;
    getEI('folderToMoveToForm').submit();
    
}