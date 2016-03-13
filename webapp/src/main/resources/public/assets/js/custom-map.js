var mapStyles = [{featureType:'water',elementType:'all',stylers:[{hue:'#d7ebef'},{saturation:-5},{lightness:54},{visibility:'on'}]},{featureType:'landscape',elementType:'all',stylers:[{hue:'#eceae6'},{saturation:-49},{lightness:22},{visibility:'on'}]},{featureType:'poi.park',elementType:'all',stylers:[{hue:'#dddbd7'},{saturation:-81},{lightness:34},{visibility:'on'}]},{featureType:'poi.medical',elementType:'all',stylers:[{hue:'#dddbd7'},{saturation:-80},{lightness:-2},{visibility:'on'}]},{featureType:'poi.school',elementType:'all',stylers:[{hue:'#c8c6c3'},{saturation:-91},{lightness:-7},{visibility:'on'}]},{featureType:'landscape.natural',elementType:'all',stylers:[{hue:'#c8c6c3'},{saturation:-71},{lightness:-18},{visibility:'on'}]},{featureType:'road.highway',elementType:'all',stylers:[{hue:'#dddbd7'},{saturation:-92},{lightness:60},{visibility:'on'}]},{featureType:'poi',elementType:'all',stylers:[{hue:'#dddbd7'},{saturation:-81},{lightness:34},{visibility:'on'}]},{featureType:'road.arterial',elementType:'all',stylers:[{hue:'#dddbd7'},{saturation:-92},{lightness:37},{visibility:'on'}]},{featureType:'transit',elementType:'geometry',stylers:[{hue:'#c8c6c3'},{saturation:4},{lightness:10},{visibility:'on'}]}];
var polygonOptions = {strokeColor: '#1396e2',strokeOpacity: 0.8,strokeWeight: 2,fillColor: '#1396e2',fillOpacity: 0.35};

$.ajaxSetup({
    cache: true
});

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Google Map - Homepage
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

