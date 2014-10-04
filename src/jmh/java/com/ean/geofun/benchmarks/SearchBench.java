package com.ean.geofun.benchmarks;

import com.ean.geofun.ActivePropertyList;
import com.ean.geofun.search.GeoHashSearch;
import com.ean.geofun.search.GeoHashSearch2;
import com.ean.geofun.search.LinearSearch;
import com.ean.geofun.search.ParallelLinearSearch;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import static com.ean.geofun.WellKnownLocation.NEW_YORK_CITY;

/**
 *
 */
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class SearchBench {

  @State(Scope.Benchmark)
  public static class LinearSearchHolder {
    LinearSearch search ;
    @Setup
    public void setup() throws IOException {
      search = new LinearSearch(new ActivePropertyList(Paths.get("src/ratpack/ActivePropertyList.txt")));
    }
  }

  @State(Scope.Benchmark)
  public static class ParallelLinearSearchHolder {
    ParallelLinearSearch search ;
    @Setup
    public void setup() throws IOException {
      search = new ParallelLinearSearch(new ActivePropertyList(Paths.get("src/ratpack/ActivePropertyList.txt")));
    }
  }

  @State(Scope.Benchmark)
  public static class GeoHashSearchHolder {
    GeoHashSearch search ;
    @Setup
    public void setup() throws IOException {
      search = new GeoHashSearch(new ActivePropertyList(Paths.get("src/ratpack/ActivePropertyList.txt")));
    }
  }

  @State(Scope.Benchmark)
  public static class GeoHashSearch2Holder {
    GeoHashSearch2 search ;
    @Setup
    public void setup() throws IOException {
      search = new GeoHashSearch2(new ActivePropertyList(Paths.get("src/ratpack/ActivePropertyList.txt")));
    }
  }

  @Benchmark
  public void measureLinearSearch(Blackhole bh, LinearSearchHolder searchHolder) {
    bh.consume(searchHolder.search.search(NEW_YORK_CITY.point(), 20));
  }

  @Benchmark
  public void measureParallelLinearSearch(Blackhole bh, ParallelLinearSearchHolder searchHolder) {
    bh.consume(searchHolder.search.search(NEW_YORK_CITY.point(), 20));
  }

  @Benchmark
  public void measureGeoHashSearch(Blackhole bh, GeoHashSearchHolder searchHolder) {
    bh.consume(searchHolder.search.search(NEW_YORK_CITY.point(), 20));
  }

  @Benchmark
  public void measureGeoHashSearch2(Blackhole bh, GeoHashSearch2Holder searchHolder) {
    bh.consume(searchHolder.search.search(NEW_YORK_CITY.point(), 20));
  }
}
