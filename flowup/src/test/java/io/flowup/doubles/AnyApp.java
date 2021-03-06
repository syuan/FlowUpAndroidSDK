/*
 * Copyright (C) 2016 Go Karumi S.L.
 */

package io.flowup.doubles;

import android.content.Context;
import io.flowup.android.App;

import static org.mockito.Mockito.mock;

public class AnyApp extends App {

  public AnyApp() {
    this(mock(Context.class));
  }

  public AnyApp(Context context) {
    super(context);
  }

  @Override public String getAppPackageName() {
    return "io-flowup-androidsdk";
  }

  @Override public String getVersionName() {
    return "1-0-0";
  }
}
