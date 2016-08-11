/**
 * Requires: utils
 * Dynamically includes: fileinput_locale_<language>.js
 */

var PropertySubmissionForm = {};

PropertySubmissionForm.init = function(maxFileCount, maxFileSize, language, browseLabel) {
    if (language !== 'en') {
        Utils.appendScript(Utils.getPath('/assets/js/fileinput_locale_' + language + '.js'));
    }

    $('#images-upload').fileinput({
        language: language,
        maxFileCount: maxFileCount,
        maxFileSize: maxFileSize,
        showUpload: false,
        showCaption: false,
        showRemove: false,
        browseClass: 'btn btn-default',
        browseLabel: browseLabel
    });
};