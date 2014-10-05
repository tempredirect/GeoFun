package com.logicalpractice.geofun.ean;

import com.google.common.base.Joiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.http.client.HttpClient;
import ratpack.http.client.RequestSpec;
import rx.Observable;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 *
 */
@Singleton
public class DefaultHotelInformation implements HotelInformation {
  private final Logger logger = LoggerFactory.getLogger(DefaultHotelInformation.class);

  private final HttpClient httpClient;

  private final String apiKey = "cbrzfta369qwyrm9t5b8y8kf";
  private final String cid = "55505";

  @Inject
  public DefaultHotelInformation(HttpClient httpClient) {
    this.httpClient = httpClient;
  }

  @Override
  public Observable<EanHotelInfo> hotelInfo(Iterable<Long> hotelIds) {
    return Observable.create((Observable.OnSubscribe<EanHotelInfo>)(subscription)  -> {
          httpClient.get((request)->{
            request
                .method("GET")
                .url(
                    (urlSpec) ->
                        urlSpec
                            .host("api.eancdn.com")
                            .path("/ean-services/rs/hotel/v3/list")
                            .params(
                                "cid", cid,
                                "minorRev", "99",
                                "apiKey", apiKey,
                                "locale", "en_US",
                                "currencyCode", "USD",
                                "hotelIdList", Joiner.on(',').join(hotelIds)
                            ))
                .headers((headers) -> headers.set("Accept", "application/json"));
          })
              .onError(subscription::onError)
              .then(
                  (response) -> {
                    logger.info("response received: {}, body: {}", response.getStatus(), response.getBody().getText());
                    new HotelListResponseExtractor(response.getBody().getInputStream());
                    subscription.onNext(new EanHotelInfo());
                    subscription.onCompleted();
                  });
        });
  }
}
