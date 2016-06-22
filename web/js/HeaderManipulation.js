function changeHeader() {
    if (getEI('header-fixed_bk')) {
        insertHtml('currentFolder', paraphrase(currentNode.name, 17, 12));
    } else {
        insertHtml('currentFolder', paraphrase(currentNode.name, 17, 12));
        getEI('header-fixed').id = 'header-fixed_bk';
        getEI('header-fixed_dir').id = 'header-fixed';
        displayElement('header-fixed');
    }

    header = 'dirHeader';
}


function changeHeaderBack() {
    getEI('header-fixed').id = 'header-fixed_dir';
    getEI('header-fixed_bk').id = 'header-fixed';
    displayElement('header-fixed');
    hideElement('header-fixed_dir');

    header = 'mainHeader';
}


function changeHeaderBackFromSearch() {
    if (header == 'mainHeader') {
        getEI('header-fixed').id = 'header-fixed_search';
        getEI('header-fixed_bk').id = 'header-fixed';
        displayElement('header-fixed');
        hideElement('header-fixed_search');
    } else {
        getEI('header-fixed').id = 'header-fixed_search';
        getEI('header-fixed_dir').id = 'header-fixed';
        displayElement('header-fixed');
        hideElement('header-fixed_dir');
    }

    getEI('searchInput').value = '';
    displayFormat = formatBeforeSearch;
    isSearch = false;
}


function changeToSearchHeader() {
    if (getEI('header-fixed_bk')) {
        getEI('header-fixed').id = 'header-fixed_dir';
        getEI('header-fixed_search').id = 'header-fixed';
        displayElement('header-fixed');
        hideElement('header-fixed_dir');
        getEI('searchInput').focus();
    } else {
        getEI('header-fixed').id = 'header-fixed_bk';
        getEI('header-fixed_search').id = 'header-fixed';
        displayElement('header-fixed');
        hideElement('header-fixed_bk');
        getEI('searchInput').focus();
    }

    insertHtml('fileContainer', '<div style="width: 100%; height: 300px;">&nbsp;</div>');
}



function changeOrderIcon() {
    var elems = document.getElementsByClassName('reorder');
    if (elems && elems.length > 0) {
        for (var i = 0; i < elems.length; i++) {
            var elem = elems[i];
            var elemClass = new String(elem.className);
            if (elem.className.indexOf('fa-list') > -1) {
                elem.className = elemClass.replace('fa-list', 'fa-th-large');
            } else {
                elem.className = elemClass.replace('fa-th-large', 'fa-list');
            }
        }
    }
}