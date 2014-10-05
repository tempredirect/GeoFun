package com.logicalpractice.geofun.ean;

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical;

import java.net.URI;

/**
 *
 */
@Canonical
public class EanHotelInfo {
  @JsonProperty("hotelId")
  long id;
  String name;
  String address1, address2, city, postalCode, countryCode;
  String shortDescription;
  String locationDescription;
  URI thumbNailUrl;
}
