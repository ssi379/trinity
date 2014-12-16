package com.nhaarman.ellie.internal.codegen.table;

import com.nhaarman.ellie.internal.codegen.ProcessingFailedException;
import com.nhaarman.lib_setup.annotations.Table;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

public class TableProcessor {

    private final ProcessingEnvironment mProcessingEnvironment;
    private final TableValidator mTableValidator;

    public TableProcessor(final ProcessingEnvironment processingEnvironment) {
        mProcessingEnvironment = processingEnvironment;
        mTableValidator = new TableValidator();
    }

    public void process(final RoundEnvironment roundEnvironment) throws ProcessingFailedException, IOException {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Table.class);

        List<TableInfo> tableInfoList = new ArrayList<>();
        for (Element element : elements) {
            mTableValidator.validateOrThrow(element);
            TypeElement tableElement = (TypeElement) element;
            TableInfo tableInfo = new TableInfoFactory().createTableInfo(tableElement, roundEnvironment);
            tableInfoList.add(tableInfo);
        }

        for (TableInfo tableInfo : tableInfoList) {
            new MigrationWriter(mProcessingEnvironment.getFiler()).writeMigration(tableInfo);
        }
    }
}