function createHomepageGoogleMap(lat, lng){
    $.getJSON('properties.json', function(properties) {
        var map = new google.maps.Map(document.getElementById('map'), {
            zoom: 9,
            scrollwheel: false,
            zoomControl: true,
            zoomControlOptions: {
                position: google.maps.ControlPosition.LEFT_TOP
            },
            streetViewControl: false,
            center: new google.maps.LatLng(lat, lng),
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
            closeBoxURL: "http://assets.gotocy.eu/static/img/close-btn.png",
            infoBoxClearance: new google.maps.Size(1, 1)
        };

        var markers = [];
        for (var i = 0; i < properties.length; i++) {
            var pictureLabel = document.createElement("img");
            pictureLabel.src = 'http://assets.gotocy.eu/static/img/property-types/' + properties[i]['typeIcon'] + '.png';

            var marker = new MarkerWithLabel({
                title: properties[i]['title'],
                position: new google.maps.LatLng(properties[i]['latitude'], properties[i]['longitude']),
                icon: 'http://assets.gotocy.eu/static/img/marker.png',
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
                url: 'http://assets.gotocy.eu/static/img/cluster.png',
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


function complexMap(complexTitle, coordinates) {
    var latitude = 0;
    var longitude = 0;

    for (i = 0; i < coordinates.length; i++) {
        latitude = latitude + coordinates[i].lat;
        longitude = longitude + coordinates[i].lng;
    }
    latitude = latitude / coordinates.length;
    longitude = longitude / coordinates.length;

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
        zoom: 16,
        center: mapCenter,
        disableDefaultUI: false,
        scrollwheel: false,
        styles: mapStyles
    };
    var mapElement = document.getElementById('property-detail-map');
    var map = new google.maps.Map(mapElement, mapOptions);

    var plotOptions = $.extend({paths: coordinates}, polygonOptions);
    var plot = new google.maps.Polygon(plotOptions);
    plot.setMap(map);

    var infoWindow = new google.maps.InfoWindow;
    infoWindow.setContent('<b>' + complexTitle + '</b>');
    infoWindow.setPosition(new google.maps.LatLng(latitude, longitude));
    infoWindow.open(map);

    plot.addListener('click', function(e) {
       infoWindow.setPosition(e.latLng);
       infoWindow.open(map);
    });
}


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Google Map - Submit Map
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

function initPropertySubmitMap(_latitude,_longitude,_zoom){
    var mapCenter = new google.maps.LatLng(_latitude,_longitude);
    var mapOptions = {
        zoom: _zoom,
        center: mapCenter,
        disableDefaultUI: false,
        //scrollwheel: false,
        styles: mapStyles
    };
    var mapElement = document.getElementById('submit-map');
    var map = new google.maps.Map(mapElement, mapOptions);
    var geocoder = new google.maps.Geocoder;
    var marker = new MarkerWithLabel({
        position: mapCenter,
        map: map,
        icon: 'http://assets.gotocy.eu/static/img/marker.png',
        labelAnchor: new google.maps.Point(50, 0),
        draggable: true
    });

    var locationInput = $('#location');
    var addressInput = $('#address');
    var latitudeInput = $('#latitude');
    var longitudeInput = $('#longitude');

    $('#submit-map').removeClass('fade-map');
    google.maps.event.addListener(marker, "mouseup", function (event) {
        var latitude = this.position.lat();
        var longitude = this.position.lng();
        latitudeInput.val(latitude);
        longitudeInput.val(longitude);
        resolveAndSetAddress(latitude, longitude);
    });

    // Autocomplete
    var input = /** @type {HTMLInputElement} */( document.getElementById('address-search') );
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

        var latitude = marker.getPosition().lat();
        var longitude = marker.getPosition().lng();

        latitudeInput.val(latitude);
        longitudeInput.val(longitude);
        resolveAndSetAddress(latitude, longitude);
    });

    function resolveAndSetAddress(lat, lng) {
        geocoder.geocode({'location': {lat: lat, lng: lng}}, function(results, status) {

            if (status === google.maps.GeocoderStatus.OK) {

                var location;
                var address;

                for (i = 0; i < results.length; i++) {
                    var result = results[i];
                    var resultTypes = result.types;
                    if (resultTypes.length > 1) {
                        // Best case - we can get all the required information from this result object
                        if (resultTypes[0] == 'locality' && resultTypes[1] == 'political') {
                            address = result.formatted_address;
                            location = resolveLocationFromAddressComponents(result.address_components);
                        }

                        // We can obtain location, but the address would be less accurate
                        if (resultTypes[0] == 'administrative_area_level_1' && resultTypes[1] == 'political') {
                            if (!address) {
                                address = result.formatted_address;
                            }
                            location = resolveLocationFromAddressComponents(result.address_components);
                        }
                    }

                    if (address && location) {
                        break;
                    }
                }

                setLocation(location, address);
                setAddress(address);
            } else {
                // No results found
                setLocation(null, null);
                setAddress(null);
            }
        });
    }

    function resolveLocationFromAddressComponents(addressComponents) {
        for (j = 0; j < addressComponents.length; j++) {
            var addressComponent = addressComponents[j];
            var addressComponentTypes = addressComponent.types;
            if (addressComponentTypes.length > 1) {
                if (addressComponentTypes[0] == 'administrative_area_level_1' && addressComponentTypes[1] == 'political') {
                    return addressComponent.long_name;
                }
            }
        }
        return null;
    }

    function setLocation(location, address) {

        if (!location || location === 'Фамагаста') { // Google maps typo fix; Default location if necessary
            location = 'FAMAGUSTA';
        }

        if (address) {
            var commaIndex = address.indexOf(',');
            if (commaIndex > 0) {
                var locationFromAddress = address.substring(0, commaIndex);
                if (locationFromAddress === 'Ayia Napa' || locationFromAddress === 'Айя Напа' || locationFromAddress === 'Αγ. Νάπα') {
                    location = 'AYIA_NAPA';
                } else if (locationFromAddress === 'Protaras' || locationFromAddress === 'Протарас' || locationFromAddress === 'Πρωταράς') {
                    location = 'PROTARAS';
                } else if (locationFromAddress === 'Troodos' || locationFromAddress === 'Троодос' || locationFromAddress === 'Τρόοδος') {
                    location = 'TROODOS';
                }
            }
        }

        locationInput.val(location);
    }

    function setAddress(address) {
        if (!address) {
            address = 'Cyprus';
        }
        addressInput.val(address);
    }

    resolveAndSetAddress(_latitude, _longitude);
}

function initComplexSubmitMap(_latitude,_longitude) {
    var mapCenter = new google.maps.LatLng(_latitude, _longitude);
    var mapOptions = {
        zoom: 8,
        center: mapCenter,
        disableDefaultUI: false,
        //scrollwheel: false,
        styles: mapStyles
    };
    var mapElement = document.getElementById('submit-map');
    var map = new google.maps.Map(mapElement, mapOptions);

    var plotOptions = $.extend({editable: true}, polygonOptions);
    var coordinates = $('#plot-coordinates');
    if (coordinates.val()) {
        $.extend(plotOptions, {paths: eval(coordinates.val())});
    }

    var plot = new google.maps.Polygon(plotOptions);
    plot.setMap(map);
    setupPlotListeners();

    var drawingManager = new google.maps.drawing.DrawingManager({
        drawingMode: null,
        drawingControl: true,
        drawingControlOptions: {
          position: google.maps.ControlPosition.TOP_CENTER,
          drawingModes: [
            google.maps.drawing.OverlayType.POLYGON
          ]
        },
        polygonOptions: plotOptions
    });
    drawingManager.setMap(map);

    google.maps.event.addListener(drawingManager, 'polygoncomplete', function(polygon) {
        google.maps.event.clearInstanceListeners(plot.getPath());
        plot.getPath().clear();
        plot.setPath(polygon.getPath());
        plot.setMap(null);

        plot = polygon;
        setupPlotListeners();
        updateCoordinates();
    });

    function setupPlotListeners() {
        google.maps.event.addListener(plot.getPath(), 'set_at', function(e) {
            updateCoordinates();
        });
        google.maps.event.addListener(plot.getPath(), 'insert_at', function(e) {
            updateCoordinates();
        });
        google.maps.event.addListener(plot.getPath(), 'remote_at', function(e) {
            updateCoordinates();
        });
    }

    function updateCoordinates() {
        var serializedPath = [];
        plot.getPath().forEach(function(point, index) {
            serializedPath.push({lat: point.lat(), lng: point.lng()});
        });
        coordinates.val(JSON.stringify(serializedPath));
    }

}
