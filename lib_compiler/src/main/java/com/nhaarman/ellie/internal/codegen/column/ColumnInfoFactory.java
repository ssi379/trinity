package com.nhaarman.ellie.internal.codegen.column;

import com.nhaarman.lib_setup.Column;
import com.nhaarman.lib_setup.Foreign;
import com.nhaarman.lib_setup.PrimaryKey;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.NoType;
import javax.lang.model.type.TypeMirror;

public class ColumnInfoFactory {

    private final Map<String, ColumnInfo> mColumnInfoMap = new HashMap<>();
    private final TypeConverter mTypeConverter = new TypeConverter();

    public synchronized Collection<ColumnInfo> createColumnInfoList(final Set<ExecutableElement> annotatedElements) {
        mColumnInfoMap.clear();

        for (ExecutableElement annotatedElement : annotatedElements) {
            createColumnInfo(annotatedElement);
        }

        return mColumnInfoMap.values();
    }

    public ColumnInfo createColumnInfo(final ExecutableElement element) {
        Column columnAnnotation = element.getAnnotation(Column.class);
        String columnName = columnAnnotation.value();

        ColumnInfo result = mColumnInfoMap.containsKey(columnName) ? mColumnInfoMap.get(columnName) : new ColumnInfo();
        result.setColumnName(columnName);

        if (element.getReturnType() instanceof NoType) {
            processSetterElement(element, result);
        } else {
            processGetterElement(element, result);
        }

        Foreign foreignAnnotation = element.getAnnotation(Foreign.class);
        if (foreignAnnotation != null) {
            result.setForeignColumn(true);
            result.setForeignTable(foreignAnnotation.tableName());
            result.setForeignColumnName(foreignAnnotation.columnName());
        }

        PrimaryKey primaryKeyAnnotation = element.getAnnotation(PrimaryKey.class);
        if (primaryKeyAnnotation != null) {
            result.setPrimaryKey(true);

            if (mTypeConverter.toSQLiteType(result).equals(TypeConverter.TYPE_INTEGER)) {
                result.setAutoIncrement(primaryKeyAnnotation.autoIncrement());
            }
        }

        mColumnInfoMap.put(result.getColumnName(), result);
        return result;
    }

    private void processGetterElement(final ExecutableElement element, final ColumnInfo result) {
        TypeMirror type = element.getReturnType();
        result.setType(type);
        result.setGetter(element);
    }

    private void processSetterElement(final ExecutableElement element, final ColumnInfo result) {
        VariableElement variableElement = element.getParameters().get(0);
        TypeMirror type = variableElement.asType();
        result.setType(type);
        result.setSetter(element);
    }
}
