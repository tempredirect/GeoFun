package com.ean.geofun;

import ch.hsr.geohash.WGS84Point;

/**
 *
 */
public enum WellKnownLocation {

  //http://dateandtime.info/citycoordinates.php?id=5128581
  NEW_YORK_CITY(40.7142700, -74.0059700),
  LONDON(51.5085300, -0.1257400);

  private WGS84Point point;

  WellKnownLocation(double lat, double lon) {
    this.point = new WGS84Point(lat,lon);
  }

  public WGS84Point point() {
    return point;
  }
}
