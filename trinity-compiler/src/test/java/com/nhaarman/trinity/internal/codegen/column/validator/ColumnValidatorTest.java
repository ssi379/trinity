package com.nhaarman.trinity.internal.codegen.column.validator;

import com.nhaarman.trinity.internal.codegen.column.ColumnInfo;
import com.nhaarman.trinity.internal.codegen.column.ColumnMethodInfo;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ColumnValidatorTest {

    private ColumnValidator mColumnValidator;
    private Messager mMessager;

    @Before
    public void setUp() {
        mMessager = mock(Messager.class);
        mColumnValidator = new ColumnValidator(mMessager);
    }

    @Test
    public void columns_with_same_name_should_have_same_return_type() {
        /* Given */

        TypeMirror type1 = mock(TypeMirror.class);
        when(type1.toString()).thenReturn("java.lang.String");
        ExecutableElement executableElement1 = mock(ExecutableElement.class);
        when(executableElement1.getSimpleName()).thenReturn(mock(Name.class));
        ColumnMethodInfo columnMethodInfo1 = mock(ColumnMethodInfo.class);
        when(columnMethodInfo1.getType()).thenReturn(type1);
        when(columnMethodInfo1.getExecutableElement()).thenReturn(executableElement1);

        TypeMirror type2 = mock(TypeMirror.class);
        when(type2.toString()).thenReturn("java.lang.Long");
        ExecutableElement executableElement2 = mock(ExecutableElement.class);
        when(executableElement2.getSimpleName()).thenReturn(mock(Name.class));
        ColumnMethodInfo columnMethodInfo2 = mock(ColumnMethodInfo.class);
        when(columnMethodInfo2.getType()).thenReturn(type2);
        when(columnMethodInfo2.getExecutableElement()).thenReturn(executableElement2);

        ColumnInfo columnInfo = mock(ColumnInfo.class);
        when(columnInfo.getMethodInfos()).thenReturn(Arrays.asList(columnMethodInfo1, columnMethodInfo2));

        /* When */
        boolean result = mColumnValidator.validate(Collections.singletonList(columnInfo));

        /* Then */
        assertThat(result, is(false));
        verify(mMessager).printMessage(eq(Diagnostic.Kind.ERROR), anyString(), any(Element.class), any(AnnotationMirror.class));
    }


}