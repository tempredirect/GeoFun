package com.logicalpractice.geofun.ean

import groovy.json.JsonSlurper

/**
 *
 */
class HotelListResponseExtractor {

  def json

  HotelListResponseExtractor(InputStream inputStream) {
    json = new JsonSlurper().parse(inputStream)
  }

  boolean isError() {
    json.HotelListResponse.EanWsError
  }

  List<EanHotelInfo> getEanHotelInfos() {
    json.HotelListResponse.HotelList.HotelSummary.collect {
      new EanHotelInfo(
          id: it.hotelId,
          name: it.name,
          shortDescription: it.shortDescription,
          thumbNailUrl: URI.create(it.thumbNailUrl)
      )
    }
  }
}
