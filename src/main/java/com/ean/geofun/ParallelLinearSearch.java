package com.ean.geofun;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
public class ParallelLinearSearch extends LinearSearch {
  public ParallelLinearSearch(ActivePropertyList propertyList) throws IOException {
    super(propertyList);
  }

  @Override
  public List<Hotel> search(float[] latLon, int radiusM) {
    return hotels.parallelStream()
        .filter((hotel) -> hotel.distanceTo(latLon) <= radiusM)
        .collect(Collectors.toList());
  }

  public static void main(String[] args) throws IOException {
    ParallelLinearSearch search = new ParallelLinearSearch(new ActivePropertyList(Paths.get("ActivePropertyList.txt")));
    System.out.println("Loaded");
    List<Hotel> results = search.search(new float[]{40.7142700f, -74.0059700f}, 20);

    System.out.printf("New York 20km : %s\n", results.size());
  }
}
