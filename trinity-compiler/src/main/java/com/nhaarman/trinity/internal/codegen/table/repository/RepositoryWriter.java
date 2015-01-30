package com.nhaarman.trinity.internal.codegen.table.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.nhaarman.trinity.internal.codegen.table.Column;
import com.nhaarman.trinity.internal.codegen.table.ColumnMethod;
import com.nhaarman.trinity.internal.codegen.table.TableClass;
import com.nhaarman.trinity.internal.codegen.table.repository.RepositoryMethod.Parameter;
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

  public RepositoryWriter(final Filer filer) {
    mFiler = filer;
  }

  public synchronized void writeRepositoryClass(final RepositoryClass repositoryClass)
      throws IOException {
    mRepositoryClass = repositoryClass;
    mTableClass = repositoryClass.getTableClass();

    JavaFileObject sourceFile = mFiler.createSourceFile(createRepositoryClassName());

    TypeSpec.Builder repositoryBuilder = TypeSpec.classBuilder(createRepositoryClassName())
        .addModifiers(PUBLIC, FINAL)
        .addOriginatingElement(mRepositoryClass.getRepositoryElement())
        .addField(mDatabase())
        .addMethod(constructor())
        .addMethod(createContentValues())
        .addMethod(readCursor());

    if (mRepositoryClass.isInterface()) {
      repositoryBuilder.addSuperinterface(ClassName.get(mRepositoryClass.getRepositoryElement()));
    } else {
      repositoryBuilder.superclass(ClassName.get(mRepositoryClass.getRepositoryElement()));
    }

    for (RepositoryMethod repositoryMethod : mRepositoryClass.getMethods()) {
      repositoryBuilder.addMethod(implement(repositoryMethod));
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

  private MethodSpec implement(final RepositoryMethod repositoryMethod) {
    switch (repositoryMethod.getMethodName()) {
      case "find":
        return implementFind(repositoryMethod);
      case "create":
        return implementCreate(repositoryMethod);
      default:
        throw new UnsupportedOperationException(repositoryMethod.getMethodName());
    }
  }

  private MethodSpec implementFind(final RepositoryMethod repositoryMethod) {
    return MethodSpec.methodBuilder(repositoryMethod.getMethodName())
        .addAnnotation(Override.class)
        .addModifiers(PUBLIC)
        .addParameter(Long.class, "id", FINAL)
        .returns(ClassName.bestGuess(repositoryMethod.getReturnType()))
        .beginControlFlow("if (id == null)")
        .addStatement("return null")
        .endControlFlow()
        .addCode("\n")
        .addStatement("$T result = null", mTableClass.getEntityTypeElement())
        .addCode("\n")
        .addStatement(
            "$T cursor = new $T()" +
                ".from($S)" +
                ".where(\"id=?\", id)" +
                ".limit(\"1\")" +
                ".fetchFrom(mDatabase)",
            Cursor.class,
            ClassName.get("com.nhaarman.trinity.query", "Select"),
            mTableClass.getTableName()
        )
        .beginControlFlow("try")
        .beginControlFlow("if (cursor.moveToFirst())")
        .addStatement("result = $N(cursor)", readCursor())
        .endControlFlow()
        .nextControlFlow("finally")
        .addStatement("cursor.close()")
        .endControlFlow()
        .addCode("\n")
        .addStatement("return result")
        .build();
  }

  private MethodSpec implementCreate(final RepositoryMethod repositoryMethod) {
    Parameter parameter = repositoryMethod.getParameter();
    Column primaryKeyColumn = mTableClass.getPrimaryKeyColumn();

    return MethodSpec.methodBuilder("create")
        .addAnnotation(Override.class)
        .addModifiers(PUBLIC)
        .addParameter(ClassName.bestGuess(parameter.getType()), parameter.getName(), FINAL)
        .returns(ClassName.bestGuess(repositoryMethod.getReturnType()))
        .addStatement("$T result = null", Long.class)
        .addCode("\n")
        .addStatement("$T contentValues = $N($L)", ContentValues.class, createContentValues(),
            parameter.getName())
        .addStatement("$T id = mDatabase.insert($S, null, contentValues)", long.class,
            mTableClass.getTableName())
        .beginControlFlow("if (id != -1)")
        .addStatement("$L.$L(id)", parameter.getName(), primaryKeyColumn.setter().getName())
        .addStatement("result = id")
        .endControlFlow()
        .addCode("\n")
        .addStatement("return result")
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
