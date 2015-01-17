package com.nhaarman.trinity.internal.codegen.table;

import com.nhaarman.trinity.internal.codegen.column.ColumnConverter;
import com.nhaarman.trinity.internal.codegen.column.ColumnInfo;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("HardCodedStringLiteral") public class TableConverterTest {

    private TableConverter mTableConverter;
    private ColumnConverter mColumnConverter;

    @Before
    public void setUp() throws Exception {
        mColumnConverter = mock(ColumnConverter.class);
        mTableConverter = new TableConverter(mColumnConverter);
    }

    @Test
    public void create_base_statement() {
        /* Given */
        TableInfo tableInfo = mock(TableInfo.class);
        when(tableInfo.getTableName()).thenReturn("table");
        when(tableInfo.getColumns()).thenReturn(new ArrayList<ColumnInfo>());
        
        /* When */
        String result = mTableConverter.createTableStatement(tableInfo);
        
        /* Then */
        assertThat(result, is(equalTo("CREATE TABLE table()")));
    }

    @Test
    public void create_single_column_statement() {
        /* Given */
        ColumnInfo columnInfo = mock(ColumnInfo.class);
        when(mColumnConverter.toSQLiteStatement(columnInfo)).thenReturn("column INTEGER");

        TableInfo tableInfo = mock(TableInfo.class);
        when(tableInfo.getTableName()).thenReturn("table");
        when(tableInfo.getColumns()).thenReturn(Collections.singletonList(columnInfo));

        /* When */
        String result = mTableConverter.createTableStatement(tableInfo);

        /* Then */
        assertThat(result, is(CoreMatchers.equalTo("CREATE TABLE table(column INTEGER)")));
    }

    @Test
    public void create_double_column_statement() {
        /* Given */
        ColumnInfo columnInfo1 = mock(ColumnInfo.class);
        when(mColumnConverter.toSQLiteStatement(columnInfo1)).thenReturn("column1 INTEGER");

        ColumnInfo columnInfo2 = mock(ColumnInfo.class);
        when(mColumnConverter.toSQLiteStatement(columnInfo2)).thenReturn("column2 INTEGER");

        TableInfo tableInfo = mock(TableInfo.class);
        when(tableInfo.getTableName()).thenReturn("table");
        when(tableInfo.getColumns()).thenReturn(Arrays.asList(columnInfo1, columnInfo2));

        /* When */
        String result = mTableConverter.createTableStatement(tableInfo);

        /* Then */
        assertThat(result, is(CoreMatchers.equalTo("CREATE TABLE table(column1 INTEGER, column2 INTEGER)")));
    }

    @Test
    public void drop_statement() {
        /* Given */
        TableInfo tableInfo = mock(TableInfo.class);
        when(tableInfo.getTableName()).thenReturn("table");

        /* When */
        String result = mTableConverter.dropTableStatement(tableInfo);

        /* Then */
        assertThat(result, is(CoreMatchers.equalTo("DROP TABLE table")));
    }
}