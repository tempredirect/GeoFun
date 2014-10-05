package com.logicalpractice.geofun.search;

import ch.hsr.geohash.GeoHash;
import ch.hsr.geohash.WGS84Point;
import ch.hsr.geohash.queries.GeoHashCircleQuery;
import com.logicalpractice.geofun.ActivePropertyList;
import com.logicalpractice.geofun.Hotel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 *
 */
public class GeoHashSearch2 implements Search {

  private long [] hashes;
  private Hotel [] hotels;

  public GeoHashSearch2(ActivePropertyList propertyList) throws IOException {
    SortedMap<Long, List<Hotel>> hotelsTree = new TreeMap<>();
    AtomicInteger counter = new AtomicInteger();
    propertyList.stream().forEach((hotel) -> {
          Long key = GeoHash.withBitPrecision(hotel.getLatitude(), hotel.getLongitude(), 64).longValue();
          List<Hotel> container = hotelsTree.get(key);
          if (container == null) {
            container = new ArrayList<>();
            hotelsTree.put(key, container);
          }
          container.add(hotel);
          counter.incrementAndGet();
        }
    );
    
    hashes = new long[counter.get()];
    hotels = new Hotel[counter.get()];
    int index = 0;

    for (Map.Entry<Long, List<Hotel>> entry : hotelsTree.entrySet()) {
      long hash = entry.getKey();
      for (Hotel hotel : entry.getValue()) {
        hashes[index] = hash;
        hotels[index] = hotel;
        index ++;
      }
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
        .parallel()
        .flatMap(
            (hash) ->
                prefixRange(hash.longValue(), hash.significantBits())
                    .mapToObj((index) -> hotels[index])
        )
        .filter(
            (hotel) ->
                hotel.distanceTo(point) <= radiusKm * 1000)
        .collect(Collectors.toList());
  }

  private IntStream prefixRange(long search, int numberOfBitsInPrefix) {
    long mask = Long.MIN_VALUE >> (numberOfBitsInPrefix - 1);
    long head = search & mask; /// 0x1a310000
    long tail = search | ~mask; /// 0x1a31ffff

    int headIndex = translate(Arrays.binarySearch(hashes, head));
    int tailIndex = translate(Arrays.binarySearch(hashes, headIndex, hashes.length - 1, tail));
    
    return IntStream.range(headIndex, tailIndex + 1);
  }

  private int translate(int binarySearch) {
    if (binarySearch < 0) {
      return (binarySearch + 1) * -1;
    }
    return binarySearch; // really unlikely
  }
}
