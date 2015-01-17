package com.nhaarman.trinity.internal.codegen.column;

import org.junit.Before;
import org.junit.Test;

import javax.lang.model.type.TypeMirror;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("HardCodedStringLiteral") public class ColumnConverterTest {

    private ColumnConverter mColumnConverter;

    @Before
    public void setUp() {
        mColumnConverter = new ColumnConverter();
    }

    @Test
    public void simple_column_info_returns_simple_statement() {
        /* Given */
        ColumnInfo columnInfo = mockColumnInfo("long", "id");

        /* When */
        String result = mColumnConverter.toSQLiteStatement(columnInfo);

        /* Then */
        assertThat(result, is(equalTo("id INTEGER")));
    }

    @Test
    public void column_info_with_primary_key_returns_primary_key_statement() {
        /* Given */
        ColumnInfo columnInfo = mockColumnInfo("java.lang.String", "name");
        when(columnInfo.getPrimaryKeyInfo()).thenReturn(mock(PrimaryKeyInfo.class));

        /* When */
        String result = mColumnConverter.toSQLiteStatement(columnInfo);

        /* Then */
        assertThat(result, is(equalTo("name TEXT PRIMARY KEY")));
    }

    @Test
    public void column_info_with_primary_key_and_autoincrement_returns_primary_key_and_autoincrement_statement() {
        /* Given */
        ColumnInfo columnInfo = mockColumnInfo("long", "id");

        PrimaryKeyInfo primaryKeyInfo = mock(PrimaryKeyInfo.class);
        when(columnInfo.getPrimaryKeyInfo()).thenReturn(primaryKeyInfo);
        when(primaryKeyInfo.autoIncrement()).thenReturn(true);

        /* When */
        String result = mColumnConverter.toSQLiteStatement(columnInfo);

        /* Then */
        assertThat(result, is(equalTo("id INTEGER PRIMARY KEY AUTOINCREMENT")));
    }

    @Test
    public void column_info_with_foreign_returns_foreign_key_statement() {
        /* Given */
        ColumnInfo columnInfo = mockColumnInfo("long", "foreign_id");

        ForeignInfo foreignInfo = mock(ForeignInfo.class);
        when(columnInfo.getForeignInfo()).thenReturn(foreignInfo);
        when(foreignInfo.tableName()).thenReturn("table");
        when(foreignInfo.columnName()).thenReturn("id");

        /* When */
        String result = mColumnConverter.toSQLiteStatement(columnInfo);

        /* Then */
        assertThat(result, is(equalTo("foreign_id INTEGER, FOREIGN KEY(foreign_id) REFERENCES table(id)")));
    }

    private ColumnInfo mockColumnInfo(final String type, final String name) {
        TypeMirror columnType = mock(TypeMirror.class);
        when(columnType.toString()).thenReturn(type);

        ColumnInfo columnInfo = mock(ColumnInfo.class);
        when(columnInfo.getType()).thenReturn(columnType);
        when(columnInfo.getColumnName()).thenReturn(name);
        return columnInfo;
    }

}