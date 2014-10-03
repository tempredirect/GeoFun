package com.ean.geofun;

import java.awt.geom.Point2D;

/**
 *
 */
public final class Hotel {
  private final int id;
  private final float [] location;

  public Hotel(int id, float [] location) {
    this.id = id;
    this.location = location;
  }

  public int getId() {
    return id;
  }

  public float [] getLocation() {
    return location;
  }

  public float getLatitude() { return location[0]; }
  public float getLongitude() { return location[1]; }

  public int distanceTo(float[] latLon) {
    return Points.distFrom(location[0], location[1], latLon[0], latLon[1]);
  }
}
