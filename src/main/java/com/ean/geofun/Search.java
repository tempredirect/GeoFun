package com.ean.geofun;

import ch.hsr.geohash.WGS84Point;

import java.util.List;

/**
 *
 */
public interface Search {
  List<Hotel> search(WGS84Point point, int radiusM);
}
