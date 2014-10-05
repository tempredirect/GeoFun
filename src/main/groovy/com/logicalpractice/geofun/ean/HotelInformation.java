package com.logicalpractice.geofun.ean;

/**
 *
 */
public interface HotelInformation {

  rx.Observable<EanHotelInfo> hotelInfo(Iterable<Long> hotelIds);
}
