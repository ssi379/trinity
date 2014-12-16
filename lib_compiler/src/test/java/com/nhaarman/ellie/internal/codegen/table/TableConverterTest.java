package com.nhaarman.ellie.internal.codegen.table;

import com.nhaarman.ellie.internal.codegen.column.ColumnConverter;

import org.junit.Before;

import static org.mockito.Mockito.mock;

public class TableConverterTest {

    private TableConverter mTableConverter;
    private ColumnConverter mColumnConverter;

    @Before
    public void setUp() throws Exception {
        mColumnConverter = mock(ColumnConverter.class);
        mTableConverter = new TableConverter(mColumnConverter);
    }
}