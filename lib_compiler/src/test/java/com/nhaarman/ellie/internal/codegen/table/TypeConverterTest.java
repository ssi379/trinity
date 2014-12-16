package com.nhaarman.ellie.internal.codegen.table;

import com.nhaarman.ellie.internal.codegen.column.ColumnInfo;
import com.nhaarman.ellie.internal.codegen.column.TypeConverter;

import org.junit.Before;
import org.junit.Test;

import javax.lang.model.type.TypeMirror;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("HardCodedStringLiteral")
public class TypeConverterTest {

    private TypeConverter mTypeConverter;
    private TypeMirror mTypeMirror;
    private ColumnInfo mColumnInfo;

    @Before
    public void setUp() {
        mTypeConverter = new TypeConverter();
        mTypeMirror = mock(TypeMirror.class);

        mColumnInfo = mock(ColumnInfo.class);
        when(mColumnInfo.getType()).thenReturn(mTypeMirror);
    }

    @Test(expected = IllegalArgumentException.class)
    public void unknown_type_throws_exception() {
        /* Given */
        when(mTypeMirror.toString()).thenReturn("Some unknown class");

        /* When */
        mTypeConverter.toSQLiteType(mColumnInfo);

        /* Then */
        /* An exception should have been thrown by now. */
    }

    @Test
    public void java_util_boolean_type_returns_integer() {
        /* Given */
        when(mTypeMirror.toString()).thenReturn(Boolean.class.getCanonicalName());

        /* When */
        String result = mTypeConverter.toSQLiteType(mColumnInfo);

        /* Then */
        assertThat(result, is("INTEGER"));
    }

    @Test
    public void boolean_type_returns_integer() {
        /* Given */
        when(mTypeMirror.toString()).thenReturn(boolean.class.getCanonicalName());

        /* When */
        String result = mTypeConverter.toSQLiteType(mColumnInfo);

        /* Then */
        assertThat(result, is("INTEGER"));
    }

    @Test
    public void java_lang_integer_type_returns_integer() {
        /* Given */
        when(mTypeMirror.toString()).thenReturn(Integer.class.getCanonicalName());

        /* When */
        String result = mTypeConverter.toSQLiteType(mColumnInfo);

        /* Then */
        assertThat(result, is("INTEGER"));
    }

    @Test
    public void int_type_returns_integer() {
        /* Given */
        when(mTypeMirror.toString()).thenReturn(int.class.getCanonicalName());

        /* When */
        String result = mTypeConverter.toSQLiteType(mColumnInfo);

        /* Then */
        assertThat(result, is("INTEGER"));
    }

    @Test
    public void long_type_returns_integer() {
        /* Given */
        when(mTypeMirror.toString()).thenReturn(long.class.getCanonicalName());

        /* When */
        String result = mTypeConverter.toSQLiteType(mColumnInfo);

        /* Then */
        assertThat(result, is("INTEGER"));
    }

    @Test
    public void java_lang_long_type_returns_integer() {
        /* Given */
        when(mTypeMirror.toString()).thenReturn(Long.class.getCanonicalName());

        /* When */
        String result = mTypeConverter.toSQLiteType(mColumnInfo);

        /* Then */
        assertThat(result, is("INTEGER"));
    }

    @Test
    public void short_type_returns_integer() {
        /* Given */
        when(mTypeMirror.toString()).thenReturn(short.class.getCanonicalName());

        /* When */
        String result = mTypeConverter.toSQLiteType(mColumnInfo);

        /* Then */
        assertThat(result, is("INTEGER"));
    }

    @Test
    public void java_lang_short_type_returns_integer() {
        /* Given */
        when(mTypeMirror.toString()).thenReturn(Short.class.getCanonicalName());

        /* When */
        String result = mTypeConverter.toSQLiteType(mColumnInfo);

        /* Then */
        assertThat(result, is("INTEGER"));
    }

    @Test
    public void double_type_returns_real() {
        /* Given */
        when(mTypeMirror.toString()).thenReturn(double.class.getCanonicalName());

        /* When */
        String result = mTypeConverter.toSQLiteType(mColumnInfo);

        /* Then */
        assertThat(result, is("REAL"));
    }

    @Test
    public void java_lang_double_type_returns_real() {
        /* Given */
        when(mTypeMirror.toString()).thenReturn(Double.class.getCanonicalName());

        /* When */
        String result = mTypeConverter.toSQLiteType(mColumnInfo);

        /* Then */
        assertThat(result, is("REAL"));
    }

    @Test
    public void float_type_returns_real() {
        /* Given */
        when(mTypeMirror.toString()).thenReturn(float.class.getCanonicalName());

        /* When */
        String result = mTypeConverter.toSQLiteType(mColumnInfo);

        /* Then */
        assertThat(result, is("REAL"));
    }

    @Test
    public void java_lang_float_type_returns_real() {
        /* Given */
        when(mTypeMirror.toString()).thenReturn(Float.class.getCanonicalName());

        /* When */
        String result = mTypeConverter.toSQLiteType(mColumnInfo);

        /* Then */
        assertThat(result, is("REAL"));
    }

    @Test
    public void java_lang_string_type_returns_real() {
        /* Given */
        when(mTypeMirror.toString()).thenReturn(String.class.getCanonicalName());

        /* When */
        String result = mTypeConverter.toSQLiteType(mColumnInfo);

        /* Then */
        assertThat(result, is("TEXT"));
    }

    @Test
    public void foreign_type_returns_integer() {
        /* Given */
        when(mTypeMirror.toString()).thenReturn("com.example.Club");
        when(mColumnInfo.isForeign()).thenReturn(true);

        /* When */
        String result = mTypeConverter.toSQLiteType(mColumnInfo);

        /* Then */
        assertThat(result, is("INTEGER"));

    }

}