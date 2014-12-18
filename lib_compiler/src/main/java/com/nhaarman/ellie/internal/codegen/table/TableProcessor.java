package com.nhaarman.ellie.internal.codegen.table;

import com.nhaarman.ellie.internal.codegen.Node;
import com.nhaarman.ellie.internal.codegen.ProcessingFailedException;
import com.nhaarman.ellie.internal.codegen.column.ColumnConverter;
import com.nhaarman.ellie.internal.codegen.migration.CreateTableMigrationWriter;

import java.io.IOException;
import java.util.Collection;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;

public class TableProcessor {

    private final ProcessingEnvironment mProcessingEnvironment;
    private final TableConverter mTableConverter;

    public TableProcessor(final ProcessingEnvironment processingEnvironment) {
        mProcessingEnvironment = processingEnvironment;
        mTableConverter = new TableConverter(new ColumnConverter());
    }

    public void process(final RoundEnvironment roundEnvironment) throws ProcessingFailedException, IOException {
//        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Table.class);
//
//        List<TableInfo> tableInfoList = new ArrayList<>();
//        for (Element element : elements) {
//            mTableValidator.validates(element);
//            TypeElement tableElement = (TypeElement) element;
//            TableInfo tableInfo = new TableInfoFactory().createTableInfo(tableElement, roundEnvironment);
//            tableInfoList.add(tableInfo);
//        }
//
//        Collection<Node<TableInfo>> nodes = new NodeFactory().createTree(tableInfoList);
//
//        writeMigrations(nodes);
    }

    private void writeMigrations(final Collection<Node<TableInfo>> nodes) throws IOException {
        for (Node<TableInfo> node : nodes) {
            new CreateTableMigrationWriter(mProcessingEnvironment.getFiler(), mTableConverter).writeMigration(node.getValue());

            writeMigrations(node.getChildren());
        }
    }
}
