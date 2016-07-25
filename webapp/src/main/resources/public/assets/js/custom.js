////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// jQuery
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

$(document).ready(function($) {
    "use strict";

    equalHeight('.equal-height');

    $('.nav > li > ul li > ul').css('left', $('.nav > li > ul').width());

    var navigationLi = $('.nav > li');
    navigationLi.hover(function () {
        if ($('body').hasClass('navigation-fixed-bottom')) {
            if ($(window).width() > 768) {
                var spaceUnderNavigation = $(window).height() - ($(this).offset().top - $(window).scrollTop());
                if (spaceUnderNavigation < $(this).children('.child-navigation').height()) {
                    $(this).children('.child-navigation').addClass('position-bottom');
                } else {
                    $(this).children('.child-navigation').removeClass('position-bottom');
                }
            }
        }
    });

    setNavigationPosition();

    $('.tool-tip').tooltip();

    var select = $('select');
    if (select.length > 0) {
        select.selectpicker();
    }

    var bootstrapSelect = $('.bootstrap-select');
    var dropDownMenu = $('.dropdown-menu');

    bootstrapSelect.on('shown.bs.dropdown', function () {
        dropDownMenu.removeClass('animation-fade-out');
        dropDownMenu.addClass('animation-fade-in');
    });

    bootstrapSelect.on('hide.bs.dropdown', function () {
        dropDownMenu.removeClass('animation-fade-in');
        dropDownMenu.addClass('animation-fade-out');
    });

    bootstrapSelect.on('hidden.bs.dropdown', function () {
        var _this = $(this);
        $(_this).addClass('open');
        setTimeout(function () {
            $(_this).removeClass('open');
        }, 100);
    });

    select.change(function () {
        if ($(this).val() != '') {
            $('.form-search .bootstrap-select.open').addClass('selected-option-check');
        } else {
            $('.form-search  .bootstrap-select.open').removeClass('selected-option-check');
        }
    });

//  Contact form

    $("#form-contact-submit").bind("click", function (event) {
        $("#form-contact").validate({
            submitHandler: function () {
                $.post("http://assets.gotocy.com/static/php/contact.php", $("#form-contact").serialize(), function (response) {
                    $('#form-status').html(response);
                    $('#form-contact-submit').attr('disabled', 'true');
                });
                return false;
            }
        });
    });

//  Fit videos width and height

    if ($(".video").length > 0) {
        $(".video").fitVids();
    }

//  Price slider

    var $priceSlider = $("#price-input");
    if ($priceSlider.length > 0) {
        $priceSlider.slider({
            from: 100,
            to: 5000000,
            step: 100,
            heterogeneity: ['30/5000', '60/500000'],
            format: {format: '###,###', locale: 'en'},
            dimension: '&nbsp;€'
        });
    }

//  Parallax scrolling and fixed header after scroll

    $('#map .marker-style').css('opacity', '.5 !important');
    $('#map .marker-style').css('bakground-color', 'red');

    $(window).scroll(function () {
        var scrollAmount = $(window).scrollTop() / 1.5;
        scrollAmount = Math.round(scrollAmount);
        if ($("body").hasClass("navigation-fixed-bottom")) {
            if ($(window).scrollTop() > $(window).height() - $('.navigation').height()) {
                $('.navigation').addClass('navigation-fix-to-top');
            } else {
                $('.navigation').removeClass('navigation-fix-to-top');
            }
        }

        if ($(window).width() > 768) {
            if ($('#map').hasClass('has-parallax')) {
                //$('#map .gm-style > div:first-child > div:first-child').css('margin-top', scrollAmount + 'px'); // old script
                $('#map .gm-style').css('margin-top', scrollAmount + 'px');
                $('#map .leaflet-map-pane').css('margin-top', scrollAmount + 'px');
            }
            if ($('#slider').hasClass('has-parallax')) {
                $(".homepage-slider").css('top', scrollAmount + 'px');
            }
        }
    });

//  Smooth Navigation Scrolling

    $('.navigation .nav a[href^="#"], a[href^="#"].roll').on('click', function (e) {
        e.preventDefault();
        var target = this.hash,
            $target = $(target);
        if ($(window).width() > 768) {
            $('html, body').stop().animate({
                'scrollTop': $target.offset().top - $('.navigation').height()
            }, 2000)
        } else {
            $('html, body').stop().animate({
                'scrollTop': $target.offset().top
            }, 2000)
        }
    });

//  Rating

    var ratingOverall = $('.rating-overall');
    if (ratingOverall.length > 0) {
        ratingOverall.raty({
            path: 'http://assets.gotocy.com/static/img',
            readOnly: true,
            score: function () {
                return $(this).attr('data-score');
            }
        });
    }
    var ratingIndividual = $('.rating-individual');
    if (ratingIndividual.length > 0) {
        ratingIndividual.raty({
            path: 'http://assets.gotocy.com/static/img',
            readOnly: true,
            score: function () {
                return $(this).attr('data-score');
            }
        });
    }
    var ratingUser = $('.rating-user');
    if (ratingUser.length > 0) {
        $('.rating-user .inner').raty({
            path: 'http://assets.gotocy.com/static/img',
            starOff: 'big-star-off.png',
            starOn: 'big-star-on.png',
            width: 150,
            //target : '#hint',
            targetType: 'number',
            targetFormat: 'Rating: {score}',
            click: function (score, evt) {
                showRatingForm();
            }
        });
    }

//  Agent State

    $('#agent-switch').on('ifClicked', function (event) {
        agentState();
    });

    $('#create-account-user').on('ifClicked', function (event) {
        $('#agent-switch').data('agent-state', '');
        agentState();
    });

// Set Bookmark button attribute

    var bookmarkButton = $(".bookmark");

    if (bookmarkButton.data('bookmark-state') == 'empty') {
        bookmarkButton.removeClass('bookmark-added');
    } else if (bookmarkButton.data('bookmark-state') == 'added') {
        bookmarkButton.addClass('bookmark-added');
    }

    bookmarkButton.on("click", function () {
        if (bookmarkButton.data('bookmark-state') == 'empty') {
            bookmarkButton.data('bookmark-state', 'added');
            bookmarkButton.addClass('bookmark-added');
        } else if (bookmarkButton.data('bookmark-state') == 'added') {
            bookmarkButton.data('bookmark-state', 'empty');
            bookmarkButton.removeClass('bookmark-added');
        }
    });

    if ($('body').hasClass('navigation-fixed-bottom')) {
        $('#page-content').css('padding-top', $('.navigation').height());
    }

//  Masonry grid listing

    if ($('.property').hasClass('masonry')) {
        var container = $('.masonry-grid');
        container.imagesLoaded(function () {
            container.masonry({
                gutter: 15,
                itemSelector: '.masonry'
            });
        });
        if ($(window).width() > 991) {
            $('.masonry').hover(function () {
                    $('.masonry').each(function () {
                        $('.masonry').addClass('masonry-hide-other');
                        $(this).removeClass('masonry-show');
                    });
                    $(this).addClass('masonry-show');
                }, function () {
                    $('.masonry').each(function () {
                        $('.masonry').removeClass('masonry-hide-other');
                    });
                }
            );

            var config = {
                after: '0s',
                enter: 'bottom',
                move: '20px',
                over: '.5s',
                easing: 'ease-out',
                viewportFactor: 0.33,
                reset: false,
                init: true
            };
            window.scrollReveal = new scrollReveal(config);
        }
    }


// Property/Show Pano Popup

    var panoPopupContainer = $('#pano-popup-container');
    if (panoPopupContainer.length > 0) {
        panoPopupContainer.magnificPopup({
            items: [
                {
                    src: '#pano-popup',
                    type: 'inline'
                }
            ],
            key:'pano',
            midClick: true,
            callbacks: {
                open: function() {
                    initializePano(panoConfigUrl);
                }
            },
            closeBtnInside: false,
            closeOnBgClick: true,
            overflowY: 'scroll'
        });
    }

// Property/Show Gallery Popup

    var galleryPopupContainer = $('#gallery-popup-container');
    if (galleryPopupContainer.length > 0) {
        galleryPopupContainer.magnificPopup({
            items: images.map(function(url) {return {src: url}}),
            gallery: {
                enabled: true
            },
            type: 'image',
            overflowY: 'scroll'
        });
    }

//  iCheck

    if ($('.checkbox').length > 0) {
        $('input').iCheck();
    }

    if ($('.radio').length > 0) {
        $('input').iCheck();
    }

//  Pricing Tables in Submit page

    if($('.submit-pricing').length >0 ){
        $('.btn').click(function() {
                $('.submit-pricing .buttons td').each(function () {
                    $(this).removeClass('package-selected');
                });
                $(this).parent().css('opacity','1');
                $(this).parent().addClass('package-selected');

            }
        );
    }

});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// On RESIZE
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


