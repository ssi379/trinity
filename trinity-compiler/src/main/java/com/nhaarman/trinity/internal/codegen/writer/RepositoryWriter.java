package com.nhaarman.trinity.internal.codegen.writer;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.nhaarman.trinity.internal.codegen.data.Column;
import com.nhaarman.trinity.internal.codegen.data.ColumnMethod;
import com.nhaarman.trinity.internal.codegen.data.RepositoryClass;
import com.nhaarman.trinity.internal.codegen.data.RepositoryMethod;
import com.nhaarman.trinity.internal.codegen.data.TableClass;
import com.nhaarman.trinity.internal.codegen.writer.method.MethodCreatorFactory;
import com.nhaarman.trinity.internal.codegen.writer.readcursor.ReadCursorCreator;
import com.nhaarman.trinity.internal.codegen.writer.readcursor.ReadCursorCreatorFactory;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.MethodSpec.Builder;
import com.squareup.javapoet.TypeSpec;
import java.io.IOException;
import java.io.Writer;
import javax.annotation.processing.Filer;
import javax.tools.JavaFileObject;
import org.jetbrains.annotations.NotNull;

import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.PUBLIC;

@SuppressWarnings("HardCodedStringLiteral")
public class RepositoryWriter {

  private static final String REPOSITORY_CLASS_NAME = "Trinity_%s";

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

    MethodCreatorFactory methodCreatorFactory = new MethodCreatorFactory(mRepositoryClass, databaseFieldSpec, readCursorSpec, createContentValuesSpec);

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
      repositoryBuilder.addMethod(methodCreatorFactory.creatorFor(repositoryMethod).create());
    }

    TypeSpec typeSpec = repositoryBuilder.build();
    writeToFile(typeSpec);
  }

  /**
   * Writes the TypeSpec to file.
   *
   * @param typeSpec The TypeSpec to write.
   *
   * @throws IOException if an I/O error occurred.
   */
  private void writeToFile(final TypeSpec typeSpec) throws IOException {
    JavaFile javaFile = JavaFile.builder(mRepositoryClass.getPackageName(), typeSpec).build();

    JavaFileObject sourceFile = mFiler.createSourceFile(createRepositoryClassName());
    Writer writer = sourceFile.openWriter();
    javaFile.writeTo(writer);
    writer.flush();
    writer.close();
  }

  /**
   * Creates the SQLiteDatabase field spec.
   */
  private FieldSpec mDatabase() {
    return FieldSpec.builder(SQLiteDatabase.class, FIELD_NAME_DATABASE, PRIVATE, FINAL)
        .addJavadoc("The {@link $T} that is used for persistence.\n", SQLiteDatabase.class)
        .build();
  }

  /**
   * Creates the constructor.
   */
  private MethodSpec constructor() {
    return MethodSpec.constructorBuilder()
        .addModifiers(PUBLIC)
        .addParameter(SQLiteDatabase.class, "database", FINAL)
        .addStatement(FIELD_NAME_DATABASE + " = database")
        .build();
  }

  /**
   * Creates the createContentValues method.
   */
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

  /**
   * Creates the readCursor method.
   */
  private MethodSpec readCursor() {
    Builder methodBuilder =
        MethodSpec.methodBuilder("read")
            .addModifiers(PUBLIC)
            .addParameter(Cursor.class, "cursor", FINAL)
            .returns(ClassName.get(mTableClass.getEntityTypeElement()))
            .addStatement("$T result = new $T()", mTableClass.getEntityTypeElement(),
                mTableClass.getEntityTypeElement())
            .addCode("\n");

    ReadCursorCreatorFactory creatorFactory = new ReadCursorCreatorFactory("result", "cursor");
    for (Column column : mTableClass.getColumns()) {
      ReadCursorCreator readCursorCreator = creatorFactory.createReadCursorCreator(column);
      methodBuilder.addCode(readCursorCreator.create());
    }

    return methodBuilder.addCode("\n")
        .addStatement("return result")
        .build();
  }

  /**
   * Returns the class name of the repository to be written.
   */
  private String createRepositoryClassName() {
    return String.format(REPOSITORY_CLASS_NAME, mRepositoryClass.getClassName());
  }
}
