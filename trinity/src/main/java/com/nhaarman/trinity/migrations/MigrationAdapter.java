/*
 * Copyright 2015 Niek Haarman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nhaarman.trinity.migrations;

import android.database.sqlite.SQLiteDatabase;
import org.jetbrains.annotations.NotNull;

public class MigrationAdapter implements Migration {

  private final int mVersion;
  private final long mOrder;

  public MigrationAdapter() {
    this(0);
  }

  public MigrationAdapter(final int version) {
    this(version, 0);
  }

  public MigrationAdapter(final int version, final long order) {
    mVersion = version;
    mOrder = order;
  }

  @Override
  public int getVersion() {
    return mVersion;
  }

  @Override
  public long getOrder() {
    return mOrder;
  }

  @Override
  public void beforeUp(final SQLiteDatabase database) {
  }

  @Override
  public void onUpgrade(final SQLiteDatabase database) {
  }

  @Override
  public void afterUp(final SQLiteDatabase database) {
  }

  @Override
  public void beforeDown(final SQLiteDatabase database) {
  }

  @Override
  public void onDowngrade(final SQLiteDatabase database) {
  }

  @Override
  public void afterDown(final SQLiteDatabase database) {
  }

  @Override
  public int compareTo(@NotNull final Migration another) {
    return Long.valueOf(mOrder).compareTo(another.getOrder());
  }
}