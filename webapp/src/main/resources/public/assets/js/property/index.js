/**
 * Requires: utils
 */

var PropertyIndexPage = {};

PropertyIndexPage.init = function(language) {
    $('#form-sidebar').submit(function() {
        var $location = $('#location');
        var location = $location.val();
        $location.prop('disabled', true);

        var $offerType = $('#offerType');
        var offerType = $offerType.val();
        $offerType.prop('disabled', true);

        var $propertyType = $('#propertyType');
        var propertyType = $propertyType.val();
        $propertyType.prop('disabled', true);

        $(this).attr('action', Utils.getPropertySearchPath(language, location, offerType, propertyType));
    });
};