package com.logicalpractice.geofun;

import ch.hsr.geohash.WGS84Point;
import ch.hsr.geohash.util.VincentyGeodesy;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 */
public final class Hotel {
  private final int id;
  private final String name;
  private final WGS84Point location;

  public Hotel(int id,String name, WGS84Point location) {
    this.id = id;
    this.name = name;
    this.location = location;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @JsonIgnore
  public WGS84Point getLocation() {
    return location;
  }

  public double getLatitude() { return location.getLatitude(); }
  public double getLongitude() { return location.getLongitude(); }

  public int distanceTo(WGS84Point latLon) {
    return (int) VincentyGeodesy.distanceInMeters(location, latLon);
  }
}
