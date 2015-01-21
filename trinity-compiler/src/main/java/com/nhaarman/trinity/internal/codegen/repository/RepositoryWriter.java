package com.nhaarman.trinity.internal.codegen.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nhaarman.trinity.internal.codegen.column.ColumnInfo;
import com.nhaarman.trinity.internal.codegen.column.ColumnMethodInfo;
import com.nhaarman.trinity.internal.codegen.table.TableInfo;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;

import javax.annotation.processing.Filer;
import javax.lang.model.element.ExecutableElement;
import javax.tools.JavaFileObject;

import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.PUBLIC;

public class RepositoryWriter {

  private final Filer mFiler;

  private RepositoryClass mRepositoryClass;

  private TableInfo mTableInfo;

  private JavaFile mJavaFile;

  public RepositoryWriter(final Filer filer) {
    mFiler = filer;
  }

  public void writeRepository(final RepositoryClass repositoryClass) throws IOException {
    mRepositoryClass = repositoryClass;
    mTableInfo = repositoryClass.getTableInfo();


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

    for (ExecutableElement executableElement : mRepositoryClass.getMethodsToImplement()) {
      repositoryBuilder.addMethod(implement(executableElement));
    }

    JavaFile javaFile = JavaFile.builder(mRepositoryClass.getPackageName(), repositoryBuilder.build()).build();

    Writer writer = sourceFile.openWriter();
    javaFile.emit(writer);
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

  private MethodSpec implement(final ExecutableElement executableElement) {
    if (executableElement.getSimpleName().toString().equals("find")) {
      return implementFind(executableElement);
    } else if (executableElement.getSimpleName().toString().equals("create")) {
      return implementCreate(executableElement);
    } else {
      throw new UnsupportedOperationException(executableElement.getSimpleName().toString());
    }
  }

  private MethodSpec implementFind(final ExecutableElement executableElement) {
    String returnTypeString = executableElement.getReturnType().toString();

    return MethodSpec.methodBuilder(executableElement.getSimpleName().toString())
                     .addAnnotation(Override.class)
                     .addModifiers(PUBLIC)
                     .addParameter(Long.class, "id", FINAL)
                     .returns(ClassName.bestGuess(returnTypeString))
                     .beginControlFlow("if (id == null)")
                     .addStatement("return null")
                     .endControlFlow()
                     .addCode("\n")
                     .addStatement("$T result = null", mTableInfo.getEntityTypeElement())
                     .addCode("\n")
                     .addStatement(
                         "$T cursor = new $T()" +
                             ".from($S)" +
                             ".where(\"id=?\", id)" +
                             ".limit(\"1\")" +
                             ".fetchFrom(mDatabase)",
                         Cursor.class,
                         ClassName.get("com.nhaarman.trinity.query", "Select"),
                         mTableInfo.getTableName()
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

  private MethodSpec implementCreate(final ExecutableElement executableElement) {
    String parameterTypeString = executableElement.getParameters().get(0).asType().toString();
    String returnTypeString = executableElement.getReturnType().toString();

    return MethodSpec.methodBuilder("create")
                     .addAnnotation(Override.class)
                     .addModifiers(PUBLIC)
                     .addParameter(ClassName.bestGuess(parameterTypeString), "entity", FINAL)
                     .returns(ClassName.bestGuess(returnTypeString))
                     .addStatement("$T result = null", Long.class)
                     .addCode("\n")
                     .addStatement("$T contentValues = $N(entity)", ContentValues.class, createContentValues())
                     .addStatement("$T id = mDatabase.insert($S, null, contentValues)", long.class, mTableInfo.getTableName())
                     .beginControlFlow("if (id != -1)")
                     .addStatement("entity.setId(id)")
                     .addStatement("result = id")
                     .endControlFlow()
                     .addCode("\n")
                     .addStatement("return result")
                     .build();
  }

  private MethodSpec createContentValues() {
    MethodSpec.Builder methodBuilder =
        MethodSpec.methodBuilder("createContentValues")
                  .addModifiers(PUBLIC)
                  .addParameter(ClassName.get(mTableInfo.getEntityTypeElement()), "entity", FINAL)
                  .returns(ContentValues.class)
                  .addStatement("$T result = new $T()", ContentValues.class, ContentValues.class)
                  .addCode("\n");

    for (ColumnInfo columnInfo : mTableInfo.getColumns()) {
      Collection<ColumnMethodInfo> methodInfos = columnInfo.getMethodInfos();
      for (ColumnMethodInfo methodInfo : methodInfos) {
        if (!methodInfo.isSetter()) {
          methodBuilder.addStatement("result.put($S, entity.$L())", columnInfo.getColumnName(), methodInfo.getExecutableElement().getSimpleName());
        }
      }
    }

    methodBuilder.addCode("\n");
    methodBuilder.addStatement("return result");

    return methodBuilder.build();
  }

  private MethodSpec readCursor() {
    MethodSpec.Builder methodBuilder =
        MethodSpec.methodBuilder("read")
                  .addModifiers(PUBLIC)
                  .addParameter(Cursor.class, "cursor", FINAL)
                  .returns(ClassName.get(mTableInfo.getEntityTypeElement()))
                  .addStatement("$T result = new $T()", mTableInfo.getEntityTypeElement(), mTableInfo.getEntityTypeElement())
                  .addCode("\n");

    for (ColumnInfo columnInfo : mTableInfo.getColumns()) {
      Collection<ColumnMethodInfo> methodInfos = columnInfo.getMethodInfos();
      for (ColumnMethodInfo methodInfo : methodInfos) {
        if (methodInfo.isSetter()) {
          methodBuilder.addStatement(
              "result.$L(cursor.get$L(cursor.getColumnIndex($S)))",
              methodInfo.getExecutableElement().getSimpleName(),
              methodInfo.getExecutableElement().getParameters().get(0).asType().toString().substring(
                  methodInfo.getExecutableElement().getParameters().get(0).asType().toString().lastIndexOf('.') + 1
              ),
              columnInfo.getColumnName()
          );
        }
      }
    }

    return methodBuilder.addCode("\n")
                        .addStatement("return result")
                        .build();
  }

  private String createRepositoryClassName() {
    return String.format("Trinity_%s", mRepositoryClass.getRepositoryElement().getSimpleName());
  }

}
