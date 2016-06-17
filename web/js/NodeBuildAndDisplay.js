function display() {
    if (!currentNode) {
        displayFormat = 'list';
        jQuery("body").addClass("loading");
    } else {
        if (displayFormat == 'block') {
            displayFilesInListForm();
            displayFormat = 'list';
        } else {
            displayFiles();
            displayFormat = 'block';
        }
    }
}


function displayNoSwitch() {
    if (displayFormat == 'block') {
        displayFiles();
    } else {
        displayFilesInListForm();
    }
}


function displayFiles() {
    var directoryChildren = currentNode.directoryChildren;
    var fileChildren = currentNode.fileChildren;
    buildAndDisplay(directoryChildren, fileChildren, false);
}


function displayFilesInListForm() {
    var directoryChildren = currentNode.directoryChildren;
    var fileChildren = currentNode.fileChildren;
    buildAndDisplayList(directoryChildren, fileChildren, false);
}


function buildAndDisplay(directoryChildren, fileChildren, isSearch) {
    var html = '';

    if (directoryChildren && directoryChildren.length > 0) {
        html = '<div class="row" style="padding:0px; position: relative; top: -25px;"><h5 id="folderHeading" class="col-xs-12" style="padding-left:10px; text-decoration: underline; color: rgba(0,0,0,.6); font-weight: 500; padding-right:3px; padding-bottom:0px;">Folders</h5>';
        for (var i = 0; i < directoryChildren.length; i++) {
            var child = directoryChildren[i];
            var idAnchor = i + '_dir';
            var nameAnchor = idAnchor + '_name';
            html = html + '<div class="col-xs-6" style="padding-left:3px; padding-right:3px; padding-bottom:5px; margin-bottom: 0px">'
                    + '<div class="btn-group col-xs-12" style="margin:0px; padding: 0px;">'
                    + '<button type="button" onclick="goIn(\'' + idAnchor + '\')" class="btn btn-primary col-xs-9" style="height: 42px; text-align: left">'
                    + '<i class="fa fa-folder" style="float: left; position: relative; top: 3px; padding-right: 15px"></i>'
                    + '<span id="' + nameAnchor + '">' + paraphrase(child.name, 14, 7) + '</span>'
                    + '</button><button type="button" class="btn btn-primary col-xs-3 openContext" onclick="openContextMenu(event, \'' + i + '_dir\')" style="height: 42px"><i class="fa fa-ellipsis-v"></i></button></div></div>';
        }

        html = html + '</div><br>';
    }


    if (fileChildren && fileChildren.length > 0) {
        html = html + '<div class="row" style="padding:0px; position: relative; top: -25px;"><h5 id="fileHeading" class="col-xs-12" style="padding-left:10px; text-decoration: underline; color: rgba(0,0,0,.6); font-weight: 500; padding-right:3px; padding-bottom:0px;">Files</h5>';
        for (var i = 0; i < fileChildren.length; i++) {
            var child = fileChildren[i];
            var idAnchor = i + '_file';
            var nameAnchor = idAnchor + '_name';
            html = html + '<div class="col-xs-6" style="padding-left:3px; padding-right:3px; padding-bottom:5px; margin-bottom: 0px"><div class="btn-group col-xs-12" style="margin:0px; padding: 0px;">'
                    + '<button type="button" onclick="getEI(\'' + idAnchor + '\').click();" class="btn btn-primary col-xs-9" style="height: 42px; text-align: left; border-color: rgba(0,0,0,.1); background-color: rgba(0,0,0,.2)">'
                    + '<i class="fa ' + child.extensionIcon[1] + '" style="color: ' + child.extensionIcon[0] + '; float: left; position: relative; top: 3px; padding-right: 15px"></i>'
                    + '<span id="' + nameAnchor + '">' + paraphrase(child.name, 13, 7) + '</span>'
                    + '</button>'
                    + '<button type="button" class="btn btn-primary col-xs-3 openContext" onclick="openContextMenu(event, \'' + i + '_file\')" style="height: 42px; border-color: rgba(0,0,0,.1); background-color: rgba(0,0,0,0)"><i class="fa fa-ellipsis-v" style="color: rgba(0,0,0,.6)"></i></button>'
                    + '<a id="' + idAnchor + '" href="' + child.path + '" download style="visibility: hidden; height: 0px; width: 0px;"></a></div></div>';
        }

        html = html + '</div>';
    }



    if (html != null && html != '') {
        insertHtml('fileContainer', html);
    } else {
        if (isSearch) {
            html = '<div class="row" style="padding:0px; width: 100%; height: 300px; position: relative; top: 25px; text-align:center"><h3 id="folderHeading" class="col-xs-12" style="padding-left:10px; text-decoration: underline; color: rgba(0,0,0,.6); font-weight: 500; padding-right:3px; padding-bottom:0px;">No Search Results</h3>';
        } else {
            html = '<div style="width: 100%; height: 300px;">&nbsp;</div>';
        }

        insertHtml('fileContainer', html);
    }
}



