package com.nhaarman.trinity.internal.codegen.writer;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.nhaarman.trinity.internal.codegen.data.Column;
import com.nhaarman.trinity.internal.codegen.data.ColumnMethod;
import com.nhaarman.trinity.internal.codegen.data.RepositoryClass;
import com.nhaarman.trinity.internal.codegen.data.RepositoryMethod;
import com.nhaarman.trinity.internal.codegen.data.TableClass;
import com.nhaarman.trinity.internal.codegen.writer.method.CreatorFactory;
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
import org.jetbrains.annotations.NotNull;

import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.PUBLIC;

@SuppressWarnings("HardCodedStringLiteral")
public class RepositoryWriter {

  private static final String FIELD_NAME_DATABASE = "mDatabase";
  
  @NotNull
  private final Filer mFiler;

  @NotNull
  private final RepositoryClass mRepositoryClass;

  @NotNull
  private final TableClass mTableClass;

  public RepositoryWriter(@NotNull final Filer filer, @NotNull final RepositoryClass repositoryClass) {
    mFiler = filer;
    mRepositoryClass = repositoryClass;
    mTableClass = repositoryClass.getTableClass();
  }

  public void writeRepositoryClass() throws IOException {
    FieldSpec databaseFieldSpec = mDatabase();
    MethodSpec constructor = constructor();
    MethodSpec readCursorSpec = readCursor();
    MethodSpec createContentValuesSpec = createContentValues();

    CreatorFactory creatorFactory = new CreatorFactory(mRepositoryClass, databaseFieldSpec, readCursorSpec, createContentValuesSpec);

    TypeSpec.Builder repositoryBuilder = TypeSpec.classBuilder(createRepositoryClassName());
    repositoryBuilder.addModifiers(PUBLIC, FINAL);
    repositoryBuilder.addOriginatingElement(mRepositoryClass.getRepositoryElement());
    repositoryBuilder.addField(databaseFieldSpec);
    repositoryBuilder.addMethod(constructor);
    repositoryBuilder.addMethod(createContentValuesSpec);
    repositoryBuilder.addMethod(readCursorSpec);

    ClassName superclass = ClassName.get(mRepositoryClass.getRepositoryElement());
    if (mRepositoryClass.isInterface()) {
      repositoryBuilder.addSuperinterface(superclass);
    } else {
      repositoryBuilder.superclass(superclass);
    }

    for (RepositoryMethod repositoryMethod : mRepositoryClass.getMethods()) {
      repositoryBuilder.addMethod(creatorFactory.creatorFor(repositoryMethod).create());
    }

    TypeSpec typeSpec = repositoryBuilder.build();
    writeToFile(typeSpec);
  }

  private void writeToFile(final TypeSpec typeSpec) throws IOException {
    JavaFile javaFile = JavaFile.builder(mRepositoryClass.getPackageName(), typeSpec).build();

    JavaFileObject sourceFile = mFiler.createSourceFile(createRepositoryClassName());
    Writer writer = sourceFile.openWriter();
    javaFile.writeTo(writer);
    writer.flush();
    writer.close();
  }

  private FieldSpec mDatabase() {
    return FieldSpec.builder(SQLiteDatabase.class, FIELD_NAME_DATABASE, PRIVATE, FINAL)
        .addJavadoc("The {@link $T} that is used for persistence.\n", SQLiteDatabase.class)
        .build();
  }

  private MethodSpec constructor() {
    return MethodSpec.constructorBuilder()
        .addModifiers(PUBLIC)
        .addParameter(SQLiteDatabase.class, "database", FINAL)
        .addStatement(FIELD_NAME_DATABASE + " = database")
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
