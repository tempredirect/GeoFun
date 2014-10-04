package com.ean.geofun;

import ch.hsr.geohash.WGS84Point;

/**
 *
 */
public enum WellKnownLocation {
  LONDON("London", 51.5085300, -0.1257400),
  //http://dateandtime.info/citycoordinates.php?id=5128581
  NEW_YORK_CITY("New York City", 40.7142700, -74.0059700),
  LAS_VEGAS("Las Vegas", 36.1749700, -115.1372200),
  PARIS("Paris", 48.8534100, 2.3488000),
  BERLIN("Berlin", 52.5243700, 13.4105300),
  ROME("Rome", 41.8947400, 12.4839000)

  ;

  private String description;
  private WGS84Point point;

  WellKnownLocation(String description, double lat, double lon) {
    this.description = description;
    this.point = new WGS84Point(lat,lon);
  }

  public WGS84Point point() {
    return point;
  }

  public String description() { return description; }
}
