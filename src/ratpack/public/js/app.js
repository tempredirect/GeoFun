
(function($){
    var hotelMarkers = {}, // all known hotelMarkers
        map,
        LatLng = google.maps.LatLng,
        Marker = google.maps.Marker,
        defaultZoom = 11,
        searchRadiusKm = 2;

    function onMapClick(ev) {
        var searchArea = new google.maps.Circle({
            strokeColor: '#0000FF',
            strokeOpacity: 0.8,
            strokeWeight: 2,
            fillColor: '#0000FF',
            fillOpacity: 0.35,
            map: map,
            center: ev.latLng,
            radius: searchRadiusKm * 1000
        });

        jQuery.ajax("/search", {
            data: {
                lat:ev.latLng.lat(),
                lng:ev.latLng.lng(),
                radius: searchRadiusKm
            },
            success:function(data) {
                var len = data.length,
                    i;

                for (i = 0; i < len; i++) {
                    var hotel = data[i];
                    var idAsString = "" + hotel.id;
                    if (!(idAsString in hotelMarkers)) {
                        hotelMarkers[idAsString] = new Marker({
                            title:hotel.name,
                            position:new LatLng(hotel.latitude, hotel.longitude),
                            map: map,
                            visible:true
                        })
                    }
                }
                setTimeout(function(){
                    searchArea.setMap(null);
                }, 1000);
            },
            error:function(xhr, status, error) {
                searchArea.setMap(null);
                console.error(status, error, xhr)
            }
        });

    }
    function resetAndMoveTo(latLng) {
        map.panTo(latLng);
        map.setZoom(defaultZoom);

        for (var property in hotelMarkers) {
            if (hotelMarkers.hasOwnProperty(property)) {
                var marker = hotelMarkers[property];
                marker.setMap(null);
            }
        }
        hotelMarkers = {};
    }

    function initialize() {
        var mapOptions = {
            // starts at london
            center: { lat: 51.5085300, lng: -0.1257400 },
            zoom: defaultZoom
        };
        map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);

        google.maps.event.addListener(map, 'click', onMapClick);

        $(".location-list a").click(function(e) {
            var lat = parseFloat($(e.target).data("lat")),
                lng = parseFloat($(e.target).data("lng"));
            resetAndMoveTo(new LatLng(lat,lng));
            $(".location-list a").removeClass('active');
            $(e.target).addClass('active');
        })
    }
    google.maps.event.addDomListener(window, 'load', initialize);
})(jQuery);

