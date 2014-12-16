package com.nhaarman.ellie.internal.codegen.table;

import com.nhaarman.ellie.internal.codegen.ProcessingFailedException;
import com.nhaarman.ellie.internal.codegen.column.ColumnInfo;
import com.nhaarman.ellie.internal.codegen.column.ColumnInfoFactory;
import com.nhaarman.lib_setup.annotations.Column;
import com.nhaarman.lib_setup.annotations.Table;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;

public class TableInfoFactory {

    private Table mAnnotation;
    private TypeElement mElement;

    private RoundEnvironment mRoundEnvironment;

    public TableInfo createTableInfo(final TypeElement element, final RoundEnvironment roundEnvironment) throws ProcessingFailedException {
        TableInfo result = new TableInfo();

        mElement = element;
        mRoundEnvironment = roundEnvironment;
        mAnnotation = element.getAnnotation(Table.class);

        result.setTableName(getTableName());
        result.setSinceVersion(getSinceVersion());
        result.setRepositoryClass(getRepositoryClass());

        result.setPackageName(getPackageName());
        result.setEntityFQN(getEntityFQN());

        result.setColumns(getColumns());

        return result;
    }

    private Collection<ColumnInfo> getColumns() {
        ColumnInfoFactory columnInfoFactory = new ColumnInfoFactory();

        Set<ExecutableElement> annotatedElements = (Set<ExecutableElement>) mRoundEnvironment.getElementsAnnotatedWith(Column.class);
        for (Iterator<? extends Element> iterator = annotatedElements.iterator(); iterator.hasNext(); ) {
            Element annotatedElement = iterator.next();
            if (!annotatedElement.getEnclosingElement().equals(mElement)) {
                iterator.remove();
            }
        }

        return columnInfoFactory.createColumnInfoList(annotatedElements);
    }


    private String getTableName() {
        return mAnnotation.name();
    }

    private int getSinceVersion() {
        return mAnnotation.sinceVersion();
    }

    private Class<?> getRepositoryClass() throws ProcessingFailedException {
        try {
            mAnnotation.repository();
        } catch (MirroredTypeException mte) {
            try {
                return Class.forName(mte.getTypeMirror().toString());
            } catch (ClassNotFoundException e) {
                throw new ProcessingFailedException(e);
            }
        }
        return null;
    }

    private String getPackageName() {
        String entityFQN = mElement.getQualifiedName().toString();
        return entityFQN.substring(0, entityFQN.lastIndexOf('.'));
    }

    private String getEntityFQN() {
        return mElement.getQualifiedName().toString();
    }

}
