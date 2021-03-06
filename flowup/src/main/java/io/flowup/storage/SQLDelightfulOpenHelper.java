/*
 * Copyright (C) 2016 Go Karumi S.L.
 */

package io.flowup.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import io.flowup.BuildConfig;
import io.flowup.config.storage.ConfigModel;
import io.flowup.logger.Logger;
import io.flowup.reporter.storage.MetricModel;
import io.flowup.reporter.storage.ReportModel;

public class SQLDelightfulOpenHelper extends SQLiteOpenHelper {

  private static SQLDelightfulOpenHelper INSTANCE;

  private static final String DB_NAME = "flowup.db";
  private static final int DB_VERSION = BuildConfig.VERSION_CODE;
  private static final String REPORT_TIMESTAMP_INDEX =
      "CREATE INDEX search_report_timestamp ON report (report_timestamp DESC)";
  private static final String REPORT_ID_INDEX = "CREATE INDEX report_id ON metric (report_id)";

  public static synchronized SQLDelightfulOpenHelper getInstance(Context context) {
    if (INSTANCE == null) {
      INSTANCE = new SQLDelightfulOpenHelper(context);
    }
    return INSTANCE;
  }

  private SQLDelightfulOpenHelper(Context context) {
    super(context, DB_NAME, null, DB_VERSION);
  }

  @Override public void onConfigure(SQLiteDatabase db) {
    super.onConfigure(db);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
      db.setForeignKeyConstraintsEnabled(true);
    }
  }

  @Override public void onCreate(SQLiteDatabase db) {
    Logger.d("Database onCreate method invoked");
    createTables(db);
  }

  @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    if (oldVersion != newVersion) {
      dropTables(db);
      createTables(db);
    }
  }

  private void createTables(SQLiteDatabase db) {
    db.execSQL(ConfigModel.CREATE_TABLE);
    db.execSQL(ReportModel.CREATE_TABLE);
    db.execSQL(MetricModel.CREATE_TABLE);
    db.execSQL(REPORT_TIMESTAMP_INDEX);
    db.execSQL(REPORT_ID_INDEX);
  }

  private void dropTables(SQLiteDatabase db) {
    dropTable(db, ConfigModel.TABLE_NAME);
    dropTable(db, ReportModel.TABLE_NAME);
    dropTable(db, MetricModel.TABLE_NAME);
  }

  private void dropTable(SQLiteDatabase db, String tableName) {
    db.execSQL("DROP TABLE " + tableName);
  }
}