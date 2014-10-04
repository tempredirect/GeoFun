package com.ean.geofun;

import ch.hsr.geohash.WGS84Point;
import com.google.common.base.Charsets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 *
 */
public class ActivePropertyList {

  private final Path path;

  public ActivePropertyList(Path path) {
    this.path = path;
  }

  public Stream<Hotel> stream() throws IOException {
    return Files.lines(path, Charsets.ISO_8859_1)
        .skip(1)
        .map(
            (line) -> {
              String[] parts = line.split("\\|");
              int hotelId = Integer.parseInt(parts[0]);
              double lat = Double.parseDouble(parts[9]);
              double lng = Double.parseDouble(parts[10]);
              String name = parts[2];
              return new Hotel(hotelId, name, new WGS84Point(lat, lng));
            });
  }
}
