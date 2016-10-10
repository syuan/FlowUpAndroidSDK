/*
 * Copyright (C) 2016 Go Karumi S.L.
 */

package com.flowup.reporter.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Reports {

  private final transient List<String> reportsIds;
  @SerializedName("appPackage") private final String appPackage;
  @SerializedName("installationUUID") private final String uuid;
  @SerializedName("deviceModel") private final String deviceModel;
  @SerializedName("screenDensity") private final String screenDensity;
  @SerializedName("screenSize") private final String screenSize;
  @SerializedName("numberOfCores") private final int numberOfCores;
  @SerializedName("network") private final List<NetworkMetricReport> networkMetricsReports;
  @SerializedName("ui") private final List<UIMetricReport> uiMetricsReports;

  public Reports(List<String> reportsIds, String appPackage, String uuid, String deviceModel, String screenDensity,
      String screenSize, int numberOfCores, List<NetworkMetricReport> networkMetricsReports,
      List<UIMetricReport> uiMetricsReports) {
    this.reportsIds = reportsIds;
    this.appPackage = appPackage;
    this.uuid = uuid;
    this.deviceModel = deviceModel;
    this.screenDensity = screenDensity;
    this.screenSize = screenSize;
    this.numberOfCores = numberOfCores;
    this.networkMetricsReports = networkMetricsReports;
    this.uiMetricsReports = uiMetricsReports;
  }

  public List<String> getReportsIds() {
    return reportsIds;
  }
}
