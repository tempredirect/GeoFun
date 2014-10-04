import ch.hsr.geohash.WGS84Point
import com.ean.geofun.search.GeoSearchModule
import com.ean.geofun.search.Search
import com.ean.geofun.WellKnownLocation
import ratpack.jackson.JacksonModule

import static ratpack.groovy.Groovy.ratpack
import static ratpack.groovy.Groovy.groovyTemplate
import static ratpack.jackson.Jackson.json

ratpack {

  bindings {
    add new GeoSearchModule()
    add new JacksonModule()
  }

  handlers {
    get {
      render groovyTemplate("index.html",
          locations: WellKnownLocation.values() as List
      )
    }

    get("search") { Search search ->
      def lat = request.queryParams.get("lat") as double
      def lon = request.queryParams.get("lng") as double
      def radius = request.queryParams.getOrDefault("radius", "20") as int
      def results = search.search(new WGS84Point(lat, lon), radius)
      render(json(results))
    }

    assets "public"
  }
}

