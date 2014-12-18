package com.nhaarman.ellie.internal.codegen.column;

import com.nhaarman.lib_setup.annotations.Table;

import java.util.List;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;

public class ColumnMethodInfo {

    private final String mColumnName;
    private final ExecutableElement mExecutableElement;

    private TypeMirror mType;
    private ForeignInfo mForeignInfo;
    private PrimaryKeyInfo mPrimaryKeyInfo;

    public ColumnMethodInfo(final String columnName, final ExecutableElement executableElement) {
        mColumnName = columnName;
        mExecutableElement = executableElement;
    }

    public String getColumnName() {
        return mColumnName;
    }

    public TypeMirror getType() {
        return mType;
    }

    public void setType(final TypeMirror type) {
        mType = type;
    }

    public ForeignInfo getForeignInfo() {
        return mForeignInfo;
    }

    public void setForeignInfo(final ForeignInfo foreignInfo) {
        mForeignInfo = foreignInfo;
    }

    public PrimaryKeyInfo getPrimaryKeyInfo() {
        return mPrimaryKeyInfo;
    }

    public void setPrimaryKeyInfo(final PrimaryKeyInfo primaryKeyInfo) {
        mPrimaryKeyInfo = primaryKeyInfo;
    }

    public ExecutableElement getExecutableElement() {
        return mExecutableElement;
    }

    public AnnotationMirror getColumnAnnotationMirror() {
        AnnotationMirror result = null;

        List<? extends AnnotationMirror> annotationMirrors = mExecutableElement.getAnnotationMirrors();
        for (AnnotationMirror annotationMirror : annotationMirrors) {
            if (annotationMirror.getAnnotationType().toString().equals(Table.class.getCanonicalName())) {
                result = annotationMirror;
            }
        }
        return result;
    }
}
