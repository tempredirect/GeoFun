package com.logicalpractice.geofun.search;

import com.logicalpractice.geofun.ActivePropertyList;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import ratpack.launch.LaunchConfig;

import javax.inject.Singleton;
import java.io.IOException;

/**
 *
 */
public class GeoSearchModule extends AbstractModule {

  @Override
  protected void configure() { }

  @Singleton
  @Provides
  public Search search(LaunchConfig launchConfig) throws IOException {
    return new GeoHashSearch2(new ActivePropertyList(launchConfig.getBaseDir().file("ActivePropertyList.txt")));
  }
}
