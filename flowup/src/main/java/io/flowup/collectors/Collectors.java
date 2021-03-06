/*
 * Copyright (C) 2016 Go Karumi S.L.
 */

package io.flowup.collectors;

import android.app.Application;
import android.os.Build;
import android.support.annotation.RequiresApi;
import io.flowup.android.App;
import io.flowup.android.AppTrafficStats;
import io.flowup.android.CPU;
import io.flowup.android.Device;
import io.flowup.android.FileSystem;
import io.flowup.android.MainThread;
import io.flowup.metricnames.MetricNamesGenerator;
import io.flowup.utils.Time;
import java.util.concurrent.TimeUnit;

public class Collectors {

  private static UpdatableCollector frameTimeCollector;

  @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
  public static UpdatableCollector getFrameTimeCollector(Application application) {
    if (frameTimeCollector == null) {
      frameTimeCollector = new FrameTimeCollector(application, getMetricNamesGenerator(application),
          new MainThread());
    }
    return frameTimeCollector;
  }

  @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
  public static Collector getActivityLifecycleCollector(Application application) {
    return new ActivityLifecycleCollector(application, getMetricNamesGenerator(application),
        new MainThread(), new Time());
  }

  @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
  public static Collector getActivityVisibleCollector(Application application) {
    return new ActivityVisibleCollector(application, getMetricNamesGenerator(application),
        new Time());
  }

  public static Collector getNetworkUsageCollector(Application application, long samplingInterval,
      TimeUnit timeUnit) {
    return new NetworkUsageCollector(new AppTrafficStats(), getMetricNamesGenerator(application),
        samplingInterval, timeUnit, new App(application));
  }

  public static Collector getCPUUsageCollector(Application application, int samplingInterval,
      TimeUnit samplingTimeUnit, CPU cpu) {
    return new CPUUsageCollector(getMetricNamesGenerator(application), samplingInterval,
        samplingTimeUnit, cpu, new App(application));
  }

  public static Collector getMemoryUsageCollector(Application application, int samplingInterval,
      TimeUnit samplingTimeUnit, App app) {
    return new MemoryUsageCollector(getMetricNamesGenerator(application), samplingInterval,
        samplingTimeUnit, app);
  }

  public static Collector getDiskUsageCollector(Application application, int samplingInterval,
      TimeUnit samplingTimeUnit, FileSystem fileSystem) {
    return new DiskUsageCollector(getMetricNamesGenerator(application), samplingInterval,
        samplingTimeUnit, fileSystem, new App(application));
  }

  private static MetricNamesGenerator getMetricNamesGenerator(Application application) {
    return new MetricNamesGenerator(new App(application), new Device(application), new Time());
  }
}
