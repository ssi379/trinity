package com.nhaarman.ellie.internal.codegen.column;

import com.nhaarman.lib_setup.annotations.Column;
import com.nhaarman.lib_setup.annotations.Foreign;
import com.nhaarman.lib_setup.annotations.PrimaryKey;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.NoType;
import javax.lang.model.type.TypeMirror;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("HardCodedStringLiteral") public class ColumnInfoFactoryTest {

    private ColumnInfoFactory mColumnInfoFactory;

    @Before
    public void setUp() throws Exception {
        mColumnInfoFactory = new ColumnInfoFactory();
    }

    @Test
    public void create_column_info_returns_column_info_instance() {
        /* Given */
        ExecutableElement element = mockGetterElement("name");

        /* When */
        ColumnMethodInfo result = mColumnInfoFactory.createColumnMethodInfo(element);

        /* Then */
        assertThat(result, is(not(nullValue())));
    }

    @Test
    public void created_column_info_has_column_name_set() {
        /* Given */
        ExecutableElement element = mockGetterElement("name");

        /* When */
        ColumnMethodInfo result = mColumnInfoFactory.createColumnMethodInfo(element);

        /* Then */
        assertThat(result.getColumnName(), is("name"));
    }

    @Test
    public void created_column_info_for_getter_has_type_set() {
        /* Given */
        ExecutableElement element = mockGetterElement("name");

        /* When */
        ColumnMethodInfo result = mColumnInfoFactory.createColumnMethodInfo(element);

        /* Then */
        assertThat(result.getType().toString(), is("java.lang.Long"));
    }

    @Test
    public void created_column_info_for_setter_has_type_set() {
        /* Given */
        ExecutableElement element = mockSetterElement("name");

        /* When */
        ColumnMethodInfo result = mColumnInfoFactory.createColumnMethodInfo(element);

        /* Then */
        assertThat(result, is(not(nullValue())));
        assertThat(result.getType(), is(not(nullValue())));
        assertThat(result.getType().toString(), is("java.lang.Long"));
    }

//    @Test
//    public void created_column_info_for_getter_has_getter_set() {
//        /* Given */
//        ExecutableElement getterElement = mockGetterElement("name");
//
//        /* When */
//        ColumnMethodInfo result = mColumnInfoFactory.createColumnMethodInfo(getterElement);
//
//        /* Then */
//        assertThat(result, is(not(nullValue())));
//        assertThat(result.getGetter(), is(getterElement));
//    }

//    @Test
//    public void created_column_info_for_setter_has_setter_set() {
//        /* Given */
//        ExecutableElement setterElement = mockSetterElement("name");
//
//        /* When */
//        ColumnMethodInfo result = mColumnInfoFactory.createColumnMethodInfo(setterElement);
//
//        /* Then */
//        assertThat(result, is(not(nullValue())));
//        assertThat(result.getSetter(), is(setterElement));
//    }

    @Test
    public void create_column_info_for_foreign_has_foreign_set() {
        /* Given */
        Foreign foreignAnnotation = mock(Foreign.class);

        ExecutableElement setterElement = mockSetterElement("name");
        when(setterElement.getAnnotation(argThat(is(anySubClass(Foreign.class))))).thenReturn(foreignAnnotation);

        /* When */
        ColumnMethodInfo result = mColumnInfoFactory.createColumnMethodInfo(setterElement);

        /* Then */
        assertThat(result.getForeignInfo(), is(not(nullValue())));
    }

    @Test
    public void create_column_info_for_foreign_has_foreign_table_set() {
        /* Given */
        Foreign foreignAnnotation = mock(Foreign.class);
        when(foreignAnnotation.tableName()).thenReturn("table");

        ExecutableElement setterElement = mockSetterElement("name");
        when(setterElement.getAnnotation(argThat(is(anySubClass(Foreign.class))))).thenReturn(foreignAnnotation);

        /* When */
        ColumnMethodInfo result = mColumnInfoFactory.createColumnMethodInfo(setterElement);

        /* Then */
        assertThat(result.getForeignInfo().tableName(), is(equalTo("table")));
    }

    @Test
    public void create_column_info_for_foreign_has_foreign_column_set() {
        /* Given */
        Foreign foreignAnnotation = mock(Foreign.class);
        when(foreignAnnotation.columnName()).thenReturn("column");

        ExecutableElement setterElement = mockSetterElement("name");
        when(setterElement.getAnnotation(argThat(is(anySubClass(Foreign.class))))).thenReturn(foreignAnnotation);

        /* When */
        ColumnMethodInfo result = mColumnInfoFactory.createColumnMethodInfo(setterElement);

        /* Then */
        assertThat(result.getForeignInfo().columnName(), is(equalTo("column")));
    }


    @Test
    public void create_column_info_for_primarykey_has_primarykey_set() {
        /* Given */
        PrimaryKey primaryKeyAnnotation = mock(PrimaryKey.class);

        ExecutableElement setterElement = mockSetterElement("name");
        when(setterElement.getAnnotation(argThat(is(anySubClass(PrimaryKey.class))))).thenReturn(primaryKeyAnnotation);

        /* When */
        ColumnMethodInfo result = mColumnInfoFactory.createColumnMethodInfo(setterElement);

        /* Then */
        assertThat(result.getPrimaryKeyInfo(), is(not(nullValue())));
    }

    @Test
    public void create_column_info_for_primarykey_with_autoincrement_has_autoincrement_set() {
        /* Given */
        PrimaryKey primaryKeyAnnotation = mock(PrimaryKey.class);
        when(primaryKeyAnnotation.autoIncrement()).thenReturn(true);

        ExecutableElement setterElement = mockSetterElement("name");
        when(setterElement.getAnnotation(argThat(is(anySubClass(PrimaryKey.class))))).thenReturn(primaryKeyAnnotation);

        /* When */
        ColumnMethodInfo result = mColumnInfoFactory.createColumnMethodInfo(setterElement);

        /* Then */
        assertThat(result.getPrimaryKeyInfo().autoIncrement(), is(true));
    }

    @Test
    public void create_column_info_for_primarykey_without_autoincrement_has_autoincrement_not_set() {
        /* Given */
        PrimaryKey primaryKeyAnnotation = mock(PrimaryKey.class);
        when(primaryKeyAnnotation.autoIncrement()).thenReturn(false);

        ExecutableElement setterElement = mockSetterElement("name");
        when(setterElement.getAnnotation(argThat(is(anySubClass(PrimaryKey.class))))).thenReturn(primaryKeyAnnotation);

        /* When */
        ColumnMethodInfo result = mColumnInfoFactory.createColumnMethodInfo(setterElement);

        /* Then */
        assertThat(result.getPrimaryKeyInfo().autoIncrement(), is(false));
    }

    @Test
    public void empty_set_should_return_empty_set() {
        /* Given */
        Set<ExecutableElement> set = new HashSet<>();

        /* When */
        Map<String, ColumnInfo> result = mColumnInfoFactory.createColumnInfoList(set);

        /* Then */
        assertThat(result.size(), is(0));
    }

    @Test
    public void getter_and_setter_return_single_element_list() {
        /* Given */
        ExecutableElement setterElement = mockSetterElement("name");
        ExecutableElement getterElement = mockGetterElement("name");

        Set<ExecutableElement> set = new HashSet<>();
        set.add(setterElement);
        set.add(getterElement);

        /* When */
        Map<String, ColumnInfo> result = mColumnInfoFactory.createColumnInfoList(set);

        /* Then */
        assertThat(result.size(), is(1));
    }

    @Test
    public void two_getters_and_setters_return_dual_element_list() {
        /* Given */
        ExecutableElement setterElement1 = mockSetterElement("name1");
        ExecutableElement getterElement1 = mockGetterElement("name1");
        ExecutableElement setterElement2 = mockSetterElement("name2");
        ExecutableElement getterElement2 = mockGetterElement("name2");


        Set<ExecutableElement> set = new HashSet<>();
        set.add(setterElement1);
        set.add(getterElement1);
        set.add(setterElement2);
        set.add(getterElement2);

        /* When */
        Map<String, ColumnInfo> result = mColumnInfoFactory.createColumnInfoList(set);

        /* Then */
        assertThat(result.size(), is(2));
    }

    private static ExecutableElement mockSetterElement(final String columnName) {
        Column column = mock(Column.class);
        when(column.value()).thenReturn(columnName);

        TypeMirror returnType = mock(NoType.class);

        TypeMirror parameterType = mock(TypeMirror.class);
        when(parameterType.toString()).thenReturn("java.lang.Long");

        //noinspection rawtypes
        List parameters = new ArrayList<>();
        VariableElement parameter = mock(VariableElement.class);
        parameters.add(parameter);
        when(parameter.asType()).thenReturn(parameterType);

        ExecutableElement setterElement = mock(ExecutableElement.class);
        when(setterElement.getAnnotation(argThat(is(anySubClass(Column.class))))).thenReturn(column);
        when(setterElement.getReturnType()).thenReturn(returnType);
        when(setterElement.getParameters()).thenReturn(parameters);
        return setterElement;
    }

    private static ExecutableElement mockGetterElement(final String name) {
        Column column = mock(Column.class);
        when(column.value()).thenReturn(name);

        TypeMirror typeMirror = mock(TypeMirror.class);
        when(typeMirror.toString()).thenReturn("java.lang.Long");

        ExecutableElement getterElement = mock(ExecutableElement.class);
        when(getterElement.getAnnotation(argThat(is(anySubClass(Column.class))))).thenReturn(column);
        when(getterElement.getReturnType()).thenReturn(typeMirror);
        return getterElement;
    }

    static Set<ExecutableElement> setOf(final ExecutableElement... elements) {
        Set<ExecutableElement> results = new HashSet<>(elements.length);
        Collections.addAll(results, elements);
        return results;
    }

    private static <T> ClassOrSubclassMatcher<T> anySubClass(final Class<T> targetClass) {
        return new ClassOrSubclassMatcher<>(targetClass);
    }

    private static class ClassOrSubclassMatcher<T> extends BaseMatcher<Class<T>> {

        private final Class<T> mTargetClass;

        ClassOrSubclassMatcher(final Class<T> targetClass) {
            mTargetClass = targetClass;
        }

        @Override
        public boolean matches(final Object item) {
            if (item != null) {
                if (item instanceof Class) {
                    return mTargetClass.isAssignableFrom((Class<T>) item);
                }
            }
            return false;
        }

        @Override
        public void describeTo(final Description description) {
            description.appendText("Matches a class or subclass");
        }
    }

}