$(window).on('resize', function(){
    setNavigationPosition();
    equalHeight('.equal-height');
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// On LOAD
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

$(window).load(function(){

//  Show Search Box on Map

    $('.search-box.map').addClass('show-search-box');

//  Show All button

    showAllButton();

//  Show counter after appear

    var $number = $('.number');
    if ($number.length > 0 ) {
        $number.waypoint(function() {
            initCounter();
        }, { offset: '100%' });
    }

    agentState();
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Functions
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

// Pano

function initializePano(panoConfigUrl) {
    function hideUrlBar() {
    }

    pano = $('#pano');

    if (pano.length > 0) {
        pano.css('max-height', '636px');

        // left a llittle bit space for url bars, etc
        pano.css('height', ($(window).height() - 40) + 'px');
        pano.css('width', 'inherit');

        // create the panorama player with the container
        player = new pano2vrPlayer('pano');
        // add the skin object
        skin = new pano2vrSkin(player, 'http://assets.gotocy.com/static/pano2vr/');
        // load the configuration
        player.readConfigUrl(panoConfigUrl);
        // hide the URL bar on the iPhone
        setTimeout(function () {
            hideUrlBar();
        }, 10);
    }
}

function setNavigationPosition(){
    $('.nav > li').each(function () {
        if($(this).hasClass('has-child')){
            var fullNavigationWidth = $(this).children('.child-navigation').width() + $(this).children('.child-navigation').children('li').children('.child-navigation').width();
            if(($(this).children('.child-navigation').offset().left + fullNavigationWidth) > $(window).width()){
                $(this).children('.child-navigation').addClass('navigation-to-left');
            }
        }
    });
}

// Agent state - Fired when user change the state if he is agent or doesn't

function agentState(){
    var _originalHeight = $('#agency .form-group').height();
    var $agentSwitch = $('#agent-switch');
    var $agency = $('#agency');

    if ($agentSwitch.data('agent-state') == 'is-agent') {
        $agentSwitch.iCheck('check');
        $agency.removeClass('disabled');
        $agency.addClass('enabled');
        $agentSwitch.data('agent-state', '');
    } else {
        $agentSwitch.data('agent-state', 'is-agent');
        $agency.removeClass('enabled');
        $agency.addClass('disabled');
    }
}

function initCounter(){
    $('.number').countTo({
        speed: 3000,
        refreshInterval: 50
    });
}

function showAllButton(){
    var rowsToShow = 2; // number of collapsed rows to show
    var $layoutExpandable = $('.layout-expandable');
    var layoutHeightOriginal = $layoutExpandable.height();
    $layoutExpandable.height($('.layout-expandable .row').height()*rowsToShow-5);
    $('.show-all').on("click", function() {
        if ($layoutExpandable.hasClass('layout-expanded')) {
            $layoutExpandable.height($('.layout-expandable .row').height()*rowsToShow-5);
            $layoutExpandable.removeClass('layout-expanded');
            $('.show-all').removeClass('layout-expanded');
        } else {
            $layoutExpandable.height(layoutHeightOriginal);
            $layoutExpandable.addClass('layout-expanded');
            $('.show-all').addClass('layout-expanded');
        }
    });

}

// Show rating form

function showRatingForm(){
    $('.rating-form').css('height', $('.rating-form form').height() + 85 + 'px');
}

//  Equal heights

function equalHeight(container){

    var currentTallest = 0,
        currentRowStart = 0,
        rowDivs = new Array(),
        $el,
        topPosition = 0;
    $(container).each(function() {

        $el = $(this);
        $($el).height('auto');
        topPostion = $el.position().top;

        if (currentRowStart != topPostion) {
            for (currentDiv = 0 ; currentDiv < rowDivs.length ; currentDiv++) {
                rowDivs[currentDiv].height(currentTallest);
            }
            rowDivs.length = 0; // empty the array
            currentRowStart = topPostion;
            currentTallest = $el.height();
            rowDivs.push($el);
        } else {
            rowDivs.push($el);
            currentTallest = (currentTallest < $el.height()) ? ($el.height()) : (currentTallest);
        }
        for (currentDiv = 0 ; currentDiv < rowDivs.length ; currentDiv++) {
            rowDivs[currentDiv].height(currentTallest);
        }
    });
}