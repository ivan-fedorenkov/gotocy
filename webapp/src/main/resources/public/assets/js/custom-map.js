var mapStyles = [{featureType:'water',elementType:'all',stylers:[{hue:'#d7ebef'},{saturation:-5},{lightness:54},{visibility:'on'}]},{featureType:'landscape',elementType:'all',stylers:[{hue:'#eceae6'},{saturation:-49},{lightness:22},{visibility:'on'}]},{featureType:'poi.park',elementType:'all',stylers:[{hue:'#dddbd7'},{saturation:-81},{lightness:34},{visibility:'on'}]},{featureType:'poi.medical',elementType:'all',stylers:[{hue:'#dddbd7'},{saturation:-80},{lightness:-2},{visibility:'on'}]},{featureType:'poi.school',elementType:'all',stylers:[{hue:'#c8c6c3'},{saturation:-91},{lightness:-7},{visibility:'on'}]},{featureType:'landscape.natural',elementType:'all',stylers:[{hue:'#c8c6c3'},{saturation:-71},{lightness:-18},{visibility:'on'}]},{featureType:'road.highway',elementType:'all',stylers:[{hue:'#dddbd7'},{saturation:-92},{lightness:60},{visibility:'on'}]},{featureType:'poi',elementType:'all',stylers:[{hue:'#dddbd7'},{saturation:-81},{lightness:34},{visibility:'on'}]},{featureType:'road.arterial',elementType:'all',stylers:[{hue:'#dddbd7'},{saturation:-92},{lightness:37},{visibility:'on'}]},{featureType:'transit',elementType:'geometry',stylers:[{hue:'#c8c6c3'},{saturation:4},{lightness:10},{visibility:'on'}]}];

