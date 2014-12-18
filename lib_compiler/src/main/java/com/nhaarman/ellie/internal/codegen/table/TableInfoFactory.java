package com.nhaarman.ellie.internal.codegen.table;

import com.nhaarman.ellie.internal.codegen.Node;
import com.nhaarman.ellie.internal.codegen.ProcessingFailedException;
import com.nhaarman.ellie.internal.codegen.column.ColumnInfo;
import com.nhaarman.ellie.internal.codegen.column.ColumnInfoFactory;
import com.nhaarman.lib_setup.annotations.Column;
import com.nhaarman.lib_setup.annotations.Table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;

public class TableInfoFactory {

    private Table mAnnotation;
    private TypeElement mElement;

    private RoundEnvironment mRoundEnvironment;

    public Collection<Node<TableInfo>> createTableInfoTrees(final Set<? extends Element> tableElements, final RoundEnvironment roundEnvironment) {
        Collection<TableInfo> nodes = new ArrayList<>();

        for (Element tableElement : tableElements) {
            nodes.add(createTableInfo((TypeElement) tableElement, roundEnvironment));
        }

        return createTree(nodes);
    }

    public TableInfo createTableInfo(final TypeElement element, final RoundEnvironment roundEnvironment) {
        TableInfo result = new TableInfo();

        mElement = element;
        mRoundEnvironment = roundEnvironment;
        mAnnotation = element.getAnnotation(Table.class);

        result.setElement(element);

        result.setTableName(getTableName());
        result.setSinceVersion(getSinceVersion());
        result.setRepositoryClass(getRepositoryClass());

        result.setPackageName(getPackageName());
        result.setEntityFQN(getEntityFQN());

        result.setColumns(getColumns());

        return result;
    }

    private Map<String, ColumnInfo> getColumns() {
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

    public Collection<Node<TableInfo>> createTree(final Collection<TableInfo> tableInfos) {
        Set<Node<TableInfo>> results = new HashSet<>();

        /* Create nodes */
        Map<TableInfo, Node<TableInfo>> nodes = new HashMap<>();
        for (TableInfo tableInfo : tableInfos) {
            Node<TableInfo> node = new Node<>(tableInfo);
            nodes.put(tableInfo, node);
            results.add(node);
        }

        /* Find dependencies */
        for (Node<TableInfo> node : nodes.values()) {
            for (TableInfo tableInfo : tableInfos) {
                if (dependsOn(node.getValue(), tableInfo)) {
                    node.add(nodes.get(tableInfo));
                    results.remove(nodes.get(tableInfo));
                }
            }
        }

        if (results.isEmpty() && !tableInfos.isEmpty()) {
            throw new IllegalStateException("A cycle was found!");
        }

        for (Node<TableInfo> node : results) {
            if (node.isCyclic()) {
                throw new IllegalStateException("A cycle was found!");
            }
        }

        return results;
    }

    private boolean dependsOn(final TableInfo first, final TableInfo second) {
        for (ColumnInfo columnInfo : second.getColumns()) {
            TypeMirror columnType = columnInfo.getType();

            if (first.getElement().asType().equals(columnType)) {
                return true;
            } else {
                TypeMirror superclass = first.getElement().getSuperclass();
                if (superclass.equals(columnType)) {
                    return true;
                } else {
                    List<? extends TypeMirror> interfaces = first.getElement().getInterfaces();
                    for (TypeMirror anInterface : interfaces) {
                        if (anInterface.equals(columnType)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private String getTableName() {
        return mAnnotation.name();
    }

    private int getSinceVersion() {
        return mAnnotation.sinceVersion();
    }

    private Class<?> getRepositoryClass() {
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
