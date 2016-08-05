/**
 * Requires: utils
 * Dynamically includes: google maps, application maps
 */

var HomeIndexPage = {};

HomeIndexPage.init = function(language, lat, lng) {
    HomeIndexPage.staticMapCreated = false;
    HomeIndexPage.dynamicMapCreated = false;
    HomeIndexPage.dynamicMapHeight = 700;

    HomeIndexPage.lat = function() {
        return lat;
    }

    HomeIndexPage.lng = function() {
        return lng;
    }

    HomeIndexPage.manageMaps();
    $(window).on('resize', HomeIndexPage.manageMaps);

    $('#properties-search-form').submit(function() {
        var $location = $('#location');
        var location = $('#location').val();
        $location.prop('disabled', true);

        var $offerType = $('#offerType');
        var offerType = $('#offerType').val();
        $offerType.prop('disabled', true);

        var $propertyType = $('#propertyType');
        var propertyType = $('#propertyType').val();
        $propertyType.prop('disabled', true);

        $(this).attr('action', Utils.getPropertySearchPath(language, location, offerType, propertyType));
    });
};

HomeIndexPage.manageMaps = function() {
    // Quick path - dynamic map already created, no need to change anything
    if (HomeIndexPage.dynamicMapCreated) {
        return;
    }

    var width = $(window).width();
    if (width >= 768) {
        // Create the appropriate map if necessary and center the search box
        if (width < 992) {
            if (!HomeIndexPage.staticMapCreated) {
                HomeIndexPage.createStatic();
                HomeIndexPage.centerSearchBox();
            }
        } else {
            HomeIndexPage.createDynamic();
            HomeIndexPage.centerSearchBox();
        }
    }
};

HomeIndexPage.createDynamic = function() {
    HomeIndexPage.dynamicMapCreated = true;
    $('#map').css('height', HomeIndexPage.dynamicMapHeight+'px');
    Utils.appendScript('http://maps.google.com/maps/api/js?sensor=false&callback=HomeIndexPage.createHomepageGoogleMap');
}

HomeIndexPage.createHomepageGoogleMap = function() {
    Utils.appendScript(Utils.getPath('/assets/js/application-maps.min.js'));
    $.getJSON('properties.json', function(properties) {
        var map = new google.maps.Map(document.getElementById('map'), {
            zoom: 9,
            scrollwheel: false,
            zoomControl: true,
            zoomControlOptions: {
                position: google.maps.ControlPosition.LEFT_TOP
            },
            streetViewControl: false,
            center: new google.maps.LatLng(HomeIndexPage.lat(), HomeIndexPage.lng()),
            mapTypeId: google.maps.MapTypeId.ROADMAP,
            styles: mapStyles
        });

        var infoboxOptions = {
            disableAutoPan: false,
            boxStyle: {
                width: "250px"
            },
            pixelOffset: new google.maps.Size(-100, 0),
            zIndex: null,
            alignBottom: true,
            boxClass: "infobox-wrapper",
            enableEventPropagation: true,
            closeBoxMargin: "0px 0px -8px 0px",
            closeBoxURL: "http://assets.gotocy.com/static/img/close-btn.png",
            infoBoxClearance: new google.maps.Size(1, 1)
        };

        var markers = [];
        for (var i = 0; i < properties.length; i++) {
            var pictureLabel = document.createElement("img");
            pictureLabel.src = 'http://assets.gotocy.com/static/img/property-types/' + properties[i]['typeIcon'] + '.png';

            var marker = new MarkerWithLabel({
                title: properties[i]['title'],
                position: new google.maps.LatLng(properties[i]['latitude'], properties[i]['longitude']),
                icon: 'http://assets.gotocy.com/static/img/marker.png',
                labelContent: pictureLabel,
                labelAnchor: new google.maps.Point(50, 0),
                labelClass: "marker-style"
            });
            markers.push(marker);

            google.maps.event.addListener(marker, 'click', (function(i) {
                return function() {
                    if (!markers[i].infobox) {
                        var box = document.createElement("div");
                        $(box).html(
                            '<div class="infobox-inner">' +
                                '<a href="' + properties[i]['propertyUrl'] + '">' +
                                    '<div class="infobox-image" style="position: relative">' +
                                        '<img src="' + properties[i]['representativeImageUrl'] + '">' + '<div><span class="infobox-price">' + properties[i]['price'] + '</span></div>' +
                                    '</div>' +
                                '</a>' +
                                '<div class="infobox-description">' +
                                    '<div class="infobox-title"><a href="'+ properties[i]['propertyUrl'] +'">' + properties[i]['title'] + '</a></div>' +
                                    '<div class="infobox-location">' + properties[i]['shortAddress'] + '</div>' +
                                '</div>' +
                            '</div>'
                        );
                        markers[i].infobox = new InfoBox($.extend({content: box}, infoboxOptions));
                    }

                    for (var j = 0; j < markers.length; j++) {
                        if (markers[j].infobox) {
                            markers[j].infobox.close();
                        }
                    }

                    markers[i].infobox.open(map, this);
                }
            })(i));
        }

        var clusterStyles = [
            {
                url: 'http://assets.gotocy.com/static/img/cluster.png',
                height: 37,
                width: 37
            }
        ];
        var markerCluster = new MarkerClusterer(map, markers, {styles: clusterStyles, maxZoom: 15});

        $('body').addClass('loaded');
            setTimeout(function() {
                $('body').removeClass('has-fullscreen-map');
            }, 1000);
            $('#map').removeClass('fade-map');
    });
}

