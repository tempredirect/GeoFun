package com.ean.geofun.search;

import com.ean.geofun.ActivePropertyList;
import com.ean.geofun.Hotel;

import java.nio.file.Paths;
import java.util.List;

/**
 *
 */
public class Main {

  public static void main(String[] args) throws Exception {
    Class [] searches = new Class[] {LinearSearch.class, ParallelLinearSearch.class, GeoHashSearch.class};

    ActivePropertyList activePropertyList = new ActivePropertyList(Paths.get("ActivePropertyList.txt"));
    for (Class search : searches) {
      Search instance = (Search)search.getConstructor(ActivePropertyList.class).newInstance(activePropertyList);

      for (WellKnownLocation location : WellKnownLocation.values()) {
        List<Hotel> results = instance.search(location.point(), 20);
        System.out.printf("%s - %s 20km : %s\n", search.getSimpleName(), location.name(),  results.size());
      }
    }
  }
}
