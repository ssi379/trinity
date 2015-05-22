package com.nhaarman.trinity.internal.codegen.writer.method;

import com.nhaarman.trinity.internal.codegen.data.TableClass;
import com.nhaarman.trinity.internal.codegen.data.RepositoryMethod;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import static com.nhaarman.trinity.internal.codegen.AndroidClasses.CURSOR;
import static javax.lang.model.element.Modifier.PUBLIC;

class FindAllMethodCreator implements MethodCreator {

  @NotNull
  private final TableClass mTableClass;

  @NotNull
  private final FieldSpec mDatabaseFieldSpec;

  @NotNull
  private final MethodSpec mReadCursorSpec;

  @NotNull
  private final RepositoryMethod mMethod;

  FindAllMethodCreator(@NotNull final TableClass tableClass,
                       @NotNull final FieldSpec databaseFieldSpec,
                       @NotNull final MethodSpec readCursorSpec,
                       @NotNull final RepositoryMethod method) {
    mTableClass = tableClass;
    mDatabaseFieldSpec = databaseFieldSpec;
    mReadCursorSpec = readCursorSpec;
    mMethod = method;
  }

  @NotNull
  @Override
  public MethodSpec create() {
    return MethodSpec.methodBuilder(mMethod.getMethodName())
        .addJavadoc(createJavadoc())
        .addAnnotation(Override.class)
        .addModifiers(PUBLIC)
        .returns(ClassName.get(List.class))
        .addStatement(
            "$T results = new $T<$T>()",
            List.class,
            ArrayList.class,
            ClassName.bestGuess(mTableClass.getFullyQualifiedName())
        )
        .addCode("\n")
        .addStatement(
            "$T cursor = $N.query($S, null, null, null, null, null, null)",
            CURSOR,
            mDatabaseFieldSpec,
            mTableClass.getTableName()
        )
        .beginControlFlow("try")
        .beginControlFlow("while (cursor.moveToNext())")
        .addStatement("results.add($N(cursor))", mReadCursorSpec)
        .endControlFlow()
        .nextControlFlow("finally")
        .addStatement("cursor.close()")
        .endControlFlow()
        .addCode("\n")
        .addStatement("return results")
        .build();
  }

  @NonNls
  private String createJavadoc() {
    String entityName = mTableClass.getFullyQualifiedName();
    return "Performs a query to find all instances of " + entityName + ".\n"
        + '\n'
        + "@return A list which contains all instances of " + entityName + ".\n";
  }
}
