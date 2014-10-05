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
public class LinearSearch implements Search {

  protected List<Hotel> hotels ;

  public LinearSearch(ActivePropertyList propertyList) throws IOException {
    hotels = propertyList.stream().collect(Collectors.toList());
  }

  @Override
  public List<Hotel> search(WGS84Point point, int radiusM) {
    return hotels.stream()
        .filter((hotel) -> hotel.distanceTo(point) <= radiusM * 1000)
        .collect(Collectors.toList());
  }

}
