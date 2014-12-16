package com.nhaarman.ellie.internal.codegen.table;

import com.nhaarman.ellie.internal.codegen.Node;
import com.nhaarman.ellie.internal.codegen.column.ColumnInfo;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.lang.model.type.TypeMirror;

public class NodeFactory {

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
}
