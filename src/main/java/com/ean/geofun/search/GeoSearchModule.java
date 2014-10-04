package com.ean.geofun.search;

import com.ean.geofun.ActivePropertyList;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import javax.inject.Singleton;
import java.io.IOException;
import java.nio.file.Paths;

/**
 *
 */
public class GeoSearchModule extends AbstractModule {

  @Override
  protected void configure() {

  }

  @Singleton
  @Provides
  public Search search() throws IOException {
    return new GeoHashSearch(new ActivePropertyList(Paths.get("ActivePropertyList.txt")));
  }
}
