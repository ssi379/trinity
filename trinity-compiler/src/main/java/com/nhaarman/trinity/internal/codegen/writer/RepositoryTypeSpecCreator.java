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

package com.nhaarman.trinity.internal.codegen.writer;

import com.nhaarman.trinity.internal.codegen.SupportedMethod;
import com.nhaarman.trinity.internal.codegen.SupportedMethod.SupportedMethodVisitor;
import com.nhaarman.trinity.internal.codegen.data.ColumnMethod;
import com.nhaarman.trinity.internal.codegen.data.ColumnMethodRepository;
import com.nhaarman.trinity.internal.codegen.data.RepositoryClass;
import com.nhaarman.trinity.internal.codegen.data.SerializerClassRepository;
import com.nhaarman.trinity.internal.codegen.data.TableClass;
import com.nhaarman.trinity.internal.codegen.data.RepositoryMethod;
import com.nhaarman.trinity.internal.codegen.writer.method.CreateContentValuesMethodCreator;
import com.nhaarman.trinity.internal.codegen.writer.method.MethodCreatorFactory;
import com.nhaarman.trinity.internal.codegen.writer.method.readcursor.ReadCursorMethodCreator;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jetbrains.annotations.NotNull;

import static com.nhaarman.trinity.internal.codegen.AndroidClasses.SQLITE_DATABASE;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.PUBLIC;

@SuppressWarnings("HardCodedStringLiteral")
public class RepositoryTypeSpecCreator {

  private static final String REPOSITORY_CLASS_NAME = "Trinity%s";

  private static final String FIELD_NAME_DATABASE = "mDatabase";

  @NotNull
  private final RepositoryClass mRepositoryClass;

  @NotNull
  private final TableClass mTableClass;

  @NotNull
  private final ColumnMethodRepository mColumnMethodRepository;

  @NotNull final SerializerClassRepository mSerializerClassRepository;

  @NotNull
  private final Builder mRepositoryBuilder;

  private MethodCreatorFactory mMethodCreatorFactory;

  public RepositoryTypeSpecCreator(@NotNull final RepositoryClass repositoryClass,
                                   @NotNull final TableClass tableClass,
                                   @NotNull final ColumnMethodRepository columnMethodRepository,
                                   @NotNull final SerializerClassRepository serializerClassRepository) {
    mRepositoryClass = repositoryClass;
    mTableClass = tableClass;
    mColumnMethodRepository = columnMethodRepository;
    mSerializerClassRepository = serializerClassRepository;

    mRepositoryBuilder = TypeSpec.classBuilder(String.format(REPOSITORY_CLASS_NAME, mRepositoryClass.getClassName()));
  }

  @NotNull
  public TypeSpec create() {
    FieldSpec databaseFieldSpec = mDatabase();
    MethodSpec constructor = constructor();
    MethodSpec readCursorSpec = readCursor();
    MethodSpec createContentValuesSpec = createContentValues();

    ColumnMethod primaryKeySetter = mColumnMethodRepository.findPrimaryKeySetter(mTableClass.getFullyQualifiedName());
    ColumnMethod primaryKeyGetter = mColumnMethodRepository.findPrimaryKeyGetter(mTableClass.getFullyQualifiedName());

    mMethodCreatorFactory = new MethodCreatorFactory(
        mTableClass,
        databaseFieldSpec,
        readCursorSpec,
        createContentValuesSpec,
        primaryKeySetter,
        primaryKeyGetter
    );

    mRepositoryBuilder.addModifiers(PUBLIC, FINAL);
    mRepositoryBuilder.addOriginatingElement(mRepositoryClass.getElement());
    mRepositoryBuilder.addField(databaseFieldSpec);
    mRepositoryBuilder.addMethod(constructor);
    mRepositoryBuilder.addMethod(createContentValuesSpec);
    mRepositoryBuilder.addMethod(readCursorSpec);

    ClassName superclass = ClassName.get(mRepositoryClass.getPackageName(), mRepositoryClass.getClassName());
    if (mRepositoryClass.isInterface()) {
      mRepositoryBuilder.addSuperinterface(superclass);
    } else {
      mRepositoryBuilder.superclass(superclass);
    }

    List<RepositoryMethod> methods = new ArrayList<>(mRepositoryClass.getMethods());
    Collections.sort(methods);

    for (final RepositoryMethod repositoryMethod : methods) {
      SupportedMethod supportedMethod = SupportedMethod.from(repositoryMethod.getMethodName());
      if (supportedMethod == null) {
        throw new IllegalArgumentException(String.format("Method '%s' is not supported", repositoryMethod.getMethodName()));
      }

      mRepositoryBuilder.addMethod(supportedMethod.accept(new MyMethodVisitor(repositoryMethod)));
    }

    return mRepositoryBuilder.build();
  }

  /**
   * Creates the SQLiteDatabase field.
   */
  private FieldSpec mDatabase() {
    return FieldSpec.builder(SQLITE_DATABASE, FIELD_NAME_DATABASE, PRIVATE, FINAL)
        .addJavadoc("The {@link $T} that is used for persistence.\n", SQLITE_DATABASE)
        .build();
  }

  /**
   * Creates the constructor.
   */
  private MethodSpec constructor() {
    return MethodSpec.constructorBuilder()
        .addModifiers(PUBLIC)
        .addParameter(SQLITE_DATABASE, "database", FINAL)
        .addStatement(FIELD_NAME_DATABASE + " = database")
        .build();
  }

  /**
   * Creates the createContentValues method.
   */
  private MethodSpec createContentValues() {
    return new CreateContentValuesMethodCreator(mTableClass, mColumnMethodRepository, mSerializerClassRepository).create();
  }

  /**
   * Creates the readCursor method.
   */
  @NotNull
  private MethodSpec readCursor() {
    return new ReadCursorMethodCreator(mTableClass, mColumnMethodRepository, mSerializerClassRepository).create();
  }

  private class MyMethodVisitor implements SupportedMethodVisitor<MethodSpec> {

    private final RepositoryMethod mRepositoryMethod;

    MyMethodVisitor(final RepositoryMethod repositoryMethod) {
      mRepositoryMethod = repositoryMethod;
    }

    @NotNull
    @Override
    public MethodSpec visitFind() {
      return mMethodCreatorFactory.findMethodCreator(mRepositoryMethod).create();
    }

    @NotNull
    @Override
    public MethodSpec visitFindAll() {
      return mMethodCreatorFactory.findAllMethodCreator(mRepositoryMethod).create();
    }

    @NotNull
    @Override
    public MethodSpec visitCreate() {
      return mMethodCreatorFactory.createMethodCreator(mRepositoryMethod).create();
    }

    @NotNull
    @Override
    public MethodSpec visitCreateAll() {
      return mMethodCreatorFactory.createAllMethodCreator(mRepositoryMethod).create();
    }
  }
}
