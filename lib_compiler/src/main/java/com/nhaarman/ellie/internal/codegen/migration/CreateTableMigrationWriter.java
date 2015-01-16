package com.nhaarman.ellie.internal.codegen.migration;

import com.nhaarman.ellie.internal.codegen.table.TableConverter;
import com.nhaarman.ellie.internal.codegen.table.TableInfo;
import com.nhaarman.lib_setup.migrations.Migration;
import com.nhaarman.lib_setup.migrations.MigrationAdapter;
import com.squareup.javawriter.JavaWriter;

import java.io.IOException;
import java.util.Locale;

import javax.annotation.processing.Filer;
import javax.tools.JavaFileObject;

import static com.nhaarman.ellie.internal.codegen.Modifiers.PUBLIC;

public class CreateTableMigrationWriter {

    private final Filer mFiler;

    private final TableConverter mTableConverter;

    private JavaWriter mJavaWriter;
    private TableInfo mTableInfo;

    public CreateTableMigrationWriter(final Filer filer, final TableConverter tableConverter) {
        mFiler = filer;
        mTableConverter = tableConverter;
    }

    public void writeMigration(final TableInfo tableInfo) throws IOException {
        mTableInfo = tableInfo;

        JavaFileObject sourceFile = mFiler.createSourceFile(createMigrationClassName());
        mJavaWriter = new JavaWriter(sourceFile.openWriter());
        mJavaWriter.setIndent("    ");

        writeHeader();
        writeBeginType();

        writeConstructor();
        writeGetOrder();
        writeGetUpStatements();
        writeGetDownStatements();

        writeEndType();

        mJavaWriter.close();
    }

    private void writeHeader() throws IOException {
        mJavaWriter.emitSingleLineComment("Generated by Ellie. Do not modify!");
        mJavaWriter.emitPackage(mTableInfo.getPackageName());
    }

    private void writeBeginType() throws IOException {
        mJavaWriter.emitJavadoc("A {@link %s} implementation that creates a table for {@link %s}.", Migration.class.getCanonicalName(), mTableInfo.getEntityFQN());
        mJavaWriter.beginType(createMigrationClassName(), "class", PUBLIC, MigrationAdapter.class.getCanonicalName());
        mJavaWriter.emitEmptyLine();
    }

    private void writeConstructor() throws IOException {
        mJavaWriter.beginConstructor(PUBLIC);
        mJavaWriter.emitStatement("super(%d)", 1);
        mJavaWriter.endConstructor();
        mJavaWriter.emitEmptyLine();
    }

    private void writeGetOrder() throws IOException {
        mJavaWriter.emitAnnotation(Override.class);
        mJavaWriter.beginMethod(long.class.getCanonicalName(), "getOrder", PUBLIC);
        mJavaWriter.emitStatement("return %dL", System.currentTimeMillis());
        mJavaWriter.endMethod();
        mJavaWriter.emitEmptyLine();
    }

    private void writeGetUpStatements() throws IOException {
        mJavaWriter.emitAnnotation(Override.class);
        mJavaWriter.beginMethod(String[].class.getCanonicalName(), "getUpStatements", PUBLIC);

        mJavaWriter.emitStatement("return new String[]{%s}", createUpStatements());

        mJavaWriter.endMethod();
        mJavaWriter.emitEmptyLine();
    }

    private void writeGetDownStatements() throws IOException {
        mJavaWriter.emitAnnotation(Override.class);
        mJavaWriter.beginMethod(String[].class.getCanonicalName(), "getDownStatements", PUBLIC);

        mJavaWriter.emitStatement("return new String[]{%s}", createDownStatements());

        mJavaWriter.endMethod();
        mJavaWriter.emitEmptyLine();
    }

    private void writeEndType() throws IOException {
        mJavaWriter.endType();
    }

    private String createMigrationClassName() {
        return String.format("Create%sTableMigration", mTableInfo.getTableName().substring(0, 1).toUpperCase(Locale.US) + mTableInfo.getTableName().substring(1));
    }

    private String createUpStatements() {
        return '"' + mTableConverter.createTableStatement(mTableInfo) + '"';
    }

    private String createDownStatements() {
        return '"' + mTableConverter.dropTableStatement(mTableInfo) + '"';
    }

}