$.ajaxSetup({
    cache: true
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Google Map - Homepage
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

function createHomepageGoogleMap(_latitude,_longitude){
    setMapHeight();
    if( document.getElementById('map') != null ){
        $.getScript("http://assets.gotocy.eu/static/js/locations.js", function(){
            var map = new google.maps.Map(document.getElementById('map'), {
                zoom: 14,
                scrollwheel: false,
                center: new google.maps.LatLng(_latitude, _longitude),
                mapTypeId: google.maps.MapTypeId.ROADMAP,
                styles: mapStyles
            });
            var i;
            var newMarkers = [];
            for (i = 0; i < locations.length; i++) {
                var pictureLabel = document.createElement("img");
                pictureLabel.src = locations[i][7];
                var boxText = document.createElement("div");
                infoboxOptions = {
                    content: boxText,
                    disableAutoPan: false,
                    //maxWidth: 150,
                    pixelOffset: new google.maps.Size(-100, 0),
                    zIndex: null,
                    alignBottom: true,
                    boxClass: "infobox-wrapper",
                    enableEventPropagation: true,
                    closeBoxMargin: "0px 0px -8px 0px",
                    closeBoxURL: "http://assets.gotocy.eu/static/img/close-btn.png",
                    infoBoxClearance: new google.maps.Size(1, 1)
                };
                var marker = new MarkerWithLabel({
                    title: locations[i][0],
                    position: new google.maps.LatLng(locations[i][3], locations[i][4]),
                    map: map,
                    icon: 'http://assets.gotocy.eu/static/img/marker.png',
                    labelContent: pictureLabel,
                    labelAnchor: new google.maps.Point(50, 0),
                    labelClass: "marker-style"
                });
                newMarkers.push(marker);
                boxText.innerHTML =
                    '<div class="infobox-inner">' +
                        '<a href="' + locations[i][5] + '">' +
                        '<div class="infobox-image" style="position: relative">' +
                        '<img src="' + locations[i][6] + '">' + '<div><span class="infobox-price">' + locations[i][2] + '</span></div>' +
                        '</div>' +
                        '</a>' +
                        '<div class="infobox-description">' +
                        '<div class="infobox-title"><a href="'+ locations[i][5] +'">' + locations[i][0] + '</a></div>' +
                        '<div class="infobox-location">' + locations[i][1] + '</div>' +
                        '</div>' +
                        '</div>';
                //Define the infobox
                newMarkers[i].infobox = new InfoBox(infoboxOptions);
                google.maps.event.addListener(marker, 'click', (function(marker, i) {
                    return function() {
                        for (h = 0; h < newMarkers.length; h++) {
                            newMarkers[h].infobox.close();
                        }
                        newMarkers[i].infobox.open(map, this);
                    }
                })(marker, i));

            }
            var clusterStyles = [
                {
                    url: 'http://assets.gotocy.eu/static/img/cluster.png',
                    height: 37,
                    width: 37
                }
            ];
            var markerCluster = new MarkerClusterer(map, newMarkers, {styles: clusterStyles, maxZoom: 15});
            $('body').addClass('loaded');
            setTimeout(function() {
                $('body').removeClass('has-fullscreen-map');
            }, 1000);
            $('#map').removeClass('fade-map');

            //  Dynamically show/hide markers --------------------------------------------------------------

            google.maps.event.addListener(map, 'idle', function() {

                for (var i=0; i < locations.length; i++) {
                    if ( map.getBounds().contains(newMarkers[i].getPosition()) ){
                        // newMarkers[i].setVisible(true); // <- Uncomment this line to use dynamic displaying of markers

                        //newMarkers[i].setMap(map);
                        //markerCluster.setMap(map);
                    } else {
                        // newMarkers[i].setVisible(false); // <- Uncomment this line to use dynamic displaying of markers

                        //newMarkers[i].setMap(null);
                        //markerCluster.setMap(null);
                    }
                }
            });

            // Function which set marker to the user position
            function success(position) {
                var center = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
                map.panTo(center);
                $('#map').removeClass('fade-map');
            }

        });
        // Enable Geo Location on button click
        $('.geo-location').on("click", function() {
            if (navigator.geolocation) {
                $('#map').addClass('fade-map');
                navigator.geolocation.getCurrentPosition(success);
            } else {
                error('Geo Location is not supported');
            }
        });
    }
}

// Function which set marker to the user position
function success(position) {
    createHomepageGoogleMap(position.coords.latitude, position.coords.longitude);
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Google Map - Property Detail
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

function initMap(latitude, longitude, markerPictureSrc) {
    var subtractPosition = 0;
    var mapWrapper = $('#property-detail-map.float');

    if (document.documentElement.clientWidth > 1200) {
        subtractPosition = 0.013;
    }
    if (document.documentElement.clientWidth < 1199) {
        subtractPosition = 0.006;
    }
    if (document.documentElement.clientWidth < 979) {
        subtractPosition = 0.001;
    }
    if (document.documentElement.clientWidth < 767) {
        subtractPosition = 0;
    }

    var mapCenter = new google.maps.LatLng(latitude, longitude);

    if ( $("#property-detail-map").hasClass("float") ) {
        mapCenter = new google.maps.LatLng(latitude, longitude - subtractPosition);
        mapWrapper.css('width', mapWrapper.width() + mapWrapper.offset().left )
    }

    var mapOptions = {
        zoom: 15,
        center: mapCenter,
        disableDefaultUI: false,
        scrollwheel: false,
        styles: mapStyles
    };
    var mapElement = document.getElementById('property-detail-map');
    var map = new google.maps.Map(mapElement, mapOptions);

    var pictureLabel = document.createElement("img");
    pictureLabel.src = markerPictureSrc;
    var markerPosition = new google.maps.LatLng(latitude, longitude);
    var marker = new MarkerWithLabel({
        position: markerPosition,
        map: map,
        icon: 'http://assets.gotocy.eu/static/img/marker.png',
        labelContent: pictureLabel,
        labelAnchor: new google.maps.Point(50, 0),
        labelClass: "marker-style"
    });
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Google Map - Contact
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

function contactUsMap(){
    var mapCenter = new google.maps.LatLng(_latitude,_longitude);
    var mapOptions = {
        zoom: 15,
        center: mapCenter,
        disableDefaultUI: false,
        scrollwheel: false,
        styles: mapStyles
    };
    var mapElement = document.getElementById('contact-map');
    var map = new google.maps.Map(mapElement, mapOptions);

    var marker = new MarkerWithLabel({
        position: mapCenter,
        map: map,
        icon: 'http://assets.gotocy.eu/static/img/marker.png',
        //labelContent: pictureLabel,
        labelAnchor: new google.maps.Point(50, 0),
        labelClass: "marker-style"
    });
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Google Map - Submit Map
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

function initSubmitMap(_latitude,_longitude){
    var mapCenter = new google.maps.LatLng(_latitude,_longitude);
    var mapOptions = {
        zoom: 15,
        center: mapCenter,
        disableDefaultUI: false,
        //scrollwheel: false,
        styles: mapStyles
    };
    var mapElement = document.getElementById('submit-map');
    var map = new google.maps.Map(mapElement, mapOptions);
    var marker = new MarkerWithLabel({
        position: mapCenter,
        map: map,
        icon: 'http://assets.gotocy.eu/static/img/marker.png',
        labelAnchor: new google.maps.Point(50, 0),
        draggable: true
    });
    $('#submit-map').removeClass('fade-map');
    google.maps.event.addListener(marker, "mouseup", function (event) {
        var latitude = this.position.lat();
        var longitude = this.position.lng();
        $('#latitude').val( this.position.lat() );
        $('#longitude').val( this.position.lng() );
    });

//      Autocomplete
    var input = /** @type {HTMLInputElement} */( document.getElementById('address-map') );
    var autocomplete = new google.maps.places.Autocomplete(input);
    autocomplete.bindTo('bounds', map);
    google.maps.event.addListener(autocomplete, 'place_changed', function() {
        var place = autocomplete.getPlace();
        if (!place.geometry) {
            return;
        }
        if (place.geometry.viewport) {
            map.fitBounds(place.geometry.viewport);
        } else {
            map.setCenter(place.geometry.location);
            map.setZoom(17);
        }
        marker.setPosition(place.geometry.location);
        marker.setVisible(true);
        $('#latitude').val( marker.getPosition().lat() );
        $('#longitude').val( marker.getPosition().lng() );
        var address = '';
        if (place.address_components) {
            address = [
                (place.address_components[0] && place.address_components[0].short_name || ''),
                (place.address_components[1] && place.address_components[1].short_name || ''),
                (place.address_components[2] && place.address_components[2].short_name || '')
            ].join(' ');
        }
    });

    function success(position) {
        initSubmitMap(position.coords.latitude, position.coords.longitude);
        $('#latitude').val( position.coords.latitude );
        $('#longitude').val( position.coords.longitude );
    }

    $('.geo-location').on("click", function() {
        if (navigator.geolocation) {
            $('#submit-map').addClass('fade-map');
            navigator.geolocation.getCurrentPosition(success);
        } else {
            error('Geo Location is not supported');
        }
    });
}

