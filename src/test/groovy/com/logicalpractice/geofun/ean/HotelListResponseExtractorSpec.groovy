package com.logicalpractice.geofun.ean

import spock.lang.Specification
import spock.lang.Subject

/**
 *
 */
@Subject(HotelListResponseExtractor)
class HotelListResponseExtractorSpec extends Specification {

  def "error should be detected"() {
    given:
    def extractor = new HotelListResponseExtractor(new ByteArrayInputStream("{\"HotelListResponse\":{\"EanWsError\":{\"itineraryId\":-1,\"handling\":\"UNRECOVERABLE\",\"category\":\"RESULT_NULL\",\"exceptionConditionId\":-1,\"presentationMessage\":\"TravelNow.com cannot service this request.\",\"verboseMessage\":\"No Response Available. \",\"ServerInfo\":{\"@instance\":\"178\",\"@timestamp\":\"1412539468\",\"@serverTime\":\"15:04:28.167-0500\"}},\"customerSessionId\":\"0ABAAAB2-BCBE-C391-48E2-1E5D2E395E92\"}}".bytes))

    expect:
    extractor.error
  }

  def "a valid response should parse"() {
    given:
    def extractor = new HotelListResponseExtractor(new ByteArrayInputStream("{\"HotelListResponse\":{\"customerSessionId\":\"0ABAAA73-176F-7914-8E12-E8F3C29043A0\",\"numberOfRoomsRequested\":0,\"moreResultsAvailable\":false,\"HotelList\":{\"@size\":\"2\",\"@activePropertyCount\":\"2\",\"HotelSummary\":[{\"@order\":\"0\",\"hotelId\":200713,\"name\":\"Park Inn by Radisson Hotel London Heathrow\",\"address1\":\"Bath Road\",\"address2\":\"Heathrow, Middlesex\",\"city\":\"West Drayton\",\"postalCode\":\"UB7 0DU\",\"countryCode\":\"GB\",\"airportCode\":\"LHR\",\"propertyCategory\":1,\"hotelRating\":0,\"confidenceRating\":55,\"amenityMask\":1277955,\"tripAdvisorRating\":3.5,\"tripAdvisorReviewCount\":741,\"tripAdvisorRatingUrl\":\"http:\\/\\/www.tripadvisor.com\\/img\\/cdsi\\/img2\\/ratings\\/traveler\\/3.5-12345-4.gif\",\"locationDescription\":\"Near the airport\",\"shortDescription\":\"&lt;p&gt;&lt;b&gt;Property Location&lt;\\/b&gt; &lt;br \\/&gt;With a stay at Park Inn by Radisson Hotel London Heathrow in West Drayton, you&apos;ll be near the airport and close to Stockley Park. This 4-star hotel is within the\",\"highRate\":230.0113,\"lowRate\":127.7499,\"rateCurrencyCode\":\"USD\",\"latitude\":51.48184,\"longitude\":-0.45143,\"proximityDistance\":-1,\"proximityUnit\":\"MI\",\"hotelInDestination\":false,\"thumbNailUrl\":\"\\/hotels\\/1000000\\/800000\\/791400\\/791325\\/791325_131_t.jpg\",\"deepLink\":\"http:\\/\\/www.travelnow.com\\/templates\\/55505\\/hotels\\/200713\\/overview?lang=en&amp;currency=USD&amp;standardCheckin=null\\/null\\/null&amp;standardCheckout=null\\/null\\/null\"},{\"@order\":\"1\",\"hotelId\":200262,\"name\":\"Knightsbridge Green Hotel\",\"address1\":\"159 Knightsbridge\",\"city\":\"London\",\"postalCode\":\"SW1X 7PD\",\"countryCode\":\"GB\",\"airportCode\":\"LCY\",\"propertyCategory\":1,\"hotelRating\":3,\"confidenceRating\":52,\"amenityMask\":32768,\"tripAdvisorRating\":2,\"tripAdvisorReviewCount\":430,\"tripAdvisorRatingUrl\":\"http:\\/\\/www.tripadvisor.com\\/img\\/cdsi\\/img2\\/ratings\\/traveler\\/2.0-12345-4.gif\",\"locationDescription\":\"Near Harrod&apos;s\",\"shortDescription\":\"&lt;p&gt;&lt;b&gt;Property Location&lt;\\/b&gt; &lt;br \\/&gt;A stay at Knightsbridge Green Hotel places you in the heart of London, walking distance from Harrod&apos;s and Beauchamp Place. This hotel is close to Imperial College\",\"highRate\":706.5352,\"lowRate\":302.8178,\"rateCurrencyCode\":\"USD\",\"latitude\":51.50166,\"longitude\":-0.16252,\"proximityDistance\":-1,\"proximityUnit\":\"MI\",\"hotelInDestination\":false,\"thumbNailUrl\":\"\\/hotels\\/1000000\\/50000\\/47700\\/47613\\/47613_27_t.jpg\",\"deepLink\":\"http:\\/\\/www.travelnow.com\\/templates\\/55505\\/hotels\\/200262\\/overview?lang=en&amp;currency=USD&amp;standardCheckin=null\\/null\\/null&amp;standardCheckout=null\\/null\\/null\"}]}}}".bytes))

    expect:
    ! extractor.error
    extractor.eanHotelInfos.size() == 2
  }

}
