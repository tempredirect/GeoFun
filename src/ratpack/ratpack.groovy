import ch.hsr.geohash.WGS84Point
import com.logicalpractice.geofun.ean.EanHotelInfo
import com.logicalpractice.geofun.ean.EanModule
import com.logicalpractice.geofun.ean.HotelInformation
import com.logicalpractice.geofun.search.GeoSearchModule
import com.logicalpractice.geofun.search.Search
import com.logicalpractice.geofun.WellKnownLocation
import ratpack.jackson.JacksonModule
import ratpack.rx.RxRatpack

import static ratpack.groovy.Groovy.ratpack
import static ratpack.groovy.Groovy.groovyTemplate
import static ratpack.jackson.Jackson.json

ratpack {
  RxRatpack.initialize()

  bindings {
    add new GeoSearchModule()
    add new JacksonModule()
    add new EanModule()
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

    get("info") { HotelInformation information ->
      information
          .hotelInfo(request.queryParams.getAll("id").collect { it.toLong() })
          .toList()
          .subscribe(
              { List<EanHotelInfo> info -> render(json(info)) },
              { Throwable e -> error(e) }
          )
    }

    assets "public"
  }
}

