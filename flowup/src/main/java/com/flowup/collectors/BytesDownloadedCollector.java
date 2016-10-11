/*
 * Copyright (C) 2016 Go Karumi S.L.
 */

package com.flowup.collectors;

import android.net.TrafficStats;
import android.os.Process;
import android.util.Log;
import com.codahale.metrics.CachedGauge;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import com.flowup.metricnames.MetricNamesGenerator;
import java.util.concurrent.TimeUnit;

class BytesDownloadedCollector implements Collector {

  private final MetricNamesGenerator metricNamesGenerator;
  private final long samplingInterval;
  private final TimeUnit timeUnit;

  private long lastBytesSample;

  BytesDownloadedCollector(MetricNamesGenerator metricNamesGenerator, long samplingInterval,
      TimeUnit timeUnit) {
    this.metricNamesGenerator = metricNamesGenerator;
    this.samplingInterval = samplingInterval;
    this.timeUnit = timeUnit;
  }

  @Override public void initialize(MetricRegistry registry) {
    registry.register(metricNamesGenerator.getBytesDownloadedMetricsName(),
        new CachedGauge<Long>(samplingInterval, timeUnit) {
          @Override public Long loadValue() {
            int applicationUid = Process.myUid();
            long totalRxBytes = TrafficStats.getUidRxBytes(applicationUid);
            long rxBytes = totalRxBytes - lastBytesSample;
            lastBytesSample = totalRxBytes;
            Log.d("FlowUp", "Collecting http bytes downloaded metric-> " + rxBytes);
            new Gauge<Long>() {
              @Override public Long getValue() {
                return null;
              }
            };
            return rxBytes;
          }
        });
  }
}