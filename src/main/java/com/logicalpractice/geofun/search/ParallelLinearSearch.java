package com.logicalpractice.geofun.search;

import ch.hsr.geohash.WGS84Point;
import com.logicalpractice.geofun.ActivePropertyList;
import com.logicalpractice.geofun.Hotel;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
public class ParallelLinearSearch extends LinearSearch {
  public ParallelLinearSearch(ActivePropertyList propertyList) throws IOException {
    super(propertyList);
  }

  @Override
  public List<Hotel> search(WGS84Point point, int radiusM) {
    return hotels.parallelStream()
        .filter((hotel) -> hotel.distanceTo(point) <= radiusM * 1000)
        .collect(Collectors.toList());
  }

}
