function goUp() {
    if (parentQueue.length > 1) {
        if (getEI('header-fixed_search')) {
            currentNode = parentQueue.pop();
            var parentNode = parentQueue.pop();
            parentQueue.push(parentNode);
            changeHeader();
        } else {
            currentNode = parentQueue.pop();
            var parentNode = parentQueue.pop();
            parentQueue.push(parentNode);
            if(!currentNode.isSearch){
                changeHeaderBackFromSearch();
            }            
        }

        displayNoSwitch();
    } else {
        if (getEI('header-fixed_search')) {
            currentNode = parentQueue.pop();
            var parentNode = null;
            changeHeaderBack();
        } else {
            currentNode = parentQueue.pop();
            var parentNode = null;
            header = 'mainHeader';
            changeHeaderBackFromSearch();
        }

        displayNoSwitch();
    }
}


function goIn(childIndex) {
    if (currentNode) {
        var parentNode = currentNode;
        parentQueue.push(currentNode);

        if (childIndex.endsWith('_dir')) {
            currentNode = parentNode.directoryChildren[childIndex.replace('_dir', '')];
        } else {
            currentNode = parentNode.fileChildren[childIndex.replace('_file', '')];
        }

        changeHeader();

        displayNoSwitch();
    } else {
        loadingIndex = childIndex;
        jQuery("body").addClass("loading");
    }

}