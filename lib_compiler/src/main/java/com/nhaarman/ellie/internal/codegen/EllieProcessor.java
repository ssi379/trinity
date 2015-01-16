/*
 * Copyright (C) 2014 Michael Pardo
 * Copyright (C) 2014 Niek Haarman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nhaarman.ellie.internal.codegen;

import com.nhaarman.ellie.internal.codegen.column.ColumnConverter;
import com.nhaarman.ellie.internal.codegen.column.validator.ColumnTypeValidator;
import com.nhaarman.ellie.internal.codegen.column.validator.ColumnValidator;
import com.nhaarman.ellie.internal.codegen.migration.CreateTableMigrationWriter;
import com.nhaarman.ellie.internal.codegen.repository.RepositoryInfo;
import com.nhaarman.ellie.internal.codegen.repository.RepositoryInfoFactory;
import com.nhaarman.ellie.internal.codegen.repository.RepositoryWriter;
import com.nhaarman.ellie.internal.codegen.repository.validator.RepositoryTypeValidator;
import com.nhaarman.ellie.internal.codegen.table.TableConverter;
import com.nhaarman.ellie.internal.codegen.table.TableInfo;
import com.nhaarman.ellie.internal.codegen.table.TableInfoFactory;
import com.nhaarman.ellie.internal.codegen.table.validator.TableTypeValidator;
import com.nhaarman.ellie.internal.codegen.table.validator.TableValidator;
import com.nhaarman.lib_setup.annotations.Column;
import com.nhaarman.lib_setup.annotations.Repository;
import com.nhaarman.lib_setup.annotations.Table;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

@SupportedAnnotationTypes("com.nhaarman.lib_setup.annotations.*")
public class EllieProcessor extends AbstractProcessor {


  private TableTypeValidator mTableTypeValidator;

  private ColumnTypeValidator mColumnTypeValidator;

  private TableValidator mTableValidator;

  private ColumnValidator mColumnValidator;

  private CreateTableMigrationWriter mCreateTableMigrationWriter;

  private RepositoryWriter mRepositoryWriter;

  private RepositoryTypeValidator mRepositoryTypeValidator;

  @Override
  public SourceVersion getSupportedSourceVersion() {
    return SourceVersion.latestSupported();
  }

  @Override
  public synchronized void init(final ProcessingEnvironment processingEnv) {
    super.init(processingEnv);
    mTableTypeValidator = new TableTypeValidator(processingEnv.getMessager());
    mColumnTypeValidator = new ColumnTypeValidator(processingEnv.getMessager());
    mRepositoryTypeValidator = new RepositoryTypeValidator(processingEnv.getMessager());

    mTableValidator = new TableValidator(processingEnv.getMessager());
    mColumnValidator = new ColumnValidator(processingEnv.getMessager());

    mCreateTableMigrationWriter = new CreateTableMigrationWriter(
        processingEnv.getFiler(),
        new TableConverter(new ColumnConverter())
    );
    mRepositoryWriter = new RepositoryWriter(processingEnv.getFiler());
  }

  @Override
  public synchronized boolean process(final Set<? extends TypeElement> annotations,
                                      final RoundEnvironment roundEnv) {

    /* Validate individual elements */
    Set<? extends Element> tableElements = roundEnv.getElementsAnnotatedWith(Table.class);
    mTableTypeValidator.validate(tableElements);

    Set<? extends Element> columnElements = roundEnv.getElementsAnnotatedWith(Column.class);
    mColumnTypeValidator.validate(columnElements);

    Set<? extends Element> repositoryElements = roundEnv.getElementsAnnotatedWith(Repository.class);
    mRepositoryTypeValidator.validate(repositoryElements);

    /* Gather information about repositories, tables and columns */
    Collection<TableInfo> tableInfos = new TableInfoFactory().createTableInfos(tableElements, roundEnv);

    mTableValidator.validate(tableInfos);

    for (TableInfo tableInfo : tableInfos) {
      mColumnValidator.validate(tableInfo.getColumns());
    }

    Collection<RepositoryInfo> repositoryInfos = new RepositoryInfoFactory().createRepositoryInfo(repositoryElements, tableInfos, roundEnv);

    for (RepositoryInfo repositoryInfo : repositoryInfos) {
      try {
        mRepositoryWriter.writeRepository(repositoryInfo);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return true;
  }
}