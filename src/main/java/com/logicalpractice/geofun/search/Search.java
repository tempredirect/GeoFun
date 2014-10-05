package com.logicalpractice.geofun.search;

import ch.hsr.geohash.WGS84Point;
import com.logicalpractice.geofun.Hotel;

import java.util.List;

/**
 *
 */
public interface Search {
  List<Hotel> search(WGS84Point point, int radiusKm);
}
