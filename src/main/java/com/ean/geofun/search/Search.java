package com.ean.geofun.search;

import ch.hsr.geohash.WGS84Point;

import java.util.List;

/**
 *
 */
public interface Search {
  List<Hotel> search(WGS84Point point, int radiusM);
}
