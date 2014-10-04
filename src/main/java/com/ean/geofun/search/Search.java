package com.ean.geofun.search;

import ch.hsr.geohash.WGS84Point;
import com.ean.geofun.Hotel;

import java.util.List;

/**
 *
 */
public interface Search {
  List<Hotel> search(WGS84Point point, int radiusKm);
}
