package com.logicalpractice.geofun.search;

import ch.hsr.geohash.GeoHash;
import ch.hsr.geohash.WGS84Point;
import ch.hsr.geohash.queries.GeoHashCircleQuery;
import com.logicalpractice.geofun.ActivePropertyList;
import com.logicalpractice.geofun.Hotel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 */
public class GeoHashSearch implements Search {

//  ListMultimap<GeoHash, Hotel> hotels = ArrayListMultimap.create(30_000, 500);

//  SortedMap<GeoHash, Hotel> hotels = new TreeMap<>();
  SortedMap<Long, List<Hotel>> hotels = new TreeMap<>();

  public GeoHashSearch(ActivePropertyList propertyList) throws IOException {
    AtomicInteger counter = new AtomicInteger();
    propertyList.stream().forEach((hotel) -> {
          Long key = GeoHash.withBitPrecision(hotel.getLatitude(), hotel.getLongitude(), 64).longValue();
          List<Hotel> container = hotels.get(key);
          if (container == null) {
            container = new ArrayList<>();
            hotels.put(key, container);
          }
          container.add(hotel);
          counter.incrementAndGet();
        }
    );
    long propertyListSize = propertyList.stream().collect(Collectors.counting());
    if ( propertyListSize != counter.get()) {
      throw new IllegalStateException("We lost some " + propertyListSize + " v " + hotels.size());
    }
  }

  @Override
  public List<Hotel> search(WGS84Point point, int radiusKm) {
    GeoHashCircleQuery query = new GeoHashCircleQuery(point, radiusKm * 1000);

    return query.getSearchHashes()
        .stream()
        .flatMap(
            (hash) ->
                Stream.concat(Stream.of(hash), Stream.of(hash.getAdjacent()))
        )
        .distinct()
        .flatMap(
            (hash) -> {
              long mask = Long.MAX_VALUE >> (hash.significantBits()-1);
              Long tail = mask | hash.longValue(); // will keep the hash bits and leave the tail as 1111
              return hotels.subMap(hash.longValue(), tail).values().stream();
            }
        )
        .flatMap(Collection::stream)
        .filter(
            (hotel) ->
                hotel.distanceTo(point) <= radiusKm * 1000)
        .collect(Collectors.toList()); //.stream().collect(Collectors.toList());
  }

}
