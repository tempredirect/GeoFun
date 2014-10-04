package com.ean.geofun.search;

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
    return Files.lines(path)
        .skip(1)
        .map(
            (line) -> {
              String[] parts = line.split("\\|");
              int hotelId = Integer.parseInt(parts[0]);
              float lat = Float.parseFloat(parts[9]);
              float lon = Float.parseFloat(parts[10]);
              return new Hotel(hotelId, new float[]{lat, lon});
            });
  }
}