function buildAndDisplayList(directoryChildren, fileChildren, isSearch) {
    var html = '';

    if (directoryChildren && directoryChildren.length > 0) {
        html = '<div class="row" style="padding:0px; position: relative; top: -25px;"><h5 id="folderHeading" class="col-xs-12" style="padding-left:10px; text-decoration: underline; color: rgba(0,0,0,.6); font-weight: 500; padding-right:3px; padding-bottom:0px;">Folders</h5>';
        for (var i = 0; i < directoryChildren.length; i++) {
            var child = directoryChildren[i];
            var idAnchor = i + '_dir';
            var nameAnchor = idAnchor + '_name';
            html = html + '<div class="col-xs-12" style="padding-left:3px; padding-right:3px; padding-bottom:2px; margin-bottom: 0px">'
                    + '<div class="btn-group col-xs-12" style="margin:0px; padding: 0px;">'
                    + '<button type="button" onclick="goIn(\'' + idAnchor + '\')" class="btn btn-primary col-xs-9" style="height: 55px; text-align: left">'
                    + '<i class="fa fa-folder" style="float: left; position: relative; top: 3px; padding-right: 15px"></i>'
                    + '<span id="' + nameAnchor + '">' + paraphrase(child.name, 27, 21) + '</span>'
                    + '</button><button type="button" class="btn btn-primary col-xs-3 openContext" onclick="openContextMenu(event, \'' + i + '_dir\')" style="height: 55px"><i class="fa fa-ellipsis-v"></i></button></div></div>';
        }

        html = html + '</div><br>';
    }

    if (fileChildren && fileChildren.length > 0) {
        html = html + '<div class="row" style="padding:0px; position: relative; top: -25px;"><h5 id="fileHeading" class="col-xs-12" style="padding-left:10px; text-decoration: underline; color: rgba(0,0,0,.6); font-weight: 500; padding-right:3px; padding-bottom:0px;">Files</h5>';
        for (var i = 0; i < fileChildren.length; i++) {
            var child = fileChildren[i];
            var idAnchor = i + '_file';
            var nameAnchor = idAnchor + '_name';
            html = html + '<div class="col-xs-12" style="padding-left:3px; padding-right:3px; padding-bottom:2px; margin-bottom: 0px"><div class="btn-group col-xs-12" style="margin:0px; padding: 0px;">'
                    + '<button type="button" onclick="getEI(\'' + idAnchor + '\').click();" class="btn btn-primary col-xs-9" style="height: 55px; text-align: left; border-color: rgba(0,0,0,.1); background-color: rgba(0,0,0,.2)">'
                    + '<i class="fa ' + child.extensionIcon[1] + '" style="color: ' + child.extensionIcon[0] + '; float: left; position: relative; top: 3px; padding-right: 15px"></i>'
                    + '<span id="' + nameAnchor + '">' + paraphrase(child.name, 27, 21) + '</span>'
                    + '</button>'
                    + '<button type="button" class="btn btn-primary col-xs-3 openContext" onclick="openContextMenu(event, \'' + i + '_file\')" style="height: 55px; border-color: rgba(0,0,0,.1); background-color: rgba(0,0,0,0)"><i class="fa fa-ellipsis-v" style="color: rgba(0,0,0,.6)"></i></button>'
                    + '<a id="' + idAnchor + '" href="' + child.path + '" download style="visibility: hidden; height: 0px; width: 0px;"></a></div></div>';
        }

        html = html + '</div>';
    }

    if (html != null && html != '') {
        insertHtml('fileContainer', html);
    } else {
        if (isSearch) {
            html = '<div class="row" style="padding:0px; width: 100%; height: 300px; position: relative; top: 25px; text-align:center"><h3 id="folderHeading" class="col-xs-12" style="padding-left:10px; text-decoration: underline; color: rgba(0,0,0,.6); font-weight: 500; padding-right:3px; padding-bottom:0px;">No Search Results</h3>';
        } else {
            html = '<div style="width: 100%; height: 300px;">&nbsp;</div>';
        }

        insertHtml('fileContainer', html);
    }
}



function paraphrase(name, maxLength, cutOff) {
    if (name && name.length > maxLength) {
        return name.substring(0, cutOff) + '...' + name.substring(name.length - 8, name.length);
    }

    return name;
}