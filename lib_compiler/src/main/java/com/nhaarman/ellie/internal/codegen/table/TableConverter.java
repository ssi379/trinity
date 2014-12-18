package com.nhaarman.ellie.internal.codegen.table;

import com.nhaarman.ellie.internal.codegen.column.ColumnConverter;
import com.nhaarman.ellie.internal.codegen.column.ColumnInfo;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class TableConverter {

    private final ColumnConverter mColumnConverter;

    public TableConverter(final ColumnConverter columnConverter) {
        mColumnConverter = columnConverter;
    }

    public String createTableStatement(final TableInfo tableInfo) {
        StringBuilder result = new StringBuilder(255);

        result.append("CREATE TABLE ");
        result.append(tableInfo.getTableName());
        result.append('(');

        Collection<ColumnInfo> columns = tableInfo.getColumns();
        for (Iterator<ColumnInfo> iterator = columns.iterator(); iterator.hasNext(); ) {
            ColumnInfo columnInfo = iterator.next();
            result.append(mColumnConverter.toSQLiteStatement(columnInfo));

            if (iterator.hasNext()) {
                result.append(", ");
            }
        }

        result.append(')');

        return result.toString();
    }

    public String dropTableStatement(final TableInfo tableInfo) {
        return "DROP TABLE " + tableInfo.getTableName();
    }
}
