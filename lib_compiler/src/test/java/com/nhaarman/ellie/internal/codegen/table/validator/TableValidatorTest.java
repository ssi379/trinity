package com.nhaarman.ellie.internal.codegen.table.validator;

import com.nhaarman.ellie.internal.codegen.table.TableInfo;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TableValidatorTest {

    private Messager mMessager;
    private TableValidator mTableValidator;

    @Before
    public void setUp() throws Exception {
        mMessager = mock(Messager.class);
        mTableValidator = new TableValidator(mMessager);
    }

    @Test
    public void duplicate_table_names_fails_validation() {
        /* Given */
        TableInfo tableInfo1 = mock(TableInfo.class);
        when(tableInfo1.getTableName()).thenReturn("name");

        TableInfo tableInfo2 = mock(TableInfo.class);
        when(tableInfo2.getTableName()).thenReturn("name");

        /* When */
        boolean result = mTableValidator.validates(Arrays.asList(tableInfo1, tableInfo2));

        /* Then */
        assertThat(result, is(false));
        verify(mMessager).printMessage(eq(Diagnostic.Kind.ERROR), anyString(), any(Element.class), any(AnnotationMirror.class));
    }
}