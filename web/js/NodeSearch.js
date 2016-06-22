function SearchNode() {
    var name;
    var isDirectory = true;
    var isFile = false;
    var isSearch = true;
    var fileChildren;
    var directoryChildren;
    var numFiles;
    var numDirectories;
}

var isSearch = false;
function search() {
    var searchTerm = new String(getEIV('searchInput')).trim();    
    
    if (searchTerm.length > 0) {
        var results = fuseSearcher.search(searchTerm);
        filterSearchResults(results, searchTerm);
    }
    if(!isSearch){
        formatBeforeSearch = displayFormat;
        displayFormat = 'list';
        isSearch = true;
    }
}


function filterSearchResults(results, searchTerm) {
    var filteredDirectoryResults = [];
    var filteredFileResults = [];
    var dupeList = new Array();
    
    if (results && results.length > 0) {
        for (var i = 0; i < results.length; i++) {
            var node = results[i];
            var name = node.name.toLowerCase();
            var extension = node.extension.toLowerCase();
            searchTerm = searchTerm.toLowerCase();
            if (name.indexOf(searchTerm) > -1 || extension.indexOf(searchTerm) > -1) {
                if(jQuery.inArray(node.path, dupeList) == -1){
                    if (node.isDirectory) {
                        filteredDirectoryResults.push(node);
                    } else {
                        filteredFileResults.push(node);
                    }
                    dupeList.push(node.path);
                }
            }
        }

        var searchNode = createSearchNode(filteredDirectoryResults, filteredFileResults, searchTerm);
        manageParentQueue(searchNode);
        buildAndDisplayList(searchNode.directoryChildren, searchNode.fileChildren, true);
    }
}


function manageParentQueue(searchNode){
    parentQueue.push(currentNode);
    currentNode = searchNode;
}


function createSearchNode(filteredDirectoryResults, filteredFileResults, searchTerm) {
    var searchNode = new SearchNode;
    searchNode.name = searchTerm;
    searchNode.fileChildren = filteredFileResults;
    searchNode.directoryChildren = filteredDirectoryResults;
    searchNode.numFiles = filteredFileResults.length;
    searchNode.numDirectories = filteredDirectoryResults.length;
    searchNode.isSearch = true;
    
    return searchNode;
}