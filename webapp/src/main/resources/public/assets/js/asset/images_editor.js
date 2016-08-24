ImagesEditor = {};

ImagesEditor.init = function() {
    var $imageSelect = $(".image-select");
    if ($imageSelect.length > 0) {
        ImagesEditor.withSelect = true;

        $(".image-select button").click(function(e) {
            $(this).toggleClass("btn-success");
            $(this).parent(".image-select").parent(".image").toggleClass("selected");
            e.preventDefault();
        });
    }

    var $imageRemove = $(".image-remove");
    if ($imageRemove.length > 0) {
        $(".image-remove a").click(function(e) {
            var $image = $(this).parent(".image-remove").parent(".image");
            $image.toggleClass("removed");
            $image.removeClass("selected");

            if (ImagesEditor.withSelect) {
                $image.children(".image-select").children("button").removeClass("btn-success");
            }

            var imageId = $image.attr("id");
            var imageIdVal = imageId.substring(6, imageId.length - 1);
            var $inputRemove = $image.children("#imagesState" + imageIdVal + "\\.removed");
            $inputRemove.val($inputRemove.val() == "false" ? "true" : "false");

            e.preventDefault();
        });
    }

    $("#images-editor").magnificPopup({
        delegate: 'a.image-popup',
        type: 'image',
        gallery: {
            enabled: true,
            navigateByImgClick: true,
            preload: [0,1]
        },
        overflowY: 'scroll'
    });
}