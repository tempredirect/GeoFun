package com.logicalpractice.geofun.ean;

import com.google.inject.AbstractModule;

import javax.inject.Singleton;

/**
 *
 */
public class EanModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(HotelInformation.class).to(DefaultHotelInformation.class).in(Singleton.class);
  }
}
