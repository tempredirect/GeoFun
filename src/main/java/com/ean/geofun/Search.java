package com.ean.geofun;

import java.util.List;

/**
 *
 */
public interface Search {
  List<Hotel> search(float[] latLon, int radiusM);
}
