package com.ean.geofun.search;

import ch.hsr.geohash.WGS84Point;
import ch.hsr.geohash.util.VincentyGeodesy;

import java.awt.geom.Point2D;

/**
 *
 */
public final class Hotel {
  private final int id;
  private final WGS84Point location;

  public Hotel(int id, float [] location) {
    this.id = id;
    this.location = new WGS84Point(location[0], location[1]);
  }

  public int getId() {
    return id;
  }

  public WGS84Point getLocation() {
    return location;
  }

  public double getLatitude() { return location.getLatitude(); }
  public double getLongitude() { return location.getLongitude(); }

  public int distanceTo(WGS84Point latLon) {
    return (int) VincentyGeodesy.distanceInMeters(location, latLon);
  }
}
