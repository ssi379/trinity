package com.nhaarman.trinity.internal.codegen.table.repository.writer;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.nhaarman.trinity.internal.codegen.table.Column;
import com.nhaarman.trinity.internal.codegen.table.ColumnMethod;
import com.nhaarman.trinity.internal.codegen.table.TableClass;
import com.nhaarman.trinity.internal.codegen.table.repository.RepositoryClass;
import com.nhaarman.trinity.internal.codegen.table.repository.RepositoryMethod;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.MethodSpec.Builder;
import com.squareup.javapoet.TypeSpec;
import java.io.IOException;
import java.io.Writer;
import javax.annotation.processing.Filer;
import javax.lang.model.type.TypeMirror;
import javax.tools.JavaFileObject;

import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.PUBLIC;

@SuppressWarnings("HardCodedStringLiteral")
public class RepositoryWriter {

  private final Filer mFiler;

  private RepositoryClass mRepositoryClass;

  private TableClass mTableClass;

  private CreatorFactory mCreatorFactory;

  public RepositoryWriter(final Filer filer) {
    mFiler = filer;
  }

  public synchronized void writeRepositoryClass(final RepositoryClass repositoryClass)
      throws IOException {
    mRepositoryClass = repositoryClass;
    mTableClass = repositoryClass.getTableClass();

    JavaFileObject sourceFile = mFiler.createSourceFile(createRepositoryClassName());

    MethodSpec readCursorSpec = readCursor();
    MethodSpec createContentValuesSpec = createContentValues();

    mCreatorFactory = new CreatorFactory(mRepositoryClass, readCursorSpec, createContentValuesSpec);

    TypeSpec.Builder repositoryBuilder = TypeSpec.classBuilder(createRepositoryClassName())
        .addModifiers(PUBLIC, FINAL)
        .addOriginatingElement(mRepositoryClass.getRepositoryElement())
        .addField(mDatabase())
        .addMethod(constructor())
        .addMethod(createContentValuesSpec)
        .addMethod(readCursorSpec);

    if (mRepositoryClass.isInterface()) {
      repositoryBuilder.addSuperinterface(ClassName.get(mRepositoryClass.getRepositoryElement()));
    } else {
      repositoryBuilder.superclass(ClassName.get(mRepositoryClass.getRepositoryElement()));
    }

    for (RepositoryMethod repositoryMethod : mRepositoryClass.getMethods()) {
      repositoryBuilder.addMethod(mCreatorFactory.creatorFor(repositoryMethod).create());
    }

    JavaFile javaFile =
        JavaFile.builder(mRepositoryClass.getPackageName(), repositoryBuilder.build()).build();

    Writer writer = sourceFile.openWriter();
    javaFile.writeTo(writer);
    writer.flush();
    writer.close();
  }

  private FieldSpec mDatabase() {
    return FieldSpec.builder(SQLiteDatabase.class, "mDatabase", PRIVATE, FINAL)
        .addJavadoc("The {@link $T} that is used for persistence.\n", SQLiteDatabase.class)
        .build();
  }

  private MethodSpec constructor() {
    return MethodSpec.constructorBuilder()
        .addModifiers(PUBLIC)
        .addParameter(SQLiteDatabase.class, "database", FINAL)
        .addStatement("mDatabase = database")
        .build();
  }

  private MethodSpec createContentValues() {
    Builder methodBuilder =
        MethodSpec.methodBuilder("createContentValues")
            .addModifiers(PUBLIC)
            .addParameter(ClassName.get(mTableClass.getEntityTypeElement()), "entity", FINAL)
            .returns(ContentValues.class)
            .addStatement("$T result = new $T()", ContentValues.class, ContentValues.class)
            .addCode("\n");

    for (Column column : mTableClass.getColumns()) {
      ColumnMethod getter = column.getter();
      methodBuilder.addStatement("result.put($S, entity.$L())", column.getName(), getter.getName());
    }

    methodBuilder.addCode("\n");
    methodBuilder.addStatement("return result");

    return methodBuilder.build();
  }

  private MethodSpec readCursor() {
    Builder methodBuilder =
        MethodSpec.methodBuilder("read")
            .addModifiers(PUBLIC)
            .addParameter(Cursor.class, "cursor", FINAL)
            .returns(ClassName.get(mTableClass.getEntityTypeElement()))
            .addStatement("$T result = new $T()", mTableClass.getEntityTypeElement(),
                mTableClass.getEntityTypeElement())
            .addCode("\n");

    for (Column column : mTableClass.getColumns()) {
      ColumnMethod setter = column.setter();
      TypeMirror type = setter.getType();
      String cursorMethodName;
      switch (type.toString()) {
        case "java.lang.Integer":
        case "int":
          cursorMethodName = "getInt";
          break;
        case "java.lang.Long":
        case "long":
          cursorMethodName = "getLong";
          break;
        case "java.lang.String":
          cursorMethodName = "getString";
          break;
        default:
          throw new UnsupportedOperationException("Unknown type: " + type);
      }

      methodBuilder.addStatement(
          "result.$L(cursor.$L(cursor.getColumnIndex($S)))",
          setter.getName(),
          cursorMethodName,
          column.getName()
      );
    }

    return methodBuilder.addCode("\n")
        .addStatement("return result")
        .build();
  }

  private String createRepositoryClassName() {
    return String.format("Trinity_%s", mRepositoryClass.getClassName());
  }
}
