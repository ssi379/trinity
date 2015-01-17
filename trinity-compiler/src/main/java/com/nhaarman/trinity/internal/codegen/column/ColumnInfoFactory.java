package com.nhaarman.trinity.internal.codegen.column;

import com.nhaarman.trinity.annotations.Column;
import com.nhaarman.trinity.annotations.Foreign;
import com.nhaarman.trinity.annotations.PrimaryKey;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.NoType;
import javax.lang.model.type.TypeMirror;

public class ColumnInfoFactory {

    private final Map<String, ColumnInfo> mColumnInfoMap = new HashMap<>();

    public synchronized Map<String, ColumnInfo> createColumnInfoList(final Set<ExecutableElement> annotatedElements) {
        mColumnInfoMap.clear();

        for (ExecutableElement annotatedElement : annotatedElements) {
            ColumnMethodInfo columnMethodInfo = createColumnMethodInfo(annotatedElement);
            String columnName = columnMethodInfo.getColumnName();

            if (mColumnInfoMap.containsKey(columnName)) {
                mColumnInfoMap.get(columnName).addMethodInfo(columnMethodInfo);
            } else {
                mColumnInfoMap.put(columnName, new ColumnInfo(columnName, columnMethodInfo));
            }
        }

        return Collections.unmodifiableMap(mColumnInfoMap);
    }

    public ColumnMethodInfo createColumnMethodInfo(final ExecutableElement element) {
        Column columnAnnotation = element.getAnnotation(Column.class);
        String columnName = columnAnnotation.value();

        ColumnMethodInfo result = new ColumnMethodInfo(columnName, element);
        result.setType(getType(element));

        Foreign foreignAnnotation = element.getAnnotation(Foreign.class);
        if (foreignAnnotation != null) {
            result.setForeignInfo(new ForeignInfo(foreignAnnotation.tableName(), foreignAnnotation.columnName()));
        }

        PrimaryKey primaryKeyAnnotation = element.getAnnotation(PrimaryKey.class);
        if (primaryKeyAnnotation != null) {
            result.setPrimaryKeyInfo(new PrimaryKeyInfo(primaryKeyAnnotation.autoIncrement()));
        }

        return result;
    }

    private TypeMirror getType(final ExecutableElement element) {
        if (element.getReturnType() instanceof NoType) {
            VariableElement variableElement = element.getParameters().get(0);
            return variableElement.asType();
        } else {
            return element.getReturnType();
        }
    }
}
