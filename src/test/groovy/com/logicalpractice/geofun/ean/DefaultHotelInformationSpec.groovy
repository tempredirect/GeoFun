package com.logicalpractice.geofun.ean

import ratpack.http.client.HttpClient
import ratpack.http.client.HttpClients
import ratpack.launch.LaunchConfigBuilder
import ratpack.launch.internal.DefaultLaunchConfig
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

/**
 *
 */
class DefaultHotelInformationSpec extends Specification {

  @Shared HttpClient httpClient = HttpClients.httpClient(LaunchConfigBuilder.noBaseDir().build())

  @Subject DefaultHotelInformation hotelInformation = new DefaultHotelInformation(httpClient)

  def "hotelInfo should return multiple results"() {
    when:
    def result = hotelInformation.hotelInfo([200713, 200262]).toList().toBlocking().first()

    then:
    result.id.size() == 2
  }

}
