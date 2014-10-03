package com.ean.geofun;

import ch.hsr.geohash.GeoHash;
import ch.hsr.geohash.WGS84Point;
import ch.hsr.geohash.queries.GeoHashCircleQuery;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 */
public class GeoHashSearch implements Search {

  ListMultimap<GeoHash, Hotel> hotels = ArrayListMultimap.create(30_000, 500);

//  SortedMap<GeoHash, Hotel> hotels = new TreeMap<>();

  public GeoHashSearch(ActivePropertyList propertyList) throws IOException {
    propertyList.stream().forEach((hotel) ->
            hotels.put(GeoHash.withBitPrecision(hotel.getLatitude(), hotel.getLongitude(), 17), hotel)
    );
  }

  @Override
  public List<Hotel> search(float[] latLon, int radiusM) {
    GeoHashCircleQuery query = new GeoHashCircleQuery(new WGS84Point(latLon[0], latLon[1]), radiusM * 1000);

    return query.getSearchHashes()
        .stream()
        .flatMap((hash) ->
                Stream.concat(Stream.of(hash), Stream.of(hash.getAdjacent()))
        )
        .distinct()
        .flatMap((hash) ->
                hotels.get(hash).stream()
        )
        .filter((hotel) -> hotel.distanceTo(latLon) <= radiusM)
        .collect(Collectors.toList()); //.stream().collect(Collectors.toList());
  }

  public static void main(String[] args) throws IOException {
    GeoHashSearch search = new GeoHashSearch(new ActivePropertyList(Paths.get("ActivePropertyList.txt")));
    System.out.println("Loaded");
    List<Hotel> results = search.search(new float[]{40.7142700f, -74.0059700f}, 20);

    System.out.printf("New York 20km : %s\n", results.size());
  }
}