HomeIndexPage.createStatic = function() {
    staticMapCreated = true;
    // 2.13 = 1280 / 600 (768x300 scale 2)
    var mapHeight = $(window).width() / 2.13;
    $('#map').css('height', mapHeight);
    var $mapImage = $(document.createElement('img'));
    $mapImage.attr('src', 'http://maps.googleapis.com/maps/api/staticmap?center=' + HomeIndexPage.lat() + ',' + HomeIndexPage.lng() + '&zoom=8&size=768x300&scale=2&style=feature:water|element:all|hue:0xd7ebef|saturation:-5|lightness:54|visibility:on&style=feature:landscape|element:all|hue:0xeceae6|saturation:-49|lightness:22|visibility:on&style=feature:poi.ark|element:all|hue:0xdddbd7|saturation:-81|lightness:34|visibility:on&style=feature:poi.medical|element:all|hue:0xdddbd7|saturation:-80|lightness:-2|visibility:on&style=feature:poi.school|element:all|hue:0xc8c6c3|saturation:-91|lightness:-7|visibility:on&style=feature:landscape.natural|element:all|hue:0xc8c6c3|saturation:-71|lightness:-18|visibility:on&style=feature:road.highway|element:all|hue:0xdddbd7|saturation:-92|lightness:60|visibility:on&style=feature:poi|element:all|hue:0xdddbd7|saturation:-81|lightness:34|visibility:on&style=feature:road.arterial|element:all|hue:0xdddbd7|saturation:-81|lightness:34|visibility:on&style=feature:transit|element:geometry|hue:0xc8c6c3|saturation:4|lightness:10|visibility:on&key=AIzaSyDHd0r7iRvEzgEwPscSKbRmU6eIoL8LcUs');
    $mapImage.addClass('img-responsive');
    $mapImage.addClass('center');
    // This is essential for Firefox
    $mapImage.css('height', mapHeight);
    $('#map').html($mapImage);
};

HomeIndexPage.centerSearchBox = function() {
    var mapHeight = $('#map').height();
    var navigationHeight = $('.navigation').height();
    var contentHeight = $('.search-box').height();
    var top = mapHeight === HomeIndexPage.dynamicMapHeight ? mapHeight + navigationHeight - contentHeight * 2 : mapHeight * 0.7 + navigationHeight;
    $('.search-box-wrapper').css('top', top);
};